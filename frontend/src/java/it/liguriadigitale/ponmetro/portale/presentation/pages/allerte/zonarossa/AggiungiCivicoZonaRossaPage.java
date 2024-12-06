package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungicivico.AggiungiCivicoZonaRossaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.model.CompoundPropertyModel;

public class AggiungiCivicoZonaRossaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2738884820371587321L;

  public AggiungiCivicoZonaRossaPage() {
    this(null);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AggiungiCivicoZonaRossaPage(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AggiungiCivicoZonaRossaPanel aggiungiCivicoPanel =
        new AggiungiCivicoZonaRossaPanel("aggiungiCivicoPanel", datiCompleti);
    addOrReplace(aggiungiCivicoPanel);

    setOutputMarkupId(true);
  }
}
