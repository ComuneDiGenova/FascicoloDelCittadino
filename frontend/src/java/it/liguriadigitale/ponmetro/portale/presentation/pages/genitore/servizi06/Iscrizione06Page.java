package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.servizi06;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class Iscrizione06Page extends WebPage {

  private static final long serialVersionUID = -2182283460479700050L;

  public Iscrizione06Page() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage("/../../../iscrizione06/"));
    // getRequestCycle().scheduleRequestHandlerAfterCurrent(new
    // RedirectRequestHandler("../iscrizione06/"));
  }
}
