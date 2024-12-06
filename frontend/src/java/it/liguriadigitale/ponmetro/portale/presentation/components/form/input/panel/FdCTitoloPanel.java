package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class FdCTitoloPanel extends BasePanel {

  private static final long serialVersionUID = -4552017662447140033L;

  public FdCTitoloPanel(String id, String testo) {
    super(id);

    fillDati(testo);
  }

  @Override
  public void fillDati(Object dati) {
    String testo = (String) dati;

    NotEmptyLabel testoTitolo = new NotEmptyLabel("testoTitolo", testo);
    addOrReplace(testoTitolo);
  }
}
