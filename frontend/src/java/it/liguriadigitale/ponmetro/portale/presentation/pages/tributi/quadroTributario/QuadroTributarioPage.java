package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario;

import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.QuadroTributarioExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.TributiExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.legend.LegendBasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.panel.QuadroTributarioPanel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;

public class QuadroTributarioPage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = -1519953776775543410L;

  private List<TributiExt> listaTuttiTributi = null;

  @SuppressWarnings("unchecked")
  public QuadroTributarioPage() {
    super();
    listaTuttiTributi = getTributiExt();
    this.initPage(HomeWebApplication.ID_QUADROTRIBUTARIO_OFTRIBUTI_OFHOME);
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
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

    // 3 - legend > eventuale legenda in base agli stati degli elementi del
    // contesto
    add(new LegendBasePanel(QuadroTributarioExt.stati, 4));

    // 4 - content > tabs
    addOrReplace(createTabAndContent(this));
  }

  @Override
  protected Panel getPanelRisultatoByYear(String panelId, Integer annoIesimo) {
    List<TributiExt> tributiExtByYear = getTributiExtFilteredByYear(annoIesimo);
    QuadroTributarioPanel quadroTributarioPanel = new QuadroTributarioPanel(panelId, annoIesimo);
    quadroTributarioPanel.fillDati(tributiExtByYear);
    return quadroTributarioPanel;
  }

  private List<TributiExt> getTributiExtFilteredByYear(Integer annoIesimo) {
    try {
      return ServiceLocator.getInstance()
          .getServiziQuadroTributario()
          .getTributiExtFilteredByYear(listaTuttiTributi, null, annoIesimo);
    } catch (ApiException e) {
      return new ArrayList<>();
    } catch (Exception e) {
      return null;
    }
  }

  private List<TributiExt> getTributiExt() {
    try {
      return ServiceLocator.getInstance().getServiziQuadroTributario().getTributiExt(getUtente());
    } catch (ApiException e) {
      return new ArrayList<>();
    } catch (Exception e) {
      return null;
    }
  }
}
