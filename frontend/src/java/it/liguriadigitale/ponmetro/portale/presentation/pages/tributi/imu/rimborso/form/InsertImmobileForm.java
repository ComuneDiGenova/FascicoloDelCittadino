package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastaleEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoImmobileEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.UtilizzoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCapField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public abstract class InsertImmobileForm extends AbstracFrameworkForm<Immobile> {

  private static final long serialVersionUID = 19879765413188L;

  Immobile immobile;

  FeaturesGeoserver geoServerFeature;
  // GeoserverComboSelect2<Object> indirizzo;
  ViarioGeoserverAutoComplete autoCompleteViario;

  WebMarkupContainer _container;
  DropDownChoice<TipoImmobileEnum> tipoImmobile;
  DropDownChoice<CategoriaCatastaleEnum> categoria;
  RadioChoice<UtilizzoEnum> utilizzo;
  boolean isNotEditMode;

  public InsertImmobileForm(
      String id, Immobile model, WebMarkupContainer container, boolean isNotEditMode) {
    super(id, model);
    // TODO Auto-generated constructor stub
    immobile = model;
    this._container = container;
    this.isNotEditMode = isNotEditMode;

    addElementiForm();
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub
    tipoImmobile =
        new DropDownChoice<TipoImmobileEnum>(
            "tipoI",
            new PropertyModel<TipoImmobileEnum>(immobile, "tipo"),
            Arrays.asList(TipoImmobileEnum.values()));

    tipoImmobile.setRequired(true);
    tipoImmobile.setOutputMarkupId(true);
    tipoImmobile.setEnabled(isNotEditMode);

    categoria =
        new DropDownChoice<CategoriaCatastaleEnum>(
            "categoriaI",
            new PropertyModel<CategoriaCatastaleEnum>(immobile, "categoria"),
            Arrays.asList(CategoriaCatastaleEnum.values()));
    categoria.setOutputMarkupId(true);
    categoria.setEnabled(isNotEditMode);

    FdCTextField<Component> sezione =
        new FdCTextField<Component>(
            "sezioneI",
            new PropertyModel<String>(immobile, "sezione"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    sezione.setEnabled(isNotEditMode);
    sezione.setRequired(true);
    sezione.setOutputMarkupId(true);

    FdCNumberField<Component> foglio =
        new FdCNumberField<Component>(
            "foglioI",
            new PropertyModel<Integer>(immobile, "foglio"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    foglio.setEnabled(isNotEditMode);
    foglio.setRequired(true);
    foglio.setOutputMarkupId(true);

    FdCNumberField<Component> particella =
        new FdCNumberField<Component>(
            "particellaI",
            new PropertyModel<Integer>(immobile, "particella"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    particella.setEnabled(isNotEditMode);
    particella.setRequired(true);
    particella.setOutputMarkupId(true);

    FdCNumberField<Component> subalterno =
        new FdCNumberField<Component>(
            "subalternoI",
            new PropertyModel<Integer>(immobile, "subalterno"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    subalterno.setEnabled(isNotEditMode);
    subalterno.setRequired(true);
    subalterno.setOutputMarkupId(true);

    FdCTextField<Component> indirizzoReadOnly =
        new FdCTextField<Component>(
            "indirizzoIReadOnly",
            new PropertyModel<String>(immobile, "indirizzo"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");

    indirizzoReadOnly.setEnabled(false);
    indirizzoReadOnly.setVisible(!isNotEditMode);
    indirizzoReadOnly.setOutputMarkupId(true);

    //		indirizzo = new GeoserverComboSelect2<Object>("indirizzoI",
    //				new PropertyModel<FeaturesGeoserver>(immobile, "geoServerFeature"),
    // InsertImmobileForm.this,
    //				"DatiImmobiliPanel");
    //		indirizzo.getCombo().setRequired(true);

    AbstractAutoCompleteTextRenderer<FeaturesGeoserver> autocompleteRender =
        new AbstractAutoCompleteTextRenderer<FeaturesGeoserver>() {

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
            "indirizzoI",
            new PropertyModel<FeaturesGeoserver>(immobile, "geoServerFeature"),
            autocompleteRender,
            settings);
    autoCompleteViario.setLabel(Model.of("Indirizzo"));
    autoCompleteViario.setRequired(true);

    // indirizzo.getCombo().add(new OnChangeAjaxBehaviorSenzaIndicator() {

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            selezioneIndirizzo();
            // target.add(indirizzo);
            target.add(autoCompleteViario);
          }

          private void selezioneIndirizzo() {
            // FeaturesGeoserver featuresGeoserver = (FeaturesGeoserver)
            // indirizzo.getCombo().getModelObject();
            FeaturesGeoserver featuresGeoserver =
                (FeaturesGeoserver) autoCompleteViario.getModelObject();

            log.debug("[FeatureGeoServer] GeoServer: " + featuresGeoserver);

            String colore =
                LabelFdCUtil.checkIfNull(featuresGeoserver.getCOLORE())
                        || LabelFdCUtil.checkNotEmpty(featuresGeoserver.getCOLORE())
                    ? "N"
                    : featuresGeoserver.getCOLORE();

            immobile.setColore(colore);
            immobile.setCivico(Long.valueOf(featuresGeoserver.getNUMERO()));
            immobile.setEsponente(featuresGeoserver.getLETTERA());
            immobile.setIndirizzo(featuresGeoserver.getDESVIA());
          }
        });

    // indirizzo.setEnabled(isNotEditMode);
    //		indirizzo.setVisible(isNotEditMode);
    //		indirizzo.setOutputMarkupId(true);

    autoCompleteViario.setEnabled(isNotEditMode);
    autoCompleteViario.setVisible(isNotEditMode);
    autoCompleteViario.setOutputMarkupId(true);

    FdCTextField<Component> scala =
        new FdCTextField<Component>(
            "scalaI",
            new PropertyModel<String>(immobile, "scala"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    scala.setEnabled(isNotEditMode);
    scala.setOutputMarkupId(true);

    FdCTextField<Component> piano =
        new FdCTextField<Component>(
            "pianoI",
            new PropertyModel<String>(immobile, "piano"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    piano.setEnabled(isNotEditMode);
    piano.setOutputMarkupId(true);
    FdCTextField<Component> interno =
        new FdCTextField<Component>(
            "internoI",
            new PropertyModel<String>(immobile, "interno"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    interno.setRequired(true);
    interno.setEnabled(isNotEditMode);
    interno.setOutputMarkupId(true);

    FdCCapField<Component> cap =
        new FdCCapField<Component>(
            "capI",
            new PropertyModel<String>(immobile, "cap"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    cap.setRequired(true);
    cap.getTextField().add(new CAPValidator());
    cap.setOutputMarkupId(true);

    FdCTextField<Component> classe =
        new FdCTextField<Component>(
            "classeI",
            new PropertyModel<String>(immobile, "classe"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    classe.setEnabled(isNotEditMode);
    classe.setOutputMarkupId(true);

    FdCTextField<Component> altro =
        new FdCTextField<Component>(
            "altroI",
            new PropertyModel<String>(immobile, "altro"),
            InsertImmobileForm.this,
            "DatiImmobiliPanel");
    altro.setVisible(false);
    altro.setOutputMarkupId(true);

    NumberTextField<BigDecimal> percentualePossesso =
        new NumberTextField<BigDecimal>(
            "percentualePossessoI",
            new PropertyModel<BigDecimal>(immobile, "percentualePossesso"),
            BigDecimal.class);
    percentualePossesso.setRequired(true);
    percentualePossesso.add(new AttributeAppender("step", ".01"));
    percentualePossesso.setLabel(Model.of("Percentuale di Possesso"));
    percentualePossesso.setOutputMarkupId(true);

    utilizzo =
        new RadioChoice<UtilizzoEnum>(
            "utilizzoI",
            new PropertyModel<UtilizzoEnum>(immobile, "utilizzo"),
            Arrays.asList(UtilizzoEnum.values()));

    utilizzo.setPrefix("<div class=\"form-check\">");
    utilizzo.setSuffix("</div>");
    utilizzo.setRequired(true);
    utilizzo.setOutputMarkupPlaceholderTag(true);
    utilizzo.setOutputMarkupId(true);
    utilizzo.setLabel(Model.of("Utilizzo"));

    utilizzo.add(
        new AjaxFormChoiceComponentUpdatingBehavior() {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            altro.setVisible(utilizzo.getModelObject().equals(UtilizzoEnum.U6));
            target.add(utilizzo, altro);
          }
        });

    addOrReplace(categoria);
    addOrReplace(tipoImmobile);
    addOrReplace(sezione);
    addOrReplace(foglio);
    addOrReplace(particella);
    addOrReplace(subalterno);
    // addOrReplace(indirizzo);
    addOrReplace(autoCompleteViario);
    addOrReplace(indirizzoReadOnly);
    addOrReplace(scala);
    addOrReplace(piano);
    addOrReplace(interno);
    addOrReplace(cap);
    addOrReplace(classe);
    addOrReplace(percentualePossesso);
    addOrReplace(utilizzo);
    addOrReplace(altro);

    Button annulla =
        new AjaxButton("btnAnnulla") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            annulla(target);
          }
        };

    annulla.setDefaultFormProcessing(false);
    annulla.setLabel(Model.of("Annulla"));

    addOrReplace(annulla);

    /*Button salva = new AjaxButton("btnSalva") {
    	private static final long serialVersionUID = 1L;

    	@Override
    	public void onSubmit(AjaxRequestTarget target) {
    		// TODO Auto-generated method stub
    		salva(target);
    	}

    };

    salva.setDefaultFormProcessing(true);
    salva.setLabel(Model.of("Salva"));
    addOrReplace(salva);*/

  }

  public abstract void annulla(AjaxRequestTarget target);

  public abstract void salva(AjaxRequestTarget target);
}
