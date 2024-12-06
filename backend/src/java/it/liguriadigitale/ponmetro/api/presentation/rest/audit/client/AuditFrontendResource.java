package it.liguriadigitale.ponmetro.api.presentation.rest.audit.client;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.audit.service.AuditRestInterface;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditFrontendResource implements AuditRestInterface {

  private static Log log = LogFactory.getLog(AuditFrontendResource.class);

  @Override
  public void trace(AuditDto dto) {

    try {
      ServiceLocator.getInstance().getAuditBackend().trace(dto);
    } catch (BusinessException e) {
      log.error("Errore Audit: " + e);
      throw new BadRequestException("Impossibile salvare l'Audit");
    }
  }
}
