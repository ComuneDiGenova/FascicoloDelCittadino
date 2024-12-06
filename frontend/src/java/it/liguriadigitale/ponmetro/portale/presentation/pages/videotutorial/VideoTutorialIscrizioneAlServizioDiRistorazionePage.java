package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialIscrizioneAlServizioDiRistorazionePage extends WebPage {

  private static final long serialVersionUID = -2949341988210347428L;
  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=D_C5gL3Grb8";

  public VideoTutorialIscrizioneAlServizioDiRistorazionePage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
