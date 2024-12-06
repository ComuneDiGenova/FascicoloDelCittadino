package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.privacy.ServiziCortesiaPrivacyPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class ServiziCortesiaPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8986340937612301826L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ServiziCortesiaPrivacyPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ServiziCortesiaPrivacyPanel serviziCortesiaPrivacy =
        new ServiziCortesiaPrivacyPanel("serviziCortesiaPrivacy");
    addOrReplace(serviziCortesiaPrivacy);
  }
}
