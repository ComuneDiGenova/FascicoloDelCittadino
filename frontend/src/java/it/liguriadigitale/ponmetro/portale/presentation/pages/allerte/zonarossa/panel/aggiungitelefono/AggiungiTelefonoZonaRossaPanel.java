package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungitelefono;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
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

public class AggiungiTelefonoZonaRossaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -8370015359955840082L;

  private int index;

  private StepPanel stepPanel;

  private DatiTelefonoPanel datiTelefonoPanel;

  private EsitoInvioDatiAllerteRossePanel esitoRegistrazionePanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public AggiungiTelefonoZonaRossaPanel(String id, ContattoTelefonicoZonaRossa contattoTelefonico) {
    super(id, contattoTelefonico);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(contattoTelefonico);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    ContattoTelefonicoZonaRossa contattoTelefonico = (ContattoTelefonicoZonaRossa) dati;

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance()
                .getServiziAllerteZonaRossa()
                .getListaStepAggiungiTelefono());
    form.addOrReplace(stepPanel);

    datiTelefonoPanel = new DatiTelefonoPanel("datiTelefonoPanel", contattoTelefonico, index);
    datiTelefonoPanel.setVisible(index == 1);
    form.addOrReplace(datiTelefonoPanel);

    esitoRegistrazionePanel = new EsitoInvioDatiAllerteRossePanel("esitoPanel", index);
    esitoRegistrazionePanel.setVisible(index == 5);
    form.addOrReplace(esitoRegistrazionePanel);

    form.addOrReplace(creaBottoneAvanti(contattoTelefonico));
    form.addOrReplace(creaBottoneIndietro(contattoTelefonico));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    log.debug("CP getTargetPanel = " + index);

    if (index == 1) {
      datiTelefonoPanel.setVisible(true);
      datiTelefonoPanel.setEnabled(true);
      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(false);

      target.add(datiTelefonoPanel, esitoRegistrazionePanel);

    } else if (index == 2) {
      datiTelefonoPanel.setVisible(true);
      datiTelefonoPanel.setEnabled(false);
      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(true);

      target.add(datiTelefonoPanel, esitoRegistrazionePanel);

    } else if (index == 3) {
      datiTelefonoPanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(datiTelefonoPanel, esitoRegistrazionePanel);
    }

    return target;
  }

  private FdcAjaxButton creaBottoneAvanti(ContattoTelefonicoZonaRossa contattoTelefonico) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -8876032590961196225L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP creaBottoneAvanti aggiungi tel");

            index = index + 1;

            if (index == 3) {

              try {
                if (LabelFdCUtil.checkIfNotNull(contattoTelefonico)) {
                  ServiceLocator.getInstance()
                      .getServiziAllerteZonaRossa()
                      .aggiungiTelefono(contattoTelefonico);
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

                error("Errore cancellazione telefono");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(AggiungiTelefonoZonaRossaPanel.this);

            log.error("CP errore aggiungi tel allerta cortesia ");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AggiungiTelefonoZonaRossaPanel.avanti", AggiungiTelefonoZonaRossaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -4899716592826947793L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(AggiungiTelefonoZonaRossaPanel.this);

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
                    "AggiungiTelefonoZonaRossaPanel.annulla",
                    AggiungiTelefonoZonaRossaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      ContattoTelefonicoZonaRossa contattoTelefonico) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 7471333218422061468L;

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
                    "AggiungiTelefonoZonaRossaPanel.indietro",
                    AggiungiTelefonoZonaRossaPanel.this)));

    return indietro;
  }
}
