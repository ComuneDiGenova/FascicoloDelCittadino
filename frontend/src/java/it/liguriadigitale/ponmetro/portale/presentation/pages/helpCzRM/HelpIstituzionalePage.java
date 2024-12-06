package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class HelpIstituzionalePage extends WebPage {
  private static final long serialVersionUID = 1L;

  static final String URL_SUGGERIMENTI =
      "https://smart.comune.genova.it/form/fascicolo-digitale-help";

  public HelpIstituzionalePage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SUGGERIMENTI));
  }
}
