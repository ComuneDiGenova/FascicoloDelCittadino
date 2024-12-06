package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.panel.RimborsiTariNetribePanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import java.util.ArrayList;
import java.util.List;

public class RimborsiTariNetribePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5397135723035903922L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RimborsiTariNetribePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<TARIRimborsi> listaRimborsi = getListaRimborsi();
    RimborsiTariNetribePanel rimborsiPanel =
        new RimborsiTariNetribePanel("rimborsiPanel", listaRimborsi);
    addOrReplace(rimborsiPanel);
  }

  private List<TARIRimborsi> getListaRimborsi() {
    List<TARIRimborsi> listaRimborsi = new ArrayList<TARIRimborsi>();
    try {
      listaRimborsi =
          ServiceLocator.getInstance()
              .getServiziTariRimborsiNetribe()
              .getRimborsiTARIAnnoCorrente(getUtente().getCodiceFiscaleOperatore());
    } catch (ApiException | BusinessException e) {
      log.error("Errore getRimborsiOrdinati: " + e.getMessage());
    }
    return listaRimborsi;
  }
}
