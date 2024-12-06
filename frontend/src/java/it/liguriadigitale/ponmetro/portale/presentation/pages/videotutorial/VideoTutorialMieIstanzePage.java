package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialMieIstanzePage extends WebPage {

  private static final long serialVersionUID = 3063127533246166219L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=Sn8RoidAcws";

  public VideoTutorialMieIstanzePage() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
