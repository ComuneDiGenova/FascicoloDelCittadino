package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.service.ServiziSegnalazioneDanniBeniPrivati;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSegnalazioneDanniBeniPrivatiImpl
    implements ServiziSegnalazioneDanniBeniPrivati {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_SEGNALAZIONE_DANNI_BENI_PRIVATI =
      "Errore di connessione alle API Domande segnalazione danni beni privati";

  @Override
  public ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response
      getListSegnalazioneDanniBeniPrivati(String codiceFiscale, TipologiaRichiestaEnum tipo)
          throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    log.debug("cf_dal_busniness = " + codiceFiscale + " TipologiaRichiestaEnum " + tipo);

    try {

      ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response utenteAccentureByCf =
          getRecuperoUtenteByCodiceFiscale(codiceFiscale);

      if (LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
        return null;
      }

      String idUtenteAccentureByCf = utenteAccentureByCf.getId();

      ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response utemteById =
          getRecuperoUtenteByIdAccenture(idUtenteAccentureByCf);

      log.debug("utemteById = " + utemteById);

      ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response domande =
          instance
              .getApiSegnalazioneDaniBeniPrivati()
              .servicesDataV580SobjectsAccountIdProcedimentiRGet(idUtenteAccentureByCf);

      log.debug("oggetto_domande_prima " + domande.getRecords());

      if (domande != null && domande.getRecords() != null && !domande.getRecords().isEmpty()) {

        List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner> listRecords =
            domande.getRecords().stream()
                .filter(elem -> elem != null && elem.getTipologiaPraticaC() != null)
                .collect(Collectors.toList());
        domande.setRecords(listRecords);

        log.debug("oggetto_domande " + domande);

        return domande;
      } else {
        ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response di =
            new ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response();
        List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner> l =
            new ArrayList<
                ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>();
        di.setRecords(l);

        log.debug("dento_else" + di);

        return new ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response();
      }

    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioneDanniBeniPrivatiImpl -- getListaDomandeSegnalazioneDanniBeniPrivati: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONE_DANNI_BENI_PRIVATI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioneDanniBeniPrivatiImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta segnalazione danni beni privati"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {
      response =
          instance
              .getApiSegnalazioneDaniBeniPrivati()
              .servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(codiceFiscale);

      return response;

    } catch (BusinessException e) {
      log.error(
          "ServiziSegnalazioneDanniBeniPrivatiImpl -- getListaDomandeSegnalazioneDanniBeniPrivati: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONE_DANNI_BENI_PRIVATI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziSegnalazioneDanniBeniPrivatiImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta segnalazione danni beni privati"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {

      log.debug("id_segnalazione " + idAccenture);

      response =
          instance
              .getApiSegnalazioneDaniBeniPrivati()
              .servicesDataV580SobjectsAccountIdGet(idAccenture);

      return response;

    } catch (BusinessException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_SEGNALAZIONE_DANNI_BENI_PRIVATI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazione danni beni privati"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
