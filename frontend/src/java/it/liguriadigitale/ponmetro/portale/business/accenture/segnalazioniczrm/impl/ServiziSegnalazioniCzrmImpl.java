package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service.ServiziSegnalazioniCzrmService;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.util.SegnalazioniCzrmUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountIdCasesGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsEmailMessageIDGet200Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSegnalazioniCzrmImpl implements ServiziSegnalazioniCzrmService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_SEGNALAZIONI_CZRM =
      "Errore di connessione alle API Segnalazioni CZRM";

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException {

    log.debug("ServiziSegnalazioniCzrmImpl getRecuperoUtenteByCodiceFiscale = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {
      response =
          instance
              .getApiAccenture()
              .servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(codiceFiscale);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByCodiceFiscale: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {

    log.debug("ServiziSegnalazioniCzrmImpl getRecuperoUtenteByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {
      response = instance.getApiAccenture().servicesDataV580SobjectsAccountIdGet(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByIdAccenture: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getRecuperoUtenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV590SobjectsAccountIdCasesGet200Response
      getListaSegnalazioniByIdUtenteAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {

    log.debug(
        "ServiziSegnalazioniCzrmImpl getListaSegnalazioniByIdUtenteAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsAccountIdCasesGet200Response response = null;

    try {
      response =
          instance
              .createServiziRichiestaAssistenzaApi()
              .servicesDataV590SobjectsAccountIdCasesGet(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniByIdUtenteAccenture: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniByIdUtenteAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniByIdUtenteAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public SegnalazioniCzrmUtil getListaSegnalazioniCzrm(String codiceFiscale)
      throws BusinessException, IOException, ApiException {

    log.debug("ServiziSegnalazioniCzrmImpl getListaSegnalazioniCzrm = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    SegnalazioniCzrmUtil response = null;

    try {

      ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response utenteAccentureByCf =
          getRecuperoUtenteByCodiceFiscale(codiceFiscale);

      if (LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
        return response;
      }

      String idUtenteAccentureByCf = utenteAccentureByCf.getId();

      ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response idUtenteAccenture =
          getRecuperoUtenteByIdAccenture(idUtenteAccentureByCf);

      if (LabelFdCUtil.checkIfNull(idUtenteAccenture)) {
        return response;
      }

      response = new SegnalazioniCzrmUtil();
      response.setNomeCognome(utenteAccentureByCf.getName());
      response.setEmail(utenteAccentureByCf.getEmailC());
      response.setSegnalazioni(getListaSegnalazioniByIdUtenteAccenture(idUtenteAccenture.getId()));

      return response;
    } catch (BusinessException e) {
      log.error("ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniCzrm: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniCzrm: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaSegnalazioniCzrm: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV590SobjectsCaseIdEmailsGet200Response getListaMailSegnalazione(
      String idAccenture, String emailAccenture)
      throws BusinessException, IOException, ApiException {
    log.debug("ServiziSegnalazioniCzrmImpl getTestMailSegnalazione = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsCaseIdEmailsGet200Response response = null;
    ServicesDataV590SobjectsCaseIdEmailsGet200Response responseFiltrata = null;

    try {
      response =
          instance.getApiRichiestaAssistenza().servicesDataV590SobjectsCaseIdEmailsGet(idAccenture);

      if (LabelFdCUtil.checkIfNotNull(response)
          && LabelFdCUtil.checkIfNotNull(response.getRecords())) {
        List<ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner> listaFiltrata =
            response.getRecords().stream()
                .filter(elemMail -> elemMail.getFromAddress().equalsIgnoreCase(emailAccenture))
                .collect(Collectors.toList());

        responseFiltrata = new ServicesDataV590SobjectsCaseIdEmailsGet200Response();
        responseFiltrata.setRecords(listaFiltrata);
      }

      return responseFiltrata;

    } catch (BusinessException e) {
      log.error("ServiziSegnalazioniCzrmImpl -- getTestMailSegnalazione: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getTestMailSegnalazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getTestMailSegnalazione: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV590SobjectsCaseIdCommentiRGet200Response getListaCommentiSegnalazione(
      String idAccenture, String nomeCognome) throws BusinessException, IOException, ApiException {
    log.debug("ServiziSegnalazioniCzrmImpl getListaCommentiSegnalazione = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsCaseIdCommentiRGet200Response response = null;
    ServicesDataV590SobjectsCaseIdCommentiRGet200Response responseFiltrata = null;

    try {
      response =
          instance
              .getApiRichiestaAssistenza()
              .servicesDataV590SobjectsCaseIdCommentiRGet(idAccenture);

      if (LabelFdCUtil.checkIfNotNull(response)
          && LabelFdCUtil.checkIfNotNull(response.getRecords())) {
        List<ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner> listaFiltrata =
            response.getRecords().stream()
                .filter(
                    elemCommento ->
                        elemCommento
                            .getTipoCommentoC()
                            .equalsIgnoreCase(
                                "Commento pubblico") /*&& elemCommento.getAutoreCommentoFormulaC().equalsIgnoreCase(nomeCognome)*/)
                .collect(Collectors.toList());

        responseFiltrata = new ServicesDataV590SobjectsCaseIdCommentiRGet200Response();
        responseFiltrata.setRecords(listaFiltrata);
      }

      return responseFiltrata;
    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaCommentiSegnalazione: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaCommentiSegnalazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getListaCommentiSegnalazione: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV590SobjectsEmailMessageIDGet200Response getMailDaCommento(
      String idEmailMessage) throws BusinessException, IOException, ApiException {
    log.debug("ServiziSegnalazioniCzrmImpl getMailDaCommento = " + idEmailMessage);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    try {

      return instance
          .getApiRichiestaAssistenza()
          .servicesDataV590SobjectsEmailMessageIDGet(idEmailMessage);

    } catch (BusinessException e) {
      log.error("ServiziSegnalazioniCzrmImpl -- getMailDaCommento: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONI_CZRM);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getMailDaCommento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniCzrmImpl -- getMailDaCommento: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
