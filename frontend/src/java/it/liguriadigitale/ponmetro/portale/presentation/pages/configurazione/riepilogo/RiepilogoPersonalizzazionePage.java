package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo.panel.RiepilogoPersonalizzazionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo.panel.breadcrumb.BreadcrumbRiepilogoPersonalizzazionePanel;

public class RiepilogoPersonalizzazionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6497709389018097209L;

  public RiepilogoPersonalizzazionePage() {
    super();

    BreadcrumbRiepilogoPersonalizzazionePanel breadcrumbRiepilogoPersonalizzazionePanel =
        new BreadcrumbRiepilogoPersonalizzazionePanel("breadcrumbPanel");
    add(breadcrumbRiepilogoPersonalizzazionePanel);

    RiepilogoPersonalizzazionePanel personalizzoPanel =
        new RiepilogoPersonalizzazionePanel("ioPersonalizzoPanel");
    add(personalizzoPanel);
  }
}
