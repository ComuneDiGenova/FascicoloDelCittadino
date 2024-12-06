package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali;

import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.ConfermaRichiestaDietaSpecialePanel;

public class ConfermaRichiestaDietaSpecialePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3940091516040825176L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ConfermaRichiestaDietaSpecialePage(DietaSpeciale dietaSpeciale) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfermaRichiestaDietaSpecialePanel richiestaDietaPanel =
        new ConfermaRichiestaDietaSpecialePanel(dietaSpeciale);
    addOrReplace(richiestaDietaPanel);
  }
}
