package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class BreadcrumbMieIstanzePanel extends BasePanel {

  private static final long serialVersionUID = -9204675645246630221L;

  public BreadcrumbMieIstanzePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {}
}
