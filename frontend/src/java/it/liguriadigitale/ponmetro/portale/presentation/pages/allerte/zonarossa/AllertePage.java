package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.DettagliUtente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.AllertePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class AllertePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3649085129938200415L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AllertePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziAllerteZonaRossa().popolaListaMessaggiZonaRossa();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    DettagliUtente dettagliUtente = getDettagliUtente(getUtente().getCodiceFiscaleOperatore());
    AllertePanel zonaRossaPanel = new AllertePanel("zonaRossaPanel", dettagliUtente);

    addOrReplace(zonaRossaPanel);

    setOutputMarkupId(true);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AllertePage(FeaturesGeoserver featuresGeoserverDaVerificaPericolosita) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziAllerteZonaRossa().popolaListaMessaggiZonaRossa();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    DettagliUtente dettagliUtente = getDettagliUtente(getUtente().getCodiceFiscaleOperatore());
    AllertePanel zonaRossaPanel =
        new AllertePanel(
            "zonaRossaPanel", dettagliUtente, featuresGeoserverDaVerificaPericolosita, true);

    addOrReplace(zonaRossaPanel);

    setOutputMarkupId(true);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private DettagliUtente getDettagliUtente(String codiceFiscale) {
    try {
      return ServiceLocator.getInstance()
          .getServiziAllerteZonaRossa()
          .getDettagliUtente(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Zona Rossa"));
    }
  }
}
