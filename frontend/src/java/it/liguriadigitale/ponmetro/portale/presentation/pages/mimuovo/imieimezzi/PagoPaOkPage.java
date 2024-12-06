package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.PagoPaOkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.breadcrumb.BreadcrumbPagoPaOkPanel;

public class PagoPaOkPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8428072658612346781L;

  public PagoPaOkPage() {
    super();

    BreadcrumbPagoPaOkPanel breadcrumbPagoPaKoPanel =
        (BreadcrumbPagoPaOkPanel)
            new BreadcrumbPagoPaOkPanel("breadcrumbPanel").setRenderBodyOnly(true);
    add(breadcrumbPagoPaKoPanel);

    PagoPaOkPanel panel = (PagoPaOkPanel) new PagoPaOkPanel("pagaOkPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
