package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiNonResidentiPanel;

public class MieiDatiNonResidentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2798891474160690873L;

  public MieiDatiNonResidentiPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);

    MieiDatiNonResidentiPanel mieiDatiNonResidentiPanel =
        (MieiDatiNonResidentiPanel)
            new MieiDatiNonResidentiPanel("mieiDati").setRenderBodyOnly(true);
    addOrReplace(mieiDatiNonResidentiPanel);
  }
}
