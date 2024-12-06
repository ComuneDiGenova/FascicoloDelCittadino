package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoImmobileEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.UtilizzoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCapField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.CategoriaCatastaleRender;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.ClasseCatastaleValidator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.NumberPositiveValidator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
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
import org.apache.wicket.util.convert.IConverter;

public abstract class InserimentoImmobilePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 1L;

  FeaturesGeoserver geoServerFeature;
  // GeoserverComboSelect2<Object> indirizzo;
  ViarioGeoserverAutoComplete autoCompleteViario;

  WebMarkupContainer _container;
  WebMarkupContainer containerDati;
  DropDownChoice<TipoImmobileEnum> tipoImmobile;
  DropDownChoice<CategoriaCatastale> categoria;
  RadioChoice<UtilizzoEnum> utilizzo;
  FdCTextField<Component> interno;

  FdCNumberField<Component> subalterno;

  boolean isNotEditMode;
  Immobile immobile;

  public InserimentoImmobilePanel(String id, Immobile pojo, boolean isNotEditMode) {
    super(id, pojo);

    this.isNotEditMode = isNotEditMode;
    // TODO Auto-generated constructor stub
    this.fillDati(pojo);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    super.fillDati(dati);

    immobile = (Immobile) dati;

    List<CategoriaCatastale> categorie = RichiestaRimborsoImuHelper.getCategorieCatastali();

    tipoImmobile =
        new DropDownChoice<TipoImmobileEnum>(
            "tipoI",
            new PropertyModel<TipoImmobileEnum>(immobile, "tipo"),
            Arrays.asList(TipoImmobileEnum.values()));

    tipoImmobile.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

            if (tipoImmobile.getModelObject().equals(TipoImmobileEnum.F)) {
              subalterno.getNumberField().setRequired(true);
              interno.setRequired(true);
              interno.add(attributeModifierRequired);
              subalterno.getNumberField().add(attributeModifierRequired);
            } else {
              subalterno.getNumberField().setRequired(false);
              interno.setRequired(false);
              subalterno.getNumberField().add(AttributeModifier.remove("required"));
              interno.add(AttributeModifier.remove("required"));
            }

            target.add(subalterno, interno);
          }
        });

    tipoImmobile.setLabel(Model.of("Tipo"));
    tipoImmobile.setRequired(true);
    tipoImmobile.setOutputMarkupId(true);
    tipoImmobile.setEnabled(isNotEditMode);

    categoria =
        new DropDownChoice<CategoriaCatastale>(
            "categoriaI",
            new PropertyModel<CategoriaCatastale>(immobile, "categoria"),
            categorie,
            new CategoriaCatastaleRender());
    categoria.setOutputMarkupId(true);
    categoria.setLabel(Model.of("Categoria Catastale"));
    categoria.setRequired(true);
    categoria.setEnabled(isNotEditMode);
    /*categoria.add(new AjaxFormComponentUpdatingBehavior("change") {
    	private static final long serialVersionUID = -17979797654654987L;

    	@Override
    	protected void onUpdate(AjaxRequestTarget target) {
    		// TODO Auto-generated method stub
    		log.debug(String.format("[Categoria] Codice: %s, Descrizione: %s ", categoria.getModelObject().getCodice(), categoria.getModelObject().getDescrizione()));
    		target.add(categoria);
    	}
    });*/

    FdCTextField<Component> sezione =
        new FdCTextField<Component>(
            "sezioneI",
            new PropertyModel<String>(immobile, "sezione"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    sezione.setEnabled(isNotEditMode);
    sezione.setRequired(true);
    sezione.setOutputMarkupId(true);

    FdCNumberField<Component> foglio =
        new FdCNumberField<Component>(
            "foglioI",
            new PropertyModel<Integer>(immobile, "foglio"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    foglio.setEnabled(isNotEditMode);
    foglio.setRequired(true);
    foglio.setOutputMarkupId(true);
    foglio.getNumberField().add(new NumberPositiveValidator<Integer>(true));
    foglio.getNumberField().add(new AttributeAppender("min", "0"));

    FdCNumberField<Component> particella =
        new FdCNumberField<Component>(
            "particellaI",
            new PropertyModel<Integer>(immobile, "particella"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    particella.setEnabled(isNotEditMode);
    particella.setRequired(true);
    particella.setOutputMarkupId(true);
    particella.getNumberField().add(new NumberPositiveValidator<Integer>(true));
    particella.getNumberField().add(new AttributeAppender("min", "0"));

    subalterno =
        new FdCNumberField<Component>(
            "subalternoI",
            new PropertyModel<Integer>(immobile, "subalterno"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    subalterno.setOutputMarkupId(true);
    subalterno.setOutputMarkupPlaceholderTag(true);
    subalterno.setEnabled(isNotEditMode);
    subalterno.setRequired(true);
    subalterno.getNumberField().add(new NumberPositiveValidator<Integer>(true));
    subalterno.getNumberField().add(new AttributeAppender("min", "0"));

    FdCTextField<Component> indirizzoReadOnly =
        new FdCTextField<Component>(
            "indirizzoIReadOnly",
            new PropertyModel<String>(immobile, "indirizzo"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");

    indirizzoReadOnly.setEnabled(false);
    indirizzoReadOnly.setVisible(!isNotEditMode);
    indirizzoReadOnly.setOutputMarkupId(true);

    //		indirizzo = new GeoserverComboSelect2<Object>("indirizzoI",
    //				new PropertyModel<FeaturesGeoserver>(immobile, "geoServerFeature"),
    // InserimentoImmobilePanel.this,
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
            settings) {}; // non togliere le graffe!!!
    autoCompleteViario.setLabel(Model.of("Indirizzo"));
    autoCompleteViario.setRequired(true);

    // autoCompleteViario.add(new AttributeAppender("onchange", new Model("this.dispatchEvent(new
    // Event('change'));"), ";"));

    // indirizzo.getCombo().add(new OnChangeAjaxBehaviorSenzaIndicator() {

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            selezioneIndirizzo();
            // target.add(indirizzo);
            // target.add(autoCompleteViario);
          }

          private void selezioneIndirizzo() {
            // TODO Auto-generated method stub
            // FeaturesGeoserver featuresGeoserver = (FeaturesGeoserver)
            // indirizzo.getCombo().getModelObject();
            FeaturesGeoserver featuresGeoserver =
                (FeaturesGeoserver) autoCompleteViario.getModelObject();

            log.debug("[FeatureGeoServer] GeoServer: " + featuresGeoserver);

            if (LabelFdCUtil.checkIfNotNull(featuresGeoserver)) {
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
          }
        });

    //		indirizzo.setEnabled(isNotEditMode);
    //
    //		indirizzo.setVisible(isNotEditMode);
    //		indirizzo.setOutputMarkupId(true);

    autoCompleteViario.setEnabled(isNotEditMode);

    autoCompleteViario.setVisible(isNotEditMode);
    autoCompleteViario.setOutputMarkupId(true);

    FdCTextField<Component> scala =
        new FdCTextField<Component>(
            "scalaI",
            new PropertyModel<String>(immobile, "scala"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    scala.setEnabled(isNotEditMode);
    scala.setOutputMarkupId(true);

    FdCTextField<Component> piano =
        new FdCTextField<Component>(
            "pianoI",
            new PropertyModel<String>(immobile, "piano"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    piano.setEnabled(isNotEditMode);
    piano.setOutputMarkupId(true);

    interno =
        new FdCTextField<Component>(
            "internoI",
            new PropertyModel<String>(immobile, "interno"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    interno.setRequired(true);
    interno.setEnabled(isNotEditMode);
    interno.setOutputMarkupId(true);

    FdCCapField<Component> cap =
        new FdCCapField<Component>(
            "capI",
            new PropertyModel<String>(immobile, "cap"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    cap.setRequired(false);
    cap.getTextField().add(new CAPValidator());
    cap.setOutputMarkupId(true);

    FdCTextField<Component> classe =
        new FdCTextField<Component>(
            "classeI",
            new PropertyModel<String>(immobile, "classe"),
            InserimentoImmobilePanel.this,
            "DatiImmobiliPanel");
    classe.setEnabled(isNotEditMode);
    classe.setOutputMarkupId(true);
    classe.getTextField().add(new AttributeAppender("maxlength", "2"));
    classe.getTextField().add(new ClasseCatastaleValidator());

    FdCTextField<Component> altro =
        new FdCTextField<Component>(
            "altroI",
            new PropertyModel<String>(immobile, "altro"),
            InserimentoImmobilePanel.this,
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
    percentualePossesso.setMaximum(new BigDecimal(100));
    percentualePossesso.setMinimum(BigDecimal.ZERO);

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

    form.addOrReplace(categoria);
    form.addOrReplace(tipoImmobile);
    form.addOrReplace(sezione);
    form.addOrReplace(foglio);
    form.addOrReplace(particella);
    form.addOrReplace(subalterno);
    // form.addOrReplace(indirizzo);
    form.addOrReplace(autoCompleteViario);
    form.addOrReplace(indirizzoReadOnly);
    form.addOrReplace(scala);
    form.addOrReplace(piano);
    form.addOrReplace(interno);
    form.addOrReplace(cap);
    form.addOrReplace(classe);
    form.addOrReplace(percentualePossesso);
    form.addOrReplace(utilizzo);
    form.addOrReplace(altro);

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

    form.addOrReplace(annulla);

    Button salva =
        new AjaxButton("btnSalva") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            salva(target, immobile);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            target.add(feedbackPanel);
          }
        };

    salva.setDefaultFormProcessing(true);
    salva.setLabel(Model.of("Salva"));
    form.addOrReplace(salva);
  }

  @Override
  public <C> IConverter<C> getConverter(Class<C> type) {
    // TODO Auto-generated method stub
    return super.getConverter(type);
  }

  public abstract void annulla(AjaxRequestTarget target);

  public abstract void salva(AjaxRequestTarget target, Immobile immobile);
}
