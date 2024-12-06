package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.RichiestaAgevolazioneStep2Panel;

public class RichiestaAgevolazioneStep2Page extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4165338765650170919L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaAgevolazioneStep2Page(AgevolazioneStep1 iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaAgevolazioneStep2Panel panel = new RichiestaAgevolazioneStep2Panel(iscrizione);
    add(panel);
  }
}
