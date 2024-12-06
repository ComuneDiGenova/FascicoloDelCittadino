package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AggiungiFiglio;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.BaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.form.AggiungiFiglioForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.CodiceFiscaleUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class AggiungiFiglioPanel extends BasePanel {

  private static final long serialVersionUID = -5821985565593872082L;

  private AggiungiFiglioForm aggiungiFiglioForm = null;
  private AggiungiFiglio datiFiglio = new AggiungiFiglio();
  private HashMap<String, Boolean> mappaMinori = new HashMap<String, Boolean>();
  private WebMarkupContainer btnSalva;

  public AggiungiFiglioPanel(String id) {
    super(id);

    fillDati("");
    createFeedBackPanel();
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    addOrReplace(aggiungiForm());
  }

  private AbstracFrameworkForm<AggiungiFiglio> aggiungiForm() {

    aggiungiFiglioForm = new AggiungiFiglioForm("form", datiFiglio, getUtente());
    aggiungiFiglioForm.addOrReplace(creaBottoneAnnulla());
    aggiungiFiglioForm.addOrReplace(creaBottoneAggiungi());
    aggiungiFiglioForm.addOrReplace(creaToggle());
    return aggiungiFiglioForm;
  }

  private LaddaAjaxButton creaBtnAggiungiFiglio(AggiungiFiglio datiFiglio) {
    LaddaAjaxButton aggiungi =
        new LaddaAjaxButton("aggiungi", Type.Primary) {

          private static final long serialVersionUID = 2325141650432664811L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(AggiungiFiglioPanel.this);

            try {

              log.debug("CP datiFiglio prima = " + datiFiglio);

              if (PageUtil.isStringValid(datiFiglio.getCodiceFiscale())) {
                datiFiglio.setCodiceFiscale(datiFiglio.getCodiceFiscale().toUpperCase());
              }

              if (PageUtil.isStringValid(datiFiglio.getNome())) {
                datiFiglio.setNome(datiFiglio.getNome().toUpperCase());
              }

              if (PageUtil.isStringValid(datiFiglio.getCognome())) {
                datiFiglio.setCognome(datiFiglio.getCognome().toUpperCase());
              }

              log.debug("CP datiFiglio dopo = " + datiFiglio);

              CodiceFiscaleUtil cfUtil = new CodiceFiscaleUtil();

              String codiceFiscaleCalcolato =
                  cfUtil.generaCodiceFiscale(
                      datiFiglio.getNome(), datiFiglio.getCognome(), 1, 1, 2001, 'm', "xxxx");
              String nomeCognome = codiceFiscaleCalcolato.substring(0, 6);
              log.debug("CP nome cognome = " + nomeCognome);

              if (!nomeCognome.equalsIgnoreCase(datiFiglio.getCodiceFiscale().substring(0, 6))) {
                AggiungiFiglioPanel.this.error(
                    "Attenzione, nome e cognome non coerenti con il codice fiscale inserito.");
              } else {
                datiFiglio.setIdPerson(null);
                ServiceLocator.getInstance()
                    .getServiziConfigurazione()
                    .insertMinoreConviventePerDichiarazioneGenitoreNonResidente(
                        datiFiglio, getUtente());
                TimeUnit.SECONDS.sleep(1);
                getUtente().aggiornaStatoFigli();
                log.debug("Modifiche salvate");

                setResponsePage(BaseLandingPage.class);
              }

            } catch (BusinessException | InterruptedException e) {
              AggiungiFiglioPanel.this.error("Errore durante il salvataggio");
              log.error("Errore:", e);
            } catch (Exception e) {
              AggiungiFiglioPanel.this.error("Attenzione, autodichiarazione già esistente.");
              log.error("Errore genera cf:", e);
            }

            updateMappa(datiFiglio);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AggiungiFiglioPanel.this);
          }
        };
    aggiungi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AggiungiFiglioPanel.salva", AggiungiFiglioPanel.this)));

    return aggiungi;
  }

  private LaddaAjaxLink<Void> creaBottoneAnnulla() {
    LaddaAjaxLink<Void> btnAnnulla =
        new LaddaAjaxLink<Void>("annulla", Type.Primary) {

          private static final long serialVersionUID = 7423033033645572794L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            aggiungiFiglioForm.clearInput();

            datiFiglio = new AggiungiFiglio();

            target.add(AggiungiFiglioPanel.this);
          }
        };

    btnAnnulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AggiungiFiglioPanel.annulla", AggiungiFiglioPanel.this)));

    btnAnnulla.setOutputMarkupId(true);
    btnAnnulla.setOutputMarkupPlaceholderTag(true);

    return btnAnnulla;
  }

  private WebMarkupContainer creaBottoneAggiungi() {

    btnSalva = new WebMarkupContainer("salva");

    btnSalva.setOutputMarkupId(true);
    btnSalva.setOutputMarkupPlaceholderTag(true);
    btnSalva.setVisible(false);

    btnSalva.addOrReplace(creaBtnAggiungiFiglio(datiFiglio));

    return btnSalva;
  }

  private void updateMappa(AggiungiFiglio minore) {
    log.debug("minore:" + minore.getCodiceFiscale());
    mappaMinori.put(minore.getCodiceFiscale(), false);
  }

  private AjaxCheckBox creaToggle() {

    Boolean accettato = false;
    AjaxCheckBox check =
        new AjaxCheckBox("isCoresidente", new Model<Boolean>(accettato)) {

          private static final long serialVersionUID = 9191900306595442761L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            String valoreSelezionato = getDefaultModelObjectAsString();
            log.debug("[AggiungiFiglioPanel] The selected value is " + valoreSelezionato);
            btnSalva.setVisible(getModelObject());
            target.add(btnSalva);
          }
        };
    return (check);
  }
}
