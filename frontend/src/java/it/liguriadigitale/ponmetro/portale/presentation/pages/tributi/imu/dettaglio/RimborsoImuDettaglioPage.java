package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.dettaglio;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.dettaglio.panel.RimborsoImuDettaglioPanel;

public class RimborsoImuDettaglioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 19875416414787151L;

  public RimborsoImuDettaglioPage() {
    super();
  }

  public RimborsoImuDettaglioPage(Integer praticaRimborsoId) {
    // TODO Auto-generated constructor stub
    this();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RimborsoImuDettaglioPanel dettaglioPanel =
        new RimborsoImuDettaglioPanel("rimborsoImuDettaglio", praticaRimborsoId);
    addOrReplace(dettaglioPanel);
  }
}
