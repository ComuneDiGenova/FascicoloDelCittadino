package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.LinguaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.LinguaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.LinguaNoItaliaDrowDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.TipoTelefonoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class RegistrazioneAllertePanel extends BasePanel {

  private static final long serialVersionUID = -2393588124844299119L;

  private int index;
  private String email;

  public RegistrazioneAllertePanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    this.setIndex(index);
    fillDati(datiCompleti);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa>) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("RegistrazioneAllertePanel.titolo")));

    FdCTextField nome =
        new FdCTextField(
            "nome",
            datiCompleti.bind("dettagliUtenteZonaRossa.nome"),
            RegistrazioneAllertePanel.this);
    nome.getTextField().setRequired(true);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            datiCompleti.bind("dettagliUtenteZonaRossa.cognome"),
            RegistrazioneAllertePanel.this);
    cognome.getTextField().setRequired(true);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale",
            datiCompleti.bind("dettagliUtenteZonaRossa.codiceFiscale"),
            RegistrazioneAllertePanel.this);
    codiceFiscale.getTextField().setRequired(true);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCEmailTextField eMail =
        new FdCEmailTextField(
            "eMail",
            datiCompleti.bind("dettagliUtenteZonaRossa.eMail"),
            RegistrazioneAllertePanel.this);
    eMail.getTextField().setRequired(true);
    addOrReplace(eMail);

    FdCPhoneNumberField numero =
        new FdCPhoneNumberField(
            "numero",
            datiCompleti.bind("dettagliUtenteZonaRossa.numero"),
            RegistrazioneAllertePanel.this);
    numero.getTextField().setRequired(true);
    addOrReplace(numero);

    TipoTelefonoDropDownChoice tipo =
        new TipoTelefonoDropDownChoice("tipo", datiCompleti.bind("dettagliUtenteZonaRossa.tipo"));
    tipo.setRequired(true);
    tipo.setLabel(Model.of("Tipo telefono"));
    addOrReplace(tipo);

    LinguaDropDownChoice lingua =
        new LinguaDropDownChoice("lingua", datiCompleti.bind("dettagliUtenteZonaRossa.lingua"));
    lingua.setLabel(Model.of("Lingua"));
    addOrReplace(lingua);

    WebMarkupContainer wmkLinguaNoItalia = new WebMarkupContainer("wmkLinguaNoItalia");

    wmkLinguaNoItalia.setOutputMarkupId(true);
    wmkLinguaNoItalia.setOutputMarkupPlaceholderTag(true);

    addOrReplace(wmkLinguaNoItalia);

    LinguaNoItaliaDrowDownChoice linguaNoItalia =
        new LinguaNoItaliaDrowDownChoice(
            "linguaNoItalia", datiCompleti.bind("dettagliUtenteZonaRossa.linguaNoItalia"));
    linguaNoItalia.setLabel(Model.of("Lingua non italiana"));
    linguaNoItalia.setNullValid(true);
    wmkLinguaNoItalia.addOrReplace(linguaNoItalia);

    lingua.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 4567199981239849845L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            boolean linguaNoItaliaAbilitata =
                lingua.getModelObject().equals(LinguaEnum.BUONA_SOLO_SE_IN_LINGUA_STRANIERA);
            linguaNoItalia.setEnabled(linguaNoItaliaAbilitata);
            linguaNoItalia.setRequired(linguaNoItaliaAbilitata);

            if (!linguaNoItaliaAbilitata) {

              target.appendJavaScript("");
              linguaNoItalia.setModelObject(null);
            }
            target.add(wmkLinguaNoItalia);
          }
        });
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
