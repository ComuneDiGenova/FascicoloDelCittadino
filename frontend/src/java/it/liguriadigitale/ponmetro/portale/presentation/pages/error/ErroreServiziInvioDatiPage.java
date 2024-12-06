package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import org.apache.wicket.markup.html.basic.MultiLineLabel;

public class ErroreServiziInvioDatiPage extends ErroreServiziPage {

  private static final long serialVersionUID = 896190388562834561L;

  public ErroreServiziInvioDatiPage(String messaggio) {
    super(messaggio);

    log.debug("ErroreServiziInvioDatiPage= " + messaggio);

    String servizio = "Servizio ".concat(messaggio);
    MultiLineLabel messaggioDettaglioErrore =
        new MultiLineLabel("messaggioDettaglioErrore", servizio);

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
