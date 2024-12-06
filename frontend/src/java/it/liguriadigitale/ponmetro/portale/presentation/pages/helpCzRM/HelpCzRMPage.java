package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel.HelpCzRMPanel;

public class HelpCzRMPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -9122836252262494875L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public HelpCzRMPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HelpCzRMPanel panel = new HelpCzRMPanel("helpCzRMPanel");
    addOrReplace(panel);
  }
}
