package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze;

import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.TributiScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.info.InfoBasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.legend.LegendBasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.panel.TributiScadenzePanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

public class TributiScadenzePage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = 1L;
  protected Log log = LogFactory.getLog(getClass());

  private TributiScadenzeExt scadenzeExt = null;

  public TributiScadenzePage() {
    super();
    // chiamo una sola volta e poi "filtro" senza richiamare servizio
    scadenzeExt = getScadenzeExt();
    this.initPage(HomeWebApplication.ID_TRIBSCADENZE_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME);
  }

  static final String URL_PER_CALCOLARE =
      "https://calcolotributi.comune.genova.it/portale-tributi-tasi/";

  private ExternalLink createLinkEsternoCalcola(String idWicket) {
    ExternalLink btnCalcola = new ExternalLink(idWicket, URL_PER_CALCOLARE);
    return btnCalcola;
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
    InfoBasePanel infoBasePanel =
        new InfoBasePanel(idPage, createLinkEsternoCalcola("bottone1"), 0);
    add(infoBasePanel);

    LegendBasePanel legend =
        new LegendBasePanel(
            "legendPanel", "titoloScadenzeAccertato", AccertatoScadenzeExt.stati, 4);
    add(legend);

    // 4 - content > tabs
    addOrReplace(createTabAndContent(this));
  }

  @Override
  protected Panel getPanelRisultatoByYear(String panelId, Integer annoIesimo) {
    TributiScadenzeExt scadenzeExt = getScadenzeExtFilteredByYear(annoIesimo);
    TributiScadenzePanel scadenzePanel = new TributiScadenzePanel(panelId, annoIesimo);
    scadenzePanel.fillDati(scadenzeExt);
    return scadenzePanel;
  }

  private TributiScadenzeExt getScadenzeExtFilteredByYear(Integer annoIesimo) {
    try {
      return ServiceLocator.getInstance()
          .getServiziQuadroTributario()
          .getScadenzeExtFilteredByYear(scadenzeExt, getUtente(), annoIesimo);
    } catch (ApiException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  private TributiScadenzeExt getScadenzeExt() {
    try {
      return ServiceLocator.getInstance().getServiziQuadroTributario().getScadenzeExt(getUtente());
    } catch (ApiException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }
}
