package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungicomponente.AggiungiComponenteZonaRossaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class AggiungiComponenteZonaRossaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1623969194253172038L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AggiungiComponenteZonaRossaPage(ComponenteNucleoZonaRossa componente) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AggiungiComponenteZonaRossaPanel aggiungiComponentePanel =
        new AggiungiComponenteZonaRossaPanel("aggiungiComponentePanel", componente);
    addOrReplace(aggiungiComponentePanel);

    setOutputMarkupId(true);
  }
}
