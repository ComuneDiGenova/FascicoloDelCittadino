package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.modifica;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;

public class ModificaUtenteServiziCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = 8271975975470846734L;

  private int index;

  public ModificaUtenteServiziCortesiaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti,
      int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiCompleti);
    this.setIndex(index);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiRegistrazioneUtente =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia>) dati;

    add(new FdCTitoloPanel("titolo", getString("ModificaUtenteServiziCortesiaPanel.titolo")));

    FdCTextField nome =
        new FdCTextField<Component>(
            "nome",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.nome"),
            ModificaUtenteServiziCortesiaPanel.this);
    nome.setRequired(true);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField<Component>(
            "cognome",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.cognome"),
            ModificaUtenteServiziCortesiaPanel.this);
    cognome.setRequired(true);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField<Component>(
            "codiceFiscale",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.codiceFiscale"),
            ModificaUtenteServiziCortesiaPanel.this);
    codiceFiscale.setRequired(true);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.email"),
            ModificaUtenteServiziCortesiaPanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCPhoneNumberField telefonoCellulare =
        new FdCPhoneNumberField(
            "telefonoCellulare",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.telefonoCellulare"),
            ModificaUtenteServiziCortesiaPanel.this);
    addOrReplace(telefonoCellulare);

    FdCPhoneNumberField telefonoFisso =
        new FdCPhoneNumberField(
            "telefonoFisso",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.telefonoFisso"),
            ModificaUtenteServiziCortesiaPanel.this);
    addOrReplace(telefonoFisso);

    FdCTextField indirizzo =
        new FdCTextField<Component>(
            "indirizzo",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.indirizzo"),
            ModificaUtenteServiziCortesiaPanel.this);
    indirizzo.setEnabled(!getUtente().isResidente());
    addOrReplace(indirizzo);

    FdCTextField comune =
        new FdCTextField<Component>(
            "comune",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.comune"),
            ModificaUtenteServiziCortesiaPanel.this);
    comune.setEnabled(!getUtente().isResidente());
    addOrReplace(comune);

    FdCTextField cap =
        new FdCTextField<Component>(
            "cap",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.cap"),
            ModificaUtenteServiziCortesiaPanel.this);
    cap.setEnabled(!getUtente().isResidente());
    addOrReplace(cap);

    FdCTextField provincia =
        new FdCTextField<Component>(
            "provincia",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.provincia"),
            ModificaUtenteServiziCortesiaPanel.this);
    provincia.setEnabled(!getUtente().isResidente());
    addOrReplace(provincia);

    FdCTextField nazione =
        new FdCTextField<Component>(
            "nazione",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.nazione"),
            ModificaUtenteServiziCortesiaPanel.this);
    addOrReplace(nazione);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
