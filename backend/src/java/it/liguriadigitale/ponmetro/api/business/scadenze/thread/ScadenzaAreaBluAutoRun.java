package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgKeyValueDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgRFcittScadenzePersonaliDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgRFcittScadenzePersonaliSequenceDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittScadenzePersonaliInsertDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AreaBluDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaAreaBluAutoRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaAbbonamentiAmtAutoRun.class);

  private ScadenzePersonalizzateDto utenteDto;

  public ScadenzaAreaBluAutoRun(ScadenzePersonalizzateDto utenteDto) {
    super();
    this.utenteDto = utenteDto;
    log.debug("[ScadenzaAreaBluAutoRun] INIZIO");
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaAreaBluAutoRun run INIZIO---");
    log.debug("ScadenzaAreaBluAutoRun=" + utenteDto);
    Thread ct = Thread.currentThread();
    Boolean esito = true;

    for (AreaBluDto areaBluDto : utenteDto.getPermessiAreaBlu()) {
      ct.setName("3dAreablu_" + areaBluDto.getPermitCode());

      try {
        PonMetroDatasourceTransactionManager manager =
            new PonMetroDatasourceTransactionManager() {

              @Override
              protected void execute(Connection paramConnection) throws Exception {
                LocalDate dataScadenza = ricavaScadenzaAreaBlu(areaBluDto);
                if (dataScadenza.isAfter(LocalDate.now())
                    && isScadenzaNonPresente(paramConnection, areaBluDto)) {
                  inserisciNuovaScadenza(paramConnection, areaBluDto);
                }
              }
            };
        manager.executeTransaction();
      } catch (BusinessException e) {
        log.error("Errore durante la chiamata all' AMT: ", e);
        esito = false;
      }
      log.debug("--- ScadenzaAreaBluAutoRun FINE ---");
    }
    return esito;
  }

  private LocalDate ricavaScadenzaAreaBlu(AreaBluDto areaBluDto) throws BusinessException {
    log.debug("ricavaScadenzaAreaBlu INIZIO: " + areaBluDto);
    LocalDate datafineValidita = areaBluDto.getValidTo().toLocalDate();
    return datafineValidita;
  }

  protected void inserisciNuovaScadenza(Connection paramConnection, AreaBluDto areaBluDto)
      throws SQLException, BusinessException {
    log.debug("inserisciNuovaScadenza Area Blu INIZIO");
    LocalDate scadenzaAreaBlu = ricavaScadenzaAreaBlu(areaBluDto);
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    scadenza.setDataScadenza(scadenzaAreaBlu.atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setDataInvioMsg(controllaDataInvioMessaggio(scadenzaAreaBlu, idScadenza, utenteDto));
    scadenza.setIdScadenza(idScadenza);
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_PERMESSI_GENOVA_PARCHEGGI);
    scadenza.setScadenza(
        getTestoDaPubblicare(paramConnection)
            + " "
            + areaBluDto.getPermitCode()
            + " "
            + areaBluDto.getCategoryDescription()
            + " - "
            + areaBluDto.getZoneDescription());
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(areaBluDto.getPermitCode());
    CfgRFcittScadenzePersonaliInsertDAO dao = new CfgRFcittScadenzePersonaliInsertDAO(scadenza);
    dao.insertPrepared(paramConnection);
  }

  private Long getNextIdSequence(Connection paramConnection) throws SQLException {
    log.debug("getNextIdSequence Area Blu INIZIO");
    CfgRFcittScadenzePersonaliSequenceDAO dao = new CfgRFcittScadenzePersonaliSequenceDAO();
    return (Long) dao.retrieveWhere(paramConnection).get(0);
  }

  private String getTestoDaPubblicare(Connection con) throws SQLException {
    log.debug("getTestoDaPubblicare Area Blu INIZIO");
    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(TESTO_SCADENZA_PERMESSI_GENOVA_PARCHEGGI);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }

  @SuppressWarnings("unchecked")
  protected boolean isScadenzaNonPresente(Connection paramConnection, AreaBluDto areaBluDto)
      throws SQLException, BusinessException {
    log.debug("isScadenzaNonPresente Area Blu INIZIO");
    LocalDate scadenzaAreaBlu = ricavaScadenzaAreaBlu(areaBluDto);
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(scadenzaAreaBlu.atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_PERMESSI_GENOVA_PARCHEGGI);
    scadenza.setOggetto(String.valueOf(areaBluDto.getPermitCode()));
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente=" + lista.isEmpty());
    return lista.isEmpty();
  }
}
