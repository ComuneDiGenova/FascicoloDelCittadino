package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import it.liguriadigitale.ponmetro.portale.presentation.components.link.MailToSupportoTecnicoLogin;
import org.apache.wicket.markup.html.WebPage;

public class LoginErrorPage extends WebPage {

  private static final long serialVersionUID = 8721108526883771958L;

  public LoginErrorPage() {
    super();

    MailToSupportoTecnicoLogin link = new MailToSupportoTecnicoLogin("supportoTec");
    add(link);
  }
}
