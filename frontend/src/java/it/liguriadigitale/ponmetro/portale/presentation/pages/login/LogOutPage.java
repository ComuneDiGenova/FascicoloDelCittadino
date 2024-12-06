package it.liguriadigitale.ponmetro.portale.presentation.pages.login;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.flow.RedirectToUrlException;

public class LogOutPage extends WebPage {

  protected Log log = LogFactory.getLog(getClass());

  private static final long serialVersionUID = 535234795800634225L;

  public static final String URL_TO_REDIRECT =
      "https://smart.comune.genova.it/sezione/iogenova-fascicolo-digitale-del-cittadino/";

  // public static final String URL_LOGOUT_GLOBO =
  // "https://fascicolo-test.comune.genova.it/auth-service/logout";

  public LogOutPage() {
    super();
    log.debug(" >>> LOGOUTPAGE <<< ");
    String serverUrl = PageUtil.getServerUrl();
    log.debug(" >>> serverUrl: " + serverUrl);
    String url =
        "/portale/web/nondelegabili/Shibboleth.sso/Logout?return="
            + BaseServiceImpl.URL_LOGOUT_GLOBO;
    invalidateSession();
    log.debug(" >>> LogOutPage(), redirect to url: " + url);
    // logoutGlobo();
    throw new RedirectToUrlException(serverUrl + url);
  }

  public void logoutGlobo() {
    try {
      URL urlGlobo = new URL(BaseServiceImpl.URL_LOGOUT_GLOBO);
      Object content = urlGlobo.getContent();
      log.debug("Content globo logout:" + content);
    } catch (IOException e) {
      log.error("Errore logout Globo: ", e);
    }
  }

  private void invalidateSession() {
    log.debug(" >>> LogOutPage.invalidateSession() ");
    LoginInSession session = (LoginInSession) getSession();
    // FRR 20200102 controllo su logout null pointer
    synchronized (session) {
      if (session != null) {
        Utente utente = null;
        if (session != null) {
          utente = session.getUtente();
        }
        String cognomeUtente = utente != null ? utente.getCognome() : " nullo ";
        log.debug(" >>> session() - cognome utente " + cognomeUtente + " NULL ");
        log.debug(" >>> setto utente a null");
        if (session != null) {
          session.setUtente(null);
        }
        log.debug(" >>> invalido...");
        if (session != null) {
          session.invalidate();
        }
      }
    }
    log.debug(" ESCO DA invalidateSession() ");
  }
}
