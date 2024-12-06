package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.RichiestaIscrizionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.breadcrumb.BreadcrumbRichiestaIscrizionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class RichiestaIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  @SuppressWarnings("unchecked")
  public RichiestaIscrizionePage(UtenteServiziRistorazione iscrizione) {
    super();

    if (iscrizione == null) {
      if (getSession().getAttribute("iscrizioneRistorazione") != null) {
        iscrizione =
            (UtenteServiziRistorazione) getSession().getAttribute("iscrizioneRistorazione");
      }
    } else {
      getSession().setAttribute("iscrizioneRistorazione", iscrizione);
    }

    @SuppressWarnings("rawtypes")
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaIscrizionePanel panel = new RichiestaIscrizionePanel(iscrizione);
    add(panel);
  }

  public RichiestaIscrizionePage() {
    this(null);
  }

  public RichiestaIscrizionePage(
      UtenteServiziRistorazione utenteServiziRistorazione, Iscrizione iscrizione) {
    super();

    BreadcrumbRichiestaIscrizionePanel breadcrumbRichiestaIscrizionePanel =
        new BreadcrumbRichiestaIscrizionePanel(
            "breadcrumbPanel", utenteServiziRistorazione, iscrizione);
    add(breadcrumbRichiestaIscrizionePanel);

    RichiestaIscrizionePanel panel =
        new RichiestaIscrizionePanel(utenteServiziRistorazione, iscrizione);
    add(panel);
  }
}
