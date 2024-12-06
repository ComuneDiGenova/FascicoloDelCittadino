package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialPresenzeInMensaEMenuPage extends WebPage {

  private static final long serialVersionUID = 4779281228519911384L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=pMbZEraGdsQ";

  public VideoTutorialPresenzeInMensaEMenuPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
