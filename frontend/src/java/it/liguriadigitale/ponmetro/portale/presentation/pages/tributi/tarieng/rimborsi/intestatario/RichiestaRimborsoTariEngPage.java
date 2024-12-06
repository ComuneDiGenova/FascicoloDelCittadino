package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel.RichiestaRimborsoTariEngPanel;

public class RichiestaRimborsoTariEngPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2846067512861962432L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaRimborsoTariEngPage(DatiRichiestaRimborsoTariEng datiRimborso) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaRimborsoTariEngPanel datiRimborsoPanel =
        new RichiestaRimborsoTariEngPanel("datiRimborsoPanel", datiRimborso);
    addOrReplace(datiRimborsoPanel);

    setOutputMarkupId(true);
  }
}
