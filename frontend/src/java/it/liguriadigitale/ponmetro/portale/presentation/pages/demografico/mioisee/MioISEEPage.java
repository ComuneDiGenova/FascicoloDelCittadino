package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.panel.MioISEEPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.pojo.DatiMioISEE;

public class MioISEEPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8371958479102751629L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public MioISEEPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiMioISEE datiMioISEE = new DatiMioISEE();

    MioISEEPanel mioISEEPanel = new MioISEEPanel("mioISEEPanel", datiMioISEE);
    addOrReplace(mioISEEPanel);
  }
}
