package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.ubicazioneparcheggio;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.ViarioGeoserverAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class UbicazioneParcheggioPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  // Select2Choice<FeaturesGeoserver> indirizzo;
  private ViarioGeoserverAutoComplete autoCompleteViario;

  TextField<String> descIndirizzo;
  private boolean attivo;

  public UbicazioneParcheggioPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      boolean attivo) {
    super(id);
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    this.attivo = attivo;
    fillDati(null);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void fillDati(Object dati) {

    //		indirizzo = new Select2Choice<FeaturesGeoserver>("indirizzo",
    //				richiestaPermessoPersonalizzatoModel.bind("geoserverUbicazioneParcheggio"),
    //				new FeaturesGeoserverProvider());
    //		indirizzo.setOutputMarkupId(true);
    //		indirizzo.setOutputMarkupPlaceholderTag(true);
    //
    //		addOrReplace(indirizzo);

    // configure various Select2 options
    //		indirizzo.getSettings().setMinimumInputLength(1);
    //		indirizzo.getSettings().setWidth("100%");

    // indirizzo.setEnabled(inserimentoIndirizzoAbilitato());

    // indirizzo.setVisible(attivo);

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
            "indirizzo",
            richiestaPermessoPersonalizzatoModel.bind("geoserverUbicazioneParcheggio"),
            autocompleteRender,
            settings) {};
    autoCompleteViario.setLabel(Model.of("Ubicazione parcheggio"));

    autoCompleteViario.setVisible(attivo);

    addOrReplace(autoCompleteViario);

    descIndirizzo =
        new TextField<>(
            "descIndirizzo",
            richiestaPermessoPersonalizzatoModel.bind(
                "geoserverUbicazioneParcheggio.MACHINE_LAST_UPD"));
    descIndirizzo.setEnabled(false);
    descIndirizzo.setVisible(!attivo);
    addOrReplace(descIndirizzo);

    if (inserimentoIndirizzoAbilitato()) {
      // indirizzo.setModelObject(null);
      autoCompleteViario.setModelObject(null);
    }
  }

  private boolean inserimentoIndirizzoAbilitato() {
    return attivo
        && richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
        && EnumTipoDomandaPermessoPersonalizzato.getById(
                richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
            .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_LAVORO);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    // indirizzo.setEnabled(inserimentoIndirizzoAbilitato());
    autoCompleteViario.setEnabled(inserimentoIndirizzoAbilitato());
  }

  /*
   * private List<FeaturesGeoserver> getFeaturesGeoserver(String input) {
   *
   * log.debug("CP getFeaturesGeoserver = " + input);
   *
   * try { List<FeaturesGeoserver> lista = new ArrayList<FeaturesGeoserver>();
   *
   * List<FeaturesGeoserver> response =
   * ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(input);
   *
   *
   *
   * return lista; } catch (BusinessException | ApiException e) {
   * log.debug("Errore durante la chiamata delle API", e); throw new
   * RestartResponseAtInterceptPageException(ErrorPage.class); } }
   */

}
