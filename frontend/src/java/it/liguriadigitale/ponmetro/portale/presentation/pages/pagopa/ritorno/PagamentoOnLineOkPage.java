package it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.ritorno;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.PagoPaOkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.ritorno.panel.breadcrumb.BreadcrumbPagamentoOnLineOkPanel;

public class PagamentoOnLineOkPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8428072658612346781L;

  public PagamentoOnLineOkPage() {
    super();

    BreadcrumbPagamentoOnLineOkPanel breadcrumbPagamentoOnLineOkPanel =
        new BreadcrumbPagamentoOnLineOkPanel("breadcrumbPanel");
    add(breadcrumbPagamentoOnLineOkPanel);

    PagoPaOkPanel panel = (PagoPaOkPanel) new PagoPaOkPanel("pagaOkPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
