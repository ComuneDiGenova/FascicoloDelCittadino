package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

public class ErroreServiziPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6102679176105030810L;

  public ErroreServiziPage(String messaggio) {
    log.debug("ErroreServiziPage= " + messaggio);

    String servizio = "Servizio ".concat(messaggio).concat(" ");
    String nonDisponibile =
        "attualmente non disponibile."
            .concat("\n")
            .concat("Si prega di riprovare più tardi. Grazie!");
    String messaggioDaMostrare = servizio.concat(nonDisponibile);

    MultiLineLabel messaggioDettaglioErrore =
        new MultiLineLabel("messaggioDettaglioErrore", messaggioDaMostrare);

    addOrReplace(messaggioDettaglioErrore);
  }

  public ErroreServiziPage(String messaggioDaBackend, boolean isBackend) {
    log.debug("ErroreServiziPage= " + messaggioDaBackend);

    MultiLineLabel messaggioDettaglioErrore =
        new MultiLineLabel("messaggioDettaglioErrore", messaggioDaBackend);

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
