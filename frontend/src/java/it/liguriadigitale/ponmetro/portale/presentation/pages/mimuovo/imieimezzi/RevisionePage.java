package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.RevisionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.breadcrumb.BreadcrumbRevisionePanel;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;

public class RevisionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3912024932414706137L;

  public RevisionePage() {
    super();

    RevisionePanel panel = new RevisionePanel("revisionePanel");
    panel.fillDati("");
    add(panel);

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public RevisionePage(Veicolo veicolo) {
    super();

    RevisionePanel panel = new RevisionePanel("revisionePanel", veicolo);
    add(panel);
    BreadcrumbRevisionePanel breadcrumbRevisionePanel =
        new BreadcrumbRevisionePanel("breadcrumbPanel");
    add(breadcrumbRevisionePanel);
  }
}
