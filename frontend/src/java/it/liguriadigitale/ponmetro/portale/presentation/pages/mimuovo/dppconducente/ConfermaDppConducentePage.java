package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.ConfermaDppConducentePanel;

public class ConfermaDppConducentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2639889013279664084L;

  public ConfermaDppConducentePage(RicercaVerbaleConducente ricercaVerbaleConducente) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfermaDppConducentePanel confermaDppConducentePanel =
        new ConfermaDppConducentePanel(ricercaVerbaleConducente);
    addOrReplace(confermaDppConducentePanel);
  }
}
