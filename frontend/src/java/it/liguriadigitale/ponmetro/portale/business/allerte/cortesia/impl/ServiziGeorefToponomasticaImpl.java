package it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.Risposta;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service.ServiziGeorefToponomastica;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziGeorefToponomasticaImpl implements ServiziGeorefToponomastica {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_GEOREFERENZA_TOPONOMASTICA =
      "Errore di connessione alle API Georeferenza Toponomastica";

  @Override
  public Risposta getWsGetGeorefToponomastica(String strada)
      throws BusinessException, ApiException, IOException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiGeorefToponomastica()
          .rstGetCiviciRaggruppati(strada);

    } catch (BusinessException e) {
      log.error(
          "ServiziGeorefToponomasticaImpl -- getWsGetGeorefToponomastica: errore API Georeferenza Toponomastica:");
      throw new BusinessException(ERRORE_API_GEOREFERENZA_TOPONOMASTICA);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziGeorefToponomasticaImpl -- getWsGetGeorefToponomastica: errore durante la chiamata delle API Georeferenza Toponomastica"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_GEOREFERENZA_TOPONOMASTICA);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziGeorefToponomasticaImpl -- getWsGetGeorefToponomastica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGeorefToponomasticaImpl -- getWsGetGeorefToponomastica: errore durante la chiamata delle API Georeferenza Toponomastica ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Georeferenza Toponomastica"));
    }
  }
}
