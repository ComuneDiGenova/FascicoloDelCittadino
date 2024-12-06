package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.CittadinanzaAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ComuniItalianiAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ComuniTuttiAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ProfessioneAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ComuneNascitaDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ParenteleDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SessoDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.StatoCivileDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.TitoloStudioDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel.modale.ModaleCancellaIndividuoCaricatoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleAncheStranieroValidatorUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class IndividuiCollegatiResidenzaForm
    extends AbstracFrameworkForm<IndividuiCollegatiImmigrazione> {

  private static final long serialVersionUID = -6977468702977932879L;

  private VariazioniResidenza variazioniResidenza;

  private IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione;

  private Utente utente;

  private String nome;

  private String cognome;

  private String cf;

  private String dataNascita;

  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer containerIndividuiCaricati =
      new WebMarkupContainer("containerIndividuiCaricati");

  private ModaleCancellaIndividuoCaricatoPanel<String> modaleCancellaIndividuoCaricato;

  private WebMarkupContainer containerComune;

  private WebMarkupContainer containerComuneNascitaEstero;

  private WebMarkupContainer containerDatiMatrimonio;

  private WebMarkupContainer containerDatiCartaIdentita;

  private CompoundPropertyModel<IndividuiCollegatiImmigrazione> compoundPropertyModel;

  public IndividuiCollegatiResidenzaForm(
      String id,
      VariazioniResidenza variazioniResidenza,
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione,
      Utente utente,
      FeedbackPanel feedbackPanel) {
    super(id, individuiCollegatiImmigrazione);

    setVariazioniResidenza(variazioniResidenza);
    setIndividuiCollegatiImmigrazione(individuiCollegatiImmigrazione);
    this.setUtente(utente);
    setFeedbackPanel(feedbackPanel);

    addElementiForm(utente, variazioniResidenza);
  }

  public IndividuiCollegatiResidenzaForm(
      String id,
      VariazioniResidenza variazioniResidenza,
      CompoundPropertyModel<IndividuiCollegatiImmigrazione> compoundPropertyModel,
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione,
      Utente utente,
      FeedbackPanel feedbackPanel) {
    super(id, individuiCollegatiImmigrazione);

    setVariazioniResidenza(variazioniResidenza);
    setIndividuiCollegatiImmigrazione(individuiCollegatiImmigrazione);
    setUtente(utente);
    setFeedbackPanel(feedbackPanel);
    setCompoundPropertyModel(compoundPropertyModel);

    addElementiForm(utente, variazioniResidenza);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addElementiForm(Utente utente, VariazioniResidenza variazioniResidenza) {

    containerDatiMatrimonio = new WebMarkupContainer("containerDatiMatrimonio");
    containerDatiMatrimonio.setOutputMarkupId(true);
    containerDatiMatrimonio.setOutputMarkupPlaceholderTag(true);

    containerDatiCartaIdentita = new WebMarkupContainer("containerDatiCartaIdentita");
    containerDatiCartaIdentita.setOutputMarkupId(true);
    containerDatiCartaIdentita.setOutputMarkupPlaceholderTag(true);

    containerComune = new WebMarkupContainer("containerComune");
    containerComune.setOutputMarkupId(true);
    containerComune.setOutputMarkupPlaceholderTag(true);
    containerComune.setVisible(false);

    containerComuneNascitaEstero = new WebMarkupContainer("containerComuneNascitaEstero");
    containerComuneNascitaEstero.setOutputMarkupId(true);
    containerComuneNascitaEstero.setOutputMarkupPlaceholderTag(true);
    containerComuneNascitaEstero.setVisible(false);

    if (!utente.isResidente()) {
      if (LabelFdCUtil.checkIfNotNull(utente.getNome())) {

        if (LabelFdCUtil.checkIfNotNull(getModelObject().getNome())
            && !utente.getNome().equalsIgnoreCase(getModelObject().getNome())
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && !variazioniResidenza.isAggiungi()) {
          getModelObject().setNome(getModelObject().getNome());
        } else {
          if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
              && !variazioniResidenza.isAggiungi()) {
            getModelObject().setNome(utente.getNome());
          } else {
            getModelObject().setNome(null);
          }
        }
      }
    }
    TextField nome = new TextField("nome", new PropertyModel(getModelObject(), "nome"));
    nome.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -3913655601449364325L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setNome(nome.getValue());
            getModelObject().setNome(nome.getValue());
          }
        });
    getModelObject().setNome(nome.getValue());
    nome.setLabel(Model.of("Nome"));
    nome.add(StringValidator.maximumLength(200));
    nome.setRequired(true);
    addOrReplace(nome);

    if (!utente.isResidente()) {
      if (LabelFdCUtil.checkIfNotNull(utente.getCognome())) {
        if (LabelFdCUtil.checkIfNotNull(getModelObject().getCognome())
            && !utente.getCognome().equalsIgnoreCase(getModelObject().getCognome())
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && !variazioniResidenza.isAggiungi()) {
          getModelObject().setCognome(getModelObject().getCognome());
        } else {
          if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
              && !variazioniResidenza.isAggiungi()) {
            getModelObject().setCognome(utente.getCognome());
          } else {
            getModelObject().setCognome(null);
          }
        }
      }
    }
    TextField cognome = new TextField("cognome", new PropertyModel(getModelObject(), "cognome"));
    cognome.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -6153553266236787317L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setCognome(cognome.getValue());
            getModelObject().setCognome(cognome.getValue());
          }
        });
    getModelObject().setCognome(cognome.getValue());
    cognome.setLabel(Model.of("Cognome"));
    cognome.add(StringValidator.maximumLength(60));
    cognome.setRequired(true);
    addOrReplace(cognome);

    if (!utente.isResidente()) {
      if (LabelFdCUtil.checkIfNotNull(utente.getCodiceFiscaleOperatore())) {
        if (LabelFdCUtil.checkIfNotNull(getModelObject().getCf())
            && !utente.getCodiceFiscaleOperatore().equalsIgnoreCase(getModelObject().getCf())
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && variazioniResidenza.isAggiungi()) {
          getModelObject().setCf(getModelObject().getCf());
        } else {
          if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
              && !variazioniResidenza.isAggiungi()) {
            getModelObject().setCf(utente.getCodiceFiscaleOperatore());
          } else {
            getModelObject().setCf(null);
          }
        }
      }
    }
    TextField cf = new TextField("cf", new PropertyModel(getModelObject(), "cf"));
    cf.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -940344361105445986L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setCf(cf.getValue());
            getModelObject().setCf(cf.getValue());
          }
        });
    getModelObject().setCf(cf.getValue());
    cf.setLabel(Model.of("Codice fiscale"));

    // PRIMA DI RICHIESTA DEL 03/02/2022
    // cf.add(StringValidator.exactLength(16));
    // cf.add(new CodiceFiscaleValidatorUtil());
    // DOPO RICHIESTA DEL 03/02/2022
    cf.add(StringValidator.lengthBetween(11, 16));
    cf.add(new CodiceFiscaleAncheStranieroValidatorUtil());

    cf.setRequired(true);
    cf.setOutputMarkupId(true);
    cf.setOutputMarkupPlaceholderTag(true);
    addOrReplace(cf);

    SessoDropDownChoise sesso =
        new SessoDropDownChoise("sesso", new PropertyModel(getModelObject(), "sesso"));
    sesso.setRequired(true);
    addOrReplace(sesso);

    TextField numeroCI = new TextField("numeroCI", new PropertyModel(getModelObject(), "numeroCI"));
    numeroCI.add(StringValidator.maximumLength(15));
    numeroCI.setOutputMarkupId(true);
    numeroCI.setLabel(Model.of("Numero carta d'identità"));
    containerDatiCartaIdentita.addOrReplace(numeroCI);

    FdCLocalDateTextfield dataRilascioCI =
        new FdCLocalDateTextfield(
            "dataRilascioCI", new PropertyModel(getModelObject(), "dataRilascioCI"));
    dataRilascioCI.setLabel(Model.of("Data rilascio"));
    dataRilascioCI.add(RangeValidator.maximum(LocalDate.now()));
    dataRilascioCI.setOutputMarkupId(true);
    containerDatiCartaIdentita.addOrReplace(dataRilascioCI);

    FdCLocalDateTextfield dataScadenzaCI =
        new FdCLocalDateTextfield(
            "dataScadenzaCI", new PropertyModel(getModelObject(), "dataScadenzaCI"));
    dataScadenzaCI.setLabel(Model.of("Data scadenza"));
    dataScadenzaCI.add(RangeValidator.minimum(LocalDate.now()));
    dataScadenzaCI.setOutputMarkupId(true);
    containerDatiCartaIdentita.addOrReplace(dataScadenzaCI);

    ComuniTuttiAutoComplete comuneCi =
        new ComuniTuttiAutoComplete(
            "comuneCi", new PropertyModel<String>(getModelObject(), "comuneCi"));
    comuneCi.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -6406765428083798534L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneCi.setOutputMarkupId(true);
    comuneCi.setLabel(Model.of("Comune carta d'identità"));
    containerDatiCartaIdentita.addOrReplace(comuneCi);

    CheckBox espatrio =
        new CheckBox("espatrio", new PropertyModel<Boolean>(getModelObject(), "espatrio"));
    espatrio.setLabel(Model.of("Espatrio"));
    espatrio.setOutputMarkupId(true);
    containerDatiCartaIdentita.addOrReplace(espatrio);

    addOrReplace(containerDatiCartaIdentita);

    CittadinanzaAutoComplete cittadinanza =
        new CittadinanzaAutoComplete(
            "cittadinanza", new PropertyModel<String>(getModelObject(), "cittadinanza"));
    cittadinanza.add(
        new AjaxFormComponentUpdatingBehavior("blur") {

          private static final long serialVersionUID = -8174465333170521473L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug(
                "CP getModelObject().getDataNascita() = " + getModelObject().getDataNascita());

            if (LabelFdCUtil.checkIfNotNull(cittadinanza.getValue())
                && cittadinanza.getValue().equalsIgnoreCase("ITALIANA-I")
                && LabelFdCUtil.checkIfNotNull(getModelObject().getDataNascita())
                && LocalDateUtil.isMaggiorenne(getModelObject().getDataNascita())) {
              numeroCI.setRequired(true);
              dataRilascioCI.setRequired(true);
              dataScadenzaCI.setRequired(true);
              comuneCi.setRequired(true);
              espatrio.setRequired(true);

              AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

              numeroCI.add(attributeModifierRequired);
              dataRilascioCI.add(attributeModifierRequired);
              dataScadenzaCI.add(attributeModifierRequired);
              comuneCi.add(attributeModifierRequired);
              espatrio.add(attributeModifierRequired);

            } else {

              numeroCI.setRequired(false);
              dataRilascioCI.setRequired(false);
              dataScadenzaCI.setRequired(false);
              comuneCi.setRequired(false);
              espatrio.setRequired(false);

              numeroCI.add(AttributeModifier.remove("required"));
              dataRilascioCI.add(AttributeModifier.remove("required"));
              dataScadenzaCI.add(AttributeModifier.remove("required"));
              comuneCi.add(AttributeModifier.remove("required"));
              espatrio.add(AttributeModifier.remove("required"));
            }

            target.add(containerDatiCartaIdentita);
          }
        });
    cittadinanza.setRequired(true);
    addOrReplace(cittadinanza);

    if (!utente.isResidente()) {
      if (LabelFdCUtil.checkIfNotNull(utente.getDataDiNascita())) {

        if (LabelFdCUtil.checkIfNotNull(getModelObject().getDataNascita())
            && !utente.getDataDiNascita().isEqual(getModelObject().getDataNascita())
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && !variazioniResidenza.isAggiungi()) {
          getModelObject().setDataNascita(getModelObject().getDataNascita());
        } else {
          if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
              && !variazioniResidenza.isAggiungi()) {
            getModelObject().setDataNascita(utente.getDataDiNascita());
          } else {
            getModelObject().setDataNascita(null);
          }
        }
      }
    }
    FdCLocalDateTextfield dataNascita =
        new FdCLocalDateTextfield(
            "dataNascita", new PropertyModel(getModelObject(), "dataNascita"));
    dataNascita.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -6026111366384512639L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            try {
              LocalDate dataNascitaLocalDate =
                  LocalDateUtil.convertiDaFormatoEuropeo(dataNascita.getValue());

              setDataNascita(dataNascita.getValue());
              getModelObject().setDataNascita(dataNascitaLocalDate);

              if (LabelFdCUtil.checkIfNotNull(cittadinanza.getValue())
                  && cittadinanza.getValue().equalsIgnoreCase("ITALIANA-I")
                  && LabelFdCUtil.checkIfNotNull(getModelObject().getDataNascita())
                  && LocalDateUtil.isMaggiorenne(getModelObject().getDataNascita())) {
                numeroCI.setRequired(true);
                dataRilascioCI.setRequired(true);
                dataScadenzaCI.setRequired(true);
                comuneCi.setRequired(true);
                espatrio.setRequired(true);

                AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

                numeroCI.add(attributeModifierRequired);
                dataRilascioCI.add(attributeModifierRequired);
                dataScadenzaCI.add(attributeModifierRequired);
                comuneCi.add(attributeModifierRequired);
                espatrio.add(attributeModifierRequired);

              } else {

                numeroCI.setRequired(false);
                dataRilascioCI.setRequired(false);
                dataScadenzaCI.setRequired(false);
                comuneCi.setRequired(false);
                espatrio.setRequired(false);

                numeroCI.add(AttributeModifier.remove("required"));
                dataRilascioCI.add(AttributeModifier.remove("required"));
                dataScadenzaCI.add(AttributeModifier.remove("required"));
                comuneCi.add(AttributeModifier.remove("required"));
                espatrio.add(AttributeModifier.remove("required"));
              }

              target.add(containerDatiCartaIdentita);

            } catch (BusinessException e) {
              log.error("Errore durante conversione data nascita variazione immigrazione: " + e, e);
            }
          }
        });
    try {
      getModelObject()
          .setDataNascita(LocalDateUtil.convertiDaFormatoEuropeo(dataNascita.getValue()));
    } catch (BusinessException e) {
      log.error("Errore durante conversione data nascita variazione immigrazione: " + e, e);
    }
    dataNascita.setRequired(true);
    dataNascita.setLabel(Model.of("Data di nascita"));
    dataNascita.add(RangeValidator.maximum(LocalDate.now()));
    addOrReplace(dataNascita);

    ComuneNascitaDropDownChoise comboComuneNascita =
        new ComuneNascitaDropDownChoise(
            "comboComuneNascita", new PropertyModel(getModelObject(), "comboComuneNascita"));

    comboComuneNascita.setOutputMarkupId(true);
    comboComuneNascita.setOutputMarkupPlaceholderTag(true);
    comboComuneNascita.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -6796117729416496313L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(comboComuneNascita)) {
              if (comboComuneNascita.getValue().equalsIgnoreCase("0")) {
                containerComune.setVisible(true);
                containerComuneNascitaEstero.setVisible(false);
              } else {
                containerComune.setVisible(false);
                containerComuneNascitaEstero.setVisible(true);
              }
            }

            target.add(containerComune, containerComuneNascitaEstero);
          }
        });
    addOrReplace(comboComuneNascita);

    ComuniItalianiAutoComplete comuneNascita =
        new ComuniItalianiAutoComplete(
            "comuneNascita", new PropertyModel(getModelObject(), "comuneNascita"));
    comuneNascita.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -3496586232757888552L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneNascita.setLabel(Model.of("Comune di Nascita"));
    comuneNascita.setRequired(true);
    containerComune.addOrReplace(comuneNascita);

    addOrReplace(containerComune);

    ComuniItalianiAutoComplete comuneNascitaEstero =
        new ComuniItalianiAutoComplete(
            "comuneNascitaEstero", new PropertyModel(getModelObject(), "comuneNascitaEstero"));
    comuneNascitaEstero.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -2159457711086508645L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    comuneNascitaEstero.setRequired(false);
    comuneNascitaEstero.setLabel(Model.of("Comune estero"));
    containerComuneNascitaEstero.addOrReplace(comuneNascitaEstero);

    addOrReplace(containerComuneNascitaEstero);

    TextField nomeConiuge =
        new TextField("nomeConiuge", new PropertyModel(getModelObject(), "nomeConiuge"));
    nomeConiuge.setLabel(Model.of("Nome coniuge"));
    nomeConiuge.add(StringValidator.maximumLength(200));
    nomeConiuge.setOutputMarkupPlaceholderTag(true);
    nomeConiuge.setOutputMarkupId(true);
    containerDatiMatrimonio.addOrReplace(nomeConiuge);

    TextField cognomeConiuge =
        new TextField("cognomeConiuge", new PropertyModel(getModelObject(), "cognomeConiuge"));
    cognomeConiuge.setLabel(Model.of("Cognome coniuge"));
    cognomeConiuge.add(StringValidator.maximumLength(60));
    cognomeConiuge.setOutputMarkupPlaceholderTag(true);
    cognomeConiuge.setOutputMarkupId(true);
    containerDatiMatrimonio.addOrReplace(cognomeConiuge);

    FdCLocalDateTextfield dataMatrimonio =
        new FdCLocalDateTextfield(
            "dataMatrimonio", new PropertyModel(getModelObject(), "dataMatrimonio"));
    dataMatrimonio.setLabel(Model.of("Data matrimonio"));
    dataMatrimonio.add(RangeValidator.maximum(LocalDate.now()));
    dataMatrimonio.setOutputMarkupPlaceholderTag(true);
    dataMatrimonio.setOutputMarkupId(true);
    containerDatiMatrimonio.addOrReplace(dataMatrimonio);

    addOrReplace(containerDatiMatrimonio);

    ComuniTuttiAutoComplete comuneMatrimonio =
        new ComuniTuttiAutoComplete(
            "comuneMatrimonio", new PropertyModel<String>(getModelObject(), "comuneMatrimonio"));
    comuneMatrimonio.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -3496586232757888552L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });

    comuneMatrimonio.setLabel(Model.of("Comune matrimonio"));
    comuneMatrimonio.setOutputMarkupPlaceholderTag(true);
    comuneMatrimonio.setOutputMarkupId(true);
    containerDatiMatrimonio.addOrReplace(comuneMatrimonio);

    StatoCivileDropDownChoise statoCivile =
        new StatoCivileDropDownChoise<>(
            "statoCivile", new PropertyModel<>(getModelObject(), "statoCivile"));
    statoCivile.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 3476992650081625805L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            log.debug("CP click stato civile = " + statoCivile.getValue());

            if (LabelFdCUtil.checkIfNotNull(statoCivile.getValue())
                && (statoCivile.getValue().equalsIgnoreCase("3")
                    || statoCivile.getValue().equalsIgnoreCase("4"))) {

              nomeConiuge.setRequired(true);
              cognomeConiuge.setRequired(true);
              dataMatrimonio.setRequired(true);
              comuneMatrimonio.setRequired(true);

              AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

              nomeConiuge.add(attributeModifierRequired);
              cognomeConiuge.add(attributeModifierRequired);
              dataMatrimonio.add(attributeModifierRequired);
              comuneMatrimonio.add(attributeModifierRequired);

            } else {
              nomeConiuge.setRequired(false);
              cognomeConiuge.setRequired(false);
              dataMatrimonio.setRequired(false);
              comuneMatrimonio.setRequired(false);

              nomeConiuge.add(AttributeModifier.remove("required"));
              cognomeConiuge.add(AttributeModifier.remove("required"));
              dataMatrimonio.add(AttributeModifier.remove("required"));
              comuneMatrimonio.add(AttributeModifier.remove("required"));
            }

            target.add(containerDatiMatrimonio);
          }
        });
    addOrReplace(statoCivile);

    ParenteleDropDownChoise parentela =
        new ParenteleDropDownChoise(
            "parentela", new PropertyModel<>(getModelObject(), "parentela"));
    parentela.setRequired(true);
    addOrReplace(parentela);

    SiNoDropDownChoice patenti =
        new SiNoDropDownChoice("patenti", new PropertyModel<String>(getModelObject(), "patenti"));
    patenti.setLabel(Model.of("Patenti"));
    patenti.setRequired(true);
    addOrReplace(patenti);

    SiNoDropDownChoice veicoli =
        new SiNoDropDownChoice("veicoli", new PropertyModel<String>(getModelObject(), "veicoli"));
    veicoli.setLabel(Model.of("Veicoli"));
    veicoli.setRequired(true);
    addOrReplace(veicoli);

    ProfessioneAutoComplete professione =
        new ProfessioneAutoComplete(
            "professione", new PropertyModel<String>(getModelObject(), "professione"));
    professione.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = 7267474495842554564L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    addOrReplace(professione);

    professione.setLabel(Model.of("Professione"));
    addOrReplace(professione);

    TitoloStudioDropDownChoise titoloDiStudio =
        new TitoloStudioDropDownChoise(
            "titoloDiStudio", new PropertyModel(getModelObject(), "titoloDiStudio"));
    addOrReplace(titoloDiStudio);

    containerIndividuiCaricati.setOutputMarkupId(true);
    containerIndividuiCaricati.setOutputMarkupPlaceholderTag(true);
    containerIndividuiCaricati.setVisible(false);

    List<IndividuiCollegatiImmigrazione> listaIndividuiCaricati =
        new ArrayList<IndividuiCollegatiImmigrazione>();
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
        && !LabelFdCUtil.checkEmptyList(
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
      listaIndividuiCaricati = variazioniResidenza.getListaIndividuiCollegatiImmigrazione();
    }
    PageableListView<IndividuiCollegatiImmigrazione> listViewIndividuiCaricati =
        new PageableListView<IndividuiCollegatiImmigrazione>(
            "boxIndividuiCaricati", listaIndividuiCaricati, 6) {

          private static final long serialVersionUID = -5675802498972515707L;

          @Override
          protected void populateItem(ListItem<IndividuiCollegatiImmigrazione> item) {

            final IndividuiCollegatiImmigrazione individuo = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(VariazioniResidenzaUtil.getCssIconaIndividuoImmi(individuo));
            item.addOrReplace(icona);

            Label nominativoIndiduoCaricato =
                new Label("nominativoIndiduoCaricato", individuo.getNominativo());
            nominativoIndiduoCaricato.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativoIndiduoCaricato);

            Label cfIndividuoCaricato = new Label("cfIndividuoCaricato", individuo.getCf());
            cfIndividuoCaricato.setVisible(PageUtil.isStringValid(individuo.getCf()));
            item.addOrReplace(cfIndividuoCaricato);

            modaleCancellaIndividuoCaricato =
                new ModaleCancellaIndividuoCaricatoPanel<String>(
                    "modaleCancellaIndividuoCaricato", individuo);
            String infoCancellazione =
                getString("IndividuiCollegatiResidenzaPanel.nome").concat(" ");
            Label individuoDaCancellare =
                new Label(
                    "individuoDaCancellare", infoCancellazione.concat(individuo.getNominativo()));
            modaleCancellaIndividuoCaricato.myAdd(individuoDaCancellare);
            modaleCancellaIndividuoCaricato.addOrReplace(
                creaBtnSi(modaleCancellaIndividuoCaricato, individuo));
            modaleCancellaIndividuoCaricato.addOrReplace(
                creaBtnNo(modaleCancellaIndividuoCaricato, individuo));
            item.addOrReplace(modaleCancellaIndividuoCaricato);
            item.addOrReplace(
                creaBtnCancellaIndividuoCaricato(modaleCancellaIndividuoCaricato, individuo));
          }
        };
    containerIndividuiCaricati.addOrReplace(listViewIndividuiCaricati);
    containerIndividuiCaricati.setVisible(
        LabelFdCUtil.checkIfNotNull(listaIndividuiCaricati)
            && !LabelFdCUtil.checkEmptyList(listaIndividuiCaricati));

    addOrReplace(containerIndividuiCaricati);
  }

  public VariazioniResidenza getVariazioniResidenza() {
    return variazioniResidenza;
  }

  public void setVariazioniResidenza(VariazioniResidenza variazioniResidenza) {
    this.variazioniResidenza = variazioniResidenza;
  }

  public IndividuiCollegatiImmigrazione getIndividuiCollegatiImmigrazione() {
    return individuiCollegatiImmigrazione;
  }

  public void setIndividuiCollegatiImmigrazione(
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione) {
    this.individuiCollegatiImmigrazione = individuiCollegatiImmigrazione;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getNome() {
    return nome;
  }

  public FeedbackPanel getFeedbackPanel() {
    return feedbackPanel;
  }

  public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
    this.feedbackPanel = feedbackPanel;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(String dataNascita) {
    this.dataNascita = dataNascita;
  }

  public CompoundPropertyModel<IndividuiCollegatiImmigrazione> getCompoundPropertyModel() {
    return compoundPropertyModel;
  }

  public void setCompoundPropertyModel(
      CompoundPropertyModel<IndividuiCollegatiImmigrazione> compoundPropertyModel) {
    this.compoundPropertyModel = compoundPropertyModel;
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }

  private LaddaAjaxLink<Object> creaBtnSi(
      Modal<String> modale, IndividuiCollegatiImmigrazione individuo) {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -669267938656131541L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(getVariazioniResidenza())
                && LabelFdCUtil.checkIfNotNull(
                    getVariazioniResidenza().getListaIndividuiCollegatiImmigrazione())) {
              getVariazioniResidenza()
                  .getListaIndividuiCollegatiImmigrazione()
                  .removeIf(elem -> elem.getCf().equalsIgnoreCase(individuo.getCf()));
            }

            setResponsePage(new RichiestaResidenzaPage(3, getVariazioniResidenza()));
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaForm.si", IndividuiCollegatiResidenzaForm.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo(
      Modal<String> modale, IndividuiCollegatiImmigrazione individuo) {
    LaddaAjaxLink<Object> btnNo =
        new LaddaAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = 2964316716220115193L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modale.close(target);
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaForm.no", IndividuiCollegatiResidenzaForm.this)));

    return btnNo;
  }

  private LaddaAjaxLink<Object> creaBtnCancellaIndividuoCaricato(
      Modal<String> modale, IndividuiCollegatiImmigrazione individuo) {
    LaddaAjaxLink<Object> btnCancellaIndividuoCaricato =
        new LaddaAjaxLink<Object>("btnCancellaIndividuoCaricato", Type.Primary) {

          private static final long serialVersionUID = -228954625313765821L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modale.show(target);
          }
        };

    btnCancellaIndividuoCaricato.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaForm.elimina",
                    IndividuiCollegatiResidenzaForm.this)));

    IconType iconType =
        new IconType("btnCancellaIndividuoCaricato") {

          private static final long serialVersionUID = -14756350546809681L;

          @Override
          public String cssClassName() {
            return "icon-spazzatura";
          }
        };
    btnCancellaIndividuoCaricato.setIconType(iconType);

    btnCancellaIndividuoCaricato.setOutputMarkupId(true);
    btnCancellaIndividuoCaricato.setOutputMarkupPlaceholderTag(true);

    return btnCancellaIndividuoCaricato;
  }
}
