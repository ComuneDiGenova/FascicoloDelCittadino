package it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP.panel.PagamentiPagoPaMipKOPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP.panel.breadcrumb.BreadcrumbPagamentiPagoPaMipKOPanel;

public class PagamentiPagoPaMipKOPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6277837065883213009L;

  public PagamentiPagoPaMipKOPage() {
    super();

    BreadcrumbPagamentiPagoPaMipKOPanel breadcrumbPagamentiPagoPaMipKOPanel =
        new BreadcrumbPagamentiPagoPaMipKOPanel("breadcrumb");
    add(breadcrumbPagamentiPagoPaMipKOPanel);

    PagamentiPagoPaMipKOPanel panel =
        (PagamentiPagoPaMipKOPanel)
            new PagamentiPagoPaMipKOPanel("pagamentiMIPKOPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
