package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungitelefono.AggiungiTelefonoZonaRossaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class AggiungiTelefonoZonaRossaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1240495345956697152L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AggiungiTelefonoZonaRossaPage(ContattoTelefonicoZonaRossa contattoTelefonico) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AggiungiTelefonoZonaRossaPanel aggiungiTelefonoPanel =
        new AggiungiTelefonoZonaRossaPanel("aggiungiTelefonoPanel", contattoTelefonico);
    addOrReplace(aggiungiTelefonoPanel);

    setOutputMarkupId(true);
  }
}
