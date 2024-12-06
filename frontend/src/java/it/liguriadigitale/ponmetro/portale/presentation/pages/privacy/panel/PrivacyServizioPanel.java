package it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.form.PrivacyServizioForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;

public class PrivacyServizioPanel extends BasePanel {

  private static final long serialVersionUID = -127711434222446814L;

  private PrivacyServizio privacyServizio;

  public PrivacyServizioPanel(String id, PrivacyServizio privacyServizio) {
    super(id);

    this.privacyServizio = privacyServizio;

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    PrivacyServizioForm form = new PrivacyServizioForm("form", privacyServizio);
    form.setOutputMarkupId(true);
    addOrReplace(form);
  }
}
