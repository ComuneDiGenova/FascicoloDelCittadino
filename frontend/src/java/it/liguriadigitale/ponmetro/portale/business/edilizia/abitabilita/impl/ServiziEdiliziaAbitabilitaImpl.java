package it.liguriadigitale.ponmetro.portale.business.edilizia.abitabilita.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaRequest;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaResponse;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AllegatoBody;
import it.liguriadigitale.ponmetro.portale.business.edilizia.abitabilita.service.ServiziEdiliziaAbitabilita;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.edilizia.abitabilita.Abitabilita;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziEdiliziaAbitabilitaImpl implements ServiziEdiliziaAbitabilita {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_EDILIZIA_ABITABILITA =
      "Errore di connessione alle API EDILIZIA ABITABILITA";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAbitabilita() {

    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();
    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("abitabilita", "Certificati di abitabilità"));
    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiAbitabilita() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Il servizio di ricerca dei decreti/certificati di abitabilità/agibilità rende possibile l’accesso allo schedario storico contenente i dati relativi all’indirizzo toponomastico dell’edificio relativo al tuo indirizzo residenza o a un indirizzo da te digitato.\r\n"
            + "Si precisa che essendo dati registrati alla data di conseguimento del decreto/certificato, le informazioni potrebbero in qualche caso essere incomplete e comunque da verificare richiedendo alla  <a href=\"https://smart.comune.genova.it/node/6023\" target=\"_blank\">Segreteria Organi Istituzionali</a> – Ufficio Rilascio Atti (via Garibaldi 9 – Palazzo Albini – I° piano) la copia del decreto/certificato medesimo");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);
    return listaMessaggi;
  }

  @Override
  public List<AbitabilitaResponse> getDecretiAbitabilita(Abitabilita abitabilita)
      throws BusinessException, IOException, ApiException {
    log.debug("CP getDecretiAbitabilita: " + abitabilita);
    ServiceLocatorLivelloUnoCortesia instance = ServiceLocatorLivelloUnoCortesia.getInstance();
    try {
      AbitabilitaRequest abitabilitaBody = new AbitabilitaRequest();
      abitabilitaBody.setAnno(abitabilita.getAnnoDecreto());
      abitabilitaBody.setCivicoA(abitabilita.getCivicoA());
      abitabilitaBody.setCivicoDa(abitabilita.getCivicoDa());
      abitabilitaBody.setColore(abitabilita.getColore());
      abitabilitaBody.setLetteraInterno(abitabilita.getLetteraInterno());
      abitabilitaBody.setNomeStrada(abitabilita.getVia());
      abitabilitaBody.setNumero(abitabilita.getNumeroCartellinoDecreto());
      return instance.getApiEdiliziaAbitabilita().decretiPost(abitabilitaBody);

    } catch (BusinessException e) {
      log.error("ServiziEdiliziaImpl -- getDecretiAbitabilita: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_EDILIZIA_ABITABILITA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziEdiliziaImpl -- getDecretiAbitabilita: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziEdiliziaImpl -- getDecretiAbitabilita: errore durante la chiamata delle API Edilizia ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Certificati di Abitabilità"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AllegatoBody getdecretoIdUdAllegatoNomeFile(Integer idUd, String nomeFile)
      throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUnoCortesia instance = ServiceLocatorLivelloUnoCortesia.getInstance();
    return closeConnectionInGetdecretoIdUdAllegatoNomeFile(instance, idUd, nomeFile);
  }

  private AllegatoBody closeConnectionInGetdecretoIdUdAllegatoNomeFile(
      ServiceLocatorLivelloUnoCortesia instance, Integer idUd, String nomeFile) {
    try {
      return instance.getApiEdiliziaAbitabilita().decretoIdUdAllegatoNomeFileGet(idUd, nomeFile);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ServiziEdiliziaAbitabilita"));
    } finally {
      instance.closeConnection();
    }
  }
}
