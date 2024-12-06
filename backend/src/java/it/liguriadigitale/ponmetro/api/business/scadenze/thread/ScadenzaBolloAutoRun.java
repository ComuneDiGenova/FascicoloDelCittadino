package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.veicoli.impl.BolloImpl;
import it.liguriadigitale.ponmetro.api.business.veicoli.service.BolloInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgKeyValueDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgRFcittScadenzePersonaliDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgRFcittScadenzePersonaliSequenceDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittScadenzePersonaliInsertDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittScadenzePersonaliUpdateDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaBolloAutoRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaBolloAutoRun.class);

  private ScadenzePersonalizzateDto utenteDto;
  private VeicoloDto veicolo;

  public ScadenzaBolloAutoRun(ScadenzePersonalizzateDto utenteDto, VeicoloDto veicolo) {
    super();
    this.utenteDto = utenteDto;
    this.veicolo = veicolo;
    log.debug("[ScadenzaBolloAutoRun] INIZIO");
    log.debug("[ScadenzaBolloAutoRun] utenteDto=" + utenteDto);
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaBolloAutoRun run INIZIO---");
    log.debug("ScadenzePersonalizzateDto=" + utenteDto);
    Thread ct = Thread.currentThread();
    ct.setName("3dBollo_" + veicolo.getTarga());
    Boolean esito = true;
    try {
      LocalDate scadenzaBollo = ricavaScadenzaBollo();

      PonMetroDatasourceTransactionManager manager =
          new PonMetroDatasourceTransactionManager() {

            @Override
            protected void execute(Connection paramConnection) throws Exception {

              if (isScadenzaNonPresente(paramConnection, scadenzaBollo)) {
                inserisciNuovaScadenza(paramConnection, scadenzaBollo);
              }

              if (!esistonoScadenzeVeicoliNonPiuDiProprieta(paramConnection).isEmpty()) {
                log.debug("Ci sono veicoli non piu di proprieta");

                for (String oggetto : esistonoScadenzeVeicoliNonPiuDiProprieta(paramConnection)) {
                  log.debug("CP oggetto = " + oggetto);
                  cambioStatoScadenzaDiVeicoloNonPiuDiProprieta(paramConnection, oggetto);
                }

                ServiceLocator.getInstance()
                    .getScadenze()
                    .getListaScadenzeByCittadino(utenteDto.getIdFcitt());
              }
            }
          };
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata al BolloAuto: ", e);
      esito = false;
    }
    log.debug("--- ScadenzaBolloAutoRun FINE ---");
    return esito;
  }

  private LocalDate ricavaScadenzaBollo() throws BusinessException {
    log.debug("ricavaScadenzaBollo INIZIO");
    BolloInterface service = new BolloImpl();
    List<Bollo> listaBolli = service.getListaDettagliBolli(veicolo);

    if (!listaBolli.isEmpty()) {
      Bollo bollo = listaBolli.get(0);
      LocalDate primoGiornoUtile;
      try {
        LocalDate dataScadenzaBollo =
            LocalDate.parse("01/" + bollo.getScadenza(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        log.debug("dataScadenzaBollo=" + dataScadenzaBollo);
        primoGiornoUtile = dataScadenzaBollo.plusMonths(1).minusYears(1);
        log.debug("primoGiornoUtile=" + primoGiornoUtile);
        return primoGiornoUtile;
      } catch (DateTimeParseException e) {
        log.error("Scadenza non calcolabile", e);
        throw new BusinessException("Scadenza non calcolabile");
      }

    } else {
      log.error("Nessun bollo presente, scadenza non calcolabile");
      throw new BusinessException("Nessun bollo presente, scadenza non calcolabile");
    }
  }

  protected void cambioStatoScadenzaDiVeicoloNonPiuDiProprieta(
      Connection paramConnection, String oggetto) throws SQLException, BusinessException {
    log.debug("cambioStatoScadenzaDiVeicoloNonPiuDiProprieta INIZIO");

    CfgRFcittScadenzePersonali scadenze = new CfgRFcittScadenzePersonali();
    scadenze.setIdFcitt(utenteDto.getIdFcitt());
    scadenze.setIdStato(STATO_TOLTA_DALLA_PUBBLICAZIONE);
    scadenze.setIdTEvento(SCADENZA_BOLLO);
    scadenze.setUtenteAgg(BATCH_SCADENZE);
    scadenze.setOggetto(oggetto);
    scadenze.setDataAgg(LocalDateTime.now());

    CfgRFcittScadenzePersonaliUpdateDAO dao = new CfgRFcittScadenzePersonaliUpdateDAO(scadenze);
    dao.updateWhere(paramConnection);
  }

  protected void inserisciNuovaScadenza(Connection paramConnection, LocalDate scadenzaBollo)
      throws SQLException, BusinessException {
    log.debug("inserisciNuovaScadenza INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    scadenza.setDataScadenza(scadenzaBollo.atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setDataInvioMsg(
        controllaDataInvioMessaggioBollo(scadenzaBollo, idScadenza, utenteDto));
    scadenza.setIdScadenza(idScadenza);
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_BOLLO);
    scadenza.setScadenza(getTestoDaPubblicare(paramConnection) + " " + getTarga());
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(getTarga());
    CfgRFcittScadenzePersonaliInsertDAO dao = new CfgRFcittScadenzePersonaliInsertDAO(scadenza);
    dao.insertPrepared(paramConnection);
  }

  private LocalDateTime controllaDataInvioMessaggioBollo(
      LocalDate scadenzaBollo, Long idScadenza, ScadenzePersonalizzateDto utenteDto2) {
    LocalDateTime dataInvioMessaggio =
        seDataNelPassatoAvvertiUtente(scadenzaBollo.atStartOfDay(), idScadenza, utenteDto);
    return dataInvioMessaggio;
  }

  private String getTarga() {
    return veicolo.getTarga();
  }

  private Long getNextIdSequence(Connection paramConnection) throws SQLException {
    log.debug("getNextIdSequence INIZIO");
    CfgRFcittScadenzePersonaliSequenceDAO dao = new CfgRFcittScadenzePersonaliSequenceDAO();
    return (Long) dao.retrieveWhere(paramConnection).get(0);
  }

  private String getTestoDaPubblicare(Connection con) throws SQLException {
    log.debug("getTestoDaPubblicare INIZIO");
    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(TESTO_SCADENZA_BOLLO);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }

  protected boolean isScadenzaNonPresente(Connection paramConnection, LocalDate scadenzaBollo)
      throws SQLException, BusinessException {
    log.debug("isScadenzaNonPresente INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(scadenzaBollo.atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_BOLLO);
    scadenza.setOggetto(getTarga());
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente=" + lista.isEmpty());
    return lista.isEmpty();
  }

  protected List<String> esistonoScadenzeVeicoliNonPiuDiProprieta(Connection paramConnection)
      throws SQLException, BusinessException {
    log.debug("esistonoScadenzeVeicoliNonPiuDiProprieta INIZIO " + veicolo.getTarga());

    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_BOLLO);
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> listaScadenze = dao.retrieveWhere(paramConnection);

    List<VeicoloDto> listaVeicoliAttivi = utenteDto.getVeicoliAttivi();

    List<String> listaTargheDaBollo =
        listaVeicoliAttivi.stream()
            .map(VeicoloDto::getTarga)
            .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
            .collect(Collectors.toList());

    List<String> listaTargheDaScadenze =
        listaScadenze.stream()
            .map(CfgRFcittScadenzePersonali::getOggetto)
            .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
            .distinct()
            .collect(Collectors.toList());

    List<String> lista =
        listaTargheDaScadenze.stream()
            .filter(item -> !listaTargheDaBollo.contains(item))
            .collect(Collectors.toList());

    return lista;
  }
}
