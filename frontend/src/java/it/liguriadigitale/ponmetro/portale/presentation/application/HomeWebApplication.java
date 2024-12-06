package it.liguriadigitale.ponmetro.portale.presentation.application;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.less.BootstrapLess;
import de.agilecoders.wicket.webjars.WicketWebjars;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.application.resource.LiguriaDigitaleBootstrapSettings;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.auth.FdcSimplePageAuthorizationStrategy;
import it.liguriadigitale.ponmetro.portale.presentation.application.resource.DbResourceLoader;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.account.DatiSpidPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.RiepilogoBibliotechePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.breadcrumb.Breadcrumbs;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.notifiche.ConfigurazioneNotifichePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo.RiepilogoPersonalizzazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.MieiDatiNonResidentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.RiepilogoMieiDatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziConStatusPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.demo.ErrorDemoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIoGenitorePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.GloboPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.GloboRicercaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.headers.HeadersPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.BaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.RedirectBaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.abito.IoAbitoRiepilogoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.cerco.IoCercoRiepilogoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.StatusPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LogOutFromShibboletPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LogOutPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginDemoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.MessaggiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.PagamentiFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.PagamentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.IMieiMezziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.RiepilogoMiMuovoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.propongo.riepilogo.RiepilogoPropongoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.ScadenzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazioni.riepilogo.RiepilogoSegnalazioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.RiepilogoContribuentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo.RiepilogoIoSostenibilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard3AutocertificazioneNonResidentePage;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.core.request.mapper.HomePageMapper;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.settings.ExceptionSettings;
import org.apache.wicket.util.time.Duration;

public class HomeWebApplication extends AuthenticatedWebApplication {

  private static final String PATH_SEPARATOR = "/";
  public static String ID_SUFFIX_PANEL = "Panel";
  public static String ID_SUFFIX_PANEL_WITH_DOT = ID_SUFFIX_PANEL + ".";

  public static String ID_HOME = "homepage/home";
  public static String ID_MIMUOVO_OFHOME = "ioMiMuovo";
  public static String ID_VERBALI_OFMIMUOVO_OFHOME = "imieiverbali";
  public static String ID_DETTAGLIO_OFVERBALI_OFMIMUOVO_OFHOME = "dettaglioverbali";
  public static String ID_CREAISTANZAPL_OFDETTAGLIO_OFVERBALI_OFMIMUOVO_OFHOME = "creaIstanzaPL";
  public static String ID_INTEGRAOCONCLUDIISTANZAPL_OFDETTAGLIO_OFVERBALI_OFMIMUOVO_OFHOME =
      "integraOConcludiIstanzaPL";

  public static String ID_TRIBUTI_OFHOME = "tributi";
  public static String ID_QUADROTRIBUTARIO_OFTRIBUTI_OFHOME = "quadroTributario";
  public static String ID_DETTAGLIO_OFQUADROTRIBUTARIO_OFTRIBUTI_OFHOME =
      "dettaglioQuadroTributario";
  public static String ID_SCADENZEEVERSATO_OFTRIBUTI_OFHOME = "scadenzeEVersato";
  public static String ID_TRIBSCADENZE_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME = "tributiScadenze";
  public static String ID_DETTAGLIOACCERTATO_OFTRIBSCADENZE_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME =
      "dettaglioAccertatoTributiScadenze";
  public static String ID_TRIBVERSATO_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME = "tributiVersato";
  public static String ID_DETTAGLIO_OFTRIBVERSATO_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME =
      "dettaglioTributiVersato";
  public static String ID_PROPRIETAEUTENZE_OFTRIBUTI_OFHOME = "proprietaEUtenze";
  public static String ID_DETTAGLIO_OFPROPRIETAEUTENZE_OFTRIBUTI_OFHOME =
      "dettaglioProprietaEUtenze";

  private String userName;
  public static CONTEXT_PATH contextPath;
  protected Log log = LogFactory.getLog(getClass());

  /**
   * prevent wicket from launching a java application window on the desktop <br>
   * once someone uses awt-specific classes java will automatically do so and allocate a window
   * unless you tell java to run in 'headless-mode'
   */
  static {
    System.setProperty("java.awt.headless", "true");
    contextPath = CONTEXT_PATH.NORMALE;
  }

  /** Constructor. */
  public HomeWebApplication() {}

  /**
   * @see org.apache.wicket.protocol.http.WebApplication#init()
   */
  @Override
  protected void init() {
    super.init();
    getResourceSettings().setDefaultCacheDuration(Duration.NONE);
    getResourceSettings().getStringResourceLoaders().add(new DbResourceLoader());
    getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
    getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
    // (de)activate debug mode
    boolean isDebug;
    switch (Constants.DEPLOY) {
      case ESERCIZIO:
        isDebug = false;
        break;
      case TEST_LOGIN:
        isDebug = false;
        break;
      case TEST_IAM:
        isDebug = false;
        break;
      case SVILUPPO:
        isDebug = true;
        break;
      case INTEGRAZIONE:
        isDebug = true;
        break;
      case MANUTENZIONE:
        isDebug = true;
        break;
      case DEMO:
        isDebug = false;
        break;
      default:
        isDebug = false;
        break;
    }
    impostaSettaggiDebug(isDebug);
    //
    configureBootstrap();
    // sostituisce i riferimenti di default di Wicket a jQuery
    /*
     * getJavaScriptLibrarySettings() .setJQueryReference(new
     * WebjarsJavaScriptResourceReference("jquery/current/jquery.min.js"));
     */
    //
    montaPagineStatiche();
    // Gestione degli errori estesa
    getApplicationSettings().setUploadProgressUpdatesEnabled(false);

    if (LayoutBasePage.ambienteDemo()) {
      getApplicationSettings().setInternalErrorPage(ErrorDemoPage.class);
    } else {
      getApplicationSettings().setInternalErrorPage(ErrorPage.class);
    }

    getPageSettings().setRecreateBookmarkablePagesAfterExpiry(true);
    // forza il caricamento dei JS nel footer della pagina
    setHeaderResponseDecorator(
        response -> {
          return new ResourceAggregator(
              new JavaScriptFilteredIntoFooterHeaderResponse(response, "footer-container"));
        });
    // Auth strategy
    FdcSimplePageAuthorizationStrategy authorizationStrategy =
        new FdcSimplePageAuthorizationStrategy(
            LayoutBasePage.class, UtenteNonAutorizzatoPage.class);
    getSecuritySettings().setAuthorizationStrategy(authorizationStrategy);
  }

  private void montaPagineStatiche() {
    montaTutteLePagine(contextPath);

    // Home e pagine di riepilogo
    new HomePageMapper(BaseLandingPage.class);
    this.mountPage("/homepage/home", RedirectBaseLandingPage.class);
    this.mountPage(getPathFunzioniBase("/ioAbito"), IoAbitoRiepilogoPage.class);
    this.mountPage(getPathFunzioniBase("/ioRichiedo"), IoCercoRiepilogoPage.class);
    this.mountPage(getPathFunzioniBase("/bambiniascuola"), RiepilogoIscrizioniPage.class);
    this.mountPage(getPathFunzioniBase("/ioGenitore"), RiepilogoIoGenitorePage.class);
    this.mountPage(getPathFunzioniBase("/ioContribuente"), RiepilogoContribuentePage.class);
    this.mountPage(
        getPathFunzioniBase(PATH_SEPARATOR + ID_MIMUOVO_OFHOME), RiepilogoMiMuovoPage.class);
    this.mountPage(getPathFunzioniBase("/imieimezzi"), IMieiMezziPage.class);
    this.mountPage(getPathFunzioniBase("/imieidati"), RiepilogoMieiDatiPage.class);
    this.mountPage(getPathFunzioniBase("/mieiDatiNonResidente"), MieiDatiNonResidentiPage.class);
    this.mountPage(getPathFunzioniBase("/ioSostenibile"), RiepilogoIoSostenibilePage.class);
    this.mountPage(
        getPathFunzioniBase(PATH_SEPARATOR + ID_TRIBUTI_OFHOME), RiepilogoContribuentePage.class);
    this.mountPage(getPathFunzioniBase("/ioLeggo"), RiepilogoBibliotechePage.class);
    this.mountPage(
        getPathFunzioniBase("/personalizzazioneFascicolo"), RiepilogoPersonalizzazionePage.class);
    this.mountPage(getPathFunzioniBase("/ioSegnalo"), RiepilogoSegnalazioniPage.class);
    this.mountPage(getPathFunzioniBase("/ioPropongo"), RiepilogoPropongoPage.class);

    // Privacy
    this.mountPage(getPathFunzioniBase("/privacy"), Wizard1PrivacyPage.class);
    this.mountPage(getPathFunzioniBase("/autocertificazione"), Wizard2AutocertificazionePage.class);
    this.mountPage(
        getPathFunzioniBase("/autocertificazioneNonResidente"),
        Wizard3AutocertificazioneNonResidentePage.class);

    // pagine non presenti nel db
    this.mountPage(getPathFunzioniBase("/globoRicerca"), GloboRicercaPage.class);
    this.mountPage(
        getPathFunzioniBase("/globo/#{ID_FUNZIONE}/#{ID_PROCEDIMENTO}"), GloboPage.class);
    this.mountPage(
        getPathFunzioniBase("/menu/#{ID_SEZIONE}/#{NOME_SEZIONE}"),
        MenuRiepilogoDinamicoPage.class);
    this.mountPage(getPathFunzioniBase("/scadenze"), ScadenzePage.class);
    this.mountPage(getPathFunzioniBase("/messaggi"), MessaggiPage.class);
    this.mountPage(getPathFunzioniBase("/autocertificazione"), Wizard2AutocertificazionePage.class);
    this.mountPage(getPathFunzioniBase("/config"), ConfigurazioneNotifichePage.class);
    this.mountPage("/status", StatusPage.class);
    // this.mountPage(getPathFunzioniBase("/pagamentoOk"),
    // PagoPaOkPage.class);
    // this.mountPage(getPathFunzioniBase("/pagamentoKo"),
    // PagoPaKoPage.class);
    // this.mountPage(getPathFunzioniBase("/pagamentoPagoPaMIPOk"),
    // PagamentiPagoPaMipOKPage.class);
    // this.mountPage(getPathFunzioniBase("/pagamentoPagoPaMIPKo"),
    // PagamentiPagoPaMipKOPage.class);
    this.mountPage(getPathFunzioniBase("/ilmioprofilo"), DatiSpidPage.class);
    this.mountPage(getPathFunzioniBase("/pagamentiPagoPa"), PagamentiPage.class);
    this.mountPage(getPathFunzioniBase("/ricercaDebitiServizio"), PagamentiFormPage.class);
    this.mountPage(getPathFunzioniBase("/pagoPagoPa"), PagamentoOnLinePage.class);
    this.mountPage(getPathFunzioniBase("/404"), Page404.class);
    this.mountPage(getPathFunzioniBase("/erroreServizio"), ErroreServiziPage.class);
    this.mountPage(getPathFunzioniBase("/erroreServizi"), ErroreServiziConStatusPage.class);
    this.mountPage(getPathFunzioniBase("/erroreGenerico"), UtenteNonAutorizzatoPage.class);
    this.mountPage(getPathFunzioniBase("/erroreInterno"), ErrorPage.class);
    this.mountPage(getPathFunzioniBase("/headers"), HeadersPage.class);
    // modifiche per versione logout con Shibbolet
    // che chiama LogOutFromShibbolet automaticamente dopo chiamata a logout
    // da Shibbolet
    // frr scommento per prove:
    this.mountPage("/logoutshibbolet", LogOutFromShibboletPage.class);
    // frr scommento per prove:
    this.mountPage("/logout", LogOutPage.class);
  }

  private String getPathFunzioniBase(String pathFunzione) {
    return PATH_SEPARATOR + FUNZIONI_PATH.NON_DELEGABILI.getPath() + pathFunzione;
  }

  @SuppressWarnings("unused")
  private <T extends Page> void mountAndBreadcrumb(String id, String idParent, Class<T> pageClass) {
    this.mountPage(PATH_SEPARATOR + id, pageClass);
    breadcrumb(id, idParent, pageClass);
  }

  private <T extends Page> void breadcrumb(String id, String idParent, Class<T> pageClass) {
    Breadcrumbs.addElement(id, idParent, pageClass);
  }

  /**
   * Imposta l'applicazione per il massimo livello di debug
   *
   * @param inDebug
   */
  private void impostaSettaggiDebug(final boolean inDebug) {
    getDebugSettings().setAjaxDebugModeEnabled(false);
    getDebugSettings().setComponentUseCheck(inDebug);
    getDebugSettings().setDevelopmentUtilitiesEnabled(inDebug);
    getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(inDebug);
    getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(inDebug);
    getDebugSettings().setOutputMarkupContainerClassName(inDebug);

    if (!inDebug) {
      // ATTENZIONE:
      // per ragioni di debug cripto le url solo in produzione
      // modificare per fare test CQ realistici
      setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
      getExceptionSettings()
          .setUnexpectedExceptionDisplay(ExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
    }
  }

  /** Configura estensione Wicket-Bootstrap */
  private void configureBootstrap() {
    WicketWebjars.install(this);
    final IBootstrapSettings settings = new LiguriaDigitaleBootstrapSettings();
    settings.useCdnResources(false);
    settings.setAutoAppendResources(true);
    settings.setJsResourceFilterName("footer-container");
    settings.setUpdateSecurityManager(true);
    /*
     * settings.setJsResourceReference( new
     * ContextRelativeResourceReference(
     * "bootstrap-italia/js/bootstrap-italia.bundle.min.js"));
     * settings.setCssResourceReference( new
     * ContextRelativeResourceReference(
     * "bootstrap-italia/css/bootstrap-italia.min.css"));
     */
    Bootstrap.install(this, settings);
    BootstrapLess.install(this);
  }

  @Override
  public Class<? extends Page> getHomePage() {

    switch (Constants.DEPLOY) {
      case ESERCIZIO:
        return checkUserInHeaderRequest();
      case TEST_IAM:
        return checkUserInHeaderRequest();
      case TEST_LOGIN:
        return LoginPage.class;
      case SVILUPPO:
        return LoginPage.class;
      case INTEGRAZIONE:
        return LoginPage.class;
      case MANUTENZIONE:
        return Page404.class;
      case DEMO:
        return LoginDemoPage.class;
      default:
        return checkUserInHeaderRequest();
    }
  }

  private Class<? extends Page> checkUserInHeaderRequest() {
    userName = retrieveUserByHeaderRequest();
    if (userName != null) return RedirectBaseLandingPage.class;
    else return ErrorPage.class;
  }

  @Override
  public WebSession newSession(Request request, Response reponse) {
    return new LoginInSession(request);
  }

  private String retrieveUserByHeaderRequest() {
    RequestCycle requestCycle = RequestCycle.get();
    WebRequest request = (WebRequest) requestCycle.getRequest();
    String userName = request.getHeader("comge_codicefiscale");
    return userName;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass() {
    // Integrazione IAM
    switch (Constants.DEPLOY) {
      case ESERCIZIO:
        return Page403.class;
      case TEST_LOGIN:
        return LoginPage.class;
      case TEST_IAM:
        return Page403.class;
      case SVILUPPO:
        return LoginPage.class;
      case INTEGRAZIONE:
        return LoginPage.class;
      case DEMO:
        return LoginDemoPage.class;

      default:
        return Page403.class;
    }
  }

  @Override
  protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
    return LoginInSession.class;
  }

  private void montaTutteLePagine(CONTEXT_PATH contextPath) {
    try {
      List<FunzioniDisponibili> listaFunzioni =
          ServiceLocator.getInstance().getServiziConfigurazione().getFunzioniAbilitate();
      List<String> listaPageClassDelegabili =
          BreadcrumbFdCUtil.getInstance().creaListaPageClassDelegabili(listaFunzioni);
      List<SottopagineDisponibili> listaSottoPagine =
          ServiceLocator.getInstance().getServiziHomePage().getElencoSottoPagine();
      montaFunzioni(contextPath, listaFunzioni, listaPageClassDelegabili);
      montaFunzioniFiglie(contextPath, listaFunzioni);
      montaPagine(contextPath, listaSottoPagine);
    } catch (BusinessException e) {
      log.error("Errore", e);
    }
  }

  private void montaFunzioniFiglie(
      CONTEXT_PATH contextPath, List<FunzioniDisponibili> listaFunzioni) {
    // ciclo su CFG_T_CAT_FUNZ
    // where ID_FUNZ_PADRE IS NOT NULL
    for (FunzioniDisponibili funzione : listaFunzioni) {
      if (StringUtils.isNotBlank(funzione.getIdFunzPadre())) {
        String mountedPath =
            ricavaMountedPathDaClassePaginaPadre(
                funzione.getIdPagina(), funzione.getClassePaginaStdPadre());
        BreadcrumbFdCUtil.getInstance().creaBreadcrumb(funzione, mountedPath, contextPath);
        log.error(
            "funzione.getPath()="
                + funzione.getPath()
                + " funzione.getClassePaginaStdPadre()="
                + funzione.getClassePaginaStdPadre());
        log.error("mountedPath=" + mountedPath);
        montaPagina(funzione.getClassePaginaStd(), mountedPath);
      }
    }
  }

  private void montaPagine(
      CONTEXT_PATH contextPath, List<SottopagineDisponibili> listaSottoPagine) {
    // ciclo su CFG_T_CAT_FUNZ_PAGINE
    log.debug("listaSottoPagine.size=" + listaSottoPagine.size());
    for (SottopagineDisponibili pagina : listaSottoPagine) {
      String classePaginaStdPadre = pagina.getClassePaginaStdPadre();
      log.debug("classePaginaStdPadre=" + classePaginaStdPadre);
      String mountedPath =
          ricavaMountedPathDaClassePaginaPadre(pagina.getNomePaginaNelPath(), classePaginaStdPadre);
      FunzioniDisponibili funzione = creaFunzioneDaPagina(pagina);
      BreadcrumbFdCUtil.getInstance().creaBreadcrumb(funzione, mountedPath, contextPath);
      montaPagina(pagina.getClassePaginaStd(), mountedPath);
    }
  }

  private void montaFunzioni(
      CONTEXT_PATH contextPath,
      List<FunzioniDisponibili> listaFunzioni,
      List<String> listaPageClassDelegabili) {
    // ciclo su CFG_T_CAT_FUNZ
    // where ID_FUNZ_PADRE IS NULL
    log.debug("listaFunzioni.size=" + listaFunzioni.size());
    for (FunzioniDisponibili funzione : listaFunzioni) {
      if (StringUtils.isBlank(funzione.getIdFunzPadre())) {
        creaBreadCrumbAndMountFunzioni(contextPath, listaPageClassDelegabili, funzione);
      }
    }
  }

  private FunzioniDisponibili creaFunzioneDaPagina(SottopagineDisponibili pagina) {
    FunzioniDisponibili funzione = new FunzioniDisponibili();
    funzione.setDescrizioneFunz(pagina.getNomePaginaNelPath());
    funzione.setClassePaginaStdPadre(pagina.getClassePaginaStdPadre());
    funzione.setClassePaginaStd(pagina.getClassePaginaStd());
    return funzione;
  }

  private String ricavaMountedPathDaClassePaginaPadre(
      String nomePaginaNelPath, String classePaginaStdPadre) {
    BreadcrumbFdC breadcrumb = MapBreadcrumbsFdc.getBreadcrumbCorrente(classePaginaStdPadre);
    if (breadcrumb != null) {
      String mountedPath = breadcrumb.getMountedPath() + PATH_SEPARATOR + nomePaginaNelPath;
      log.debug("mountedPathPagina=" + mountedPath);
      return mountedPath;
    } else {
      return "";
    }
  }

  private void creaBreadCrumbAndMountFunzioni(
      CONTEXT_PATH contextPath,
      List<String> listaPageClassDelegabili,
      FunzioniDisponibili funzione) {
    String classePaginaStd = funzione.getClassePaginaStd();
    boolean isDelegabile = listaPageClassDelegabili.contains(classePaginaStd);
    log.error("funzione.getClassePaginaStd()=" + classePaginaStd + " isDelegabile=" + isDelegabile);
    String mountedPath = "";
    log.error("funzione.getIdFunzPadre()=" + funzione.getIdFunzPadre());
    mountedPath =
        BreadcrumbFdCUtil.getInstance().ricavaMountedPath(funzione, contextPath, isDelegabile);
    log.error("mountedPath=" + mountedPath);
    BreadcrumbFdCUtil.getInstance().creaBreadcrumb(funzione, mountedPath, contextPath);
    montaPagina(classePaginaStd, mountedPath);
  }

  @SuppressWarnings("unchecked")
  private void montaPagina(String classePaginaStd, String mountedPath) {
    Class<Page> classePagina =
        (Class<Page>) PageClassFinder.findClassByNameFunction(classePaginaStd);
    if (!classePagina.equals(Page404.class) && StringUtils.isNotBlank(mountedPath)) {
      this.mountPage(mountedPath, classePagina);
      log.error(classePaginaStd + " montata: " + mountedPath);
    }
  }

  @Override
  protected WebResponse newWebResponse(
      final WebRequest webRequest, final HttpServletResponse httpServletResponse) {
    return new ServletWebResponse((ServletWebRequest) webRequest, httpServletResponse) {

      /*
       * faccio override della url per togliere JSESSIONID vedi:
       * "https://cwiki.apache.org/confluence/display/WICKET/SEO+-+Search+Engine+Optimization"
       *
       */

      @Override
      public String encodeURL(CharSequence url) {
        return url.toString();
      }

      @Override
      public String encodeRedirectURL(CharSequence url) {
        return url.toString();
      }
    };
  }
}
