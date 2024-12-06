package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.TipologiaIscrizioneDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import java.time.LocalDate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiGeneraliForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = -7349674609304452360L;

  private Utente utente;

  private String email;

  private String telefono;

  public DatiGeneraliForm(String id, VariazioniResidenza model, Utente utente) {
    super(id, model);

    this.setUtente(utente);

    addElementiForm(utente);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public void addElementiForm(Utente utente) {
    Label cf = new Label("cf", getUtente().getCodiceFiscaleOperatore());
    addOrReplace(cf);

    String nomeCognome = getUtente().getNome().concat(" ").concat(getUtente().getCognome());
    Label nominativo = new Label("nominativo", nomeCognome);
    addOrReplace(nominativo);

    if (LabelFdCUtil.checkIfNotNull(utente.getMail())) {
      if (LabelFdCUtil.checkIfNotNull(getModelObject().getEmailRichiedente())
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmailRichiedente())) {
        getModelObject().setEmailRichiedente(getModelObject().getEmailRichiedente());
      } else {
        getModelObject().setEmailRichiedente(utente.getMail());
      }
    }
    EmailTextField emailRichiedente =
        new EmailTextField(
            "emailRichiedente", new PropertyModel<String>(getModelObject(), "emailRichiedente"));
    emailRichiedente.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -152294056444944062L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailRichiedente.getValue());
            getModelObject().setEmailRichiedente(emailRichiedente.getValue());
          }
        });
    getModelObject().setEmailRichiedente(emailRichiedente.getValue());
    emailRichiedente.setRequired(true);
    emailRichiedente.setLabel(Model.of("Email"));
    emailRichiedente.add(StringValidator.minimumLength(9));
    addOrReplace(emailRichiedente);

    EmailTextField pec =
        new EmailTextField("pec", new PropertyModel(getModelObject(), "pecRichiedente"));
    pec.setLabel(Model.of("PEC"));
    pec.add(StringValidator.minimumLength(9));
    addOrReplace(pec);

    if (LabelFdCUtil.checkIfNotNull(utente.getMobile())) {
      if (LabelFdCUtil.checkIfNotNull(getModelObject().getTelefonoRichiedente())
          && !utente.getMobile().equalsIgnoreCase(getModelObject().getTelefonoRichiedente())) {
        getModelObject().setTelefonoRichiedente(getModelObject().getTelefonoRichiedente());
      } else {
        getModelObject().setTelefonoRichiedente(utente.getMobile());
      }
    }
    TextField telefono =
        new TextField(
            "telefono", new PropertyModel<String>(getModelObject(), "telefonoRichiedente"));
    telefono.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -8027829385285156101L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setTelefono(telefono.getValue());
            getModelObject().setTelefonoRichiedente(telefono.getValue());
          }
        });
    getModelObject().setTelefonoRichiedente(telefono.getValue());
    telefono.setRequired(true);
    telefono.setLabel(Model.of("Telefono"));
    telefono.add(new TelefonoFissoCellulareValidator());
    telefono.add(StringValidator.minimumLength(9));
    addOrReplace(telefono);

    TextField secondoTelefono =
        new TextField(
            "secondoTelefono", new PropertyModel(getModelObject(), "secondoTelefonoRichiedente"));
    secondoTelefono.setLabel(Model.of("Secondo telefono"));
    secondoTelefono.add(new TelefonoFissoCellulareValidator());
    secondoTelefono.add(StringValidator.minimumLength(9));
    addOrReplace(secondoTelefono);

    Label dataDecorrenza =
        new Label("dataDecorrenza", LocalDateUtil.getDataFormatoEuropeo(LocalDate.now()));
    addOrReplace(dataDecorrenza);

    Integer tipoPratica = 2;
    TipologiaIscrizioneDropDownChoise comboTipologiaIscrizione =
        new TipologiaIscrizioneDropDownChoise<>(
            "comboTipologiaIscrizione",
            new PropertyModel<>(getModelObject(), "comboTipologiaIscrizione"),
            tipoPratica);
    addOrReplace(comboTipologiaIscrizione);
  }

  @Override
  public void addElementiForm() {}

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}
