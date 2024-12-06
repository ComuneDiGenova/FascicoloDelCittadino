package it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsAttestazioneIsee200;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsDichiarazioneIsee200;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.service.ServiziInpsModiImplService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoInps;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziInpsModiImpl implements ServiziInpsModiImplService {

  private Log log = LogFactory.getLog(getClass());

  private static final String FORMATO_RISPOSTA = "JSON";

  @Override
  public ConsultazioneAttestazioneCF200 consultazioneAttestazioneCF(
      ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody) throws BusinessException {

    log.debug(
        "[ServiziInpsModiImpl] consultazioneAttestazioneCF " + consultazioneAttestazioneCFBody);

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      ConsultazioneAttestazioneCF200 consultazioneAttestazioneCF200 =
          instance
              .getApiInpsModi()
              .postConsultazioneAttestazioneCF(consultazioneAttestazioneCFBody, FORMATO_RISPOSTA);
      return consultazioneAttestazioneCF200;

    } catch (BusinessException e) {
      log.error("[ServiziInpsModiImpl] consultazioneAttestazioneCF -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] consultazioneAttestazioneCF -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (Exception e) {
      log.error("[ServiziInpsModiImpl] Errore Exception di WSO2 " + e.getMessage());

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneAttestazioneCF200 consultazioneAttestazioneCFPerBorse(
      ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody) throws BusinessException {

    log.debug(
        "[ServiziInpsModiImpl] consultazioneAttestazioneCFPerBorse "
            + consultazioneAttestazioneCFBody);

    ServiceLocatorLivelloUnoInps instance = ServiceLocatorLivelloUnoInps.getInstance();

    try {

      ConsultazioneAttestazioneCF200 consultazioneAttestazioneCF200 =
          instance
              .getApiInpsModi()
              .postConsultazioneAttestazioneCF(consultazioneAttestazioneCFBody, FORMATO_RISPOSTA);
      return consultazioneAttestazioneCF200;

    } catch (BusinessException e) {
      log.error("[ServiziInpsModiImpl] consultazioneAttestazioneCFPerBorse -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] consultazioneAttestazioneCFPerBorse -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (Exception e) {
      log.error(
          "[ServiziInpsModiImpl] Errore Exception di WSO2 consultazioneAttestazioneCFPerBorse "
              + e.getMessage());

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneDichiarazioneCF200 consultazioneDichiarazioneCF(
      ConsultazioneDichiarazioneCFBody consultazioneDichiarazioneCFBody) throws BusinessException {

    log.debug("[ServiziInpsModiImpl] consultazioneDichiarazioneCF");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      ConsultazioneDichiarazioneCF200 consultazioneDichiarazioneCF =
          instance
              .getApiInpsModi()
              .postConsultazioneDichiarazioneCF(consultazioneDichiarazioneCFBody, FORMATO_RISPOSTA);
      return consultazioneDichiarazioneCF;

    } catch (BusinessException e) {
      log.error("[ServiziInpsModiImpl] consultazioneDichiarazioneCF -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] consultazioneDichiarazioneCF -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (Exception e) {
      log.error("[ServiziInpsModiImpl] Errore Exception di WSO2 " + e.getMessage());

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF(
      ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody) throws BusinessException {

    log.debug("[ServiziInpsModiImpl] consultazioneIndicatoreCF");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF =
          instance
              .getApiInpsModi()
              .postConsultazioneIndicatoreCF(consultazioneIndicatoreCFBody, FORMATO_RISPOSTA);
      return consultazioneIndicatoreCF;

    } catch (BusinessException e) {

      log.error("[ServiziInpsModiImpl] consultazioneIndicatoreCF -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] consultazioneIndicatoreCF -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (Exception e) {
      log.error("[ServiziInpsModiImpl] Errore Exception di WSO2 " + e.getMessage());

      if (!e.getMessage()
          .equalsIgnoreCase("javax.ws.rs.InternalServerErrorException: Internal Server Error")) {
        return null;
      } else {
        throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));
      }

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServiziInpsAttestazioneIsee200 attestazioneIsee(String codiceFiscale, String dataValidita)
      throws BusinessException {

    log.debug("[ServiziInpsModiImpl] attestazioneIsee");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      ServiziInpsAttestazioneIsee200 consultazioneAttestazione =
          instance.getApiInpsModi().getAttestazioneCF(codiceFiscale, dataValidita);
      return consultazioneAttestazione;

    } catch (BusinessException e) {
      log.error("[ServiziInpsModiImpl] attestazioneIsee -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] attestazioneIsee -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServiziInpsDichiarazioneIsee200 dichiarazioneISEE(
      String codiceFiscale, String dataValidita) throws BusinessException {

    log.debug("[ServiziInpsModiImpl dichiarazioneISEE");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      ServiziInpsDichiarazioneIsee200 consultazioneDichiarazione =
          instance.getApiInpsModi().getDichiarazioneCF(codiceFiscale, dataValidita);
      return consultazioneDichiarazione;

    } catch (BusinessException e) {
      log.error("[ServiziInpsModiImpl] dichiarazioneISEE -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsModiImpl] dichiarazioneISEE -- errore webapp:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
