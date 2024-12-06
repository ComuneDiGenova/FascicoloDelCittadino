package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungicomponente;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione.EsitoInvioDatiAllerteRossePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class AggiungiComponenteZonaRossaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -1985327615824030146L;

  private int index;

  private StepPanel stepPanel;

  private DatiPersonaPanel datiPersonaPanel;

  private EsitoInvioDatiAllerteRossePanel esitoRegistrazionePanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public AggiungiComponenteZonaRossaPanel(String id, ComponenteNucleoZonaRossa componente) {
    super(id, componente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(componente);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance()
                .getServiziAllerteZonaRossa()
                .getListaStepAggiungiComponente());
    form.addOrReplace(stepPanel);

    ComponenteNucleoZonaRossa componente = (ComponenteNucleoZonaRossa) dati;

    datiPersonaPanel = new DatiPersonaPanel("datiPersonaPanel", componente, index);
    datiPersonaPanel.setVisible(index == 1);
    form.addOrReplace(datiPersonaPanel);

    esitoRegistrazionePanel = new EsitoInvioDatiAllerteRossePanel("esitoRegistrazionePanel", index);
    esitoRegistrazionePanel.setVisible(index == 3);
    form.addOrReplace(esitoRegistrazionePanel);

    form.addOrReplace(creaBottoneAvanti(componente));
    form.addOrReplace(creaBottoneIndietro(componente));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(ComponenteNucleoZonaRossa componente) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -4001019716345346938L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP creaBottoneAvanti aggiungi componente ");

            index = index + 1;

            if (index == 3) {

              try {
                if (LabelFdCUtil.checkIfNotNull(componente)) {
                  ServiceLocator.getInstance()
                      .getServiziAllerteZonaRossa()
                      .aggiungiComponente(componente);
                }
              } catch (ApiException e) {

                index = index - 1;

                String myMessage = e.getMyMessage();
                String eccezione = "javax.ws.rs.WebApplicationException:";
                String messaggioRicevuto = myMessage;
                if (myMessage.contains(eccezione)) {
                  messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
                } else {
                  messaggioRicevuto =
                      "Servizio momentaneamente non disponibile, riprovare più tardi";
                }
                log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

                Integer indexOf = messaggioRicevuto.indexOf(":");

                log.debug("CP indexOf = " + indexOf);

                if (messaggioRicevuto.contains(": null")) {
                  messaggioRicevuto = messaggioRicevuto.replace(": null", "");
                }

                log.debug("CP messaggioRicevuto = " + messaggioRicevuto);

                error(messaggioRicevuto);

              } catch (IOException | BusinessException e) {
                log.error("BusinessException gestito durante la chiamata delle API:", e);

                error("Errore aggiungi componente");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(AggiungiComponenteZonaRossaPanel.this);

            log.error("CP errore aggiungi coponente allerta cortesia ");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiComponenteZonaRossaPanel.avanti",
                    AggiungiComponenteZonaRossaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 4410114318999271960L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(AggiungiComponenteZonaRossaPanel.this);

            form.clearInput();

            setResponsePage(new AllertePage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiComponenteZonaRossaPanel.annulla",
                    AggiungiComponenteZonaRossaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      ComponenteNucleoZonaRossa componente) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 442199658228857221L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiComponenteZonaRossaPanel.indietro",
                    AggiungiComponenteZonaRossaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    log.debug("CP getTargetPanel = " + index);

    if (index == 1) {
      datiPersonaPanel.setVisible(true);
      datiPersonaPanel.setEnabled(true);
      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(false);

      target.add(datiPersonaPanel, esitoRegistrazionePanel);

    } else if (index == 2) {
      datiPersonaPanel.setVisible(true);
      datiPersonaPanel.setEnabled(false);

      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(true);

      target.add(datiPersonaPanel, esitoRegistrazionePanel);
    } else if (index == 3) {
      datiPersonaPanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(datiPersonaPanel, esitoRegistrazionePanel);
    }
    return target;
  }
}
