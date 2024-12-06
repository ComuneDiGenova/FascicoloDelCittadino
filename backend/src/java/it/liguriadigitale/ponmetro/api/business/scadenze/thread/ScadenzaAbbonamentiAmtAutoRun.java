package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenza;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenze;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgKeyValueDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgRFcittScadenzePersonaliDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgRFcittScadenzePersonaliSequenceDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittScadenzePersonaliInsertDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AbbonamentoAmtDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.util.LocalDateUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaAbbonamentiAmtAutoRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaAbbonamentiAmtAutoRun.class);

  private ScadenzePersonalizzateDto utenteDto;

  public ScadenzaAbbonamentiAmtAutoRun(ScadenzePersonalizzateDto utenteDto) {
    super();
    this.utenteDto = utenteDto;
    log.debug("[ScadenzaAbbonamentiAmtAutoRun] INIZIO");
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaAbbonamentiAmtAutoRun run INIZIO---");
    log.debug("ScadenzaAbbonamentiAmtAutoRun=" + utenteDto);
    Thread ct = Thread.currentThread();
    Boolean esito = true;

    for (AbbonamentoAmtDto abbonamentoDto : utenteDto.getAbbonamentiAmt()) {

      List<AbbonamentoAmtDto> listAbbonamentiPerSingolo =
          ricavaListaScadenzeDaApiAMT(abbonamentoDto);

      esito = controlloListaAbbonamentiSingolo(ct, esito, listAbbonamentiPerSingolo);
    }
    return esito;
  }

  private Boolean controlloListaAbbonamentiSingolo(
      Thread ct, Boolean esito, List<AbbonamentoAmtDto> listAbbonamentiPerSingolo) {
    for (AbbonamentoAmtDto abbonamentoSingoloIndividuo : listAbbonamentiPerSingolo) {

      ct.setName("3dAbbonamentiAmt_" + abbonamentoSingoloIndividuo.getCityPass());

      try {
        PonMetroDatasourceTransactionManager manager =
            new PonMetroDatasourceTransactionManager() {

              @Override
              protected void execute(Connection paramConnection) throws Exception {
                LocalDate dataScadenza = ricavaScadenzaAbbonamento(abbonamentoSingoloIndividuo);
                if (dataScadenza != null
                    && dataScadenza.isAfter(LocalDate.now())
                    && isScadenzaNonPresente(paramConnection, abbonamentoSingoloIndividuo)) {
                  inserisciNuovaScadenza(paramConnection, abbonamentoSingoloIndividuo);
                }
              }
            };
        manager.executeTransaction();
      } catch (BusinessException e) {
        log.error("Errore durante la chiamata all' AMT: ", e);
        esito = false;
      }
      log.debug("--- ScadenzaAbbonamentiAmtAutoRun FINE ---");
    }
    return esito;
  }

  private List<AbbonamentoAmtDto> ricavaListaScadenzeDaApiAMT(AbbonamentoAmtDto abbonamentoDto)
      throws BusinessException {
    log.debug("ScadenzaAbbonamentiAmtAutoRun > call > ricavaListaScadenzeDaApiAMT");
    List<AbbonamentoAmtDto> listaAbbonamenti = new ArrayList<AbbonamentoAmtDto>();

    Scadenze scadenze =
        ServiceLocatorLivelloUno.getInstance()
            .getApiAbbonamentiAmt()
            .getScadenze(abbonamentoDto.getCodiceFiscale());
    utenteDto.setNome(abbonamentoDto.getNome());
    List<Scadenza> listaScadenzeTessere = new ArrayList<Scadenza>();
    listaScadenzeTessere.addAll(scadenze.getScadenzaTessere());

    for (Scadenza scadenza : listaScadenzeTessere) {
      AbbonamentoAmtDto abbonamento = new AbbonamentoAmtDto();
      abbonamento.setCodiceFiscale(scadenze.getCodiceFiscale());
      abbonamento.setCognome(abbonamentoDto.getCognome());
      abbonamento.setCityPass(scadenza.getCityPass());
      abbonamento.setDescrizioneTessera(scadenza.getDescrizioneTessera());
      abbonamento.setFineValidita(
          scadenza.getFineValidita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      abbonamento.setNome(abbonamentoDto.getNome());

      listaAbbonamenti.add(abbonamento);
    }
    log.debug(
        "lista abbonamenti da scadenze AMT per "
            + abbonamentoDto.getCognome()
            + " "
            + abbonamentoDto.getNome()
            + "=\n"
            + listaAbbonamenti);

    return listaAbbonamenti;
  }

  private LocalDate ricavaScadenzaAbbonamento(AbbonamentoAmtDto abbonamentoDto)
      throws BusinessException {
    log.debug("ricavaScadenzaAbbonamento INIZIO: " + abbonamentoDto);

    LocalDate datafineValidita = null;

    if (abbonamentoDto.getFineValidita() != null && !abbonamentoDto.getFineValidita().isEmpty()) {
      datafineValidita = LocalDateUtil.convertiDaFormatoEuropeo(abbonamentoDto.getFineValidita());
    }

    return datafineValidita;
  }

  protected void inserisciNuovaScadenza(
      Connection paramConnection, AbbonamentoAmtDto abbonamentoDto)
      throws SQLException, BusinessException {
    log.debug("inserisciNuovaScadenza INIZIO");
    LocalDate scadenzaAbbonamento = ricavaScadenzaAbbonamento(abbonamentoDto);
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    scadenza.setDataScadenza(scadenzaAbbonamento.atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setDataInvioMsg(
        controllaDataInvioMessaggio(scadenzaAbbonamento, idScadenza, utenteDto));
    scadenza.setIdScadenza(idScadenza);
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_ABBONAMENTI_AMT);
    scadenza.setScadenza(
        getTestoDaPubblicare(paramConnection)
            + " "
            + abbonamentoDto.getCityPass()
            + " di "
            + utenteDto.getNome());
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(abbonamentoDto.getCityPass());
    CfgRFcittScadenzePersonaliInsertDAO dao = new CfgRFcittScadenzePersonaliInsertDAO(scadenza);
    dao.insertPrepared(paramConnection);
  }

  private Long getNextIdSequence(Connection paramConnection) throws SQLException {
    log.debug("getNextIdSequence AMT INIZIO");
    CfgRFcittScadenzePersonaliSequenceDAO dao = new CfgRFcittScadenzePersonaliSequenceDAO();
    return (Long) dao.retrieveWhere(paramConnection).get(0);
  }

  private String getTestoDaPubblicare(Connection con) throws SQLException {
    log.debug("getTestoDaPubblicare AMT INIZIO");
    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(TESTO_SCADENZA_ABBONAMENTI_AMT);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }

  @SuppressWarnings("unchecked")
  protected boolean isScadenzaNonPresente(
      Connection paramConnection, AbbonamentoAmtDto abbonamentoDto)
      throws SQLException, BusinessException {
    log.debug("isScadenzaNonPresente AMT INIZIO");
    LocalDate scadenzaAbbonamento = ricavaScadenzaAbbonamento(abbonamentoDto);
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(scadenzaAbbonamento.atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_ABBONAMENTI_AMT);
    scadenza.setOggetto(abbonamentoDto.getCityPass());
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente=" + lista.isEmpty());
    return lista.isEmpty();
  }
}
