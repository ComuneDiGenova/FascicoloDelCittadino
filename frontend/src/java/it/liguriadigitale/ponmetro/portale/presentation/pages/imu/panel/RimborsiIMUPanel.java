package it.liguriadigitale.ponmetro.portale.presentation.pages.imu.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class RimborsiIMUPanel extends BasePanel {

  private static final String ICON_ARPAL = "color-fc-secondary col-3 icon-rimborsi-imu";

  public RimborsiIMUPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

  }
}
