package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.AutodichiarazioneGenitoreNonResidentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.breadcrumb.BreadcrumbWizard3AutocertificazioneNonResidentePanel;

public class Wizard3AutocertificazioneNonResidentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2651368234174049163L;

  public Wizard3AutocertificazioneNonResidentePage() {
    super();

    BreadcrumbWizard3AutocertificazioneNonResidentePanel breadcrumbPanel =
        new BreadcrumbWizard3AutocertificazioneNonResidentePanel("breadcrumbPanel");
    addOrReplace(breadcrumbPanel);

    AutodichiarazioneGenitoreNonResidentePanel nonResidentePanel =
        new AutodichiarazioneGenitoreNonResidentePanel("nonResidentePanel");
    addOrReplace(nonResidentePanel);
  }
}
