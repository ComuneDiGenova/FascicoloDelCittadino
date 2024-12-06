package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialVerbaliDiCommissioniMensaPage extends WebPage {

  private static final long serialVersionUID = 99855547719061272L;

  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=kVZ4Zx08S_0";

  public VideoTutorialVerbaliDiCommissioniMensaPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
