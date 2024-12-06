package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CancellaComponenteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.cancellacomponente.CancellaComponenteAllerteRossePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class CancellaComponenteAllerteRossePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8992744610332728583L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public CancellaComponenteAllerteRossePage(CancellaComponenteZonaRossa cancellaComponente) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziAllerteZonaRossa()
            .popolaListaMessaggiCancellaComponente();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    CancellaComponenteAllerteRossePanel cancellaComponentePanel =
        new CancellaComponenteAllerteRossePanel("cancellaComponentePanel", cancellaComponente);
    addOrReplace(cancellaComponentePanel);

    setOutputMarkupId(true);
  }
}
