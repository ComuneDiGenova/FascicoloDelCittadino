package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo.panel.RiepilogoIoSostenibilePanel;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoIoSostenibilePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3677730830801641425L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RiepilogoIoSostenibilePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(getIdSezione(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiepilogoIoSostenibilePanel ioVirtuosoPanel =
        new RiepilogoIoSostenibilePanel("ioVirtuosoPanel");
    addOrReplace(ioVirtuosoPanel);
  }

  private String getIdSezione() {
    // idSezione presente sul DB
    return "8";
  }

  @SuppressWarnings("unused")
  private List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioSostenibile", "io Sostenibile"));

    return listaBreadcrumb;
  }
}
