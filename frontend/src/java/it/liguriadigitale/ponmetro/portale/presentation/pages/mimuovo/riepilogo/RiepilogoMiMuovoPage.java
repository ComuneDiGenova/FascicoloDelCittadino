package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.panel.RiepilogoMiMuovoPanel;

public class RiepilogoMiMuovoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5132915525336121116L;

  public RiepilogoMiMuovoPage() {
    super();
    log.debug("RiepilogoMiMuovoPage");
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(getIdSezione(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiepilogoMiMuovoPanel miMuovoPanel = new RiepilogoMiMuovoPanel("miMuovoPanel");
    add(miMuovoPanel);
  }

  private String getIdSezione() {
    // idSezione presente sul DB
    return "3";
  }
}
