package it.liguriadigitale.ponmetro.api.presentation.rest.breadcrumb;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.breadcrumbfdc.apiclient.BreadcrumbFdCClassicApi;
import it.liguriadigitale.ponmetro.breadcrumbfdc.model.BreadcrumbList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreadcrumbResource implements BreadcrumbFdCClassicApi {

  private static Log log = LogFactory.getLog(BreadcrumbResource.class);

  @Override
  public BreadcrumbList getBreadcrumbFdcClassic(Long idFcitt, Long idFunzFiglio) {
    try {
      return ServiceLocator.getInstance().getBreadcrumb().getBreadcrumb(idFcitt, idFunzFiglio);
    } catch (BusinessException e) {
      log.error("Errore durante getBreadcrumbFdcClassic: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare breadcrumb");
    }
  }
}
