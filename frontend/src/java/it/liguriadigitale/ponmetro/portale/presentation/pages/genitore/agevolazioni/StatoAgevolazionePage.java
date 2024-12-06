package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.panel.StatoAgevolazionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class StatoAgevolazionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4165338765650170919L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public StatoAgevolazionePage(UtenteServiziRistorazione iscrizione) {
    super();

    StatoAgevolazionePanel panel = new StatoAgevolazionePanel(iscrizione);
    add(panel);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public StatoAgevolazionePage() {
    this(new UtenteServiziRistorazione());
  }
}
