package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.allertecortesia.model.Utente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.CambiaCellulareAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class CambiaCellulareAllerteCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6230419962491480430L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public CambiaCellulareAllerteCortesiaPage(Utente datiUtente) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziAllerteCortesia()
            .popolaListaMessaggiCambiaCellulare();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    CambiaCellulareAllerteCortesiaPanel cambiaCellularePanel =
        new CambiaCellulareAllerteCortesiaPanel("cambiaCellularePanel", datiUtente);
    addOrReplace(cambiaCellularePanel);

    setOutputMarkupId(true);
  }
}
