package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.FascicoloTabContentPagamentiMIPPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.RicercaDebitoFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.breadcrumb.BreadcrumbPagamentiFormPanel;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class PagamentiFormPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5503476344050929558L;

  private Component pagamentiPanel;

  public PagamentiFormPage(List<TipologiaEntrata> listaTipologiaEntrata) {
    super();

    BreadcrumbPagamentiFormPanel breadcrumbPagamentiFormPanel =
        new BreadcrumbPagamentiFormPanel("breadcrumbPanel");
    breadcrumbPagamentiFormPanel.fillDati("");
    addOrReplace(breadcrumbPagamentiFormPanel);

    RicercaDebitoFormPanel ricercaDebitoFormPanel =
        new RicercaDebitoFormPanel(
            "pagamentiFormPanel", new TipologiaEntrata(), listaTipologiaEntrata);
    addOrReplace(ricercaDebitoFormPanel);

    pagamentiPanel = new EmptyPanel("tabsPanel").setRenderBodyOnly(true);
    addOrReplace(pagamentiPanel);

    setOutputMarkupId(true);
  }

  public void aggiornaPagamenti(List<DebitoMipFascicoloDatiGenerali> listaDebiti) {
    if (pagamentiPanel.getParent() == null) {
      pagamentiPanel = new FascicoloTabContentPagamentiMIPPanel(listaDebiti);
      addOrReplace(pagamentiPanel);
    } else {
      pagamentiPanel.replaceWith(new FascicoloTabContentPagamentiMIPPanel(listaDebiti));
    }
  }
}
