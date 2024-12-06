package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.RiassuntoConfermaIscrizionePanel;

public class RiassuntoConfermaIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  @SuppressWarnings("unchecked")
  public RiassuntoConfermaIscrizionePage(Iscrizione iscrizione) {
    super();

    @SuppressWarnings("rawtypes")
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiassuntoConfermaIscrizionePanel panel = new RiassuntoConfermaIscrizionePanel(iscrizione);
    add(panel);
  }
}
