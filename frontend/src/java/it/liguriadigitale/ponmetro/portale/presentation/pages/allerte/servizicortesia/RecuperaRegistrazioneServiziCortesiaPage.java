package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.RecuperaRegistrazioneServiziCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class RecuperaRegistrazioneServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3222121363453523628L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RecuperaRegistrazioneServiziCortesiaPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiRegistrazioneAllerteCortesia datiRegistrazione = new DatiRegistrazioneAllerteCortesia();
    datiRegistrazione.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    RecuperaRegistrazioneServiziCortesiaPanel recuperaRegistrazionePanel =
        new RecuperaRegistrazioneServiziCortesiaPanel(
            "recuperaRegistrazionePanel", datiRegistrazione);
    addOrReplace(recuperaRegistrazionePanel);

    setOutputMarkupId(true);
  }
}
