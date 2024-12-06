package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialCertificatoStatoCivilePage extends WebPage {

  private static final long serialVersionUID = -3592250157375539175L;

  static final String URL_SIMULAZIONE = "https://youtu.be/NLgk1H8U4S4";

  public VideoTutorialCertificatoStatoCivilePage() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
