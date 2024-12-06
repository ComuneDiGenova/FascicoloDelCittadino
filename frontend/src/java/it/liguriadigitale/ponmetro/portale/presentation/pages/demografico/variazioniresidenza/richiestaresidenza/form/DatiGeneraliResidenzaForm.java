package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ComuniItalianiAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.StatoAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ProvenienzaDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.TipologiaIscrizioneDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import java.time.LocalDate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiGeneraliResidenzaForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = -3722863133546087390L;

  private Utente utente;

  private String email;

  private String telefono;

  private WebMarkupContainer containerComune;

  private WebMarkupContainer containerStato;

  public DatiGeneraliResidenzaForm(String id, VariazioniResidenza model, Utente utente) {
    super(id, model);

    this.setUtente(utente);

    addElementiForm(utente);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public void addElementiForm(Utente utente) {

    containerComune = new WebMarkupContainer("containerComune");
    containerComune.setOutputMarkupId(true);
    containerComune.setOutputMarkupPlaceholderTag(true);
    boolean containerComuneVisibile =
        LabelFdCUtil.checkIfNotNull(getModelObject())
            && LabelFdCUtil.checkIfNotNull(getModelObject().getComuneProvenienza());
    containerComune.setVisible(containerComuneVisibile);

    containerStato = new WebMarkupContainer("containerStato");
    containerStato.setOutputMarkupId(true);
    containerStato.setOutputMarkupPlaceholderTag(true);
    boolean containerStatoVisibile =
        LabelFdCUtil.checkIfNotNull(getModelObject())
            && LabelFdCUtil.checkIfNotNull(getModelObject().getStatoProvenienza());
    containerStato.setVisible(containerStatoVisibile);

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

    Integer tipoPratica = 1;
    TipologiaIscrizioneDropDownChoise comboTipologiaIscrizione =
        new TipologiaIscrizioneDropDownChoise<>(
            "comboTipologiaIscrizione",
            new PropertyModel<>(getModelObject(), "comboTipologiaIscrizione"),
            tipoPratica);
    addOrReplace(comboTipologiaIscrizione);

    ComuniItalianiAutoComplete comuneProvenienza =
        new ComuniItalianiAutoComplete(
            "comuneProvenienza", new PropertyModel(getModelObject(), "comuneProvenienza"));
    comuneProvenienza.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -3065820476529907335L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    //

    //		Select2Choice<Comune> comuneProvenienza = new Select2Choice<Comune>("comuneProvenienza",
    //				new PropertyModel(getModelObject(), "comuneProvenienza"), new
    // ComuniProvider(getSession()));
    //		comuneProvenienza.getSettings().setMinimumInputLength(1);
    //		comuneProvenienza.getSettings().setWidth("100%");
    comuneProvenienza.setLabel(Model.of("Comune di Provenienza"));
    comuneProvenienza.setRequired(true);
    containerComune.addOrReplace(comuneProvenienza);

    addOrReplace(containerComune);

    StatoAutoComplete statoProvenienza =
        new StatoAutoComplete(
            "statoProvenienza", new PropertyModel(getModelObject(), "statoProvenienza"));
    statoProvenienza.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -4287706483457048090L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    statoProvenienza.setRequired(true);

    //		Select2Choice<Stato> statoProvenienza = new Select2Choice<Stato>("statoProvenienza",
    //				new PropertyModel(getModelObject(), "statoProvenienza"), new StatoProvider(getSession()));
    //		statoProvenienza.getSettings().setMinimumInputLength(1);
    //		statoProvenienza.getSettings().setWidth("100%");
    statoProvenienza.setLabel(Model.of("Stato di Provenienza"));
    statoProvenienza.setRequired(true);
    containerStato.addOrReplace(statoProvenienza);

    addOrReplace(containerStato);

    ProvenienzaDropDownChoise comboProvenienza =
        new ProvenienzaDropDownChoise<>(
            "comboProvenienza", new PropertyModel<>(getModelObject(), "comboProvenienza"));
    comboProvenienza.setOutputMarkupId(true);
    comboProvenienza.setOutputMarkupPlaceholderTag(true);

    comboProvenienza.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 1563815012758143954L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(getModelObject().getComboProvenienza())) {
              if (getModelObject().getComboProvenienza().getCodice().equalsIgnoreCase("1")) {
                containerComune.setVisible(true);
                containerStato.setVisible(false);
              } else {
                containerComune.setVisible(false);
                containerStato.setVisible(true);
              }
            }

            target.add(containerComune, containerStato);
          }
        });

    addOrReplace(comboProvenienza);
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }

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
