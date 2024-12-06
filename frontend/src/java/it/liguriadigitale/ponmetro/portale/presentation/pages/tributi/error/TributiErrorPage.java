package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.error;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class TributiErrorPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3634928306245353054L;

  public TributiErrorPage() {
    super();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE verbali");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
