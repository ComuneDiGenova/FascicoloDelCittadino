package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import it.liguriadigitale.ponmetro.borsestudio.model.FileAllegato;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.AnnoScolasticoBorseStudioDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.CategoriaBorseStudioDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.ParentelaBorseStudioDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.ScuolaBorseStudioDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete.ComuneBorseStudioAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete.ProvinciaBorseStudioAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCIbanField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberDoubleField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadOnlyPDFValidator;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class DatiDomandaBorsaStudioPanel extends BasePanel {

  private static final long serialVersionUID = -7546299459199368060L;

  private int index;

  private FileUploadField allegatoBorseUpload;

  private FileUploadField allegatoBorseScontriniUpload;

  private String nomeAllegatoUpload;

  private String nomeAllegatoUploadScontrini;

  private byte[] byteAllegatoUpload;

  private byte[] byteAllegatoUploadScontrini;

  private Form<?> formUploadAllegati;

  private Form<?> formUploadAllegatoScontrini;

  private WebMarkupContainer containerAllegatiUpload;

  private WebMarkupContainer containerAllegatiScontriniUpload;

  private WebMarkupContainer containerDatiDomicilio;

  private WebMarkupContainer containerInGenova;

  private WebMarkupContainer containerFuoriGenova;

  private WebMarkupContainer lblFileAllegatoVittimaUpload;

  private WebMarkupContainer lblFileAllegatoScontriniUpload;

  private AjaxButton eliminaAllegato;

  private AjaxButton eliminaAllegatoScontrini;

  private WebMarkupContainer wmkComune;

  private ScuolaBorseStudioDropDownChoice scuola;

  private PraticaBorseStudioExtend datiDomanda;

  private NotificationPanel feedbackPanel;

  private ViarioGeoserverAutoComplete autoCompleteViario;

  private ProvinciaBorseStudioAutocomplete autocompleteProvincia;

  private ComuneBorseStudioAutocomplete autocompleteComune;

  public DatiDomandaBorsaStudioPanel(
      String id, PraticaBorseStudioExtend datiDomanda, int index, NotificationPanel feedbackPanel) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    this.feedbackPanel = feedbackPanel;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    datiDomanda = (PraticaBorseStudioExtend) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiDomandaBorsaStudioPanel.titolo")));

    NumberTextField classeNumero =
        new NumberTextField<Integer>(
            "classeNumero", new PropertyModel<Integer>(datiDomanda, "classeNumero"));
    classeNumero.setMinimum(0);
    classeNumero.setRequired(true);
    classeNumero.setLabel(Model.of("Classe"));
    addOrReplace(classeNumero);

    Label classeNumeroLabel = new NotEmptyLabel("classeNumeroLabel", "Classe");
    classeNumeroLabel.setOutputMarkupId(true);
    classeNumeroLabel.setOutputMarkupPlaceholderTag(true);
    classeNumeroLabel.add(new AttributeModifier("for", classeNumero.getMarkupId()));
    classeNumeroLabel.add(new AttributeModifier("class", "active"));
    addOrReplace(classeNumeroLabel);

    FdCTextField sezione =
        new FdCTextField(
            "sezione", new PropertyModel(datiDomanda, "sezione"), DatiDomandaBorsaStudioPanel.this);
    sezione.setRequired(true);
    sezione.setOutputMarkupId(true);
    sezione.setOutputMarkupPlaceholderTag(true);
    addOrReplace(sezione);

    scuola =
        new ScuolaBorseStudioDropDownChoice(
            "scuola", new PropertyModel(datiDomanda, "scuola"), datiDomanda.getCategoria());
    scuola.setRequired(true);
    scuola.setLabel(Model.of("Scuola"));
    addOrReplace(scuola);

    WebMarkupContainer lblScuola = new WebMarkupContainer("lblScuola");
    lblScuola.add(new AttributeModifier("for", scuola.getMarkupId()));
    addOrReplace(lblScuola);

    CategoriaBorseStudioDropDownChoice categoria =
        new CategoriaBorseStudioDropDownChoice("categoria", Model.of(new Categoria()));
    categoria.setRequired(true);
    categoria.setLabel(Model.of("Categoria"));

    categoria.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -7886220772015519384L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            scuola.getScuole(categoria.getValue());

            datiDomanda.setCategoria(categoria.getModelObject());

            target.add(scuola);
          }
        });

    addOrReplace(categoria);

    WebMarkupContainer lblCategoria = new WebMarkupContainer("lblCategoria");
    lblCategoria.add(new AttributeModifier("for", categoria.getMarkupId()));
    addOrReplace(lblCategoria);

    AnnoScolasticoBorseStudioDropDownChoice annoScolastico =
        new AnnoScolasticoBorseStudioDropDownChoice(
            "annoScolastico", Model.of(new AnnoScolastico()));
    annoScolastico.setRequired(true);
    annoScolastico.setLabel(Model.of("Anno scolastico"));

    annoScolastico.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -3591162607395997537L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            datiDomanda.setAnnoScolastico(annoScolastico.getModelObject());
          }
        });

    addOrReplace(annoScolastico);

    WebMarkupContainer lblAnnoScolastico = new WebMarkupContainer("lblAnnoScolastico");
    lblAnnoScolastico.add(new AttributeModifier("for", annoScolastico.getMarkupId()));
    addOrReplace(lblAnnoScolastico);

    ParentelaBorseStudioDropDownChoice parentela =
        new ParentelaBorseStudioDropDownChoice(
            "parentela", new PropertyModel(datiDomanda, "parentela"));
    parentela.setRequired(true);
    parentela.setLabel(Model.of("Rapporto Parentela con lo studente"));
    addOrReplace(parentela);

    WebMarkupContainer lblParentela = new WebMarkupContainer("lblParentela");
    lblParentela.add(new AttributeModifier("for", parentela.getMarkupId()));
    addOrReplace(lblParentela);

    FdCNumberDoubleField<Component> importoEuro =
        new FdCNumberDoubleField(
            "importoEuro",
            new PropertyModel(datiDomanda, "importoEuro"),
            DatiDomandaBorsaStudioPanel.this,
            0);
    importoEuro.getDoubleField().setMinimum(0.0);
    importoEuro.add(new AttributeModifier("step", "any"));
    importoEuro.getDoubleField().setRequired(true);
    addOrReplace(importoEuro);

    NotEmptyLabel infoIban =
        new NotEmptyLabel(
            "infoIban",
            new StringResourceModel("DatiDomandaBorsaStudioPanel.infoIban", this)
                .setParameters(
                    datiDomanda.getNomeRichiedente(), datiDomanda.getCognomeRichiedente()));
    addOrReplace(infoIban);

    FdCIbanField iban =
        new FdCIbanField(
            "iban", new PropertyModel(datiDomanda, "iban"), DatiDomandaBorsaStudioPanel.this);
    iban.setRequired(true);
    addOrReplace(iban);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloIndirizzo", getString("DatiDomandaBorsaStudioPanel.titoloIndirizzo")));

    containerDatiDomicilio = new WebMarkupContainer("containerDatiDomicilio");

    containerInGenova = new WebMarkupContainer("containerInGenova");

    containerFuoriGenova = new WebMarkupContainer("containerFuoriGenova");

    FdCTextField indirizzoResidenza =
        new FdCTextField(
            "indirizzoResidenza",
            new PropertyModel(datiDomanda, "indirizzoResidenza"),
            DatiDomandaBorsaStudioPanel.this);
    indirizzoResidenza.setEnabled(false);
    addOrReplace(indirizzoResidenza);

    AbstractAutoCompleteTextRenderer<FeaturesGeoserver> autocompleteViarioRender =
        new AbstractAutoCompleteTextRenderer<FeaturesGeoserver>() {

          @Override
          protected String getTextValue(FeaturesGeoserver indirizzo) {
            return indirizzo.getMACHINE_LAST_UPD();
          }
        };

    AutoCompleteSettings settings = new AutoCompleteSettings();
    settings.setShowListOnEmptyInput(false);
    settings.setUseSmartPositioning(true);
    settings.setShowListOnFocusGain(true);
    settings.setMinInputLength(1);
    settings.setThrottleDelay(1000);

    autoCompleteViario =
        new ViarioGeoserverAutoComplete(
            "autocompleteViario",
            new PropertyModel<FeaturesGeoserver>(datiDomanda, "autocompleteViario"),
            autocompleteViarioRender,
            settings) {};
    autoCompleteViario.setLabel(Model.of("Via domicilio"));

    containerInGenova.addOrReplace(autoCompleteViario);

    FdCTextField internoDomicilioGenova =
        new FdCTextField(
            "internoDomicilioGenova",
            new PropertyModel(datiDomanda, "internoDomicilioGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerInGenova.addOrReplace(internoDomicilioGenova);

    FdCTextField capDomicilioGenova =
        new FdCTextField(
            "capDomicilioGenova",
            new PropertyModel(datiDomanda, "capDomicilioGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerInGenova.addOrReplace(capDomicilioGenova);

    FdCTextField viaDomicilioNoGenova =
        new FdCTextField(
            "viaDomicilioNoGenova",
            new PropertyModel(datiDomanda, "viaDomicilioNoGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerFuoriGenova.addOrReplace(viaDomicilioNoGenova);

    FdCTextField civicoDomicilioNoGenova =
        new FdCTextField(
            "civicoDomicilioNoGenova",
            new PropertyModel(datiDomanda, "civicoDomicilioNoGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerFuoriGenova.addOrReplace(civicoDomicilioNoGenova);

    FdCTextField internoDomicilioNoGenova =
        new FdCTextField(
            "internoDomicilioNoGenova",
            new PropertyModel(datiDomanda, "internoDomicilioNoGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerFuoriGenova.addOrReplace(internoDomicilioNoGenova);

    FdCTextField capDomicilioNoGenova =
        new FdCTextField(
            "capDomicilioNoGenova",
            new PropertyModel(datiDomanda, "capDomicilioNoGenova"),
            DatiDomandaBorsaStudioPanel.this);
    containerFuoriGenova.addOrReplace(capDomicilioNoGenova);

    wmkComune = new WebMarkupContainer("wmkComune");
    wmkComune.setVisible(false);
    wmkComune.setOutputMarkupId(true);
    wmkComune.setOutputMarkupPlaceholderTag(true);

    containerDatiDomicilio.addOrReplace(wmkComune);

    AbstractAutoCompleteTextRenderer<Comune> autocompleteComuneRender =
        new AbstractAutoCompleteTextRenderer<Comune>() {

          @Override
          protected String getTextValue(Comune comune) {
            return comune.getDescrizione();
          }
        };

    String codiceProvincia = "";
    if (LabelFdCUtil.checkIfNotNull(datiDomanda)
        && LabelFdCUtil.checkIfNotNull(datiDomanda.getAutocompleteProvincia())) {
      codiceProvincia = datiDomanda.getAutocompleteProvincia().getCodice();
    }

    autocompleteComune =
        new ComuneBorseStudioAutocomplete(
            "autocompleteComune",
            new PropertyModel<Comune>(datiDomanda, "autocompleteComune"),
            autocompleteComuneRender,
            settings,
            codiceProvincia) {}; // non togliere le graffe!!
    autocompleteComune.setLabel(Model.of("Comune domicilio"));

    autocompleteComune.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(datiDomanda.getAutocompleteComune())) {
              datiDomanda.setComuneDomicilio(datiDomanda.getAutocompleteComune().getCodice());
            }

            log.debug("CP on update comune");

            if (LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getAutocompleteComune())) {
              if (PageUtil.isStringValid(datiDomanda.getAutocompleteComune().getCodice())
                  && datiDomanda.getAutocompleteComune().getCodice().equalsIgnoreCase("025")) {
                log.debug("CP comune genova");

                containerInGenova.setVisible(true);
                containerFuoriGenova.setVisible(false);

                autoCompleteViario.setRequired(true);

                internoDomicilioGenova.setRequired(true);

                capDomicilioGenova.setRequired(true);

                viaDomicilioNoGenova.setRequired(false);
                civicoDomicilioNoGenova.setRequired(false);
                capDomicilioNoGenova.setRequired(false);
                internoDomicilioNoGenova.setRequired(false);

              } else {

                log.debug("CP comune non genova ");

                containerInGenova.setVisible(false);
                containerFuoriGenova.setVisible(true);

                autoCompleteViario.setRequired(false);

                internoDomicilioGenova.setRequired(false);

                capDomicilioGenova.setRequired(false);

                viaDomicilioNoGenova.setRequired(true);
                civicoDomicilioNoGenova.setRequired(true);
                capDomicilioNoGenova.setRequired(true);
                internoDomicilioNoGenova.setRequired(true);
              }
            } else {
              containerInGenova.setVisible(false);
              containerFuoriGenova.setVisible(false);
            }

            target.add(
                containerInGenova,
                containerFuoriGenova,
                autoCompleteViario,
                internoDomicilioGenova,
                capDomicilioGenova,
                viaDomicilioNoGenova,
                civicoDomicilioNoGenova,
                capDomicilioNoGenova,
                internoDomicilioNoGenova);
          }
        });

    wmkComune.addOrReplace(autocompleteComune);

    AbstractAutoCompleteTextRenderer<Provincia> autocompletePronvinciaRender =
        new AbstractAutoCompleteTextRenderer<Provincia>() {

          @Override
          protected String getTextValue(Provincia provincia) {
            return provincia.getDescrizione();
          }
        };

    autocompleteProvincia =
        new ProvinciaBorseStudioAutocomplete(
            "autocompleteProvincia",
            new PropertyModel<Provincia>(datiDomanda, "autocompleteProvincia"),
            autocompletePronvinciaRender,
            settings) {}; // non togliere le graffe!!!
    autocompleteProvincia.setLabel(Model.of("Provincia domicilio"));

    autocompleteProvincia.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP onUpdate provincia " + datiDomanda.getAutocompleteProvincia());

            autocompleteComune.setModelObject(new Comune());

            if (LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getAutocompleteProvincia())) {

              datiDomanda.setProvinciaDomicilio(datiDomanda.getAutocompleteProvincia().getCodice());

              wmkComune.setVisible(true);
              autocompleteComune.setRequired(true);

              autocompleteComune.setCodiceProvincia(
                  datiDomanda.getAutocompleteProvincia().getCodice());

              if (datiDomanda.getAutocompleteProvincia().getCodice().equalsIgnoreCase("010")) {
                autoCompleteViario.setVisible(true);
              } else {
                autoCompleteViario.setVisible(false);
              }
            }

            target.add(wmkComune, autocompleteComune, autoCompleteViario);
          }
        });

    containerDatiDomicilio.addOrReplace(autocompleteProvincia);

    SiNoDropDownChoice datiResidenzaDomicilioCoincidono =
        new SiNoDropDownChoice(
            "datiResidenzaDomicilioCoincidono",
            new PropertyModel<>(datiDomanda, "datiResidenzaDomicilioCoincidono"));
    datiResidenzaDomicilioCoincidono.setRequired(true);
    datiResidenzaDomicilioCoincidono.setLabel(Model.of("Il domicilio coincide con la residenza"));

    datiResidenzaDomicilioCoincidono.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -454719368762510112L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug(
                "CP datiResidenzaDomicilioCoincidono = "
                    + datiResidenzaDomicilioCoincidono.getValue());

            // SI = 0 e NO = 1

            if (LabelFdCUtil.checkIfNotNull(datiResidenzaDomicilioCoincidono)
                && LabelFdCUtil.checkIfNotNull(datiResidenzaDomicilioCoincidono.getValue())) {

              datiDomanda.setProvinciaResidenza("010");
              datiDomanda.setComuneResidenza("025");

              FeaturesGeoserver toponomasticaLoggato = datiDomanda.getToponomasticaUtenteLoggato();

              if (LabelFdCUtil.checkIfNotNull(toponomasticaLoggato)) {
                datiDomanda.setViaResidenza(toponomasticaLoggato.getDESVIA());
                datiDomanda.setCivicoResidenza(toponomasticaLoggato.getTESTO());
                datiDomanda.setCodiceViaResidenza(toponomasticaLoggato.getCOD_STRADA());
              }

              if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
                  && LabelFdCUtil.checkIfNotNull(
                      getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
                datiDomanda.setInternoResidenza(
                    getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumber());
                datiDomanda.setCapResidenza(
                    getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
              }

              if (datiResidenzaDomicilioCoincidono.getValue().equalsIgnoreCase("0")) {
                containerDatiDomicilio.setVisible(false);

                autocompleteProvincia.setModelObject(new Provincia());
                autocompleteComune.setModelObject(new Comune());

                datiDomanda.setProvinciaDomicilio(datiDomanda.getProvinciaResidenza());
                datiDomanda.setComuneDomicilio(datiDomanda.getComuneResidenza());
                datiDomanda.setViaDomicilioGenova(datiDomanda.getViaResidenza());
                datiDomanda.setCivicoDomicilioGenova(datiDomanda.getCivicoResidenza());
                datiDomanda.setInternoDomicilioGenova(datiDomanda.getInternoResidenza());
                datiDomanda.setCapDomicilioGenova(datiDomanda.getCapResidenza());
                datiDomanda.setCodiceViaDomicilioGenova(datiDomanda.getCodiceViaResidenza());

              } else {
                containerDatiDomicilio.setVisible(true);
                containerFuoriGenova.setVisible(false);
                wmkComune.setVisible(false);

                autocompleteProvincia.setModelObject(new Provincia());
                autocompleteComune.setModelObject(new Comune());

                datiDomanda.setProvinciaDomicilio(null);
                datiDomanda.setComuneDomicilio(null);
                datiDomanda.setViaDomicilioGenova(null);
                datiDomanda.setCivicoDomicilioGenova(null);
                datiDomanda.setInternoDomicilioGenova(null);
                datiDomanda.setCapDomicilioGenova(null);
                datiDomanda.setCodiceViaDomicilioGenova(null);
              }
            }

            target.add(containerDatiDomicilio, containerFuoriGenova, wmkComune);
          }
        });

    addOrReplace(datiResidenzaDomicilioCoincidono);

    containerInGenova.setOutputMarkupId(true);
    containerInGenova.setOutputMarkupPlaceholderTag(true);
    containerInGenova.setVisible(false);
    containerDatiDomicilio.addOrReplace(containerInGenova);

    containerFuoriGenova.setOutputMarkupId(true);
    containerFuoriGenova.setOutputMarkupPlaceholderTag(true);
    containerFuoriGenova.setVisible(false);
    containerDatiDomicilio.addOrReplace(containerFuoriGenova);

    containerDatiDomicilio.setOutputMarkupId(true);
    containerDatiDomicilio.setOutputMarkupPlaceholderTag(true);
    containerDatiDomicilio.setVisible(false);
    addOrReplace(containerDatiDomicilio);

    containerAllegatiUpload = new WebMarkupContainer("containerAllegatiUpload");
    containerAllegatiUpload.setVisible(false);
    containerAllegatiUpload.setOutputMarkupId(true);
    containerAllegatiUpload.setOutputMarkupPlaceholderTag(true);

    formUploadAllegati = new Form<>("formUploadAllegati");
    formUploadAllegati.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 3483348422026628529L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = allegatoBorseUpload.getFileUpload();
            if (uploadedFile != null) {

              setAllegatoBorseUpload(allegatoBorseUpload);
              setNomeAllegatoUpload(uploadedFile.getClientFileName());
              setByteAllegatoUpload(uploadedFile.getBytes());

              containerAllegatiUpload.setVisible(true);

              Label nomeFileAllegato = new Label("nomeFileAllegato", getNomeAllegatoUpload());
              containerAllegatiUpload.addOrReplace(nomeFileAllegato);

              Label dimensioneFileAllegato =
                  new Label(
                      "dimensioneFileAllegato", FileFdCUtil.getSizeFile(uploadedFile.getSize()));
              containerAllegatiUpload.addOrReplace(dimensioneFileAllegato);

              if (LabelFdCUtil.checkIfNotNull(getAllegatoBorseUpload())) {
                FileAllegato fileAllegato = new FileAllegato();
                fileAllegato.setFile(getByteAllegatoUpload());

                String mimeType = "";
                try {
                  mimeType = FileFdCUtil.getMimeTypeFileUploadAllegato(getByteAllegatoUpload());
                } catch (BusinessException | MagicMatchNotFoundException e) {
                  log.error("Errore mime type: " + e.getMessage(), e);
                }
                fileAllegato.setMimeType(mimeType);

                String estensioneFile = "";
                try {
                  estensioneFile =
                      FileFdCUtil.getEstensionFileUploadAllegato(getByteAllegatoUpload());
                } catch (BusinessException | MagicMatchNotFoundException e) {
                  log.error("Errore estensioneFile: " + e.getMessage(), e);
                }
                fileAllegato.setEstensioneFile(estensioneFile);

                fileAllegato.setNomeFile(getNomeAllegatoUpload());

                datiDomanda.setFileAllegatoVittimaIn(fileAllegato);
              }

              eliminaAllegato =
                  new AjaxButton("eliminaAllegato") {

                    private static final long serialVersionUID = -6058353779879696431L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerAllegatiUpload.setVisible(false);

                      datiDomanda.setFileAllegatoVittimaIn(null);

                      targetElimina.add(containerAllegatiUpload);
                    }
                    ;
                  };
              eliminaAllegato.setDefaultFormProcessing(false);

              containerAllegatiUpload.addOrReplace(eliminaAllegato);

              target.add(formUploadAllegati);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    lblFileAllegatoVittimaUpload = new WebMarkupContainer("lblFileAllegatoVittimaUpload");
    formUploadAllegati.addOrReplace(lblFileAllegatoVittimaUpload);

    formUploadAllegati.addOrReplace(containerAllegatiUpload);
    formUploadAllegati.addOrReplace(
        allegatoBorseUpload = new FileUploadField("fileAllegatoVittimaUpload"));

    allegatoBorseUpload.setRequired(
        LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getFiglioVittimaLavoro())
            && datiDomanda.getFiglioVittimaLavoro());

    allegatoBorseUpload.setLabel(Model.of("Upload documento"));

    allegatoBorseUpload.add(new FileUploadValidator());

    formUploadAllegati.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getFiglioVittimaLavoro())
            && datiDomanda.getFiglioVittimaLavoro());

    formUploadAllegati.setOutputMarkupId(true);
    formUploadAllegati.setOutputMarkupPlaceholderTag(true);

    addOrReplace(formUploadAllegati);

    containerAllegatiScontriniUpload = new WebMarkupContainer("containerAllegatiScontriniUpload");
    containerAllegatiScontriniUpload.setVisible(false);
    containerAllegatiScontriniUpload.setOutputMarkupId(true);
    containerAllegatiScontriniUpload.setOutputMarkupPlaceholderTag(true);

    formUploadAllegatoScontrini = new Form<>("formUploadAllegatoScontrini");
    formUploadAllegatoScontrini.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 3483348422026628529L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFileScontrini = allegatoBorseScontriniUpload.getFileUpload();
            if (uploadedFileScontrini != null) {

              setAllegatoBorseScontriniUpload(allegatoBorseScontriniUpload);
              setNomeAllegatoUploadScontrini(uploadedFileScontrini.getClientFileName());
              setByteAllegatoUploadScontrini(uploadedFileScontrini.getBytes());

              containerAllegatiScontriniUpload.setVisible(true);

              Label nomeFileAllegatoScontrini =
                  new Label("nomeFileAllegatoScontrini", getNomeAllegatoUploadScontrini());
              containerAllegatiScontriniUpload.addOrReplace(nomeFileAllegatoScontrini);

              Label dimensioneFileAllegatoScontrini =
                  new Label(
                      "dimensioneFileAllegatoScontrini",
                      FileFdCUtil.getSizeFile(uploadedFileScontrini.getSize()));
              containerAllegatiScontriniUpload.addOrReplace(dimensioneFileAllegatoScontrini);

              if (LabelFdCUtil.checkIfNotNull(getAllegatoBorseScontriniUpload())) {
                FileAllegato fileAllegatoScontrini = new FileAllegato();
                fileAllegatoScontrini.setFile(getByteAllegatoUploadScontrini());

                String mimeType = "";
                try {
                  mimeType =
                      FileFdCUtil.getMimeTypeFileUploadAllegato(getByteAllegatoUploadScontrini());
                } catch (BusinessException | MagicMatchNotFoundException e) {
                  log.error("Errore mime type: " + e.getMessage(), e);
                }
                fileAllegatoScontrini.setMimeType(mimeType);

                String estensioneFile = "";
                try {
                  estensioneFile =
                      FileFdCUtil.getEstensionFileUploadAllegato(getByteAllegatoUploadScontrini());
                } catch (BusinessException | MagicMatchNotFoundException e) {
                  log.error("Errore estensioneFile: " + e.getMessage(), e);
                }
                fileAllegatoScontrini.setEstensioneFile(estensioneFile);

                fileAllegatoScontrini.setNomeFile(getNomeAllegatoUploadScontrini());
                datiDomanda.setFileScontriniIn(fileAllegatoScontrini);
              }

              eliminaAllegatoScontrini =
                  new AjaxButton("eliminaAllegatoScontrini") {

                    private static final long serialVersionUID = -2456915420575123225L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      containerAllegatiScontriniUpload.setVisible(false);

                      datiDomanda.setFileScontriniIn(null);

                      targetElimina.add(containerAllegatiScontriniUpload);
                    }
                    ;
                  };
              eliminaAllegatoScontrini.setDefaultFormProcessing(false);

              containerAllegatiScontriniUpload.addOrReplace(eliminaAllegatoScontrini);

              target.add(formUploadAllegatoScontrini);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    lblFileAllegatoScontriniUpload = new WebMarkupContainer("lblFileAllegatoScontriniUpload");
    formUploadAllegatoScontrini.addOrReplace(lblFileAllegatoScontriniUpload);

    formUploadAllegatoScontrini.addOrReplace(containerAllegatiScontriniUpload);
    formUploadAllegatoScontrini.addOrReplace(
        allegatoBorseScontriniUpload = new FileUploadField("fileAllegatoScontriniUpload"));

    allegatoBorseScontriniUpload.setLabel(Model.of("Upload documento con scontrini"));

    allegatoBorseScontriniUpload.add(new FileUploadOnlyPDFValidator());

    formUploadAllegatoScontrini.setOutputMarkupId(true);
    formUploadAllegatoScontrini.setOutputMarkupPlaceholderTag(true);

    addOrReplace(formUploadAllegatoScontrini);
  }

  public String getNomeAllegatoUpload() {
    return nomeAllegatoUpload;
  }

  public void setNomeAllegatoUpload(String nomeAllegatoUpload) {
    this.nomeAllegatoUpload = nomeAllegatoUpload;
  }

  public byte[] getByteAllegatoUpload() {
    return byteAllegatoUpload;
  }

  public void setByteAllegatoUpload(byte[] byteAllegatoUpload) {
    this.byteAllegatoUpload = byteAllegatoUpload;
  }

  public Form<?> getFormUploadAllegati() {
    return formUploadAllegati;
  }

  public void setFormUploadAllegati(Form<?> formUploadAllegati) {
    this.formUploadAllegati = formUploadAllegati;
  }

  public WebMarkupContainer getContainerAllegatiUpload() {
    return containerAllegatiUpload;
  }

  public void setContainerAllegatiUpload(WebMarkupContainer containerAllegatiUpload) {
    this.containerAllegatiUpload = containerAllegatiUpload;
  }

  public FileUploadField getAllegatoBorseUpload() {
    return allegatoBorseUpload;
  }

  public void setAllegatoBorseUpload(FileUploadField allegatoBorseUpload) {
    this.allegatoBorseUpload = allegatoBorseUpload;
  }

  public FileUploadField getAllegatoBorseScontriniUpload() {
    return allegatoBorseScontriniUpload;
  }

  public void setAllegatoBorseScontriniUpload(FileUploadField allegatoBorseScontriniUpload) {
    this.allegatoBorseScontriniUpload = allegatoBorseScontriniUpload;
  }

  public String getNomeAllegatoUploadScontrini() {
    return nomeAllegatoUploadScontrini;
  }

  public void setNomeAllegatoUploadScontrini(String nomeAllegatoUploadScontrini) {
    this.nomeAllegatoUploadScontrini = nomeAllegatoUploadScontrini;
  }

  public byte[] getByteAllegatoUploadScontrini() {
    return byteAllegatoUploadScontrini;
  }

  public void setByteAllegatoUploadScontrini(byte[] byteAllegatoUploadScontrini) {
    this.byteAllegatoUploadScontrini = byteAllegatoUploadScontrini;
  }

  public Form<?> getFormUploadAllegatoScontrini() {
    return formUploadAllegatoScontrini;
  }

  public void setFormUploadAllegatoScontrini(Form<?> formUploadAllegatoScontrini) {
    this.formUploadAllegatoScontrini = formUploadAllegatoScontrini;
  }

  public WebMarkupContainer getContainerAllegatiScontriniUpload() {
    return containerAllegatiScontriniUpload;
  }

  public void setContainerAllegatiScontriniUpload(
      WebMarkupContainer containerAllegatiScontriniUpload) {
    this.containerAllegatiScontriniUpload = containerAllegatiScontriniUpload;
  }

  public WebMarkupContainer getLblFileAllegatoVittimaUpload() {
    return lblFileAllegatoVittimaUpload;
  }

  public void setLblFileAllegatoVittimaUpload(WebMarkupContainer lblFileAllegatoVittimaUpload) {
    this.lblFileAllegatoVittimaUpload = lblFileAllegatoVittimaUpload;
  }

  public AjaxButton getEliminaAllegato() {
    return eliminaAllegato;
  }

  public void setEliminaAllegato(AjaxButton eliminaAllegato) {
    this.eliminaAllegato = eliminaAllegato;
  }

  public WebMarkupContainer getLblFileAllegatoScontriniUpload() {
    return lblFileAllegatoScontriniUpload;
  }

  public void setLblFileAllegatoScontriniUpload(WebMarkupContainer lblFileAllegatoScontriniUpload) {
    this.lblFileAllegatoScontriniUpload = lblFileAllegatoScontriniUpload;
  }

  public AjaxButton getEliminaAllegatoScontrini() {
    return eliminaAllegatoScontrini;
  }

  public void setEliminaAllegatoScontrini(AjaxButton eliminaAllegatoScontrini) {
    this.eliminaAllegatoScontrini = eliminaAllegatoScontrini;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    lblFileAllegatoVittimaUpload.setVisible(isEnabled());

    lblFileAllegatoScontriniUpload.setVisible(isEnabled());

    if (LabelFdCUtil.checkIfNotNull(eliminaAllegato)) {
      eliminaAllegato.setVisible(isEnabled());
    }

    if (LabelFdCUtil.checkIfNotNull(eliminaAllegatoScontrini)) {
      eliminaAllegatoScontrini.setVisible(isEnabled());
    }

    if (LabelFdCUtil.checkIfNotNull(datiDomanda)
        && LabelFdCUtil.checkIfNotNull(datiDomanda.getFiglioVittimaLavoro())
        && datiDomanda.getFiglioVittimaLavoro()) {
      formUploadAllegati.setVisible(true);
    } else {
      formUploadAllegati.setVisible(false);
    }
  }
}
