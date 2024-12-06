package it.liguriadigitale.ponmetro.portale.presentation.common.tributi;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class TributiErrorPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6696829267724947744L;

  public TributiErrorPage() {
    super();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE tributi");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
