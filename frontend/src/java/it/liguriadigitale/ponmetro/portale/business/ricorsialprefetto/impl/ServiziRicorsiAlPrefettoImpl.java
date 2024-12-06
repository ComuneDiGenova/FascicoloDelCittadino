package it.liguriadigitale.ponmetro.portale.business.ricorsialprefetto.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.ricorsialprefetto.service.ServiziRicorsiAlPrefettoService;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsiAlPrefettoCollection;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziRicorsiAlPrefettoImpl implements ServiziRicorsiAlPrefettoService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_RICORSI_AL_PREFETTO =
      "Errore di connessione alle API Verbali Ricorsi al Prefetto";

  @Override
  public RicorsiAlPrefettoCollection getListaRircorsiAlPrefetto(String codiceFiscale)
      throws BusinessException, ApiException {
    log.debug("getListaRircorsiAlPrefetto");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRicorsiAlPrefetto().getRicorsiAlPrefetto(codiceFiscale);
    } catch (BusinessException e) {
      log.error(
          "ServiziRicorsiAlPrefettoImpl -- getListaRircorsiAlPrefetto: errore API Verbali Ricorsi al Prefetto:",
          e);
      throw new BusinessException(ERRORE_API_RICORSI_AL_PREFETTO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRicorsiAlPrefettoImpl -- getListaRircorsiAlPrefetto: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRicorsiAlPrefettoImpl -- getListaRircorsiAlPrefetto: errore durante la chiamata delle API Verbali Ricorsi al Prefetto",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Verbali Ricorsi al Prefetto"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
