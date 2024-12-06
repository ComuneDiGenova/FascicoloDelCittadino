package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.panel.DettagliContributoTariPanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;

public class DettagliContributoTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -9001709173958732474L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DettagliContributoTariPage(DatiAgevolazioneTariffariaTari domanda, Double mqMassimi) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    DettagliContributoTariPanel contributoTariPanel =
        new DettagliContributoTariPanel("dettagliContributoTariPanel", domanda, mqMassimi);
    addOrReplace(contributoTariPanel);

    setOutputMarkupId(true);
  }
}
