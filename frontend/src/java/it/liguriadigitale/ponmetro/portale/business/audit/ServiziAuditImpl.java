package it.liguriadigitale.ponmetro.portale.business.audit;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziAuditImpl implements ServiziAuditService {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void trace(AuditDto dto) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      instance.getApiAudit().trace(dto);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
