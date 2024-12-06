package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Civico;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.dettagli.DettagliCivicoZonaRossaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class DettagliCivicoZonaRossaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4897384784686234004L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DettagliCivicoZonaRossaPage(Civico civico) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DettagliCivicoZonaRossaPanel dettagliCivicoPanel =
        new DettagliCivicoZonaRossaPanel("dettagliCivicoPanel", civico);
    addOrReplace(dettagliCivicoPanel);

    setOutputMarkupId(true);
  }
}
