package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.erede;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.erede.panel.RichiestaRimborsoTariEngEredePanel;

public class RichiestaRimborsoTariEngEredePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3982837395197681259L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaRimborsoTariEngEredePage(DatiRichiestaRimborsoTariEng datiRimborso) {
    super();

    log.debug("CP RichiestaRimborsoTariEngEredePage");

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaRimborsoTariEngEredePanel datiRimborsoPanel =
        new RichiestaRimborsoTariEngEredePanel("datiRimborsoPanel", datiRimborso);
    addOrReplace(datiRimborsoPanel);

    setOutputMarkupId(true);
  }
}
