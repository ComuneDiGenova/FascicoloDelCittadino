package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.DocumentoCaricato;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.modali.ModaleEliminaAllegatoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.StringGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class CaricaDocumentiForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = -518065887404962701L;

  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer containerUploadFile;

  private Form<?> formUploadAllegati;

  private FileUploadField documentoUpload;

  private WebMarkupContainer containerDocumentiCaricati;

  private PaginazioneFascicoloPanel paginationDocumentiCaricati;

  private VariazioniResidenza variazioniResidenza;

  private ModaleEliminaAllegatoPanel<String> modaleEliminaAllegato;

  WebMarkupContainer containerFileUpload;

  public CaricaDocumentiForm(String id, VariazioniResidenza model, FeedbackPanel feedbackPanel) {
    super(id, model);

    setOutputMarkupId(true);

    this.feedbackPanel = feedbackPanel;
    this.variazioniResidenza = model;

    addElementiForm();
  }

  @Override
  public void addElementiForm() {

    containerUploadFile = new WebMarkupContainer("containerUploadFile");
    containerUploadFile.setOutputMarkupId(true);
    containerUploadFile.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerUploadFile);

    containerDocumentiCaricati = new WebMarkupContainer("containerDocumentiCaricati");
    List<DocumentoPratica> listaDocumentiCaricati = new ArrayList<DocumentoPratica>();
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdPratica())) {
      listaDocumentiCaricati =
          VariazioniResidenzaUtil.getDocumentiCaricati(variazioniResidenza.getIdPratica());
    }
    containerDocumentiCaricati.setVisible(
        LabelFdCUtil.checkIfNotNull(listaDocumentiCaricati)
            && !LabelFdCUtil.checkEmptyList(listaDocumentiCaricati));
    containerDocumentiCaricati.setOutputMarkupId(true);
    containerDocumentiCaricati.setOutputMarkupPlaceholderTag(true);

    PageableListView<DocumentoPratica> listViewBoxDocumentiCaricati =
        new PageableListView<DocumentoPratica>("boxDocumentiCaricati", listaDocumentiCaricati, 6) {

          private static final long serialVersionUID = -8546764796525798682L;

          @Override
          protected void populateItem(ListItem<DocumentoPratica> item) {
            final DocumentoPratica documento = item.getModelObject();

            Label idDocumento = new Label("idDocumento", documento.getIdDocumento());
            idDocumento.setVisible(LabelFdCUtil.checkIfNotNull(documento.getIdDocumento()));
            item.addOrReplace(idDocumento);

            Label nomeDocumento = new Label("nomeDocumento", documento.getNomeDocumento());
            nomeDocumento.setVisible(PageUtil.isStringValid(documento.getNomeDocumento()));
            item.addOrReplace(nomeDocumento);

            try {
              if (LabelFdCUtil.checkIfNotNull(documento)
                  && LabelFdCUtil.checkIfNotNull(documento.getNomeDocumento())
                  && LabelFdCUtil.checkIfNotNull(documento.getDocumento())) {
                item.addOrReplace(
                    VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                        "btnDownloadDocumento",
                        documento.getNomeDocumento(),
                        documento.getDocumento()));
              } else {
                WebMarkupContainer btnDownloadDocumento =
                    new WebMarkupContainer("btnDownloadDocumento");
                btnDownloadDocumento.setVisible(false);
                item.addOrReplace(btnDownloadDocumento);
              }
            } catch (BusinessException | MagicMatchNotFoundException e) {
              log.error("Errore scarica documento apk: " + e.getMessage());
            }

            modaleEliminaAllegato =
                new ModaleEliminaAllegatoPanel<String>("modaleEliminaAllegato", documento);
            String infoAnnullamento = getString("CaricaDocumentiForm.id").concat(" ");
            Label allegatoDaAnnullare =
                new Label(
                    "allegatoDaAnnullare",
                    infoAnnullamento.concat(String.valueOf(documento.getIdDocumento())));
            modaleEliminaAllegato.myAdd(allegatoDaAnnullare);
            modaleEliminaAllegato.addOrReplace(
                creaBtnSi(modaleEliminaAllegato, documento.getIdDocumento()));
            modaleEliminaAllegato.addOrReplace(
                creaBtnNo(modaleEliminaAllegato, variazioniResidenza));
            item.addOrReplace(modaleEliminaAllegato);
            item.addOrReplace(
                creaBtnCancellaDocumento(modaleEliminaAllegato, documento.getIdDocumento()));
          }
        };

    containerDocumentiCaricati.addOrReplace(listViewBoxDocumentiCaricati);

    paginationDocumentiCaricati =
        new PaginazioneFascicoloPanel("paginationDocumentiCaricati", listViewBoxDocumentiCaricati);
    paginationDocumentiCaricati.setVisible(listaDocumentiCaricati.size() > 6);
    containerDocumentiCaricati.addOrReplace(paginationDocumentiCaricati);

    addOrReplace(containerDocumentiCaricati);

    containerFileUpload = new WebMarkupContainer("containerFileUpload");
    containerFileUpload.setVisible(false);
    containerFileUpload.setOutputMarkupId(true);

    formUploadAllegati =
        new Form<Void>("formUploadAllegati") {

          private static final long serialVersionUID = 2245687007224827251L;

          @Override
          protected void onSubmit() {
            final FileUpload uploadedFile = documentoUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };

    formUploadAllegati.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -8546764796525798682L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {

            final FileUpload uploadedFile = documentoUpload.getFileUpload();

            if (uploadedFile != null) {

              setDocumentoUpload(documentoUpload);

              containerFileUpload.setVisible(true);

              DocumentoCaricato documento = new DocumentoCaricato();

              documento.setByteDocumentoUpload(uploadedFile.getBytes());
              documento.setDocumentoUpload(documentoUpload);
              documento.setIdPratica(getModelObject().getIdPratica());
              documento.setNomeFile(uploadedFile.getClientFileName());

              Label nomeFile = new Label("nomeFile", documento.getNomeFile());
              containerFileUpload.addOrReplace(nomeFile);

              Label dimensioneFile =
                  new Label(
                      "dimensioneFile",
                      FileFdCUtil.getSizeFile(documento.getByteDocumentoUpload().length));
              containerFileUpload.addOrReplace(dimensioneFile);

              containerFileUpload.addOrReplace(creaBtnCaricaDocumento(documento));

              AjaxButton eliminaFile =
                  new AjaxButton("eliminaFile") {

                    private static final long serialVersionUID = 6550282645552296193L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerFileUpload.setVisible(false);

                      documento.setByteDocumentoUpload(null);
                      documento.setDocumentoUpload(null);
                      documento.setIdPratica(null);
                      documento.setNomeFile(null);

                      targetElimina.add(containerFileUpload);
                    }
                  };
              eliminaFile.setDefaultFormProcessing(false);

              containerFileUpload.addOrReplace(eliminaFile);

              target.add(formUploadAllegati, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            formUploadAllegati.setMultiPart(true);
            target.add(formUploadAllegati, feedbackPanel);
          }
        });

    formUploadAllegati.addOrReplace(containerFileUpload);

    formUploadAllegati.setMultiPart(true);

    documentoUpload = new FileUploadField("documentoUpload");
    documentoUpload.setOutputMarkupId(true);
    documentoUpload.setLabel(Model.of("Upload documenti"));
    documentoUpload.add(new FileUploadValidator());

    formUploadAllegati.addOrReplace(documentoUpload);
    containerUploadFile.addOrReplace(formUploadAllegati);
    addOrReplace(containerUploadFile);
  }

  public FeedbackPanel getFeedbackPanel() {
    return feedbackPanel;
  }

  public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
    this.feedbackPanel = feedbackPanel;
  }

  public WebMarkupContainer getContainerUploadFile() {
    return containerUploadFile;
  }

  public void setContainerUploadFile(WebMarkupContainer containerUploadFile) {
    this.containerUploadFile = containerUploadFile;
  }

  public Form<?> getFormUploadAllegati() {
    return formUploadAllegati;
  }

  public void setFormUploadAllegati(Form<?> formUploadAllegati) {
    this.formUploadAllegati = formUploadAllegati;
  }

  public FileUploadField getDocumentoUpload() {
    return documentoUpload;
  }

  public void setDocumentoUpload(FileUploadField documentoUpload) {
    this.documentoUpload = documentoUpload;
  }

  private LaddaAjaxButton creaBtnCancellaDocumento(
      Modal<String> modaleEliminaAllegato, Integer idDocumento) {
    LaddaAjaxButton btnCancellaDocumento =
        new LaddaAjaxButton("btnCancellaDocumento", Type.Primary) {

          private static final long serialVersionUID = 1210070737375199240L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            modaleEliminaAllegato.show(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore step 4 cancella documento");
          }
        };
    btnCancellaDocumento.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiForm.elimina", CaricaDocumentiForm.this)));

    IconType iconType =
        new IconType("btnCancellaDocumento") {

          private static final long serialVersionUID = -8923243373846560932L;

          @Override
          public String cssClassName() {
            return "icon-spazzatura";
          }
        };
    btnCancellaDocumento.setIconType(iconType);

    btnCancellaDocumento.setOutputMarkupId(true);
    btnCancellaDocumento.setOutputMarkupPlaceholderTag(true);

    return btnCancellaDocumento;
  }

  private LaddaAjaxLink<Object> creaBtnSi(Modal<String> modale, Integer documentoId) {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -3647872176845097659L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            try {
              StringGenericResponse praticaSospesa =
                  ServiceLocator.getInstance()
                      .getServiziAnagrafici()
                      .cancellaAllegatoCaricato(documentoId);
              if (LabelFdCUtil.checkIfNotNull(praticaSospesa)
                  && praticaSospesa.getStatus().equalsIgnoreCase("OK")) {

                vaiCambioORichiesta();

              } else {
                target.add(feedbackPanel);
                error("Errore durante cancellazione documento");
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore delete documento " + e.getMessage());
            }
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiForm.si", CaricaDocumentiForm.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo(
      Modal<String> modaleEliminaAllegato, VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> btnNo =
        new LaddaAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = 3360928691362261115L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modaleEliminaAllegato.close(target);
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiForm.no", CaricaDocumentiForm.this)));

    return btnNo;
  }

  private LaddaAjaxButton creaBtnCaricaDocumento(DocumentoCaricato documento) {
    LaddaAjaxButton btnCaricaFile =
        new LaddaAjaxButton("btnCaricaFile", Type.Primary) {

          private static final long serialVersionUID = -8196453157788619205L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            try {
              PostInserimentoAllegatiResponseGenericResponse caricaAllegato =
                  ServiceLocator.getInstance()
                      .getServiziAnagrafici()
                      .inserimentoAllegato(variazioniResidenza.getIdPratica(), documento);

              if (LabelFdCUtil.checkIfNotNull(caricaAllegato)
                  && !caricaAllegato.getStatus().equalsIgnoreCase("KO")) {

                vaiCambioORichiesta();

                target.add(containerDocumentiCaricati);

              } else {
                error("Errore durante inserimento dell'allegato");

                target.add(feedbackPanel);
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore post inserimento allegato " + e.getMessage());
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore step 4 carica documento");
          }
        };
    btnCaricaFile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiForm.carica", CaricaDocumentiForm.this)));

    IconType iconType =
        new IconType("btnCaricaFile") {

          private static final long serialVersionUID = 3264452172039592276L;

          @Override
          public String cssClassName() {
            return "icon-allegati";
          }
        };
    btnCaricaFile.setIconType(iconType);

    btnCaricaFile.setOutputMarkupId(true);

    return btnCaricaFile;
  }

  private void vaiCambioORichiesta() {
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTipoVariazioneDiResidenza())) {
      if (variazioniResidenza.getTipoVariazioneDiResidenza().getId() == 1) {
        setResponsePage(new CambioIndirizzoPage(4, "Cambio indirizzo", variazioniResidenza));
      } else {
        setResponsePage(new RichiestaResidenzaPage(4, "Richiesta residenza", variazioniResidenza));
      }
    }

    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali().getIdTipo())) {
      if (variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("3")
          || variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("4")) {
        setResponsePage(new CambioIndirizzoPage(4, "Cambio indirizzo", variazioniResidenza));
      } else if (variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("1")) {
        setResponsePage(new RichiestaResidenzaPage(4, "Richiesta residenza", variazioniResidenza));
      }
    }
  }
}
