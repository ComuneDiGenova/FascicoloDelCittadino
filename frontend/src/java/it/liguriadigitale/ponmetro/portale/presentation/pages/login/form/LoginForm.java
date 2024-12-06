package it.liguriadigitale.ponmetro.portale.presentation.pages.login.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.RedirectBaseLandingPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class LoginForm extends Form<Void> {

  @SuppressWarnings("unused")
  private static final String WSO2_SHIBBOLETH =
      "https://wso2spct2.comune.genova.it/oauth2/authorize";

  private static final long serialVersionUID = -7825478031038731460L;

  protected Log log = LogFactory.getLog(getClass());

  private static final String USERNAME = "username";

  private static final String NOME = "nome";

  private static final String COGNOME = "cognome";

  private final ValueMap properties = new ValueMap();

  public LoginForm(final String id) {
    super(id);
    // Attach textfield components that edit properties map model
    TextField<String> textFieldCf =
        new TextField<>(USERNAME, new PropertyModel<String>(properties, USERNAME));
    TextField<String> textFieldNome =
        new TextField<>(NOME, new PropertyModel<String>(properties, NOME));
    TextField<String> textFieldCognome =
        new TextField<>(COGNOME, new PropertyModel<String>(properties, COGNOME));

    addOrReplace(textFieldCf);
    addOrReplace(textFieldNome);
    addOrReplace(textFieldCognome);

    createFeedBackPanel();
  }

  @Override
  protected String getActionUrl() {
    // if (Constants.DEPLOY != TIPO_DEPLOY.SVILUPPO)
    // return WSO2_SHIBBOLETH;
    // else
    return (String) super.getActionUrl();
  }

  @Override
  public final void onSubmit() {
    LoginInSession session = getMySession();
    if (loginOK(session)) {
      log.debug(this.getClass().getName() + ":Login OK  per utente " + getUsername());

      if (!session.getUtente().isResidente()) {
        session.getUtente().setNome(getNome());
        session.getUtente().setCognome(getCognome());
      }

      setResponsePage(RedirectBaseLandingPage.class);

    } else {
      String errmsg = getString("loginError", null, "Username o password errate");
      error(errmsg);
      session.invalidate();
      setResponsePage(getApplication().getHomePage());
    }
  }

  private boolean loginOK(final LoginInSession session) {
    return session.signIn(getUsername(), getPassword());
  }

  /**
   * @return
   */
  private String getPassword() {
    return "PASSWORD";
  }

  /**
   * @return
   */
  private String getUsername() {
    return properties.getString(USERNAME).toUpperCase();
  }

  private String getNome() {
    String nome = "";
    if (properties.getString(NOME) != null) {
      nome = properties.getString(NOME).toUpperCase();
    }
    return nome;
  }

  private String getCognome() {
    String cognome = "";
    if (properties.getString(COGNOME) != null) {
      cognome = properties.getString(COGNOME).toUpperCase();
    }
    return cognome;
  }

  /**
   * @return
   */
  private LoginInSession getMySession() {
    return (LoginInSession) getSession();
  }

  protected void createFeedBackPanel() {
    NotificationPanel feedback = new NotificationPanel("feedback");
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }
}
