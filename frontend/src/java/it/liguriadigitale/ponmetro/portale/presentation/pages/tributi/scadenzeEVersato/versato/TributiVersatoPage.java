package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.versato;

import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.ElencoVersamentiExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.versato.panel.TributiVersatoPanel;
import it.liguriadigitale.ponmetro.tributi.model.Versamenti;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.panel.Panel;

public class TributiVersatoPage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = 1L;
  protected Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("unused")
  private ElencoVersamentiExt elencoVersamentiExt = null;

  public TributiVersatoPage() {
    super();
    // chiamo una sola volta e poi "filtro" senza richiamare servizio
    setElencoVersamentiExt(getElencoVersamentiExt());
    this.initPage(HomeWebApplication.ID_TRIBVERSATO_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME);
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

    // 4 - content > tabs
    addOrReplace(createTabAndContent(this));
  }

  @Override
  protected Panel getPanelRisultatoByYear(String panelId, Integer annoIesimo) {
    ElencoVersamentiExt elencoVersamentiExt = getElencoVersamentiExtFilteredByYear(annoIesimo);
    log.debug("GGGGGGGGGGGGGGGGGG elencoVersamentiExt: " + elencoVersamentiExt);
    TributiVersatoPanel versatoPanel = new TributiVersatoPanel(panelId, annoIesimo);
    log.debug("HHHHHHHHHHHHHHHH elencoVersamentiExt: " + versatoPanel);
    versatoPanel.fillDati(elencoVersamentiExt);
    return versatoPanel;
  }

  private ElencoVersamentiExt getElencoVersamentiExtFilteredByYear(Integer annoIesimo) {
    ElencoVersamentiExt elencoVersamentiExt = null;
    try {
      elencoVersamentiExt =
          ServiceLocator.getInstance()
              .getServiziQuadroTributario()
              .getElencoVersamentiExtFilteredByYear(elencoVersamentiExt, getUtente(), annoIesimo);
    } catch (ApiException e) {
    } catch (Exception e) {
    }
    if (elencoVersamentiExt == null) {
      elencoVersamentiExt = new ElencoVersamentiExt();
      elencoVersamentiExt.setAnnoDaTenere(annoIesimo);
      elencoVersamentiExt.setElencoVersamentiIMU(new ArrayList<Versamenti>());
    }
    return elencoVersamentiExt;
  }

  private ElencoVersamentiExt getElencoVersamentiExt() {
    ElencoVersamentiExt elencoVersamentiExt = null;
    try {
      elencoVersamentiExt =
          ServiceLocator.getInstance()
              .getServiziQuadroTributario()
              .getElencoVersamentiExt(getUtente());
    } catch (ApiException e) {
    } catch (Exception e) {
    }
    if (elencoVersamentiExt == null) {
      elencoVersamentiExt = new ElencoVersamentiExt();
      elencoVersamentiExt.setAnnoDaTenere(null);
      elencoVersamentiExt.setElencoVersamentiIMU(new ArrayList<Versamenti>());
    }
    return elencoVersamentiExt;
  }

  public void setElencoVersamentiExt(ElencoVersamentiExt elencoVersamentiExt) {
    this.elencoVersamentiExt = elencoVersamentiExt;
  }
}
