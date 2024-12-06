package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.bravprivacy;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.panel.PrivacyServizioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;

public class BravPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5088063870878048888L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BravPrivacyPage(PrivacyServizio privacyServizio) {
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
