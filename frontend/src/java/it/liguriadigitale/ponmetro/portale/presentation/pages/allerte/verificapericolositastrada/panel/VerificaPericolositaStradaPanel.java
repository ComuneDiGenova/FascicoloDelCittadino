package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.verificapericolositastrada.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.cerco.IoCercoRiepilogoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("rawtypes")
public class VerificaPericolositaStradaPanel extends BasePanel {

  private static final long serialVersionUID = 8579371953467576008L;

  private ViarioGeoserverAutoComplete autoCompleteViario;

  private FdCTextField pericolositaIdro;
  private FdCTextField pericolositaIncendio;
  private FdCTextField pericolositaFrane;
  private FeaturesGeoserver geoServerFeature;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private IModel<String> modelOfPericolositaIncedio = new Model<String>();
  private IModel<String> modelOfPericolositaIdrualica = new Model<String>();
  private IModel<String> modelOfPericolositaPfrane = new Model<String>();

  private WebMarkupContainer containerCortesia;
  private WebMarkupContainer containerZonaRossa;

  private String pericolositaAlta = "ALTA";

  public VerificaPericolositaStradaPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(null);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public void fillDati(Object dati) {

    containerCortesia = new WebMarkupContainer("containerCortesia");

    FdCButtonBootstrapAjaxLink btnVaiACortesia = btnCortesia();
    containerCortesia.add(btnVaiACortesia);

    containerCortesia.setOutputMarkupId(true);
    containerCortesia.setOutputMarkupPlaceholderTag(true);
    containerCortesia.setVisible(false);
    addOrReplace(containerCortesia);

    containerZonaRossa = new WebMarkupContainer("containerZonaRossa");

    FdCButtonBootstrapAjaxLink btnVaiAZonaRossa = btnZonaRossa();
    containerZonaRossa.add(btnVaiAZonaRossa);

    containerZonaRossa.setOutputMarkupId(true);
    containerZonaRossa.setOutputMarkupPlaceholderTag(true);
    containerZonaRossa.setVisible(false);
    addOrReplace(containerZonaRossa);

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
            "autocompleteViario",
            Model.of(geoServerFeature),
            autocompleteRender,
            settings) {}; // non togliere le graffe!!!
    autoCompleteViario.setLabel(Model.of("Indirizzo"));
    autoCompleteViario.setRequired(true);

    List<FeaturesGeoserver> listaIndirizzoDiResidenza = new ArrayList<FeaturesGeoserver>();

    try {
      FeaturesGeoserver toponomasticaResidenzaLoggato =
          ServiceLocator.getInstance()
              .getServiziGeoserver()
              .getToponomasticaResidenzaUtenteLoggato(getUtente());

      autoCompleteViario.setModelObject(toponomasticaResidenzaLoggato);

      aggiornaPericolosita();

      listaIndirizzoDiResidenza.add(toponomasticaResidenzaLoggato);

      log.debug(
          "CP modelOfPericolositaIdrualica.getObject() loggato = "
              + modelOfPericolositaIdrualica.getObject());

      if (LabelFdCUtil.checkIfNotNull(modelOfPericolositaIdrualica)
          && LabelFdCUtil.checkIfNotNull(modelOfPericolositaIdrualica.getObject())) {
        if (modelOfPericolositaIdrualica.getObject().equalsIgnoreCase(pericolositaAlta)) {
          containerZonaRossa.setVisible(true);
          containerCortesia.setVisible(false);
        } else {
          containerZonaRossa.setVisible(false);
          containerCortesia.setVisible(true);
        }
      }

    } catch (BusinessException | ApiException e) {
      log.error("Errore toponomastica " + e.getMessage(), e);
    }

    log.debug("CP listaIndirizzoDiResidenza = " + listaIndirizzoDiResidenza);

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            aggiornaPericolosita();

            if (LabelFdCUtil.checkIfNotNull(modelOfPericolositaIdrualica)
                && LabelFdCUtil.checkIfNotNull(modelOfPericolositaIdrualica.getObject())) {
              if (modelOfPericolositaIdrualica.getObject().equalsIgnoreCase(pericolositaAlta)) {
                containerZonaRossa.setVisible(true);
                containerCortesia.setVisible(false);
              } else {
                containerZonaRossa.setVisible(false);
                containerCortesia.setVisible(true);
              }
            }

            target.add(
                pericolositaIdro,
                pericolositaIncendio,
                pericolositaFrane,
                containerCortesia,
                containerZonaRossa);
          }
        });

    addOrReplace(autoCompleteViario);

    pericolositaIdro =
        new FdCTextField(
            "pericolositaIdro", modelOfPericolositaIdrualica, VerificaPericolositaStradaPanel.this);
    pericolositaIdro.setEnabled(false);
    pericolositaIdro.setOutputMarkupId(true);
    addOrReplace(pericolositaIdro);

    pericolositaIncendio =
        new FdCTextField(
            "pericolositaIncendio",
            modelOfPericolositaIncedio,
            VerificaPericolositaStradaPanel.this);
    pericolositaIncendio.setEnabled(false);
    pericolositaIncendio.setOutputMarkupId(true);
    pericolositaIncendio.setOutputMarkupPlaceholderTag(true);
    addOrReplace(pericolositaIncendio);

    pericolositaFrane =
        new FdCTextField(
            "pericolositaFrane", modelOfPericolositaPfrane, VerificaPericolositaStradaPanel.this);
    pericolositaFrane.setEnabled(false);
    pericolositaFrane.setOutputMarkupId(true);
    pericolositaFrane.setOutputMarkupPlaceholderTag(true);
    addOrReplace(pericolositaFrane);

    addOrReplace(creaBottoneAnnulla());
  }

  @SuppressWarnings({"unchecked"})
  private FdCButtonBootstrapAjaxLink btnCortesia() {
    FdCButtonBootstrapAjaxLink btnVaiACortesia =
        new FdCButtonBootstrapAjaxLink("btnVaiACortesia", Type.Primary) {

          private static final long serialVersionUID = 7717074067109225909L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    IconType icon =
        new IconType("btnVaiACortesia") {

          private static final long serialVersionUID = -4364612481443783595L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnVaiACortesia.setIconType(icon);

    btnVaiACortesia.setLabel(
        Model.of(getString("VerificaPericolositaStradaPanel.btnVaiACortesia")));
    return btnVaiACortesia;
  }

  @SuppressWarnings({"unchecked"})
  private FdCButtonBootstrapAjaxLink btnZonaRossa() {
    FdCButtonBootstrapAjaxLink btnVaiAZonaRossa =
        new FdCButtonBootstrapAjaxLink("btnVaiAZonaRossa", Type.Primary) {

          private static final long serialVersionUID = 7342661918296185975L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new AllertePage(autoCompleteViario.getModelObject()));
          }
        };

    IconType icon =
        new IconType("btnVaiAZonaRossa") {

          private static final long serialVersionUID = -4364612481443783595L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnVaiAZonaRossa.setIconType(icon);

    btnVaiAZonaRossa.setLabel(
        Model.of(getString("VerificaPericolositaStradaPanel.btnVaiAZonaRossa")));
    return btnVaiAZonaRossa;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 4725095535564124330L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(VerificaPericolositaStradaPanel.this);

            setResponsePage(new IoCercoRiepilogoPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "VerificaPericolositaStradaPanel.annulla",
                    VerificaPericolositaStradaPanel.this)));

    annulla.setVisible(false);

    return annulla;
  }

  private void aggiornaPericolosita() {
    FeaturesGeoserver featuresGeoserver = (FeaturesGeoserver) autoCompleteViario.getModelObject();

    if (LabelFdCUtil.checkIfNotNull(featuresGeoserver)) {
      String codStrada = featuresGeoserver.getCOD_STRADA();
      String lettera = featuresGeoserver.getLETTERA();
      String numero = featuresGeoserver.getNUMERO();
      String colore = featuresGeoserver.getCOLORE();

      if (lettera == null || lettera.equalsIgnoreCase("")) {
        lettera = "0";
      }

      if (colore == null || colore.equalsIgnoreCase("")) {
        colore = "N";
      }

      try {
        Features feature =
            ServiceLocator.getInstance()
                .getServiziPericolosita()
                .getPericolosita(codStrada, lettera, numero, colore);

        String pFrane = feature.getFeatures().get(0).getProperties().getPERICOLOSFRANE();
        if (pFrane == null || pFrane.equalsIgnoreCase("")) {
          pFrane =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "VerificaPericolositaStradaPanel.nessunaPericolosita",
                      VerificaPericolositaStradaPanel.this);
        }

        modelOfPericolositaPfrane.setObject(pFrane);

        String pIdro = feature.getFeatures().get(0).getProperties().getPERICOLOSIDRO();
        if (pIdro == null || pIdro.equalsIgnoreCase("")) {
          pIdro =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "VerificaPericolositaStradaPanel.nessunaPericolosita",
                      VerificaPericolositaStradaPanel.this);
        }

        modelOfPericolositaIdrualica.setObject(pIdro);

        String pInc = feature.getFeatures().get(0).getProperties().getPERICOLOSINC();
        if (pInc == null || pInc.equalsIgnoreCase("")) {
          pInc =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "VerificaPericolositaStradaPanel.nessunaPericolosita",
                      VerificaPericolositaStradaPanel.this);
        }

        modelOfPericolositaIncedio.setObject(pInc);

      } catch (BusinessException | ApiException | IOException e) {
        log.error("CP errore pericolosita" + e.getMessage(), e);
      }
    }
  }
}
