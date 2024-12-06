package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.privacy.ServiziCedolePrivacyForm;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class CedoleLibrariePrivacyPanel extends BasePanel {

  private static final long serialVersionUID = -3151336481926758355L;

  UtenteServiziRistorazione iscritto;

  public CedoleLibrariePrivacyPanel(String id, UtenteServiziRistorazione iscritto) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.iscritto = iscritto;

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    ServiziCedolePrivacyForm form = new ServiziCedolePrivacyForm("form", getUtente(), iscritto);
    form.setOutputMarkupId(true);
    addOrReplace(form);
  }
}
