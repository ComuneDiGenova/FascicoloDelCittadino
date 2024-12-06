package it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.ritorno;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.PagoPaKoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.ritorno.panel.breadcrumb.BreadcrumbPagamentoOnLineKoPanel;

public class PagamentoOnLineKoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8428072658612346781L;

  public PagamentoOnLineKoPage() {
    super();

    BreadcrumbPagamentoOnLineKoPanel breadcrumbPagamentoOnLineKoPanel =
        new BreadcrumbPagamentoOnLineKoPanel("breadcrumbPanel");
    add(breadcrumbPagamentoOnLineKoPanel);

    PagoPaKoPanel panel = (PagoPaKoPanel) new PagoPaKoPanel("pagaKoPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
