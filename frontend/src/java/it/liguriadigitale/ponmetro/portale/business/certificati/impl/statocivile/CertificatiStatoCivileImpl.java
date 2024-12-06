package it.liguriadigitale.ponmetro.portale.business.certificati.impl.statocivile;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.certificati.impl.common.CertificatiBackendImpl;
import it.liguriadigitale.ponmetro.portale.business.certificati.service.ServiziCertificatiStatoCivile;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mail.SendMailUtil;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.WrapperGetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.builder.WrapperGetSelezioneAttoResponseGenericResponseBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggio;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggioBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Atto;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoAttoPersona;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiAttiPersonaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetRicercaAttiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoAttiResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoAttiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import it.liguriadigitale.ponmetro.servizianagrafici.model.MezzoConsegna;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoSCResponseGenericResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.mail.MessagingException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class CertificatiStatoCivileImpl extends CertificatiBackendImpl
    implements ServiziCertificatiStatoCivile {

  private static Log log = LogFactory.getLog(CertificatiStatoCivileImpl.class);

  @Override
  public List<CertificatoAttoPersona> elencoCertificatiRichiesti(Utente utente) {
    List<CertificatoAttoPersona> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetCertificatiAttiPersonaResponseGenericResponse response =
          instance
              .getApiCertificatoStatoCivile()
              .getCertificatiAttiPersona(utente.getCodiceFiscaleOperatore());
      if (response.getStatusCode() == null) {
        lista = response.getResult().getCertificati();
        return ordinaListaCertificati(lista);
      }
    } catch (BusinessException e) {
      log.error("Errore elencoCertificatiRichiesti: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiStatoCivileImpl -- elencoCertificatiRichiesti: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati stato civile"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return lista;
  }

  private List<CertificatoAttoPersona> ordinaListaCertificati(List<CertificatoAttoPersona> lista) {
    Collections.sort(lista, new CertificatoAttoPersonaComparator());
    return lista;

    // return lista.stream()
    // .sorted(Comparator.comparing(CertificatoAttoPersona::getIdentificativoRichiesta).reversed())
    // .collect(Collectors.toList());
  }

  @Override
  public GetScaricoCertificatoAttiResponse getScaricoCertificatoAtti(
      PostRichiestaEmissioneCertificatoAttoRequest request, Integer idCertificato, Utente utente)
      throws BusinessException, ApiException {
    String codiceFiscaleRichiedente = request.getEstremiRichiedente().getCodiceFiscale();
    String codiceFiscaleIntestatario = request.getEstremiIntestatario().getCodiceFiscale();
    String idRichiesta = String.valueOf(idCertificato);
    idRichiesta = togliPrefissoDaIdRichiesta(idRichiesta);

    if (codiceFiscaleRichiedente == null) {
      codiceFiscaleRichiedente = utente.getCodiceFiscaleOperatore();
    }
    log.debug("codiceFiscaleRichiedente=" + codiceFiscaleRichiedente);
    log.debug("codiceFiscaleIntestatario=" + codiceFiscaleIntestatario);
    log.debug("idRichiesta=" + idRichiesta);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetScaricoCertificatoAttiResponseGenericResponse response =
          instance
              .getApiCertificatoStatoCivile()
              .getScaricoCertificatoAtti(
                  codiceFiscaleRichiedente, codiceFiscaleIntestatario, idRichiesta);
      log.debug("response: " + response.getStatusCode());
      if (response.getStatusCode() == null) {
        return response.getResult();
      } else {
        throw new BusinessException(
            response.getStatusCode() + "-" + response.getStatusDescription());
      }
    } catch (WebApplicationException e) {
      log.error(
          "CertificatiStatoCivileImpl -- getScaricoCertificatoAtti: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "CertificatiStatoCivileImpl -- getScaricoCertificatoAtti: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati stato civile"));
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
  public PostRichiestaEmissioneCertificatoSCResponseGenericResponse
      postRichiestaEmissioneCertificatoAtto(PostRichiestaEmissioneCertificatoAttoRequest request)
          throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiCertificatoStatoCivile().postRichiestaEmissioneCertificatoAtto(request);
    } catch (BusinessException e) {
      log.error("Errore postRichiestaEmissioneCertificatoAtto: ", e);
      throw new BusinessException("Impossibile inviare la richiesta");
    } catch (RuntimeException e) {
      log.error(
          "CertificatiStatoCivileImpl -- postRichiestaEmissioneCertificatoAtto: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati stato civile"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Atto> getRicercaAtti(
      Utente utente, PostRichiestaEmissioneCertificatoAttoRequest request) {

    List<Atto> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      GetRicercaAttiResponseGenericResponse response =
          instance.getApiCertificatoStatoCivile().getRicercaAtti();
      if (response.getStatusCode() == null) {
        lista = response.getResult().getAtti();
        if (!isIntestatarioInNucleoFamigliare(utente, request.getEstremiIntestatario())) {
          lista = rimuoviNonRichiedibilePerTutti(lista);
        } else {
          lista = rimuoviDisabilitati(lista);
        }
      }
    } catch (BusinessException e) {
      log.error("Errore getRicercaAtti: ", e);
    } catch (RuntimeException e) {
      log.error(
          "CertificatiStatoCivileImpl -- getRicercaAtti: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati stato civile"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }

    return lista;
  }

  @Override
  public List<MezzoConsegna> getRicercaMezziConsegna() {
    List<MezzoConsegna> lista = new ArrayList<>();
    MezzoConsegna mezzo1 = new MezzoConsegna();
    mezzo1.setTipoConsegnaScelta("Spedizione posta ordinaria");
    MezzoConsegna mezzo2 = new MezzoConsegna();
    mezzo2.setTipoConsegnaScelta("ON-LINE");
    // lista.add(mezzo1);
    lista.add(mezzo2);
    return lista;
  }

  private boolean isIntestatarioInNucleoFamigliare(
      Utente utente, Intestatario estremiIntestatario) {

    Boolean found = false;
    if (!utente.getNucleoFamiliareAllargato().isEmpty()) {
      for (ComponenteNucleo componente : utente.getNucleoFamiliareAllargato()) {
        if (componente != null
            && componente.getDatiCittadino() != null
            && componente.getDatiCittadino().getCpvTaxCode() != null) {
          log.debug("componente:" + componente.getDatiCittadino().getCpvTaxCode());
          if (componente
              .getDatiCittadino()
              .getCpvTaxCode()
              .equalsIgnoreCase(estremiIntestatario.getCodiceFiscale())) {
            found = true;
          }
        } else {
          log.error(
              "ERRORE CertificatiStatoCivileImpl > isIntestatarioInNucleoFamigliare "
                  + "componente, CpvTaxCode, Dati Cittadino di "
                  + utente.getNome()
                  + " "
                  + utente.getCognome()
                  + " e' nullo");
        }
      }
    }
    log.debug("found:" + found);
    return found;
  }

  private List<Atto> rimuoviDisabilitati(List<Atto> lista) {
    List<VCertificatiTipi> listaCertificatiDisponibili =
        getCertificatiBackendPerUfficio(UFFICIO.STATO_CIVILE);

    List<Atto> nuovaLista = new ArrayList<>();
    for (Atto certificato : lista) {
      if (!getCertificatoPerCodice(certificato.getCodice(), listaCertificatiDisponibili)
          .isEmpty()) {
        nuovaLista.add(certificato);
      }
    }
    log.debug("rimuoviNonRichiedibilePerTutti - listaFiltrata:" + nuovaLista);
    return nuovaLista;
  }

  private List<Atto> rimuoviNonRichiedibilePerTutti(List<Atto> lista) {
    // CODICE PRECEDENTE DA RIMUOVERE
    // nuovaLista = lista.stream().filter(elemento ->
    // !elemento.getRichiedibilePerTutti().equalsIgnoreCase("NO"))
    // .collect(Collectors.toList());
    // log.debug("rimuoviNonRichiedibilePerTutti: " + lista.size());

    List<VCertificatiTipi> listaBackend =
        getCertificatiPerTutti(getCertificatiBackendPerUfficio(UFFICIO.STATO_CIVILE));
    log.debug("rimuoviNonRichiedibilePerTutti - listaBackend:" + listaBackend);
    List<Atto> nuovaLista = new ArrayList<>();
    for (Atto atto : lista) {
      if (!getCertificatoPerCodice(atto.getCodice(), listaBackend).isEmpty()) {
        nuovaLista.add(atto);
      }
    }
    log.debug("rimuoviNonRichiedibilePerTutti - listaFiltrata:" + nuovaLista);
    return nuovaLista;
  }

  @Override
  public boolean checkCertificatoRichiedibile(CertificatoAttoPersona certificato) {

    String codiceFiscaleIntestatario = certificato.getEstremiIntestatario().getCodiceFiscale();
    String codiceFiscaleAltroIntestatario = null;
    LocalDate dataCertificatoStorico = null;
    String codiceCertificato = certificato.getIdentificativoRichiesta();
    if (certificato.getEstremiAltraPersona() != null) {
      codiceFiscaleAltroIntestatario = certificato.getEstremiAltraPersona().getCodiceFiscale();
    }
    if (certificato.getDataCertificatoStorico() != null) {
      dataCertificatoStorico = certificato.getDataCertificatoStorico();
      // dataCertificatoStorico =
      // LocalDateUtil.getOffsetDateTime(certificato.getDataCertificatoStorico());
    }
    log.debug("codiceFiscaleIntestatario=" + codiceFiscaleIntestatario);
    log.debug("codiceFiscaleAltroIntestatario=" + codiceFiscaleAltroIntestatario);
    log.debug("dataCertificatoStorico=" + dataCertificatoStorico);
    log.debug("codiceCertificato=" + codiceCertificato);
    GetSelezioneAttoResponseGenericResponse response;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      response =
          instance
              .getApiCertificatoStatoCivile()
              .getSelezioneAtto(
                  codiceCertificato,
                  codiceFiscaleIntestatario,
                  dataCertificatoStorico,
                  codiceFiscaleAltroIntestatario);
      log.debug("Response checkCertificatoRichiedibile " + response);
      return checkCertificabilita(response);
    } catch (BusinessException e) {
      log.error("Errore checkCertificatoRichiedibile: ", e);
      return false;
    } catch (RuntimeException e) {
      log.error(
          "CertificatiStatoCivileImpl -- checkCertificatoRichiedibile: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("certificati stato civile"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private boolean getCertificabilitaDBLocale(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni) {

    String idCert = request.getCodiceCertificato();
    log.debug("idCert:" + idCert);
    List<VCertificatiTipi> lista =
        getCertificatoPerCodice(idCert, getCertificatiBackendPerUfficio(UFFICIO.STATO_CIVILE));
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

  private boolean checkCertificabilita(GetSelezioneAttoResponseGenericResponse response) {
    if ("KO".equalsIgnoreCase(response.getStatus())) {
      return false;
    } else {
      if (response.getResult() != null) {
        log.debug("Certificato non disponibile");
      }
      log.debug("Certificato disponibile in formato digitale");
      return true;
    }
  }

  @Override
  public WrapperGetSelezioneAttoResponseGenericResponse checkCertificatoRichiedibile(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException {
    if (getCertificabilitaDBLocale(request, informazioni)) {
      ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
      try {
        String codiceFiscaleIntestatario = request.getEstremiIntestatario().getCodiceFiscale();
        String codiceFiscaleAltroIntestatario = null;
        LocalDate dataCertificatoStorico = null;
        String codiceCertificato = request.getCodiceCertificato();
        if (request.getEstremiAltraPersona() != null) {
          codiceFiscaleAltroIntestatario = request.getEstremiAltraPersona().getCodiceFiscale();
        }
        if (request.getDataCertificatoStorico() != null) {
          dataCertificatoStorico = request.getDataCertificatoStorico();
        }

        log.debug("codiceFiscaleIntestatario=" + codiceFiscaleIntestatario);
        log.debug("codiceFiscaleAltroIntestatario=" + codiceFiscaleAltroIntestatario);
        log.debug("dataCertificatoStorico=" + dataCertificatoStorico);
        log.debug("codiceCertificato=" + codiceCertificato);
        GetSelezioneAttoResponseGenericResponse response =
            instance
                .getApiCertificatoStatoCivile()
                .getSelezioneAtto(
                    codiceCertificato,
                    codiceFiscaleIntestatario,
                    dataCertificatoStorico,
                    codiceFiscaleIntestatario);
        log.debug("Response checkCertificatoRichiedibile " + response);

        boolean isDisponibile = false;
        if (response != null && response.getStatus().equalsIgnoreCase("OK")) {
          isDisponibile = true;
        }
        return WrapperGetSelezioneAttoResponseGenericResponseBuilder.getInstance()
            .addResult(isDisponibile)
            .addResponse(response)
            .build();
      } catch (BusinessException e) {
        log.error("Errore checkCertificatoRichiedibile: ", e);
        throw new BusinessException("Impossibile verificare la disponibilità del certificato");
      } catch (RuntimeException e) {
        log.error(
            "CertificatiStatoCivileImpl -- checkCertificatoRichiedibile: errore durante la chiamata delle API APK ",
            e);
        throw new RestartResponseAtInterceptPageException(
            new ErroreServiziPage("certificati stato civile"));
      } finally {
        log.debug("close connection");
        instance.closeConnection();
      }
    } else {
      return WrapperGetSelezioneAttoResponseGenericResponseBuilder.getInstance()
          .addResult(false)
          .build();
    }
  }

  @Override
  public boolean inviaRichiestaPerEmail(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni) {
    boolean isResidenteComuneGenova = isCittadinoResidente(request);
    informazioni.setResidenteComuneGenova(isResidenteComuneGenova);
    return inviaMailCertificatoStatoCivile(request, informazioni);
  }

  private boolean inviaMailCertificatoStatoCivile(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni) {

    String mailFrom = BaseServiceImpl.FROM_ADDRESS;
    String mailto = BaseServiceImpl.TO_STATOCIVLE_ADDRESS;
    String mailCC = "";
    if (BaseServiceImpl.CC_ANAGRAFE_ADDRESS != null
        && !BaseServiceImpl.CC_ANAGRAFE_ADDRESS.isEmpty()) {
      mailCC = BaseServiceImpl.CC_ANAGRAFE_ADDRESS + ";";
    }
    String oggetto =
        "Richiesta certificato Stato Civile: "
            + informazioni.getDescrizioneTipoCertificato()
            + " CF: "
            + request.getEstremiIntestatario().getCodiceFiscale();

    String mailbody =
        "<p> E' stata inserita una richiesta per Certificato: "
            + SendMailUtil.getEscapedHTML(informazioni.getDescrizioneTipoCertificato())
            + "</p>"
            + "<br>";
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
    LocalDate dataNascitaFromCf = null;
    try {
      dataNascitaFromCf =
          CodiceFiscaleValidatorUtil.getDataNascitaFromCf(
              request.getEstremiIntestatario().getCodiceFiscale());
    } catch (Exception e1) {
      log.debug("errore: ", e1);
    }
    if (informazioni.isResidenteComuneGenova()) {
      try {
        Residente residente =
            ServiceLocator.getInstance()
                .getServizioDemografico()
                .getDatiResidente(request.getEstremiIntestatario().getCodiceFiscale());
        mailbody +=
            "<li> Data di nascita del soggetto intestatario: "
                + LocalDateUtil.getDataFormatoEuropeo(residente.getCpvDateOfBirth())
                + "</li>";
      } catch (BusinessException e) {
        log.error(
            "ERRORE CertificatiStatoCivileImpl > inviaMailCertificatoStatoCivile BusinessException = "
                + e);
      } catch (ApiException e) {
        log.error(
            "ERRORE CertificatiStatoCivileImpl > inviaMailCertificatoStatoCivile ApiException = "
                + e);
      }

    } else {
      mailbody +=
          "<li> Data di nascita (calcolata da CF) del soggetto intestatario: "
              + LocalDateUtil.getDataFormatoEuropeo(dataNascitaFromCf)
              + "</li>";
    }

    if (request.getDataCertificatoStorico() != null) {
      if (informazioni.isResidenteComuneGenova()) {
        mailbody +=
            "<li> Data evento : "
                + LocalDateUtil.getDataFormatoEuropeo(request.getDataCertificatoStorico())
                + "</li>";
      } else {
        mailbody +=
            "<li> Data evento (inserita da utente Fascicolo): "
                + LocalDateUtil.getDataFormatoEuropeo(request.getDataCertificatoStorico())
                + "</li>";
      }
    }

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

    if (request.getEstremiAltraPersona() != null
        && request.getEstremiAltraPersona().getCodiceFiscale() != null) {
      if (request.getDataCertificatoStorico() != null) {
        mailbody +=
            "<li> Data evento: "
                + LocalDateUtil.getDataFormatoEuropeo(request.getDataCertificatoStorico())
                + "</li>";
      }
      mailbody +=
          "<li> CF altro soggetto interessato: "
              + request.getEstremiAltraPersona().getCodiceFiscale()
              + "</li>";
      LocalDate altraDataNascitaFromCf =
          CodiceFiscaleValidatorUtil.getDataNascitaFromCf(
              request.getEstremiAltraPersona().getCodiceFiscale());
      mailbody +=
          "<li> Data di nascita del soggetto intestatario: "
              + LocalDateUtil.getDataFormatoEuropeo(altraDataNascitaFromCf)
              + "</li>";
    }
    if (informazioni.getDescrizioneMotivazioneRichiesta() != null) {
      mailbody +=
          "<li> Motivazione: "
              + SendMailUtil.getEscapedHTML(informazioni.getDescrizioneMotivazioneRichiesta())
              + "</li>";
    }
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
    } catch (MessagingException e) {
      log.error("Errore durante l'invio della Mail segnalazione certificati: " + e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public GetScaricoCertificatoAttiResponse getScaricoCertificatoDopoPost(
      PostRichiestaEmissioneCertificatoAttoRequest request, Utente utente)
      throws BusinessException, ApiException {

    PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
        postRichiestaEmissioneCertificatoAtto(request);
    if ("OK".equalsIgnoreCase(response.getStatus())) {
      if (response.getResult() != null) {
        Integer idCertificato = response.getResult().getIdCertificato();
        log.debug("Certificato da scaricare: " + idCertificato);
        return getScaricoCertificatoAtti(request, idCertificato, utente);
      } else {
        log.debug("Oggetto result nullo: " + response);
        throw new BusinessException("Id certificato non restituito");
      }
    } else {
      String errore = response.getStatus() + " " + response.getStatusDescription();
      log.debug("Errore durante la POST: " + errore);
      throw new BusinessException(errore);
    }
  }

  protected void erroreSeListaVuota(List<CertificatoAttoPersona> lista) throws BusinessException {
    if (lista.isEmpty()) {
      log.debug("Nessun certificato trovato");
      throw new BusinessException("Nessun certificato trovato");
    }
  }

  @Override
  public boolean inviaRichiestaPerEmailConPost(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException {

    try {
      // TODO inserire gestione errore API che non risponde
      PostRichiestaEmissioneCertificatoSCResponseGenericResponse response =
          postRichiestaEmissioneCertificatoAtto(request);
      log.debug("response" + response);
    } catch (Exception e) {
      log.error("Impossibile contattare: ", e);
    }
    return inviaRichiestaPerEmail(request, informazioni);
  }

  private boolean isCittadinoResidente(PostRichiestaEmissioneCertificatoAttoRequest request) {

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
}
