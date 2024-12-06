package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.InQuantoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.portale.pojo.imu.StatoRimborsoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoAllegatoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoEredeEnum;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form.CheckDocumentiException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form.ErroreAllegato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form.SendDocumentException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco.RimborsiImuPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.modal.ModalEliminaFile;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadDieteValidator;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.ProtocollazioneIstanzaRimborso;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class AllegaDocumentiPanel extends BasePanel {

  private static final long serialVersionUID = 1565658798421569L;

  private final int NUMERO_MAX_DOCUMENTO = 4;

  WebMarkupContainer containerCaricaFile;
  WebMarkupContainer containerFile;
  WebMarkupContainer containerAltro;
  WebMarkupContainer containerElencoDocumenti;
  WebMarkupContainer containerInvia;
  WebMarkupContainer containerEsito;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  ModalEliminaFile modalViewPanel;

  LaddaAjaxLink<Object> aggiungi;
  FdcAjaxButton annulla;
  FdcAjaxButton salva;
  RimborsoImu rimborso;
  WebMarkupContainer listaVuota;
  List<RimborsoImuAllegato> allegati;

  Form<?> formFileAllegato;
  FileUploadField fileAllegatoUpload;
  AjaxButton eliminaFile;

  String altro;
  TipoAllegatoEnum tipoDocumento;
  String nomeFileStr;
  byte[] fileBytes;

  PageableListView<RimborsoImuAllegato> table;
  boolean isReadOnly;
  RadioChoice<TipoAllegatoEnum> tipoAllegato;

  private static final String ICON_DOCUMENTO = "color-green col-3 icon-foglio";

  public AllegaDocumentiPanel(String id) {
    super(id);
    createFeedBackPanel();
  }

  public AllegaDocumentiPanel(String id, RimborsoImu rimborso) {
    this(id);
    // TODO Auto-generated constructor stub
    this.rimborso = rimborso;
    isReadOnly = false;
    log.debug("[AllegaDocumentiPage] Rimborso: " + this.rimborso);
    this.fillDati(rimborso);
  }

  public AllegaDocumentiPanel(String id, RimborsoImu rimborso, boolean readOnly) {
    this(id);
    // TODO Auto-generated constructor stub
    this.rimborso = rimborso;
    isReadOnly = readOnly;
    log.debug("[AllegaDocumentiPage] Rimborso: " + this.rimborso);
    this.fillDati(rimborso);
  }

  @Override
  public void fillDati(Object dati) {
    /* TITLE */
    WebMarkupContainer titolo = new WebMarkupContainer("titolo");
    titolo.setVisible(!isReadOnly);
    addOrReplace(titolo);

    WebMarkupContainer titoloReadOnly = new WebMarkupContainer("titoloReadOnly");
    titoloReadOnly.setVisible(isReadOnly);
    addOrReplace(titoloReadOnly);

    /* LINK */
    WebMarkupContainer containerLink = new WebMarkupContainer("containerLink");

    String urlDelegaRiscossione = RichiestaRimborsoImuHelper.getUrl("URL_DELEGA_RISCOSSIONE_IMU");
    String labelDelegaRiscossione =
        getLocalizer().getString("AllegaDocumentiPanel.delegaCoerede", AllegaDocumentiPanel.this);
    containerLink.addOrReplace(
        RichiestaRimborsoImuHelper.createExtenalLink(
            "linkDelegaRiscossione", urlDelegaRiscossione, labelDelegaRiscossione));

    String urlAutocertificazioneEredi =
        RichiestaRimborsoImuHelper.getUrl("URL_AUTOCERTIFICAZIONE_EREDI_IMU");
    String labelAutocertificazioneEredi =
        getLocalizer()
            .getString("AllegaDocumentiPanel.autocertificazioneEredi", AllegaDocumentiPanel.this);
    containerLink.addOrReplace(
        RichiestaRimborsoImuHelper.createExtenalLink(
            "autocertificazionErediLink",
            urlAutocertificazioneEredi,
            labelAutocertificazioneEredi));

    containerLink.setVisible(!isReadOnly);
    addOrReplace(containerLink);

    allegati =
        getAllegati(rimborso.getCodiceFiscalePerAuriga(), rimborso.getUriPratica().longValue());

    containerCaricaFile = new WebMarkupContainer("containerCaricaFile");
    containerCaricaFile.setOutputMarkupId(true);
    containerCaricaFile.setOutputMarkupPlaceholderTag(true);
    containerCaricaFile.setVisible(false);

    containerEsito = new WebMarkupContainer("containerEsito");
    containerEsito.setOutputMarkupId(true);
    containerEsito.setOutputMarkupPlaceholderTag(true);
    containerEsito.setVisible(false);

    tipoAllegato = radioChoiceTipoAllegato();

    containerAltro = new WebMarkupContainer("containerAltro");
    containerAltro.setOutputMarkupId(true);
    containerAltro.setVisible(false);
    containerAltro.addOrReplace(new TextField<String>("altro", new Model<String>(altro)));
    containerFile = new WebMarkupContainer("containerFile");
    containerFile.setOutputMarkupId(true);
    containerFile.setVisible(false);

    Label nomeFile = new Label("nomeFile", "");
    Label dimensioneFile = new Label("dimensioneFile", "");

    eliminaFile =
        new AjaxButton("eliminaFile") {

          private static final long serialVersionUID = 6421871465837323595L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            clearFile();
            containerFile.setVisible(false);
            target.add(containerFile, containerElencoDocumenti);
          }
          ;
        };
    eliminaFile.setDefaultFormProcessing(false);

    containerFile.addOrReplace(eliminaFile);

    containerFile.addOrReplace(nomeFile);
    containerFile.addOrReplace(dimensioneFile);

    containerCaricaFile.addOrReplace(tipoAllegato);
    containerCaricaFile.addOrReplace(containerAltro);

    fileAllegatoUpload = new FileUploadField("fileAllegatoUpload");
    fileAllegatoUpload.setOutputMarkupId(true);
    fileAllegatoUpload.setOutputMarkupPlaceholderTag(true);
    fileAllegatoUpload.setLabel(Model.of("Upload Allegato"));
    fileAllegatoUpload.add(new FileUploadDieteValidator());

    formFileAllegato =
        new Form<Void>("formFileAllegato") {

          private static final long serialVersionUID = 2245687007224827251L;

          @Override
          protected void onSubmit() {
            final FileUpload uploadedFile = fileAllegatoUpload.getFileUpload();
            log.debug("[OnSubmit] Submit della Form");

            if (uploadedFile != null) {
              log.debug("[OnSubmit] File caricato");
            }
          }
        };

    formFileAllegato.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -8546764796525798682L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = fileAllegatoUpload.getFileUpload();
            log.debug("[OnSubmit] File caricato: " + uploadedFile);

            if (uploadedFile != null) {
              log.debug("[OnSubmit] File caricato");

              containerFile.setVisible(true);

              Label nomeFile = new Label("nomeFile", uploadedFile.getClientFileName());
              containerFile.addOrReplace(nomeFile);

              Label dimensioneFile =
                  new Label("dimensioneFile", FileFdCUtil.getSizeFile(uploadedFile.getSize()));
              containerFile.addOrReplace(dimensioneFile);

              nomeFileStr = uploadedFile.getClientFileName();
              fileBytes = uploadedFile.getBytes();
            }

            target.add(formFileAllegato);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            super.onError(target);

            target.add(containerCaricaFile, feedbackPanel);
          }
        });

    formFileAllegato.addOrReplace(containerFile);
    formFileAllegato.addOrReplace(fileAllegatoUpload);
    containerCaricaFile.addOrReplace(formFileAllegato);

    Form<?> insertFile = new Form<Object>("insertFile");
    insertFile.addOrReplace(annulla = creaBottoneAnnullaDocumento());

    insertFile.addOrReplace(salva = creaButtonSalvaDocumento());

    containerCaricaFile.addOrReplace(insertFile);
    addOrReplace(containerCaricaFile);

    aggiungi =
        new LaddaAjaxLink<Object>("btnAggiungi", Type.Primary) {

          private static final long serialVersionUID = 19898871321125L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            log.debug("[OnClick containerFile] Container: ");
            containerCaricaFile.setVisible(true);
            aggiungi.setVisible(false);
            target.add(containerCaricaFile, aggiungi);
          }
        };
    aggiungi.setOutputMarkupId(true);
    aggiungi.setOutputMarkupPlaceholderTag(true);
    aggiungi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllegaDocumentiPanel.aggiungi", AllegaDocumentiPanel.this)));

    aggiungi.setVisible(!isReadOnly);
    addOrReplace(aggiungi);

    listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setOutputMarkupId(true);
    listaVuota.setOutputMarkupPlaceholderTag(true);
    listaVuota.setVisible(allegati.isEmpty());
    addOrReplace(listaVuota);

    table =
        new PageableListView<RimborsoImuAllegato>("listview", allegati, NUMERO_MAX_DOCUMENTO) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<RimborsoImuAllegato> item) {
            // TODO Auto-generated method stub
            log.debug("[Allegato]" + item.getModelObject());
            final RimborsoImuAllegato data = item.getModelObject();
            WebMarkupContainer icona = new WebMarkupContainer("icona");
            AttributeAppender attribute = new AttributeAppender("class", " " + ICON_DOCUMENTO);
            icona.add(attribute);
            item.addOrReplace(icona);

            String tipoAllegato =
                LabelFdCUtil.checkIfNotNull(data.getAllegato())
                    ? data.getAllegato().toString()
                    : "";

            item.addOrReplace(
                new AmtCardLabel<>("tipoDocumento", tipoAllegato, AllegaDocumentiPanel.this));
            item.addOrReplace(
                new AmtCardLabel<>("nomeFile", data.getNomeFile(), AllegaDocumentiPanel.this));

            Form<?> formElimina = new Form<Object>("formElimina");

            modalViewPanel = new ModalEliminaFile("modalViewPanel");
            modalViewPanel.addOrReplace(createButtonSi(modalViewPanel, data));
            modalViewPanel.addOrReplace(createButtonNo(modalViewPanel));
            formElimina.addOrReplace(modalViewPanel);

            AjaxButton eliminaAllegato =
                new AjaxButton("eliminaAllegato") {
                  private static final long serialVersionUID = 1L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget target) {
                    // TODO Auto-generated method stub
                    modalViewPanel.show(target);
                  }
                };
            eliminaAllegato.setDefaultFormProcessing(false);
            eliminaAllegato.setVisible(!isReadOnly);
            formElimina.setVisible(rimborso.getStato().equals(StatoRimborsoEnum.C));
            formElimina.addOrReplace(eliminaAllegato);

            ResourceLink<?> scaricaAllegato = scaricaDocumento(data);
            item.addOrReplace(scaricaAllegato);

            item.addOrReplace(formElimina);
          }
        };

    table.setOutputMarkupId(true);
    table.setDefaultModel(Model.of(allegati));

    containerElencoDocumenti = new WebMarkupContainer("containerElencoDocumenti");
    containerElencoDocumenti.setOutputMarkupId(true);
    containerElencoDocumenti.setOutputMarkupPlaceholderTag(true);

    containerElencoDocumenti.addOrReplace(table);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", table);
    paginazioneFascicolo.setOutputMarkupId(true);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(allegati) && allegati.size() > NUMERO_MAX_DOCUMENTO);
    containerElencoDocumenti.addOrReplace(paginazioneFascicolo);
    containerElencoDocumenti.setVisible(!allegati.isEmpty());

    addOrReplace(containerElencoDocumenti);

    containerInvia = new WebMarkupContainer("containerInvia");
    Form<?> formInvia = new Form<Object>("formInvia");
    FdcAjaxButton invia = creaButtonInviaDocumento();
    formInvia.addOrReplace(invia);
    containerInvia.addOrReplace(formInvia);
    containerInvia.setOutputMarkupId(true);
    containerInvia.setVisible(
        rimborso.getStato().equals(StatoRimborsoEnum.C)
            || rimborso.getStato().equals(StatoRimborsoEnum.S));

    FdCButtonBootstrapAjaxLink<Object> annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {
          private static final long serialVersionUID = 1798754641188L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            setResponsePage(new RimborsiImuPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllegaDocumentiPanel.annulla", AllegaDocumentiPanel.this)));

    formInvia.addOrReplace(annulla);

    addOrReplace(containerInvia);

    addOrReplace(containerEsito);
  }

  private RadioChoice<TipoAllegatoEnum> radioChoiceTipoAllegato() {

    RadioChoice<TipoAllegatoEnum> tipoAllegato =
        new RadioChoice<TipoAllegatoEnum>(
            "tipoAllegato", Model.of(tipoDocumento), Arrays.asList(TipoAllegatoEnum.values()));
    tipoAllegato.setOutputMarkupId(true);
    tipoAllegato.setOutputMarkupPlaceholderTag(true);
    tipoAllegato.setPrefix("<div class=\"form-check\">");
    tipoAllegato.setSuffix("</div>");
    tipoAllegato.setRequired(true);
    tipoAllegato.setLabel(Model.of("Tipo allegato"));
    tipoAllegato.add(
        new AjaxFormChoiceComponentUpdatingBehavior() {
          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            tipoDocumento = tipoAllegato.getModelObject();

            if (tipoDocumento.equals(TipoAllegatoEnum.ALTRI_ALLEGATI)) {
              containerAltro.setVisible(true);
            }

            target.add(containerCaricaFile, containerAltro);
          }
        });

    return tipoAllegato;
  }

  private FdcAjaxButton creaButtonInviaDocumento() {
    FdcAjaxButton invia =
        new FdcAjaxButton("invia") {
          private static final long serialVersionUID = 1798754641188L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            try {
              inviaPraticaRimborso();
            } catch (CheckDocumentiException | BusinessException e) {
              log.debug("[Protocollazione] Errore nella protocollazione: " + e.getMessage());
              error(e.getMessage());
            }
            target.add(
                containerCaricaFile,
                containerInvia,
                containerElencoDocumenti,
                containerEsito,
                feedbackPanel,
                aggiungi,
                listaVuota);
          }

          private void inviaPraticaRimborso() throws CheckDocumentiException, BusinessException {
            // ErroreAllegato check = checkDocumenti();

            if (allegati.isEmpty() && !LabelFdCUtil.checkIfNull(rimborso.getTipoErede())) {
              throw new CheckDocumentiException(
                  getLocalizer()
                      .getString(
                          "AllegaDocumentiPanel.erroreNessunDocumentoCaricato",
                          AllegaDocumentiPanel.this));
            }

            try {

              log.debug(
                  "[ProtocollazioneIMU] Richiesta di protocollazione Rimborso IMU: "
                      + rimborso.getCodiceSoggetto());

              /*String codiceFiscale = LabelFdCUtil.checkIfNull(rimborso.getCodiceFiscaleDefunto())
              || rimborso.getCodiceFiscaleDefunto().isEmpty() ? rimborso.getCodiceFiscale()
              		: rimborso.getCodiceFiscaleDefunto(); */

              ProtocollazioneIstanzaRimborso protocollazione =
                  ServiceLocator.getInstance()
                      .getServiziImuEng()
                      .praticaRimborsoIMUProtocollazione(
                          rimborso.getNome(),
                          rimborso.getCognome(),
                          "1",
                          rimborso.getCodiceFiscalePerAuriga(),
                          rimborso.getCodiceSoggetoPerAuriga().toString(),
                          "Pratica di Rimborso IMU",
                          Long.valueOf(rimborso.getUriPratica()));

              if (LabelFdCUtil.checkIfNotNull(protocollazione)) {
                containerEsito.setVisible(true);
                containerElencoDocumenti.setVisible(false);
                containerInvia.setVisible(false);
                containerCaricaFile.setVisible(false);
                listaVuota.setVisible(false);
                aggiungi.setVisible(false);
              } else {
                log.debug("[ProtocollazioneIMU] Impossibile Protocollare la richiesta di rimborso");
                throw new BusinessException(
                    getLocalizer()
                        .getString(
                            "AllegaDocumentiPanel.protocollazioneKo", AllegaDocumentiPanel.this));
              }
            } catch (BusinessException | ApiException e) {
              // TODO Auto-generated catch block
              log.debug("Impossibile Protocollare " + e);
              throw new BusinessException(
                  getLocalizer()
                      .getString(
                          "AllegaDocumentiPanel.protocollazioneKo", AllegaDocumentiPanel.this));
            }
          }

          private ErroreAllegato checkDocumenti() {
            // TODO Auto-generated method stub
            ErroreAllegato errore = new ErroreAllegato();

            if (LabelFdCUtil.checkIfNull(allegati) || allegati.size() == 0) {
              errore.setErrore(true);
              errore.setRisorsa("AllegaDocumentiPanel.erroreNessunDocumentoCaricato");
              return errore;
            }

            if (rimborso.getInQuanto().equals(InQuantoEnum.EREDE)
                && !allegati.stream()
                    .anyMatch(
                        x -> x.getAllegato().equals(TipoAllegatoEnum.AUTOCERTIFICAZIONE_EREDI))) {
              errore.setErrore(true);
              errore.setRisorsa("AllegaDocumentiPanel.erroreAutocertificazioneErede");
              return errore;
            }

            if (rimborso.getTipoErede().equals(TipoEredeEnum.COEREDE)
                && !allegati.stream()
                    .anyMatch(x -> x.getAllegato().equals(TipoAllegatoEnum.DELEGA_COERDE))) {
              errore.setErrore(true);
              errore.setRisorsa("AllegaDocumentiPanel.erroreAutocertificazioneCoeredi");
              return errore;
            }

            if (LabelFdCUtil.checkIfNotNull(rimborso.getDelegato())
                && !allegati.stream()
                    .anyMatch(x -> x.getAllegato().equals(TipoAllegatoEnum.DELEGA_RITIRO))) {
              errore.setErrore(true);
              errore.setRisorsa("AllegaDocumentiPanel.erroreAutocertificazioneDelegato");
              return errore;
            }

            errore.setErrore(false);
            return errore;
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            target.add(feedbackPanel);
          }
        };
    return invia;
  }

  private FdcAjaxButton creaBottoneAnnullaDocumento() {
    FdcAjaxButton button =
        new FdcAjaxButton("btnAnnulla") {

          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub

            clearFile();

            containerCaricaFile.setVisible(false);
            aggiungi.setVisible(true);
            containerFile.setVisible(false);
            target.add(containerCaricaFile, aggiungi, containerFile);
          }
        };

    button.setOutputMarkupId(true);
    button.setOutputMarkupPlaceholderTag(true);
    button.setDefaultFormProcessing(false);
    button.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllegaDocumentiPanel.annulla", AllegaDocumentiPanel.this)));

    return button;
  }

  private FdcAjaxButton creaButtonSalvaDocumento() {
    FdcAjaxButton button =
        new FdcAjaxButton("btnSalva") {

          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            try {
              salvaDocumento();
            } catch (FileNotFoundException | SendDocumentException e) {
              log.debug("[SubmitInviaDocumento] Errore salvataggio documento: " + e);
              error(e.getMessage());
            }
            target.add(
                containerElencoDocumenti,
                containerCaricaFile,
                paginazioneFascicolo,
                aggiungi,
                listaVuota,
                feedbackPanel,
                tipoAllegato);
          }

          private void salvaDocumento() throws FileNotFoundException, SendDocumentException {

            if (LabelFdCUtil.checkIfNull(nomeFileStr)) {
              throw new FileNotFoundException(
                  getLocalizer()
                      .getString(
                          "AllegaDocumentiPanel.selezionareUnFile", AllegaDocumentiPanel.this));
            }

            if (LabelFdCUtil.checkIfNull(tipoDocumento)) {
              error("Indicare il tipo di documento da allegare");
              return;
            }

            IstanzaRimborso istanzaRimborso = inviaAllegatoToEng(nomeFileStr);

            if (LabelFdCUtil.checkIfNull(istanzaRimborso)) {
              throw new SendDocumentException("Errore salvataggio documento");
            }

            if (LabelFdCUtil.checkIfNotNull(istanzaRimborso.getMessaggioErrore())) {
              throw new SendDocumentException(istanzaRimborso.getMessaggioErrore());
            }

            log.debug("[AllegaDocumento] Documento allegato con successo: " + nomeFileStr);

            RimborsoImuAllegato allegato = new RimborsoImuAllegato();
            allegato.setIdAllegato(istanzaRimborso.getIdAllegato());
            allegato.setAllegato(tipoDocumento);
            allegato.setNomeFile(nomeFileStr);
            allegato.setAltro(altro);
            allegato.setDocumento(fileBytes);
            allegati.add(allegato);

            containerCaricaFile.setVisible(false);
            aggiungi.setVisible(true);

            listaVuota.setVisible(false);
            containerElencoDocumenti.setVisible(true);

            paginazioneFascicolo.setVisible(
                LabelFdCUtil.checkIfNotNull(allegati) && allegati.size() > NUMERO_MAX_DOCUMENTO);

            clearFile();

            containerFile.setVisible(false);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            target.add(containerCaricaFile, feedbackPanel);
          }
        };

    button.setOutputMarkupId(true);
    button.setOutputMarkupPlaceholderTag(true);
    button.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllegaDocumentiPanel.salva", AllegaDocumentiPanel.this)));

    return button;
  }

  private void clearFile() {
    // TODO Auto-generated method stub
    tipoDocumento = null;
    nomeFileStr = null;
    altro = null;
    fileBytes = null;

    tipoAllegato = radioChoiceTipoAllegato();
    containerCaricaFile.addOrReplace(tipoAllegato);
  }

  // scarico allegato
  public ResourceLink scaricaDocumento(RimborsoImuAllegato allegato) {
    final AbstractResource fileResourceByte =
        new AbstractResource() {
          private static final long serialVersionUID = -4905841306372495387L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              byte[] convertByte =
                  (LabelFdCUtil.checkIfNull(allegato.getFileBase64())
                          || allegato.getFileBase64().isEmpty())
                      ? allegato.getDocumento()
                      : Base64.getDecoder().decode(allegato.getFileBase64());

              r.setFileName(allegato.getNomeFile());
              r.setContentType("application/pdf"); // da capire se è un pdf o un immagine
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(convertByte.length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(convertByte);
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf avviso " + e.getMessage());
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("downloadFile", fileResourceByte) {

          private static final long serialVersionUID = -4905841306372495386L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + allegato.getNomeFile());
          }
        };

    return linkFile;
  }

  private IstanzaRimborso inviaAllegatoToEng(String fileName) {

    try {

      log.debug("[inviaAllegatoToEng] Invio il file ad Eng: " + fileName);

      /*String codiceFiscale = LabelFdCUtil.checkIfNull(rimborso.getCodiceFiscaleDefunto())
      || rimborso.getCodiceFiscaleDefunto().isEmpty() ? rimborso.getCodiceFiscale()
      		: rimborso.getCodiceFiscaleDefunto();*/

      return ServiceLocator.getInstance()
          .getServiziImuEng()
          .inserisciAllegato(
              fileName,
              Long.valueOf(rimborso.getUriPratica()),
              rimborso.getCodiceFiscalePerAuriga(),
              tipoDocumento.name(),
              Base64.getEncoder().encodeToString(fileBytes));

    } catch (ApiException | BusinessException e) {
      // TODO Auto-generated catch block
      log.debug("[inviaAllegatoToEng] Impossibile inviare il documento: " + e);

      return null;
    }
  }

  private List<RimborsoImuAllegato> getAllegati(String codiceFiscale, Long praticaRimborsoId) {
    log.debug("[getAllegati] Rimborso: " + this.rimborso);

    try {
      return ServiceLocator.getInstance()
          .getServiziImuEng()
          .getElencoAllegatiPraticaRimborso(codiceFiscale, praticaRimborsoId)
          .stream()
          .filter(x -> LabelFdCUtil.checkIfNotNull(x.getAllegato()))
          .collect(Collectors.toList());

    } catch (BusinessException | ApiException e) {
      // TODO Auto-generated catch block
      log.debug("[GetAllegati] Errore nel recupero degli allegati");
      return new ArrayList<RimborsoImuAllegato>();
    }
  }

  private AjaxButton createButtonNo(ModalEliminaFile modalViewPanel) {
    // TODO Auto-generated method stub
    AjaxButton no =
        new AjaxButton("btnNo") {
          private static final long serialVersionUID = 1323565488721315L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            modalViewPanel.close(target);
          }
        };

    return no;
  }

  private AjaxButton createButtonSi(ModalEliminaFile modalViewPanel, RimborsoImuAllegato item) {
    // TODO Auto-generated method stub
    AjaxButton yes =
        new AjaxButton("btnSi") {
          private static final long serialVersionUID = 146432132165498L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug("[Remove - Immobile] Rimuovo il Immobile dalla Lista");
            try {

              elimina(item);

            } catch (SendDocumentException ex) {
              log.debug("[Eliminare Versamento] Errore: " + ex);
              error(ex.getMessage());
            }

            target.add(containerElencoDocumenti, listaVuota, feedbackPanel);
            modalViewPanel.close(target);
          }

          private void elimina(RimborsoImuAllegato item) throws SendDocumentException {
            IstanzaRimborso istanzaRimborso = eliminaDocumento(item);

            if (LabelFdCUtil.checkIfNotNull(istanzaRimborso.getMessaggioErrore())) {
              throw new SendDocumentException(istanzaRimborso.getMessaggioErrore());
            }

            listaVuota.setVisible(allegati.isEmpty());
            containerElencoDocumenti.setVisible(!allegati.isEmpty());

            log.debug("[Remove - Immobile] Rimuovo il Immobile dalla Lista");
            RimborsoImuAllegato doc =
                allegati.stream().filter(x -> x.getId().equals(item.getId())).findFirst().get();

            allegati.remove(doc);
          }

          private IstanzaRimborso eliminaDocumento(RimborsoImuAllegato item) {
            // TODO Auto-generated method stub
            try {

              return ServiceLocator.getInstance()
                  .getServiziImuEng()
                  .cancellaAllegato(item.getIdAllegato());

            } catch (ApiException | BusinessException e) {
              // TODO Auto-generated catch block
              log.debug("[inviaAllegatoToEng] Impossibile inviare il documento: " + e);
              IstanzaRimborso ir = new IstanzaRimborso();
              ir.setMessaggioErrore("Impossibile eliminare il documento selezionato");
              return ir;
            }
          }
        };

    return yes;
  }
}
