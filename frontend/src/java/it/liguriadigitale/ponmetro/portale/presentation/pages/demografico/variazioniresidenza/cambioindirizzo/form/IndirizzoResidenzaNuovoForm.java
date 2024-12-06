package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniDiResidenzaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ComuniItalianiAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ComuniTuttiAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.NazionalitaProprietarioDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.TipoOccupazioneDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ToponomasticaApkRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Comune;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceComuneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetToponomasticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Toponomastica;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class IndirizzoResidenzaNuovoForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = -173251537526776758L;

  private Utente utente;

  private ViarioGeoserverAutoComplete autoCompleteViario;

  public IndirizzoResidenzaNuovoForm(String id, VariazioniResidenza model, Utente utente) {
    super(id, model);

    setUtente(utente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void addElementiForm() {

    WebMarkupContainer containerDatiProprietario =
        new WebMarkupContainer("containerDatiProprietario");

    WebMarkupContainer containerDatiCatastali = new WebMarkupContainer("containerDatiCatastali");

    WebMarkupContainer containerDescrizioneTitolo =
        new WebMarkupContainer("containerDescrizioneTitolo");

    WebMarkupContainer containerContratto = new WebMarkupContainer("containerContratto");

    WebMarkupContainer containerScalaInterno = new WebMarkupContainer("containerScalaInterno");
    containerScalaInterno.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject())
            && LabelFdCUtil.checkIfNotNull(getModelObject().getAutocompleteViario()));
    containerScalaInterno.setOutputMarkupId(true);
    containerScalaInterno.setOutputMarkupPlaceholderTag(true);
    containerScalaInterno.setMarkupId("containerScalaInterno");

    addOrReplace(containerScalaInterno);

    AbstractAutoCompleteTextRenderer<FeaturesGeoserver> autocompleteRender =
        new AbstractAutoCompleteTextRenderer<FeaturesGeoserver>() {

          private static final long serialVersionUID = 4984034840369577999L;

          @Override
          protected String getTextValue(FeaturesGeoserver servizio) {
            return servizio.getMACHINE_LAST_UPD();
          }
        };

    AutoCompleteSettings settings = new AutoCompleteSettings();
    settings.setShowListOnEmptyInput(false);
    settings.setUseSmartPositioning(true);
    settings.setShowListOnFocusGain(true);
    settings.setMinInputLength(3);
    settings.setThrottleDelay(1000);

    autoCompleteViario =
        new ViarioGeoserverAutoComplete(
            "autocompleteViario",
            new PropertyModel<FeaturesGeoserver>(getModelObject(), "autocompleteViario"),
            autocompleteRender,
            settings) {

          //			@Override
          //			protected void onComponentTag(ComponentTag tag) {
          //				super.onComponentTag(tag);
          //				// Aggiungi l'attributo onchange all'elemento input dell'autocomplete
          //				tag.put("onchange", "handleAutoCompleteChange()");
          //			}

        };
    autoCompleteViario.setLabel(Model.of("Indirizzo"));
    autoCompleteViario.setRequired(true);

    //		autoCompleteViario
    //				.add(new AttributeAppender("onchange", new Model("this.dispatchEvent(new
    // Event('change'));"), ";"));

    addOrReplace(autoCompleteViario);

    Select selectToponomastica = new Select("selectToponomastica");
    selectToponomastica.setOutputMarkupId(true);
    selectToponomastica.setOutputMarkupPlaceholderTag(true);
    selectToponomastica.setRequired(true);
    selectToponomastica.setLabel(Model.of("Interno ed esponente"));
    selectToponomastica.setMarkupId("selectToponomastica");

    containerScalaInterno.addOrReplace(selectToponomastica);

    if (getModelObject().getMappaToponomastiche() != null) {
      RepeatingView rv = new RepeatingView("repeatingView");
      selectToponomastica.addOrReplace(rv);

      for (String elem : getModelObject().getMappaToponomastiche().keySet()) {

        AbstractItem item = new AbstractItem(rv.newChildId());
        rv.addOrReplace(item);

        WebMarkupContainer optGroup = new WebMarkupContainer("optGroup");
        item.addOrReplace(optGroup);

        String gruppoScala = PageUtil.isStringValid(elem) == true ? "Scala ".concat(elem) : "";

        optGroup.add(new AttributeModifier("label", new Model<String>(gruppoScala)));

        optGroup.add(
            new SelectOptions<Toponomastica>(
                "selectOptions",
                getModelObject().getMappaToponomastiche().get(elem),
                new ToponomasticaApkRender()));
      }
    }

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (getModelObject().getAutocompleteViario() != null) {
              try {

                GetToponomasticaResponseGenericResponse response =
                    ServiceLocator.getInstance()
                        .getServiziAnagrafici()
                        .getToponomasticaConSelect2(getModelObject().getAutocompleteViario());

                if (response != null
                    && response.getResult() != null
                    && response.getResult().getToponomastiche() != null) {

                  List<Toponomastica> listaToponomasticheOrdinatePerInterno =
                      response.getResult().getToponomastiche().stream()
                          .sorted(
                              Comparator.comparing(
                                  Toponomastica::getInterno,
                                  Comparator.nullsLast(Comparator.naturalOrder())))
                          .collect(Collectors.toList());

                  Collections.sort(
                      listaToponomasticheOrdinatePerInterno,
                      new Comparator<Toponomastica>() {
                        @Override
                        public int compare(Toponomastica t1, Toponomastica t2) {
                          int n1 = 0;

                          if (t1.getInterno() != null) {
                            n1 = Integer.parseInt(t1.getInterno());
                          }

                          int n2 = 0;
                          if (t2.getInterno() != null) {
                            n2 = Integer.parseInt(t2.getInterno());
                          }

                          if (n1 >= n2) {
                            return 1;
                          }
                          return -1;
                        }
                      });

                  for (Toponomastica topo : listaToponomasticheOrdinatePerInterno) {
                    if (!PageUtil.isStringValid(topo.getScala())) {
                      topo.setScala("");
                    }
                  }

                  Map<String, List<Toponomastica>> mappaToponomastiche =
                      listaToponomasticheOrdinatePerInterno.stream()
                          .collect(Collectors.groupingBy(Toponomastica::getScala));

                  getModelObject().setMappaToponomastiche(mappaToponomastiche);

                  RepeatingView rv = new RepeatingView("repeatingView");
                  selectToponomastica.addOrReplace(rv);

                  if (mappaToponomastiche != null) {
                    for (String elem : mappaToponomastiche.keySet()) {

                      AbstractItem item = new AbstractItem(rv.newChildId());
                      rv.addOrReplace(item);

                      WebMarkupContainer optGroup = new WebMarkupContainer("optGroup");
                      item.addOrReplace(optGroup);

                      String gruppoScala =
                          PageUtil.isStringValid(elem) == true ? "Scala ".concat(elem) : "";

                      optGroup.add(new AttributeModifier("label", new Model<String>(gruppoScala)));

                      optGroup.add(
                          new SelectOptions<Toponomastica>(
                              "selectOptions",
                              mappaToponomastiche.get(elem),
                              new ToponomasticaApkRender()));
                    }
                  }
                }

                containerScalaInterno.setVisible(true);

                target.add(containerScalaInterno, selectToponomastica);

              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore durante toponomestica APK: " + e.getMessage(), e);
              }
            }
          }
        });

    TextField sezione = new TextField("sezione", new PropertyModel(getModelObject(), "sezione"));
    sezione.setLabel(Model.of("Sezione"));
    sezione.add(StringValidator.maximumLength(9));
    containerDatiCatastali.addOrReplace(sezione);

    TextField foglio = new TextField("foglio", new PropertyModel(getModelObject(), "foglio"));
    foglio.setLabel(Model.of("Foglio"));
    foglio.add(StringValidator.maximumLength(5));
    containerDatiCatastali.addOrReplace(foglio);

    TextField numero = new TextField("numero", new PropertyModel(getModelObject(), "numero"));
    numero.setLabel(Model.of("Numero"));
    numero.add(StringValidator.maximumLength(5));
    containerDatiCatastali.addOrReplace(numero);

    TextField subalterno =
        new TextField("subalterno", new PropertyModel(getModelObject(), "subalterno"));
    subalterno.setLabel(Model.of("Subalterno"));
    subalterno.add(StringValidator.maximumLength(4));
    containerDatiCatastali.addOrReplace(subalterno);

    TextField descrizioneTitolo =
        new TextField(
            "descrizioneTitolo", new PropertyModel(getModelObject(), "descrizioneTitolo"));
    descrizioneTitolo.setLabel(Model.of("Descrizione titolo"));
    descrizioneTitolo.add(StringValidator.maximumLength(255));
    containerDescrizioneTitolo.addOrReplace(descrizioneTitolo);

    TextField numeroContratto =
        new TextField("numeroContratto", new PropertyModel(getModelObject(), "numeroContratto"));
    numeroContratto.setLabel(Model.of("Numero contratto"));
    numeroContratto.add(StringValidator.maximumLength(50));
    containerContratto.addOrReplace(numeroContratto);

    FdCLocalDateTextfield dataContratto =
        new FdCLocalDateTextfield(
            "dataContratto", new PropertyModel(getModelObject(), "dataContratto"));
    dataContratto.setLabel(Model.of("Data contratto"));
    containerContratto.addOrReplace(dataContratto);

    ComuniItalianiAutoComplete comuneAgEntrate =
        new ComuniItalianiAutoComplete(
            "comuneAgEntrate", new PropertyModel(getModelObject(), "comuneAgEntrate"));
    comuneAgEntrate.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -6275493123483875009L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneAgEntrate.setLabel(Model.of("Comune Agenzia Entrate"));
    containerContratto.addOrReplace(comuneAgEntrate);

    TextField nomeProprietario =
        new TextField("nomeProprietario", new PropertyModel(getModelObject(), "nomeProprietario"));
    nomeProprietario.setLabel(Model.of("Nome proprietario"));
    nomeProprietario.setRequired(true);

    containerDatiProprietario.addOrReplace(nomeProprietario);

    TextField cognomeProprietario =
        new TextField(
            "cognomeProprietario", new PropertyModel(getModelObject(), "cognomeProprietario"));
    cognomeProprietario.setLabel(Model.of("Cognome proprietario"));
    cognomeProprietario.setRequired(true);
    containerDatiProprietario.addOrReplace(cognomeProprietario);

    TextField cfProprietario =
        new TextField("cfProprietario", new PropertyModel(getModelObject(), "cfProprietario"));
    cfProprietario.setLabel(Model.of("Codice fiscale proprietario"));
    cfProprietario.add(StringValidator.exactLength(16));
    cfProprietario.add(new CodiceFiscaleValidatorUtil());
    cfProprietario.setOutputMarkupPlaceholderTag(true);
    cfProprietario.setOutputMarkupId(true);
    containerDatiProprietario.addOrReplace(cfProprietario);

    ComuniTuttiAutoComplete comuneNascitaProprietario =
        new ComuniTuttiAutoComplete(
            "comuneNascitaProprietario",
            new PropertyModel(getModelObject(), "comuneNascitaProprietario"));
    comuneNascitaProprietario.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = 3957018748022739193L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneNascitaProprietario.setOutputMarkupPlaceholderTag(true);
    comuneNascitaProprietario.setOutputMarkupId(true);
    comuneNascitaProprietario.setLabel(Model.of("Comune Nascita proprietario"));
    containerDatiProprietario.addOrReplace(comuneNascitaProprietario);

    NazionalitaProprietarioDropDownChoice nazionalita =
        new NazionalitaProprietarioDropDownChoice(
            "nazionalita", new PropertyModel(getModelObject(), "nazionalita"));
    nazionalita.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 658969451782667665L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(nazionalita)
                && LabelFdCUtil.checkIfNotNull(nazionalita.getValue())) {
              if (nazionalita.getValue().equalsIgnoreCase("1")) {
                AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

                cfProprietario.setRequired(true);
                cfProprietario.add(attributeModifierRequired);

              } else {
                cfProprietario.setRequired(false);
                cfProprietario.add(AttributeModifier.remove("required"));
              }
            }

            target.add(cfProprietario, comuneNascitaProprietario);
          }
        });
    nazionalita.setOutputMarkupPlaceholderTag(true);
    nazionalita.setOutputMarkupId(true);
    nazionalita.setRequired(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione())
            && !getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("01"));
    containerDatiProprietario.addOrReplace(nazionalita);

    TextField ragioneSocialeProprietario =
        new TextField(
            "ragioneSocialeProprietario",
            new PropertyModel(getModelObject(), "ragioneSocialeProprietario"));
    ragioneSocialeProprietario.setLabel(Model.of("Ragione sociale proprietario"));
    ragioneSocialeProprietario.add(StringValidator.maximumLength(200));
    containerDatiProprietario.addOrReplace(ragioneSocialeProprietario);

    FdCLocalDateTextfield dataNascitaProprietario =
        new FdCLocalDateTextfield(
            "dataNascitaProprietario",
            new PropertyModel(getModelObject(), "dataNascitaProprietario"));
    dataNascitaProprietario.setLabel(Model.of("Data di nascita proprietario"));
    dataNascitaProprietario.setRequired(true);
    containerDatiProprietario.addOrReplace(dataNascitaProprietario);

    ComuniTuttiAutoComplete comuneResidenzaProprietario =
        new ComuniTuttiAutoComplete(
            "comuneResidenzaProprietario",
            new PropertyModel(getModelObject(), "comuneResidenzaProprietario"));
    comuneResidenzaProprietario.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = 3214821163426901795L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneResidenzaProprietario.setLabel(Model.of("Comune Residenza proprietario"));
    containerDatiProprietario.addOrReplace(comuneResidenzaProprietario);

    TextField indirizzoProprietario =
        new TextField(
            "indirizzoProprietario", new PropertyModel(getModelObject(), "indirizzoProprietario"));
    indirizzoProprietario.setLabel(Model.of("Indirizzo proprietario'"));
    indirizzoProprietario.setRequired(true);
    indirizzoProprietario.add(StringValidator.maximumLength(255));
    containerDatiProprietario.addOrReplace(indirizzoProprietario);

    EmailTextField emailProprietario =
        new EmailTextField(
            "emailProprietario", new PropertyModel<String>(getModelObject(), "emailProprietario"));
    emailProprietario.setLabel(Model.of("Email proprietario"));
    emailProprietario.setRequired(true);
    emailProprietario.add(StringValidator.maximumLength(150));
    containerDatiProprietario.addOrReplace(emailProprietario);

    TextField telefonoProprietario =
        new TextField(
            "telefonoProprietario", new PropertyModel(getModelObject(), "telefonoProprietario"));
    telefonoProprietario.setLabel(Model.of("Telefono proprietario"));
    telefonoProprietario.add(new TelefonoFissoCellulareValidator());
    telefonoProprietario.add(StringValidator.maximumLength(20));
    telefonoProprietario.setRequired(true);
    containerDatiProprietario.addOrReplace(telefonoProprietario);

    containerDatiProprietario.setOutputMarkupPlaceholderTag(true);
    containerDatiProprietario.setOutputMarkupId(true);
    containerDatiProprietario.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione()));
    addOrReplace(containerDatiProprietario);

    TipoOccupazioneDropDownChoise comboTipoOccupazione =
        new TipoOccupazioneDropDownChoise(
            "comboTipoOccupazione", new PropertyModel(getModelObject(), "comboTipoOccupazione"));
    comboTipoOccupazione.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -7435645335906855026L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(comboTipoOccupazione.getValue())) {

              // per proprietario

              if (comboTipoOccupazione.getValue().equalsIgnoreCase("01")) {
                AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

                foglio.add(attributeModifierRequired);
                sezione.add(attributeModifierRequired);
                subalterno.add(attributeModifierRequired);
                numero.add(attributeModifierRequired);

                foglio.setRequired(true);
                sezione.setRequired(true);
                subalterno.setRequired(true);
                numero.setRequired(true);
              } else {
                getModelObject().setFoglio(null);
                getModelObject().setSubalterno(null);
                getModelObject().setSezione(null);
                getModelObject().setNumero(null);

                foglio.add(AttributeModifier.remove("required"));
                subalterno.add(AttributeModifier.remove("required"));
                sezione.add(AttributeModifier.remove("required"));
                numero.add(AttributeModifier.remove("required"));

                foglio.setRequired(false);
                sezione.setRequired(false);
                subalterno.setRequired(false);
                numero.setRequired(false);
              }

              if (LabelFdCUtil.checkIfNotNull(getModelObject().getTipoVariazioneDiResidenza())
                  && !(getModelObject()
                          .getTipoVariazioneDiResidenza()
                          .equals(VariazioniDiResidenzaEnum.RICHIESTA_RESIDENZA)
                      && getUtente().isResidente())) {

                if (comboTipoOccupazione.getValue().equalsIgnoreCase("01")) {
                  getModelObject().setNomeProprietario(getUtente().getNome());
                  getModelObject().setCognomeProprietario(getUtente().getCognome());
                  getModelObject().setCfProprietario(getUtente().getCodiceFiscaleOperatore());
                  getModelObject().setDataNascitaProprietario(getUtente().getDataDiNascita());

                  if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
                      && LabelFdCUtil.checkIfNotNull(
                          getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
                    getModelObject()
                        .setIndirizzoProprietario(
                            getUtente()
                                .getDatiCittadinoResidente()
                                .getCpvHasAddress()
                                .getClvFullAddress());

                    String comuneResidenza =
                        getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity();
                    try {
                      GetCodiceComuneResponseGenericResponse comuniResponse =
                          ServiceLocator.getInstance()
                              .getServiziAnagrafici()
                              .getTuttiComuniApkappa(null, comuneResidenza);

                      if (LabelFdCUtil.checkIfNotNull(comuniResponse)
                          && LabelFdCUtil.checkIfNotNull(comuniResponse.getResult())) {

                        List<Comune> listaComuni = comuniResponse.getResult().getComuni();

                        if (LabelFdCUtil.checkIfNotNull(listaComuni)
                            && !LabelFdCUtil.checkEmptyList(listaComuni)
                            && listaComuni.size() == 1) {

                          if (LabelFdCUtil.checkIfNotNull(listaComuni.get(0))
                              && LabelFdCUtil.checkIfNotNull(listaComuni.get(0).getCodice())) {
                            String codiceComuneResidenza =
                                String.valueOf(listaComuni.get(0).getCodice());
                            getModelObject()
                                .setComuneResidenzaProprietario(
                                    comuneResidenza.concat("-").concat(codiceComuneResidenza));
                          }
                        }
                      }

                    } catch (BusinessException | ApiException | IOException e) {
                      log.error("Errore durante GetComune APK: " + e.getMessage());
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
                      && LabelFdCUtil.checkIfNotNull(
                          getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace())) {

                    if (PageUtil.isStringValid(
                            getUtente()
                                .getDatiCittadinoResidente()
                                .getCpvHasBirthPlace()
                                .getClvCountry())
                        && getUtente()
                            .getDatiCittadinoResidente()
                            .getCpvHasBirthPlace()
                            .getClvCountry()
                            .equalsIgnoreCase("ITALIA")) {

                      String comuneNascita =
                          getUtente()
                              .getDatiCittadinoResidente()
                              .getCpvHasBirthPlace()
                              .getClvCity();
                      String codiceComuneNascita =
                          getUtente()
                              .getDatiCittadinoResidente()
                              .getCpvHasBirthPlace()
                              .getClvHasIdentifier();

                      getModelObject()
                          .setComuneNascitaProprietario(
                              comuneNascita.concat("-").concat(codiceComuneNascita));
                    }

                    getModelObject()
                        .setCountryNascitaProprietario(
                            getUtente()
                                .getDatiCittadinoResidente()
                                .getCpvHasBirthPlace()
                                .getClvCountry());
                  }

                  if (getUtente().isResidente()) {
                    if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
                        && LabelFdCUtil.checkIfNotNull(
                            getUtente().getDatiCittadinoResidente().getCpvHasCitizenship())
                        && LabelFdCUtil.checkIfNotNull(
                            getUtente()
                                .getDatiCittadinoResidente()
                                .getCpvHasCitizenship()
                                .getClvHasIdentifier())) {
                      if (getUtente()
                          .getDatiCittadinoResidente()
                          .getCpvHasCitizenship()
                          .getClvHasIdentifier()
                          .equalsIgnoreCase("100")) {
                        getModelObject().setNazionalita(NazionalitaProprietarioEnum.ITALIANO);
                      } else {
                        getModelObject().setNazionalita(NazionalitaProprietarioEnum.STRANIERO);
                      }
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(getUtente().getMail())) {
                    getModelObject().setEmailProprietario(getUtente().getMail());
                  }

                  if (LabelFdCUtil.checkIfNotNull(getUtente().getMobile())) {
                    getModelObject().setTelefonoProprietario(getUtente().getMobile());
                  }

                  AttributeModifier attributeModifierReadonly =
                      new AttributeModifier("readonly", "");
                  AttributeModifier attributeModifierDisabled =
                      new AttributeModifier("disabled", "");

                  nomeProprietario.add(attributeModifierReadonly);
                  cognomeProprietario.add(attributeModifierReadonly);
                  cfProprietario.add(attributeModifierReadonly);
                  if (getUtente().isResidente()) {
                    comuneNascitaProprietario.add(attributeModifierReadonly);
                    indirizzoProprietario.add(attributeModifierReadonly);
                  }

                  dataNascitaProprietario.add(attributeModifierReadonly);

                  if (LabelFdCUtil.checkIfNotNull(
                      getModelObject().getComuneResidenzaProprietario())) {
                    comuneResidenzaProprietario.add(attributeModifierReadonly);
                  }

                  if (getUtente().isResidente()) {
                    nazionalita.setRequired(false);
                    nazionalita.add(attributeModifierDisabled);
                  } else {
                    nazionalita.setRequired(true);
                  }

                  AttributeModifier attributeModifierRequired =
                      new AttributeModifier("required", "");

                  foglio.add(attributeModifierRequired);
                  sezione.add(attributeModifierRequired);
                  subalterno.add(attributeModifierRequired);
                  numero.add(attributeModifierRequired);

                  foglio.setRequired(true);
                  sezione.setRequired(true);
                  subalterno.setRequired(true);
                  numero.setRequired(true);

                  containerDatiProprietario.setVisible(true);

                } else {
                  getModelObject().setNomeProprietario(null);
                  getModelObject().setCognomeProprietario(null);
                  getModelObject().setRagioneSocialeProprietario(null);
                  getModelObject().setCfProprietario(null);
                  getModelObject().setComuneNascitaProprietario(null);
                  getModelObject().setCountryNascitaProprietario(null);
                  getModelObject().setDataNascitaProprietario(null);
                  getModelObject().setComuneResidenzaProprietario(null);
                  getModelObject().setIndirizzoProprietario(null);
                  getModelObject().setNazionalita(null);
                  getModelObject().setEmailProprietario(null);
                  getModelObject().setTelefonoProprietario(null);

                  nomeProprietario.add(AttributeModifier.remove("readonly"));
                  cognomeProprietario.add(AttributeModifier.remove("readonly"));
                  nazionalita.add(AttributeModifier.remove("disabled"));
                  cfProprietario.add(AttributeModifier.remove("readonly"));
                  comuneNascitaProprietario.add(AttributeModifier.remove("readonly"));
                  dataNascitaProprietario.add(AttributeModifier.remove("readonly"));
                  comuneResidenzaProprietario.add(AttributeModifier.remove("readonly"));
                  indirizzoProprietario.add(AttributeModifier.remove("readonly"));

                  getModelObject().setFoglio(null);
                  getModelObject().setSubalterno(null);
                  getModelObject().setSezione(null);
                  getModelObject().setNumero(null);

                  foglio.add(AttributeModifier.remove("required"));
                  subalterno.add(AttributeModifier.remove("required"));
                  sezione.add(AttributeModifier.remove("required"));
                  numero.add(AttributeModifier.remove("required"));

                  foglio.setRequired(false);
                  sezione.setRequired(false);
                  subalterno.setRequired(false);
                  numero.setRequired(false);

                  containerDatiProprietario.setVisible(true);

                  nazionalita.setRequired(true);
                }
              } else {
                containerDatiProprietario.setVisible(true);
              }

              // per dati catastali

              if (comboTipoOccupazione.getValue().equalsIgnoreCase("01")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("05")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("06")) {
                containerDatiCatastali.setVisible(true);
              } else {
                getModelObject().setSezione(null);
                getModelObject().setFoglio(null);
                getModelObject().setNumero(null);
                getModelObject().setSubalterno(null);

                containerDatiCatastali.setVisible(false);
              }

              // per titolo
              if (comboTipoOccupazione.getValue().equalsIgnoreCase("05")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("06")) {
                containerDescrizioneTitolo.setVisible(true);
              } else {
                getModelObject().setDescrizioneTitolo(null);

                containerDescrizioneTitolo.setVisible(false);
              }

              // per comune ag entrate data e numero
              if (comboTipoOccupazione.getValue().equalsIgnoreCase("02")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("04")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("05")
                  || comboTipoOccupazione.getValue().equalsIgnoreCase("06")) {
                containerContratto.setVisible(true);
              } else {
                getModelObject().setComuneAgEntrate(null);
                getModelObject().setDataContratto(null);
                getModelObject().setNumeroContratto(null);

                containerContratto.setVisible(false);
              }

              target.add(
                  containerDatiProprietario,
                  containerDatiCatastali,
                  containerDescrizioneTitolo,
                  containerContratto);
            }
          }
        });

    comboTipoOccupazione.setOutputMarkupId(true);
    comboTipoOccupazione.setOutputMarkupId(true);

    addOrReplace(comboTipoOccupazione);

    containerDatiCatastali.setOutputMarkupPlaceholderTag(true);
    containerDatiCatastali.setOutputMarkupId(true);
    containerDatiCatastali.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione())
            && (getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("01")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("02")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("05")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("06")));
    addOrReplace(containerDatiCatastali);

    containerDescrizioneTitolo.setOutputMarkupPlaceholderTag(true);
    containerDescrizioneTitolo.setOutputMarkupId(true);
    containerDescrizioneTitolo.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione())
            && (getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("05")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("06")));
    addOrReplace(containerDescrizioneTitolo);

    containerContratto.setOutputMarkupPlaceholderTag(true);
    containerContratto.setOutputMarkupId(true);
    containerContratto.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione())
            && (getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("02")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("04")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("05")
                || getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("06")));
    addOrReplace(containerContratto);

    if (LabelFdCUtil.checkIfNotNull(getModelObject().getComboTipoOccupazione())
        && getModelObject().getComboTipoOccupazione().getCodice().equalsIgnoreCase("01")) {
      AttributeModifier attributeModReadonly = new AttributeModifier("readonly", "");
      AttributeModifier attributeModDisabled = new AttributeModifier("disabled", "");
      AttributeModifier attributeModRequired = new AttributeModifier("required", "");

      nomeProprietario.add(attributeModReadonly);
      cognomeProprietario.add(attributeModReadonly);
      nazionalita.add(attributeModDisabled);
      cfProprietario.add(attributeModReadonly);
      comuneNascitaProprietario.add(attributeModReadonly);
      dataNascitaProprietario.add(attributeModReadonly);
      comuneResidenzaProprietario.add(attributeModReadonly);
      indirizzoProprietario.add(attributeModReadonly);

      foglio.setRequired(true);
      sezione.setRequired(true);
      subalterno.setRequired(true);
      numero.setRequired(true);

      foglio.add(attributeModRequired);
      sezione.add(attributeModRequired);
      subalterno.add(attributeModRequired);
      numero.add(attributeModRequired);
    }
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }
}
