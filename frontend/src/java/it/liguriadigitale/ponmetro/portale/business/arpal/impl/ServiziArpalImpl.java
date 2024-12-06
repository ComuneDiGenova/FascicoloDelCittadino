package it.liguriadigitale.ponmetro.portale.business.arpal.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.arpal.model.AnalisiVerifiche;
import it.liguriadigitale.ponmetro.arpal.model.Impianti;
import it.liguriadigitale.ponmetro.arpal.model.ItemA;
import it.liguriadigitale.ponmetro.arpal.model.ItemS;
import it.liguriadigitale.ponmetro.portale.business.arpal.service.ServiziArpal;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziArpalImpl implements ServiziArpal {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_ARPAL = "Errore di connessione alle API ARPAL";

  // private static final String directorySimpa = "SIMPA_Allegati";

  // private static final String directoryAlims = "ALIMS_Allegati";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("praticheArpal", "Pratiche ARPAL"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio("Messaggio ...");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<ItemA> elencoProveLaboratorioAlims(Utente utente)
      throws ApiException, BusinessException {

    log.debug("inizio prova laboratorio impl");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      AnalisiVerifiche verifiche =
          instance.getApiArpalAlims().getUtenteA(utente.getCodiceFiscaleOperatore());
      List<ItemA> lista = verifiche.getItems();
      log.debug("Prova Chiamata: " + lista);
      return lista;
    } catch (WebApplicationException e) {
      log.error(
          "ServiziArpalImpl -- elencoProveLaboratorio: errore nel recupero prove di laboratorio:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziArpalImpl -- elencoProveLaboratorio: RunTimeException " + e.getMessage(), e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARPAL"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<ItemS> elencoProveLaboratorioSimpa(Utente utente)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Impianti impianti =
          instance.getApiArpalSimpa().getUtenteS(utente.getCodiceFiscaleOperatore());
      List<ItemS> lista = impianti.getItems();

      return lista;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziArpalImpl -- elencoProveLaboratorioSimpa: errore nel recupero prove di laboratorio:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziArpalImpl -- elencoProveLaboratorioSimpa: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARPAL"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public File getAllegatoPdfSimpa(String nomeFile)
      throws BusinessException, ApiException, IOException {

    log.debug("Allegato getAllegatoPdf Simpa: " + nomeFile);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      return instance.getApiArpalSimpa().getDocumentoS(nomeFile);

    } catch (BusinessException e) {

      log.error("ServiziArpalImpl -- getAllegatoPdf: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARPAL);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziArpalImpl -- getAllegatoPdf: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziArteImpl -- getAllegatoPdf: errore durante la chiamata delle API ARPAL ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARPAL"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public File getAllegatoPdfAlims(String nomeFile, String nomeDirectory)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("avant-try-getAllegatoPdfAlims");
      return instance.getApiArpalAlims().getDocumentoA(nomeFile, nomeDirectory);
    } catch (BusinessException e) {
      log.debug("BusinessException-getAllegatoPdfAlims");
      log.error("ServiziArpalImpl -- getAllegatoPdf: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARPAL);

    } catch (WebApplicationException e) {
      log.debug("WebApplicationException-getAllegatoPdfAlims");
      log.error(
          "ServiziArpalImpl -- getAllegatoPdf: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {
      log.debug("RuntimeException-getAllegatoPdfAlims");
      log.error(
          "ServiziArteImpl -- getAllegatoPdf: errore durante la chiamata delle API ARPAL ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARPAL"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiArpal() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Per leggere e comprendere l’informativa cliccare sul seguente <a href=\"https://www.arpal.liguria.it/privacy.html/\" target=\"_blank\">link</a> che riporterà al sito del titolare");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }
}
