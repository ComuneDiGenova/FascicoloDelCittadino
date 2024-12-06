package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico.panel.InfoTrafficoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico.panel.MappaInfoTrafficoPanel;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class InfoTrafficoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2925368097070985581L;

  WebMarkupContainer container = new WebMarkupContainer("mappa");

  MappaInfoTrafficoPanel mappaInfoTrafficoPanel;

  public InfoTrafficoPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    InfoTrafficoPanel infoTrafficoPanel = new InfoTrafficoPanel("infoTrafficoPanel");
    List<DatiSegnalazioneTraffico> lista = popolaListaInfoTraffico();
    infoTrafficoPanel.fillDati(lista);
    add(infoTrafficoPanel);

    mappaInfoTrafficoPanel =
        (MappaInfoTrafficoPanel)
            new MappaInfoTrafficoPanel("mappaInfoTrafficoPanel").setOutputMarkupId(true);
    mappaInfoTrafficoPanel.fillDati("");
    add(mappaInfoTrafficoPanel);

    setOutputMarkupId(true);
  }

  private List<DatiSegnalazioneTraffico> popolaListaInfoTraffico() {
    try {
      return ServiceLocator.getInstance()
          .getServiziSegnalazioneTraffico()
          .getListSegnalazioniTraffico();
    } catch (BusinessException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
