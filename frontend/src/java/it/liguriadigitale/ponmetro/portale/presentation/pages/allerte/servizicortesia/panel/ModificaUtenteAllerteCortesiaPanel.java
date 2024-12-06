package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;

public class ModificaUtenteAllerteCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = 6559053369607067280L;

  private int index;

  public ModificaUtenteAllerteCortesiaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti,
      int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiCompleti);
    this.index = index;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiRegistrazioneUtente =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia>) dati;

    add(new FdCTitoloPanel("titolo", getString("ModificaUtenteAllerteCortesiaPanel.titolo")));

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.email"),
            ModificaUtenteAllerteCortesiaPanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCPhoneNumberField telefonoFisso =
        new FdCPhoneNumberField(
            "telefonoFisso",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.telefonoFisso"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(telefonoFisso);
    telefonoFisso.setVisible(false);

    FdCTextField indirizzo =
        new FdCTextField<Component>(
            "indirizzo",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.indirizzo"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(indirizzo);
    indirizzo.setVisible(false);

    FdCTextField comune =
        new FdCTextField<Component>(
            "comune",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.comune"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(comune);
    comune.setVisible(false);

    FdCTextField cap =
        new FdCTextField<Component>(
            "cap",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.cap"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(cap);
    cap.setVisible(false);

    FdCTextField provincia =
        new FdCTextField<Component>(
            "provincia",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.provincia"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(provincia);
    provincia.setVisible(false);

    FdCTextField nazione =
        new FdCTextField<Component>(
            "nazione",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.nazione"),
            ModificaUtenteAllerteCortesiaPanel.this);
    addOrReplace(nazione);
    nazione.setVisible(false);
  }
}
