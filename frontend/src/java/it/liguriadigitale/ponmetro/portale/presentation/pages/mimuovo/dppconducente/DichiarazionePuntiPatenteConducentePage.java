package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.RicercaVerbaleConducenteFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.breadcrumb.BreadcrumbDichiarazionePuntiPatenteConducentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.nonresidenti.RicercaVerbaleConducenteNonResidenteFormPanel;

public class DichiarazionePuntiPatenteConducentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2587899964648286004L;

  public DichiarazionePuntiPatenteConducentePage() {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    if (getUtente().isResidente()) {
      RicercaVerbaleConducenteFormPanel ricercaVerbaleConducenteFormPanel =
          new RicercaVerbaleConducenteFormPanel("ricercaVerbaleFormPanel");
      ricercaVerbaleConducenteFormPanel.fillDati(new RicercaVerbaleConducente());
      addOrReplace(ricercaVerbaleConducenteFormPanel);
    } else {
      RicercaVerbaleConducenteNonResidenteFormPanel ricercaVerbaleConducenteFormPanel =
          new RicercaVerbaleConducenteNonResidenteFormPanel("ricercaVerbaleFormPanel");
      ricercaVerbaleConducenteFormPanel.fillDati(new RicercaVerbaleConducente());
      addOrReplace(ricercaVerbaleConducenteFormPanel);
    }

    setOutputMarkupId(true);
  }

  public DichiarazionePuntiPatenteConducentePage(
      RicercaVerbaleConducente ricercaVerbaleConducente) {
    super();

    BreadcrumbDichiarazionePuntiPatenteConducentePanel
        breadcrumbDichiarazionePuntiPatenteConducentePanel =
            (BreadcrumbDichiarazionePuntiPatenteConducentePanel)
                new BreadcrumbDichiarazionePuntiPatenteConducentePanel("breadcrumbPanel")
                    .setRenderBodyOnly(true);
    addOrReplace(breadcrumbDichiarazionePuntiPatenteConducentePanel);

    if (getUtente().isResidente()) {
      RicercaVerbaleConducenteFormPanel ricercaVerbaleConducenteFormPanel =
          new RicercaVerbaleConducenteFormPanel("ricercaVerbaleFormPanel");
      ricercaVerbaleConducenteFormPanel.fillDati(ricercaVerbaleConducente);
      addOrReplace(ricercaVerbaleConducenteFormPanel);
    } else {
      RicercaVerbaleConducenteNonResidenteFormPanel ricercaVerbaleConducenteFormPanel =
          new RicercaVerbaleConducenteNonResidenteFormPanel("ricercaVerbaleFormPanel");
      ricercaVerbaleConducenteFormPanel.fillDati(ricercaVerbaleConducente);
      addOrReplace(ricercaVerbaleConducenteFormPanel);
    }

    setOutputMarkupId(true);
  }
}
