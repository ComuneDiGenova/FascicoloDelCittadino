package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialMovimentiPagamentiPage extends WebPage {

  private static final long serialVersionUID = -8604612631183603518L;
  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=VDdzaB1c2U0";

  public VideoTutorialMovimentiPagamentiPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
