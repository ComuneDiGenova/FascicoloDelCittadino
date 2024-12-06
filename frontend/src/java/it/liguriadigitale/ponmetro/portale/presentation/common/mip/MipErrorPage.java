package it.liguriadigitale.ponmetro.portale.presentation.common.mip;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class MipErrorPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7251464392047883881L;

  public MipErrorPage() {
    super();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE mip");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
