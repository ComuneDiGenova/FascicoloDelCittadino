package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.PrivacyPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.breadcrumb.BreadcrumbWizard1PrivacyPanel;

public class Wizard1PrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1303479588957705534L;

  public Wizard1PrivacyPage() {
    super();

    BreadcrumbWizard1PrivacyPanel breadcrumbWizard1PrivacyPanel =
        new BreadcrumbWizard1PrivacyPanel("breadcrumbPanel");
    add(breadcrumbWizard1PrivacyPanel);

    PrivacyPanel panel = new PrivacyPanel();
    add(panel);
  }
}
