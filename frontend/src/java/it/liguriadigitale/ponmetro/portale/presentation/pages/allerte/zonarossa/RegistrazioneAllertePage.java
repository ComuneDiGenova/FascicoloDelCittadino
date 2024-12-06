package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione.DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.model.CompoundPropertyModel;

public class RegistrazioneAllertePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3913915699637130463L;

  protected int index = 1;

  public RegistrazioneAllertePage() {
    this(null, 1);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RegistrazioneAllertePage(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      int index) {

    log.debug("CP RegistrazioneAllertePage = " + datiCompleti);

    this.index = index;

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel datiCompletiRegistrazione =
        new DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel("datiCompleti", datiCompleti);
    addOrReplace(datiCompletiRegistrazione);

    setOutputMarkupId(true);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RegistrazioneAllertePage(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      Integer index,
      boolean isDaVerificaPericolositaStrada,
      FeaturesGeoserver featuresGeoserverDaVerificaPericolosita) {

    this.index = index;

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel datiCompletiRegistrazione =
        new DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel(
            "datiCompleti",
            datiCompleti,
            isDaVerificaPericolositaStrada,
            featuresGeoserverDaVerificaPericolosita);
    addOrReplace(datiCompletiRegistrazione);

    setOutputMarkupId(true);
  }
}
