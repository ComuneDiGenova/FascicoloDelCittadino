package it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.AggiornamentoRecapitiResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertecortesia.model.InserimentoResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.StradeCivici;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServizi;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service.ServiziAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziAllerteCortesiaImpl implements ServiziAllerteCortesia {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_ALLERTE =
      "Errore di connessione alle API Allerte Cortesia";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAllerteCortesia() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbVerificaEmail() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("verificaEmail", "Verifica email"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbVerificaCellulare() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("verificaCellulare", "Verifica cellulare"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbCambiaCellulare() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("cambiaCellulare", "Cambia cellulare"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRecuperaRegistrazione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("recuperaRegistrazione", "Recupera registrazione"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPrimaRegistrazione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("primaRegistrazione", "Prima registrazione"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbModificaDati() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("modificaDatiRegistrazioneAllerteCortesia", "Modifica dati"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbModificaServizio() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("modificaServizioAllerteCortesia", "Modifica servizio"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPrivacy() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("privacyServiziCortesia", "Privacy allerte meteo e servizi di cortesia"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAggiungiPreferenza() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("cortesia", "Registrazione allerte meteo e servizi di cortesia"));
    listaBreadcrumb.add(new BreadcrumbFdC("modificaServizioAllerteCortesia", "Modifica servizio"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("aggiungiPreferenzaServizioCortesia", "Aggiungi preferenza"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiCortesia() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    /*
     * MessaggiInformativi messaggio1 = new MessaggiInformativi(); messaggio1.
     * setMessaggio("Il canale SMS è disponibile solo per i telefoni cellulari.");
     * messaggio1.setType("info"); listaMessaggi.add(messaggio1);
     *
     * MessaggiInformativi messaggio2 = new MessaggiInformativi(); messaggio2.
     * setMessaggio("Alle allerte meteo ci si può registrare solo se è stato abilitato il canale SMS."
     * ); messaggio2.setType("info"); listaMessaggi.add(messaggio2);
     */

    //		MessaggiInformativi messaggio3 = new MessaggiInformativi();
    //		messaggio3.setMessaggio(
    //				"Scarica la App di “TELEGRAM” e iscriviti al Canale <a
    // href=\"https://t.me/ComGeGenovaAlert/\" target=\"_blank\">@GenovaAlert</a>");
    //		messaggio3.setType("info");
    //		listaMessaggi.add(messaggio3);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiVerificaEmail() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Usando il bottone AVANTI ti arriverà una E-MAIL con un codice che devi inserire in questa pagina per completare l'operazione di verifica.\r\n"
            + "Se non ricevi l'e-mail usa il pulsante ANNULLA e poi passa al cambio e-mail per impostarne una nuova.");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiVerificaCellulare() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio("Qui inserisci il codice che è arrivato al cellulare");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiCambiaCellulare() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Qui puoi modificare il numero di cellulare al quale riceverai le notifiche via SMS dei servizi ai quali ti vorrai registrare");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiAggiungiPreferenza() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio("Qui puoi inserire una via di tuo interesse");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiAggiungiStrada() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Metti solo il nome della via, ad esempio per corso europa metti europa ed utilizza il bottone AVANTI per visualizzare la lista delle strade. Scegli, poi, la strada desiderata utilizzando il bottone AGGIUNGI.");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public DettagliUtente getWsLoginByCf(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getLoginByCf: " + codiceFiscale);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsLoginCF(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- getLoginByCf: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getLoginByCf: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getLoginByCf: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getLoginByCf: errore durante la chiamata delle API Allerte Cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public DettagliUtente getWsLoginEMAIL(String email, String pwd, String cf)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsLoginEMAIL: " + email);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsLoginEMAIL(email, pwd, cf);

    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- getWsLoginEMAIL: errore API Allerte Cortesia:", e);
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsLoginEMAIL: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsLoginEMAIL: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsLoginEMAIL: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public VerificaServiziResponse getWsGetListaServizi(String email)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsGetListaServizi: " + email);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsGetListaServizi(email);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetListaServizi: errore API Allerte Cortesia:", e);
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetListaServizi: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetListaServizi: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetListaServizi: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public VerificaServiziResponse getWsGetServizioById(String email, String idServizio)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsGetServizioById: " + email + " - " + idServizio);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsGetServizioById(email, idServizio);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetServizioById: errore API Allerte Cortesia:", e);
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetServizioById: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetServizioById: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetServizioById: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public InserimentoResponse putWsPutUtente(
      DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsPutUtente = " + datiCompletiRegistrazioneUtenteAllerteCortesia);

    try {

      String cognome = null;
      String nome = null;
      String email = null;
      String nazione = null;
      String provincia = null;
      String comune = null;
      String cap = null;
      String indirizzo = null;
      String telefonoFisso = null;
      String telefonoCellulare = null;
      String pwd = null;
      String cf = null;

      if (LabelFdCUtil.checkIfNotNull(datiCompletiRegistrazioneUtenteAllerteCortesia)
          && LabelFdCUtil.checkIfNotNull(
              datiCompletiRegistrazioneUtenteAllerteCortesia
                  .getDatiRegistrazioneAllerteCortesia())) {

        cognome =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getCognome();
        nome =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getNome();
        email =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getEmail();
        nazione =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getNazione();
        provincia =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getProvincia();
        comune =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getComune();
        cap =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getCap();
        indirizzo =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getIndirizzo();
        telefonoFisso =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getTelefonoFisso();
        telefonoCellulare =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getTelefonoCellulare();
        pwd = "SPID#SPID2020";
        cf =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getCodiceFiscale();
      }

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsPutUtente(
              cognome,
              email,
              pwd,
              cf,
              nome,
              nazione,
              provincia,
              comune,
              cap,
              indirizzo,
              telefonoFisso,
              telefonoCellulare);
    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- putWsPutUtente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutUtente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutUtente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutUtente: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public AggiornamentoRecapitiResponse putwsUpdateUtente(
      DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsPutUtente = " + datiCompletiRegistrazioneUtenteAllerteCortesia);

    try {

      String email = null;
      String nazione = null;
      String provincia = null;
      String comune = null;
      String cap = null;
      String indirizzo = null;
      String telefonoFisso = null;

      if (LabelFdCUtil.checkIfNotNull(datiCompletiRegistrazioneUtenteAllerteCortesia)
          && LabelFdCUtil.checkIfNotNull(
              datiCompletiRegistrazioneUtenteAllerteCortesia
                  .getDatiRegistrazioneAllerteCortesia())) {

        email =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getEmail();
        nazione =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getNazione();
        provincia =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getProvincia();
        comune =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getComune();
        cap =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getCap();
        indirizzo =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getIndirizzo();
        telefonoFisso =
            datiCompletiRegistrazioneUtenteAllerteCortesia
                .getDatiRegistrazioneAllerteCortesia()
                .getTelefonoFisso();
      }

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsUpdateUtente(email, nazione, provincia, comune, cap, indirizzo, telefonoFisso);
    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- putwsUpdateUtente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putwsUpdateUtente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putwsUpdateUtente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putwsUpdateUtente: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public VerificaServizi putWsIscrizioneAdUnServizio(String email, String idRelazioneServizioCanale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsIscrizioneAdUnServizio: " + email);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsIscrizioneAdUnServizio(email, idRelazioneServizioCanale);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsIscrizioneAdUnServizio: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsIscrizioneAdUnServizio: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsIscrizioneAdUnServizio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsIscrizioneAdUnServizio: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public VerificaServizi putWsCancellazioneAdUnServizio(
      String email, String idRelazioneServizioCanale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsCancellazioneAdUnServizio: " + email);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsCancellazioneAdUnServizio(email, idRelazioneServizioCanale);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazioneAdUnServizio: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazioneAdUnServizio: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazioneAdUnServizio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazioneAdUnServizio: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public StradeCivici getWsGetStradeCivici(
      String email, String idServizio, String strada, String paginazioneStart)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsGetStradeCivici: " + email);

    try {
      String paginazioneLegth = "10";

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsGetStradeCivici(email, idServizio, strada, paginazioneStart, paginazioneLegth);

    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- getWsGetStradeCivici: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetStradeCivici: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetStradeCivici: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetStradeCivici: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public InserimentoResponse putWsAggiuntaPreferenzaServizio(
      String email,
      String idServizio,
      String codiceStrada,
      String codiceDivisione,
      String codiceCircoscrizione)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsAggiuntaPreferenzaServizio: " + email);

    try {
      String codiceUnitaUrbanistica = "XXX";

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsAggiuntaPreferenzaServizio(
              email,
              idServizio,
              codiceStrada,
              codiceDivisione,
              codiceCircoscrizione,
              codiceUnitaUrbanistica);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsAggiuntaPreferenzaServizio: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsAggiuntaPreferenzaServizio: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsAggiuntaPreferenzaServizio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsAggiuntaPreferenzaServizio: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public InserimentoResponse putWsCancellazionePreferenzaServizio(
      String email,
      String idServizio,
      String codiceStrada,
      String codiceDivisione,
      String codiceCircoscrizione,
      String codiceUnitaUrbanistica)
      throws BusinessException, ApiException, IOException {
    log.debug(
        "CP putWsCancellazionePreferenzaServizio: "
            + email
            + " - "
            + idServizio
            + " - "
            + codiceStrada
            + " - "
            + codiceDivisione
            + " - "
            + codiceCircoscrizione
            + " - "
            + codiceUnitaUrbanistica);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsCancellazionePreferenzaServizio(
              email,
              idServizio,
              codiceStrada,
              codiceDivisione,
              codiceCircoscrizione,
              codiceUnitaUrbanistica);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazionePreferenzaServizio: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazionePreferenzaServizio: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazionePreferenzaServizio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsCancellazionePreferenzaServizio: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public AggiornamentoRecapitiResponse putWsUpdateUtenteTelefonoCellulare(
      String email, String telefonoNew) throws BusinessException, ApiException, IOException {
    log.debug("CP putWsUpdateUtenteTelefonoCellulare: " + email + " - " + telefonoNew);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsUpdateUtenteTelefonoCellulare(email, telefonoNew);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsUpdateUtenteTelefonoCellulare: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsUpdateUtenteTelefonoCellulare: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsUpdateUtenteTelefonoCellulare: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsUpdateUtenteTelefonoCellulare: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public AggiornamentoRecapitiResponse putWsPutCheckTelefonoSMS(
      DatiVerificaCellulareAllerteCortesia datiVerifica)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsPutCheckTelefonoSMS: ");

    try {

      String email = datiVerifica.getEmail();
      String reinvia = datiVerifica.getReinvia();

      log.debug("CP email = " + email + " - reinvia = " + reinvia);

      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsPutCheckTelefonoSMS(email, reinvia);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public AggiornamentoRecapitiResponse getWsGetCheckTelefonoSMS(
      String email, String codiceVerificaTelefono)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putWsPutCheckTelefonoSMS: " + email);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsGetCheckTelefonoSMS(email, codiceVerificaTelefono);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- putWsPutCheckTelefonoSMS: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public AggiornamentoRecapitiResponse putWsUpdateUtenteMail(String emailOld, String emailNew)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsUpdateUtenteMail: " + emailOld);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .putWsUpdateUtenteMail(emailOld, emailNew);
    } catch (BusinessException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsUpdateUtenteMail: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsUpdateUtenteMail: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsUpdateUtenteMail: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsUpdateUtenteMail: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati", 1));
    listaStep.add(new StepFdC("Riepilogo dati", 2));
    listaStep.add(new StepFdC("Esito", 3));

    return listaStep;
  }

  @Override
  public List<StepFdC> getListaStepPreferenze() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Strada", 1));
    listaStep.add(new StepFdC("Lista risultati", 2));

    return listaStep;
  }

  @Override
  public String pulisciStringaStrada(String strada) {
    log.debug("CP pulisciStringaStrada = " + strada);
    String stradaPulita = StringUtils.stripAccents(strada);

    log.debug("CP strada pulita  = " + stradaPulita);
    return stradaPulita;
  }

  @Override
  public AggiornamentoRecapitiResponse getWsGetCheckEmail(String codice)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getWsGetCheckEmail: " + codice);

    try {
      return ServiceLocatorLivelloUnoCortesia.getInstance()
          .getApiAllerteCortesia()
          .getWsGetCheckEmail(codice);
    } catch (BusinessException e) {
      log.error("ServiziAllerteCortesiaImpl -- getWsGetCheckEmail: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetCheckEmail: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetCheckEmail: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteCortesiaImpl -- getWsGetCheckEmail: errore durante la chiamata delle API allerte cortesia ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Cortesia"));
    }
  }

  @Override
  public List<StepFdC> getListaStepPrimaRegistrazione() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati", 1));
    listaStep.add(new StepFdC("Riepilogo dati", 2));

    return listaStep;
  }
}
