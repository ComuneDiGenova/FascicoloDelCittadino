package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.StatoIscrizionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class StatoIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public StatoIscrizionePage(UtenteServiziRistorazione iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StatoIscrizionePanel panel = new StatoIscrizionePanel(iscrizione);
    add(panel);
  }
}
