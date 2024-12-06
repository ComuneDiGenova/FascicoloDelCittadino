package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialRichiestaResidenzaPage extends WebPage {

  private static final long serialVersionUID = 4087056562945236946L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=jjpR0-FnqSM";

  public VideoTutorialRichiestaResidenzaPage() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
