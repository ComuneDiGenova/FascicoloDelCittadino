package it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.AvvisoPagoPA;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.GeneraAvvisoPagoPARequest;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitsListResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.RinnovaRequest;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service.ServiziGenovaParcheggi;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziGenovaParcheggiImpl implements ServiziGenovaParcheggi {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_GENOVA_PARCHEGGI =
      "Errore di connessione alle API Genova Parcheggi";

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio(
        "Qui puoi trovare tutti i permessi Genova Parcheggi a te intestati con l’indicazione dello stato: emesso (icona verde), in pagamento (icona gialla), annullati (icona rossa) o scaduti (icona grigia).\r\n"
            + "Puoi consultare i dati e rinnovare i tuoi permessi in scadenza cliccando sul relativo pulsante: è possibile effettuare il rinnovo a partire da 30 giorni prima della data fine validità e per i 10 giorni successivi.\r\n");
    messaggio.setType("info");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }

  @Override
  public PermitsListResult getPermessiGePark(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPermessiGePark: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PermitsListResult permessiGeParkOrdinatiPerDataDecrescente = new PermitsListResult();
      PermitsListResult permessiGePark =
          instance.getApiGenovaParcheggi().permitsList(codiceFiscale);

      if (LabelFdCUtil.checkIfNotNull(permessiGePark)) {
        List<Permit> listaPermessi = permessiGePark.getPermitsList();
        List<Permit> listaPermessiOrdinatiPerDataDecrescente = new ArrayList<Permit>();

        if (LabelFdCUtil.checkIfNotNull(listaPermessi)) {

          listaPermessiOrdinatiPerDataDecrescente =
              listaPermessi.stream()
                  .sorted(Comparator.comparing(Permit::getValidTo).reversed())
                  .collect(Collectors.toList());

          permessiGeParkOrdinatiPerDataDecrescente.setPermitsList(
              listaPermessiOrdinatiPerDataDecrescente);
        }
      }

      return permessiGeParkOrdinatiPerDataDecrescente;

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl -- getPermessiGePark: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore durante la chiamata delle API Genova Parcheggi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PermitsListResult getPermessiGeParkConFiltroDaFdCBackend(
      String codiceFiscale, List<BravPermessi> listaPermessiBrav)
      throws BusinessException, ApiException, IOException {
    log.debug(
        "CP getPermessiGeParkConFiltroDaFdCBackend: " + codiceFiscale + " - " + listaPermessiBrav);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PermitsListResult permessiGeParkOrdinatiPerDataDecrescente = new PermitsListResult();
      PermitsListResult permessiGePark =
          instance.getApiGenovaParcheggi().permitsList(codiceFiscale);

      if (LabelFdCUtil.checkIfNotNull(permessiGePark)) {
        List<Permit> listaPermessi = permessiGePark.getPermitsList();
        List<Permit> listaPermessiOrdinatiPerDataDecrescente = new ArrayList<Permit>();
        List<Permit> listaPermessiFiltrati = new ArrayList<Permit>();

        if (LabelFdCUtil.checkIfNotNull(listaPermessi)) {

          log.debug("CP listaPermessi = " + listaPermessi);

          listaPermessiFiltrati =
              listaPermessi.stream()
                  .filter(
                      elemBrav ->
                          listaPermessiBrav.stream()
                              .anyMatch(
                                  elemDB ->
                                      elemDB.getIdPermesso().compareTo(elemBrav.getPermitTypeId())
                                          == 0))
                  .collect(Collectors.toList());

          listaPermessiOrdinatiPerDataDecrescente =
              listaPermessiFiltrati.stream()
                  .sorted(Comparator.comparing(Permit::getValidTo).reversed())
                  .collect(Collectors.toList());

          permessiGeParkOrdinatiPerDataDecrescente.setPermitsList(
              listaPermessiOrdinatiPerDataDecrescente);
        }
      }

      return permessiGeParkOrdinatiPerDataDecrescente;

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl -- getPermessiGePark: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore durante la chiamata delle API Genova Parcheggi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("genovaParcheggi", "Genova Parcheggi"));

    return listaBreadcrumb;
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("Annullato");
    legenda1.setStile("badge bg-danger");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("Da pagare");
    legenda2.setStile("badge bg-warning");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Emesso");
    legenda3.setStile("badge bg-success");
    listaLegenda.add(legenda3);

    Legenda legenda4 = new Legenda();
    legenda4.setTesto("Scaduto");
    legenda4.setStile("badge bg-secondary");
    listaLegenda.add(legenda4);

    return listaLegenda;
  }

  @Override
  public List<PermitAllowedAction> getAzioniSulPermesso(Permit permit)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAzioniSulPermesso: " + permit);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Integer permitIdInteger = permit.getPermitId().intValueExact();
    try {
      return instance.getApiGenovaParcheggi().allowedActions(permitIdInteger);
      // return instance.getApiGenovaParcheggi().permitAllowedActions(permitId);
    } catch (BusinessException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getAzioniSulPermesso: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getAzioniSulPermesso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getAzioniSulPermesso: errore durante la chiamata delle API Genova Parcheggi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<PermitVerificationResult> permitVerification(String customerIdCode, String filterKey)
      throws BusinessException, ApiException, IOException {
    log.debug("permitVerification");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiGenovaParcheggi().permitVerification(customerIdCode, filterKey);
    } catch (BusinessException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- permitVerification: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- permitVerification: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- permitVerification: errore durante la chiamata delle API Genova Parcheggi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PermitsListResult getPermessiGeParkPerPreloadingPage(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPermessiGeParkPerPreloadingPage: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PermitsListResult permessiGeParkOrdinatiPerDataDecrescente = new PermitsListResult();
      PermitsListResult permessiGePark =
          instance.getApiGenovaParcheggi().permitsList(codiceFiscale);

      if (LabelFdCUtil.checkIfNotNull(permessiGePark)) {
        List<Permit> listaPermessi = permessiGePark.getPermitsList();
        List<Permit> listaPermessiOrdinatiPerDataDecrescente = new ArrayList<Permit>();

        if (LabelFdCUtil.checkIfNotNull(listaPermessi)) {
          listaPermessiOrdinatiPerDataDecrescente =
              listaPermessi.stream()
                  .sorted(Comparator.comparing(Permit::getValidTo).reversed())
                  .collect(Collectors.toList());
          permessiGeParkOrdinatiPerDataDecrescente.setPermitsList(
              listaPermessiOrdinatiPerDataDecrescente);
        }
      }

      return permessiGeParkOrdinatiPerDataDecrescente;

    } catch (BusinessException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGeParkPerPreloadingPage: errore API Genova Parcheggi:",
          e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGeParkPerPreloadingPage: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGenovaParcheggiImpl -- getPermessiGeParkPerPreloadingPage: errore durante la chiamata delle API Genova Parcheggi ",
          e);

      return null;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getAllegatoPdfQuietanza(Permit permit)
      throws BusinessException, ApiException, IOException {

    log.debug("getAllegatoPdfQuietanza");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      log.debug("permit_bigDecimale " + permit.getPermitId());
      log.debug("permitIdInteger_test " + permitIdInteger);

      String pdfString = instance.getApiGenovaParcheggi().getPDF(permitIdInteger);
      return DatatypeConverter.parseBase64Binary(pdfString);

    } catch (BusinessException e) {

      log.error("ServiziArpalImpl -- getAllegatoPdfQuietanza: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getAllegatoPdfQuietanza: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getAllegatoPdfQuietanza: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getGeneraAvvisoPagoPa(Permit permit)
      throws BusinessException, ApiException, IOException {

    log.debug("getGeneraAvvisoPagoPa");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      GeneraAvvisoPagoPARequest obj = new GeneraAvvisoPagoPARequest();
      obj.setPermitId(permitIdInteger);
      AvvisoPagoPA avviso = instance.getApiGenovaParcheggi().generaAvvisoPagoPA(obj);
      log.debug("avviso_pdf " + avviso);
      return DatatypeConverter.parseBase64Binary(avviso.getPdfNotice());

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl-- getGeneraAvvisoPagoPa: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- getGeneraAvvisoPagoPa: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getGeneraAvvisoPagoPa: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {

      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagoPA getAvvisoPagoPaIuv(Permit permit)
      throws BusinessException, ApiException, IOException {

    log.debug("getAvvisoPagoPaIuv");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      GeneraAvvisoPagoPARequest obj = new GeneraAvvisoPagoPARequest();
      obj.setPermitId(permitIdInteger);
      AvvisoPagoPA avviso = instance.getApiGenovaParcheggi().generaAvvisoPagoPA(obj);

      return avviso;

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl-- getAvvisoPagoPaIuv: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- getAvvisoPagoPaIuv: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getAvvisoPagoPaIuv: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {

      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void rinnovaUnPermesso(Permit permit) throws BusinessException, ApiException, IOException {

    log.debug("rinnovaUnPermesso");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Integer permitIdIntegerPadre = permit.getPermitId().intValueExact();
      RinnovaRequest obj = new RinnovaRequest();
      log.debug("permitIdIntegerPadre: " + permitIdIntegerPadre);
      obj.setPermitId(permitIdIntegerPadre);
      instance.getApiGenovaParcheggi().rinnova(obj);

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl-- rinnovaUnPermesso: errore API GEPARKPERMESSI:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- rinnovaUnPermesso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- rinnovaUnPermesso: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void annullaPermesso(Permit permit) throws BusinessException, ApiException, IOException {

    log.debug("annullaPermesso");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      RinnovaRequest obj = new RinnovaRequest();
      obj.setPermitId(permitIdInteger);
      instance.getApiGenovaParcheggi().cancel(obj);
    } catch (BusinessException e) {

      log.error("ServiziGenovaParcheggiImpl-- annullaPermesso: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- annullaPermesso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- annullaPermesso: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagoPA getAvvisoByPermit(Permit permit)
      throws BusinessException, ApiException, IOException {

    log.debug("getAvvisoByPermit");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      GeneraAvvisoPagoPARequest obj = new GeneraAvvisoPagoPARequest();
      obj.setDownloadPdf(true);
      obj.setPermitId(permitIdInteger);
      log.debug("getAvvisoByPermit obj " + obj);

      AvvisoPagoPA avviso = instance.getApiGenovaParcheggi().generaAvvisoPagoPA(obj);
      log.debug("avviso_dopo " + avviso);
      return avviso;

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl-- getAvvisoByPermit: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- getAvvisoByPermit: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getAvvisoByPermit: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {

      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagoPA getAvvisoByPermitConBoolean(Permit permit, boolean downloadPdf)
      throws BusinessException, ApiException, IOException {

    log.debug("getAvvisoByPermitConBoolean");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Integer permitIdInteger = permit.getPermitId().intValueExact();
      GeneraAvvisoPagoPARequest obj = new GeneraAvvisoPagoPARequest();
      obj.setDownloadPdf(downloadPdf);
      obj.setPermitId(permitIdInteger);
      log.debug("getAvvisoByPermitConBoolean obj " + obj);

      AvvisoPagoPA avviso = instance.getApiGenovaParcheggi().generaAvvisoPagoPA(obj);
      log.debug("avviso_dopo " + avviso);
      return avviso;

    } catch (BusinessException e) {
      log.error("ServiziGenovaParcheggiImpl-- getAvvisoByPermitConBoolean: errore API GEPARK:", e);
      throw new BusinessException(ERRORE_API_GENOVA_PARCHEGGI);

    } catch (WebApplicationException e) {

      log.error(
          "ServiziGenovaParcheggiImpl -- getAvvisoByPermitConBoolean: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());

    } catch (RuntimeException e) {

      log.error(
          "ServiziGenovaParcheggilImpl -- getAvvisoByPermitConBoolean: errore durante la chiamata delle API GEPARKPERMESSI ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));

    } finally {

      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiOK() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio("Il pagamento è andato a buone fine.");
    // messaggio.setType("danger");
    messaggio.setType("success");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiKO() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio("Il pagamento è andato storto.");
    messaggio.setType("danger");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }
}
