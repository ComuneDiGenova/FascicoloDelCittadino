package it.liguriadigitale.ponmetro.portale.presentation.common.assicurazioneRevisione;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class AssicurazioneRevisioneErrorPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4430548417449093707L;

  public AssicurazioneRevisioneErrorPage() {
    super();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE assicurazione revisione");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
