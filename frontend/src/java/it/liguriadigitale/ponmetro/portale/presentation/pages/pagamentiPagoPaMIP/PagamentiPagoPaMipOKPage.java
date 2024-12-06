package it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP.panel.PagamentiPagoPaMipOkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP.panel.breadcrumb.BreadcrumbPagamentiPagoPaMipOKPanel;

public class PagamentiPagoPaMipOKPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4863345665043957932L;

  public PagamentiPagoPaMipOKPage() {
    super();

    BreadcrumbPagamentiPagoPaMipOKPanel breadcrumbPagamentiPagoPaMipOKPanel =
        new BreadcrumbPagamentiPagoPaMipOKPanel("breadcrumb");
    add(breadcrumbPagamentiPagoPaMipOKPanel);

    PagamentiPagoPaMipOkPanel panel =
        (PagamentiPagoPaMipOkPanel)
            new PagamentiPagoPaMipOkPanel("pagamentiMIPOKPanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
