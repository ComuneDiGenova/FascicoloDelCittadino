package it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.CittadinoMinoreDiEtaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.PreLoadingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginDemoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.LoginPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BaseLandingPage extends WebPage {

  private static final long serialVersionUID = 1437341532196533213L;
  private static Log log = LogFactory.getLog(BaseLandingPage.class);

  public BaseLandingPage(final PageParameters parameters) {
    super();
    checkUserLogged();
  }

  private void checkUserLogged() {
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
    log.debug("CP checkUtenteIsMaggiorenne = " + checkUtenteIsMaggioreDiEta(utente));
    if (utente != null) {

      if (utente.isUtenteLoginEidas() || checkUtenteIsMaggioreDiEta(utente)) {
        if (!hasUtentePrivacy(utente)) {
          redirigiToPrivacy();
        } else if (utente.isDeceduto()) {
          throw new RestartResponseAtInterceptPageException(ErrorPage.class);
        } else {
          throw new RestartResponseAtInterceptPageException(PreLoadingPage.class);
        }
      } else {
        throw new RestartResponseAtInterceptPageException(CittadinoMinoreDiEtaPage.class);
      }
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

  private WebRequest getWicketWebRequest() {
    RequestCycle requestCycle = RequestCycle.get();
    WebRequest request = (WebRequest) requestCycle.getRequest();
    return request;
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

  private void redirigiToPrivacy() {
    // if (!(this instanceof Wizard1PrivacyPage)) {
    this.setResponsePage(new Wizard1PrivacyPage());
    // }
  }

  private boolean hasUtentePrivacy(Utente utente) {
    log.debug("[LayoutBasePage] hasUtentePrivacy: " + utente);
    return utente != null && utente.isFascicoloAperto();
  }
}
