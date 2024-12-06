package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.AnteprimaRicevutaDppCondCondPanel;

public class AnteprimaRicevutaDppCondCondPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 9214738079022937407L;

  public AnteprimaRicevutaDppCondCondPage(RicercaVerbaleConducente ricercaVerbaleConducente) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AnteprimaRicevutaDppCondCondPanel anteprimaRicevutaDppCondCondPanel =
        new AnteprimaRicevutaDppCondCondPanel(ricercaVerbaleConducente);
    addOrReplace(anteprimaRicevutaDppCondCondPanel);
  }
}
