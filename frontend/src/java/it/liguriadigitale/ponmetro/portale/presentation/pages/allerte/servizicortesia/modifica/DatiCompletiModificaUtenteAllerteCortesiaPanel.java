package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.modifica;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class DatiCompletiModificaUtenteAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -5104707682493429055L;

  private int index;
  private StepPanel stepPanel;
  private ModificaUtenteServiziCortesiaPanel modificaUtenteServiziCortesiaPanel;
  private EsitoModificaUtenteServiziCortesiaPanel esitoModificaUtenteServiziCortesiaPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public DatiCompletiModificaUtenteAllerteCortesiaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    super(id, datiCompleti);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiCompleti);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziAllerteCortesia().getListaStep());
    form.addOrReplace(stepPanel);

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> dettagliUtente =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia>) dati;

    modificaUtenteServiziCortesiaPanel =
        new ModificaUtenteServiziCortesiaPanel(
            "modificaUtenteServiziCortesiaPanel", dettagliUtente, index);
    form.addOrReplace(modificaUtenteServiziCortesiaPanel);

    esitoModificaUtenteServiziCortesiaPanel =
        new EsitoModificaUtenteServiziCortesiaPanel(
            "esitoModificaUtenteServiziCortesiaPanel", dettagliUtente, index);
    esitoModificaUtenteServiziCortesiaPanel.setVisible(false);
    form.addOrReplace(esitoModificaUtenteServiziCortesiaPanel);

    form.addOrReplace(creaBottoneAvanti(dettagliUtente));
    form.addOrReplace(creaBottoneIndietro(dettagliUtente));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 8271899623570526107L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP btn avanti = " + datiCompleti.getObject());

            index = index + 1;

            if (index == 3) {
              try {
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putwsUpdateUtente(datiCompleti.getObject());
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
                String messaggioDaVisualizzare =
                    messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

                error(messaggioDaVisualizzare);

              } catch (IOException | BusinessException e) {

                index = index - 1;
                error("Errore di sistema.");
                log.error("BusinessException gestito durante la chiamata delle API:", e);
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DatiCompletiModificaUtenteAllerteCortesiaPanel.this);

            log.error("CP errore registrazione allerta cortesia");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiCompletiModificaUtenteAllerteCortesiaPanel.avanti",
                    DatiCompletiModificaUtenteAllerteCortesiaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 9009168084240184131L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiCompletiModificaUtenteAllerteCortesiaPanel.this);

            form.clearInput();

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiCompletiModificaUtenteAllerteCortesiaPanel.annulla",
                    DatiCompletiModificaUtenteAllerteCortesiaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = -5818559099013356465L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // target.add(DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this);

            log.debug("CP pannellone creaBottoneIndietro prima " + index);

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
                    "DatiCompletiModificaUtenteAllerteCortesiaPanel.indietro",
                    DatiCompletiModificaUtenteAllerteCortesiaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {

    log.debug("CP getTargetPanel = " + index);
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {
      modificaUtenteServiziCortesiaPanel.setVisible(true);
      modificaUtenteServiziCortesiaPanel.setEnabled(true);
      esitoModificaUtenteServiziCortesiaPanel.setVisible(false);
      target.add(modificaUtenteServiziCortesiaPanel, esitoModificaUtenteServiziCortesiaPanel);
      indietro.setVisible(false);

    } else if (index == 2) {

      modificaUtenteServiziCortesiaPanel.setVisible(true);
      indietro.setVisible(true);
      esitoModificaUtenteServiziCortesiaPanel.setVisible(false);
      modificaUtenteServiziCortesiaPanel.setEnabled(false);
      target.add(modificaUtenteServiziCortesiaPanel, esitoModificaUtenteServiziCortesiaPanel);

    } else if (index == 3) {

      target.add(modificaUtenteServiziCortesiaPanel, esitoModificaUtenteServiziCortesiaPanel);

      modificaUtenteServiziCortesiaPanel.setVisible(false);
      esitoModificaUtenteServiziCortesiaPanel.setVisible(true);
      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);
    }

    return target;
  }
}
