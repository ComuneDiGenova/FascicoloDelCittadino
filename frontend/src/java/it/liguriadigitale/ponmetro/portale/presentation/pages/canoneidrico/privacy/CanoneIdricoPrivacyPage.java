package it.liguriadigitale.ponmetro.portale.presentation.pages.canoneidrico.privacy;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.panel.PrivacyServizioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;

public class CanoneIdricoPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4446317300966972104L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public CanoneIdricoPrivacyPage(PrivacyServizio privacyServizio) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PrivacyServizioPanel privacyPanel = new PrivacyServizioPanel("privacyPanel", privacyServizio);
    addOrReplace(privacyPanel);
  }
}
