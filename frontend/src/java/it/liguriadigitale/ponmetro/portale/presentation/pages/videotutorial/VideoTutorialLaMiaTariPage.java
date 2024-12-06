package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialLaMiaTariPage extends WebPage {

  private static final long serialVersionUID = -528952911007037635L;

  static final String URL_SIMULAZIONE = "https://youtu.be/3tutBi_UT2Y";

  public VideoTutorialLaMiaTariPage() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
