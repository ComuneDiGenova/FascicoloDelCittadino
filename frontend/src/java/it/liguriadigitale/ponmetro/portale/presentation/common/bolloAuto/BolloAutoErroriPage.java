package it.liguriadigitale.ponmetro.portale.presentation.common.bolloAuto;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.markup.html.basic.Label;

public class BolloAutoErroriPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 423715692770233884L;

  public BolloAutoErroriPage() {
    super();
    log.debug("BolloAutoErroriPage");
    Label messaggioDettaglioErrore = new Label("messaggioDettaglioErrore", "");
    addOrReplace(messaggioDettaglioErrore);
  }

  public BolloAutoErroriPage(String messaggio) {
    log.debug("BolloAutoErroriPage=" + messaggio);
    String auxMessaggio = "Servizio Bollo Auto attualmente non disponibile \n " + (messaggio);
    Label messaggioDettaglioErrore = new Label("messaggioDettaglioErrore", auxMessaggio);
    addOrReplace(messaggioDettaglioErrore);
  }

  // @Override
  // public void renderPage() {
  // if (hasBeenRendered()) {
  // setResponsePage(getPageClass(), getPageParameters());
  // } else {
  // super.renderPage();
  // }
  // }
}
