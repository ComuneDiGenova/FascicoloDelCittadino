package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
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
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaAssicurazioneAutoRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaAssicurazioneAutoRun.class);

  private ScadenzePersonalizzateDto utenteDto;
  private VerificaAssicurazioneVeicoli assicurazione;

  public ScadenzaAssicurazioneAutoRun(
      ScadenzePersonalizzateDto utenteDto, VerificaAssicurazioneVeicoli assicurazione) {
    super();
    this.utenteDto = utenteDto;
    this.assicurazione = assicurazione;
    log.debug("[ScadenzaAssicurazioneAutoRun] INIZIO");
    log.debug("[ScadenzaAssicurazioneAutoRun] utenteDto=" + utenteDto);
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaAssicurazioneAutoRun run INIZIO---");
    log.debug("ScadenzePersonalizzateDto=" + utenteDto);
    Thread ct = Thread.currentThread();
    ct.setName("3dAssicurazione_" + getTarga());
    Boolean esito = true;
    try {
      PonMetroDatasourceTransactionManager manager =
          new PonMetroDatasourceTransactionManager() {

            @Override
            protected void execute(Connection paramConnection) throws Exception {
              if (isScadenzaNonPresente(paramConnection, assicurazione)) {
                inserisciNuovaScadenza(paramConnection);
              }

              if (!esistonoScadenzeVeicoliNonPiuDiProprieta(paramConnection).isEmpty()) {
                log.debug("Ci sono veicoli non piu di proprieta");

                for (String oggetto : esistonoScadenzeVeicoliNonPiuDiProprieta(paramConnection)) {
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
      log.debug("Errore: ", e);
      esito = false;
    }
    log.debug("--- ScadenzaAssicurazioneAutoRun FINE ---");
    return esito;
  }

  protected void inserisciNuovaScadenza(Connection paramConnection)
      throws SQLException, BusinessException {
    log.debug("inserisciNuovaScadenza INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    LocalDate dataScadenza = ricavaDataScadenzaAssicurazione(assicurazione);
    scadenza.setDataScadenza(dataScadenza.atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setIdScadenza(idScadenza);
    scadenza.setDataInvioMsg(controllaDataInvioMessaggio(dataScadenza, idScadenza, utenteDto));
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_ASSICURAZIONE);
    scadenza.setScadenza(getTestoDaPubblicare(paramConnection) + " " + getTarga());
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(getTarga());
    CfgRFcittScadenzePersonaliInsertDAO dao = new CfgRFcittScadenzePersonaliInsertDAO(scadenza);
    dao.insertPrepared(paramConnection);
  }

  private Long getNextIdSequence(Connection paramConnection) throws SQLException {
    log.debug("getNextIdSequence INIZIO");
    CfgRFcittScadenzePersonaliSequenceDAO dao = new CfgRFcittScadenzePersonaliSequenceDAO();
    return (Long) dao.retrieveWhere(paramConnection).get(0);
  }

  private String getTestoDaPubblicare(Connection con) throws SQLException {
    log.debug("getTestoDaPubblicare INIZIO");
    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(TESTO_SCADENZA_ASS_AUTO);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }

  private boolean isScadenzaNonPresente(
      Connection paramConnection, VerificaAssicurazioneVeicoli assicurazione)
      throws SQLException, BusinessException {
    log.debug("isScadenzaNonPresente INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(ricavaDataScadenzaAssicurazione(assicurazione).atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_ASSICURAZIONE);
    scadenza.setOggetto(getTarga());
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente=" + lista.isEmpty());
    return lista.isEmpty();
  }

  protected void cambioStatoScadenzaDiVeicoloNonPiuDiProprieta(
      Connection paramConnection, String oggetto) throws SQLException, BusinessException {
    log.debug("rimuoviScadenza INIZIO");

    CfgRFcittScadenzePersonali scadenze = new CfgRFcittScadenzePersonali();
    scadenze.setIdFcitt(utenteDto.getIdFcitt());
    scadenze.setIdStato(STATO_TOLTA_DALLA_PUBBLICAZIONE);
    scadenze.setIdTEvento(SCADENZA_ASSICURAZIONE);
    scadenze.setUtenteAgg(BATCH_SCADENZE);
    scadenze.setOggetto(oggetto);
    scadenze.setDataAgg(LocalDateTime.now());

    log.debug("CP scadenze = " + scadenze);

    CfgRFcittScadenzePersonaliUpdateDAO dao = new CfgRFcittScadenzePersonaliUpdateDAO(scadenze);
    dao.updateWhere(paramConnection);
  }

  private String getTarga() {
    try {
      return assicurazione
          .getVerificaAssicurazioneDatiAnagraficiResponse()
          .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
          .getTarga();
    } catch (Exception e) {
      log.error("Targa non presente");
      return "";
    }
  }

  private LocalDate ricavaDataScadenzaAssicurazione(VerificaAssicurazioneVeicoli assicurazione)
      throws BusinessException {

    if (assicurazione
            .getVerificaAssicurazioneDatiAnagraficiResponse()
            .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
            .getDataScadenzaAssicurazione()
        != null) {
      String scadenzaAssicurazione =
          assicurazione
              .getVerificaAssicurazioneDatiAnagraficiResponse()
              .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
              .getDataScadenzaAssicurazione();
      log.debug("scadenzaAssicurazione=" + scadenzaAssicurazione);
      LocalDate dataScadenzaAssicurazione =
          LocalDate.parse(scadenzaAssicurazione, DateTimeFormatter.ISO_DATE);
      log.debug("dataScadenzaAssicurazione=" + dataScadenzaAssicurazione);
      return dataScadenzaAssicurazione;
    } else {
      throw new BusinessException("Nessuna data assicurazione");
    }
  }

  protected List<String> esistonoScadenzeVeicoliNonPiuDiProprieta(Connection paramConnection)
      throws SQLException, BusinessException {
    log.debug("esistonoScadenzeVeicoliNonPiuDiProprieta INIZIO ");

    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_ASSICURAZIONE);
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
