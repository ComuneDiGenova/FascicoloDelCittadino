package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.AutodichiarazioneGenitorePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.breadcrumb.BreadcrumbWizard2AutocertificazionePanel;

public class Wizard2AutocertificazionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1303479588957705534L;

  public Wizard2AutocertificazionePage() {
    super();

    BreadcrumbWizard2AutocertificazionePanel breadcrumbWizard2AutocertificazionePanel =
        new BreadcrumbWizard2AutocertificazionePanel("breadcrumbPanel");
    add(breadcrumbWizard2AutocertificazionePanel);

    AutodichiarazioneGenitorePanel panel = new AutodichiarazioneGenitorePanel("nucleo");
    add(panel);
  }
}
