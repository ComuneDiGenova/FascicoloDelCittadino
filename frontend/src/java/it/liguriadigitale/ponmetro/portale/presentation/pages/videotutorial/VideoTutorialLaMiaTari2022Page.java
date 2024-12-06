package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialLaMiaTari2022Page extends WebPage {

  private static final long serialVersionUID = 4662329113816661117L;

  static final String URL_SIMULAZIONE = "https://youtu.be/RK-3Ctv60aU";

  public VideoTutorialLaMiaTari2022Page() {
    super();

    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
