package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialIMieiFigliPage extends WebPage {

  private static final long serialVersionUID = 1L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=KEMyam-PYbs";

  public VideoTutorialIMieiFigliPage() {

    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
