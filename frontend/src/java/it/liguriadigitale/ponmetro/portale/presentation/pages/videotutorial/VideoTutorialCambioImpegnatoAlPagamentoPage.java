package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialCambioImpegnatoAlPagamentoPage extends WebPage {

  private static final long serialVersionUID = -1652490442921905385L;
  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=L700oZnniZg";

  public VideoTutorialCambioImpegnatoAlPagamentoPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
