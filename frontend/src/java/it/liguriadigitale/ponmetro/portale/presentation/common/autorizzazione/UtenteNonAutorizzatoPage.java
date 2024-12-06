package it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class UtenteNonAutorizzatoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2694632569544638758L;

  public UtenteNonAutorizzatoPage() {
    super();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE utente non autorizzato");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
