package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.FascicoloTabContentPagamentiMIPPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.HeaderPagamentiPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PagamentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1748226041376816777L;

  public PagamentiPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HeaderPagamentiPanel headerPagamentiPanel = new HeaderPagamentiPanel("headerPagamentiPanel");
    headerPagamentiPanel.fillDati("");
    add(headerPagamentiPanel);

    List<ITab> listaTabsPagamenti = new ArrayList<>();
    AbstractTab tabNonPagati =
        new AbstractTab(new Model<>(getString("PagamentiPage.nonPagati"))) {

          private static final long serialVersionUID = 1723668265543971566L;

          @Override
          public Panel getPanel(String panelId) {
            boolean flag = true;
            List<DebitoMipFascicoloDatiGenerali> listaDebitiDatiGenerali =
                popolaListaPagamentiDatiGeneraliFiltratiSenzaVerbaliSenzaTariEng(flag);
            return new FascicoloTabContentPagamentiMIPPanel(panelId, listaDebitiDatiGenerali);
          }
        };
    AbstractTab tabTuttiPagamenti =
        new AbstractTab(new Model<>(getString("PagamentiPage.tutti"))) {

          private static final long serialVersionUID = -3154151311133325252L;

          @Override
          public Panel getPanel(String panelId) {
            boolean flag = false;
            List<DebitoMipFascicoloDatiGenerali> listaDebitiDatiGenerali =
                popolaListaPagamentiDatiGeneraliFiltratiSenzaVerbaliSenzaTariEng(flag);
            return new FascicoloTabContentPagamentiMIPPanel(panelId, listaDebitiDatiGenerali);
          }
        };
    listaTabsPagamenti.add(tabNonPagati);
    listaTabsPagamenti.add(tabTuttiPagamenti);
    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<>("tabsPanel", listaTabsPagamenti);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);

    setOutputMarkupId(true);
  }

  private List<DebitoMipFascicoloDatiGenerali>
      popolaListaPagamentiDatiGeneraliFiltratiSenzaVerbaliSenzaTariEng(boolean flagSoloNonPagati) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltratiSenzaVerbaliSenzaTariEngSenzaTriNetribe(
              getUtente(), flagSoloNonPagati);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }
}
