package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialSimulazioneTariffariaPage extends WebPage {

  private static final long serialVersionUID = 1L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=Rq8W1MBHz5k";

  public VideoTutorialSimulazioneTariffariaPage() {

    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
