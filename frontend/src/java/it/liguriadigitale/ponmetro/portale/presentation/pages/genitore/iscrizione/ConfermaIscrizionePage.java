package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.ConfermaIscrizionePanel;

public class ConfermaIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public ConfermaIscrizionePage(Iscrizione iscrizione) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfermaIscrizionePanel panel = new ConfermaIscrizionePanel(iscrizione);
    add(panel);
  }
}
