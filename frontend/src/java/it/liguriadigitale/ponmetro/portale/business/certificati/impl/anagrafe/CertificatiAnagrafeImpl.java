package it.liguriadigitale.ponmetro.portale.business.certificati.impl.anagrafe;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.certificati.CertificatiResponse;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.certificati.impl.common.CertificatiBackendImpl;
import it.liguriadigitale.ponmetro.portale.business.certificati.service.ServiziCertificatiAnagrafe;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mail.SendMailUtil;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggio;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggioBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Certificato;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoPersona;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiPersonaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoCartaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetUtilizzoCertificatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoSCResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.UtilizzoItem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class CertificatiAnagrafeImpl extends CertificatiBackendImpl
    implements ServiziCertificatiAnagrafe {

  private static Log log = LogFactory.getLog(CertificatiAnagrafeImpl.class);

  enum ERRORI_ANPR {
    EN164,
    EN163,
    EN160,
    EN354,
    EN061,
    EN529
  }

  @Override
  public List<CertificatoPersona> elencoCertificatiRichiesti(Utente utente) {
    List<CertificatoPersona> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetCertificatiPersonaResponseGenericResponse response =
          instance
              .getApiCertificatoAnagrafe()
              .getCertificatiPersona(utente.getCodiceFiscaleOperatore());
      if (response.getStatusCode() == null) {
        lista = response.getResult().getCertificati();
        // lista = lista.stream().filter(elemento ->
        // elemento.getIdentificativoRichiesta().startsWith("RICH"))
        // .collect(Collectors.toList());
        return ordinaListaCertificati(lista);
      }
    } catch (BusinessException e) {
      log.error("Errore elencoCertificatiRichiesti: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- elencoCertificatiRichiesti: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return lista;
  }

  private List<CertificatoPersona> ordinaListaCertificati(List<CertificatoPersona> lista) {
    Collections.sort(lista, new CertificatoPersonaComparator());
    return lista;
  }

  @Override
  public GetScaricoCertificatoResponse getScaricoCertificato(
      PostRichiestaEmissioneCertificatoRequest request, Integer idCertificato, Utente utente)
      throws BusinessException, ApiException {
    String codiceFiscaleRichiedente = request.getEstremiRichiedente().getCodiceFiscale();
    String codiceFiscaleIntestatario = request.getEstremiIntestatario().getCodiceFiscale();
    String idRichiesta = String.valueOf(idCertificato);
    if (codiceFiscaleRichiedente == null) {
      codiceFiscaleRichiedente = utente.getCodiceFiscaleOperatore();
    }
    idRichiesta = togliPrefissoDaIdRichiesta(idRichiesta);
    log.debug("codiceFiscaleRichiedente=" + codiceFiscaleRichiedente);
    log.debug("codiceFiscaleIntestatario=" + codiceFiscaleIntestatario);
    log.debug("idRichiesta=" + idRichiesta);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetScaricoCertificatoResponseGenericResponse response =
          instance
              .getApiCertificatoAnagrafe()
              .getScaricoCertificato(
                  codiceFiscaleRichiedente, codiceFiscaleIntestatario, idRichiesta);
      log.debug("response: " + response.getStatusCode());
      if (response.getResult() != null) {
        return response.getResult();
      } else {
        throw new BusinessException(
            response.getStatusCode() + "-" + response.getStatusDescription());
      }
    } catch (WebApplicationException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getScaricoCertificato: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getScaricoCertificato: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private String togliPrefissoDaIdRichiesta(String idRichiesta) {
    int posizionePuntoVirgola = idRichiesta.indexOf(";");
    log.debug("posizionePuntoVirgola: " + posizionePuntoVirgola);
    if (posizionePuntoVirgola > -1) {
      idRichiesta = idRichiesta.substring(posizionePuntoVirgola + 1);
    }
    return idRichiesta;
  }

  @Override
  public boolean postRichiestaEmissioneCertificato(PostRichiestaEmissioneCertificatoRequest request)
      throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
          instance.getApiCertificatoAnagrafe().postRichiestaEmissioneCertificato(request);
      log.debug("response postRichiestaEmissioneCertificato=" + response);
      return "OK".equalsIgnoreCase(response.getStatus());
    } catch (BusinessException e) {
      log.error("Errore postRichiestaEmissioneCertificato: ", e);
      throw new BusinessException("Impossibile inviare la richiesta");
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- postRichiestaEmissioneCertificato: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Certificato> getCertificati(
      Utente utente, PostRichiestaEmissioneCertificatoRequest request) {
    List<Certificato> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetCertificatiResponseGenericResponse response =
          instance.getApiCertificatoAnagrafe().getCertificati();
      log.debug("response: " + response.getStatusCode());
      if (response.getStatusCode() == null) {
        lista = response.getResult().getCertificati();
        log.debug("getCertificati: " + lista.size());
        if (!isIntestatarioInNucleoFamigliare(utente, request.getEstremiIntestatario())) {
          lista = rimuoviNonRichiedibilePerTutti(lista);
        } else {
          lista = rimuoviDisabilitati(lista);
        }
      }
    } catch (BusinessException e) {
      log.error("Errore getCertificati: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getCertificati: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return lista;
  }

  private List<Certificato> rimuoviDisabilitati(List<Certificato> lista) {
    List<VCertificatiTipi> listaCertificatiDisponibili =
        getCertificatiBackendPerUfficio(UFFICIO.ANAGRAFE);

    List<Certificato> nuovaLista = new ArrayList<>();
    for (Certificato certificato : lista) {
      if (!getCertificatoPerCodice(certificato.getCodice(), listaCertificatiDisponibili)
          .isEmpty()) {
        nuovaLista.add(certificato);
      }
    }
    log.debug("rimuoviNonRichiedibilePerTutti - listaFiltrata:" + nuovaLista);
    return nuovaLista;
  }

  @Override
  public List<ListItem> getTipologiaCertificati() {
    List<ListItem> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetTipoCartaResponseGenericResponse response =
          instance.getApiCertificatoAnagrafe().getTipoCarta();
      if (response.getStatusCode() == null) {
        lista = response.getResult().getTipologieCertificabilita();
      }
    } catch (BusinessException e) {
      log.error("Errore getTipologiaCertificati: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getTipologiaCertificati: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return lista;
  }

  @Override
  public List<UtilizzoItem> getUtilizzoCertificati() {
    List<UtilizzoItem> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetUtilizzoCertificatiResponseGenericResponse response =
          instance.getApiCertificatoAnagrafe().getUtilizzoCertificati();
      if (response.getStatusCode() == null) {
        lista = response.getResult().getUtilizziCertificati();
        log.debug("getUtilizziCertificato: " + lista.size());
      }
    } catch (BusinessException e) {
      log.error("Errore getUtilizzoCertificati: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getUtilizzoCertificati: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return lista;
  }

  private List<Certificato> rimuoviNonRichiedibilePerTutti(List<Certificato> lista) {

    List<VCertificatiTipi> listaCertificatiDisponibiliPerTutti =
        getCertificatiPerTutti(getCertificatiBackendPerUfficio(UFFICIO.ANAGRAFE));

    List<Certificato> nuovaLista = new ArrayList<>();
    for (Certificato certificato : lista) {
      if (!getCertificatoPerCodice(certificato.getCodice(), listaCertificatiDisponibiliPerTutti)
          .isEmpty()) {
        nuovaLista.add(certificato);
      }
    }
    log.debug("rimuoviNonRichiedibilePerTutti - listaFiltrata:" + nuovaLista);
    return nuovaLista;
  }

  private boolean isIntestatarioInNucleoFamigliare(
      Utente utente, Intestatario estremiIntestatario) {

    Boolean found = false;
    for (ComponenteNucleo componente : utente.getNucleoFamiliareAllargato()) {
      if (LabelFdCUtil.checkIfNotNull(componente.getDatiCittadino())
          && LabelFdCUtil.checkIfNotNull(componente.getDatiCittadino().getCpvTaxCode())
          && componente
              .getDatiCittadino()
              .getCpvTaxCode()
              .equalsIgnoreCase(estremiIntestatario.getCodiceFiscale())) {
        found = true;
      }
    }
    log.debug("found:" + found);
    return found;
  }

  @Override
  public List<ListItem> getRicercaMezziConsegna() {
    List<ListItem> lista = new ArrayList<>();
    ListItem mezzo1 = new ListItem();
    mezzo1.setCodice("0");
    mezzo1.setDescrizione("Spedizione posta ordinaria");
    ListItem mezzo2 = new ListItem();
    mezzo2.setCodice("1");
    mezzo2.setDescrizione("ON-LINE");
    ListItem mezzo3 = new ListItem();
    mezzo3.setCodice("2");
    mezzo3.setDescrizione("Posta elettronica");
    lista.add(mezzo2);
    lista.add(mezzo3);
    return lista;
  }

  private List<VCertificatiTipi> getBackend() {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      CertificatiResponse response = instance.getApiCertificatiBackend().getListaCertificati();
      if (response.getEsito().isEsito()) {
        log.debug("lista backend:" + response.getListaCertificati().size());
        return response.getListaCertificati();
      }
    } catch (BusinessException e) {
      log.error("Errore getBackend");
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- getBackend: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return null;
  }

  @SuppressWarnings("unused")
  private List<VCertificatiTipi> getCertificabilitaAnagrafici(String idCert) {
    List<VCertificatiTipi> lista = getBackend();

    String filtro = "ANAGRAFE";

    List<VCertificatiTipi> listaAnagrafici =
        lista.stream()
            .filter(certificato -> filtro.contains(certificato.getTipo()))
            .collect(Collectors.toList());

    List<VCertificatiTipi> listaCercata =
        listaAnagrafici.stream()
            .filter(certificato -> idCert.contains(certificato.getCodiceAnpr()))
            .collect(Collectors.toList());

    return listaCercata;
  }

  @Override
  public boolean getCertificabilita(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni) {

    String idCert = request.getCodiceCertificato();
    log.debug("idCert:" + idCert);
    List<VCertificatiTipi> lista =
        getCertificatoPerCodice(idCert, getCertificatiBackendPerUfficio(UFFICIO.ANAGRAFE));
    log.debug("lista:" + lista.size());
    List<VCertificatiTipi> listaFiltrata = getCertificatiOnLine(lista);
    log.debug("listaFiltrata:" + listaFiltrata);
    log.debug("listaFiltrata.isEmpty:" + listaFiltrata.isEmpty());
    if (listaFiltrata.isEmpty()) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean inviaRichiestaPerEmail(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni) {
    return inviaMailCertificatiAnagrafici(request, informazioni);
  }

  private boolean inviaMailCertificatiAnagrafici(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni) {

    String mailFrom = BaseServiceImpl.FROM_ADDRESS;
    String mailCC = null;
    if (BaseServiceImpl.CC_ANAGRAFE_ADDRESS != null
        && !BaseServiceImpl.CC_ANAGRAFE_ADDRESS.isEmpty()) {
      mailCC = BaseServiceImpl.CC_ANAGRAFE_ADDRESS + ";";
    }
    String mailto = BaseServiceImpl.TO_ANAGRAFE_ADDRESS;
    String oggetto =
        "Richiesta certificato Anagrafe: "
            + informazioni.getDescrizioneTipoCertificato()
            + " CF: "
            + request.getEstremiIntestatario().getCodiceFiscale();

    String dataCertStorico = "";
    if (request.getDataCertificatoStorico() != null) {
      dataCertStorico = LocalDateUtil.getDataFormatoEuropeo(request.getDataCertificatoStorico());
    }
    String mailbody =
        "<p> E' stata inserita una richiesta per Certificato: "
            + SendMailUtil.getEscapedHTML(informazioni.getDescrizioneTipoCertificato())
            + " "
            + dataCertStorico;
    if (informazioni.getDataDA() != null) {
      mailbody += " data da: " + LocalDateUtil.getDataFormatoEuropeo(informazioni.getDataDA());
    }
    if (informazioni.getDataA() != null) {
      mailbody += " data a: " + LocalDateUtil.getDataFormatoEuropeo(informazioni.getDataA());
    }
    mailbody += "</p><br>";
    mailbody +=
        "<p> Di seguito i dati inseriti nel Fascicolo durante la procedura on-line:" + "</p>";
    mailbody += "<ul>";
    mailbody +=
        "<li> Nome: "
            + SendMailUtil.getEscapedHTML(request.getEstremiIntestatario().getNome())
            + "</li>";
    mailbody +=
        "<li> Cognome: "
            + SendMailUtil.getEscapedHTML(request.getEstremiIntestatario().getCognome())
            + "</li>";
    mailbody +=
        "<li> Codice fiscale: " + request.getEstremiIntestatario().getCodiceFiscale() + "</li>";
    LocalDate dataNascitaFromCf =
        CodiceFiscaleValidatorUtil.getDataNascitaFromCf(
            request.getEstremiIntestatario().getCodiceFiscale());
    mailbody +=
        "<li> Data di nascita del soggetto intestatario: "
            + LocalDateUtil.getDataFormatoEuropeo(dataNascitaFromCf)
            + "</li>";

    if (!informazioni.isResidenteComuneGenova()) {
      mailbody += "<li> Cittadino NON residente nel Comune di Genova " + "</li>";
    }
    if (request.getEstremiRichiedente() != null
        && request
            .getEstremiRichiedente()
            .getCodiceFiscale()
            .equalsIgnoreCase(request.getEstremiIntestatario().getCodiceFiscale())) {
      mailbody += "<br>richiesto per s&eacute; stesso.";
    } else {
      mailbody += "<br>richiesto da:";
      mailbody +=
          "<li> Nome: "
              + SendMailUtil.getEscapedHTML(request.getEstremiRichiedente().getNome())
              + "</li>";
      mailbody +=
          "<li> Cognome: "
              + SendMailUtil.getEscapedHTML(request.getEstremiRichiedente().getCognome())
              + "</li>";
      mailbody +=
          "<li> Codice fiscale: " + request.getEstremiRichiedente().getCodiceFiscale() + "</li>";
    }

    mailbody +=
        "<li> Motivazione: "
            + SendMailUtil.getEscapedHTML(informazioni.getDescrizioneMotivazioneRichiesta())
            + "</li>";
    if (request.getNumeroMarcaBollo() != null) {
      mailbody += "<li> N. marca da bollo: " + request.getNumeroMarcaBollo() + "</li>";
      mailbody +=
          "<li> Data marca da bollo: "
              + LocalDateUtil.getDataFormatoEuropeo(request.getDataMarcaBollo())
              + "</li>";
    }
    mailbody += "<li> Mail richiedente: " + request.getEmail() + "</li>";
    mailbody += "<li> Cellulare richiedente: " + informazioni.getTelefonoContatto() + "</li>";
    mailbody +=
        "<li> Data richiesta: " + LocalDateUtil.getDataFormatoEuropeo(LocalDate.now()) + "</li>";
    mailbody += "</ul>";

    ContenutoMessaggio contenutoMessaggio =
        new ContenutoMessaggioBuilder()
            .getInstance()
            .setTo(mailto)
            .setCc(mailCC)
            .setBcc("")
            .setFrom(mailFrom)
            .setSubject(oggetto)
            .setText(mailbody)
            .setSmtpServer(BaseServiceImpl.HOST_SMTP)
            .build();

    try {
      log.debug("mailbody:" + mailbody);
      SendMailUtil.getInstance().sendHtmlMail(contenutoMessaggio);
      log.info("Mail inviata");
    } catch (MessagingException e) {
      log.error("Errore durante l'invio della Mail segnalazione certificati: " + e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public GetScaricoCertificatoResponse getScaricoCertificatoDopoPost(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni,
      Utente utente)
      throws BusinessException, ApiException {

    PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
        postRichiestaEmissioneCertificatoAnagrafe(request);
    if ("OK".equalsIgnoreCase(response.getStatus())) {
      if (response.getResult() != null) {
        Integer idCertificato = response.getResult().getIdCertificato();
        log.debug("Certificato da scaricare: " + idCertificato);
        return getScaricoCertificato(request, idCertificato, utente);
      } else {
        log.debug("Oggetto result nullo: " + response);
        throw new BusinessException("Id certificato non restituito");
      }
    } else {
      String errore = response.getStatus() + " " + response.getStatusDescription();
      log.debug("Errore durante la POST: " + errore);

      if (Boolean.TRUE.equals(isErroreChePrevedeInvioEmail(errore))) {
        ServiceLocator.getInstance()
            .getCertificatiAnagrafe()
            .inviaRichiestaPerEmail(request, informazioni);
      }
      throw new BusinessException(errore);
    }
  }

  private Boolean isErroreChePrevedeInvioEmail(String messaggioErrore) {

    Boolean erroreRichiedeEmail = true;
    log.debug("ERRORI_ANPR:" + messaggioErrore);
    for (ERRORI_ANPR codiceErrore : ERRORI_ANPR.values()) {
      if (messaggioErrore.contains(codiceErrore.name())) {
        log.debug("ERRORI_ANPR:" + codiceErrore.name());
        erroreRichiedeEmail = false;
      }
    }
    return erroreRichiedeEmail;
  }

  @SuppressWarnings("unused")
  private List<CertificatoPersona> ordinaLista(List<CertificatoPersona> lista) {

    lista =
        lista.stream()
            .filter(elemento -> elemento.getDataRichiesta().isEqual(LocalDate.now()))
            .collect(Collectors.toList());
    lista =
        lista.stream()
            .sorted(Comparator.comparing(CertificatoPersona::getIdentificativoRichiesta).reversed())
            .collect(Collectors.toList());
    return lista;
  }

  @SuppressWarnings("unused")
  private void erroreSeTipoRichiestoNonPresente(
      List<CertificatoPersona> lista, PostRichiestaEmissioneCertificatoRequest modelloForm)
      throws BusinessException {
    CertificatoPersona ultimoCertificato = lista.get(0);
    if (!ultimoCertificato.getTipoCertificato().equals(modelloForm.getTipoCertificato())) {
      log.debug("Ultimo certificato trovato su APK non corrisponde alla tipologia richiesta");
      throw new BusinessException(
          "Ultimo certificato trovato non corrisponde alla tipologia richiesta");
    }
  }

  @SuppressWarnings("unused")
  private void erroreSeListaVuota(List<CertificatoPersona> lista) throws BusinessException {
    if (lista.isEmpty()) {
      log.debug("Nessun certificato trovato su APK");
      throw new BusinessException("Nessun certificato trovato");
    }
  }

  private PostRichiestaEmissioneCertificatoSCResponseGenericResponse
      postRichiestaEmissioneCertificatoAnagrafe(PostRichiestaEmissioneCertificatoRequest request)
          throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
          instance.getApiCertificatoAnagrafe().postRichiestaEmissioneCertificato(request);
      log.debug("response postRichiestaEmissioneCertificato=" + response);
      return response;
    } catch (BusinessException e) {
      log.error("Errore postRichiestaEmissioneCertificatoAnagrafe: ", e);
      throw new BusinessException("Impossibile inviare la richiesta");
    } catch (RuntimeException e) {
      log.error(
          "CertificatiAnagrafeImpl -- postRichiestaEmissioneCertificatoAnagrafe: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati anagrafici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public boolean isCittadinoResidente(PostRichiestaEmissioneCertificatoRequest request) {

    if (request != null && request.getEstremiIntestatario() != null) {
      String codiceFiscaleIntestatario = request.getEstremiIntestatario().getCodiceFiscale();
      try {
        Residente residente =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getDatiResidentePerApk(codiceFiscaleIntestatario);
        if (residente != null) {
          return true;
        } else {
          return false;
        }
      } catch (BusinessException | ApiException e) {
        log.debug("Impossibile contattare il Demografico: ", e);
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean inviaRichiestaPerEmailConPost(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException {
    // TODO inserire gestione errore API che non risponde
    PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
        postRichiestaEmissioneCertificatoAnagrafe(request);
    log.debug("Post per salvataggio richiesta: " + response);
    return inviaRichiestaPerEmail(request, informazioni);
  }
}
