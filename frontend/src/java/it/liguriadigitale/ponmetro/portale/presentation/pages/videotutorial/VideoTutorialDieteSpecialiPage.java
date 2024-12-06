package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialDieteSpecialiPage extends WebPage {

  private static final long serialVersionUID = 6619664299683675220L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=mTvI9y7Pk14";

  public VideoTutorialDieteSpecialiPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
