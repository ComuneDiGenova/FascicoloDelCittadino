package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato;

import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.panel.ScadenzeEVersatoPanel;

public class ScadenzeEVersatoPage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = -1519953776775543410L;

  public ScadenzeEVersatoPage() {
    super();
    this.initPage(HomeWebApplication.ID_SCADENZEEVERSATO_OFTRIBUTI_OFHOME);
  }

  private void initPage(final String idPage) {
    // 1 - breadcrumb > path per navigare nel sito
    // add(new BreadcrumbBasePanel(idPage));
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    // 2 - header > indicazioni aggiuntive per informazioni su pagina
    // visitata (messaggi)
    // add(new InfoBasePanel(idPage));
    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    // 4 - content > NO TAB solo panel
    addOrReplace(createContent("contentPanel"));
  }

  private BasePanelGenericContent createContent(final String idElement) {
    return new ScadenzeEVersatoPanel(idElement);
  }
}
