package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.AllegatoPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Bytes;

public class FormDocumentoAllegatoPermessiPersonalizzati
    extends AbstracFrameworkForm<AllegatoPermessiPersonalizzati> {

  private static final long serialVersionUID = 2025833823732541553L;

  public FileUploadField fileUploadField;

  public Form<?> formInterno;
  WebMarkupContainer containerFileUpload;
  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer wmkUploadLabel;
  private Label lblPulsanteUpload;

  private Utente utente;

  private int idDomanda;

  CompoundPropertyModel<AllegatoPermessiPersonalizzati> allegatoPermessiPersonalizzatiModel;

  private String wicketId;

  public FormDocumentoAllegatoPermessiPersonalizzati(
      String id,
      CompoundPropertyModel<AllegatoPermessiPersonalizzati> allegatoPermessiPersonalizzatiModel,
      FeedbackPanel feedbackPanel,
      Utente utente,
      String wicketId,
      int idDomanda) {
    super(id, allegatoPermessiPersonalizzatiModel.getObject());
    this.allegatoPermessiPersonalizzatiModel = allegatoPermessiPersonalizzatiModel;
    this.wicketId = wicketId;
    this.idDomanda = idDomanda;
    this.feedbackPanel = feedbackPanel;
    this.setUtente(utente);
    setOutputMarkupId(true);

    addElementiForm(utente);
  }

  /*
  private AjaxLink<Page> createDownloadLink() {
  	final AJAXDownload download = new AJAXDownload() {

  		private static final long serialVersionUID = 8189916125148718792L;


  		@Override
  		protected IResourceStream getResourceStream() {

  			ConfigurazioneInterface stampa = null;

  			try {

  				AllegatoBody allegatoBody = ServiceLocator.getInstance().getServiziPermessiPersonalizzati()
  						.getDomanda(idDomanda, allegatoPermessiPersonalizzatiModel.getObject().getNomeFile());
  				allegatoPermessiPersonalizzatiModel.getObject().setByteFile(allegatoBody.getAllegatoFile().getFile());

  		} catch (BusinessException | ApiException | IOException e) {
  			log.debug("Errore durante la chiamata delle API", e);
  			throw new RestartResponseAtInterceptPageException(
  					new ErroreServiziPage("elenco permessi personalizzati"));
  		}


  			return PageUtil.createResourceStream(allegatoPermessiPersonalizzatiModel.getObject().getByteFile());
  		}

  		@Override
  		protected String getFileName() {
  			return getString("FormDocumentoAllegatoPermessiPersonalizzatiForm.nomeFile");
  		}
  	};
  	add(download);

  	final AjaxLink<Page> linkDownload = new AjaxLink<Page>("btnDownload") {

  		private static final long serialVersionUID = 562860070843086644L;

  		@Override
  		protected void onComponentTag(final ComponentTag tag) {
  			super.onComponentTag(tag);
  			tag.put("target", "_blank");
  		}

  		@Override
  		public void onClick(final AjaxRequestTarget target) {
  			download.initiate(target);
  		}
  	};

  	return linkDownload;
  }
  */

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addElementiForm(Utente utente) {

    setFileMaxSize(Bytes.kilobytes(2000));

    containerFileUpload = new WebMarkupContainer("containerFileUpload");

    if (allegatoPermessiPersonalizzatiModel != null
        && allegatoPermessiPersonalizzatiModel.getObject().getNomeFile() != null) {
      containerFileUpload.setVisible(true);
    } else containerFileUpload.setVisible(false);
    containerFileUpload.setOutputMarkupId(true);

    formInterno =
        new Form<AllegatoPermessiPersonalizzati>(
            "formUpload", allegatoPermessiPersonalizzatiModel) {

          @Override
          protected void onBeforeRender() {
            super.onBeforeRender();
            final FileUpload uploadedFile = fileUploadField.getFileUpload();

            Label nomeFile =
                new Label(
                    "nomeFile", allegatoPermessiPersonalizzatiModel.getObject().getNomeFile());

            // AjaxLink<Page> downloadLink = createDownloadLink();

            // containerFileUpload.addOrReplace(downloadLink);

            containerFileUpload.addOrReplace(nomeFile);

            Label dimensioneFile =
                new Label(
                    "dimensioneFile",
                    getFileSize(
                        allegatoPermessiPersonalizzatiModel.getObject().getDimensioneFile()));
            containerFileUpload.addOrReplace(dimensioneFile);

            AjaxButton eliminaFile =
                new AjaxButton("eliminaFile") {

                  private static final long serialVersionUID = 2880168485223940915L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget targetElimina) {

                    containerFileUpload.setVisible(false);

                    allegatoPermessiPersonalizzatiModel.getObject().setNomeFile(null);
                    allegatoPermessiPersonalizzatiModel.getObject().setByteFile(null);

                    targetElimina.add(containerFileUpload, feedbackPanel);
                  }
                  ;
                };
            eliminaFile.setDefaultFormProcessing(false);

            containerFileUpload.addOrReplace(eliminaFile);
          }
        };
    formInterno.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 7369430723025556986L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {

            AllegatoPermessiPersonalizzati allegatoPermessiPersonalizzati =
                (AllegatoPermessiPersonalizzati) formInterno.getModelObject();

            final FileUpload uploadedFile = fileUploadField.getFileUpload();
            if (uploadedFile != null) {

              allegatoPermessiPersonalizzati.setNomeFile(uploadedFile.getClientFileName());
              allegatoPermessiPersonalizzati.setByteFile(uploadedFile.getBytes());
              allegatoPermessiPersonalizzati.setDimensioneFile(
                  Math.toIntExact(uploadedFile.getSize()));
              containerFileUpload.setVisible(true);

              Label nomeFile = new Label("nomeFile", uploadedFile.getClientFileName());

              // AjaxLink<Page> downloadLink = createDownloadLink();

              // containerFileUpload.addOrReplace(downloadLink);

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

                      allegatoPermessiPersonalizzati.setNomeFile(null);
                      allegatoPermessiPersonalizzati.setByteFile(null);

                      targetElimina.add(containerFileUpload, feedbackPanel);
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
            formInterno.setMultiPart(true);
            target.add(formInterno, feedbackPanel);
          }
        });

    formInterno.addOrReplace(containerFileUpload);

    String codiceAllegato = allegatoPermessiPersonalizzatiModel.getObject().getCodiceAllegato();

    this.setMultiPart(true);
    fileUploadField = new FileUploadField("uploadFile");
    fileUploadField.add(new AttributeModifier("id", codiceAllegato));
    fileUploadField.add(new FileUploadValidator());

    formInterno.addOrReplace(fileUploadField);

    wmkUploadLabel = new WebMarkupContainer("wmkUploadLabel");
    wmkUploadLabel.add(new AttributeModifier("for", codiceAllegato));
    formInterno.addOrReplace(wmkUploadLabel);

    wmkUploadLabel.addOrReplace(
        lblPulsanteUpload = new Label("lblPulsanteUpload", Model.of("Upload documento")));

    // formInterno.setVisible(false);
    addOrReplace(formInterno);

    setMultiPart(true);
    setMaxSize(Bytes.kilobytes(2000));
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

  private boolean checkEstensioneFile() {
    boolean estensioneOk = false;
    try {
      if (checkFileCaricato()) {
        String mimeType =
            FileFdCUtil.getMimeTypeFileUploadAllegato(
                allegatoPermessiPersonalizzatiModel.getObject().getByteFile());
        if (mimeType.equalsIgnoreCase("application/pdf")
            || mimeType.substring(0, 5).equalsIgnoreCase("image")) {
          estensioneOk = true;
        }
      }
    } catch (BusinessException | MagicMatchNotFoundException e) {
      log.error("Errore durante mime type patente: " + e.getMessage());
    }
    return estensioneOk;
  }

  private boolean checkFileCaricato() {
    boolean fileCaricato = false;
    if (getFileUploadField() != null) {
      if (getFileUploadField().getFileUpload().getClientFileName() != null) {
        fileCaricato = true;
      }
    }
    return fileCaricato;
  }

  public FileUploadField getFileUploadField() {
    return fileUploadField;
  }

  public void setFileUploadField(FileUploadField fileUploadField) {
    this.fileUploadField = fileUploadField;
  }
}
