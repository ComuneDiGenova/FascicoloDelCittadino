package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class BreadcrumbMieIstanzeFormPanel extends BasePanel {

  private static final long serialVersionUID = -6104712421351444153L;

  public BreadcrumbMieIstanzeFormPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {}
}
