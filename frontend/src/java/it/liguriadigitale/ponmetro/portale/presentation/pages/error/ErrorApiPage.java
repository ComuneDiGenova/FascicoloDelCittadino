package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import it.liguriadigitale.ponmetro.portale.presentation.components.link.MailToSupportoTecnico;
import org.apache.wicket.markup.html.WebPage;

public class ErrorApiPage extends WebPage {

  private static final long serialVersionUID = -1853474812121195908L;

  public ErrorApiPage() {
    super();

    MailToSupportoTecnico link = new MailToSupportoTecnico("supportoTec");
    add(link);
  }
}
