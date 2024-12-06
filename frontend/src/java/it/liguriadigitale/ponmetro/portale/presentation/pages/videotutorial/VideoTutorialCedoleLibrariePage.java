package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialCedoleLibrariePage extends WebPage {

  private static final long serialVersionUID = 7375204991045175110L;
  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=g3f0--Zfk2g";

  public VideoTutorialCedoleLibrariePage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
