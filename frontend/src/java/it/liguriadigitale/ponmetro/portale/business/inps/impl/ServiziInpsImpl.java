package it.liguriadigitale.ponmetro.portale.business.inps.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.inps.service.ServiziInpsService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.AttestazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.DichiarazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.time.LocalDate;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziInpsImpl implements ServiziInpsService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public AttestazioniISEE verificaPresentazioneISEE(Utente utente, LocalDate oggi)
      throws BusinessException {
    log.debug("[ServiziInpsImpl] verificaPresentazioneISEE -- INIZIO");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      AttestazioniISEE attestazione = instance.getApiInpsSenzaYaml().attestazioneIsee(utente, oggi);
      log.debug("Attestazione:" + attestazione);
      return attestazione;

    } catch (BusinessException e) {
      log.error("[ServiziInpsImpl] verificaPresentazioneISEE -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsImpl] verificaPresentazioneISEE -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziInpsImpl -- verificaPresentazioneISEE: errore durante la chiamata delle API INPS ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DichiarazioniISEE getDichiarazioneISEE(Utente utente, LocalDate oggi)
      throws BusinessException {
    log.debug("[ServiziInpsImpl] getDichiarazioneISEE -- INIZIO");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DichiarazioniISEE dichiarazione =
          instance.getApiInpsSenzaYaml().dichiarazioneIsee(utente, oggi);
      log.debug("Attestazione:" + dichiarazione);
      return dichiarazione;

    } catch (BusinessException e) {
      log.error("[ServiziInpsImpl] getDichiarazioneISEE -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsImpl] getDichiarazioneISEE -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziInpsImpl -- getDichiarazioneISEE: errore durante la chiamata delle API INPS ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
