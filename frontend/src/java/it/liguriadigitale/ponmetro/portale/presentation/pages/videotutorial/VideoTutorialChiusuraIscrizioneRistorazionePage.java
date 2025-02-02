package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialChiusuraIscrizioneRistorazionePage extends WebPage {

  private static final long serialVersionUID = -7267151875514170392L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=DNCfMeyui4s";

  public VideoTutorialChiusuraIscrizioneRistorazionePage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
