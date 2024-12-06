package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungicivico;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione.EsitoInvioDatiAllerteRossePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione.IndirizzoRegistrazioneAllertePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione.PericolositaCivicoRegistrazioneAllertePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class AggiungiCivicoZonaRossaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 8579371953467576008L;

  private int index;

  private StepPanel stepPanel;

  private IndirizzoRegistrazioneAllertePanel indirizzoRegistrazionePanel;

  private PericolositaCivicoRegistrazioneAllertePanel pericolositaCivicoRegistrazioneAllertePanel;

  private EsitoInvioDatiAllerteRossePanel esitoRegistrazionePanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public AggiungiCivicoZonaRossaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    super(id, datiCompleti);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiCompleti);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziAllerteZonaRossa().getListaStepAggiungiCivico());
    form.addOrReplace(stepPanel);

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> dettagliUtente =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa>) dati;
    avanti = creaBottoneAvanti(dettagliUtente);
    form.addOrReplace(avanti);
    form.addOrReplace(creaBottoneIndietro(dettagliUtente));
    form.addOrReplace(creaBottoneAnnulla());

    log.debug("CP fill dati aggiungi civico = " + dettagliUtente);

    indirizzoRegistrazionePanel =
        new IndirizzoRegistrazioneAllertePanel(
            "indirizzoRegistrazionePanel", dettagliUtente, index, avanti);
    indirizzoRegistrazionePanel.setVisible(index == 1);
    form.addOrReplace(indirizzoRegistrazionePanel);

    pericolositaCivicoRegistrazioneAllertePanel =
        new PericolositaCivicoRegistrazioneAllertePanel(
            "pericolositaCivicoRegistrazioneAllertePanel", dettagliUtente, index, avanti);
    pericolositaCivicoRegistrazioneAllertePanel.setVisible(index == 2);
    form.addOrReplace(pericolositaCivicoRegistrazioneAllertePanel);

    esitoRegistrazionePanel = new EsitoInvioDatiAllerteRossePanel("esitoRegistrazionePanel", index);
    esitoRegistrazionePanel.setVisible(index == 4);
    form.addOrReplace(esitoRegistrazionePanel);
  }

  private FdcAjaxButton creaBottoneAvanti(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 4689779827742157891L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            if (index == 4) {

              log.debug("CP creaBottoneAvanti aggiungi civico = " + datiCompleti);

              try {
                if (LabelFdCUtil.checkIfNotNull(datiCompleti)) {
                  Indirizzo indirizzoCreato =
                      ServiceLocator.getInstance()
                          .getServiziAllerteZonaRossa()
                          .aggiungiCivico(datiCompleti.getObject().getCivicoZonaRossa());

                  if (LabelFdCUtil.checkIfNotNull(indirizzoCreato)) {
                    ComponenteNucleoZonaRossa componenteZonaRossa = new ComponenteNucleoZonaRossa();
                    componenteZonaRossa.setIdUtente(
                        datiCompleti.getObject().getCivicoZonaRossa().getIdUtente());
                    componenteZonaRossa.setIdCivico(indirizzoCreato.getId().intValue());
                    componenteZonaRossa.setTipo(
                        datiCompleti.getObject().getDettagliUtenteZonaRossa().getTipoPersona());

                    ServiceLocator.getInstance()
                        .getServiziAllerteZonaRossa()
                        .aggiungiComponenteCivico(componenteZonaRossa);
                  }
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

                error("Errore aggiungi civico");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(AggiungiCivicoZonaRossaPanel.this);

            log.error("CP errore aggiungi civico allerta cortesia ");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiCivicoZonaRossaPanel.avanti", AggiungiCivicoZonaRossaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 4725095535564124330L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(AggiungiCivicoZonaRossaPanel.this);

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
                    "AggiungiCivicoZonaRossaPanel.annulla", AggiungiCivicoZonaRossaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 3716759002176199811L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // target.add(DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this);

            index = index - 1;

            getTargetPanel(target);

            // setResponsePage(new RegistrazioneAllertePage(datiCompleti, index));
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiCivicoZonaRossaPanel.indietro", AggiungiCivicoZonaRossaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    log.debug("CP getTargetPanel = " + index);

    if (index == 1) {
      indirizzoRegistrazionePanel.setVisible(true);
      esitoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(false);

      avanti.setVisible(true);
      indietro.setVisible(false);

      target.add(this);

    } else if (index == 2) {
      indirizzoRegistrazionePanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(true);

      avanti.setVisible(true);
      indietro.setVisible(true);

      target.add(this);
    } else if (index == 3) {
      indirizzoRegistrazionePanel.setVisible(true);
      indirizzoRegistrazionePanel.setEnabled(false);

      pericolositaCivicoRegistrazioneAllertePanel.setVisible(true);
      pericolositaCivicoRegistrazioneAllertePanel.setEnabled(false);

      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(true);

      target.add(this);
    } else if (index == 4) {
      indirizzoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(this);
    }
    return target;
  }
}
