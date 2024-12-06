package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;

public class DatiInformativaPrivacyPanel extends BasePanel {
  private static final long serialVersionUID = 198797543213219879L;

  boolean ck1;

  public DatiInformativaPrivacyPanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    CheckBox chbox = new CheckBox("ckUno", Model.of(ck1));
    addOrReplace(chbox);
  }
}
