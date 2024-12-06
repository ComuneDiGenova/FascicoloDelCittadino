package it.liguriadigitale.ponmetro.api.presentation.rest.geoworks;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.contatti.ContattiResource;
import it.liguriadigitale.ponmetro.geoworkshelper.apiclient.GeoworksHelperApi;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeoworksResource implements GeoworksHelperApi {

  private static Log log = LogFactory.getLog(ContattiResource.class);

  @Override
  public List<GeoworksFunzioni> getGeoworksFunzioni() {
    try {
      return ServiceLocator.getInstance().getGeoworksHelper().getGeoworksFunzioni();
    } catch (BusinessException e) {
      log.error("Errore durante getGeoworksFunzioni: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare funzioni geoworks");
    }
  }

  @Override
  public List<GeoworksServizi> getGeoworksServizi(String tipoFunz) {
    try {
      return ServiceLocator.getInstance().getGeoworksHelper().getGeoworksServizi(tipoFunz);
    } catch (BusinessException e) {
      log.error("Errore durante getGeoworksServizi: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare servizi geoworks");
    }
  }

  @Override
  public GeoworksFunzioni getGeoworksFunzione(String tipoFunz) {
    try {
      return ServiceLocator.getInstance().getGeoworksHelper().getGeoworksFunzione(tipoFunz);
    } catch (BusinessException e) {
      log.error("Errore durante getGeoworksFunzione: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare funzione geoworks");
    }
  }
}
