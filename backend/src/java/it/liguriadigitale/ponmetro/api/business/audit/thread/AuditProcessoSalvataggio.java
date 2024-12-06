package it.liguriadigitale.ponmetro.api.business.audit.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.integration.dao.FdcAuditDAO;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditProcessoSalvataggio implements Runnable {

  private static Log log = LogFactory.getLog(AuditProcessoSalvataggio.class);

  private AuditDto auditData;

  public AuditProcessoSalvataggio(AuditDto auditData) {
    super();
    this.auditData = auditData;
  }

  @Override
  public void run() {
    log.debug("AuditProcessoSalvataggio in corso...");
    Thread ct = Thread.currentThread();
    ct.setName("Thread AuditProcessoSalvataggio");

    log.error(
        "[AUDIT] "
            + auditData.getIdFCitt()
            + "|"
            + auditData.getNomePagina()
            + "|"
            + auditData.getTimeStamp()
            + "|"
            + auditData.getIsAuthorized()
            + "|"
            + auditData.getSessionID()
            + "|"
            + auditData.getAmbienteDeploy()
            + "|"
            + auditData.getSesso()
            + "|"
            + auditData.getAnnoNascita()
            + "|"
            + auditData.getServizioEsterno());

    // TODO escludere salvataggio via properties
    FdcAudit fdcaudit = new FdcAudit();
    fdcaudit.setSessionId(auditData.getSessionID());
    fdcaudit.setAmbiente(auditData.getAmbienteDeploy());
    fdcaudit.setAutorizzato(auditData.getIsAuthorized());
    fdcaudit.setIdFcitt(BigDecimal.valueOf(auditData.getIdFCitt()));
    fdcaudit.setNomePagina(auditData.getNomePagina());
    fdcaudit.setTimeStamp(auditData.getTimeStamp());
    fdcaudit.setTipoUtente(Long.valueOf(auditData.getTipoUtente()));
    fdcaudit.setSesso(auditData.getSesso());
    fdcaudit.setServizioEsterno(auditData.getServizioEsterno());
    fdcaudit.setAnnoNascita(auditData.getAnnoNascita());
    FdcAuditDAO dao = new FdcAuditDAO(fdcaudit);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      helper.inserisciOggetto();
      log.info("Audit salvato");
    } catch (BusinessException e) {
      log.error("[AUDIT] Impossibile scrivere su tabella:", e);
    }
  }
}
