package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.panel.RiepilogoIoGenitorePanel;

public class RiepilogoIoGenitorePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2247250974297495852L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiepilogoIoGenitorePage() {
    super();
    RiepilogoIoGenitorePanel ioGenitorePanel = new RiepilogoIoGenitorePanel("ioGenitorePanel");
    add(ioGenitorePanel);

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(getIdSezione(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  private String getIdSezione() {
    // idSezione presente sul DB
    return "2";
  }
}
