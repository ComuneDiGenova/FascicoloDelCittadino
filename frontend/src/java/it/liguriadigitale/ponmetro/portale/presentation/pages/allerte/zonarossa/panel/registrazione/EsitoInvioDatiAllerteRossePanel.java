package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class EsitoInvioDatiAllerteRossePanel extends BasePanel {

  private static final long serialVersionUID = -8336726524155066629L;

  private Integer index;

  public EsitoInvioDatiAllerteRossePanel(String id, Integer index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    log.debug("CP fillDati esito allerte rosse");
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }
}
