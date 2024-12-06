package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("rawtypes")
public class EsitoPanel extends BasePanel {

  private static final long serialVersionUID = 4853065343502194027L;

  private Integer index;

  private VariazioniResidenza variazione;

  private FeedbackPanel feedbackPanel;

  private boolean esito;

  private WebMarkupContainer containerPericolosita;

  private FeaturesGeoserver geoServerFeature;

  private IModel<String> modelOfPericolositaIdrualica = new Model<String>();

  private String pericolositaAlta = "ALTA";

  public EsitoPanel(String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.setVariazione(variazioniResidenza);

    fillDati(variazioniResidenza);

    createFeedBackStep6Panel(index);
  }

  public EsitoPanel(
      String id, Integer index, VariazioniResidenza variazioniResidenza, boolean esito) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.setVariazione(variazioniResidenza);
    this.esito = esito;

    fillDati(variazioniResidenza);

    createFeedBackStep6Panel(index);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    if (index == 6) {
      if (esito) {
        String id = String.valueOf(variazioniResidenza.getIdPratica());
        String messaggio =
            "Pratica".concat(" ").concat(id).concat(" ").concat("inviata correttamente");
        success(messaggio);

        log.debug(
            "CP variazioni residenza indirizzo = " + variazioniResidenza.getAutocompleteViario());

        containerPericolosita = new WebMarkupContainer("containerPericolosita");
        containerPericolosita.setOutputMarkupId(true);
        containerPericolosita.setOutputMarkupPlaceholderTag(true);

        checkPericolositaNuovoIndirizzo(variazioniResidenza.getAutocompleteViario());

        FdCButtonBootstrapAjaxLink btnVaiAZonaRossa =
            btnZonaRossa(variazioniResidenza.getAutocompleteViario());
        containerPericolosita.add(btnVaiAZonaRossa);

        if (LabelFdCUtil.checkIfNotNull(modelOfPericolositaIdrualica)) {
          if (modelOfPericolositaIdrualica.getObject().equalsIgnoreCase(pericolositaAlta)) {
            containerPericolosita.setVisible(true);
          } else {
            containerPericolosita.setVisible(false);
          }
        }

        addOrReplace(containerPericolosita);

      } else {
        error("Errore durante invio pratica");
      }
    }
  }

  public VariazioniResidenza getVariazione() {
    return variazione;
  }

  public void setVariazione(VariazioniResidenza variazione) {
    this.variazione = variazione;
  }

  public FeedbackPanel getFeedbackPanel() {
    return feedbackPanel;
  }

  public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
    this.feedbackPanel = feedbackPanel;
  }

  protected FeedbackPanel createFeedBackStep6Panel(Integer index) {

    NotificationPanel feedback =
        new NotificationPanel("feedback6") {

          private static final long serialVersionUID = 5422405580694686803L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    feedback.setVisible(index == 6);
    this.add(feedback);
    return feedback;
  }

  private void checkPericolositaNuovoIndirizzo(FeaturesGeoserver datiToponomyNuovoIndirizzo) {
    FeaturesGeoserver featuresGeoserver = (FeaturesGeoserver) datiToponomyNuovoIndirizzo;

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

        String pIdro = feature.getFeatures().get(0).getProperties().getPERICOLOSIDRO();
        if (pIdro == null || pIdro.equalsIgnoreCase("")) {
          pIdro =
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString("EsitoPanel.nessunaPericolosita", EsitoPanel.this);
        }

        modelOfPericolositaIdrualica.setObject(pIdro);

      } catch (BusinessException | ApiException | IOException e) {
        log.error("CP errore checkPericolositaNuovoIndirizzo: " + e.getMessage(), e);
      }
    }
  }

  @SuppressWarnings({"unchecked"})
  private FdCButtonBootstrapAjaxLink btnZonaRossa(FeaturesGeoserver featuresGeoserver) {
    FdCButtonBootstrapAjaxLink btnVaiAZonaRossa =
        new FdCButtonBootstrapAjaxLink("btnVaiAZonaRossa", Type.Primary) {

          private static final long serialVersionUID = -5059405048288891880L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new AllertePage(featuresGeoserver));
          }
        };

    IconType icon =
        new IconType("btnVaiAZonaRossa") {

          private static final long serialVersionUID = 6679979527507064717L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnVaiAZonaRossa.setIconType(icon);

    btnVaiAZonaRossa.setLabel(Model.of(getString("EsitoPanel.btnVaiAZonaRossa")));
    return btnVaiAZonaRossa;
  }
}
