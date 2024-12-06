package it.liguriadigitale.ponmetro.portale.presentation.common.mipGlobali;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.markup.html.basic.Label;

public class MipGlobaliErrorPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7251464392047883881L;

  public MipGlobaliErrorPage() {
    super();

    log.debug("MipGlobaliErrorPage");
    Label messaggioDettaglioErrore =
        new Label("messaggioDettaglioErrore", getString("MipGlobaliErrorPage.infoErrore"));
    addOrReplace(messaggioDettaglioErrore);
  }

  public MipGlobaliErrorPage(String messaggio) {
    log.debug("MipGlobaliErrorPage=" + messaggio);
    String auxMessaggio = "Servizio Pagamenti attualmente non disponibile: ".concat(messaggio);
    Label messaggioDettaglioErrore = new Label("messaggioDettaglioErrore", auxMessaggio);
    addOrReplace(messaggioDettaglioErrore);
  }

  @Override
  public void renderPage() {
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
