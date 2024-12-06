package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.panel.TariNetribeDettagliPanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;

public class TariNetribeDettagliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3574229161302593763L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public TariNetribeDettagliPage(TARIResult documento) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TariNetribeDettagliPanel dettagliPanel = new TariNetribeDettagliPanel("dettagliPanel");
    dettagliPanel.fillDati(documento);
    addOrReplace(dettagliPanel);
  }
}
