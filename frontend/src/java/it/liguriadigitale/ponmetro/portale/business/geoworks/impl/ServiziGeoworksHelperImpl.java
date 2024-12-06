package it.liguriadigitale.ponmetro.portale.business.geoworks.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.geoworks.service.ServiziGeoworksHelperService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziGeoworksHelperImpl implements ServiziGeoworksHelperService {

  private static Log log = LogFactory.getLog(ServiziGeoworksHelperImpl.class);

  @Override
  public List<GeoworksFunzioni> getGeoworksFunzioni() throws BusinessException {
    log.debug("getGeoworksFunzioni:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGeoworksHelper().getGeoworksFunzioni();
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getGeoworksFunzioni: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks Helper"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<GeoworksServizi> getGeoworksServizi(String tipoFunz) throws BusinessException {
    log.debug("getGeoworksServizi:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGeoworksHelper().getGeoworksServizi(tipoFunz);
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getGeoworksServizi: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks Helper"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public GeoworksFunzioni getGeoworksFunzione(String tipoFunz) throws BusinessException {
    log.debug("getGeoworksFunzione:");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiGeoworksHelper().getGeoworksFunzione(tipoFunz);
    } catch (BusinessException | WebApplicationException e) {
      log.error("Errore getGeoworksFunzione: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks Helper"));
    } finally {
      instance.closeConnection();
    }
  }
}
