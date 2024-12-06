package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.DichiarazionePuntiPatentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.breadcrumb.BreadcrumbDichiarazionePuntiPatentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.dpp.nonresidenti.DichiarazionePuntiPatenteNonResidentePanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;

public class DichiarazionePuntiPatentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4586665811999496870L;

  public DichiarazionePuntiPatentePage(DettaglioVerbale dettaglioVerbale, Verbale verbale) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    if (getUtente().isResidente()) {
      DichiarazionePuntiPatentePanel dichiarazionePuntiPatentePanel =
          (DichiarazionePuntiPatentePanel)
              new DichiarazionePuntiPatentePanel(
                      "dichiarazionePuntiPatentePanel", dettaglioVerbale, verbale)
                  .setRenderBodyOnly(true);
      dichiarazionePuntiPatentePanel.fillDati(dettaglioVerbale);
      addOrReplace(dichiarazionePuntiPatentePanel);
    } else {
      DichiarazionePuntiPatenteNonResidentePanel dichiarazionePuntiPatentePanel =
          (DichiarazionePuntiPatenteNonResidentePanel)
              new DichiarazionePuntiPatenteNonResidentePanel(
                      "dichiarazionePuntiPatentePanel", dettaglioVerbale, verbale)
                  .setRenderBodyOnly(true);
      dichiarazionePuntiPatentePanel.fillDati(dettaglioVerbale);
      addOrReplace(dichiarazionePuntiPatentePanel);
    }

    setOutputMarkupId(true);
  }

  public DichiarazionePuntiPatentePage(
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {
    super();

    BreadcrumbDichiarazionePuntiPatentePanel breadcrumbDichiarazionePuntiPatentePanel =
        (BreadcrumbDichiarazionePuntiPatentePanel)
            new BreadcrumbDichiarazionePuntiPatentePanel("breadcrumbPanel", verbale)
                .setRenderBodyOnly(true);
    addOrReplace(breadcrumbDichiarazionePuntiPatentePanel);

    if (getUtente().isResidente()) {
      DichiarazionePuntiPatentePanel dichiarazionePuntiPatentePanel =
          (DichiarazionePuntiPatentePanel)
              new DichiarazionePuntiPatentePanel(
                      "dichiarazionePuntiPatentePanel", puntiPatenteProprietario, dettaglioVerbale)
                  .setRenderBodyOnly(true);
      dichiarazionePuntiPatentePanel.fillDati(dettaglioVerbale);
      addOrReplace(dichiarazionePuntiPatentePanel);
    } else {
      DichiarazionePuntiPatenteNonResidentePanel dichiarazionePuntiPatentePanel =
          (DichiarazionePuntiPatenteNonResidentePanel)
              new DichiarazionePuntiPatenteNonResidentePanel(
                      "dichiarazionePuntiPatentePanel", puntiPatenteProprietario, dettaglioVerbale)
                  .setRenderBodyOnly(true);
      dichiarazionePuntiPatentePanel.fillDati(dettaglioVerbale);
      addOrReplace(dichiarazionePuntiPatentePanel);
    }

    setOutputMarkupId(true);
  }
}
