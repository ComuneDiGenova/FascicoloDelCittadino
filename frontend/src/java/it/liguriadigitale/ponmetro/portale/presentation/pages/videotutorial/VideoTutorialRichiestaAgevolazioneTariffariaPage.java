package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialRichiestaAgevolazioneTariffariaPage extends WebPage {

  private static final long serialVersionUID = 8177655420765201994L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=Pf4SCtp4wko";

  public VideoTutorialRichiestaAgevolazioneTariffariaPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
