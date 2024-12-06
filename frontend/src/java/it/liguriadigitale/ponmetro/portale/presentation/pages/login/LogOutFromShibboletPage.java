package it.liguriadigitale.ponmetro.portale.presentation.pages.login;

import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LogOutFromShibboletPage extends WebPage {

  protected Log log = LogFactory.getLog(getClass());

  private static final long serialVersionUID = 3283345281623866647L;

  // private static final String	RETURN_PARAMETER_NAME	= "return";
  // 20191011 - shibbolet non chiama piu' questa pagina (la lasciamo per
  // completezza) ma fa la redirect diremmante shibbolet
  public LogOutFromShibboletPage(PageParameters parameters) {
    super(parameters);
    log.debug(" >>> LogOutFromShibboletPage <<< ");
    log.debug(" >>> parameters : " + parameters);
    String redirectUrl = LogOutPage.URL_TO_REDIRECT;
    log.debug(" >>> redirectUrl: " + redirectUrl);
    invalidateSession();
    log.debug(" >>> LogOutFromShibboletPage(), redirect to url: " + redirectUrl);
    throw new RedirectToUrlException(redirectUrl);
    // frr rimosso StringValue returnParameter =
    // parameters.get(RETURN_PARAMETER_NAME);
    // if (!returnParameter.isNull() && !returnParameter.isEmpty()) {
    // throw new RedirectToUrlException(returnParameter.toString());
    // }
  }

  private void invalidateSession() {
    log.debug(" >>> LogOutFromShibboletPage.invalidateSession() ");
    LoginInSession session = (LoginInSession) getSession();
    log.debug(
        " >>> session() - cognome utente " + session.getUtente() != null
            ? session.getUtente().getCognome()
            : " NULL ");
    log.debug(" >>> setto utente a null");
    session.setUtente(null);
    log.debug(" >>> invalido...");
    session.invalidate();
    log.debug(" ESCO DA invalidateSession() ");
  }
}
