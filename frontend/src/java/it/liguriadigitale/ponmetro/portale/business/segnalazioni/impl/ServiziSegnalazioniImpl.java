package it.liguriadigitale.ponmetro.portale.business.segnalazioni.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoSegnalazioni;
import it.liguriadigitale.ponmetro.portale.business.segnalazioni.service.ServiziSegnalazioniService;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.segnalazioni.model.PostCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.Stat;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSegnalazioniImpl implements ServiziSegnalazioniService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_SEGNALAZIONI =
      "Errore di connessione alle API Segnalazioni";

  @Override
  public PostCollection getPostCittadino(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziSegnalazioniImpl] getPostCittadino " + utente.getCodiceFiscaleOperatore());

      PostCollection postCollection =
          ServiceLocatorLivelloUnoSegnalazioni.getInstance()
              .getApiSegnalazioniPost()
              .loadPosts(null, null, null, null, utente.getCodiceFiscaleOperatore());
      return postCollection;

    } catch (BusinessException e) {
      log.error("ServiziSegnalazioniImpl -- getPostCittadino: errore API segnalazioni:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniImpl -- getPostCittadino: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziSegnalazioniImpl -- getPostCittadino: errore durante la chiamata delle API segnalazioni ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("segnalazioni"));
    }
  }

  @Override
  public Stat getSegnalazioniInviateAperteInCarico(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug(
          "[ServiziSegnalazioniImpl] getSegnalazioniInviateAperteInCarico "
              + utente.getCodiceFiscaleOperatore());

      String statIdentifier = "status";
      Stat stat =
          ServiceLocatorLivelloUnoSegnalazioni.getInstance()
              .getApiSegnalazioniStat()
              .getUserStatByIdentifier(
                  statIdentifier, utente.getCodiceFiscaleOperatore(), null, null, null);
      return stat;

    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioniImpl -- getSegnalazioniInviateAperteInCarico: errore API segnalazioni:",
          e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniImpl -- getSegnalazioniInviateAperteInCarico: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziSegnalazioniImpl -- getSegnalazioniInviateAperteInCarico: errore durante la chiamata delle API segnalazioni ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("segnalazioni"));
    }
  }
}
