package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.PagoPaKoPanel;

public class PagoPaKoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8428072658612346781L;

  public PagoPaKoPage() {
    super();
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PagoPaKoPanel panel = (PagoPaKoPanel) new PagoPaKoPanel("pagaKoPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
