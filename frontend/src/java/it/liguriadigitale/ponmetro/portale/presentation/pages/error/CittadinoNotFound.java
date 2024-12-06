package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.http.WebResponse;

public class CittadinoNotFound extends WebPage {

  private static final long serialVersionUID = 7359051219473706565L;

  static final String TORNA_ALLA_HOME_SVILUPPO_INTEGRAZIONE_TEST = "/";
  static final String TORNA_ALLA_HOME_PRODUZIONE =
      "https://www.comune.genova.it/servizi/ambiente/fascicolo-del-cittadino";

  public CittadinoNotFound() {
    super();

    ExternalLink linkTornaAllaHome;

    if (LayoutBasePage.ambienteDiSviluppo()
        || LayoutBasePage.ambienteInternoDiTest()
        || LayoutBasePage.ambienteSviluppoIntegrazione()) {
      linkTornaAllaHome =
          new ExternalLink("linkTornaAllaHome", TORNA_ALLA_HOME_SVILUPPO_INTEGRAZIONE_TEST);
    } else {
      linkTornaAllaHome = new ExternalLink("linkTornaAllaHome", TORNA_ALLA_HOME_PRODUZIONE);
    }

    add(linkTornaAllaHome);
  }

  @Override
  protected void configureResponse(WebResponse arg0) {
    super.configureResponse(arg0);
  }

  @Override
  public boolean isVersioned() {
    return false;
  }

  @Override
  public boolean isErrorPage() {
    return true;
  }
}
