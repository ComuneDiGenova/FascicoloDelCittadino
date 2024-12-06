package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialPresenzeInMensaPage extends WebPage {

  private static final long serialVersionUID = 1L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=LNF-ehQ41vU";

  public VideoTutorialPresenzeInMensaPage() {

    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
