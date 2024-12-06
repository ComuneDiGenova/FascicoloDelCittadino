package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.preferenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiStradeAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.model.Model;

public class StradaAllerteCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = -5932545105184441563L;

  private int index;

  Immobile immobile;

  ViarioGeoserverAutoComplete autoCompleteViario;
  FeaturesGeoserver geoServerFeature;

  public StradaAllerteCortesiaPanel(String id, DatiStradeAllerteCortesia datiStrada, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiStrada);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    DatiStradeAllerteCortesia datiStrada = (DatiStradeAllerteCortesia) dati;

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziAllerteCortesia()
            .popolaListaMessaggiAggiungiStrada();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

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

      // autoCompleteViario.setModelObject(toponomasticaResidenzaLoggato);

      // aggiornaPericolosita();

      listaIndirizzoDiResidenza.add(toponomasticaResidenzaLoggato);

      /*
      log.debug("CP modelOfPericolositaIdrualica.getObject() loggato = "
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
      }*/

    } catch (BusinessException | ApiException e) {
      log.error("Errore toponomastica " + e.getMessage(), e);
    }

    log.debug("CP listaIndirizzoDiResidenza = " + listaIndirizzoDiResidenza);

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            // aggiornaPericolosita();
            FeaturesGeoserver featuresGeoserver =
                (FeaturesGeoserver) autoCompleteViario.getModelObject();
            datiStrada.setAutocompleteViario(featuresGeoserver);

            // target.add(pericolositaIdro, pericolositaIncendio, pericolositaFrane,
            // containerCortesia,
            //		containerZonaRossa);

          }
        });

    addOrReplace(autoCompleteViario);
  }
}
