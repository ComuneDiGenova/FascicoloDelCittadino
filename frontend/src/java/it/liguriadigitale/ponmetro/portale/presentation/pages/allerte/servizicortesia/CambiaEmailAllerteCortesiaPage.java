package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.CambiaEmailAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class CambiaEmailAllerteCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7515514813905906633L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public CambiaEmailAllerteCortesiaPage(DettagliUtente dettagliUtente) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziAllerteCortesia().popolaListaMessaggiVerificaEmail();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    CambiaEmailAllerteCortesiaPanel cambiaEmailPanel =
        new CambiaEmailAllerteCortesiaPanel("cambiaEmailPanel", dettagliUtente);
    addOrReplace(cambiaEmailPanel);

    setOutputMarkupId(true);
  }
}
