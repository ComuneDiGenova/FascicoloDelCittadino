package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni.panel.CondoniEdiliziPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;

public class CondoniEdiliziPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -198288545001424207L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public CondoniEdiliziPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    CondoniEdiliziPanel condoniPanel = new CondoniEdiliziPanel("condoniPanel");
    addOrReplace(condoniPanel);

    setOutputMarkupId(true);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }
}
