package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiDocumentiTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.panel.DettagliQuadroTributarioTariPanel;
import java.util.List;
import org.apache.wicket.Component;

public class DettagliQuadroTributarioTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5702479001077208680L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DettagliQuadroTributarioTariPage(DatiDocumentiTariEng documento) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziTariEng()
            .popolaListaMessaggiQuadroDettagli(documento);

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    DettagliQuadroTributarioTariPanel dettagliPanel =
        new DettagliQuadroTributarioTariPanel("dettagliPanel");
    dettagliPanel.fillDati(documento);
    addOrReplace(dettagliPanel);
  }
}
