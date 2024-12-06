package it.liguriadigitale.ponmetro.portale.presentation.pages.login;

import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.renderheader.RenderHeader;
import it.liguriadigitale.ponmetro.portale.presentation.pages.login.form.LoginDemoForm;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginDemoPage extends WebPage {

  private static final long serialVersionUID = 8693057139654456644L;

  public LoginDemoPage() {
    this(null);
  }

  public LoginDemoPage(final PageParameters parameters) {
    invalidateSession();
    add(new LoginDemoForm("signInForm"));
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
}
