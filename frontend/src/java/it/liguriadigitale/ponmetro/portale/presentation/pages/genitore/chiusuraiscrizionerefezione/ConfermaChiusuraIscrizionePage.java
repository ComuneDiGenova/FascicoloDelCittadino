package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione;

import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel.ConfermaChiusuraIscrizionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class ConfermaChiusuraIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7694556291059130530L;

  public ConfermaChiusuraIscrizionePage(
      Chiusuraiscrizionerefezione chiusuraiscrizionerefezione, UtenteServiziRistorazione iscritto) {
    super();

    ConfermaChiusuraIscrizionePanel panel =
        new ConfermaChiusuraIscrizionePanel(
            "confermahiusuraiscrizionePanel", chiusuraiscrizionerefezione, iscritto);
    add(panel);

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }
}
