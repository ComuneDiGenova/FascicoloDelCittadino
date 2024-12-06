package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel.HelpCzRMDettagliDomandePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo.CzrmMailCommenti;
import java.util.List;

public class HelpCzRMDettagliDomandePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8474943222085004460L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public HelpCzRMDettagliDomandePage(List<CzrmMailCommenti> listaMailCommenti, String caseNumber) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    HelpCzRMDettagliDomandePanel panel =
        new HelpCzRMDettagliDomandePanel("dettagliPanel", listaMailCommenti, caseNumber);
    addOrReplace(panel);
  }
}
