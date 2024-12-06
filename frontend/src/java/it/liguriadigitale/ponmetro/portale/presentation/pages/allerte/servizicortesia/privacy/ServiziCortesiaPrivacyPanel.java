package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.privacy;

import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.privacy.form.ServiziCortesiaPrivacyForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class ServiziCortesiaPrivacyPanel extends BasePanel {

  private static final long serialVersionUID = -1845956333792478979L;

  public ServiziCortesiaPrivacyPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    ServiziCortesiaPrivacyForm form = new ServiziCortesiaPrivacyForm("form", getUtente());
    form.setOutputMarkupId(true);
    addOrReplace(form);
  }
}
