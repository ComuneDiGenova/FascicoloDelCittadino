package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.modifica;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.model.CompoundPropertyModel;

public class ModificaUtenteServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2589667050268519151L;

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

    DatiRegistrazioneAllerteCortesia datiRegistrazione = new DatiRegistrazioneAllerteCortesia();
    datiRegistrazione.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());

    DatiCompletiModificaUtenteAllerteCortesiaPanel datiCompletiPanel =
        new DatiCompletiModificaUtenteAllerteCortesiaPanel("datiCompletiPanel", datiCompleti);
    addOrReplace(datiCompletiPanel);

    setOutputMarkupId(true);

    log.debug("CP PrimaRegistrazioneServiziCortesiaPage = " + datiCompleti);
  }
}
