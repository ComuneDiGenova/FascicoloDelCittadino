package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiFiglioExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiGenitoreExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiNascitaResidenzaDomicilio;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente.CittadinanzaIscrizioneMensaAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente.ComuneIscrizioneMensaAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente.ProvinciaIscrizioneMensaAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.dropdown.BollettazioneMensaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import java.time.LocalDate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiAnagraficiIscrizioneMensaNonResidentePanel extends BasePanel {

  private static final long serialVersionUID = 5793669804941866832L;

  private int index;

  private boolean isGenitore;

  private ProvinciaIscrizioneMensaAutocomplete autocompleteProvinciaNascita;

  private ComuneIscrizioneMensaAutocomplete autocompleteComuneNascita;

  private ProvinciaIscrizioneMensaAutocomplete autocompleteProvinciaResidenza;

  private ComuneIscrizioneMensaAutocomplete autocompleteComuneResidenza;

  private ProvinciaIscrizioneMensaAutocomplete autocompleteProvinciaDomicilio;

  private ComuneIscrizioneMensaAutocomplete autocompleteComuneDomicilio;

  private ViarioGeoserverAutoComplete autoCompleteViario;

  private CittadinanzaIscrizioneMensaAutocomplete autocompleteCittadinanze;

  private CittadinanzaIscrizioneMensaAutocomplete autocompleteStatoNascita;

  private WebMarkupContainer containerIndirizzoDomicilioFuoriGenova;

  private WebMarkupContainer containerIndirizzoDomicilioInGenova;

  private WebMarkupContainer containerDatiDomicilio;

  private WebMarkupContainer wmkComuneNascita;

  private WebMarkupContainer wmkComuneResidenza;

  private WebMarkupContainer wmkComuneDomicilio;

  private WebMarkupContainer containerProvinciaComuneItaliani;

  private DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio;

  public DatiAnagraficiIscrizioneMensaNonResidentePanel(
      String id,
      CompoundPropertyModel<DatiIscrizioneMensaNonResidente> datiDomanda,
      int index,
      NotificationPanel feedbackPanel,
      boolean isGenitore) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    this.feedbackPanel = feedbackPanel;

    this.isGenitore = isGenitore;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {

    AutoCompleteSettings settings = new AutoCompleteSettings();
    settings.setShowListOnEmptyInput(false);
    settings.setUseSmartPositioning(true);
    settings.setShowListOnFocusGain(true);
    settings.setMinInputLength(1);
    settings.setThrottleDelay(1000);

    CompoundPropertyModel<DatiIscrizioneMensaNonResidente> datiDomanda =
        (CompoundPropertyModel<DatiIscrizioneMensaNonResidente>) dati;

    DatiGenitoreExtend datiGenitore = datiDomanda.getObject().getDatiGenitore();
    DatiFiglioExtend datiFiglio = datiDomanda.getObject().getDatiBambino();

    DatiGeneraliAnagrafe datiGeneraliAnagrafe = new DatiGeneraliAnagrafe();
    setDatiNascitaResidenzaDomicilio(new DatiNascitaResidenzaDomicilio());

    if (isGenitore) {
      datiGeneraliAnagrafe = datiGenitore.getDatiGeneraliAnagrafe();
      setDatiNascitaResidenzaDomicilio(datiGenitore.getDatiNascitaResidenzaDomicilio());
      datiDomanda
          .getObject()
          .getDatiGenitore()
          .setDatiNascitaResidenzaDomicilio(datiGenitore.getDatiNascitaResidenzaDomicilio());
    } else {
      datiGeneraliAnagrafe = datiFiglio.getDatiGeneraliAnagrafe();
      setDatiNascitaResidenzaDomicilio(datiFiglio.getDatiNascitaResidenzaDomicilio());
      datiDomanda
          .getObject()
          .getDatiBambino()
          .setDatiNascitaResidenzaDomicilio(datiFiglio.getDatiNascitaResidenzaDomicilio());
    }

    wmkComuneNascita = new WebMarkupContainer("wmkComuneNascita");
    wmkComuneNascita.setVisible(
        PageUtil.isStringValid(datiGeneraliAnagrafe.getCodiceComuneNascita()));
    wmkComuneNascita.setOutputMarkupId(true);
    wmkComuneNascita.setOutputMarkupPlaceholderTag(true);

    containerProvinciaComuneItaliani = new WebMarkupContainer("containerProvinciaComuneItaliani");
    boolean containerProvinciaComuneItalianiIsVisible =
        (LabelFdCUtil.checkIfNotNull(datiGenitore)
                && LabelFdCUtil.checkIfNotNull(datiGenitore.getAutocompleteStatoNascita())
                && datiGenitore.getAutocompleteStatoNascita().getCodice().equalsIgnoreCase("100"))
            || (LabelFdCUtil.checkIfNotNull(datiFiglio)
                && LabelFdCUtil.checkIfNotNull(datiFiglio.getAutocompleteCittadinanze())
                && datiFiglio.getAutocompleteCittadinanze().getCodice().equalsIgnoreCase("100"));
    containerProvinciaComuneItaliani.setVisible(containerProvinciaComuneItalianiIsVisible);
    containerProvinciaComuneItaliani.setOutputMarkupId(true);
    containerProvinciaComuneItaliani.setOutputMarkupPlaceholderTag(true);

    wmkComuneResidenza = new WebMarkupContainer("wmkComuneResidenza");
    wmkComuneResidenza.setVisible(
        PageUtil.isStringValid(datiGeneraliAnagrafe.getCodiceComuneResidenza()));
    wmkComuneResidenza.setOutputMarkupId(true);
    wmkComuneResidenza.setOutputMarkupPlaceholderTag(true);

    wmkComuneDomicilio = new WebMarkupContainer("wmkComuneDomicilio");
    wmkComuneDomicilio.setVisible(
        PageUtil.isStringValid(datiGeneraliAnagrafe.getCodiceComuneDomicilio()));
    wmkComuneDomicilio.setOutputMarkupId(true);
    wmkComuneDomicilio.setOutputMarkupPlaceholderTag(true);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiAnagrafici",
            getString("DatiAnagraficiIscrizioneMensaNonResidentePanel.titoloDatiAnagrafici")));

    FdCTextField nome =
        new FdCTextField(
            "nome",
            new PropertyModel(datiGeneraliAnagrafe, "nome"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    nome.setRequired(true);
    nome.setEnabled(false);
    nome.setOutputMarkupId(true);
    nome.setOutputMarkupPlaceholderTag(true);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            new PropertyModel(datiGeneraliAnagrafe, "cognome"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    cognome.setRequired(true);
    cognome.setEnabled(false);
    cognome.setOutputMarkupId(true);
    cognome.setOutputMarkupPlaceholderTag(true);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale",
            new PropertyModel(datiGeneraliAnagrafe, "codiceFiscale"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    codiceFiscale.setRequired(true);
    codiceFiscale.setEnabled(false);
    codiceFiscale.setOutputMarkupId(true);
    codiceFiscale.setOutputMarkupPlaceholderTag(true);
    addOrReplace(codiceFiscale);

    FdCLocalDateField dataNascita =
        new FdCLocalDateField(
            "dataNascita",
            new PropertyModel<LocalDate>(datiGeneraliAnagrafe, "dataNascita"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    dataNascita.setEnabled(false);
    dataNascita.setOutputMarkupId(true);
    dataNascita.setOutputMarkupPlaceholderTag(true);
    addOrReplace(dataNascita);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(datiGenitore, "email"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    email.getTextField().setRequired(true);
    email.setVisible(isGenitore);
    addOrReplace(email);

    FdCPhoneNumberField telefono =
        new FdCPhoneNumberField(
            "telefono",
            new PropertyModel(datiGenitore, "telefono"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    telefono.setRequired(true);
    telefono.setVisible(isGenitore);
    addOrReplace(telefono);

    AbstractAutoCompleteTextRenderer<Cittadinanza> autocompleteCittadinanzaRender =
        new AbstractAutoCompleteTextRenderer<Cittadinanza>() {

          @Override
          protected String getTextValue(Cittadinanza cittadinanza) {
            return cittadinanza.getDescrizione();
          }
        };

    autocompleteCittadinanze =
        new CittadinanzaIscrizioneMensaAutocomplete(
            "autocompleteCittadinanze",
            new PropertyModel<Cittadinanza>(datiFiglio, "autocompleteCittadinanze"),
            autocompleteCittadinanzaRender,
            settings) {};
    autocompleteCittadinanze.setLabel(Model.of("Cittadinanza"));

    autocompleteCittadinanze.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(datiFiglio)
                && LabelFdCUtil.checkIfNotNull(datiFiglio.getAutocompleteCittadinanze())) {

              if (PageUtil.isStringValid(datiFiglio.getAutocompleteCittadinanze().getCodice())
                  && datiFiglio.getAutocompleteCittadinanze().getCodice().equalsIgnoreCase("100")) {
                containerProvinciaComuneItaliani.setVisible(true);
              } else {
                containerProvinciaComuneItaliani.setVisible(false);
              }
            }

            target.add(containerProvinciaComuneItaliani);
          }
        });

    autocompleteCittadinanze.setVisible(!isGenitore);

    addOrReplace(autocompleteCittadinanze);

    autocompleteStatoNascita =
        new CittadinanzaIscrizioneMensaAutocomplete(
            "autocompleteStatoNascita",
            datiDomanda.bind("datiGenitore.autocompleteStatoNascita"),
            autocompleteCittadinanzaRender,
            settings) {};

    autocompleteStatoNascita.setLabel(Model.of("Stato nascita"));

    autocompleteStatoNascita.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(datiGenitore)
                && LabelFdCUtil.checkIfNotNull(datiGenitore.getAutocompleteStatoNascita())) {

              if (PageUtil.isStringValid(datiGenitore.getAutocompleteStatoNascita().getCodice())
                  && datiGenitore
                      .getAutocompleteStatoNascita()
                      .getCodice()
                      .equalsIgnoreCase("100")) {
                containerProvinciaComuneItaliani.setVisible(true);
              } else {
                containerProvinciaComuneItaliani.setVisible(false);
              }
            }

            target.add(containerProvinciaComuneItaliani);
          }
        });

    autocompleteStatoNascita.setVisible(isGenitore);

    addOrReplace(autocompleteStatoNascita);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiNascita",
            getString("DatiAnagraficiIscrizioneMensaNonResidentePanel.titoloDatiNascita")));

    AbstractAutoCompleteTextRenderer<Provincia> autocompletePronvinciaRender =
        new AbstractAutoCompleteTextRenderer<Provincia>() {

          @Override
          protected String getTextValue(Provincia provincia) {
            return provincia.getDescrizione();
          }
        };

    AbstractAutoCompleteTextRenderer<Comune> autocompleteComuneRender =
        new AbstractAutoCompleteTextRenderer<Comune>() {

          @Override
          protected String getTextValue(Comune comune) {
            return comune.getDescrizione();
          }
        };

    String codiceProvinciaNascita = "";
    if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
        && LabelFdCUtil.checkIfNotNull(
            getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaNascita())) {
      codiceProvinciaNascita =
          getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaNascita().getCodice();
    }

    autocompleteComuneNascita =
        new ComuneIscrizioneMensaAutocomplete(
            "autocompleteComuneNascita",
            new PropertyModel<Comune>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteComuneNascita"),
            autocompleteComuneRender,
            settings,
            codiceProvinciaNascita) {};

    autocompleteComuneNascita.setLabel(Model.of("Comune nascita"));

    wmkComuneNascita.addOrReplace(autocompleteComuneNascita);
    containerProvinciaComuneItaliani.addOrReplace(wmkComuneNascita);
    addOrReplace(containerProvinciaComuneItaliani);

    autocompleteProvinciaNascita =
        new ProvinciaIscrizioneMensaAutocomplete(
            "autocompleteProvinciaNascita",
            new PropertyModel<Provincia>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteProvinciaNascita"),
            autocompletePronvinciaRender,
            settings) {};

    autocompleteProvinciaNascita.setLabel(Model.of("Provincia nascita"));

    autocompleteProvinciaNascita.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP onUpdate provincia nascita " + getDatiNascitaResidenzaDomicilio());

            autocompleteComuneNascita.setModelObject(new Comune());

            if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
                && LabelFdCUtil.checkIfNotNull(
                    getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaNascita())) {

              wmkComuneNascita.setVisible(true);

              autocompleteComuneNascita.setCodiceProvincia(
                  getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaNascita().getCodice());
            }

            target.add(wmkComuneNascita, autocompleteComuneNascita);
          }
        });

    containerProvinciaComuneItaliani.addOrReplace(autocompleteProvinciaNascita);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiResidenza",
            getString("DatiAnagraficiIscrizioneMensaNonResidentePanel.titoloDatiResidenza")));

    autocompleteProvinciaResidenza =
        new ProvinciaIscrizioneMensaAutocomplete(
            "autocompleteProvinciaResidenza",
            new PropertyModel<Provincia>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteProvinciaResidenza"),
            autocompletePronvinciaRender,
            settings) {};
    autocompleteProvinciaResidenza.setLabel(Model.of("Provincia residenza"));

    autocompleteProvinciaResidenza.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP onUpdate provincia residenza " + getDatiNascitaResidenzaDomicilio());

            autocompleteComuneResidenza.setModelObject(new Comune());

            if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
                && LabelFdCUtil.checkIfNotNull(
                    getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaResidenza())) {

              wmkComuneResidenza.setVisible(true);

              autocompleteComuneResidenza.setCodiceProvincia(
                  getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaResidenza()
                      .getCodice());
            }

            target.add(wmkComuneResidenza, autocompleteComuneResidenza);
          }
        });

    addOrReplace(autocompleteProvinciaResidenza);

    String codiceProvinciaResidenza = "";
    if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
        && LabelFdCUtil.checkIfNotNull(
            getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaResidenza())) {
      codiceProvinciaResidenza =
          getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaResidenza().getCodice();
    }

    autocompleteComuneResidenza =
        new ComuneIscrizioneMensaAutocomplete(
            "autocompleteComuneResidenza",
            new PropertyModel<Comune>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteComuneResidenza"),
            autocompleteComuneRender,
            settings,
            codiceProvinciaResidenza) {};

    autocompleteComuneResidenza.setLabel(Model.of("Comune residenza"));

    wmkComuneResidenza.addOrReplace(autocompleteComuneResidenza);

    addOrReplace(wmkComuneResidenza);

    FdCTextField viaResidenza =
        new FdCTextField(
            "viaResidenza",
            new PropertyModel(datiGeneraliAnagrafe, "viaResidenza"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    viaResidenza.setRequired(true);
    addOrReplace(viaResidenza);

    FdCTextField civicoResidenza =
        new FdCTextField(
            "civicoResidenza",
            new PropertyModel(datiGeneraliAnagrafe, "civicoResidenza"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    civicoResidenza.setRequired(true);
    addOrReplace(civicoResidenza);

    FdCTextField internoResidenza =
        new FdCTextField(
            "internoResidenza",
            new PropertyModel(datiGeneraliAnagrafe, "internoResidenza"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    internoResidenza.setRequired(true);
    addOrReplace(internoResidenza);

    FdCTextField capResidenza =
        new FdCTextField(
            "capResidenza",
            new PropertyModel(datiGeneraliAnagrafe, "capResidenza"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    capResidenza.setRequired(true);
    addOrReplace(capResidenza);

    containerDatiDomicilio = new WebMarkupContainer("containerDatiDomicilio");
    containerDatiDomicilio.setOutputMarkupId(true);
    containerDatiDomicilio.setOutputMarkupPlaceholderTag(true);
    containerDatiDomicilio.setVisible(false);

    containerDatiDomicilio.addOrReplace(wmkComuneDomicilio);

    SiNoDropDownChoice datiResidenzaDomicilioCoincidonoGenitore =
        new SiNoDropDownChoice(
            "datiResidenzaDomicilioCoincidonoGenitore",
            new PropertyModel<>(datiDomanda, "datiResidenzaDomicilioCoincidonoGenitore"));
    datiResidenzaDomicilioCoincidonoGenitore.setRequired(isGenitore);
    datiResidenzaDomicilioCoincidonoGenitore.setVisible(isGenitore);
    datiResidenzaDomicilioCoincidonoGenitore.setLabel(
        Model.of("Il domicilio coincide con la residenza"));

    datiResidenzaDomicilioCoincidonoGenitore.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1 , se si metto tutto come residenza

            if (LabelFdCUtil.checkIfNotNull(datiResidenzaDomicilioCoincidonoGenitore)
                && LabelFdCUtil.checkIfNotNull(
                    datiResidenzaDomicilioCoincidonoGenitore.getValue())) {
              if (datiResidenzaDomicilioCoincidonoGenitore.getValue().equalsIgnoreCase("0")) {
                containerDatiDomicilio.setVisible(false);

                datiDomanda.getObject().getDatiGenitore().setDatiResidenzaDomicilioCoincidono("Si");

              } else {
                containerDatiDomicilio.setVisible(true);

                if (LabelFdCUtil.checkIfNotNull(datiGenitore.getDatiGeneraliAnagrafe())
                    && PageUtil.isStringValid(
                        datiGenitore.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio())
                    && datiGenitore
                        .getDatiGeneraliAnagrafe()
                        .getCodiceComuneDomicilio()
                        .equalsIgnoreCase("025")) {
                  containerIndirizzoDomicilioFuoriGenova.setVisible(false);
                  containerIndirizzoDomicilioInGenova.setVisible(true);
                } else {
                  containerIndirizzoDomicilioFuoriGenova.setVisible(false);
                  containerIndirizzoDomicilioInGenova.setVisible(false);
                }

                datiDomanda.getObject().getDatiGenitore().setDatiResidenzaDomicilioCoincidono("No");
              }
            }

            target.add(containerDatiDomicilio);
          }
        });

    addOrReplace(datiResidenzaDomicilioCoincidonoGenitore);

    SiNoDropDownChoice datiResidenzaDomicilioCoincidonoFiglio =
        new SiNoDropDownChoice(
            "datiResidenzaDomicilioCoincidonoFiglio",
            new PropertyModel<>(datiDomanda, "datiResidenzaDomicilioCoincidonoFiglio"));
    datiResidenzaDomicilioCoincidonoFiglio.setRequired(!isGenitore);
    datiResidenzaDomicilioCoincidonoFiglio.setVisible(!isGenitore);
    datiResidenzaDomicilioCoincidonoFiglio.setLabel(
        Model.of("Il domicilio coincide con la residenza"));

    datiResidenzaDomicilioCoincidonoFiglio.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1 , se si metto tutto come residenza

            if (LabelFdCUtil.checkIfNotNull(datiResidenzaDomicilioCoincidonoFiglio)
                && LabelFdCUtil.checkIfNotNull(datiResidenzaDomicilioCoincidonoFiglio.getValue())) {
              if (datiResidenzaDomicilioCoincidonoFiglio.getValue().equalsIgnoreCase("0")) {
                containerDatiDomicilio.setVisible(false);

                datiDomanda.getObject().getDatiBambino().setDatiResidenzaDomicilioCoincidono("Si");

              } else {
                containerDatiDomicilio.setVisible(true);

                if (LabelFdCUtil.checkIfNotNull(datiFiglio.getDatiGeneraliAnagrafe())
                    && PageUtil.isStringValid(
                        datiFiglio.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio())
                    && datiFiglio
                        .getDatiGeneraliAnagrafe()
                        .getCodiceComuneDomicilio()
                        .equalsIgnoreCase("025")) {
                  containerIndirizzoDomicilioFuoriGenova.setVisible(false);
                  containerIndirizzoDomicilioInGenova.setVisible(true);
                } else {
                  containerIndirizzoDomicilioFuoriGenova.setVisible(false);
                  containerIndirizzoDomicilioInGenova.setVisible(false);
                }

                datiDomanda.getObject().getDatiBambino().setDatiResidenzaDomicilioCoincidono("No");
              }
            }

            target.add(containerDatiDomicilio);
          }
        });

    addOrReplace(datiResidenzaDomicilioCoincidonoFiglio);

    containerDatiDomicilio.addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiDomicilio",
            getString("DatiAnagraficiIscrizioneMensaNonResidentePanel.titoloDatiDomicilio")));

    autocompleteProvinciaDomicilio =
        new ProvinciaIscrizioneMensaAutocomplete(
            "autocompleteProvinciaDomicilio",
            new PropertyModel<Provincia>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteProvinciaDomicilio"),
            autocompletePronvinciaRender,
            settings) {};
    autocompleteProvinciaDomicilio.setLabel(Model.of("Provincia domicilio"));

    autocompleteProvinciaDomicilio.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP onUpdate provincia domicilio ");

            autocompleteComuneDomicilio.setModelObject(new Comune());

            if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
                && LabelFdCUtil.checkIfNotNull(
                    getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaDomicilio())) {

              wmkComuneDomicilio.setVisible(true);

              autocompleteComuneDomicilio.setCodiceProvincia(
                  getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaDomicilio()
                      .getCodice());
            }

            target.add(wmkComuneDomicilio, autocompleteComuneDomicilio);
          }
        });

    containerDatiDomicilio.addOrReplace(autocompleteProvinciaDomicilio);

    String codiceProvinciaDomicilio = "";
    if (LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
        && LabelFdCUtil.checkIfNotNull(
            getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaDomicilio())) {
      codiceProvinciaDomicilio =
          getDatiNascitaResidenzaDomicilio().getAutocompleteProvinciaDomicilio().getCodice();
    }

    autocompleteComuneDomicilio =
        new ComuneIscrizioneMensaAutocomplete(
            "autocompleteComuneDomicilio",
            new PropertyModel<Comune>(datiNascitaResidenzaDomicilio, "autocompleteComuneDomicilio"),
            autocompleteComuneRender,
            settings,
            codiceProvinciaDomicilio) {};

    autocompleteComuneDomicilio.setLabel(Model.of("Comune domicilio"));

    wmkComuneDomicilio.addOrReplace(autocompleteComuneDomicilio);

    containerIndirizzoDomicilioFuoriGenova =
        new WebMarkupContainer("containerIndirizzoDomicilioFuoriGenova");

    FdCTextField viaDomicilio =
        new FdCTextField(
            "viaDomicilio",
            new PropertyModel(datiGeneraliAnagrafe, "viaDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioFuoriGenova.addOrReplace(viaDomicilio);

    FdCTextField civicoDomicilio =
        new FdCTextField(
            "civicoDomicilio",
            new PropertyModel(datiGeneraliAnagrafe, "civicoDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioFuoriGenova.addOrReplace(civicoDomicilio);

    FdCTextField internoDomicilio =
        new FdCTextField(
            "internoDomicilio",
            new PropertyModel(datiGeneraliAnagrafe, "internoDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioFuoriGenova.addOrReplace(internoDomicilio);

    FdCTextField capDomicilio =
        new FdCTextField(
            "capDomicilio",
            new PropertyModel(datiGeneraliAnagrafe, "capDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioFuoriGenova.addOrReplace(capDomicilio);

    containerIndirizzoDomicilioFuoriGenova.setOutputMarkupId(true);
    containerIndirizzoDomicilioFuoriGenova.setOutputMarkupPlaceholderTag(true);
    containerDatiDomicilio.addOrReplace(containerIndirizzoDomicilioFuoriGenova);

    containerIndirizzoDomicilioInGenova =
        new WebMarkupContainer("containerIndirizzoDomicilioInGenova");
    containerIndirizzoDomicilioInGenova.setOutputMarkupId(true);
    containerIndirizzoDomicilioInGenova.setOutputMarkupPlaceholderTag(true);
    containerIndirizzoDomicilioInGenova.setVisible(
        PageUtil.isStringValid(datiGeneraliAnagrafe.getCodiceComuneDomicilio())
            && datiGeneraliAnagrafe.getCodiceComuneDomicilio().equalsIgnoreCase("025"));

    log.debug(
        "CP containerIndirizzoDomicilioInGenova = "
            + datiGeneraliAnagrafe.getCodiceComuneDomicilio());

    AbstractAutoCompleteTextRenderer<FeaturesGeoserver> autocompleteViarioRender =
        new AbstractAutoCompleteTextRenderer<FeaturesGeoserver>() {

          @Override
          protected String getTextValue(FeaturesGeoserver indirizzo) {
            return indirizzo.getMACHINE_LAST_UPD();
          }
        };

    autoCompleteViario =
        new ViarioGeoserverAutoComplete(
            "autocompleteViario",
            new PropertyModel<FeaturesGeoserver>(
                getDatiNascitaResidenzaDomicilio(), "autocompleteViario"),
            autocompleteViarioRender,
            settings) {};
    autoCompleteViario.setLabel(Model.of("Via domicilio"));
    containerIndirizzoDomicilioInGenova.addOrReplace(autoCompleteViario);

    FdCTextField internoDomicilioGenova =
        new FdCTextField(
            "internoDomicilioGenova",
            new PropertyModel(datiGeneraliAnagrafe, "internoDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioInGenova.addOrReplace(internoDomicilioGenova);

    FdCTextField capDomicilioGenova =
        new FdCTextField(
            "capDomicilioGenova",
            new PropertyModel(datiGeneraliAnagrafe, "capDomicilio"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    containerIndirizzoDomicilioInGenova.addOrReplace(capDomicilioGenova);

    containerDatiDomicilio.addOrReplace(containerIndirizzoDomicilioInGenova);

    addOrReplace(containerDatiDomicilio);

    autocompleteComuneDomicilio.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP on update comune domicilio");

            if (LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(getDatiNascitaResidenzaDomicilio())
                && LabelFdCUtil.checkIfNotNull(
                    getDatiNascitaResidenzaDomicilio().getAutocompleteComuneDomicilio())) {
              if (PageUtil.isStringValid(
                      getDatiNascitaResidenzaDomicilio()
                          .getAutocompleteComuneDomicilio()
                          .getCodice())
                  && getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneDomicilio()
                      .getCodice()
                      .equalsIgnoreCase("025")) {
                log.debug("CP comune genova");

                containerIndirizzoDomicilioInGenova.setVisible(true);
                containerIndirizzoDomicilioFuoriGenova.setVisible(false);

                autoCompleteViario.setRequired(true);

                internoDomicilioGenova.setRequired(true);

                capDomicilioGenova.setRequired(true);

                viaDomicilio.setRequired(false);
                civicoDomicilio.setRequired(false);
                capDomicilio.setRequired(false);
                internoDomicilio.setRequired(false);

              } else {

                log.debug("CP comune non genova ");

                containerIndirizzoDomicilioInGenova.setVisible(false);
                containerIndirizzoDomicilioFuoriGenova.setVisible(true);

                autoCompleteViario.setRequired(false);

                internoDomicilioGenova.setRequired(false);

                capDomicilioGenova.setRequired(false);

                viaDomicilio.setRequired(true);
                civicoDomicilio.setRequired(true);
                capDomicilio.setRequired(true);
                internoDomicilio.setRequired(true);
              }
            } else {
              containerIndirizzoDomicilioInGenova.setVisible(false);
              containerIndirizzoDomicilioFuoriGenova.setVisible(false);
            }

            target.add(
                containerIndirizzoDomicilioInGenova,
                containerIndirizzoDomicilioFuoriGenova,
                autoCompleteViario,
                internoDomicilioGenova,
                capDomicilioGenova,
                viaDomicilio,
                civicoDomicilio,
                capDomicilio,
                internoDomicilio);
          }
        });

    WebMarkupContainer containerDatiBollettazione =
        new WebMarkupContainer("containerDatiBollettazione");
    containerDatiBollettazione.setOutputMarkupId(true);
    containerDatiBollettazione.setOutputMarkupPlaceholderTag(true);

    containerDatiBollettazione.addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiBollettazione",
            getString("DatiAnagraficiIscrizioneMensaNonResidentePanel.titoloDatiBollettazione")));

    FdCEmailTextField emailBollettazione =
        new FdCEmailTextField(
            "emailBollettazione",
            new PropertyModel(datiGenitore, "emailBollettazione"),
            DatiAnagraficiIscrizioneMensaNonResidentePanel.this);
    emailBollettazione.getTextField().setEnabled(false);
    emailBollettazione.setVisible(false);
    emailBollettazione.setOutputMarkupId(true);
    emailBollettazione.setOutputMarkupPlaceholderTag(true);
    containerDatiBollettazione.addOrReplace(emailBollettazione);

    BollettazioneMensaDropDownChoice modalitaBollettazione =
        new BollettazioneMensaDropDownChoice(
            "modalitaBollettazione",
            new PropertyModel<ModalitaBollettazioneEnum>(datiGenitore, "modalitaBollettazione"));
    modalitaBollettazione.setRequired(true);
    modalitaBollettazione.setLabel(Model.of("Modalità bollettazione"));

    modalitaBollettazione.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug(
                "modalitaBollettazione = "
                    + modalitaBollettazione.getValue()
                    + " - "
                    + datiGenitore.getEmail());

            if (LabelFdCUtil.checkIfNotNull(modalitaBollettazione.getValue())) {

              if (modalitaBollettazione.getValue().equalsIgnoreCase("DEMATERIALIZZATA")) {

                datiGenitore.setEmailBollettazione(datiGenitore.getEmail());

                emailBollettazione.getTextField().setEnabled(true);
                emailBollettazione.getTextField().setRequired(true);
                emailBollettazione.setVisible(true);

              } else {

                datiGenitore.setEmailBollettazione(null);

                emailBollettazione.getTextField().setEnabled(false);
                emailBollettazione.getTextField().setRequired(false);
                emailBollettazione.setVisible(false);
              }
            }

            target.add(emailBollettazione);
          }
        });

    containerDatiBollettazione.addOrReplace(modalitaBollettazione);

    containerDatiBollettazione.setVisible(!isGenitore);
    addOrReplace(containerDatiBollettazione);
  }

  public DatiNascitaResidenzaDomicilio getDatiNascitaResidenzaDomicilio() {
    return datiNascitaResidenzaDomicilio;
  }

  public void setDatiNascitaResidenzaDomicilio(
      DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio) {
    this.datiNascitaResidenzaDomicilio = datiNascitaResidenzaDomicilio;
  }
}
