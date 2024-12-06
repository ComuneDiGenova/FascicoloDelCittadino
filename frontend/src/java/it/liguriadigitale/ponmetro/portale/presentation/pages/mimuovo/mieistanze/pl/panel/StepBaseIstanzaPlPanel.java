package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class StepBaseIstanzaPlPanel extends BasePanel {

  protected Integer index;

  protected DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;

  public StepBaseIstanzaPlPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

  }
}
