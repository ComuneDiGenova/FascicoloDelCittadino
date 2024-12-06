package it.liguriadigitale.ponmetro.portale.business.edilizia.pratiche.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratica;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratiche;
import it.liguriadigitale.ponmetro.portale.business.edilizia.pratiche.service.ServiziEdiliziaPratiche;
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

public class ServiziEdiliziaPraticheImpl implements ServiziEdiliziaPratiche {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_EDILIZIA_PRATICHE =
      "Errore di connessione alle API EDILIZIA PRATICHE";

  ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPratiche() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("praticheEdilizia", "Le mie pratiche edilizie"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiPratiche() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Il servizio permette la consultazione delle principali informazioni contenute nelle pratiche edilizie da te richieste dal 1995 ad oggi.");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public Pratiche getPratiche(String codiceFiscale) throws BusinessException, ApiException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiEdiliziaPratiche()
          .getPratiche(codiceFiscale);

    } catch (BusinessException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getPratiche: errore API sevizi edilizia pratiche:", e);
      throw new BusinessException(ERRORE_API_EDILIZIA_PRATICHE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getPratiche: errore API sevizi edilizia pratiche:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getPratiche: errore API sevizi edilizia pratiche:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("edilizia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Pratica getDettaglioPratica(String codicePratica) throws BusinessException, ApiException {

    try {

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiEdiliziaPratiche()
          .getDettagliPratica(codicePratica);

    } catch (BusinessException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getDettaglioPratica: errore API sevizi edilizia pratiche:",
          e);
      throw new BusinessException(ERRORE_API_EDILIZIA_PRATICHE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getDettaglioPratica: errore API sevizi edilizia pratiche:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaPraticheImpl -- getDettaglioPratica: errore API sevizi edilizia pratiche:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("edilizia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
