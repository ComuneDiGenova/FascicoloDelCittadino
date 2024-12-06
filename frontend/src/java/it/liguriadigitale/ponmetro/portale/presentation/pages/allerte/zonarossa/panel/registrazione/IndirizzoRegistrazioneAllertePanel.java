package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class IndirizzoRegistrazioneAllertePanel extends BasePanel {

  private static final long serialVersionUID = -4189271656690258149L;

  @SuppressWarnings("unused")
  private int index;

  private ViarioGeoserverAutoComplete autoCompleteViario;

  @SuppressWarnings("rawtypes")
  private FdCTextField pericolositaIdro;

  @SuppressWarnings("rawtypes")
  private FdCTextField pericolositaIncendio;

  @SuppressWarnings("rawtypes")
  private FdCTextField pericolositaFrane;

  private WebMarkupContainer wmkPericolositaNonSufficiente;

  private FdcAjaxButton avanti;

  private CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti;

  private boolean isDaVerificaPericolositaStrada;

  public IndirizzoRegistrazioneAllertePanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      int index,
      FdcAjaxButton avanti) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.avanti = avanti;
    fillDati(datiCompleti);
  }

  public IndirizzoRegistrazioneAllertePanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      int index,
      FdcAjaxButton avanti,
      boolean isDaVerificaPericolositaStrada) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.avanti = avanti;
    this.isDaVerificaPericolositaStrada = isDaVerificaPericolositaStrada;

    fillDati(datiCompleti);
  }

  private void aggiornaPericolositaDaUtente() {

    try {
      FeaturesGeoserver datiToponomy = null;

      if (isDaVerificaPericolositaStrada) {
        datiToponomy = datiCompleti.getObject().getCivicoZonaRossa().getAutocompleteViario();
      } else {
        datiToponomy =
            ServiceLocator.getInstance()
                .getServiziGeoserver()
                .getToponomasticaResidenzaUtenteLoggato(getUtente());
      }

      String codStrada = datiToponomy.getCOD_STRADA();
      String lettera = datiToponomy.getLETTERA();
      String numero = datiToponomy.getNUMERO();
      String colore = datiToponomy.getCOLORE();

      if (!PageUtil.isStringValid(lettera)) {
        lettera = "0";
      }
      if (!PageUtil.isStringValid(colore)) {
        colore = "N";
      }

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
                    "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                    IndirizzoRegistrazioneAllertePanel.this);
      }
      datiCompleti.getObject().getCivicoZonaRossa().setPericolositaFrane(pFrane);

      String pIdro = feature.getFeatures().get(0).getProperties().getPERICOLOSIDRO();
      if (pIdro == null || pIdro.equalsIgnoreCase("")) {
        pIdro =
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                    IndirizzoRegistrazioneAllertePanel.this);
      }
      datiCompleti.getObject().getCivicoZonaRossa().setPericolositaIdro(pIdro);

      String pInc = feature.getFeatures().get(0).getProperties().getPERICOLOSINC();
      if (pInc == null || pInc.equalsIgnoreCase("")) {
        pInc =
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                    IndirizzoRegistrazioneAllertePanel.this);
      }
      datiCompleti.getObject().getCivicoZonaRossa().setPericolositaIncendio(pInc);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("CP errore pericolosita" + e.getMessage(), e);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {

    datiCompleti = (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa>) dati;

    addOrReplace(
        new FdCTitoloPanel("titolo", getString("IndirizzoRegistrazioneAllertePanel.titolo")));

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
            datiCompleti.bind("civicoZonaRossa.autocompleteViario"),
            autocompleteRender,
            settings) {}; // da non togliere le graffe!!!
    autoCompleteViario.setLabel(Model.of("Indirizzo"));
    autoCompleteViario.setRequired(true);

    List<FeaturesGeoserver> listaIndirizzoDiResidenza = new ArrayList<FeaturesGeoserver>();
    if (LabelFdCUtil.checkIfNotNull(datiCompleti)
        && LabelFdCUtil.checkIfNotNull(datiCompleti.getObject())
        && LabelFdCUtil.checkIfNotNull(datiCompleti.getObject().getCivicoZonaRossa())
        && LabelFdCUtil.checkIfNotNull(
            datiCompleti.getObject().getCivicoZonaRossa().getAutocompleteViario())) {

      listaIndirizzoDiResidenza.add(
          datiCompleti.getObject().getCivicoZonaRossa().getAutocompleteViario());

      aggiornaPericolositaDaUtente();
    }
    log.debug("CP listaIndirizzoDiResidenza = " + listaIndirizzoDiResidenza);

    autoCompleteViario.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("onupdate indirizzi");

            aggiornaPericolosita();

            wmkPericolositaNonSufficiente.setVisible(
                !"ALTA"
                    .equalsIgnoreCase(
                        datiCompleti.getObject().getCivicoZonaRossa().getPericolositaIdro()));
            avanti.setVisible(
                "ALTA"
                    .equalsIgnoreCase(
                        datiCompleti.getObject().getCivicoZonaRossa().getPericolositaIdro()));

            target.add(
                pericolositaIdro,
                pericolositaIncendio,
                pericolositaFrane,
                wmkPericolositaNonSufficiente,
                avanti);
          }
        });

    log.debug("CP datiCompleti.getObject() = " + datiCompleti.getObject());

    addOrReplace(autoCompleteViario);

    pericolositaIdro =
        new FdCTextField(
            "pericolositaIdro",
            datiCompleti.bind("civicoZonaRossa.pericolositaIdro"),
            IndirizzoRegistrazioneAllertePanel.this);
    pericolositaIdro.setEnabled(false);
    addOrReplace(pericolositaIdro);

    pericolositaIncendio =
        new FdCTextField(
            "pericolositaIncendio",
            datiCompleti.bind("civicoZonaRossa.pericolositaIncendio"),
            IndirizzoRegistrazioneAllertePanel.this);
    pericolositaIncendio.setEnabled(false);
    addOrReplace(pericolositaIncendio);

    pericolositaFrane =
        new FdCTextField(
            "pericolositaFrane",
            datiCompleti.bind("civicoZonaRossa.pericolositaFrane"),
            IndirizzoRegistrazioneAllertePanel.this);
    pericolositaFrane.setEnabled(false);
    addOrReplace(pericolositaFrane);

    wmkPericolositaNonSufficiente = new WebMarkupContainer("wmkPericolositaNonSufficiente");

    FdCButtonBootstrapAjaxLink btnVaiACortesia =
        new FdCButtonBootstrapAjaxLink("btnVaiACortesia", Type.Primary) {

          private static final long serialVersionUID = 1L;

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
        Model.of(getString("IndirizzoRegistrazioneAllertePanel.btnVaiACortesia")));

    wmkPericolositaNonSufficiente.add(btnVaiACortesia);

    wmkPericolositaNonSufficiente.setOutputMarkupId(true);
    wmkPericolositaNonSufficiente.setOutputMarkupPlaceholderTag(true);

    add(wmkPericolositaNonSufficiente);
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
                      "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                      IndirizzoRegistrazioneAllertePanel.this);
        }
        datiCompleti.getObject().getCivicoZonaRossa().setPericolositaFrane(pFrane);

        String pIdro = feature.getFeatures().get(0).getProperties().getPERICOLOSIDRO();
        if (pIdro == null || pIdro.equalsIgnoreCase("")) {
          pIdro =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                      IndirizzoRegistrazioneAllertePanel.this);
        }
        datiCompleti.getObject().getCivicoZonaRossa().setPericolositaIdro(pIdro);

        String pInc = feature.getFeatures().get(0).getProperties().getPERICOLOSINC();
        if (pInc == null || pInc.equalsIgnoreCase("")) {
          pInc =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "IndirizzoRegistrazioneAllertePanel.nessunaPericolosita",
                      IndirizzoRegistrazioneAllertePanel.this);
        }
        datiCompleti.getObject().getCivicoZonaRossa().setPericolositaIncendio(pInc);

      } catch (BusinessException | ApiException | IOException e) {
        log.error("CP errore pericolosita" + e.getMessage(), e);
      }
    }
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    avanti.setVisible(
        "ALTA"
            .equalsIgnoreCase(datiCompleti.getObject().getCivicoZonaRossa().getPericolositaIdro()));

    wmkPericolositaNonSufficiente.setVisible(
        !"ALTA"
            .equalsIgnoreCase(datiCompleti.getObject().getCivicoZonaRossa().getPericolositaIdro()));
  }
}
