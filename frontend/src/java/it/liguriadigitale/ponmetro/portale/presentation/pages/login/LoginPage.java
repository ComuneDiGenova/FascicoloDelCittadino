package it.liguriadigitale.ponmetro.portale.presentation.pages.login;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.renderheader.RenderHeader;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.form.LoginForm;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginPage extends WebPage {

  private static final long serialVersionUID = -5357329298177002049L;
  private static Log log = LogFactory.getLog(LoginPage.class);

  /** Construct */
  public LoginPage() {
    this(null);
  }

  /**
   * Constructor
   *
   * @param parameters The page parameters
   */
  public LoginPage(final PageParameters parameters) {
    invalidateSession();
    verificaAutorizzazioneCodiceFiscaleByHeaderRequest();
    add(new LoginForm("signInForm"));
    String ambiente = StringUtils.upperCase(Constants.DEPLOY.name());
    add(new NotEmptyLabel("ambienteDeploy", ambiente));
  }

  private void invalidateSession() {
    LoginInSession session = (LoginInSession) getSession();
    session.setUtente(null);
  }

  @Override
  public void renderHead(final IHeaderResponse response) {
    RenderHeader.render(response, getApplication());
    response.render(
        CssContentHeaderItem.forUrl(
            "/" + Constants.Componente.GESTORE.getWebContext() + "/css/signin.css"));
  }

  private WebRequest retrieveWebRequest() {
    final RequestCycle requestCycle = RequestCycle.get();
    final WebRequest request = (WebRequest) requestCycle.getRequest();
    return request;
  }

  private void verificaAutorizzazioneCodiceFiscaleByHeaderRequest() {
    WebRequest request = retrieveWebRequest();
    String codiceFiscale = request.getHeader(Utente.COMGE_CODICEFISCALE);
    log.debug("--- getUtenteByHeaderRequest= " + codiceFiscale);
    try {
      if (ServiceLocator.getInstance()
          .getGestioneLogin()
          .verificaCodiceFiscaleAutorizzato(codiceFiscale)) {
        log.debug("AUTORIZZATO!");
      } else {
        log.debug("codiceFiscale nullo o vuoto");
        throw new RestartResponseAtInterceptPageException(Page403.class);
      }
    } catch (BusinessException e) {
      log.error("Errore durante la verifica del codice fiscale: " + codiceFiscale, e);
      throw new RestartResponseAtInterceptPageException(Page403.class);
    }
  }
}
