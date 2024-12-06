package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class EsitoDomandaBorsaStudioPanel extends BasePanel {

  private static final long serialVersionUID = 6631408415211410819L;

  private int index;

  public EsitoDomandaBorsaStudioPanel(String id, PraticaBorseStudioExtend datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @Override
  public void fillDati(Object dati) {}
}
