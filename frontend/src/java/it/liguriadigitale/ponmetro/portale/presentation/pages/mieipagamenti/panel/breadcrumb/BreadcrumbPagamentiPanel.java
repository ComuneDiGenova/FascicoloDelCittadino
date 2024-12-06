package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class BreadcrumbPagamentiPanel extends BasePanel {

  private static final long serialVersionUID = 5859848886051923394L;

  public BreadcrumbPagamentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {}
}
