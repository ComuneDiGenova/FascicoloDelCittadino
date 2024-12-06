package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.panel.ProprietaEUtenzePanel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;

public class ProprietaEUtenzePage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = -7319525914583185128L;

  public ProprietaEUtenzePage() {
    super();
    initPage(HomeWebApplication.ID_PROPRIETAEUTENZE_OFTRIBUTI_OFHOME);
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

    addOrReplace(createTabAndContent(this));
  }

  @Override
  protected Panel getPanelRisultatoByYear(String panelId, Integer annoIesimo) {
    List<ProprietaUtenzeExt> proprietaUtenze = getProprietaUtenteExt("" + annoIesimo);
    ProprietaEUtenzePanel proprietaEUtenzePanel = new ProprietaEUtenzePanel(panelId, annoIesimo);
    proprietaEUtenzePanel.setOutputMarkupId(true);
    proprietaEUtenzePanel.fillDati(proprietaUtenze);
    return proprietaEUtenzePanel;
  }

  private List<ProprietaUtenzeExt> getProprietaUtenteExt(final String annoRiferimento) {
    try {
      return ServiceLocator.getInstance()
          .getServiziQuadroTributario()
          .getProprietaUtenzeExt(getUtente(), annoRiferimento);
    } catch (ApiException e) {
      return new ArrayList<>();
    } catch (BusinessException e) {
      return null;
    }
  }
}
