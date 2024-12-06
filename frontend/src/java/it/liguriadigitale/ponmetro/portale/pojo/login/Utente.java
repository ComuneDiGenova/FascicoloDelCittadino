package it.liguriadigitale.ponmetro.portale.pojo.login;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.pojo.common.FrameworkUtente;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.demografico.model.SchedaAnagrafica;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitsListResult;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.enums.CpvParentTypeEnum;
import it.liguriadigitale.ponmetro.portale.pojo.portale.UtenteServiziRistorazioneHandler;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.CittadinoNotFound;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorApiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.WidgetUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.http.WebRequest;

public class Utente extends FrameworkUtente {

  private static final long serialVersionUID = -1904033322764674460L;
  private static Log log = LogFactory.getLog(Utente.class);

  private static final String COMGE_DATANASCITA = "comge_dataNascita";
  public static final String COMGE_CODICEFISCALE = "comge_codicefiscale";
  private static final String COMGE_NOME = "comge_nome";
  private static final String COMGE_COGNOME = "comge_cognome";
  private static final String COMGE_EMAIL_ADDRESS = "comge_emailAddress";
  private static final String COMGE_INDIRIZZO_RESIDENZA = "comge_indirizzoResidenza";
  private static final String COMGE_CELLULARE = "comge_cellulare";
  private static final String SHIB_SESSION_ID = "Shib-Session-ID";
  private static final boolean SPID_PREFIX = false;

  // deleghe
  public static final String COMGE_DELEGANTE_CODICEFISCALE = "comge_delegante_codicefiscale";
  private static final String COMGE_DELEGANTE_NOME = "comge_delegante_nome";
  private static final String COMGE_DELEGANTE_COGNOME = "comge_delegante_cognome";
  private static final String COMGE_DELEGA_DESCRIZIONE_TIPO = "comge_delega_descrizione_tipo";
  private static final String COMGE_DELEGA_ID = "comge_delega_id";

  // EIDAS
  private static final String COMGE_LUOGO_NASCITA = "comge_luogoNascita";
  private static final String COMGE_SPID_CODE = "comge_spidCode";
  private static final String COMGE_SESSO = "comge_sesso";

  private String login;
  private String codiceIdentificativoSpid;
  private String idAnagrafica;

  @Deprecated private Long idAnonimoComuneGenova;

  private Long ultimaVersioneInformativaPrivacy;
  private String domicilio;
  private LocalDate dataDiNascita;
  private Residente datiCittadinoResidente;
  private SchedaAnagrafica schedaAnagrafica;
  private List<ResidenteTari> tuttiConviventiTuttiNuclei;
  private List<ComponenteNucleo> nucleoFamiliareAllargato;
  private List<ComponenteNucleo> nucleoFamiliare;

  // TODO commenti
  private List<ComponenteNucleo> listaNucleoFamiliareConviventiEAutodichiarati;

  private List<UtenteServiziRistorazione> listaFigli;
  private List<Veicolo> listaVeicoliAttivi;
  private boolean isFascicoloApertoOPrivacyNonAccettata;
  private static HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurati;

  private List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaUtenteBiblioteche;
  private List<Movimento> listaMovimentiBiblioteche;

  private Long ultimaVersioneInformativaPrivacySebina;
  private boolean isSebinaPrivacyNonAccettata;
  private boolean isResidente;
  private boolean isRichiestoRefreshFigli;
  private boolean isRichiestoRefreshVeicoliAttivi;
  private List<Tessera> listaAbbonamentiAmtDelLoggato;
  private PermitsListResult listaPermessiAreaBlu;
  private boolean isInviataEmailPerRelazioneParentaleErrata;

  private Long ultimaVersioneInformativaPrivacyServiziCortesia;
  private boolean isServiziCortesiaPrivacyNonAccettata;

  private Long ultimaVersioneInformativaPrivacyServiziCedole;
  private boolean isServiziCedolePrivacyNonAccettata;

  private Long ultimaVersioneInformativaPrivacyServiziBrav;
  private boolean isServiziBravPrivacyNonAccettata;

  private Long ultimaVersioneInformativaPrivacyServiziCanoneIdrico;
  private boolean isServiziCanoneIdricoPrivacyNonAccettata;

  private String delegatoNome;
  private String delegatoCodiceFiscale;
  private String delegatoCognome;
  private String delegaDescrizioneTipo;
  private String idDelega;
  private boolean isUtenteLoginLocale;
  private boolean isUtenteLoginEidas;

  @SuppressWarnings("rawtypes")
  private Class classePagina;

  private String luogoDiNascita;
  private String spidCode;

  private boolean isBolloNonRaggiungibile;

  private boolean isBibliotecheNonRaggiungibile;

  public boolean isUtenteLoginLocale() {
    return isUtenteLoginLocale;
  }

  public void setUtenteLoginLocale(boolean isUtenteLoginLocale) {
    this.isUtenteLoginLocale = isUtenteLoginLocale;
  }

  private Utente() {
    super();
    isFascicoloApertoOPrivacyNonAccettata = false;
    isRichiestoRefreshFigli = false;
    isInviataEmailPerRelazioneParentaleErrata = false;
    isRichiestoRefreshVeicoliAttivi = true;
    isUtenteLoginLocale = false;
    isUtenteLoginEidas = false;
  }

  public Utente(String cf) {
    this();
    setCodiceFiscaleOperatore(cf);
  }

  /**
   * Create utente by user name.
   *
   * <p>utilizzato in caso di ingresso tramite maschera login con cf
   *
   * @param username the username
   * @return the utente by user name
   */
  public static Utente getUtenteByUserName(String username) {
    // Lo username dell'utente coincide con il codice fiscale
    log.debug("--- getUtenteByUserName");
    Utente utente = new Utente();
    utente.setLogin(username);
    utente.setCodiceFiscaleOperatore(username);
    utente.setTokenId("Login_+" + UUID.randomUUID().toString());
    utente.setSesso(CodiceFiscaleValidatorUtil.getSessoFromCf(username));
    utente.setNome(StringUtils.substring(username, 3, 6));
    utente.setCognome(StringUtils.substring(username, 0, 3));
    utente.setDataDiNascita(CodiceFiscaleValidatorUtil.getDataNascitaFromCf(username));
    utente.setUtenteLoginLocale(true);
    utente.setUtenteLoginEidas(!isNotLoginLocaleOrCodiceFiscaleNonValido(username));
    inizializza(utente);
    return utente;
  }

  private static boolean isNotLoginLocaleOrCodiceFiscaleNonValido(String username) {

    if (StringUtils.equalsIgnoreCase(username, "NAM")) {
      return false;
    } else {
      return CodiceFiscaleValidatorUtil.isStringaLunga16Caratteri(username);
    }
  }

  /**
   * Create utente by WebRequest.
   *
   * <p>utilizzato in caso di ingresso tramite SPID
   *
   * @param request the WebRequest
   * @return the utente by user name
   */
  public static Utente getUtenteByHeaderRequest(WebRequest request) {
    log.debug("--- getUtenteByHeaderRequest");
    String codiceFiscale = request.getHeader(COMGE_CODICEFISCALE);
    String nome = request.getHeader(COMGE_NOME);
    String cognome = request.getHeader(COMGE_COGNOME);
    String mail = request.getHeader(COMGE_EMAIL_ADDRESS);
    String domicilio = request.getHeader(COMGE_INDIRIZZO_RESIDENZA);
    String mobile = request.getHeader(COMGE_CELLULARE);
    String sessionId = request.getHeader(SHIB_SESSION_ID);
    String dataNascitaStringa = request.getHeader(COMGE_DATANASCITA);
    String idDelega = request.getHeader(COMGE_DELEGA_ID);
    String descrizioneDelegaTipo = request.getHeader(COMGE_DELEGA_DESCRIZIONE_TIPO);
    String codiceFiscaleDelegante = request.getHeader(COMGE_DELEGANTE_CODICEFISCALE);
    String nomeDelegante = request.getHeader(COMGE_DELEGANTE_NOME);
    String cognomeDelegante = request.getHeader(COMGE_DELEGANTE_COGNOME);

    String luogoDiNascitaHeader = request.getHeader(COMGE_LUOGO_NASCITA);
    String sessoHeader = request.getHeader(COMGE_SESSO);
    String spidCodeHeader = request.getHeader(COMGE_SPID_CODE);

    logHeaders(request);
    log.debug("--- codiceFiscale=" + codiceFiscale);
    log.debug("--- nome=" + nome);
    log.debug("--- cognome=" + cognome);
    log.debug("--- mail=" + mail);
    log.debug("--- domicilio=" + domicilio);
    log.debug("--- sessionId=" + sessionId);
    log.debug("--- dataNascitaStringa=" + dataNascitaStringa);
    log.debug("--- idDelega=" + idDelega);
    log.debug("--- descrizioneDelegaTipo=" + descrizioneDelegaTipo);
    log.debug("--- codiceFiscaleDelegante=" + codiceFiscaleDelegante);
    log.debug("--- nomeDelegante=" + nomeDelegante);
    log.debug("--- cognomeDelegante=" + cognomeDelegante);
    log.debug("--- luogoDiNascitaHeader=" + luogoDiNascitaHeader);
    log.debug("--- sessoHeader=" + sessoHeader);
    log.debug("--- spidCodeHeader=" + spidCodeHeader);

    Boolean isEidas = false;

    if (StringUtils.isEmpty(codiceFiscale)) {
      if (StringUtils.isNotBlank(spidCodeHeader)) {
        isEidas = true;
        codiceFiscale = spidCodeHeader;
      } else {
        throw new RestartResponseAtInterceptPageException(Page403.class);
      }
    } else {
      if (SPID_PREFIX)
        if (codiceFiscale.length() > 6) {
          codiceFiscale = codiceFiscale.substring(6);
        } else {
          codiceFiscale = "";
        }
    }

    LocalDate dataNascitaHeader =
        convertiDataNascitaSpid(dataNascitaStringa, codiceFiscale, isEidas);
    Utente utente = new Utente();

    if (org.apache.commons.lang3.StringUtils.isBlank(codiceFiscaleDelegante)) {
      utente.setLogin(codiceFiscale);
      utente.setCodiceFiscaleOperatore(codiceFiscale);
      utente.setNome(nome);
      utente.setCognome(cognome);
    } else {
      utente.setLogin(codiceFiscale);
      utente.setCodiceFiscaleOperatore(codiceFiscaleDelegante);
      utente.setNome(nomeDelegante);
      utente.setCognome(cognomeDelegante);
      utente.setDelegatoCodiceFiscale(codiceFiscale);
      utente.setDelegatoNome(nome);
      utente.setDelegatoCognome(cognome);
      utente.setIdDelega(idDelega);
      utente.setDelegaDescrizioneTipo(descrizioneDelegaTipo);
    }
    utente.setMail(mail);
    utente.setDomicilio(domicilio);
    utente.setMobile(mobile);
    utente.setTokenId(sessionId);
    utente.setDataDiNascita(dataNascitaHeader);
    utente.setLuogoDiNascita(luogoDiNascitaHeader);
    utente.setSesso(sessoHeader);
    utente.setUtenteLoginEidas(isEidas);
    // inizializza(utente);
    return utente;
  }

  public static Utente inizializzaUtenteByHeaderRequest(WebRequest request) {
    Utente utente = getUtenteByHeaderRequest(request);
    inizializza(utente);
    return utente;
  }

  private static LocalDate convertiDataNascitaSpid(
      String dataNascitaStringa, String codiceFiscale, Boolean isEidas) {

    if (StringUtils.isNotEmpty(dataNascitaStringa) && isEidas) {
      return LocalDateUtil.convertiDataStringaFallback(dataNascitaStringa);
    }

    if (StringUtils.isNotEmpty(dataNascitaStringa)) {
      try {
        return LocalDateUtil.convertiDaFormatoEuropeo(dataNascitaStringa);
      } catch (BusinessException e) {
        log.error("Errore nella conversione della data: ", e);
        return null;
      }
    } else {
      log.error("Data di nascita SPID nulla: " + dataNascitaStringa);
      if (StringUtils.length(codiceFiscale) == 16) {
        return CodiceFiscaleValidatorUtil.getDataNascitaFromCf(codiceFiscale);
      } else {
        log.error(
            "La stringa memorizzata nel codice fiscale ha una lunghezza diversa da 16 caratteri: <"
                + codiceFiscale
                + ">");
        return null;
      }
    }
  }

  private static void logHeaders(WebRequest request) {
    HttpServletRequest servletReq = (HttpServletRequest) request.getContainerRequest();
    Map<String, String> map = new HashMap<>();

    Enumeration<String> headerNames = servletReq.getHeaderNames();
    log.debug("--- ALTRI HEADERS HTTP ---");
    while (headerNames.hasMoreElements()) {
      String key = headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
      log.debug(key + "-->" + value);
    }
  }

  public static void inizializzaWidgetDaVisualizzare(Utente utente) {

    widgetConfigurati = new HashMap<>();
    try {
      log.debug("inizializzaWidgetDaVisualizzare -- INZIO");
      try {
        List<DatiVisualizzazioneSezioneWidget> listaWidget =
            ServiceLocator.getInstance().getServiziConfigurazione().getListaWidgetHomePage(utente);
        widgetConfigurati = WidgetUtil.creaMappaDaElencoWidget(listaWidget);
      } catch (NotFoundException e) {
        log.debug("NotFoundException: " + e.getLocalizedMessage());
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ClientErrorException e) {
      log.debug("errore: " + e.getLocalizedMessage());
      throw new RestartResponseAtInterceptPageException(ErrorApiPage.class);
    }
    log.debug("widgetConfigurati:" + widgetConfigurati.size());
  }

  private static void inizializzaNucleoFamiliareAllargato(Utente utente) {
    log.debug("inizializzaNucleoFamiliareAllargato -- INIZIO");
    List<ComponenteNucleo> componenteNucleo = null;
    try {
      utente.setNucleoFamiliareAllargato(new ArrayList<>());
      componenteNucleo =
          ServiceLocator.getInstance().getServizioDemografico().getNucleoFamiliareAllargato(utente);
    } catch (BusinessException | ApiException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
    utente.setNucleoFamiliareAllargato(componenteNucleo);
    log.debug("inizializzaNucleoFamiliareAllargato -- FINE");
  }

  private void inizializzaTuttiComponentiTuttiNuclei() {
    log.debug("inizializzaTuttiComponentiTuttiNuclei -- INIZIO");
    List<ResidenteTari> listResidenteTari = null;
    try {
      listResidenteTari =
          ServiceLocator.getInstance().getServizioDemografico().getTuttiConviventiTuttiNuclei(this);
    } catch (BusinessException | ApiException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
    setTuttiConviventiTuttiNuclei(listResidenteTari);
    log.debug("inizializzaTuttiComponentiTuttiNuclei -- FINE");
  }

  public void setTuttiConviventiTuttiNuclei(List<ResidenteTari> listResidenteTari) {
    tuttiConviventiTuttiNuclei = listResidenteTari;
  }

  public List<ResidenteTari> getTuttiConviventiTuttiNuclei() {
    if (tuttiConviventiTuttiNuclei != null) {
      return tuttiConviventiTuttiNuclei;
    } else {
      inizializzaTuttiComponentiTuttiNuclei();
      return tuttiConviventiTuttiNuclei;
    }
  }

  public void aggiornaStatoFigli() {
    inizializzaListaFigli(this);
    setRichiestoRefreshFigli(false);
  }

  // TODO modifica per prova non residenti
  public boolean isDeceduto() {
    if (isResidente()) {
      return (getDatiCittadinoResidente().getCpvDateOfDeath() != null
          && getDatiCittadinoResidente().getCpvDateOfBirth() != null
          && (getDatiCittadinoResidente()
                  .getCpvDateOfBirth()
                  .isBefore(getDatiCittadinoResidente().getCpvDateOfDeath())
              || getDatiCittadinoResidente()
                  .getCpvDateOfBirth()
                  .isEqual(getDatiCittadinoResidente().getCpvDateOfDeath())));
    } else {
      return false;
    }
  }

  public boolean isFascicoloAperto() {
    return isFascicoloApertoOPrivacyNonAccettata;
  }

  public void apriFascicolo(boolean apri) {
    isFascicoloApertoOPrivacyNonAccettata = apri;
  }

  private static void inizializza(Utente utente) {
    inizializzaIdAnonimoCittadino(utente);
    inizializzaPrivacyEFascicolo(utente);

    inizializzaPrivacySebina(utente);

    inizializzaListaFigli(utente);
    if (utente.isResidente) {
      inizializzaResidenza(utente);
    } else {
      Residente nonResidente = new Residente();
      nonResidente.setCpvTaxCode(utente.getCodiceFiscaleOperatore());
      nonResidente.setCpvFamilyName(utente.getCognome());
      nonResidente.setCpvGivenName(utente.getNome());
      nonResidente.setRdfsLabel(getNominativoCapoFamiglia(utente));
      utente.setDatiCittadinoResidente(nonResidente);
    }
    inizializzaWidgetDaVisualizzare(utente);
    inizializzaVeicoliAttivi(utente);
    inizializzaBiblioteche(utente);
  }

  private static String getNominativoCapoFamiglia(Utente capoFamiglia) {
    StringBuilder builder = new StringBuilder();
    return builder
        .append(capoFamiglia.getCognome())
        .append(" ")
        .append(capoFamiglia.getNome())
        .toString();
  }

  private static void inizializzaIdAnonimoCittadino(Utente utente) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      AnonimoData anonimo = new AnonimoData();
      anonimo.setCognome(utente.getCognome());
      anonimo.setNome(utente.getNome());
      anonimo.setDataNascita(utente.getDataDiNascita());
      anonimo.setSesso(utente.getSesso());
      anonimo.setCodiceBelfiore(StringUtils.substring(utente.getCodiceFiscaleOperatore(), 11, 15));
      anonimo.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
      IdCPVPerson person = instance.getApiAnonym().anonimizzaPost(anonimo);
      log.debug("isResidente: " + person.getIsCittadinoComuneGe());
      utente.setResidente(person.getIsCittadinoComuneGe());
      BigDecimal idPersonDaYaml = person.getPersonId();
      Long idPerson = idPersonDaYaml.longValue();
      CitizenResponse citizenResponse = instance.getApiFamily().getIdCittadino(idPerson);
      if (citizenResponse.getEsito() != null) {
        Long idFcitt = citizenResponse.getIdFcittCitizen();
        utente.setIdAnonimoComuneGenova(idFcitt);
      } else {
        throw new RestartResponseAtInterceptPageException(ErrorPage.class);
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (NotFoundException e) {
      log.debug("errore: " + e.getLocalizedMessage());
      throw new RestartResponseAtInterceptPageException(CittadinoNotFound.class);
    } catch (ClientErrorException e) {
      log.debug("errore: " + e.getLocalizedMessage());
      throw new RestartResponseAtInterceptPageException(ErrorApiPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  private static void inizializzaPrivacyEFascicolo(Utente utente) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_APP, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacy(response.getIdVersionePrivacy());

      if (response.getEsito() != null) {
        utente.isFascicoloApertoOPrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isFascicoloApertoOPrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaPrivacySebina(Utente utente) {
    log.debug("inizializzaPrivacySebina");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_SEBINA, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacySebina(response.getIdVersionePrivacy());
      if (response.getEsito() != null) {
        utente.isSebinaPrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isSebinaPrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaPrivacyServiziCortesia(Utente utente) {
    log.debug("inizializzaPrivacyServiziCortesia");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_SERVIZI_DI_CORTESIA, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacyServiziCortesia(response.getIdVersionePrivacy());
      if (response.getEsito() != null) {
        utente.isServiziCortesiaPrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isServiziCortesiaPrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaPrivacyServiziCedole(Utente utente) {
    log.debug("inizializzaPrivacyServiziCedole");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_CEDOLE, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacyServiziCedole(response.getIdVersionePrivacy());
      if (response.getEsito() != null) {
        utente.isServiziCedolePrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isServiziCedolePrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaPrivacyServiziBrav(Utente utente) {
    log.debug("inizializzaPrivacyServiziBrav");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_BRAV, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacyServiziBrav(response.getIdVersionePrivacy());
      if (response.getEsito() != null) {
        utente.isServiziBravPrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isServiziBravPrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaPrivacyServiziCanoneIdrico(Utente utente) {
    log.debug("inizializzaPrivacyServiziCanoneIdrico");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .verificaLetturaPrivacyCorrente(
                  BaseServiceImpl.COD_CANONEIDRICO, utente.getIdAnonimoComuneGenova());
      utente.setUltimaVersioneInformativaPrivacyServiziCanoneIdrico(
          response.getIdVersionePrivacy());
      if (response.getEsito() != null) {
        utente.isServiziCanoneIdricoPrivacyNonAccettata = response.getEsito().isEsito();
      } else {
        utente.isServiziCanoneIdricoPrivacyNonAccettata = false;
      }
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
  }

  public static void inizializzaDatiAnagrafici(Residente residente, Utente utente) {

    utente.setIdAnagrafica(residente.getCpvPersonID());
    utente.setCognome(residente.getCpvFamilyName());
    utente.setNome(residente.getCpvGivenName());
    utente.setDataDiNascita(residente.getCpvDateOfBirth());

    if (residente.getCpvHasBirthPlace() != null)
      utente.setLuogoNascita(residente.getCpvHasBirthPlace().getClvCity());

    if (utente.getDatiCittadinoResidente() == null) {
      utente.setDatiCittadinoResidente(residente);
    }
  }

  private static void inizializzaResidenza(Utente utente) {

    try {
      Residente datiResidenza =
          ServiceLocator.getInstance().getServizioDemografico().getDatiResidente(utente);
      utente.datiCittadinoResidente = datiResidenza;
      inizializzaDatiAnagrafici(datiResidenza, utente);
    } catch (BusinessException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ApiException e) {
      if (e.getResponse().getStatus() == 404 || e.getResponse().getStatus() == 204) {
        throw new RestartResponseAtInterceptPageException(CittadinoNotFound.class);
      } else {
        throw new RestartResponseAtInterceptPageException(ErrorPage.class);
      }
    }
  }

  public static void inizializzaListaFigli(Utente utente) {
    log.debug("INIZIALIZZA ListaFigli");
    try {
      utente.listaFigli =
          ServiceLocator.getInstance().getServiziRistorazione().trovaIscritti(utente);
    } catch (BusinessException | ApiException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  public static void inizializzaVeicoliAttivi(Utente utente) {
    try {
      utente.setListaVeicoliAttivi(
          ServiceLocator.getInstance().getServiziMieiMezzi().getListaVeicoli(utente));
      utente.setBolloNonRaggiungibile(false);
    } catch (BusinessException | ApiException | IOException | RuntimeException e) {
      utente.setListaVeicoliAttivi(new ArrayList<>());
      utente.setBolloNonRaggiungibile(true);
      log.error("Errore nella chiamata al Bollo: ", e);
    }
  }

  public static void inizializzaBiblioteche(Utente utente) {
    try {
      utente.setListaUtenteBiblioteche(
          ServiceLocator.getInstance()
              .getServiziBiblioteche()
              .getUtenteByDocIdentita(utente.getCodiceFiscaleOperatore()));
      utente.setBibliotecheNonRaggiungibile(false);
    } catch (BusinessException | ApiException | IOException | WebApplicationException e) {
      utente.setListaUtenteBiblioteche(new ArrayList<>());
      utente.setBibliotecheNonRaggiungibile(true);
      log.error("Errore nella chiamata a Biblioteche: ", e);
    }
  }

  public static void inizializzaScadenzeMovimentiBiblioteca(Utente utente) {
    try {
      utente.setListaMovimentiBiblioteche(
          ServiceLocator.getInstance()
              .getServiziBiblioteche()
              .getMovimentiCorrentiUtenteById(utente.getCodiceFiscaleOperatore()));
    } catch (BusinessException | ApiException | IOException | WebApplicationException e) {
      utente.setListaMovimentiBiblioteche(new ArrayList<>());
      log.error("Errore nella chiamata a Sebina: ", e);
    }
  }

  public boolean isMaggiorenne() {
    if (isResidente()) {
      return LocalDateUtil.isMaggiorenne(getDatiCittadinoResidente().getCpvDateOfBirth());
    } else {
      // TODO prova per non residenti
      return false;
    }
  }

  public String getCodiceIdentificativoSpid() {
    return codiceIdentificativoSpid;
  }

  @SuppressWarnings("unused")
  private void setCodiceIdentificativoSpid(String codiceIdentificativoSpid) {
    this.codiceIdentificativoSpid = codiceIdentificativoSpid;
  }

  public String getDomicilio() {
    return domicilio;
  }

  private void setDomicilio(String domicilio) {
    this.domicilio = domicilio;
  }

  public LocalDate getDataDiNascita() {
    if (!isResidente && !isUtenteLoginEidas) {
      dataDiNascita = CodiceFiscaleValidatorUtil.getDataNascitaFromCf(getCodiceFiscaleOperatore());
    }
    return dataDiNascita;
  }

  private void setDataDiNascita(LocalDate dataDiNascita) {
    this.dataDiNascita = dataDiNascita;
  }

  public String getIdAnagrafica() {
    return idAnagrafica;
  }

  private void setIdAnagrafica(String idAnagrafica) {
    this.idAnagrafica = idAnagrafica;
  }

  public Residente getDatiCittadinoResidente() {
    return datiCittadinoResidente;
  }

  public void setDatiCittadinoResidente(Residente datiCittadinoResidente) {
    this.datiCittadinoResidente = datiCittadinoResidente;
  }

  public SchedaAnagrafica getSchedaAnagrafica() {
    return schedaAnagrafica;
  }

  @SuppressWarnings("unused")
  private void setSchedaAnagrafica(SchedaAnagrafica schedaAnagrafica) {
    this.schedaAnagrafica = schedaAnagrafica;
  }

  public List<UtenteServiziRistorazione> getListaFigli() {
    return listaFigli;
  }

  public void setListaFigli(List<UtenteServiziRistorazione> listaFigli) {
    this.listaFigli = listaFigli;
  }

  public List<ComponenteNucleo> getNucleoFamiliareAllargato() {
    return this.inizializzaNucleoFamiliareAllargato(false);
  }

  public List<ComponenteNucleo> resetNucleoFamiliareAllargato() {
    return this.inizializzaNucleoFamiliareAllargato(true);
  }

  private List<ComponenteNucleo> inizializzaNucleoFamiliareAllargato(Boolean forceUpdate) {
    if (LabelFdCUtil.checkIfNull(nucleoFamiliareAllargato) || forceUpdate) {
      log.debug(
          "getNucleoFamiliareAllargato  nucleoFamiliareAllargato: " + nucleoFamiliareAllargato);
      inizializzaNucleoFamiliareAllargato(this);
    }
    log.debug(
        "getNucleoFamiliareAllargato  nucleoFamiliareAllargato.size: "
            + nucleoFamiliareAllargato.size());
    return nucleoFamiliareAllargato;
  }

  public void setNucleoFamiliareAllargato(List<ComponenteNucleo> nucleoFamiliareAllargato) {
    this.nucleoFamiliareAllargato = nucleoFamiliareAllargato;
  }

  public Long getUltimaVersioneInformativaPrivacy() {
    return ultimaVersioneInformativaPrivacy;
  }

  private void setUltimaVersioneInformativaPrivacy(Long ultimaVersioneInformativaPrivacy) {
    this.ultimaVersioneInformativaPrivacy = ultimaVersioneInformativaPrivacy;
  }

  public Long getIdAnonimoComuneGenova() {
    return idAnonimoComuneGenova;
  }

  private void setIdAnonimoComuneGenova(Long idAnonimoComuneGenova) {
    this.idAnonimoComuneGenova = idAnonimoComuneGenova;
  }

  public Map<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> getWidgetConfigurati() {
    return widgetConfigurati;
  }

  public List<Veicolo> getListaVeicoliAttivi() {
    return listaVeicoliAttivi;
  }

  public void setListaVeicoliAttivi(List<Veicolo> listaVeicoliAttivi) {
    this.listaVeicoliAttivi = listaVeicoliAttivi;
  }

  public PermitsListResult getListaPermessiAreaBlu() {
    return listaPermessiAreaBlu;
  }

  public void setListaPermessiAreaBlu(PermitsListResult listaPermessiAreaBlu) {
    this.listaPermessiAreaBlu = listaPermessiAreaBlu;
  }

  // Metodi supporto - interrogazioni binarie
  public boolean hasFigli() {
    return (listaFigli != null && !listaFigli.isEmpty());
  }

  public boolean hasFigliIscrittiAMensa() {
    return (getPrimoIscrittoConLinkVisibile() != null);
  }

  // Metodi supporto - retrieve dati propri
  public UtenteServiziRistorazione getPrimoIscrittoConLinkVisibile() {
    if (hasFigli()) {
      return listaFigli.stream()
          .filter(
              iscritto -> (new UtenteServiziRistorazioneHandler(iscritto)).isVisibileLinkMensa())
          .findFirst()
          .orElse(null);
    }
    return null;
  }

  public List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente>
      getListaUtenteBiblioteche() {
    if (listaUtenteBiblioteche == null) {
      return listaUtenteBiblioteche;
    } else {
      return listaUtenteBiblioteche;
    }
  }

  public void setListaUtenteBiblioteche(
      List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaUtenteBiblioteche) {
    this.listaUtenteBiblioteche = listaUtenteBiblioteche;
  }

  public List<Movimento> getListaMovimentiBiblioteche() {
    return listaMovimentiBiblioteche;
  }

  public void setListaMovimentiBiblioteche(List<Movimento> listaMovimentiBiblioteche) {
    this.listaMovimentiBiblioteche = listaMovimentiBiblioteche;
  }

  public Long getUltimaVersioneInformativaPrivacySebina() {
    return ultimaVersioneInformativaPrivacySebina;
  }

  public void setUltimaVersioneInformativaPrivacySebina(
      Long ultimaVersioneInformativaPrivacySebina) {
    this.ultimaVersioneInformativaPrivacySebina = ultimaVersioneInformativaPrivacySebina;
  }

  public boolean isResidente() {
    return isResidente;
  }

  public void setResidente(boolean isResidente) {
    this.isResidente = isResidente;
  }

  public boolean isFascicoloApertoOPrivacyNonAccettata() {
    return isFascicoloApertoOPrivacyNonAccettata;
  }

  public void setFascicoloApertoOPrivacyNonAccettata(
      boolean isFascicoloApertoOPrivacyNonAccettata) {
    this.isFascicoloApertoOPrivacyNonAccettata = isFascicoloApertoOPrivacyNonAccettata;
  }

  public boolean isSebinaPrivacyNonAccettata() {
    return isSebinaPrivacyNonAccettata;
  }

  public void setSebinaPrivacyNonAccettata(boolean isSebinaPrivacyNonAccettata) {
    this.isSebinaPrivacyNonAccettata = isSebinaPrivacyNonAccettata;
  }

  public boolean isRichiestoRefreshFigli() {
    return isRichiestoRefreshFigli;
  }

  public void setRichiestoRefreshFigli(boolean isRichiestoRefreshFigli) {
    this.isRichiestoRefreshFigli = isRichiestoRefreshFigli;
  }

  public int getCasoUtentePerAgevolazioneTariffariaTARI() {
    int iReturn = 0;
    // 1 piu di 70 anni da solo
    // 2 piu di 70 anni con almeno un convivente
    // 3 meno di 70 anni con con almeno un figlio convivente entro i 26
    // ............. anni (almeno da 1 a 4 figli)
    // 4 meno di 70 anni con figli conviventi tutti sopra i 26 anni
    if (LocalDateUtil.isMaggiore70anni(getDataDiNascita())) {
      // da solo
      if (getNucleoFamiliareAllargato() == null || getNucleoFamiliareAllargato().size() == 1) {
        iReturn = 1;
      } else {
        iReturn = 2;
      }
    } else if (getNucleoFamiliareAllargato() != null && getNucleoFamiliareAllargato().size() >= 2) {

      log.debug(
          "CP  getNucleoFamiliareAllargato().size() = " + getNucleoFamiliareAllargato().size());

      iReturn = 4;
      // verifica i figli, se ne trovo uno sotto i 26 anni => ritorno 3
      for (ComponenteNucleo componente : getNucleoFamiliareAllargato()) {

        log.debug("CP componente in under 70 is cores = " + componente.isCoResidente());

        if (componente
                .getRelazione()
                .getCpvParentType()
                .equalsIgnoreCase(CpvParentTypeEnum.FG.value())
            && LocalDateUtil.calcolaEta(componente.getDataNascita()) < 26
            && componente.isCoResidente()) {

          log.debug("CP entro in fg 26 anni");

          iReturn = 3;
          break;
        } else {
          iReturn = 5;
        }
      }
    }

    log.debug("CP ireturn = " + iReturn);

    return iReturn;
  }

  public List<ComponenteNucleo> getNucleoFamiliare() {
    return nucleoFamiliare;
  }

  public void setNucleoFamiliare(List<ComponenteNucleo> nucleoFamiliare) {
    this.nucleoFamiliare = nucleoFamiliare;
  }

  public List<ComponenteNucleo> getListaNucleoFamiliareConviventiEAutodichiarati() {
    if (LabelFdCUtil.checkIfNotNull(listaNucleoFamiliareConviventiEAutodichiarati)) {
      return listaNucleoFamiliareConviventiEAutodichiarati;
    } else {
      List<ComponenteNucleo> listaTemp = this.getNucleoFamiliareAllargato();
      listaNucleoFamiliareConviventiEAutodichiarati =
          listaTemp.stream()
              .filter(elemento -> elemento.isCoResidente())
              .collect(Collectors.toList());
      return listaNucleoFamiliareConviventiEAutodichiarati;
    }
  }

  public void setListaNucleoFamiliareConviventiEAutodichiarati(
      List<ComponenteNucleo> listaNucleoFamiliareConviventiEAutodichiarati) {
    this.listaNucleoFamiliareConviventiEAutodichiarati =
        listaNucleoFamiliareConviventiEAutodichiarati;
  }

  public List<Tessera> getListaAbbonamentiAmtDelLoggato() {
    return listaAbbonamentiAmtDelLoggato;
  }

  public void setListaAbbonamentiAmtDelLoggato(List<Tessera> listaAbbonamentiAmtDelLoggato) {
    this.listaAbbonamentiAmtDelLoggato = listaAbbonamentiAmtDelLoggato;
  }

  public void resettaListaNucleoFamiliareAllargato() {
    this.setNucleoFamiliareAllargato(null);
  }

  public ComponenteNucleo getUtenteLoggatoComeComponenteNucleo() {
    List<ComponenteNucleo> listaTemp = this.getNucleoFamiliareAllargato();
    List<ComponenteNucleo> listaUtenteLoggatoComeComponenteNucleo =
        listaTemp.stream()
            .filter(
                elemento ->
                    this.getCodiceFiscaleOperatore().equalsIgnoreCase(elemento.getCodiceFiscale()))
            .collect(Collectors.toList());
    if (listaUtenteLoggatoComeComponenteNucleo.isEmpty()) {
      return null;
    } else {
      return listaUtenteLoggatoComeComponenteNucleo.get(0);
    }
  }

  public List<ComponenteNucleo> listaFigliInNucleoAllargatoCoresidentiUnder26() {
    List<ComponenteNucleo> nucleoAllargatoUnder26 = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(this.getNucleoFamiliareAllargato())) {
      LocalDate oggi = LocalDate.now();
      nucleoAllargatoUnder26 =
          this.getNucleoFamiliareAllargato().stream()
              .filter(
                  elem ->
                      (LabelFdCUtil.checkIfNotNull(elem.getRelazione())
                              && CpvParentTypeEnum.FG
                                  .value()
                                  .equalsIgnoreCase(elem.getRelazione().getCpvParentType()))
                          && (Period.between(elem.getDataNascita(), oggi).getYears() < 26)
                          && elem.isCoResidente())
              .collect(Collectors.toList());
    }
    return nucleoAllargatoUnder26;
  }

  public List<ComponenteNucleo> listaFigliInNucleoAllargatoCoresidentiUnder18() {
    List<ComponenteNucleo> nucleoAllargatoUnder18 = new ArrayList<>();

    if (LabelFdCUtil.checkIfNotNull(this.getNucleoFamiliareAllargato())) {
      LocalDate oggi = LocalDate.now();

      nucleoAllargatoUnder18 =
          this.getNucleoFamiliareAllargato().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && (LabelFdCUtil.checkIfNotNull(elem.getRelazione())
                              && CpvParentTypeEnum.FG
                                  .value()
                                  .equalsIgnoreCase(elem.getRelazione().getCpvParentType()))
                          && (LabelFdCUtil.checkIfNotNull(elem.getDatiCittadino())
                              && LabelFdCUtil.checkIfNotNull(
                                  elem.getDatiCittadino().getCpvDateOfBirth())
                              && Period.between(elem.getDatiCittadino().getCpvDateOfBirth(), oggi)
                                      .getYears()
                                  < 18)
                          && elem.isCoResidente())
              .collect(Collectors.toList());
    }
    return nucleoAllargatoUnder18;
  }

  @Override
  public String getSesso() {
    String siglaSesso = super.getSesso();
    if (StringUtils.isNotEmpty(siglaSesso)) {
      return siglaSesso;
    } else {
      return CodiceFiscaleValidatorUtil.getSessoFromCf(getCodiceFiscaleOperatore());
    }
  }

  public List<ComponenteNucleo> listaNucleoAllargatoCoresidentiOver18() {
    List<ComponenteNucleo> nucleoAllargatoOver18 = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(this.getNucleoFamiliareAllargato())) {
      LocalDate oggi = LocalDate.now();
      // nucleoAllargatoOver18 =
      // this.getNucleoFamiliareAllargato().stream()
      // .filter(elem -> LabelFdCUtil.checkIfNotNull(elem)
      // && (Period.between(elem.getDataNascita(), oggi).getYears() > 18)
      // &&
      // elem.isCoResidente())
      // .collect(Collectors.toList());

      nucleoAllargatoOver18 =
          this.getNucleoFamiliareAllargato().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && (LabelFdCUtil.checkIfNotNull(elem.getDatiCittadino())
                              && LabelFdCUtil.checkIfNotNull(
                                  elem.getDatiCittadino().getCpvDateOfBirth())
                              && Period.between(elem.getDatiCittadino().getCpvDateOfBirth(), oggi)
                                      .getYears()
                                  > 18)
                          && elem.isCoResidente())
              .collect(Collectors.toList());
    }
    log.debug("CP lista over 18 = " + nucleoAllargatoOver18.size());
    return nucleoAllargatoOver18;
  }

  public boolean isInviataEmailPerRelazioneParentaleErrata() {
    return isInviataEmailPerRelazioneParentaleErrata;
  }

  public void setInviataEmailPerRelazioneParentaleErrata(
      boolean isInviataEmailPerRelazioneParentaleErrata) {
    this.isInviataEmailPerRelazioneParentaleErrata = isInviataEmailPerRelazioneParentaleErrata;
  }

  public Long getUltimaVersioneInformativaPrivacyServiziCortesia() {
    return ultimaVersioneInformativaPrivacyServiziCortesia;
  }

  public void setUltimaVersioneInformativaPrivacyServiziCortesia(
      Long ultimaVersioneInformativaPrivacyServiziCortesia) {
    this.ultimaVersioneInformativaPrivacyServiziCortesia =
        ultimaVersioneInformativaPrivacyServiziCortesia;
  }

  public Long getUltimaVersioneInformativaPrivacyServiziCedole() {
    return ultimaVersioneInformativaPrivacyServiziCedole;
  }

  public void setUltimaVersioneInformativaPrivacyServiziCedole(
      Long ultimaVersioneInformativaPrivacyServiziCedole) {
    this.ultimaVersioneInformativaPrivacyServiziCedole =
        ultimaVersioneInformativaPrivacyServiziCedole;
  }

  public Long getUltimaVersioneInformativaPrivacyServiziBrav() {
    return ultimaVersioneInformativaPrivacyServiziBrav;
  }

  public void setUltimaVersioneInformativaPrivacyServiziBrav(
      Long ultimaVersioneInformativaPrivacyServiziBrav) {
    this.ultimaVersioneInformativaPrivacyServiziBrav = ultimaVersioneInformativaPrivacyServiziBrav;
  }

  public boolean isServiziBravPrivacyNonAccettata() {
    return isServiziBravPrivacyNonAccettata;
  }

  public void setServiziBravPrivacyNonAccettata(boolean isServiziBravPrivacyNonAccettata) {
    this.isServiziBravPrivacyNonAccettata = isServiziBravPrivacyNonAccettata;
  }

  public Long getUltimaVersioneInformativaPrivacyServiziCanoneIdrico() {
    return ultimaVersioneInformativaPrivacyServiziCanoneIdrico;
  }

  public void setUltimaVersioneInformativaPrivacyServiziCanoneIdrico(
      Long ultimaVersioneInformativaPrivacyServiziCanoneIdrico) {
    this.ultimaVersioneInformativaPrivacyServiziCanoneIdrico =
        ultimaVersioneInformativaPrivacyServiziCanoneIdrico;
  }

  public boolean isServiziCanoneIdricoPrivacyNonAccettata() {
    return isServiziCanoneIdricoPrivacyNonAccettata;
  }

  public void setServiziCanoneIdricoPrivacyNonAccettata(
      boolean isServiziCanoneIdricoPrivacyNonAccettata) {
    this.isServiziCanoneIdricoPrivacyNonAccettata = isServiziCanoneIdricoPrivacyNonAccettata;
  }

  public boolean isServiziCortesiaPrivacyNonAccettata() {
    return isServiziCortesiaPrivacyNonAccettata;
  }

  public void setServiziCortesiaPrivacyNonAccettata(boolean isServiziCortesiaPrivacyNonAccettata) {
    this.isServiziCortesiaPrivacyNonAccettata = isServiziCortesiaPrivacyNonAccettata;
  }

  public boolean isServiziCedolePrivacyNonAccettata() {
    return isServiziCedolePrivacyNonAccettata;
  }

  public void setServiziCedolePrivacyNonAccettata(boolean isServiziCedolePrivacyNonAccettata) {
    this.isServiziCedolePrivacyNonAccettata = isServiziCedolePrivacyNonAccettata;
  }

  public boolean isRichiestoRefreshVeicoliAttivi() {
    return isRichiestoRefreshVeicoliAttivi;
  }

  public void setRichiestoRefreshVeicoliAttivi(boolean isRichiestoRefreshVeicoliAttivi) {
    this.isRichiestoRefreshVeicoliAttivi = isRichiestoRefreshVeicoliAttivi;
  }

  public String getDelegatoNome() {
    return delegatoNome;
  }

  public void setDelegatoNome(String delegatoNome) {
    this.delegatoNome = delegatoNome;
  }

  public String getDelegatoCodiceFiscale() {
    return delegatoCodiceFiscale;
  }

  public void setDelegatoCodiceFiscale(String delegatoCodiceFiscale) {
    this.delegatoCodiceFiscale = delegatoCodiceFiscale;
  }

  public String getDelegatoCognome() {
    return delegatoCognome;
  }

  public void setDelegatoCognome(String delegatoCognome) {
    this.delegatoCognome = delegatoCognome;
  }

  public String getDelegaDescrizioneTipo() {
    return delegaDescrizioneTipo;
  }

  public void setDelegaDescrizioneTipo(String delegaDescrizioneTipo) {
    this.delegaDescrizioneTipo = delegaDescrizioneTipo;
  }

  public boolean isUtenteDelegato() {
    return org.apache.commons.lang3.StringUtils.isNotBlank(delegatoCodiceFiscale);
  }

  public String getIdDelega() {
    return idDelega;
  }

  public void setIdDelega(String idDelega) {
    this.idDelega = idDelega;
  }

  @Override
  public String toString() {
    return "Utente [login="
        + login
        + ", codiceIdentificativoSpid="
        + codiceIdentificativoSpid
        + ", idAnagrafica="
        + idAnagrafica
        + ", idAnonimoComuneGenova="
        + idAnonimoComuneGenova
        + ", ultimaVersioneInformativaPrivacy="
        + ultimaVersioneInformativaPrivacy
        + ", domicilio="
        + domicilio
        + ", dataDiNascita="
        + dataDiNascita
        + ", datiCittadinoResidente="
        + datiCittadinoResidente
        + ", schedaAnagrafica="
        + schedaAnagrafica
        + ", tuttiConviventiTuttiNuclei="
        + tuttiConviventiTuttiNuclei
        + ", nucleoFamiliareAllargato="
        + nucleoFamiliareAllargato
        + ", nucleoFamiliare="
        + nucleoFamiliare
        + ", listaNucleoFamiliareConviventiEAutodichiarati="
        + listaNucleoFamiliareConviventiEAutodichiarati
        + ", listaFigli="
        + listaFigli
        + ", listaVeicoliAttivi="
        + listaVeicoliAttivi
        + ", isFascicoloApertoOPrivacyNonAccettata="
        + isFascicoloApertoOPrivacyNonAccettata
        + ", listaUtenteBiblioteche="
        + listaUtenteBiblioteche
        + ", listaMovimentiBiblioteche="
        + listaMovimentiBiblioteche
        + ", ultimaVersioneInformativaPrivacySebina="
        + ultimaVersioneInformativaPrivacySebina
        + ", isSebinaPrivacyNonAccettata="
        + isSebinaPrivacyNonAccettata
        + ", isResidente="
        + isResidente
        + ", isRichiestoRefreshFigli="
        + isRichiestoRefreshFigli
        + ", isRichiestoRefreshVeicoliAttivi="
        + isRichiestoRefreshVeicoliAttivi
        + ", listaAbbonamentiAmtDelLoggato="
        + listaAbbonamentiAmtDelLoggato
        + ", listaPermessiAreaBlu="
        + listaPermessiAreaBlu
        + ", isInviataEmailPerRelazioneParentaleErrata="
        + isInviataEmailPerRelazioneParentaleErrata
        + ", ultimaVersioneInformativaPrivacyServiziCortesia="
        + ultimaVersioneInformativaPrivacyServiziCortesia
        + ", isServiziCortesiaPrivacyNonAccettata="
        + isServiziCortesiaPrivacyNonAccettata
        + ", ultimaVersioneInformativaPrivacyServiziCedole="
        + ultimaVersioneInformativaPrivacyServiziCedole
        + ", isServiziCedolePrivacyNonAccettata="
        + isServiziCedolePrivacyNonAccettata
        + ", delegatoNome="
        + delegatoNome
        + ", delegatoCodiceFiscale="
        + delegatoCodiceFiscale
        + ", delegatoCognome="
        + delegatoCognome
        + ", delegaDescrizioneTipo="
        + delegaDescrizioneTipo
        + ", idDelega="
        + idDelega
        + ", isUtenteLoginLocale="
        + isUtenteLoginLocale
        + ", getRuoliIAM()="
        + getRuoliIAM()
        + ", getListaRuoliUtente()="
        + getListaRuoliUtente()
        + ", getCodiceFiscaleOperatore()="
        + getCodiceFiscaleOperatore()
        + ", getNome()="
        + getNome()
        + ", getState()="
        + getState()
        + ", getMail()="
        + getMail()
        + ", getCodEnte()="
        + getCodEnte()
        + ", getCodTipoEnte()="
        + getCodTipoEnte()
        + ", getCognome()="
        + getCognome()
        + ", getTipoOperatore()="
        + getTipoOperatore()
        + ", getRuoloAttivo()="
        + getRuoloAttivo()
        + ", getLogin()="
        + getLogin()
        + ", getTokenId()="
        + getTokenId()
        + ", getDataNascita()="
        + getDataNascita()
        + ", getLuogoNascita()="
        + getLuogoNascita()
        + ", getProvinciaNascita()="
        + getProvinciaNascita()
        + ", getMobile()="
        + getMobile()
        + ", getPec()="
        + getPec()
        + ", getIdpSorgente()="
        + getIdpSorgente()
        + ", getIpUtente()="
        + getIpUtente()
        + ", getSessionIdIAM()="
        + getSessionIdIAM()
        + ", toString()="
        + super.toString()
        + ", getUltimaMod()="
        + getUltimaMod()
        + ", getClass()="
        + getClass()
        + ", hashCode()="
        + hashCode()
        + "]";
  }

  @SuppressWarnings("rawtypes")
  public Class getClassePagina() {
    return classePagina;
  }

  @SuppressWarnings("rawtypes")
  public void setClassePagina(Class classePagina) {
    this.classePagina = classePagina;
  }

  public String getLuogoDiNascita() {
    return luogoDiNascita;
  }

  public void setLuogoDiNascita(String luogoDiNascita) {
    this.luogoDiNascita = luogoDiNascita;
  }

  public String getSpidCode() {
    return spidCode;
  }

  public void setSpidCode(String spidCode) {
    this.spidCode = spidCode;
  }

  public boolean isUtenteLoginEidas() {
    if (StringUtils.equalsIgnoreCase(login, "NAM")
        || StringUtils.equalsIgnoreCase(getCodiceFiscaleOperatore(), "NAM")) {
      log.debug("login: " + login);
      return false;
    } else {
      return isUtenteLoginEidas;
    }
  }

  public void setUtenteLoginEidas(boolean isUtenteLoginEidas) {
    this.isUtenteLoginEidas = isUtenteLoginEidas;
  }

  public boolean isBolloNonRaggiungibile() {
    return isBolloNonRaggiungibile;
  }

  public void setBolloNonRaggiungibile(boolean isBolloNonRaggiungibile) {
    this.isBolloNonRaggiungibile = isBolloNonRaggiungibile;
  }

  public boolean isBibliotecheNonRaggiungibile() {
    return isBibliotecheNonRaggiungibile;
  }

  public void setBibliotecheNonRaggiungibile(boolean isBibliotecheNonRaggiungibile) {
    this.isBibliotecheNonRaggiungibile = isBibliotecheNonRaggiungibile;
  }
}
