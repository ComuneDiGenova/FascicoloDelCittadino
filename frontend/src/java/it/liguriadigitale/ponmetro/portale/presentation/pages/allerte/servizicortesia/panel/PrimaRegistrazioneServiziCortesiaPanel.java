package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;

public class PrimaRegistrazioneServiziCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = 8271975975470846734L;

  private int index;

  public PrimaRegistrazioneServiziCortesiaPanel(
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

    add(new FdCTitoloPanel("titolo", getString("PrimaRegistrazioneServiziCortesiaPanel.titolo")));

    FdCTextField nome =
        new FdCTextField<Component>(
            "nome",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.nome"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    nome.setRequired(true);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField<Component>(
            "cognome",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.cognome"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    cognome.setRequired(true);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField<Component>(
            "codiceFiscale",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.codiceFiscale"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    codiceFiscale.setRequired(true);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.email"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCPhoneNumberField telefonoCellulare =
        new FdCPhoneNumberField(
            "telefonoCellulare",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.telefonoCellulare"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    addOrReplace(telefonoCellulare);

    FdCPhoneNumberField telefonoFisso =
        new FdCPhoneNumberField(
            "telefonoFisso",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.telefonoFisso"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    addOrReplace(telefonoFisso);
    telefonoFisso.setVisible(false);

    FdCTextField indirizzo =
        new FdCTextField<Component>(
            "indirizzo",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.indirizzo"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    indirizzo.setEnabled(!getUtente().isResidente());
    addOrReplace(indirizzo);
    indirizzo.setVisible(false);

    FdCTextField comune =
        new FdCTextField<Component>(
            "comune",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.comune"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    comune.setEnabled(!getUtente().isResidente());
    addOrReplace(comune);
    comune.setVisible(false);

    FdCTextField cap =
        new FdCTextField<Component>(
            "cap",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.cap"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    cap.setEnabled(!getUtente().isResidente());
    addOrReplace(cap);
    cap.setVisible(false);

    FdCTextField provincia =
        new FdCTextField<Component>(
            "provincia",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.provincia"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    provincia.setEnabled(!getUtente().isResidente());
    addOrReplace(provincia);
    provincia.setVisible(false);

    FdCTextField nazione =
        new FdCTextField<Component>(
            "nazione",
            datiRegistrazioneUtente.bind("datiRegistrazioneAllerteCortesia.nazione"),
            PrimaRegistrazioneServiziCortesiaPanel.this);
    addOrReplace(nazione);
    nazione.setVisible(false);
  }
}
