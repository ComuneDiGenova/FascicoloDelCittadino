package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.RichiestaAgevolazioneStep1Panel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.Year;

public class RichiestaAgevolazioneStep1Page extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4165338765650170919L;

  public RichiestaAgevolazioneStep1Page() {
    this(new UtenteServiziRistorazione());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaAgevolazioneStep1Page(UtenteServiziRistorazione iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    int annoCorrente = Year.now().getValue();
    RichiestaAgevolazioneStep1Panel panel =
        new RichiestaAgevolazioneStep1Panel(iscrizione, annoCorrente);
    add(panel);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaAgevolazioneStep1Page(
      UtenteServiziRistorazione iscrittoSelezionato, Integer anno) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaAgevolazioneStep1Panel panel =
        new RichiestaAgevolazioneStep1Panel(iscrittoSelezionato, anno);
    addOrReplace(panel);
  }
}
