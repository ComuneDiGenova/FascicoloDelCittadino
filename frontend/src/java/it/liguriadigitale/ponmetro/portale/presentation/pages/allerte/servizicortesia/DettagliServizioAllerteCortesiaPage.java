package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.dettagliservizio.DettagliServizioAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;

public class DettagliServizioAllerteCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5895360832164533805L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DettagliServizioAllerteCortesiaPage(
      VerificaServiziResponse verificaServizi, String email) {
    super();

    @SuppressWarnings("unused")
    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance()
            .getServiziAllerteCortesia()
            .getListaBreadcrumbModificaServizio();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DettagliServizioAllerteCortesiaPanel modificaServizioPanel =
        new DettagliServizioAllerteCortesiaPanel("modificaServizioPanel", verificaServizi, email);
    addOrReplace(modificaServizioPanel);

    setOutputMarkupId(true);
  }
}
