package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.verificapericolositastrada;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.verificapericolositastrada.panel.VerificaPericolositaStradaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class VerificaPericolositaStradaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 19970381952786034L;

  public VerificaPericolositaStradaPage() {

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziAllerteZonaRossa().popolaListaMessaggiZonaRossa();

    @SuppressWarnings("unchecked")
    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    VerificaPericolositaStradaPanel aggiungiCivicoPanel =
        new VerificaPericolositaStradaPanel("aggiungiCivicoPanel");

    addOrReplace(aggiungiCivicoPanel);
  }
}
