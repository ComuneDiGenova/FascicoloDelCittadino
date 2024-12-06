package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.form.PrivacyForm;

public class PrivacyPanel extends BasePanel {

  private static final long serialVersionUID = 8241192045685630741L;

  public PrivacyPanel(String id) {
    super(id);
    fillDati("");
  }

  public PrivacyPanel() {
    this("privacy");
  }

  @Override
  public void fillDati(Object dati) {

    PrivacyForm form = new PrivacyForm("form");
    add(form);
  }
}
