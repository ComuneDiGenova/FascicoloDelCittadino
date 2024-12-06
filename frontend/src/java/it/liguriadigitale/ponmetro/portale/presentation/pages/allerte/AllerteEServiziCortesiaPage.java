package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.panel.AllerteEServiziCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class AllerteEServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5282764303878298682L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AllerteEServiziCortesiaPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AllerteEServiziCortesiaPanel allerteEServiziCortesiaPanel =
        new AllerteEServiziCortesiaPanel("allerteEServiziCortesiaPanel");
    addOrReplace(allerteEServiziCortesiaPanel);

    setOutputMarkupId(true);
  }
}
