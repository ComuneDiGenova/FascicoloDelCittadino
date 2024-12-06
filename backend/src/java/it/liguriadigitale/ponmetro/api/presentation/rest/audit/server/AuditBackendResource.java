package it.liguriadigitale.ponmetro.api.presentation.rest.audit.server;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.audit.apiclient.AuditApi;
import it.liguriadigitale.ponmetro.audit.model.AuditOutputList;
import it.liguriadigitale.ponmetro.audit.model.AuditRecord;
import java.time.LocalDate;
import java.util.List;
import javax.ws.rs.Path;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("/audit")
@Deprecated
public class AuditBackendResource implements AuditApi {

  private static Log log = LogFactory.getLog(AuditBackendResource.class);

  @Override
  @Deprecated
  public AuditRecord getAllAudit() {
    log.debug("--- AuditBackendResource getAllAudit INIZIO---");
    AuditRecord response = new AuditRecord();
    try {
      List<AuditOutputList> listaRecord =
          ServiceLocator.getInstance().getAuditBackend().estraiUltimiRecordAudit();
      response.setEsito(true);
      response.setListaRecord(listaRecord);
    } catch (BusinessException e) {
      log.error("Errore durante l'interrogazione del DB: ", e);
      response.setEsito(false);
    }
    return response;
  }

  @Override
  @Deprecated
  public AuditRecord getByDateAudit(Integer giorno, Integer mese, Integer anno) {
    log.debug("--- AuditBackendResource getByDateAudit INIZIO---");
    AuditRecord response = new AuditRecord();
    try {
      List<AuditOutputList> listaRecord =
          ServiceLocator.getInstance()
              .getAuditBackend()
              .estraiRecordAuditPerIntervalloDate(
                  LocalDate.of(anno, mese, giorno), LocalDate.now());
      response.setEsito(true);
      response.setListaRecord(listaRecord);
    } catch (BusinessException e) {
      log.error("Errore durante l'interrogazione del DB: ", e);
      response.setEsito(false);
    }
    return response;
  }
}
