package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.panel.RiepilogoContribuentePanel;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoContribuentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5132915525336121116L;

  public RiepilogoContribuentePage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(getIdSezione(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiepilogoContribuentePanel contribuentePanel =
        new RiepilogoContribuentePanel("contribuentePanel");
    add(contribuentePanel);
  }

  private String getIdSezione() {
    // idSezione presente sul DB
    return "4";
  }

  @SuppressWarnings("unused")
  private List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("tributi", "io Contribuente"));

    return listaBreadcrumb;
  }
}
