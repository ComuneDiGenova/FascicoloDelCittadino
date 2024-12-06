package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class VideoTutorialAttestazioniDiPagamentoPage extends WebPage {

  private static final long serialVersionUID = 1862882097906900962L;
  static final String URL_SIMULAZIONE = "https://www.youtube.com/watch?v=eBJ7054soHA";

  public VideoTutorialAttestazioniDiPagamentoPage() {
    super();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
