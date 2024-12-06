package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DocumentoCaricatoIstanza;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.modali.ModaleEliminaAllegatoPLPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatoIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IdDocumentiIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class Step4IstanzaPlCaricaDocumentiIstanzaForm
    extends AbstracFrameworkForm<DatiRichiestaIstanzaPl> {

  private static final long serialVersionUID = -7349674609304452360L;

  private WebMarkupContainer s4containerUploadFile;

  private Form<?> s4formUploadAllegati;
  WebMarkupContainer s4containerFileUpload;
  // private FileUploadField s4documentoUpload;

  // private List<FileUploadField> s4ListdocumentoUploadDaCaricare;
  // private int s4NumeroDocumentoUploadDaCaricare;

  private WebMarkupContainer containerDocumentiCaricati;
  private WebMarkupContainer containerDocumentiDaCaricare;
  NotificationPanel feedbackPanel;
  DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;
  private ModaleEliminaAllegatoPLPanel<String> modaleEliminaAllegato;

  private PaginazioneFascicoloPanel paginationDocumentiCaricati;
  private PaginazioneFascicoloPanel paginationDocumentiDaCaricare;
  private int iii;

  public Step4IstanzaPlCaricaDocumentiIstanzaForm(
      String id, DatiRichiestaIstanzaPl model, NotificationPanel feedbackPanel) {
    super(id, model);
    this.feedbackPanel = feedbackPanel;
    iii = 0;
    addElementiForm();
  }

  public List<DatiDocumento> getDocumentiDaCaricare(
      List<IdDocumentiIstanza> listaDocumentiCaricati) {
    try {
      List<DatiDocumento> toRet = new ArrayList<>();

      log.debug("ZZZ getDocumentiDaCaricare: listaDocumentiCaricati " + listaDocumentiCaricati);
      // tutti quelli da inviare in base al codice motivo
      List<DatiDocumento> listDatiDocumentoDaInviare =
          datiRichiestaIstanzaPl.getDocumentiDaInviare();

      log.debug(
          "ZZZ getDocumentiDaCaricare: listDatiDocumentoDaInviare " + listDatiDocumentoDaInviare);
      // rimuovo da quelli da inviare i caricati cosi da ottenere quelli
      // ancora da caricare
      for (DatiDocumento documentoDaInviare : listDatiDocumentoDaInviare) {
        boolean boFound = false;
        for (IdDocumentiIstanza documentoCaricati : listaDocumentiCaricati) {
          if (documentoCaricati
              .getTipoDocumento()
              .equalsIgnoreCase(documentoDaInviare.getDocumento())) {
            boFound = true;
            break;
          }
        }
        if (!boFound) {
          toRet.add(documentoDaInviare);
        }
      }

      log.debug("ZZZ getDocumentiDaCaricare: listaDocumentiAncora da caricare " + toRet);
      // listDatiDocumento
      return toRet;
    } catch (Exception e) {
      log.error("Errore durante getDatiCompletiVerbaleDiPartenza:", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  public Boolean isObbligatoriCaricatiTutti(List<IdDocumentiIstanza> listaDocumentiCaricati) {
    // tipo documento errato non permette controlli
    // if(true)return true;

    try {

      if (listaDocumentiCaricati == null) {
        listaDocumentiCaricati = new ArrayList<>();
        listaDocumentiCaricati = getDocumentiCaricati();
      }
      List<DatiDocumento> toRet = new ArrayList<>();

      log.debug("ZZZ isObbligatoriCaricatiTutti: listaDocumentiCaricati " + listaDocumentiCaricati);
      // tutti quelli da inviare in base al codice motivo
      List<DatiDocumento> listDatiDocumentoDaInviareObbligatori =
          datiRichiestaIstanzaPl.getDocumentiDaInviare().stream()
              .filter(e -> "SI".equalsIgnoreCase(e.getObbligatorio()))
              .collect(Collectors.toList());

      log.debug(
          "ZZZ isObbligatoriCaricatiTutti: listDatiDocumentoDaInviareObbligatori "
              + listDatiDocumentoDaInviareObbligatori);
      // rimuovo da quelli da inviare i caricati cosi da ottenere quelli
      // ancora da caricare
      for (DatiDocumento documentoDaInviare : listDatiDocumentoDaInviareObbligatori) {
        boolean boFound = false;
        for (IdDocumentiIstanza documentoCaricati : listaDocumentiCaricati) {
          if (documentoCaricati
              .getTipoDocumento()
              .equalsIgnoreCase(documentoDaInviare.getDocumento())) {
            boFound = true;
            break;
          }
        }
        if (!boFound) {
          toRet.add(documentoDaInviare);
        }
      }

      log.debug(
          "ZZZ getDocumentiDaCaricare: listaDocumentiAncora da obbligatori caricare " + toRet);

      return toRet.isEmpty();
    } catch (Exception e) {
      log.error("Errore durante isObbligatoriCaricatiTutti:", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  public List<IdDocumentiIstanza> getDocumentiCaricati() {
    try {
      boolean attivo = true;
      List<IdDocumentiIstanza> listaDocumentiCaricatiPl = new ArrayList<IdDocumentiIstanza>();

      // ogni giro recupera dati documenti caricati chiamando il servizio
      Istanza istanza = datiRichiestaIstanzaPl.getIstanza();
      if (istanza != null) {
        log.debug(
            "List<IdDocumentiIstanza> getDocumentiCaricati:::: datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza():  "
                + datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza());
        istanza =
            ServiceLocator.getInstance()
                .getServiziMieiVerbali()
                .getDatiIstanza(
                    datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza().getVerbale(),
                    "" + istanza.getId(),
                    istanza.getAnno().intValue());
        listaDocumentiCaricatiPl =
            istanza != null && istanza.getDocumenti() != null
                ? istanza.getDocumenti()
                : new ArrayList<IdDocumentiIstanza>(); // List<IdDocumentiIstanza>

        log.debug(
            "Step4IstanzaPlCaricaDocumentiIstanzaForm listaDocumentiCaricatiPllistaDocumentiCaricatiPl: "
                + listaDocumentiCaricatiPl
                + " ------------");
      }
      return listaDocumentiCaricatiPl;
    } catch (Exception e) {
      log.error("Errore durante getDatiCompletiVerbaleDiPartenza:", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private LaddaAjaxButton creaBtnCancellaDocumento(
      Modal<String> modaleEliminaAllegato, BigDecimal idDocumento, boolean isCancellabile) {
    LaddaAjaxButton btnCancellaDocumento =
        new LaddaAjaxButton("btnCancellaDocumento", Type.Primary) {

          private static final long serialVersionUID = 1210070737375199240L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            modaleEliminaAllegato.show(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("errore step 4 cancella documento");
          }
        };
    btnCancellaDocumento.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaForm.elimina",
                    Step4IstanzaPlCaricaDocumentiIstanzaForm.this)));

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
    btnCancellaDocumento.setVisible(isCancellabile);
    return btnCancellaDocumento;
  }

  private LaddaAjaxLink<Object> creaBtnSi(Modal<String> modale, String documentoId) {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -3647872176845097659L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            try {
              EsitoOperazione esitoOperazione =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .deleteAllegatoIstanza(
                          datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza().getVerbale(),
                          "" + datiRichiestaIstanzaPl.getIstanza().getId(),
                          datiRichiestaIstanzaPl.getIstanza().getAnno().intValue(),
                          documentoId);

              if (LabelFdCUtil.checkIfNotNull(esitoOperazione)
                  && esitoOperazione.getEsito().equalsIgnoreCase("OK")) {
                datiRichiestaIstanzaPl.setTuttiDocumentiObbligatoriCaricati(
                    isObbligatoriCaricatiTutti(getDocumentiCaricati()));
                // vaiCambioORichiesta();
                setResponsePage(new GestisciIstanzaPLPage(4, datiRichiestaIstanzaPl));
              } else {
                target.add(feedbackPanel);
                error("Errore durante cancellazione documento");
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("errore delete documento ", e);
            }
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaForm.si",
                    Step4IstanzaPlCaricaDocumentiIstanzaForm.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo(
      Modal<String> modaleEliminaAllegato, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
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
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaForm.no",
                    Step4IstanzaPlCaricaDocumentiIstanzaForm.this)));

    return btnNo;
  }

  public Component createLinkDocumentoCaricato(
      String idWicket, String nomeFile, byte[] file, String estensione, String mimeType)
      throws BusinessException {
    ResourceLink<?> linkFile =
        VariazioniResidenzaUtil.downloadPdfImage(idWicket, nomeFile, file, estensione, mimeType);
    boolean visibile = LabelFdCUtil.checkIfNotNull(file);

    if (LabelFdCUtil.checkIfNotNull(linkFile)) {
      linkFile.setVisible(visibile);
      return linkFile;
    } else {
      WebMarkupContainer btnWicketId = new WebMarkupContainer(idWicket);
      btnWicketId.setVisible(false);

      return btnWicketId;
    }
  }

  @Override
  public void addElementiForm() {
    datiRichiestaIstanzaPl = getModelObject();

    datiRichiestaIstanzaPl.setDocumentiDaInviare(
        getDocumentiByCodiceHermes(
            datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato())); // ));

    Label labelNondevonoesserecaicatidocumenti = new Label("nondevonoesserecaicatidocumenti", "");
    addOrReplace(labelNondevonoesserecaicatidocumenti);

    Label labelPossibileinviaredocumenti = new Label("possibileinviaredocumenti", "");
    addOrReplace(labelPossibileinviaredocumenti);

    ListView<DatiDocumento> listViewDocumentazione = null;

    listViewDocumentazione =
        new ListView<DatiDocumento>(
            "listViewDocumentazione", datiRichiestaIstanzaPl.getDocumentiDaInviare()) {

          private static final long serialVersionUID = -1111111111111111L;

          @Override
          protected void populateItem(ListItem<DatiDocumento> item) {
            DatiDocumento datiDocumento = item.getModelObject();

            item.setOutputMarkupId(true);

            if (datiDocumento != null) {
              String documentazione = datiDocumento.getDocumento();

              Label documentazioneLabel = new Label("documentazione", documentazione);
              documentazioneLabel.setVisible(documentazione != null);
              item.addOrReplace(documentazioneLabel);
            }
          }
        };

    listViewDocumentazione.setVisible(
        datiRichiestaIstanzaPl.getDocumentiDaInviare() != null
            && !datiRichiestaIstanzaPl.getDocumentiDaInviare().isEmpty());
    addOrReplace(listViewDocumentazione);

    Integer numeroDocumentiDaInviare =
        datiRichiestaIstanzaPl.getDocumentiDaInviare() != null
                && !datiRichiestaIstanzaPl.getDocumentiDaInviare().isEmpty()
            ? datiRichiestaIstanzaPl.getDocumentiDaInviare().size()
            : 0;
    Label labelnumeroDocumentiDaInviare =
        new Label("numeroDocumentiDaInviare", "" + numeroDocumentiDaInviare);
    labelnumeroDocumentiDaInviare.setVisible(false);
    addOrReplace(labelnumeroDocumentiDaInviare);

    List<IdDocumentiIstanza> listaDocumentiCaricati = new ArrayList<IdDocumentiIstanza>();
    listaDocumentiCaricati = getDocumentiCaricati();

    List<DatiDocumento> listaDocumentiDaCaricare = new ArrayList<DatiDocumento>();
    listaDocumentiDaCaricare = getDocumentiDaCaricare(listaDocumentiCaricati);

    Integer numeroDocumentiInviati =
        listaDocumentiCaricati != null && !listaDocumentiCaricati.isEmpty()
            ? listaDocumentiCaricati.size()
            : 0;
    Label labelnumeroDocumentiInviati =
        new Label("numeroDocumentiInviati", "" + numeroDocumentiInviati);
    labelnumeroDocumentiInviati.setVisible(false);
    addOrReplace(labelnumeroDocumentiInviati);

    containerDocumentiCaricati = new WebMarkupContainer("containerDocumentiCaricati");

    datiRichiestaIstanzaPl.setDocumentiCaricatiHeader(listaDocumentiCaricati);
    containerDocumentiCaricati.setVisible(
        LabelFdCUtil.checkIfNotNull(listaDocumentiCaricati)
            && !LabelFdCUtil.checkEmptyList(listaDocumentiCaricati));
    containerDocumentiCaricati.setOutputMarkupId(true);
    containerDocumentiCaricati.setOutputMarkupPlaceholderTag(true);

    PageableListView<IdDocumentiIstanza> listViewBoxDocumentiCaricati =
        new PageableListView<IdDocumentiIstanza>(
            "boxDocumentiCaricati", listaDocumentiCaricati, 6) {

          private static final long serialVersionUID = -8546764796525798682L;

          @Override
          protected void populateItem(ListItem<IdDocumentiIstanza> item) {
            final IdDocumentiIstanza documento = item.getModelObject();

            Label idDocumento = new Label("idDocumento", documento.getId());
            idDocumento.setVisible(LabelFdCUtil.checkIfNotNull(documento.getId()));
            item.addOrReplace(idDocumento);

            Label hashDocumento = new Label("hashDocumento", documento.getHash());
            hashDocumento.setVisible(LabelFdCUtil.checkIfNotNull(documento.getHash()));
            item.addOrReplace(hashDocumento);

            Label tipoDocumento = new Label("tipoDocumento", documento.getTipoDocumento());
            tipoDocumento.setVisible(PageUtil.isStringValid(documento.getTipoDocumento()));
            item.addOrReplace(tipoDocumento);

            Label nomeDocumento = new Label("nomeDocumento", documento.getNome());
            nomeDocumento.setVisible(PageUtil.isStringValid(documento.getNome()));
            item.addOrReplace(nomeDocumento);

            try {
              if (LabelFdCUtil.checkIfNotNull(documento)
                  && LabelFdCUtil.checkIfNotNull(documento.getNome())) {
                // chiama servizio qui per i byte
                FileAllegato fileAllegato =
                    ServiceLocator.getInstance()
                        .getServiziMieiVerbali()
                        .getAllegatoIstanza(
                            datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza().getVerbale(),
                            "" + datiRichiestaIstanzaPl.getIstanza().getId(),
                            datiRichiestaIstanzaPl.getIstanza().getAnno().intValue(),
                            "" + documento.getId());
                String estensione =
                    FileFdCUtil.getEstensionFileUploadAllegato(fileAllegato.getFile());
                String mimeType = FileFdCUtil.getMimeTypeFileUploadAllegato(fileAllegato.getFile());
                item.addOrReplace(
                    createLinkDocumentoCaricato(
                        "btnDownloadDocumento",
                        documento.getNome(),
                        fileAllegato.getFile(),
                        estensione,
                        mimeType));
              } else {
                WebMarkupContainer btnDownloadDocumento =
                    new WebMarkupContainer("btnDownloadDocumento");
                btnDownloadDocumento.setVisible(false);
                item.addOrReplace(btnDownloadDocumento);
              }
            } catch (Exception e) {
              log.error("Errore scarica documento: " + e.getMessage());
            }

            modaleEliminaAllegato =
                new ModaleEliminaAllegatoPLPanel<String>("modaleEliminaAllegato", documento);
            String infoAnnullamento =
                getString("Step4IstanzaPlCaricaDocumentiIstanzaForm.id").concat(" ");
            Label allegatoDaAnnullare =
                new Label(
                    "allegatoDaAnnullare",
                    infoAnnullamento.concat(String.valueOf(documento.getId())));
            modaleEliminaAllegato.myAdd(allegatoDaAnnullare);
            modaleEliminaAllegato.addOrReplace(
                creaBtnSi(modaleEliminaAllegato, "" + documento.getId()));
            modaleEliminaAllegato.addOrReplace(
                creaBtnNo(modaleEliminaAllegato, datiRichiestaIstanzaPl));
            item.addOrReplace(modaleEliminaAllegato);
            item.addOrReplace(
                creaBtnCancellaDocumento(
                    modaleEliminaAllegato, documento.getId(), documento.getIsCancellabile()));
          }
        };

    containerDocumentiCaricati.addOrReplace(listViewBoxDocumentiCaricati);

    paginationDocumentiCaricati =
        new PaginazioneFascicoloPanel("paginationDocumentiCaricati", listViewBoxDocumentiCaricati);
    paginationDocumentiCaricati.setVisible(listaDocumentiCaricati.size() > 6);
    containerDocumentiCaricati.addOrReplace(paginationDocumentiCaricati);

    addOrReplace(containerDocumentiCaricati);

    /* DA CARICARE */
    // s4ListdocumentoUploadDaCaricare = new ArrayList<FileUploadField>();
    // s4NumeroDocumentoUploadDaCaricare = 0;

    PageableListView<DatiDocumento> listViewBoxDocumentiDaCaricare =
        new PageableListView<DatiDocumento>("boxDocumentiDaCaricare", listaDocumentiDaCaricare, 6) {

          private static final long serialVersionUID = -1l;

          // private List<FileUploadField> s4ListdocumentoUploadDaCaricare =
          // new ArrayList<FileUploadField>();
          // private int s4NumeroDocumentoUploadDaCaricare = 0;
          private HashMap<String, FileUploadField> s4MapDocumentoUploadDaCaricare =
              new HashMap<String, FileUploadField>();
          private HashMap<String, WebMarkupContainer> s4MapcontainerFileUploadDaCaricare =
              new HashMap<String, WebMarkupContainer>();

          // FileUploadField s4documentoUploadDaCaricareTemp;

          @Override
          protected void populateItem(ListItem<DatiDocumento> item) {
            final String tipoDocumento = item.getModelObject().getDocumento();
            final String obbligatorioDocumento = item.getModelObject().getObbligatorio();

            FileUploadField s4documentoUploadDaCaricareTemp;
            String ss = "";
            if (s4MapDocumentoUploadDaCaricare.get(tipoDocumento) == null) {
              ss = "" + iii;
              iii++;
            }
            String idsss = "s4documentoUpload" + ss;
            // s4documentoUploadDaCaricareTemp = new FileUploadField(idsss);
            s4documentoUploadDaCaricareTemp = new FileUploadField("s4documentoUpload");
            s4documentoUploadDaCaricareTemp.setOutputMarkupId(true);
            s4documentoUploadDaCaricareTemp.setLabel(Model.of("UploadDD"));
            s4documentoUploadDaCaricareTemp.add(new FileUploadValidator());

            s4documentoUploadDaCaricareTemp.setOutputMarkupId(true);
            s4documentoUploadDaCaricareTemp.setOutputMarkupPlaceholderTag(true);
            s4documentoUploadDaCaricareTemp.setMarkupId("pulsante" + item.getIndex());

            // s4ListdocumentoUploadDaCaricare.add(s4documentoUploadDaCaricareTemp);
            s4MapDocumentoUploadDaCaricare.put(tipoDocumento, s4documentoUploadDaCaricareTemp);

            // Label tipoDocumentoDaCaricare = new
            // Label("tipoDocumentoDaCaricare", tipoDocumento);
            // tipoDocumentoDaCaricare.setVisible(
            // LabelFdCUtil.checkIfNotNull(tipoDocumentoDaCaricare));
            // item.addOrReplace(tipoDocumentoDaCaricare);
            //
            // Label obbligatorioSIoNO = new Label("obbligatorioSIoNO",
            // obbligatorioDocumento);
            // obbligatorioSIoNO.setVisible(
            // LabelFdCUtil.checkIfNotNull(obbligatorioSIoNO));
            // item.addOrReplace(obbligatorioSIoNO);

            WebMarkupContainer s4containerFileUploadDaCaricare2 =
                new WebMarkupContainer("s4containerFileUploadDaCaricare");
            s4containerFileUploadDaCaricare2.setVisible(false);
            s4containerFileUploadDaCaricare2.setOutputMarkupId(true);
            s4MapcontainerFileUploadDaCaricare.put(tipoDocumento, s4containerFileUploadDaCaricare2);

            Form<?> s4formUploadAllegatiDaCaricare =
                new Form<Void>("s4formUploadAllegatiDaCaricare") {

                  private static final long serialVersionUID = -2345676436548685734L;

                  // private FileUploadField s4documentoUploadDaCaricare;

                  @Override
                  protected void onSubmit() {
                    log.debug("pulsante s4formUploadAllegati  onSubmit");
                  }

                  // public FileUploadField getFileUploadField() {
                  // return s4documentoUploadDaCaricare;
                  // }

                };
            Label tipoDocumentoDaCaricare = new Label("tipoDocumentoDaCaricare", tipoDocumento);
            tipoDocumentoDaCaricare.setVisible(
                LabelFdCUtil.checkIfNotNull(tipoDocumentoDaCaricare));
            s4formUploadAllegatiDaCaricare.addOrReplace(tipoDocumentoDaCaricare);

            /*
             * s4formUploadAllegatiDaCaricare.setOutputMarkupId(true);
             * s4formUploadAllegatiDaCaricare.setOutputMarkupPlaceholderTag( true);
             * s4formUploadAllegatiDaCaricare.setMarkupId("s4Forn" + item.getId());
             */
            Label obbligatorioSIoNO = new Label("obbligatorioSIoNO", obbligatorioDocumento);
            obbligatorioSIoNO.setVisible(LabelFdCUtil.checkIfNotNull(obbligatorioSIoNO));
            s4formUploadAllegatiDaCaricare.addOrReplace(obbligatorioSIoNO);

            s4formUploadAllegatiDaCaricare.add(
                new AjaxFormSubmitBehavior("change") {

                  private static final long serialVersionUID = -123456654323456876L;

                  @Override
                  protected void onAfterSubmit(AjaxRequestTarget target) {
                    log.debug("s4formUploadAllegatiDaCaricare onChange ");

                    final FileUpload uploadedFile =
                        s4MapDocumentoUploadDaCaricare.get(tipoDocumento).getFileUpload();

                    String documentoGetNomeFile = "-";
                    String documentoGetSizeFile = "-";
                    DocumentoCaricatoIstanza documento = new DocumentoCaricatoIstanza();
                    if (uploadedFile != null
                        && uploadedFile.getBytes() != null
                        && uploadedFile.getBytes().length > 0) {

                      // setS4DocumentoUpload(
                      // listS4documentoUploadDaCaricare.get(numeroS4documentoUploadDaCaricare));
                      log.debug("tipoDocumento: " + tipoDocumento);
                      s4MapcontainerFileUploadDaCaricare.get(tipoDocumento).setVisible(true);
                      //

                      //
                      documento.setByteDocumentoUpload(uploadedFile.getBytes());
                      documento.setDocumentoUpload(
                          s4MapDocumentoUploadDaCaricare.get(tipoDocumento));
                      documento.setIdPratica(1); // getModelObject().getIdPratica());
                      documento.setNomeFile(uploadedFile.getClientFileName());
                      documento.setTipo(tipoDocumento);

                      documentoGetNomeFile = documento.getNomeFile();
                      documentoGetSizeFile =
                          FileFdCUtil.getSizeFile(documento.getByteDocumentoUpload().length);
                    }

                    log.debug(
                        "aaaaaaaaaaaaaaaaaaaaaaaa1111111111111111111111111111 tipoDocumento: "
                            + tipoDocumento);
                    Label nomeFile = new Label("nomeFileDaCaricare", documentoGetNomeFile);
                    s4MapcontainerFileUploadDaCaricare.get(tipoDocumento).addOrReplace(nomeFile);

                    Label dimensioneFile =
                        new Label("dimensioneFileDaCaricare", documentoGetSizeFile);
                    s4MapcontainerFileUploadDaCaricare
                        .get(tipoDocumento)
                        .addOrReplace(dimensioneFile);

                    s4MapcontainerFileUploadDaCaricare
                        .get(tipoDocumento)
                        .addOrReplace(creaBtnCaricaDocumento(documento, tipoDocumento));

                    AjaxButton eliminaFile =
                        new AjaxButton("eliminaFileDaCaricare") {

                          private static final long serialVersionUID = -1L;

                          @Override
                          protected void onSubmit(AjaxRequestTarget targetElimina) {

                            s4MapcontainerFileUploadDaCaricare.get(tipoDocumento).setVisible(false);
                            documento.setByteDocumentoUpload(null);
                            documento.setDocumentoUpload(null);
                            documento.setIdPratica(null);
                            documento.setNomeFile(null);
                            targetElimina.add(
                                s4MapcontainerFileUploadDaCaricare.get(tipoDocumento));
                          }
                        };
                    eliminaFile.setDefaultFormProcessing(false);
                    log.debug("aaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddd ");
                    s4MapcontainerFileUploadDaCaricare.get(tipoDocumento).addOrReplace(eliminaFile);
                    log.debug("aaaaaaaaaaaaaaaaaaaaaaaaeeeeeeeeeeeeeeeeeeeee ");
                    target.add(s4formUploadAllegatiDaCaricare, feedbackPanel);
                  }

                  @Override
                  protected void onError(AjaxRequestTarget target) {
                    super.onError(target);
                    target.add(feedbackPanel);
                  }
                });
            log.debug(
                "aaaa s4formUploadAllegatiDaCaricare.addOrReplace(s4MapcontainerFileUploadDaCaricare.get(tipoDocumento), tipoDocumento: "
                    + tipoDocumento);

            s4formUploadAllegatiDaCaricare.addOrReplace(
                s4MapcontainerFileUploadDaCaricare.get(tipoDocumento));

            s4formUploadAllegatiDaCaricare.setMultiPart(true);
            // s4documentoUploadDaCaricareTemp = new
            // FileUploadField("s4documentoUpload");
            // s4documentoUploadDaCaricareTemp.setOutputMarkupId(true);
            // s4documentoUploadDaCaricareTemp.setLabel(Model.of("UploadDD"));
            // s4documentoUploadDaCaricareTemp.add(new
            // FileUploadValidator());

            FileUploadField fileUploadField = s4MapDocumentoUploadDaCaricare.get(tipoDocumento);
            s4formUploadAllegatiDaCaricare.addOrReplace(fileUploadField);

            WebMarkupContainer lblS4documentoUpload =
                new WebMarkupContainer("lblS4documentoUpload");
            lblS4documentoUpload.add(new AttributeModifier("for", fileUploadField.getMarkupId()));

            s4formUploadAllegatiDaCaricare.addOrReplace(lblS4documentoUpload);

            item.addOrReplace(s4formUploadAllegatiDaCaricare);
          }
        };

    containerDocumentiDaCaricare = new WebMarkupContainer("containerDocumentiDaCaricare");

    containerDocumentiDaCaricare.setVisible(
        LabelFdCUtil.checkIfNotNull(listaDocumentiDaCaricare)
            && !LabelFdCUtil.checkEmptyList(listaDocumentiDaCaricare));
    containerDocumentiDaCaricare.setOutputMarkupId(true);
    containerDocumentiDaCaricare.setOutputMarkupPlaceholderTag(true);

    containerDocumentiDaCaricare.addOrReplace(listViewBoxDocumentiDaCaricare);

    paginationDocumentiDaCaricare =
        new PaginazioneFascicoloPanel(
            "paginationDocumentiDaCaricare", listViewBoxDocumentiDaCaricare);
    paginationDocumentiDaCaricare.setVisible(listViewBoxDocumentiDaCaricare.size() > 6);
    containerDocumentiDaCaricare.addOrReplace(paginationDocumentiDaCaricare);

    addOrReplace(containerDocumentiDaCaricare);

    s4containerUploadFile = new WebMarkupContainer("s4containerUploadFile");
    s4containerUploadFile.setOutputMarkupId(true);
    s4containerUploadFile.setOutputMarkupPlaceholderTag(true);
    /*
     * addOrReplace(s4containerUploadFile);
     *
     * s4containerFileUpload = new WebMarkupContainer("s4containerFileUpload");
     * s4containerFileUpload.setVisible(false);
     * s4containerFileUpload.setOutputMarkupId(true);
     *
     * s4formUploadAllegati = new Form<Void>("s4formUploadAllegati") {
     *
     * private static final long serialVersionUID = -2345676436548685734L;
     *
     * @Override protected void onSubmit() {
     * log.error("pulsante s4formUploadAllegati  onSubmit"); }
     *
     * }; s4containerUploadFile.addOrReplace(s4formUploadAllegati);
     *
     * s4formUploadAllegati.add(new AjaxFormSubmitBehavior("change") {
     *
     * private static final long serialVersionUID = -123456654323456876L;
     *
     * @Override protected void onAfterSubmit(AjaxRequestTarget target) {
     * log.error("s4formUploadAllegati onChange "); final FileUpload uploadedFile =
     * s4documentoUpload.getFileUpload(); if (uploadedFile != null) {
     *
     * setS4DocumentoUpload(s4documentoUpload);
     * log.error("aaaaaaaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccc ");
     * s4containerFileUpload.setVisible(true); // DocumentoCaricatoIstanza documento
     * = new DocumentoCaricatoIstanza(); //
     * documento.setByteDocumentoUpload(uploadedFile.getBytes());
     * documento.setDocumentoUpload(s4documentoUpload); documento.setIdPratica(1);//
     * getModelObject().getIdPratica());
     * documento.setNomeFile(uploadedFile.getClientFileName());
     *
     * log.error("aaaaaaaaaaaaaaaaaaaaaaaa1111111111111111111111111111 "); Label
     * nomeFile = new Label("nomeFile", documento.getNomeFile());
     * s4containerFileUpload.addOrReplace(nomeFile);
     *
     * Label dimensioneFile = new Label("dimensioneFile",
     * FileFdCUtil.getSizeFile(documento.getByteDocumentoUpload().length));
     * s4containerFileUpload.addOrReplace(dimensioneFile);
     *
     * s4containerFileUpload.addOrReplace(creaBtnCaricaDocumento(documento)) ;
     *
     * AjaxButton eliminaFile = new AjaxButton("eliminaFile") {
     *
     * private static final long serialVersionUID = 6550282645552296193L;
     *
     * @Override protected void onSubmit(AjaxRequestTarget targetElimina) {
     *
     * s4containerFileUpload.setVisible(false);
     * documento.setByteDocumentoUpload(null); documento.setDocumentoUpload(null);
     * documento.setIdPratica(null); documento.setNomeFile(null);
     * targetElimina.add(s4containerFileUpload); } };
     * eliminaFile.setDefaultFormProcessing(false);
     * log.error("aaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddd ");
     * s4containerFileUpload.addOrReplace(eliminaFile);
     * log.error("aaaaaaaaaaaaaaaaaaaaaaaaeeeeeeeeeeeeeeeeeeeee ");
     * target.add(s4formUploadAllegati, feedbackPanel); } }
     *
     * @Override protected void onError(AjaxRequestTarget target) {
     * super.onError(target); target.add(feedbackPanel); } });
     *
     * s4formUploadAllegati.addOrReplace(s4containerFileUpload);
     *
     * s4formUploadAllegati.setMultiPart(true);
     *
     * s4documentoUpload = new FileUploadField("s4documentoUpload");
     * s4documentoUpload.setOutputMarkupId(true);
     * s4documentoUpload.setLabel(Model.of("Upload documenti"));
     * s4documentoUpload.add(new FileUploadValidator());
     *
     * s4formUploadAllegati.addOrReplace(s4documentoUpload);
     *
     * s4containerUploadFile.addOrReplace(s4formUploadAllegati);
     */
    addOrReplace(s4containerUploadFile);

    if (datiRichiestaIstanzaPl.getDocumentiDaInviare() == null
        && datiRichiestaIstanzaPl.getDocumentiDaInviare().isEmpty()) {
      labelNondevonoesserecaicatidocumenti.setVisible(true);
      labelPossibileinviaredocumenti.setVisible(false);
      s4containerUploadFile.setVisible(false);
    } else {
      labelNondevonoesserecaicatidocumenti.setVisible(false);
      labelPossibileinviaredocumenti.setVisible(true);
      s4containerUploadFile.setVisible(true);
    }
  }

  private LaddaAjaxButton creaBtnCaricaDocumento(
      DocumentoCaricatoIstanza documento, String tipoDocumento) {
    LaddaAjaxButton btnCaricaFile =
        new LaddaAjaxButton("btnCaricaFileDaCaricare", Type.Primary) {

          private static final long serialVersionUID = -8196453157788619205L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            try {
              // FRR RIPRISTINA
              log.debug(
                  "Step4IstanzaPlCaricaDocumentiIstanzaForm creaBtnCaricaDocumento -- tipoDocumento: "
                      + tipoDocumento);
              AllegatoIstanza allegatoIstanza = new AllegatoIstanza();
              allegatoIstanza.setTipoAllegato(tipoDocumento);
              FileAllegato fileAllegato = new FileAllegato();
              fileAllegato.setFile(documento.getByteDocumentoUpload());
              fileAllegato.setNomeFile(documento.getNomeFile());
              fileAllegato.setMimeType(
                  documento.getDocumentoUpload() != null
                          && documento.getDocumentoUpload().getFileUpload() != null
                      ? documento.getDocumentoUpload().getFileUpload().getContentType()
                      : "non riconosciuto");

              allegatoIstanza.setFile(fileAllegato);
              log.debug(
                  "Step4IstanzaPlCaricaDocumentiIstanzaForm creaBtnCaricaDocumento ------------");
              EsitoOperazione esito =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .inserisciAllegatoAdIstanza(datiRichiestaIstanzaPl, allegatoIstanza);

              log.debug(
                  "Upload esito: "
                      + esito.getEsito()
                      + " "
                      + esito.getDescrizione()
                      + " ID:"
                      + esito.getIdentificativo());
              if (LabelFdCUtil.checkIfNotNull(esito) && !esito.getEsito().equalsIgnoreCase("KO")) {

                // vaiCambioORichiesta();
                //

                info("Documento caricato");

                datiRichiestaIstanzaPl.setTuttiDocumentiObbligatoriCaricati(
                    isObbligatoriCaricatiTutti(getDocumentiCaricati()));

                target.add(containerDocumentiCaricati);
                log.debug("creaBtnCaricaDocumento onSubmit ok");
                setResponsePage(new GestisciIstanzaPLPage(4, datiRichiestaIstanzaPl));
              } else {
                error("Errore durante inserimento dell'allegato");

                target.add(feedbackPanel);
                log.error("creaBtnCaricaDocumento onSubmit Errore ");
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("errore post inserimento allegato ", e);
              error("Errore post allegato");
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("errore step 4 carica documento");
          }
        };
    btnCaricaFile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaPanel.File.carica",
                    Step4IstanzaPlCaricaDocumentiIstanzaForm.this)));

    IconType iconType =
        new IconType("btnCaricaFile") {

          private static final long serialVersionUID = 3264452172039592276L;

          @Override
          public String cssClassName() {
            return "icon-allegati";
          }
        };

    btnCaricaFile.setVisible(documento != null && documento.getNomeFile() != null);
    btnCaricaFile.setIconType(iconType);
    btnCaricaFile.setOutputMarkupId(true);

    return btnCaricaFile;
  }

  /*
   * public FileUploadField getS4DocumentoUpload() { return s4documentoUpload; }
   *
   * public void setS4DocumentoUpload(FileUploadField s4documentoUpload) {
   * this.s4documentoUpload = s4documentoUpload; }
   */
  private List<DatiDocumento> getDocumentiByCodiceHermes(String codiceHermes) {
    List<DatiDocumento> listaDatiDocumento = new ArrayList<DatiDocumento>();
    try {
      log.debug("DatiDocumento: getDocumentiByCodiceHermes: " + codiceHermes);

      /*
       * if ("00".equalsIgnoreCase(codiceHermes)) { listaDatiDocumento =
       * ServiceLocator.getInstance() .getServiziSupportoIstanzeVerbaliPL()
       * .getElencoDocumenti("03"); return listaDatiDocumento; }
       */
      if (datiRichiestaIstanzaPl.getIsIntegrazione()) {
        DatiDocumento datiDocumento = new DatiDocumento();
        datiDocumento.setCodiceInterno(codiceHermes);
        datiDocumento.setCodice("99");
        datiDocumento.setDocumento("Documento richiesto per integrazione");
        datiDocumento.setMinDoc("1");
        datiDocumento.setMaxDoc("1");

        datiDocumento.setObbligatorio("SI");
        listaDatiDocumento.add(datiDocumento);
      } else {
        listaDatiDocumento =
            ServiceLocator.getInstance()
                .getServiziSupportoIstanzeVerbaliPL()
                .getElencoDocumenti(codiceHermes);
        log.debug(
            "DatiDocumento: getDocumentiByCodiceHermes:  listaDatiDocumento " + listaDatiDocumento);
        // da modificare in base al codice scelto e in base alle scelte
        // FURTO
        if ("03".equalsIgnoreCase(codiceHermes)) {
          // deve tornare copia denuncia di furto

          // CODICE 4 se il veicolo e' stato ritrovato anche copia del
          // verbale di
          // rinvenimento e riconsegna del veicolo
          if (datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento() != null
              && !datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento()) {
            // rimuovi dalla lista il codice 4
            // listaDatiDocumento.
            listaDatiDocumento =
                listaDatiDocumento.stream()
                    .filter(e -> !("4".equalsIgnoreCase(e.getCodiceInterno())))
                    .collect(Collectors.toList());

            log.debug(
                "DatiDocumento: getDocumentiByCodiceHermes:  RITROVAMENTO a FALSE listaDatiDocumento "
                    + listaDatiDocumento);
          }
        } else if ("13".equalsIgnoreCase(codiceHermes)) {
          // non proprietario 3 documenti - proprietario 2 documenti
          log.debug(
              "codiceHermes: 13: "
                  + datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare());
          // CODICE 11 dichiarazione sottoscritta da parte del
          // disabile (Allegato 2)
          if (datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare() != null
              && datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare()) {
            log.debug(
                "codiceHermes: 13: "
                    + datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare());
            // rimuovi dalla lista il codice 11
            listaDatiDocumento =
                listaDatiDocumento.stream()
                    .filter(e -> !("11".equalsIgnoreCase(e.getCodiceInterno())))
                    .collect(Collectors.toList());
            listaDatiDocumento =
                listaDatiDocumento.stream()
                    .filter(e -> !("12".equalsIgnoreCase(e.getCodiceInterno())))
                    .collect(Collectors.toList());

            log.debug("codiceHermes: 13: " + listaDatiDocumento.size());
          }
        }

        DatiDocumento datiDocumento = new DatiDocumento();
        datiDocumento.setCodiceInterno(codiceHermes);
        datiDocumento.setCodice("99");
        datiDocumento.setDocumento("Eventuale file aggiuntivo");
        datiDocumento.setMinDoc("1");
        datiDocumento.setMaxDoc("1");

        datiDocumento.setObbligatorio("NO");
        listaDatiDocumento.add(datiDocumento);
      }
      // se integrazione olo un documento generico
      listaDatiDocumento =
          listaDatiDocumento.stream()
              .filter(e -> e.getDocumento() != null && !e.getDocumento().isEmpty())
              .collect(Collectors.toList());
      log.info("DatiDocumento: " + listaDatiDocumento.size());
    } catch (Exception e) {
      log.error("DatiDocumento ERRORE ", e);
    }
    log.debug("DatiDocumento: RITORNA LISTA CON SIZE " + listaDatiDocumento.size());
    return listaDatiDocumento;
  }

  public List<DatiDocumento> getListaDocumentiCaricati(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    try {
      // boolean attivo = true;
      log.debug("Step4IstanzaPlCaricaDocumentiIstanzaForm getListaDocumentiCaricati ------------");

      List<DatiDocumento> listaDocumentiCaricati = new ArrayList<DatiDocumento>();
      // List<DatiDocumento> listaDocumentiCaricatiOrdinatiPerData = new
      // ArrayList<DatiDocumento>();
      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)
          && LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getIstanza())) {
        AllegatiCollection allegatiCollection =
            ServiceLocator.getInstance()
                .getServiziMieiVerbali()
                .getAllegatiIstanza(datiRichiestaIstanzaPl);
        log.debug(
            "Step4IstanzaPlCaricaDocumentiIstanzaForm getListaDocumentiCaricati ------------");

        // GetDocumentiPraticaResponseGenericResponse risposta =
        // ServiceLocator.getInstance()
        // .getServiziAnagrafici()
        // .getDocumentiPratica(idPratica, attivo);
        // if (LabelFdCUtil.checkIfNotNull(risposta) &&
        // LabelFdCUtil.checkIfNotNull(risposta.getResult())) {
        // listaDocumentiCaricati = risposta.getResult().getDocumenti();
        //
        // listaDocumentiCaricatiOrdinatiPerData =
        // listaDocumentiCaricati.stream()
        // .sorted(Comparator.comparing(DocumentoPratica::getIdDocumento).reversed())
        // .collect(Collectors.toList());
        // }
      }
      return listaDocumentiCaricati;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante get dichiarazione precompilata:", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @SuppressWarnings("unused")
  private String getStringaMotivoScelto(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    String key = "IstanzePlCodiceHermes." + datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    return getString(key);
  }
  /*
   * private void vaiCambioORichiesta() {
   * if(LabelFdCUtil.checkIfNotNull(variazioniResidenza)&&
   * LabelFdCUtil.checkIfNotNull(variazioniResidenza.
   * getTipoVariazioneDiResidenza())) {
   * if(variazioniResidenza.getTipoVariazioneDiResidenza().getId() == 1) {
   * setResponsePage(new CambioIndirizzoPage(4, "Cambio indirizzo",
   * variazioniResidenza)); }else { setResponsePage(new RichiestaResidenzaPage(4,
   * "Richiesta residenza", variazioniResidenza)); } }
   *
   * if (LabelFdCUtil.checkIfNotNull(variazioniResidenza) &&
   * LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali()) &&
   * LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali().
   * getIdTipo())) { if
   * (variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("3") ||
   * variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("4")) {
   * setResponsePage(new CambioIndirizzoPage(4, "Cambio indirizzo",
   * variazioniResidenza)); } else if
   * (variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("1")) {
   * setResponsePage(new RichiestaResidenzaPage(4, "Richiesta residenza",
   * variazioniResidenza)); } } }
   */
}
