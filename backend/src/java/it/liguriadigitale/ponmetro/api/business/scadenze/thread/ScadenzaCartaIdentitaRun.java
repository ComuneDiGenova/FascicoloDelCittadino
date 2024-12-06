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
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaCartaIdentitaRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaCartaIdentitaRun.class);

  private ScadenzePersonalizzateDto utenteDto;

  public ScadenzaCartaIdentitaRun(ScadenzePersonalizzateDto utenteDto) {
    super();
    this.utenteDto = utenteDto;
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaCartaIdentitaRun run INIZIO---");
    log.debug("ScadenzePersonalizzateDto=" + utenteDto);
    Thread ct = Thread.currentThread();
    ct.setName("3dCI_" + utenteDto.getIdFcitt());
    Boolean esito = true;
    try {
      PonMetroDatasourceTransactionManager manager =
          new PonMetroDatasourceTransactionManager() {

            @Override
            protected void execute(Connection paramConnection) throws Exception {
              if (isScadenzaNonPresente(utenteDto, paramConnection)) {
                // updateScadenzePrecedenti(utenteDto, paramConnection);
                inserisciNuovaScadenza(utenteDto, paramConnection);
              }
            }
          };
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.debug("Errore: ", e);
      esito = false;
    }
    log.debug("--- ScadenzaCartaIdentitaRun FINE ---");
    return esito;
  }

  private void inserisciNuovaScadenza(
      ScadenzePersonalizzateDto utenteDto, Connection paramConnection) throws SQLException {
    log.debug("inserisciNuovaScadenza INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    scadenza.setDataScadenza(utenteDto.getDataScadenza().atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setDataInvioMsg(
        controllaDataInvioMessaggio(utenteDto.getDataScadenza(), idScadenza, utenteDto));
    scadenza.setIdScadenza(idScadenza);
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_CIE);
    scadenza.setScadenza(getTestoDaPubblicare(paramConnection) + " di " + utenteDto.getNome());
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(utenteDto.getCodiceFiscale());
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
    cfgkeyvalue.setCfgKey(TESTO_SCADENZA_CIE);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }

  private void updateScadenzePrecedenti(
      ScadenzePersonalizzateDto utenteDto, Connection paramConnection) throws SQLException {
    log.debug("updateScadenzePrecedenti INIZIO");
    CfgRFcittScadenzePersonali scadenze = new CfgRFcittScadenzePersonali();
    scadenze.setIdFcitt(utenteDto.getIdFcitt());
    scadenze.setIdStato(3L);
    scadenze.setIdTEvento(SCADENZA_CIE);
    scadenze.setUtenteAgg(BATCH_SCADENZE);
    scadenze.setOggetto(utenteDto.getCodiceFiscale());
    scadenze.setDataAgg(LocalDateTime.now());
    CfgRFcittScadenzePersonaliUpdateDAO dao = new CfgRFcittScadenzePersonaliUpdateDAO(scadenze);
    dao.updateWhere(paramConnection);
  }

  @SuppressWarnings("unchecked")
  private boolean isScadenzaNonPresente(
      ScadenzePersonalizzateDto utenteDto, Connection paramConnection) throws SQLException {
    log.debug("isScadenzaNonPresente INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(utenteDto.getDataScadenza().atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_CIE);
    scadenza.setOggetto(utenteDto.getCodiceFiscale());
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente=" + lista.isEmpty());
    return lista.isEmpty();
  }
}
