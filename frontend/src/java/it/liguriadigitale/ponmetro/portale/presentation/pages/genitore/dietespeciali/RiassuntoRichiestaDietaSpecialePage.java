package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali;

import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.RiassuntoRichiestaDietaSpecialePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class RiassuntoRichiestaDietaSpecialePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2229064783810569595L;

  public RiassuntoRichiestaDietaSpecialePage() {
    super();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiassuntoRichiestaDietaSpecialePage(
      UtenteServiziRistorazione iscrizione, DietaSpeciale dietaSpeciale) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiassuntoRichiestaDietaSpecialePanel riassuntoPanel =
        new RiassuntoRichiestaDietaSpecialePanel(iscrizione, dietaSpeciale);
    add(riassuntoPanel);
  }
}
