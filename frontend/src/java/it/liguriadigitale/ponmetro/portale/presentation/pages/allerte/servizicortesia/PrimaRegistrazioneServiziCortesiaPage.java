package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.DatiCompletiRegistrazioneUtenteAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.model.CompoundPropertyModel;

public class PrimaRegistrazioneServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2589667050268519151L;

  protected int index = 1;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public PrimaRegistrazioneServiziCortesiaPage(
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

    DatiCompletiRegistrazioneUtenteAllerteCortesiaPanel datiCompletiPanel =
        new DatiCompletiRegistrazioneUtenteAllerteCortesiaPanel("datiCompletiPanel", datiCompleti);

    addOrReplace(datiCompletiPanel);

    setOutputMarkupId(true);

    log.debug("CP PrimaRegistrazioneServiziCortesiaPage = " + datiCompleti);
  }
}
