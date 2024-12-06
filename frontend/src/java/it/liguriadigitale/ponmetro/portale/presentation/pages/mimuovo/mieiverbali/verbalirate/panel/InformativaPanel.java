package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;

public class InformativaPanel extends BasePanel {

  private static final long serialVersionUID = 1L;

  boolean ck1;

  public InformativaPanel(String id, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id);
    // TODO Auto-generated constructor stub

    fillDati(datiRichiestaIstanzaPl);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl = (DatiRichiestaIstanzaPl) dati;

    CheckBox ck1 =
        new CheckBox(
            "ckUno",
            new PropertyModel<Boolean>(datiRichiestaIstanzaPl, "checkInformativaRateizzazione"));
    addOrReplace(ck1);
  }
}
