package it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.controlli.albi.model.Elettorali;
import it.liguriadigitale.ponmetro.controlli.albi.model.Schedario;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service.ServiziControlliAlbiService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziControlliAlbiImpl implements ServiziControlliAlbiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_CONTROLLI_ALBI =
      "Errore di connessione alle API Controlli Albi";

  @Override
  public Elettorali getPresidenti(String codiceFiscale)
      throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      return instance.getApiControlliAlbi().presidenti(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziControlliAlbiImpl -- getPresidenti: errore API Controlli Albi:", e);
      throw new BusinessException(ERRORE_API_CONTROLLI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getPresidenti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getPresidenti: errore durante la chiamata delle API Controlli Albi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Albi Elettorali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Schedario getSchedario(String codiceFiscale)
      throws BusinessException, IOException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      return instance.getApiControlliAlbi().schedario(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziControlliAlbiImpl -- getSchedario: errore API Controlli Albi:", e);
      throw new BusinessException(ERRORE_API_CONTROLLI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getSchedario: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getSchedario: errore durante la chiamata delle API Controlli Albi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Albi Elettorali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Elettorali getScrutatori(String codiceFiscale)
      throws BusinessException, IOException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      return instance.getApiControlliAlbi().scrutatori(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziControlliAlbiImpl -- getScrutatori: errore API Controlli Albi:", e);
      throw new BusinessException(ERRORE_API_CONTROLLI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getScrutatori: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziControlliAlbiImpl -- getScrutatori: errore durante la chiamata delle API Controlli Albi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Albi Elettorali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
