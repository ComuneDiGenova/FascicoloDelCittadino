package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialBenvenutoHomePage extends WebPage {

  private static final long serialVersionUID = 3187241300219510715L;
  static final String URL_SIMULAZIONE =
      "https://youtube.com/playlist?list=PLMCJgutwDis2qd2qS3MOs_n8WnBhC5hEY";

  public VideoTutorialBenvenutoHomePage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
