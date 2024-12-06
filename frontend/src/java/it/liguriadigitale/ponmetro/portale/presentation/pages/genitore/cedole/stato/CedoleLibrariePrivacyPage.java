package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.CedoleLibrariePrivacyPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class CedoleLibrariePrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8986340937612301826L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public CedoleLibrariePrivacyPage(UtenteServiziRistorazione iscritto) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    CedoleLibrariePrivacyPanel privacyPanel =
        new CedoleLibrariePrivacyPanel("privacyPanel", iscritto);
    addOrReplace(privacyPanel);
  }
}
