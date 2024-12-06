package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.DatiModificaUtenteAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.model.CompoundPropertyModel;

public class ModificaUtenteServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6148705423706199216L;

  protected int index = 1;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ModificaUtenteServiziCortesiaPage(
      int index,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    super();

    this.index = index;

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiModificaUtenteAllerteCortesiaPanel datiCompletiPanel =
        new DatiModificaUtenteAllerteCortesiaPanel("datiCompletiPanel", datiCompleti);
    addOrReplace(datiCompletiPanel);

    setOutputMarkupId(true);

    log.debug("CP ModificaUtenteServiziCortesiaPage = " + datiCompleti);
  }
}
