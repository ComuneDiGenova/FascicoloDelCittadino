package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.configurazione.WidgetSelezionati;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget.form.SelezioneWidgetForm;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class ScegliWidgetHomePagePanel extends BasePanel {

  private static final long serialVersionUID = 1353388338024413609L;

  private DatiSezione datiSezione;

  public ScegliWidgetHomePagePanel(
      String id, DatiSezione datiSezione, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    super(id);
    setOutputMarkupId(true);
    this.datiSezione = datiSezione;
    log.debug("Sezione:" + datiSezione.getDenominazioneSez());
    add(new Label("testoTitoloCard", datiSezione.getDescrizioneSez()));
    fillDati(listaWidget);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DatiVisualizzazioneSezioneWidget> listaWidget =
        (List<DatiVisualizzazioneSezioneWidget>) dati;
    WidgetSelezionati selezioneAttuale = impostaDefaultCombo(listaWidget);
    SelezioneWidgetForm form = new SelezioneWidgetForm("form", selezioneAttuale, listaWidget);
    form.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
    form.add(creaBottoneSalva());
    add(form);
    createFeedBackPanel();
  }

  private LaddaAjaxButton creaBottoneSalva() {

    return new LaddaAjaxButton(
        "button", Model.of(getString("MostraWidgetPanel.save")), Type.Primary) {

      private static final long serialVersionUID = 2437468894881345638L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        target.add(ScegliWidgetHomePagePanel.this);
        log.debug("Salva:" + getModelObject());
        try {
          ServiceLocator.getInstance()
              .getServiziConfigurazione()
              .updateWidget(getUtente(), (WidgetSelezionati) getForm().getModelObject());
          TimeUnit.SECONDS.sleep(1);
          Utente.inizializzaWidgetDaVisualizzare(getUtente());
          success("Modifica effettuata");
        } catch (BusinessException | InterruptedException e) {
          log.error("Errore:", e);
          error("Impossibile salvare la modifica");
        }
      }
    };
  }

  private WidgetSelezionati impostaDefaultCombo(
      List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    WidgetSelezionati selezioneAttuale = new WidgetSelezionati(datiSezione);
    DatiVisualizzazioneSezioneWidget widgetTop = getDenominazioneWidgetByPosizione(0, listaWidget);
    DatiVisualizzazioneSezioneWidget widgetBottom =
        getDenominazioneWidgetByPosizione(1, listaWidget);
    selezioneAttuale.setWidgetTop(widgetTop);
    selezioneAttuale.setWidgetTopStatoIniziale(widgetTop);
    selezioneAttuale.setWidgetBottomStatoIniziale(widgetBottom);
    selezioneAttuale.setWidgetBottom(widgetBottom);
    return selezioneAttuale;
  }

  private DatiVisualizzazioneSezioneWidget getDenominazioneWidgetByPosizione(
      int posizione, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    if (listaWidget.size() > posizione && posizione > -1) {
      return listaWidget.get(posizione);
    } else {
      return new DatiVisualizzazioneSezioneWidget();
    }
  }
}
