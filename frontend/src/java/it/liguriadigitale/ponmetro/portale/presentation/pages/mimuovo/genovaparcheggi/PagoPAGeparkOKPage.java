package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc.PagoPAGeparkKOPanel;
import java.util.List;
import org.apache.wicket.Component;

public class PagoPAGeparkOKPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7500150767079570649L;

  public PagoPAGeparkOKPage() {

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));

    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziGenovaParcheggi().popolaListaMessaggiOK();

    @SuppressWarnings("unchecked")
    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    PagoPAGeparkKOPanel panel = new PagoPAGeparkKOPanel("PagoPAKOPanel");
    panel.fillDati("");
    addOrReplace(panel);
  }
}
