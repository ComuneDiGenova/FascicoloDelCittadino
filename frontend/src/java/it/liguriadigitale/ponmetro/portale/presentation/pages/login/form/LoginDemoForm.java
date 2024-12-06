package it.liguriadigitale.ponmetro.portale.presentation.pages.login.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.BaseLandingPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.value.ValueMap;

public class LoginDemoForm extends Form<Void> {

  protected Log log = LogFactory.getLog(getClass());

  private final ValueMap properties = new ValueMap();

  public LoginDemoForm(final String id) {
    super(id);

    createFeedBackPanel();
  }

  @Override
  protected String getActionUrl() {
    return (String) super.getActionUrl();
  }

  @Override
  public final void onSubmit() {
    log.debug("CP onsubmit login per demo " + getUsername());

    LoginInSession session = getMySession();
    if (loginOK(session)) {
      log.debug(this.getClass().getName() + ":Login DEMO OK  per utente " + getUsername());
    } else {
      String errmsg = getString("loginError", null, "Username o password errate");
      error(errmsg);
      session.invalidate();
      setResponsePage(getApplication().getHomePage());
    }
    setResponsePage(BaseLandingPage.class);
  }

  private String getUsername() {
    return "PRDLCU80P20D969J";
  }

  private String getPassword() {
    return "PASSWORD";
  }

  private LoginInSession getMySession() {
    return (LoginInSession) getSession();
  }

  private boolean loginOK(final LoginInSession session) {
    return session.signIn(getUsername(), getPassword());
  }

  protected void createFeedBackPanel() {
    NotificationPanel feedback = new NotificationPanel("feedback");
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }
}
