package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialStoricoAgevolazioneTariffariaPage extends WebPage {

  private static final long serialVersionUID = 9162175851083202503L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=PAjF7ez0qRU";

  public VideoTutorialStoricoAgevolazioneTariffariaPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
  // VideoTutorialStoricoAgevolazioneTariffariaPage
}
