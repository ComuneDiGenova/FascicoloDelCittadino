package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.framework.presentation.components.form.components.validators.CodiceFiscaleValidator;
import it.liguriadigitale.ponmetro.portale.pojo.imu.InQuantoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.portale.pojo.imu.SessoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoAllegatoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoEredeEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadDieteValidator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Bytes;

public class DatiSottoscrittoPanel extends BasePanel {

  private static final long serialVersionUID = 56543213159879820L;

  WebMarkupContainer eredeContainer;
  WebMarkupContainer altroContainer;
  WebMarkupContainer containerFile;
  WebMarkupContainer alertInfoDeleghe;
  RadioChoice<InQuantoEnum> inQuantoRadio;
  RadioChoice<TipoEredeEnum> tipoErede;
  RimborsoImu rimborsoImu;
  boolean isErede = false;

  /* File */
  Form<?> formUploadFile;
  // AjaxButton eliminaFile;
  FileUploadField fileAttestazioneEredeUpload;
  String fileName;
  String dimensione;

  public DatiSottoscrittoPanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub
  }

  public DatiSottoscrittoPanel(String id, RimborsoImu pojo) {
    this(id);

    this.fillDati(pojo);
  }

  @Override
  public void fillDati(Object dati) {

    rimborsoImu = (RimborsoImu) dati;

    // TODO Auto-generated method stub
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "cognome",
            new PropertyModel<String>(rimborsoImu, "cognome"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "nome",
            new PropertyModel<String>(rimborsoImu, "nome"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "codiceFiscale",
            new PropertyModel<String>(rimborsoImu, "codiceFiscale"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "luogoNascita",
            new PropertyModel<String>(rimborsoImu, "luogoNascita"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCLocalDateTextfield(
            "dataNascita",
            new PropertyModel<LocalDate>(rimborsoImu, "dataNascita"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "sesso",
            new PropertyModel<String>(rimborsoImu, "sesso"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "cittadinanza",
            new PropertyModel<String>(rimborsoImu, "cittadinanza"),
            false,
            true,
            DatiSottoscrittoPanel.this));

    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "provincia",
            new PropertyModel<String>(rimborsoImu, "provincia"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "comune",
            new PropertyModel<String>(rimborsoImu, "comune"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "indirizzo",
            new PropertyModel<String>(rimborsoImu, "indirizzo"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCNumberTextField(
            "civico",
            new PropertyModel<Integer>(rimborsoImu, "civico"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "esponente",
            new PropertyModel<String>(rimborsoImu, "esponente"),
            false,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "scala",
            new PropertyModel<String>(rimborsoImu, "scala"),
            false,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "interno",
            new PropertyModel<String>(rimborsoImu, "interno"),
            false,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "colore",
            new PropertyModel<String>(rimborsoImu, "colore"),
            false,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "cap",
            new PropertyModel<String>(rimborsoImu, "cap"),
            false,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCPhoneNumberField(
            "telefono",
            new PropertyModel<String>(rimborsoImu, "telefono"),
            true,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCPhoneNumberField(
            "cellulare",
            new PropertyModel<String>(rimborsoImu, "cellulare"),
            true,
            false,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCEmailTextField(
            "email",
            new PropertyModel<String>(rimborsoImu, "email"),
            true,
            true,
            DatiSottoscrittoPanel.this));
    addOrReplace(
        RichiestaRimborsoImuHelper.createFdCEmailTextField(
            "pec",
            new PropertyModel<String>(rimborsoImu, "pec"),
            true,
            false,
            DatiSottoscrittoPanel.this));

    inQuantoRadio =
        new RadioChoice<InQuantoEnum>(
            "inQuanto",
            new PropertyModel<InQuantoEnum>(rimborsoImu, "inQuanto"),
            Arrays.asList(InQuantoEnum.values()));
    inQuantoRadio.setPrefix("<div class=\"form-check\">");
    inQuantoRadio.setSuffix("</div>");
    inQuantoRadio.setRequired(true);
    inQuantoRadio.setOutputMarkupPlaceholderTag(true);
    inQuantoRadio.setLabel(Model.of("In Quanto"));

    inQuantoRadio.add(
        new AjaxFormChoiceComponentUpdatingBehavior() {
          private static final long serialVersionUID = 413215498732132488L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            clearDatiDefunto();

            switch (inQuantoRadio.getModelObject()) {
              case EREDE:
                eredeContainer.setVisible(true);
                altroContainer.setVisible(false);
                tipoErede.setVisible(true);
                isErede = true;
                break;
              case ALTRO:
                altroContainer.setVisible(true);
                eredeContainer.setVisible(true);
                tipoErede.setVisible(false);
                isErede = false;
                break;
              default:
                altroContainer.setVisible(false);
                eredeContainer.setVisible(false);
                break;
            }

            target.add(eredeContainer, altroContainer);
          }
        });

    addOrReplace(inQuantoRadio);

    eredeContainer = new WebMarkupContainer("datiErede");
    eredeContainer.setOutputMarkupId(true);
    eredeContainer.setOutputMarkupPlaceholderTag(true);

    tipoErede =
        new RadioChoice<TipoEredeEnum>(
            "tipoErede",
            new PropertyModel<TipoEredeEnum>(rimborsoImu, "tipoErede"),
            Arrays.asList(TipoEredeEnum.values()));
    tipoErede.setRequired(true);
    tipoErede.setOutputMarkupId(true);
    tipoErede.setOutputMarkupPlaceholderTag(true);
    tipoErede.setPrefix("<div class=\"form-check form-check-inline\">");
    tipoErede.setSuffix("</div>");

    // @DP: Non dovranno caricare i file in questo passaggio
    /*tipoErede.add(new AjaxFormChoiceComponentUpdatingBehavior() {
    	private static final long serialVersionUID = 1L;

    	@Override
    	protected void onUpdate(AjaxRequestTarget target) {
    		// TODO Auto-generated method stub
    		formUploadFile.setVisible(LabelFdCUtil.checkIfNotNull(rimborsoImu.getTipoErede()));
    		alertInfoDeleghe.setVisible(LabelFdCUtil.checkIfNotNull(rimborsoImu.getTipoErede()));
    		target.add(eredeContainer, alertInfoDeleghe);
    	}

    });*/

    eredeContainer.addOrReplace(tipoErede);
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "cognomeDefunto",
            new PropertyModel<String>(rimborsoImu, "cognomeDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this));
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "nomeDefunto",
            new PropertyModel<String>(rimborsoImu, "nomeDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this));

    FdCTextField<Component> cfDefunto =
        RichiestaRimborsoImuHelper.createFdCTextField(
            "codiceFiscaleDefunto",
            new PropertyModel<String>(rimborsoImu, "codiceFiscaleDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this);
    cfDefunto.getTextField().add(new CodiceFiscaleValidator());

    eredeContainer.addOrReplace(cfDefunto);

    DropDownChoice<SessoEnum> sessoDefunto =
        new DropDownChoice<SessoEnum>(
            "sessoDefunto",
            new PropertyModel<SessoEnum>(rimborsoImu, "sessoDefunto"),
            Arrays.asList(SessoEnum.values()));
    sessoDefunto.setRequired(true);

    eredeContainer.addOrReplace(sessoDefunto);
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCLocalDateTextfield(
            "dataNascitaDefunto",
            new PropertyModel<LocalDate>(rimborsoImu, "dataNascitaDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this));
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "luogoNascitaDefunto",
            new PropertyModel<String>(rimborsoImu, "luogoNascitaDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this));
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "cittadinanzaDefunto",
            new PropertyModel<String>(rimborsoImu, "cittadinanzaDefunto"),
            true,
            true,
            DatiSottoscrittoPanel.this));

    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCLocalDateTextfield(
            "dataDecesso",
            new PropertyModel<LocalDate>(rimborsoImu, "dataDecesso"),
            true,
            isErede,
            DatiSottoscrittoPanel.this));
    eredeContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "luogoDecesso",
            new PropertyModel<String>(rimborsoImu, "luogoDecesso"),
            true,
            isErede,
            DatiSottoscrittoPanel.this));
    eredeContainer.setVisible(false);
    addOrReplace(eredeContainer);

    altroContainer = new WebMarkupContainer("altroContainer");
    altroContainer.setOutputMarkupId(true);
    altroContainer.setOutputMarkupPlaceholderTag(true);
    altroContainer.addOrReplace(
        RichiestaRimborsoImuHelper.createFdCTextField(
            "altro",
            new PropertyModel<String>(rimborsoImu, "altro"),
            true,
            true,
            DatiSottoscrittoPanel.this));
    altroContainer.setVisible(false);
    addOrReplace(altroContainer);

    /* Erede */
    containerFile = new WebMarkupContainer("containerFile");
    containerFile.setVisible(false);
    containerFile.setOutputMarkupId(true);

    alertInfoDeleghe = new WebMarkupContainer("alertInfoDeleghe");
    alertInfoDeleghe.setOutputMarkupId(true);
    alertInfoDeleghe.setOutputMarkupPlaceholderTag(true);
    alertInfoDeleghe.setVisible(false);
    eredeContainer.addOrReplace(alertInfoDeleghe);

    // Label nomeFile = new Label("nomeFile", "");
    // Label dimensioneFile = new Label("dimensioneFile", "");

    /*eliminaFile = new AjaxButton("eliminaFile") {

    	private static final long serialVersionUID = 6421871465837323595L;

    	@Override
    	protected void onSubmit(AjaxRequestTarget targetElimina) {

    		containerFile.setVisible(false);
    		targetElimina.add(containerFile);
    	};
    };
    eliminaFile.setDefaultFormProcessing(false);*/

    // containerFile.addOrReplace(eliminaFile);

    // containerFile.addOrReplace(nomeFile);
    // containerFile.addOrReplace(dimensioneFile);

    formUploadFile =
        new Form<Void>("formUploadFile") {

          private static final long serialVersionUID = 5637460239713955952L;

          @Override
          protected void onSubmit() {

            final FileUpload uploadedFile = fileAttestazioneEredeUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };
    formUploadFile.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 5911456237189742864L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = fileAttestazioneEredeUpload.getFileUpload();

            log.debug("[OnAfterSubmit] UploadFile: " + uploadedFile);

            if (uploadedFile != null) {

              containerFile.setVisible(true);
              setEnabledUploadDocumenti();
              target.add(formUploadFile, eredeContainer, alertInfoDeleghe);
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
    formUploadFile.setVisible(false);

    fileAttestazioneEredeUpload =
        new FileUploadField("fileAttestazioneEredeUpload") {
          private static final long serialVersionUID = 1L;

          @Override
          public void convertInput() {
            FileUpload fileUpload = fileAttestazioneEredeUpload.getFileUpload();
            if (fileUpload != null) {
              RimborsoImuAllegato delegaErede = new RimborsoImuAllegato();
              delegaErede.setAllegato(TipoAllegatoEnum.DELEGA_COERDE);
              delegaErede.setId(UUID.randomUUID());
              delegaErede.setNomeFile(fileUpload.getClientFileName());
              delegaErede.setDocumento(fileUpload.getBytes());
              delegaErede.setDimensione(fileUpload.getSize());
              rimborsoImu.getDelegaErede().add(delegaErede);

              fileName = fileUpload.getClientFileName();
              dimensione = FileFdCUtil.getSizeFile(fileUpload.getSize());
            } else {
              setConvertedInput(getModelObject());
            }
          }
        };

    formUploadFile.addOrReplace(fileAttestazioneEredeUpload);

    fileAttestazioneEredeUpload.setLabel(Model.of("Upload delega a coerede"));
    fileAttestazioneEredeUpload.add(new FileUploadDieteValidator());
    fileAttestazioneEredeUpload.setOutputMarkupId(true);
    fileAttestazioneEredeUpload.setOutputMarkupPlaceholderTag(true);

    eredeContainer.addOrReplace(formUploadFile);

    PageableListView<RimborsoImuAllegato> fileList =
        new PageableListView<RimborsoImuAllegato>("listaFile", rimborsoImu.getDelegaErede(), 2) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<RimborsoImuAllegato> row) {
            // TODO Auto-generated method stub
            final RimborsoImuAllegato item = row.getModelObject();

            row.addOrReplace(new Label("nomeFile", item.getNomeFile()));
            row.addOrReplace(
                new Label("dimensioneFile", FileFdCUtil.getSizeFile(item.getDimensione())));

            AjaxButton eliminaFile =
                new AjaxButton("eliminaFile") {
                  private static final long serialVersionUID = 198796541318L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget target) {
                    // TODO Auto-generated method stub
                    if (rimborsoImu.getDelegaErede().size() > 0) {
                      log.debug("[RemoveFile] Rimuovo il documento dalla Lista");
                      RimborsoImuAllegato im =
                          rimborsoImu.getDelegaErede().stream()
                              .filter(x -> x.getId().equals(item.getId()))
                              .findFirst()
                              .get();

                      rimborsoImu.getDelegaErede().remove(im);
                    }

                    setEnabledUploadDocumenti();

                    target.add(containerFile, formUploadFile);
                  }
                };

            eliminaFile.setDefaultFormProcessing(false);

            row.addOrReplace(eliminaFile);
          }
        };

    containerFile.addOrReplace(fileList);
  }

  private void setEnabledUploadDocumenti() {
    // TODO Auto-generated method stub
    if (rimborsoImu.getTipoErede().equals(TipoEredeEnum.COEREDE)
        && rimborsoImu.getDelegaErede().size() == 2) {

      fileAttestazioneEredeUpload.setEnabled(false);
    } else if (rimborsoImu.getTipoErede().equals(TipoEredeEnum.EREDE_UNICO)
        && rimborsoImu.getDelegaErede().size() == 1) {
      fileAttestazioneEredeUpload.setEnabled(false);
    } else {
      fileAttestazioneEredeUpload.setEnabled(true);
    }
  }

  private void clearDatiDefunto() {
    rimborsoImu.setCodiceFiscaleDefunto(null);
    rimborsoImu.setCittadinanzaDefunto(null);
    rimborsoImu.setDataDecesso(null);
    rimborsoImu.setDataNascitaDefunto(null);
    rimborsoImu.setCittadinanzaDefunto(null);
    rimborsoImu.setNomeDefunto(null);
    rimborsoImu.setCognomeDefunto(null);
    rimborsoImu.setSessoDefunto(null);
    rimborsoImu.setLuogoDecesso(null);
    rimborsoImu.setLuogoNascitaDefunto(null);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
