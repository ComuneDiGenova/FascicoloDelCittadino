package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione;

import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel.RiassuntoConfermaChiusuraIscrizionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class RiassuntoConfermaChiusuraIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5607922140916878636L;

  public RiassuntoConfermaChiusuraIscrizionePage(
      Chiusuraiscrizionerefezione chiusuraiscrizionerefezione, UtenteServiziRistorazione iscritto) {
    super();

    RiassuntoConfermaChiusuraIscrizionePanel panel =
        new RiassuntoConfermaChiusuraIscrizionePanel(
            "riassuntochiusuraiscrizionePanel", chiusuraiscrizionerefezione, iscritto);
    add(panel);

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }
}
