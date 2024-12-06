package it.liguriadigitale.ponmetro.portale.business.plastipremia.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.plastipremia.model.Informazioni;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiCoupon;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiPunti;
import it.liguriadigitale.ponmetro.plastipremia.model.Problem;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.plastipremia.service.ServiziPlasTiPremiaService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziPlasTiPremiaImpl implements ServiziPlasTiPremiaService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_PLASTIPREMIA =
      "Errore di connessione alle API PlasTiPremia";

  @Override
  public PlastiCoupon getCoupon(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getCoupon: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPlastipremia().getCoupon(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziPlasTiPremiaImpl -- getCoupon: errore API punti TARI:", e);
      throw new BusinessException(ERRORE_API_PLASTIPREMIA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getCoupon: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getCoupon: errore durante la chiamata delle API PlasTiPremia ",
          e);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Informazioni getMessage(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getMessage: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPlastipremia().getMessage(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziPlasTiPremiaImpl -- getMessage: errore API punti TARI:", e);
      throw new BusinessException(ERRORE_API_PLASTIPREMIA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getMessage: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getMessage: errore durante la chiamata delle API PlasTiPremia ",
          e);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PlastiPunti getPunti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPunti: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPlastipremia().getPunti(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziPlasTiPremiaImpl -- getPunti: errore API punti TARI:", e);
      throw new BusinessException(ERRORE_API_PLASTIPREMIA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getPunti: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- getPunti: errore durante la chiamata delle API PlasTiPremia ",
          e);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Problem status() throws BusinessException, ApiException, IOException {
    log.debug("CP status: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPlastipremia().status();

    } catch (BusinessException e) {
      log.error("ServiziPlasTiPremiaImpl -- status: errore API punti TARI:", e);
      throw new BusinessException(ERRORE_API_PLASTIPREMIA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- status: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPlasTiPremiaImpl -- status: errore durante la chiamata delle API PlasTiPremia ",
          e);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
