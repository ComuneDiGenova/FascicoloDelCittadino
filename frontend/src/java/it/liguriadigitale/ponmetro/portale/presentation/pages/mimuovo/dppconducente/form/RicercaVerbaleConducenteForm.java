package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import java.time.LocalDate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
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

public class RicercaVerbaleConducenteForm extends AbstracFrameworkForm<RicercaVerbaleConducente> {

  private static final long serialVersionUID = 2025833823732541553L;

  public FileUploadField uploadPatente;

  public String nomeFilePatente;

  public byte[] byteFilePatente;

  public Form<?> formInterno;

  WebMarkupContainer containerFileUpload;

  private FeedbackPanel feedbackPanel;

  private String email;

  private Utente utente;

  public RicercaVerbaleConducenteForm(
      String id, RicercaVerbaleConducente model, FeedbackPanel feedbackPanel, Utente utente) {
    super(id, model);
    this.feedbackPanel = feedbackPanel;
    this.setUtente(utente);
    setOutputMarkupId(true);
    addElementiForm(utente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addElementiForm(Utente utente) {
    TextField numeroAvviso =
        new TextField("numeroAvviso", new PropertyModel(getModelObject(), "numeroAvviso"));
    numeroAvviso.setRequired(true);
    numeroAvviso.setLabel(Model.of("Numero avviso"));
    addOrReplace(numeroAvviso);

    FdCLocalDateTextfield dataVerbale =
        new FdCLocalDateTextfield(
            "dataVerbale", new PropertyModel(getModelObject(), "dataVerbale"));
    dataVerbale.setRequired(true);
    dataVerbale.setLabel(Model.of("Data verbale"));
    dataVerbale.add(RangeValidator.maximum(LocalDate.now()));
    addOrReplace(dataVerbale);

    TextField targa = new TextField("targa", new PropertyModel(getModelObject(), "targa"));
    targa.setRequired(true);
    targa.setLabel(Model.of("Targa"));
    addOrReplace(targa);

    TextField numeroPatente =
        new TextField("numeroPatente", new PropertyModel(getModelObject(), "numeroPatente"));
    numeroPatente.setRequired(true);
    numeroPatente.setLabel(Model.of("Numero patente"));
    numeroPatente.add(StringValidator.maximumLength(15));
    numeroPatente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    addOrReplace(numeroPatente);

    TextField categoriaPatente =
        new TextField("categoriaPatente", new PropertyModel(getModelObject(), "categoriaPatente"));
    categoriaPatente.setRequired(true);
    categoriaPatente.setLabel(Model.of("Categoria patente"));
    categoriaPatente.add(StringValidator.maximumLength(2));
    categoriaPatente.add(new PatternValidator(MetaPattern.VARIABLE_NAME));
    addOrReplace(categoriaPatente);

    TextField patenteRilasciataDa =
        new TextField(
            "patenteRilasciataDa", new PropertyModel(getModelObject(), "patenteRilasciataDa"));
    patenteRilasciataDa.setRequired(true);
    patenteRilasciataDa.setLabel(Model.of("Patente rilasciata da"));
    patenteRilasciataDa.add(StringValidator.maximumLength(40));
    addOrReplace(patenteRilasciataDa);

    FdCLocalDateTextfield patenteRilasciataIl =
        new FdCLocalDateTextfield(
            "patenteRilasciataIl", new PropertyModel(getModelObject(), "patenteRilasciataIl"));
    patenteRilasciataIl.setRequired(true);
    patenteRilasciataIl.setLabel(Model.of("Patente rilasciata il"));
    patenteRilasciataIl.add(RangeValidator.maximum(LocalDate.now()));
    addOrReplace(patenteRilasciataIl);

    FdCLocalDateTextfield patenteValidaFinoAl =
        new FdCLocalDateTextfield(
            "patenteValidaFinoAl", new PropertyModel(getModelObject(), "patenteValidaFinoAl"));
    patenteValidaFinoAl.setRequired(true);
    patenteValidaFinoAl.setLabel(Model.of("Patente valida fino al"));
    addOrReplace(patenteValidaFinoAl);

    if (utente.getMail() != null) {
      if (getModelObject().getEmailDichiarante() != null
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmailDichiarante())) {
        getModelObject().setEmailDichiarante(getModelObject().getEmailDichiarante());
      } else {
        getModelObject().setEmailDichiarante(utente.getMail());
      }
    }
    EmailTextField emailDichiarante =
        new EmailTextField(
            "emailDichiarante", new PropertyModel<String>(getModelObject(), "emailDichiarante"));
    emailDichiarante.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 71441013415539529L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailDichiarante.getValue());
            getModelObject().setEmailDichiarante(emailDichiarante.getValue());
          }
        });
    getModelObject().setEmailDichiarante(emailDichiarante.getValue());
    emailDichiarante.setLabel(Model.of("Email dichiarante"));
    emailDichiarante.setRequired(true);
    addOrReplace(emailDichiarante);

    setMultiPart(true);
    setFileMaxSize(Bytes.kilobytes(2000));

    containerFileUpload = new WebMarkupContainer("containerFileUpload");
    containerFileUpload.setVisible(false);
    containerFileUpload.setOutputMarkupId(true);

    formInterno = new Form<>("formUpload");
    formInterno.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 7369430723025556986L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = uploadPatente.getFileUpload();
            if (uploadedFile != null) {

              setUploadPatente(uploadPatente);
              setNomeFilePatente(uploadedFile.getClientFileName());
              setByteFilePatente(uploadedFile.getBytes());

              containerFileUpload.setVisible(true);

              Label nomeFile = new Label("nomeFile", getNomeFilePatente());
              containerFileUpload.addOrReplace(nomeFile);

              Label dimensioneFile =
                  new Label("dimensioneFile", getFileSize(uploadedFile.getSize()));
              containerFileUpload.addOrReplace(dimensioneFile);

              AjaxButton eliminaFile =
                  new AjaxButton("eliminaFile") {

                    private static final long serialVersionUID = 2880168485223940915L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerFileUpload.setVisible(false);

                      setNomeFilePatente(null);
                      setByteFilePatente(null);

                      targetElimina.add(containerFileUpload);
                    }
                    ;
                  };
              eliminaFile.setDefaultFormProcessing(false);

              containerFileUpload.addOrReplace(eliminaFile);

              target.add(formInterno, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    formInterno.addOrReplace(containerFileUpload);
    formInterno.addOrReplace(uploadPatente = new FileUploadField("uploadPatente"));
    addOrReplace(formInterno);

    CheckBox check =
        new CheckBox(
            "toggleDichiarazione",
            new PropertyModel<Boolean>(getModelObject(), "toggleDichiarazione"));
    check.setRequired(true);

    addOrReplace(check);

    setMultiPart(true);
    setMaxSize(Bytes.kilobytes(2000));
  }

  public FileUploadField getUploadPatente() {
    return uploadPatente;
  }

  public FileUploadField setUploadPatente(FileUploadField uploadPatente) {
    this.uploadPatente = uploadPatente;
    return uploadPatente;
  }

  public String getNomeFilePatente() {
    return nomeFilePatente;
  }

  public String setNomeFilePatente(String nomeFilePatente) {
    this.nomeFilePatente = nomeFilePatente;
    return nomeFilePatente;
  }

  public byte[] getByteFilePatente() {
    return byteFilePatente;
  }

  public byte[] setByteFilePatente(byte[] byteFilePatente) {
    this.byteFilePatente = byteFilePatente;
    return byteFilePatente;
  }

  public Form<?> getFormInterno() {
    return formInterno;
  }

  public void setFormInterno(Form<?> formInterno) {
    this.formInterno = formInterno;
  }

  public WebMarkupContainer getContainerFileUpload() {
    return containerFileUpload;
  }

  public void setContainerFileUpload(WebMarkupContainer containerFileUpload) {
    this.containerFileUpload = containerFileUpload;
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
