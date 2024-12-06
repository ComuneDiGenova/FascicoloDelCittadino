package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.GiorniRientroScuola;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoDietaRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.scuola.ScuolaAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadDieteValidator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Bytes;

public class RichiestaDietaSpecialeForm extends AbstracFrameworkForm<DietaSpeciale> {

  private static final long serialVersionUID = 2398190460521926314L;

  private Utente utente;

  private String email;

  private UtenteServiziRistorazione iscrizione;

  private WebMarkupContainer containerDietaMotiviSalute =
      new WebMarkupContainer("containerDietaMotiviSalute");

  private WebMarkupContainer containerDietaMotiviReligiosi =
      new WebMarkupContainer("containerDietaMotiviReligiosi");

  private WebMarkupContainer containerFile;

  public Form<?> formUploadFile;

  private FileUploadField fileAttestazioneMedicaUpload;

  private String nomeFileAttestazioneMedica;

  private byte[] byteFileAttestazioneMedica;

  private String dimensioneFileAttestazioneMedica;

  private MenuDropDownChoise comboMenu;

  private FeedbackPanel feedbackPanel;

  public RichiestaDietaSpecialeForm(
      String id,
      DietaSpeciale model,
      Utente utente,
      UtenteServiziRistorazione iscrizione,
      FeedbackPanel feedbackPanel) {
    super(id, model);

    this.setUtente(utente);
    this.iscrizione = iscrizione;
    this.feedbackPanel = feedbackPanel;

    addElementiForm(utente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addElementiForm(Utente utente) {

    containerDietaMotiviReligiosi.setOutputMarkupId(true);
    containerDietaMotiviReligiosi.setOutputMarkupPlaceholderTag(true);
    containerDietaMotiviReligiosi.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getTipoDieta())
            && getModelObject().getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI));
    addOrReplace(containerDietaMotiviReligiosi);

    containerDietaMotiviSalute.setOutputMarkupId(true);
    containerDietaMotiviSalute.setOutputMarkupPlaceholderTag(true);
    containerDietaMotiviSalute.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getTipoDieta())
            && getModelObject().getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE));
    addOrReplace(containerDietaMotiviSalute);

    Label nomeRichiedente = new Label("nomeRichiedente", utente.getNome());
    addOrReplace(nomeRichiedente);

    Label cognomeRichiedente = new Label("cognomeRichiedente", utente.getCognome());
    addOrReplace(cognomeRichiedente);

    Label cfRichiedente = new Label("cfRichiedente", utente.getCodiceFiscaleOperatore());
    addOrReplace(cfRichiedente);

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

          private static final long serialVersionUID = -1236030826234471806L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailRichiedente.getValue());
            getModelObject().setEmailRichiedente(emailRichiedente.getValue());
          }
        });
    getModelObject().setEmailRichiedente(emailRichiedente.getValue());
    emailRichiedente.setRequired(true);
    emailRichiedente.setLabel(Model.of("Email"));
    addOrReplace(emailRichiedente);

    Label nomeIscritto = new Label("nomeIscritto", iscrizione.getNome());
    addOrReplace(nomeIscritto);

    Label cognomeIscritto = new Label("cognomeIscritto", iscrizione.getCognome());
    addOrReplace(cognomeIscritto);

    Label cfIscritto = new Label("cfIscritto", iscrizione.getCodiceFiscale());
    addOrReplace(cfIscritto);

    creaComboTipoDieta();

    containerFile = new WebMarkupContainer("containerFile");
    containerFile.setVisible(false);
    containerFile.setOutputMarkupId(true);

    formUploadFile =
        new Form<Void>("formUploadFile") {

          private static final long serialVersionUID = 5637460239713955952L;

          @Override
          protected void onSubmit() {

            final FileUpload uploadedFile = fileAttestazioneMedicaUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };
    formUploadFile.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 5911456237189742864L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = fileAttestazioneMedicaUpload.getFileUpload();
            if (uploadedFile != null) {

              setFileAttestazioneMedicaUpload(fileAttestazioneMedicaUpload);
              setNomeFileAttestazioneMedica(uploadedFile.getClientFileName());
              setByteFileAttestazioneMedica(uploadedFile.getBytes());

              containerFile.setVisible(true);

              Label nomeFile = new Label("nomeFile", getNomeFileAttestazioneMedica());
              containerFile.addOrReplace(nomeFile);

              Label dimensioneFile =
                  new Label("dimensioneFile", FileFdCUtil.getSizeFile(uploadedFile.getSize()));
              containerFile.addOrReplace(dimensioneFile);

              AjaxButton eliminaFile =
                  new AjaxButton("eliminaFile") {

                    private static final long serialVersionUID = 6421871465837323595L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerFile.setVisible(false);

                      setNomeFileAttestazioneMedica(null);
                      setByteFileAttestazioneMedica(null);

                      targetElimina.add(containerFile);
                    }
                    ;
                  };
              eliminaFile.setDefaultFormProcessing(false);

              containerFile.addOrReplace(eliminaFile);

              target.add(formUploadFile, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    formUploadFile.setMultiPart(true);
    formUploadFile.setFileMaxSize(Bytes.kilobytes(2000));
    formUploadFile.addOrReplace(containerFile);
    formUploadFile.addOrReplace(
        fileAttestazioneMedicaUpload = new FileUploadField("fileAttestazioneMedicaUpload"));
    fileAttestazioneMedicaUpload.setLabel(Model.of("Upload attestazione medica"));
    fileAttestazioneMedicaUpload.add(new FileUploadDieteValidator());

    containerDietaMotiviSalute.addOrReplace(formUploadFile);

    comboMenu =
        new MenuDropDownChoise("comboMenu", new PropertyModel<>(getModelObject(), "comboMenu"));
    containerDietaMotiviReligiosi.addOrReplace(comboMenu);

    CheckBox anafilassi =
        new CheckBox("anafilassi", new PropertyModel<Boolean>(getModelObject(), "anafilassi"));
    anafilassi.setRequired(true);
    anafilassi.setOutputMarkupId(true);
    anafilassi.setOutputMarkupPlaceholderTag(true);
    anafilassi.setLabel(Model.of("Episodi di anafilassi"));
    containerDietaMotiviSalute.addOrReplace(anafilassi);

    TextArea<String> noteIntegrative =
        new TextArea<String>(
            "noteIntegrative", new PropertyModel<String>(getModelObject(), "noteIntegrative"));
    noteIntegrative.setLabel(Model.of("Note integrative"));
    addOrReplace(noteIntegrative);

    NumberTextField classe =
        new NumberTextField("classe", new PropertyModel<String>(getModelObject(), "classe"));
    classe.setLabel(Model.of("Classe"));
    classe.setMinimum(1);
    classe.setMaximum(5);
    addOrReplace(classe);

    TextField sezione =
        new TextField("sezione", new PropertyModel<String>(getModelObject(), "sezione"));
    sezione.setLabel(Model.of("Sezione"));
    addOrReplace(sezione);

    ListView<GiorniRientroScuola> listViewGiorni =
        new ListView<GiorniRientroScuola>(
            "listViewGiorni", getModelObject().getListaGiorniRientri()) {

          private static final long serialVersionUID = -5537760779885795979L;

          @Override
          protected void populateItem(ListItem<GiorniRientroScuola> item) {
            GiorniRientroScuola gg = item.getModelObject();

            Label giornoRientro = new Label("giornoRientro", gg.getGiornoRientro());
            giornoRientro.setVisible(LabelFdCUtil.checkIfNotNull(giornoRientro));
            item.addOrReplace(giornoRientro);

            CheckBox selezionato =
                new CheckBox("selezionato", new PropertyModel<Boolean>(gg, "selezionato"));
            item.addOrReplace(selezionato);
          }
        };
    addOrReplace(listViewGiorni);

    ScuolaAutoComplete scuola =
        new ScuolaAutoComplete("scuola", new PropertyModel<String>(getModelObject(), "scuola"));
    scuola.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -3418723285911237251L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    addOrReplace(scuola);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected DropDownChoice<TipoDietaSpecialeEnum> creaDropDownChoice(
      List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {

    ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
    DropDownChoice combo = new DropDownChoice(idWicket, choices);
    combo.setChoiceRenderer(choiceRenderer);
    combo.setModel(modello);
    combo.setNullValid(false);

    return combo;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  protected DropDownChoice creaDropDownChoice2(
      List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {

    ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
    DropDownChoice combo = new DropDownChoice(idWicket, choices);
    combo.setChoiceRenderer(choiceRenderer);
    combo.setModel(modello);
    combo.setNullValid(false);
    return combo;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  protected MenuMotiviReligiosiDropDownChoise creaDropDownChoiceMenu(
      List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {

    ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
    MenuMotiviReligiosiDropDownChoise combo =
        new MenuMotiviReligiosiDropDownChoise(idWicket, choices);
    combo.setChoiceRenderer(choiceRenderer);
    combo.setModel(modello);
    combo.setNullValid(false);
    return combo;
  }

  private void creaComboTipoDieta() {
    List<TipoDietaSpecialeEnum> listaTipoDieta = Arrays.asList(TipoDietaSpecialeEnum.values());

    DropDownChoice<TipoDietaSpecialeEnum> comboTipoDieta =
        creaDropDownChoice(
            listaTipoDieta,
            "tipoDieta",
            new TipoDietaRender(),
            new PropertyModel<>(getModelObject(), "tipoDieta"));

    comboTipoDieta.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -6241925736326802631L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (comboTipoDieta.getModelObject().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI)) {
              containerDietaMotiviReligiosi.setVisible(true);
              containerDietaMotiviSalute.setVisible(false);

              comboMenu.clearInput();

              getModelObject().setFileAttestazioneMedicaUpload(null);
              getModelObject().setByteFileAttestazioneMedica(null);
              getModelObject().setNomeFileAttestazioneMedica(null);
              getModelObject().setEstensioneFileAttestazioneMedica(null);
              getModelObject().setMimeTypeFileAttestazioneMedica(null);

            } else if (comboTipoDieta.getModelObject().equals(TipoDietaSpecialeEnum.DI_SALUTE)) {
              containerDietaMotiviReligiosi.setVisible(false);
              containerDietaMotiviSalute.setVisible(true);

              comboMenu.clearInput();
            }

            target.add(
                new RichiestaDietaSpecialeForm(
                    "form", getModelObject(), utente, iscrizione, feedbackPanel),
                containerDietaMotiviReligiosi,
                containerDietaMotiviSalute,
                comboMenu);
          }
        });

    comboTipoDieta.setRequired(true);
    comboTipoDieta.setOutputMarkupId(true);
    comboTipoDieta.setOutputMarkupPlaceholderTag(true);
    comboTipoDieta.setLabel(Model.of("Seleziona tipo dieta"));
    addOrReplace(comboTipoDieta);
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

  public UtenteServiziRistorazione getIscrizione() {
    return iscrizione;
  }

  public void setIscrizione(UtenteServiziRistorazione iscrizione) {
    this.iscrizione = iscrizione;
  }

  public WebMarkupContainer getContainerDietaMotiviSalute() {
    return containerDietaMotiviSalute;
  }

  public void setContainerDietaMotiviSalute(WebMarkupContainer containerDietaMotiviSalute) {
    this.containerDietaMotiviSalute = containerDietaMotiviSalute;
  }

  public WebMarkupContainer getContainerDietaMotiviReligiosi() {
    return containerDietaMotiviReligiosi;
  }

  public void setContainerDietaMotiviReligiosi(WebMarkupContainer containerDietaMotiviReligiosi) {
    this.containerDietaMotiviReligiosi = containerDietaMotiviReligiosi;
  }

  public Form<?> getFormUploadFile() {
    return formUploadFile;
  }

  public void setFormUploadFile(Form<?> formUploadFile) {
    this.formUploadFile = formUploadFile;
  }

  public FileUploadField getFileAttestazioneMedicaUpload() {
    return fileAttestazioneMedicaUpload;
  }

  public void setFileAttestazioneMedicaUpload(FileUploadField fileAttestazioneMedicaUpload) {
    this.fileAttestazioneMedicaUpload = fileAttestazioneMedicaUpload;
  }

  public String getNomeFileAttestazioneMedica() {
    return nomeFileAttestazioneMedica;
  }

  public void setNomeFileAttestazioneMedica(String nomeFileAttestazioneMedica) {
    this.nomeFileAttestazioneMedica = nomeFileAttestazioneMedica;
  }

  public byte[] getByteFileAttestazioneMedica() {
    return byteFileAttestazioneMedica;
  }

  public void setByteFileAttestazioneMedica(byte[] byteFileAttestazioneMedica) {
    this.byteFileAttestazioneMedica = byteFileAttestazioneMedica;
  }

  public String getDimensioneFileAttestazioneMedica() {
    return dimensioneFileAttestazioneMedica;
  }

  public void setDimensioneFileAttestazioneMedica(String dimensioneFileAttestazioneMedica) {
    this.dimensioneFileAttestazioneMedica = dimensioneFileAttestazioneMedica;
  }

  @Override
  public void addElementiForm() {}
}
