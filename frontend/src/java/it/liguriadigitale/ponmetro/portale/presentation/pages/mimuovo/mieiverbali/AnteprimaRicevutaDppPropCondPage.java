package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.AnteprimaRicevutaDppPropCondPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;

public class AnteprimaRicevutaDppPropCondPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 9214738079022937407L;

  public AnteprimaRicevutaDppPropCondPage(
      PuntiPatenteProprietario patenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AnteprimaRicevutaDppPropCondPanel anteprimaRicevutaDppPropCondPanel =
        new AnteprimaRicevutaDppPropCondPanel(patenteProprietario, dettaglioVerbale, verbale);
    addOrReplace(anteprimaRicevutaDppPropCondPanel);
  }
}
