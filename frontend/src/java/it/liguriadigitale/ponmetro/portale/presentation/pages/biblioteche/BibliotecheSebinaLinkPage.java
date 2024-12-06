package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class BibliotecheSebinaLinkPage extends WebPage {

  private static final long serialVersionUID = 5941445567574246672L;

  public BibliotecheSebinaLinkPage() {
    super();

    String urlSebina = "https://bibliometroge.sebina.it/opac/.do";
    throw new RestartResponseAtInterceptPageException(new RedirectPage(urlSebina));
  }
}
