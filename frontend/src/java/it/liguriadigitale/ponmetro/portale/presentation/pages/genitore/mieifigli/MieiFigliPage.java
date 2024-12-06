package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.mieifigli;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.mieifigli.panel.MieiFigliPanel;

public class MieiFigliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public MieiFigliPage() {
    super();

    // List<BreadcrumbFdC> listaBreadcrumb = new ArrayList();
    MieiFigliPanel panel = new MieiFigliPanel("mieiFigli");
    add(panel);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }
}
