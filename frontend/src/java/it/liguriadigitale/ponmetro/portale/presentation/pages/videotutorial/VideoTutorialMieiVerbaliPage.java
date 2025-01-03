package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialMieiVerbaliPage extends WebPage {

  private static final long serialVersionUID = 3063127533246166219L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=mM1UPkhKUDQ";

  public VideoTutorialMieiVerbaliPage() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
