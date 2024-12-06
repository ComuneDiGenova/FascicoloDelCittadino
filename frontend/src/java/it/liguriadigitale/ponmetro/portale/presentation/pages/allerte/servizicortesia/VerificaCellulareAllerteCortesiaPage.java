package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.VerificaCellulareAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class VerificaCellulareAllerteCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2192111354334307619L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public VerificaCellulareAllerteCortesiaPage(DatiVerificaCellulareAllerteCortesia datiVerifica) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziAllerteCortesia()
            .popolaListaMessaggiVerificaCellulare();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    VerificaCellulareAllerteCortesiaPanel verificaCellularePanel =
        new VerificaCellulareAllerteCortesiaPanel("verificaCellularePanel", datiVerifica);
    addOrReplace(verificaCellularePanel);

    setOutputMarkupId(true);
  }
}
