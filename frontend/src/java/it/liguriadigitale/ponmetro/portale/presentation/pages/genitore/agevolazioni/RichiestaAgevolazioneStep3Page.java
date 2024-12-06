package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.RichiestaAgevolazioneStep3Panel;

public class RichiestaAgevolazioneStep3Page extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4165338765650170919L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaAgevolazioneStep3Page(AgevolazioneStep2 iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaAgevolazioneStep3Panel panel = new RichiestaAgevolazioneStep3Panel(iscrizione);
    add(panel);
  }
}
