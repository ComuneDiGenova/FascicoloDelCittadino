package it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class ErroreGenericoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6824050563624219155L;

  public ErroreGenericoPage() {
    log.debug("ErroreGenericoPage: ");

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getPageClass()).build();
    addOrReplace(boxMessaggi);
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
