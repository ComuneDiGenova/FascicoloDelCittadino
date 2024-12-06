package it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service.ServiziGenovaParcheggiHelperService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziGenovaParcheggiHelperImpl implements ServiziGenovaParcheggiHelperService {

  private static Log log = LogFactory.getLog(ServiziGenovaParcheggiHelperImpl.class);

  @Override
  public List<BravFunzioni> getBravFunzioni() throws BusinessException {
    log.debug("getBravFunzioni:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGenovaParcheggiHelper().getBravFunzioni();
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getBravFunzioni: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Genova Parcheggi Helper"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public BravFunzioni getBravFunzione(String tipoFunz) throws BusinessException {
    log.debug("getBravFunzione:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGenovaParcheggiHelper().getBravFunzione(tipoFunz);
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getBravFunzione: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Genova Parcheggi Helper"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<BravPermessi> getBravPermessi(String tipoFunz) throws BusinessException {
    log.debug("getBravPermessi:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGenovaParcheggiHelper().getBravPermessi(tipoFunz);
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getBravPermessi: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Genova Parcheggi Helper"));
    } finally {
      instance.closeConnection();
    }
  }
}
