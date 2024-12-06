package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.form.nonresidenti;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapRadioChoice;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.YesNoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractChoice.LabelPosition;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class DichiarazionePuntiPatenteProprietarioNonResidenteForm
    extends AbstracFrameworkForm<PuntiPatenteProprietario> {

  private static final long serialVersionUID = 304702052448460060L;

  Boolean visibilitaProprietario = false;

  public FileUploadField patenteProprietarioUpload;

  public String nomePatenteProprietarioUpload;

  public byte[] bytePatenteProprietarioUpload;

  public FileUploadField patenteConducenteUpload;

  public String nomePatenteConducenteUpload;

  public byte[] bytePatenteConducenteUpload;

  public Form<?> formUploadProprietario;

  public Form<?> formUploadConducente;

  WebMarkupContainer containerFileUploadProprietario;

  WebMarkupContainer containerFileUploadConducente;

  WebMarkupContainer containerAlert;

  private FeedbackPanel feedbackPanel;

  private String email;

  private Utente utente;

  public DichiarazionePuntiPatenteProprietarioNonResidenteForm(
      String id, PuntiPatenteProprietario model, FeedbackPanel feedbackPanel, Utente utente) {
    super(id, model);

    this.feedbackPanel = feedbackPanel;

    this.setUtente(utente);

    addElementiForm(utente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addElementiForm(Utente utente) {

    WebMarkupContainer containerDatiProprietario =
        new WebMarkupContainer("containerDatiProprietario");
    containerDatiProprietario.setOutputMarkupId(true);

    WebMarkupContainer containerDatiProprietarioPatente =
        new WebMarkupContainer("containerDatiProprietarioPatente");
    containerDatiProprietarioPatente.setOutputMarkupId(true);
    containerDatiProprietarioPatente.setOutputMarkupPlaceholderTag(true);

    WebMarkupContainer containerDatiConducente = new WebMarkupContainer("containerDatiConducente");
    containerDatiConducente.setOutputMarkupId(true);
    containerDatiConducente.setOutputMarkupPlaceholderTag(true);

    containerAlert = new WebMarkupContainer("containerAlert");
    containerAlert.setOutputMarkupId(true);
    containerAlert.setVisible(false);

    TextField proprietarioNatoA =
        new TextField(
            "proprietarioNatoA", new PropertyModel(getModelObject(), "proprietarioNatoA"));
    proprietarioNatoA.setRequired(true);
    proprietarioNatoA.setLabel(Model.of("Proprietario nato a"));
    containerDatiProprietario.addOrReplace(proprietarioNatoA);

    TextField proprietarioNatoAProvincia =
        new TextField(
            "proprietarioNatoAProvincia",
            new PropertyModel(getModelObject(), "proprietarioNatoAProvincia"));
    proprietarioNatoAProvincia.setRequired(true);
    proprietarioNatoAProvincia.setLabel(Model.of("Provincia"));
    containerDatiProprietario.addOrReplace(proprietarioNatoAProvincia);

    TextField proprietarioResidenteIn =
        new TextField(
            "proprietarioResidenteIn",
            new PropertyModel(getModelObject(), "proprietarioResidenteIn"));
    proprietarioResidenteIn.setRequired(true);
    proprietarioResidenteIn.setLabel(Model.of("Proprietario residente in"));
    containerDatiProprietario.addOrReplace(proprietarioResidenteIn);

    TextField proprietarioResidenteCap =
        new TextField(
            "proprietarioResidenteCap",
            new PropertyModel(getModelObject(), "proprietarioResidenteCap"));
    proprietarioResidenteCap.setRequired(true);
    proprietarioResidenteCap.setLabel(Model.of("Cap"));
    containerDatiProprietario.addOrReplace(proprietarioResidenteCap);

    TextField proprietarioResidenteCitta =
        new TextField(
            "proprietarioResidenteCitta",
            new PropertyModel(getModelObject(), "proprietarioResidenteCitta"));
    proprietarioResidenteCitta.setRequired(true);
    proprietarioResidenteCitta.setLabel(Model.of("Citta'"));
    containerDatiProprietario.addOrReplace(proprietarioResidenteCitta);

    addOrReplace(containerDatiProprietario);

    List<Boolean> choices = Arrays.asList(false, true);
    BootstrapRadioChoice<Boolean> radioProprietario =
        new BootstrapRadioChoice<>(
            "dichiarazioneProprietario", choices, new YesNoRenderer("radioProprietario"));
    aggiustaRadioPerBootstrapItalia(radioProprietario);

    visibilitaProprietario = getModelObject().isDichiarazioneProprietario();

    if (visibilitaProprietario) {
      containerDatiProprietarioPatente.setVisible(true);
      containerDatiConducente.setVisible(false);
    } else {
      containerDatiProprietarioPatente.setVisible(false);
      containerDatiConducente.setVisible(true);
    }

    radioProprietario.setOutputMarkupId(true);

    radioProprietario.add(
        new AjaxFormChoiceComponentUpdatingBehavior() {

          private static final long serialVersionUID = 7498168080306818232L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (radioProprietario.getInput().equalsIgnoreCase("true")) {
              containerDatiProprietarioPatente.setVisible(true);
              containerDatiConducente.setVisible(false);
              containerDatiConducente.clearOriginalDestination();
            } else {
              containerDatiProprietarioPatente.setVisible(false);
              containerDatiConducente.setVisible(true);
              containerDatiProprietarioPatente.clearOriginalDestination();
            }

            containerAlert.setVisible(false);

            target.add(containerDatiConducente, containerDatiProprietarioPatente, feedbackPanel);
          }

          @Override
          protected void onError(AjaxRequestTarget target, RuntimeException e) {
            super.onError(target, e);
          }
        });

    addOrReplace(radioProprietario);

    TextField categoriaPatente =
        new TextField("categoriaPatente", new PropertyModel(getModelObject(), "categoriaPatente"));
    categoriaPatente.setRequired(true);
    categoriaPatente.setLabel(Model.of("Categoria patente"));
    categoriaPatente.add(StringValidator.maximumLength(2));
    categoriaPatente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    containerDatiProprietarioPatente.addOrReplace(categoriaPatente);

    TextField numeroPatente =
        new TextField("numeroPatente", new PropertyModel(getModelObject(), "numeroPatente"));
    numeroPatente.setRequired(true);
    numeroPatente.setLabel(Model.of("Numero patente"));
    numeroPatente.add(StringValidator.maximumLength(15));
    numeroPatente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    containerDatiProprietarioPatente.addOrReplace(numeroPatente);

    TextField patenteRilasciataDa =
        new TextField(
            "patenteRilasciataDa", new PropertyModel(getModelObject(), "patenteRilasciataDa"));
    patenteRilasciataDa.setRequired(true);
    patenteRilasciataDa.setLabel(Model.of("Patente rilasciata da"));
    patenteRilasciataDa.add(StringValidator.maximumLength(40));
    containerDatiProprietarioPatente.addOrReplace(patenteRilasciataDa);

    FdCLocalDateTextfield patenteRilasciataIl =
        new FdCLocalDateTextfield(
            "patenteRilasciataIl", new PropertyModel(getModelObject(), "patenteRilasciataIl"));
    patenteRilasciataIl.setRequired(true);
    patenteRilasciataIl.setLabel(Model.of("Patente rilasciata il"));
    patenteRilasciataIl.add(RangeValidator.maximum(LocalDate.now()));
    containerDatiProprietarioPatente.addOrReplace(patenteRilasciataIl);

    FdCLocalDateTextfield patenteValidaFinoAl =
        new FdCLocalDateTextfield(
            "patenteValidaFinoAl", new PropertyModel(getModelObject(), "patenteValidaFinoAl"));
    patenteValidaFinoAl.setRequired(true);
    patenteValidaFinoAl.setLabel(Model.of("Patente valida fino al"));
    containerDatiProprietarioPatente.addOrReplace(patenteValidaFinoAl);

    if (utente.getMail() != null) {
      if (getModelObject().getEmailProprietario() != null
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmailProprietario())) {
        getModelObject().setEmailProprietario(getModelObject().getEmailProprietario());
      } else {
        getModelObject().setEmailProprietario(utente.getMail());
      }
    }
    EmailTextField emailProprietario =
        new EmailTextField(
            "emailProprietario", new PropertyModel<String>(getModelObject(), "emailProprietario"));
    emailProprietario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -5585322873826537968L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailProprietario.getValue());
            getModelObject().setEmailProprietario(emailProprietario.getValue());
          }
        });
    getModelObject().setEmailProprietario(emailProprietario.getValue());
    emailProprietario.setRequired(true);
    emailProprietario.setLabel(Model.of("Email dichiarante"));
    containerDatiProprietarioPatente.addOrReplace(emailProprietario);

    TextField nomeConducente =
        new TextField("nomeConducente", new PropertyModel(getModelObject(), "nomeConducente"));
    nomeConducente.setRequired(true);
    nomeConducente.setLabel(Model.of("Nome conducente"));
    containerDatiConducente.addOrReplace(nomeConducente);

    TextField cognomeConducente =
        new TextField(
            "cognomeConducente", new PropertyModel(getModelObject(), "cognomeConducente"));
    cognomeConducente.setRequired(true);
    cognomeConducente.setLabel(Model.of("Cognome conducente"));
    containerDatiConducente.addOrReplace(cognomeConducente);

    TextField cfConducente =
        new TextField("cfConducente", new PropertyModel(getModelObject(), "cfConducente"));
    cfConducente.setRequired(true);
    cfConducente.setLabel(Model.of("Codice fiscale conducente"));
    cfConducente.add(new CodiceFiscaleValidatorUtil());
    cfConducente.add(StringValidator.exactLength(16));
    containerDatiConducente.addOrReplace(cfConducente);

    TextField conducenteNatoA =
        new TextField("conducenteNatoA", new PropertyModel(getModelObject(), "conducenteNatoA"));
    conducenteNatoA.setRequired(true);
    conducenteNatoA.setLabel(Model.of("Conducente nato a"));
    containerDatiConducente.addOrReplace(conducenteNatoA);

    TextField conducenteNatoAProvincia =
        new TextField(
            "conducenteNatoAProvincia",
            new PropertyModel(getModelObject(), "conducenteNatoAProvincia"));
    conducenteNatoAProvincia.setRequired(true);
    conducenteNatoAProvincia.setLabel(Model.of("Provincia"));
    containerDatiConducente.addOrReplace(conducenteNatoAProvincia);

    FdCLocalDateTextfield conducenteNatoIl =
        new FdCLocalDateTextfield(
            "conducenteNatoIl", new PropertyModel(getModelObject(), "conducenteNatoIl"));
    conducenteNatoIl.setRequired(true);
    conducenteNatoIl.setLabel(Model.of("Conducente nato il"));
    conducenteNatoIl.add(RangeValidator.maximum(LocalDate.now()));
    containerDatiConducente.addOrReplace(conducenteNatoIl);

    TextField conducenteResidenteA =
        new TextField(
            "conducenteResidenteA", new PropertyModel(getModelObject(), "conducenteResidenteA"));
    conducenteResidenteA.setRequired(true);
    conducenteResidenteA.setLabel(Model.of("Conducente residente a"));
    containerDatiConducente.addOrReplace(conducenteResidenteA);

    TextField conducenteResidenteIn =
        new TextField(
            "conducenteResidenteIn", new PropertyModel(getModelObject(), "conducenteResidenteIn"));
    conducenteResidenteIn.setRequired(true);
    conducenteResidenteIn.setLabel(Model.of("Conducente residente in"));
    containerDatiConducente.addOrReplace(conducenteResidenteIn);

    TextField conducenteResidenteProvincia =
        new TextField(
            "conducenteResidenteProvincia",
            new PropertyModel(getModelObject(), "conducenteResidenteProvincia"));
    conducenteResidenteProvincia.setRequired(true);
    conducenteResidenteProvincia.setLabel(Model.of("Provincia"));
    containerDatiConducente.addOrReplace(conducenteResidenteProvincia);

    TextField conducenteResidenteNumCivico =
        new TextField(
            "conducenteResidenteNumCivico",
            new PropertyModel(getModelObject(), "conducenteResidenteNumCivico"));
    conducenteResidenteNumCivico.setRequired(true);
    conducenteResidenteNumCivico.setLabel(Model.of("Numero civico"));
    containerDatiConducente.addOrReplace(conducenteResidenteNumCivico);

    TextField conducenteResidenteCap =
        new TextField(
            "conducenteResidenteCap",
            new PropertyModel(getModelObject(), "conducenteResidenteCap"));
    conducenteResidenteCap.setRequired(true);
    conducenteResidenteCap.setLabel(Model.of("Cap"));
    containerDatiConducente.addOrReplace(conducenteResidenteCap);

    EmailTextField emailConducente =
        new EmailTextField(
            "emailConducente", new PropertyModel(getModelObject(), "emailConducente"));
    emailConducente.setRequired(true);
    emailConducente.setLabel(Model.of("Email dichiarante"));
    containerDatiConducente.addOrReplace(emailConducente);

    TextField categoriaPatenteConducente =
        new TextField(
            "categoriaPatenteConducente",
            new PropertyModel(getModelObject(), "categoriaPatenteConducente"));
    categoriaPatenteConducente.setRequired(true);
    categoriaPatenteConducente.setLabel(Model.of("Categoria patente conducente"));
    categoriaPatenteConducente.add(StringValidator.maximumLength(2));
    categoriaPatenteConducente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    containerDatiConducente.addOrReplace(categoriaPatenteConducente);

    TextField numeroPatenteConducente =
        new TextField(
            "numeroPatenteConducente",
            new PropertyModel(getModelObject(), "numeroPatenteConducente"));
    numeroPatenteConducente.setRequired(true);
    numeroPatenteConducente.setLabel(Model.of("Numero patente conducente"));
    numeroPatenteConducente.add(StringValidator.maximumLength(15));
    numeroPatenteConducente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    containerDatiConducente.addOrReplace(numeroPatenteConducente);

    TextField patenteConducenteRilasciataDa =
        new TextField(
            "patenteConducenteRilasciataDa",
            new PropertyModel(getModelObject(), "patenteConducenteRilasciataDa"));
    patenteConducenteRilasciataDa.setRequired(true);
    patenteConducenteRilasciataDa.setLabel(Model.of("Patente conducente rilasciata da"));
    patenteConducenteRilasciataDa.add(StringValidator.maximumLength(40));
    containerDatiConducente.addOrReplace(patenteConducenteRilasciataDa);

    FdCLocalDateTextfield patenteConducenteRilasciataIl =
        new FdCLocalDateTextfield(
            "patenteConducenteRilasciataIl",
            new PropertyModel(getModelObject(), "patenteConducenteRilasciataIl"));
    patenteConducenteRilasciataIl.setRequired(true);
    patenteConducenteRilasciataIl.setLabel(Model.of("Patente conducente rilasciata il"));
    patenteConducenteRilasciataIl.add(RangeValidator.maximum(LocalDate.now()));
    containerDatiConducente.addOrReplace(patenteConducenteRilasciataIl);

    FdCLocalDateTextfield patenteConducenteValidaFinoAl =
        new FdCLocalDateTextfield(
            "patenteConducenteValidaFinoAl",
            new PropertyModel(getModelObject(), "patenteConducenteValidaFinoAl"));
    patenteConducenteValidaFinoAl.setRequired(true);
    patenteConducenteValidaFinoAl.setLabel(Model.of("Patente conducente valida fino al"));
    containerDatiConducente.addOrReplace(patenteConducenteValidaFinoAl);

    containerFileUploadProprietario = new WebMarkupContainer("containerFileUploadProprietario");
    containerFileUploadProprietario.setVisible(false);
    containerFileUploadProprietario.setOutputMarkupId(true);

    formUploadProprietario =
        new Form<Void>("formUploadProprietario") {

          private static final long serialVersionUID = -6483298754335671283L;

          @Override
          protected void onSubmit() {

            final FileUpload uploadedFile = patenteConducenteUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };
    formUploadProprietario.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -9172264348074013429L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = patenteProprietarioUpload.getFileUpload();
            if (uploadedFile != null) {

              setPatenteProprietarioUpload(patenteProprietarioUpload);
              setNomePatenteProprietarioUpload(uploadedFile.getClientFileName());
              setBytePatenteProprietarioUpload(uploadedFile.getBytes());

              containerFileUploadProprietario.setVisible(true);

              Label nomeFilePatenteProprietario =
                  new Label("nomeFilePatenteProprietario", getNomePatenteProprietarioUpload());
              containerFileUploadProprietario.addOrReplace(nomeFilePatenteProprietario);

              Label dimensioneFileProprietario =
                  new Label("dimensioneFileProprietario", getFileSize(uploadedFile.getSize()));
              containerFileUploadProprietario.addOrReplace(dimensioneFileProprietario);

              AjaxButton eliminaPatenteProprietario =
                  new AjaxButton("eliminaPatenteProprietario") {

                    private static final long serialVersionUID = -6008971522250588152L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerFileUploadProprietario.setVisible(false);

                      setNomePatenteProprietarioUpload(null);
                      setBytePatenteProprietarioUpload(null);

                      targetElimina.add(containerFileUploadProprietario);
                    }
                    ;
                  };
              eliminaPatenteProprietario.setDefaultFormProcessing(false);

              containerFileUploadProprietario.addOrReplace(eliminaPatenteProprietario);

              target.add(formUploadProprietario, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    formUploadProprietario.setMultiPart(true);
    formUploadProprietario.setFileMaxSize(Bytes.kilobytes(2000));
    formUploadProprietario.addOrReplace(containerFileUploadProprietario);
    formUploadProprietario.addOrReplace(
        patenteProprietarioUpload = new FileUploadField("patenteProprietarioUpload"));
    containerDatiProprietarioPatente.addOrReplace(formUploadProprietario);

    containerFileUploadConducente = new WebMarkupContainer("containerFileUploadConducente");
    containerFileUploadConducente.setVisible(false);
    containerFileUploadConducente.setOutputMarkupId(true);

    formUploadConducente =
        new Form<Void>("formUploadConducente") {

          private static final long serialVersionUID = -2127363473950210270L;

          @Override
          protected void onSubmit() {

            final FileUpload uploadedFile = patenteConducenteUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };
    formUploadConducente.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -1719480465126040438L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = patenteConducenteUpload.getFileUpload();
            if (uploadedFile != null) {

              setPatenteConducenteUpload(patenteConducenteUpload);
              setNomePatenteConducenteUpload(uploadedFile.getClientFileName());
              setBytePatenteConducenteUpload(uploadedFile.getBytes());

              containerFileUploadConducente.setVisible(true);

              Label nomeFilePatenteConducente =
                  new Label("nomeFilePatenteConducente", getNomePatenteConducenteUpload());
              containerFileUploadConducente.addOrReplace(nomeFilePatenteConducente);

              Label dimensioneFileConducente =
                  new Label("dimensioneFileConducente", getFileSize(uploadedFile.getSize()));
              containerFileUploadConducente.addOrReplace(dimensioneFileConducente);

              AjaxButton eliminaPatenteConducente =
                  new AjaxButton("eliminaPatenteConducente") {

                    private static final long serialVersionUID = -6008971522250588152L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerFileUploadConducente.setVisible(false);

                      setNomePatenteConducenteUpload(null);
                      setBytePatenteConducenteUpload(null);

                      targetElimina.add(containerFileUploadConducente);
                    }
                    ;
                  };
              eliminaPatenteConducente.setDefaultFormProcessing(false);

              containerFileUploadConducente.addOrReplace(eliminaPatenteConducente);

              target.add(formUploadConducente, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    formUploadConducente.setMultiPart(true);
    formUploadConducente.setFileMaxSize(Bytes.kilobytes(2000));
    formUploadConducente.addOrReplace(containerFileUploadConducente);
    formUploadConducente.addOrReplace(
        patenteConducenteUpload = new FileUploadField("patenteConducenteUpload"));
    containerDatiConducente.addOrReplace(formUploadConducente);

    addOrReplace(containerDatiProprietario);
    addOrReplace(containerDatiProprietarioPatente);
    addOrReplace(containerDatiConducente);
    addOrReplace(containerAlert);

    setMultiPart(true);
    setMaxSize(Bytes.kilobytes(1000));
  }

  private void aggiustaRadioPerBootstrapItalia(BootstrapRadioChoice<Boolean> radioProprietario) {
    radioProprietario.setInline(true);
    radioProprietario.setLabelPosition(LabelPosition.AFTER);
    radioProprietario.setPrefix("<div class=\"form-check form-check-inline\">");
    radioProprietario.setSuffix("</div>");
  }

  public FileUploadField getPatenteConducenteUpload() {
    return patenteConducenteUpload;
  }

  public FileUploadField setPatenteConducenteUpload(FileUploadField patenteConducenteUpload) {
    this.patenteConducenteUpload = patenteConducenteUpload;
    return patenteConducenteUpload;
  }

  public String getNomePatenteConducenteUpload() {
    return nomePatenteConducenteUpload;
  }

  public String setNomePatenteConducenteUpload(String nomePatenteConducenteUpload) {
    this.nomePatenteConducenteUpload = nomePatenteConducenteUpload;
    return nomePatenteConducenteUpload;
  }

  public byte[] getBytePatenteConducenteUpload() {
    return bytePatenteConducenteUpload;
  }

  public byte[] setBytePatenteConducenteUpload(byte[] bytePatenteConducenteUpload) {
    this.bytePatenteConducenteUpload = bytePatenteConducenteUpload;
    return bytePatenteConducenteUpload;
  }

  public FileUploadField getPatenteProprietarioUpload() {
    return patenteProprietarioUpload;
  }

  public FileUploadField setPatenteProprietarioUpload(FileUploadField patenteProprietarioUpload) {
    this.patenteProprietarioUpload = patenteProprietarioUpload;
    return patenteProprietarioUpload;
  }

  public String getNomePatenteProprietarioUpload() {
    return nomePatenteProprietarioUpload;
  }

  public String setNomePatenteProprietarioUpload(String nomePatenteProprietarioUpload) {
    this.nomePatenteProprietarioUpload = nomePatenteProprietarioUpload;
    return nomePatenteProprietarioUpload;
  }

  public byte[] getBytePatenteProprietarioUpload() {
    return bytePatenteProprietarioUpload;
  }

  public byte[] setBytePatenteProprietarioUpload(byte[] bytePatenteProprietarioUpload) {
    this.bytePatenteProprietarioUpload = bytePatenteProprietarioUpload;
    return bytePatenteProprietarioUpload;
  }

  public Form<?> getFormUploadProprietario() {
    return formUploadProprietario;
  }

  public void setFormUploadProprietario(Form<?> formUploadProprietario) {
    this.formUploadProprietario = formUploadProprietario;
  }

  public Form<?> getFormUploadConducente() {
    return formUploadConducente;
  }

  public void setFormUploadConducente(Form<?> formUploadConducente) {
    this.formUploadConducente = formUploadConducente;
  }

  public WebMarkupContainer getContainerFileUploadProprietario() {
    return containerFileUploadProprietario;
  }

  public void setContainerFileUploadProprietario(
      WebMarkupContainer containerFileUploadProprietario) {
    this.containerFileUploadProprietario = containerFileUploadProprietario;
  }

  public WebMarkupContainer getContainerFileUploadConducente() {
    return containerFileUploadConducente;
  }

  public void setContainerFileUploadConducente(WebMarkupContainer containerFileUploadConducente) {
    this.containerFileUploadConducente = containerFileUploadConducente;
  }

  public WebMarkupContainer getContainerAlert() {
    return containerAlert;
  }

  public void setContainerAlert(WebMarkupContainer containerAlert) {
    this.containerAlert = containerAlert;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  private String getFileSize(long size) {
    long n = 1000;
    String s = "";
    double kb = size / n;
    double mb = kb / n;
    double gb = mb / n;
    double tb = gb / n;
    if (size < n) {
      s = size + " Bytes";
    } else if (size >= n && size < (n * n)) {
      s = String.format("%.2f", kb) + " KB";
    } else if (size >= (n * n) && size < (n * n * n)) {
      s = String.format("%.2f", mb) + " MB";
    } else if (size >= (n * n * n) && size < (n * n * n * n)) {
      s = String.format("%.2f", gb) + " GB";
    } else if (size >= (n * n * n * n)) {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }
}
