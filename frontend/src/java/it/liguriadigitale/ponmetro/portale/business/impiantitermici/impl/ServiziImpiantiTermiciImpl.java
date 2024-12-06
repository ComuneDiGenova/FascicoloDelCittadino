package it.liguriadigitale.ponmetro.portale.business.impiantitermici.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.ImpiantiTermici;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.impiantitermici.service.ServiziImpiantiTermici;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziImpiantiTermiciImpl implements ServiziImpiantiTermici {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_CAITEL = "Errore di connessione alle API Caitel";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("catastoImpiantiTermici", "Catasto Impianti Termici"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbDettagli() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("catastoImpiantiTermici", "Catasto Impianti Termici"));
    listaBreadcrumb.add(new BreadcrumbFdC("dettagliCatastoImpiantiTermici", "Dettagli"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();

    String messaggio =
        "Catasto impianti termici:<br><ul><li>In qualità di responsabile di impianto, puoi visualizzare i dati relativi ai rapporti di efficienza e alle manutenzioni ordinarie relative a tutti gli impianti termici di tua competenza.</li><li>Privacy:<a href=\"https://www.regione.liguria.it/privacy.html\" target=\"_blank\">sito di Regione Liguria</a></li></ul>";

    messaggio1.setMessaggio(messaggio);
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiDettagli() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();

    // String messaggio = "Catasto impianti
    // termici:<br><ul><li>Rapporti:</li><li>Macchine:</li><li>Manutenzioni:</li><li>Privacy:<a
    // href=\"https://www.regione.liguria.it/privacy.html\" target=\"_blank\">sito
    // di Regione Liguria</a></li></ul>";
    String messaggio =
        "Catasto impianti termici:<br><ul><li>Privacy:<a href=\"https://www.regione.liguria.it/privacy.html\" target=\"_blank\">sito di Regione Liguria</a></li></ul>";

    messaggio1.setMessaggio(messaggio);
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public ImpiantiTermici getImpianti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getImpianti: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiCaitel().getResponsabile(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziImpiantiTermiciImpl -- getImpianti: errore API CAITEL:", e);
      throw new BusinessException(ERRORE_API_CAITEL);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziImpiantiTermiciImpl -- getImpianti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziImpiantiTermiciImpl -- getImpianti: errore durante la chiamata delle API Caitel ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Catasto Impianti"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Documento getDocumento(
      String idImpianto, String idGruppo, String idRapporto, String tipoDocumento)
      throws BusinessException, ApiException, IOException {
    log.debug(
        "CP getDocumento: "
            + idImpianto
            + " - "
            + idGruppo
            + " - "
            + idRapporto
            + " - "
            + tipoDocumento);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiCaitel().getDocumento(idImpianto, idGruppo, idRapporto, tipoDocumento);
    } catch (BusinessException e) {
      log.error("ServiziImpiantiTermiciImpl -- getDocumento: errore API CAITEL:", e);
      throw new BusinessException(ERRORE_API_CAITEL);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziImpiantiTermiciImpl -- getDocumento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
    // catch (RuntimeException e) {
    //			log.error("ServiziImpiantiTermiciImpl -- getDocumento: errore durante la chiamata delle API
    // Caitel ", e);
    //			//throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Catasto
    // Impianti"));
    //		}

    finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
