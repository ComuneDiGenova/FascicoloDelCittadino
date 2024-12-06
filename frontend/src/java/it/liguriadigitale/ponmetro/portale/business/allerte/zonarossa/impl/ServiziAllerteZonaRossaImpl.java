package it.liguriadigitale.ponmetro.portale.business.allerte.zonarossa.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Componente;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import it.liguriadigitale.ponmetro.allertezonarossa.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Problem;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.allerte.zonarossa.service.ServiziAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CivicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DettagliUtenteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziAllerteZonaRossaImpl implements ServiziAllerteZonaRossa {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_ALLERTE =
      "Errore di connessione alle API Allerte Zona Rossa";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAllerte() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAllerteZonaRossa() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAllerteZonaRossaRegistrazione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    listaBreadcrumb.add(new BreadcrumbFdC("registrazioneZonaRossa", "Registrazione"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbCancellaComponente() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    listaBreadcrumb.add(new BreadcrumbFdC("cancellaComponente", "Cancella componente"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbDettagliCivico() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    listaBreadcrumb.add(new BreadcrumbFdC("dettagliCivico", "Dettagli"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAggiungiCivico() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    listaBreadcrumb.add(new BreadcrumbFdC("aggiungiCivico", "Aggiungi civico"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAggiungiComponente() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    // listaBreadcrumb.add(new BreadcrumbFdC("dettagliCivico", "Dettagli civico"));
    listaBreadcrumb.add(new BreadcrumbFdC("aggiungiComponente", "Aggiungi componente"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAggiungiContatto() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "serviziAllerte", "Registrazione allerte protezione civile e servizi di cortesia"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "zonaRossa",
            "Registrazione allerte meteo per abitanti in zone ad alta pericolosità idraulica"));
    listaBreadcrumb.add(new BreadcrumbFdC("aggiungiTelefono", "Aggiungi telefono"));

    return listaBreadcrumb;
  }

  @Override
  public DettagliUtente getDettagliUtente(String codiceFiscale)
      throws BusinessException, ApiException, IOException {

    log.debug("CP getDettagliUtente: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAllerteZonaRossa().getUtente(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- getDettagliUtente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- getDettagliUtente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- getDettagliUtente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- getDettagliUtente: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiZonaRossa() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    /*
     * MessaggiInformativi messaggio1 = new MessaggiInformativi();
     * messaggio1.setMessaggio(
     * "Al servizio si possono registrare i residenti ed i domiciliati per i soli indirizzi e numeri civici che risultano sul territorio delle aree ad ALTA pericolosità per RISCHIO IDRAULICO che abitano al piano strada o sottostrada del Comune di Genova (piano di protezione civile)."
     * ); messaggio1.setType("info"); listaMessaggi.add(messaggio1);
     *
     * MessaggiInformativi messaggio2 = new MessaggiInformativi();
     * messaggio2.setMessaggio(
     * "Si ricorda che il NUMERO VERDE della Protezione Civile del Comune di Genova è 800177797"
     * ); messaggio2.setType("info"); listaMessaggi.add(messaggio2);
     *
     * MessaggiInformativi messaggio3 = new MessaggiInformativi();
     * messaggio3.setMessaggio(
     * "Scarica la App di “TELEGRAM” e iscriviti al Canale <a href=\"https://t.me/ComGeGenovaAlert/\" target=\"_blank\">@GenovaAlert</a>"
     * ); messaggio3.setType("info"); listaMessaggi.add(messaggio3);
     */

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiCancellaComponente() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio("Qui puoi eliminare il componente");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati indirizzo", 1));
    listaStep.add(new StepFdC("Dati generali", 2));
    listaStep.add(new StepFdC("Pericolosità", 3));
    listaStep.add(new StepFdC("Riepilogo", 4));
    listaStep.add(new StepFdC("Esito", 5));

    return listaStep;
  }

  @Override
  public List<StepFdC> getListaStepAggiungiCivico() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati indirizzo", 1));
    listaStep.add(new StepFdC("Pericolosità", 2));
    listaStep.add(new StepFdC("Riepilogo", 3));
    listaStep.add(new StepFdC("Esito", 4));

    return listaStep;
  }

  @Override
  public List<StepFdC> getListaStepAggiungiTelefono() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati telefono", 1));
    listaStep.add(new StepFdC("Riepilogo", 2));
    listaStep.add(new StepFdC("Esito", 3));

    return listaStep;
  }

  @Override
  public List<StepFdC> getListaStepAggiungiComponente() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati generali", 1));
    listaStep.add(new StepFdC("Riepilogo", 2));
    listaStep.add(new StepFdC("Esito", 3));

    return listaStep;
  }

  @Override
  public DettagliUtenteZonaRossa getDettagliZonaRossa(DettagliUtente dettagliUtente) {
    DettagliUtenteZonaRossa dettagliUtenteZonaRossa = new DettagliUtenteZonaRossa();

    if (LabelFdCUtil.checkIfNotNull(dettagliUtente)) {

      // TODO da finire
      // dettagliUtenteZonaRossa.setListaComponentiNucleo(dettagliUtente.get);
    }

    return dettagliUtenteZonaRossa;
  }

  @Override
  public Utente registraUtente(
      DatiCompletiRegistrazioneUtenteAllerteZonaRossa
          dettagliCompletiRegistrazioneUtenteAllerteZonaRossa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP registraUtente");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Utente utenteZonaRossa = new Utente();

      if (LabelFdCUtil.checkIfNotNull(dettagliCompletiRegistrazioneUtenteAllerteZonaRossa)) {

        if (LabelFdCUtil.checkIfNotNull(
            dettagliCompletiRegistrazioneUtenteAllerteZonaRossa.getDettagliUtenteZonaRossa())) {
          dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
              .getDettagliUtenteZonaRossa()
              .setDataRegistrazione(LocalDate.now());

          utenteZonaRossa.setCodiceFiscale(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .getCodiceFiscale()
                  .toUpperCase());
          utenteZonaRossa.setCognome(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .getCognome()
                  .toUpperCase());
          utenteZonaRossa.setNome(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .getNome()
                  .toUpperCase());
          utenteZonaRossa.setDataRegistrazione(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .getDataRegistrazione());
          utenteZonaRossa.seteMail(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .geteMail());
          utenteZonaRossa.setVulnerabilitaPersonale(
              dettagliCompletiRegistrazioneUtenteAllerteZonaRossa
                  .getDettagliUtenteZonaRossa()
                  .getVulnerabilitaPersonale());
        }
      }

      log.debug("CP prima di POST = " + utenteZonaRossa);

      return instance.getApiAllerteZonaRossa().setUtente(utenteZonaRossa);

    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- registraUtente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- registraUtente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- registraUtente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- registraUtente: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Indirizzo aggiungiCivico(CivicoZonaRossa civicoZonaRossa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP aggiungiCivico");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Indirizzo indirizzo = new Indirizzo();

      if (LabelFdCUtil.checkIfNotNull(civicoZonaRossa)) {

        if (LabelFdCUtil.checkIfNotNull(civicoZonaRossa.getAutocompleteViario())) {
          civicoZonaRossa.setIndirizzoCompleto(
              civicoZonaRossa.getAutocompleteViario().getMACHINE_LAST_UPD());
          civicoZonaRossa.setColore(civicoZonaRossa.getAutocompleteViario().getCOLORE());
          civicoZonaRossa.setIdVia(civicoZonaRossa.getAutocompleteViario().getCOD_STRADA());
          civicoZonaRossa.setNumeroCivico(civicoZonaRossa.getAutocompleteViario().getNUMERO());
          civicoZonaRossa.setEsponente(civicoZonaRossa.getAutocompleteViario().getLETTERA());
        }

        indirizzo.setIndirizzoCompleto(civicoZonaRossa.getIndirizzoCompleto());
        indirizzo.setIdVia(civicoZonaRossa.getIdVia());
        indirizzo.setNumeroCivico(civicoZonaRossa.getNumeroCivico());
        indirizzo.setEsponente(civicoZonaRossa.getEsponente());
        indirizzo.setColore(civicoZonaRossa.getColore());
        indirizzo.setPosizione(civicoZonaRossa.getPosizione());
        indirizzo.setVulnerabilita(civicoZonaRossa.getVulnerabilita());
        indirizzo.setAmministratore(civicoZonaRossa.getAmministratore());
        indirizzo.setProprietario(civicoZonaRossa.getProprietario());
      }

      log.debug("CP prima di POST = " + indirizzo);

      return instance.getApiAllerteZonaRossa().setCivico(indirizzo);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- aggiungiCivico: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiCivico: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiCivico: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiCivico: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Contatto aggiungiTelefono(ContattoTelefonicoZonaRossa contattoTelefonicoZonaRossa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP aggiungiTelefono");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Contatto contatto = new Contatto();

      if (LabelFdCUtil.checkIfNotNull(contattoTelefonicoZonaRossa)
          && LabelFdCUtil.checkIfNotNull(contattoTelefonicoZonaRossa.getIdUtente())) {
        contatto.setIdUtente(new BigDecimal(contattoTelefonicoZonaRossa.getIdUtente()));
      }

      contatto.setLingua(contattoTelefonicoZonaRossa.getLingua());
      if (LabelFdCUtil.checkIfNotNull(contattoTelefonicoZonaRossa)
          && LabelFdCUtil.checkIfNotNull(contattoTelefonicoZonaRossa.getLinguaNoItalia())) {
        contatto.setLinguaNoItalia(contattoTelefonicoZonaRossa.getLinguaNoItalia().getIdLingua());
      }
      contatto.setNumero(contattoTelefonicoZonaRossa.getNumero());
      contatto.setTipo(contattoTelefonicoZonaRossa.getTipo());

      log.debug("CP prima di POST = " + contatto);
      return instance.getApiAllerteZonaRossa().setTelefono(contatto);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- aggiungiTelefono: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiTelefono: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiTelefono: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiTelefono: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Problem deleteComponente(Integer idUtente, Integer idCivico, String motivo)
      throws BusinessException, ApiException, IOException {
    log.debug("CP deleteComponente");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      return instance.getApiAllerteZonaRossa().deleteComponente(idUtente, idCivico, motivo);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- deleteComponente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteComponente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteComponente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteComponente: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Problem deleteTelefono(Integer idUtente)
      throws BusinessException, ApiException, IOException {
    log.debug("CP deleteTelefono");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAllerteZonaRossa().deleteTelefono(idUtente);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- deleteTelefono: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteTelefono: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteTelefono: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- deleteTelefono: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Problem aggiungiComponente(ComponenteNucleoZonaRossa componenteZonaRossa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP setComponente");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Componente componente = new Componente();

      DatiCompletiRegistrazioneUtenteAllerteZonaRossa datiRegistrazione =
          new DatiCompletiRegistrazioneUtenteAllerteZonaRossa();

      DettagliUtenteZonaRossa dettagliUtente = new DettagliUtenteZonaRossa();
      dettagliUtente.setCodiceFiscale(componenteZonaRossa.getCodiceFiscale());
      dettagliUtente.setNome(componenteZonaRossa.getNome());
      dettagliUtente.setCognome(componenteZonaRossa.getCognome());
      dettagliUtente.setDataRegistrazione(LocalDate.now());
      dettagliUtente.seteMail(componenteZonaRossa.geteMail());
      dettagliUtente.setVulnerabilitaPersonale(componenteZonaRossa.getVulnerabilitaPersonale());

      datiRegistrazione.setDettagliUtenteZonaRossa(dettagliUtente);
      Utente utenteZonaRossa = registraUtente(datiRegistrazione);

      if (LabelFdCUtil.checkIfNotNull(utenteZonaRossa)) {
        if (LabelFdCUtil.checkIfNotNull(componenteZonaRossa)
            && LabelFdCUtil.checkIfNotNull(componenteZonaRossa.getIdCivico())) {
          componente.setIdCivico(new BigDecimal(componenteZonaRossa.getIdCivico()));
        }

        componente.setIdUtente(utenteZonaRossa.getId());
        componente.setTipo(componenteZonaRossa.getTipo());
      }

      log.debug("CP componente prima di POST aggiungi componente = " + componente);

      return instance.getApiAllerteZonaRossa().setComponente(componente);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- aggiungiComponente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponente: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponente: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Problem aggiungiComponenteCivico(ComponenteNucleoZonaRossa componenteZonaRossa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP aggiungiComponenteCivico = " + componenteZonaRossa);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Componente componente = new Componente();

      componente.setIdUtente(new BigDecimal(componenteZonaRossa.getIdUtente()));
      componente.setIdCivico(new BigDecimal(componenteZonaRossa.getIdCivico()));
      componente.setTipo(componenteZonaRossa.getTipo());

      log.debug("CP componente prima di POST aggiungi componente = " + componente);

      return instance.getApiAllerteZonaRossa().setComponente(componente);
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- aggiungiComponente: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponenteCivico: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponenteCivico: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponenteCivico: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Lingua> getLingue() throws BusinessException, ApiException, IOException {
    log.debug("CP getLingue");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      return instance.getApiAllerteZonaRossa().getLingue();
    } catch (BusinessException e) {
      log.error("ServiziAllerteZonaRossaImpl -- getLingue: errore API Allerte Cortesia:");
      throw new BusinessException(ERRORE_API_ALLERTE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- getLingue: errore durante la chiamata delle API Allerte Cortesia"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_ALLERTE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- getLingue: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAllerteZonaRossaImpl -- aggiungiComponenteCivico: errore durante la chiamata delle API allerte zona rossa ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
