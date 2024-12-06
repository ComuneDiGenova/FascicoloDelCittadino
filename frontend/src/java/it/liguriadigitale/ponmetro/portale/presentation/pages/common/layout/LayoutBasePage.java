package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.gui.layout.AbstractLayoutPage;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente.TipoEnum;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.footer.FooterPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.header.HeaderPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.notifiche.NotifichePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.renderheader.RenderHeader;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.CittadinoMinoreDiEtaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.SalvaContattiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginDemoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.privacy.PrivacyVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.privacy.PrivacyProprietaEUtenzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.privacy.PrivacyQuadroTributarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.privacy.PrivacyScadenzeVersatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.privacy.PrivacyTariPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class LayoutBasePage extends AbstractLayoutPage implements IAjaxIndicatorAware {

  private static final long serialVersionUID = -3893156412617587589L;

  private PageParameters parameters;

  private List<ContattiUtente> contattiUtente;

  public LayoutBasePage(PageParameters parameters) {
    super(parameters);
    this.parameters = parameters;
    String path = String.valueOf(parameters.get(GloboPathParametersName.PATH.name()));
    log.debug("parameters myPath:" + path);

    try {
      List<FunzioniDisponibili> lista =
          ServiceLocator.getInstance().getServiziConfigurazione().getFunzioniAbilitate();
      for (FunzioniDisponibili funzione : lista) {
        log.trace("funzione.getClassePaginaStd()=" + funzione.getClassePaginaStd());
        if (this.getClass().getSimpleName().equalsIgnoreCase(funzione.getClassePaginaStd())) {
          log.error(
              "funzione con path: "
                  + "/"
                  + funzione.getPath()
                  + "/"
                  + funzione.getUriComp()
                  + "/"
                  + funzione.getDenominazioneFunz().toLowerCase());
        }
      }
    } catch (BusinessException e) {
      log.error("Errore", e);
    }
  }

  public LayoutBasePage() {
    super();
  }

  @Override
  protected void configureResponse(final WebResponse response) {
    response.disableCaching();
    super.configureResponse(response);
  }

  @Override
  public String getAjaxIndicatorMarkupId() {
    return "indicator";
  }

  @Override
  protected void addHeader() {
    if (getUtente() != null) add(new HeaderPanel("headerPanel", LayoutBasePage.this));
  }

  @Override
  protected void addFooter() {
    add(new FooterPanel("footerPanel"));
    if (getUtente().isUtenteDelegato()) {
      add(new EmptyPanel("notifichePanel"));
    } else {
      add(new NotifichePanel("notifichePanel", LayoutBasePage.this));
    }
  }

  @Override
  public void renderHead(final IHeaderResponse response) {
    RenderHeader.render(response, getApplication());
  }

  @Override
  protected void checkUserLogged(WebRequest request) {
    LoginInSession loginSession = (LoginInSession) getSession();
    if (loginSession != null && loginSession.getUtente() == null) {
      log.debug("[BaseLandingPage] checkUserLogged loginSession && no utente.");
      if (loginSession.signIn("NAM", "NAM")) {
        log.debug(
            "[BaseLandingPage] BasePage: Utente loggato" + loginSession.getUtente().getLogin());
        controlliSuUtenteLoggato(loginSession);
      } else {
        usoLoginInternoOrNAM();
      }
    } else if (loginSession != null) {
      controlliSuUtenteLoggato(loginSession);
    }
  }

  private void controlliSuUtenteLoggato(LoginInSession loginSession) {
    Utente utente = loginSession.getUtente();
    log.debug(" checkUtenteIsMaggiorenne = " + checkUtenteIsMaggioreDiEta(utente));
    if (utente != null) {
      log.debug("isUtenteLoginEidas = " + utente.isUtenteLoginEidas());
      if (utente.isUtenteLoginEidas() || checkUtenteIsMaggioreDiEta(utente)) {
        verificaPrivacy(utente);
      } else {
        throw new RestartResponseAtInterceptPageException(CittadinoMinoreDiEtaPage.class);
      }
    }
  }

  private void verificaPrivacy(Utente utente) {
    if (!hasUtentePrivacy(utente)) {
      log.debug("PRIVACY mancante");
      redirigiToPrivacy();
    } else if (utente.isDeceduto()) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } else {
      log.debug("PRIVACY accettata --- proseguo");

      // if(Constants.DEPLOY != TIPO_DEPLOY.MANUTENZIONE && mostraSalvaContatti(utente)) {
      if (BaseServiceImpl.SALVATAGGIO_CONTATTI_ACCESO.equalsIgnoreCase("on")
          && mostraSalvaContatti(utente)) {
        log.debug("Contatti utente da inserire o aggiornare");

        redirigiToSalvaContatti();
      }
    }
  }

  private boolean hasUtentePrivacy(Utente utente) {
    log.debug("[LayoutBasePage] hasUtentePrivacy: ");
    return utente != null && utente.isFascicoloAperto();
  }

  private List<ContattiUtente> getContattiUtente(Utente utente) {
    List<ContattiUtente> contatti = null;

    try {
      contatti = ServiceLocator.getInstance().getServiziConfigurazione().getContattiUtente(utente);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getContattiUtente: " + e.getMessage(), e);
    }

    if (LabelFdCUtil.checkIfNotNull(contatti)) {

      ContattiUtente contattoCellulare =
          contatti.stream()
              .filter(elem -> elem.getTipo().equalsIgnoreCase("C"))
              .findFirst()
              .orElse(null);
      ContattiUtente contattoEmail =
          contatti.stream()
              .filter(elem -> elem.getTipo().equalsIgnoreCase("E"))
              .findFirst()
              .orElse(null);

      getUtente()
          .setMail(LabelFdCUtil.checkIfNotNull(contattoEmail) ? contattoEmail.getContatto() : null);
      getUtente()
          .setMobile(
              LabelFdCUtil.checkIfNotNull(contattoCellulare)
                  ? contattoCellulare.getContatto()
                  : null);
    }

    return contatti;
  }

  private boolean mostraSalvaContatti(Utente utente) {
    this.contattiUtente = getContattiUtente(utente);

    boolean contattiDaInserire =
        LabelFdCUtil.checkIfNull(contattiUtente) || LabelFdCUtil.checkEmptyList(contattiUtente);
    boolean contattiDaAggiornare = false;

    boolean isEmailDaAggiornare = false;
    boolean isCellulareDaAggiornare = false;

    int numeroGiorniSuDb =
        ServiceLocator.getInstance().getServiziConfigurazione().getGiorniPerAggiornareContatti();

    if (LabelFdCUtil.checkIfNotNull(contattiUtente)) {
      LocalDate dataOggi = LocalDate.now();

      ContattiUtente contattoEmail =
          contattiUtente.stream()
              .filter(elem -> elem.getTipo().equalsIgnoreCase(TipoEnum.E.value()))
              .findFirst()
              .orElse(null);
      ContattiUtente contattoCellulare =
          contattiUtente.stream()
              .filter(elem -> elem.getTipo().equalsIgnoreCase(TipoEnum.C.value()))
              .findFirst()
              .orElse(null);

      if (LabelFdCUtil.checkIfNotNull(contattoEmail)) {
        LocalDate dataAggiormanentoEmail =
            LocalDate.parse(contattoEmail.getDataAgg(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Period period = Period.between(dataAggiormanentoEmail, dataOggi);
        int giorniFraUltimoAggiornamentoEOggi = period.getDays();

        if (giorniFraUltimoAggiornamentoEOggi > numeroGiorniSuDb) {
          isEmailDaAggiornare = true;
        }
      }

      if (LabelFdCUtil.checkIfNotNull(contattoCellulare)) {
        LocalDate dataAggiormanentoCellulare =
            LocalDate.parse(
                contattoCellulare.getDataAgg(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Period period = Period.between(dataAggiormanentoCellulare, dataOggi);
        int giorniFraUltimoAggiornamentoEOggi = period.getDays();

        if (giorniFraUltimoAggiornamentoEOggi > numeroGiorniSuDb) {
          isCellulareDaAggiornare = true;
        }
      }

      if (isEmailDaAggiornare || isCellulareDaAggiornare) {
        contattiDaAggiornare = true;
      }
    }

    log.debug(
        "CP isEmailDaAggiornare = "
            + isEmailDaAggiornare
            + " - isCellulareDaAggiornare =  "
            + isCellulareDaAggiornare
            + " - contattiDaInserire "
            + contattiDaInserire);

    boolean mostraPaginaContatti = contattiDaInserire || contattiDaAggiornare;

    log.debug("CP mostraPaginaContatti = " + mostraPaginaContatti);

    return mostraPaginaContatti;
  }

  private void usoLoginInternoOrNAM() {
    if (Constants.DEPLOY == TIPO_DEPLOY.ESERCIZIO) this.setResponsePage(new ErrorPage());
    else if (Constants.DEPLOY == TIPO_DEPLOY.TEST_IAM) this.setResponsePage(new ErrorPage());
    else if (Constants.DEPLOY == TIPO_DEPLOY.TEST_LOGIN) this.setResponsePage(new LoginPage());
    else if (Constants.DEPLOY == TIPO_DEPLOY.SVILUPPO) this.setResponsePage(new LoginPage());
    else if (Constants.DEPLOY == TIPO_DEPLOY.INTEGRAZIONE) this.setResponsePage(new LoginPage());
    else if (Constants.DEPLOY == TIPO_DEPLOY.DEMO) {
      this.setResponsePage(new LoginDemoPage());
    }
  }

  private boolean checkUtenteIsMaggioreDiEta(Utente utente) {
    log.debug(
        "checkUtenteIsMaggioreDiEta = "
            + utente.getCodiceFiscaleOperatore()
            + " - "
            + utente.getDataDiNascita());
    boolean isMaggioreDi = LocalDateUtil.isMaggioreDiEta(utente.getDataDiNascita());
    log.debug("isMaggioreDi = " + isMaggioreDi);
    return isMaggioreDi;
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    final AuthenticatedWebApplication app = (AuthenticatedWebApplication) Application.get();
    // if user is not signed in, redirect him to sign in page
    if (!AuthenticatedWebSession.get().isSignedIn()) {
      app.restartResponseAtSignInPage();
    }
  }

  public static boolean ambienteDiSviluppo() {
    return (Constants.DEPLOY == TIPO_DEPLOY.SVILUPPO);
  }

  public static boolean ambienteInternoDiTest() {
    return (Constants.DEPLOY == TIPO_DEPLOY.TEST_LOGIN
        || Constants.DEPLOY == TIPO_DEPLOY.TEST_IAM
        || Constants.DEPLOY == TIPO_DEPLOY.SVILUPPO
        || Constants.DEPLOY == TIPO_DEPLOY.INTEGRAZIONE);
  }

  public static boolean ambienteSviluppoIntegrazione() {
    return (Constants.DEPLOY == TIPO_DEPLOY.SVILUPPO
        || Constants.DEPLOY == TIPO_DEPLOY.INTEGRAZIONE);
  }

  public static boolean ambienteDemo() {
    return (Constants.DEPLOY == TIPO_DEPLOY.DEMO);
  }

  public static boolean ambienteProduzione() {
    return (Constants.DEPLOY == TIPO_DEPLOY.ESERCIZIO);
  }

  @SuppressWarnings("unchecked")
  private void redirigiToPrivacy() {
    if (!(this instanceof Wizard1PrivacyPage)) {
      getUtente().setClassePagina(this.getClass());
      this.setResponsePage(trovaPaginaPrivacyPiuVicina());
    }
  }

  private void redirigiToSalvaContatti() {
    if (!(this instanceof SalvaContattiPage)) {
      getUtente().setClassePagina(this.getClass());
      this.setResponsePage(new SalvaContattiPage(false, contattiUtente));
    }
  }

  @SuppressWarnings("rawtypes")
  private Class trovaPaginaPrivacyPiuVicina() {

    String nomeClasse = this.getClass().getSimpleName();

    if (nomeClasse.equalsIgnoreCase("QuadroTributarioTariPage")) {
      return PrivacyTariPage.class;
    } else if (nomeClasse.equalsIgnoreCase("MieiVerbaliPage")) {
      return PrivacyVerbaliPage.class;
    } else if (nomeClasse.equalsIgnoreCase("ScadenzeEVersatoPage")) {
      return PrivacyScadenzeVersatoPage.class;
    } else if (nomeClasse.equalsIgnoreCase("ProprietaEUtenzePage")) {
      return PrivacyProprietaEUtenzePage.class;
    } else if (nomeClasse.equalsIgnoreCase("QuadroTributarioPage")) {
      return PrivacyQuadroTributarioPage.class;
    } else {
      return Wizard1PrivacyPage.class;
    }
  }

  @Override
  protected Class<Page403> getPaginaUtenteNonAutorizzato() {
    return Page403.class;
  }

  @Override
  protected Class<LoginPage> getLoginPage() {
    return LoginPage.class;
  }

  @Override
  public Utente getUtente() {
    LoginInSession loginSession = (LoginInSession) getSession();
    Utente utente =
        loginSession != null && loginSession.getUtente() != null ? loginSession.getUtente() : null;
    log.trace("[LayoutBasePage] Utente: " + utente);
    return utente;
  }

  protected void createFeedBackPanel() {
    NotificationPanel feedback = new NotificationPanel("feedback");
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }

  @SuppressWarnings("unused")
  private void loggaHeaders() {
    log.debug("[LayoutBasePage] >>>>> getHeaders:");
    HttpServletRequest request = ((HttpServletRequest) getRequest().getContainerRequest());
    Enumeration<String> headerNames = request.getHeaderNames();

    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String elemento = headerNames.nextElement();
        log.debug("Header: " + elemento + "=" + request.getHeader(elemento));
      }
    }
  }

  public PageParameters getMyPageParameters() {
    return parameters;
  }
}
