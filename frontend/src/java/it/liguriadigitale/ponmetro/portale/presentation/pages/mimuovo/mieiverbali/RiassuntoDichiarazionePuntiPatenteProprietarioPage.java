package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.RiassuntoDichiarazionePuntiPatenteProprietarioPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;

public class RiassuntoDichiarazionePuntiPatenteProprietarioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -218054678998295219L;

  public RiassuntoDichiarazionePuntiPatenteProprietarioPage(
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiassuntoDichiarazionePuntiPatenteProprietarioPanel
        riassuntoDichiarazionePuntiPatenteProprietarioPanel =
            new RiassuntoDichiarazionePuntiPatenteProprietarioPanel(
                puntiPatenteProprietario, dettaglioVerbale, verbale);
    addOrReplace(riassuntoDichiarazionePuntiPatenteProprietarioPanel);
  }
}
