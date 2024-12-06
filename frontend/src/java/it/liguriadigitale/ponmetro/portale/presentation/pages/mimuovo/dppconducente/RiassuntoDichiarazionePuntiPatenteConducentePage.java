package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.RiassuntoDichiarazionePuntiPatentePanel;

public class RiassuntoDichiarazionePuntiPatenteConducentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7523675218197566991L;

  public RiassuntoDichiarazionePuntiPatenteConducentePage() {
    super();
  }

  public RiassuntoDichiarazionePuntiPatenteConducentePage(
      RicercaVerbaleConducente ricercaVerbaleConducente) {

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiassuntoDichiarazionePuntiPatentePanel riassuntoDichiarazionePuntiPatentePanel =
        new RiassuntoDichiarazionePuntiPatentePanel(ricercaVerbaleConducente);
    add(riassuntoDichiarazionePuntiPatentePanel);
  }
}
