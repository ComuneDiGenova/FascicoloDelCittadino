package it.liguriadigitale.ponmetro.portale.business.edilizia.condono.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.condono.model.AllegatoBody;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponse;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponseCompleta;
import it.liguriadigitale.ponmetro.portale.business.edilizia.condono.service.ServiziEdiliziaCondono;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziEdiliziaCondonoImpl implements ServiziEdiliziaCondono {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_EDILIZIA_CONDONO =
      "Errore di connessione alle API EDILIZIA CONDONO";

  ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbCondoni() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("condoniEdilizia", "I miei condoni edilizi"));

    return listaBreadcrumb;
  }

  @Override
  public List<CondonoResponse> getCondoni(String codiceFiscale)
      throws BusinessException, ApiException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiEdiliziaCondono()
          .condoniSoggettoGet(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziSeggiElettoraliImpl -- getCondoni: errore API sevizi edilizia condono:", e);
      throw new BusinessException(ERRORE_API_EDILIZIA_CONDONO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getCondoni: errore API sevizi edilizia condono:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getCondoni: errore API sevizi edilizia condono:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("condono"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public CondonoResponseCompleta getDettaglioCondono(int numeroPratica, int annoPratica)
      throws BusinessException, ApiException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiEdiliziaCondono()
          .condonoNumeroPraticaAnnoPraticaGet(numeroPratica, annoPratica);

    } catch (BusinessException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getDettaglioCondono: errore API sevizi edilizia condono:",
          e);
      throw new BusinessException(ERRORE_API_EDILIZIA_CONDONO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getDettaglioCondono: errore API sevizi edilizia condono:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getDettaglioCondono: errore API sevizi edilizia condono:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("condono"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AllegatoBody getFilePDF(
      int numeroPratica,
      int annoPratica,
      int numeroProvvedimento,
      int annoProvvedimento,
      String nomeFile)
      throws BusinessException, ApiException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiEdiliziaCondono()
          .condonoNumeroPraticaAnnoPraticaNumeroProvvedimentoAnnoProvvedimentoAllegatoNomeFileGet(
              numeroPratica, annoPratica, numeroProvvedimento, annoProvvedimento, nomeFile);

    } catch (BusinessException e) {
      log.error("ServiziEdiliziaCondonoImpl -- getFilePDF: errore API sevizi edilizia condono", e);
      throw new BusinessException(ERRORE_API_EDILIZIA_CONDONO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getFilePDF: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaCondonoImpl -- getFilePDF: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("condono"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
