package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.panel;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EsitoIscrizioneMensaNonResidentePanel extends BasePanel {

  private static final long serialVersionUID = 5716438084493531203L;

  private int index;

  public EsitoIscrizioneMensaNonResidentePanel(
      String id, CompoundPropertyModel<DatiIscrizioneMensaNonResidente> datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @Override
  public void fillDati(Object dati) {}
}
