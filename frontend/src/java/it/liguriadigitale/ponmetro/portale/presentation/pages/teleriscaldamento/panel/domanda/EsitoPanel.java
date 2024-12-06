package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class EsitoPanel extends BasePanel {

  private int index;

  public EsitoPanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaTeleriscaldamento datiDomanda = (DatiDomandaTeleriscaldamento) dati;
  }
}
