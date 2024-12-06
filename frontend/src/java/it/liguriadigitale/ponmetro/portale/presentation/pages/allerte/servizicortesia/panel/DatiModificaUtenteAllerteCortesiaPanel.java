package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

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
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class DatiModificaUtenteAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -2917020422477540260L;

  private int index;
  private StepPanel stepPanel;

  private ModificaUtenteAllerteCortesiaPanel modificaDatiServiziCortesiaPanel;
  private EsitoModificaServiziCortesiaPanel esitoModificaDatiServiziCortesiaPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public DatiModificaUtenteAllerteCortesiaPanel(
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

    modificaDatiServiziCortesiaPanel =
        new ModificaUtenteAllerteCortesiaPanel(
            "modificaDatiServiziCortesiaPanel", dettagliUtente, index);
    form.addOrReplace(modificaDatiServiziCortesiaPanel);

    esitoModificaDatiServiziCortesiaPanel =
        new EsitoModificaServiziCortesiaPanel(
            "esitoModificaDatiServiziCortesiaPanel", dettagliUtente, index);
    esitoModificaDatiServiziCortesiaPanel.setVisible(false);
    form.addOrReplace(esitoModificaDatiServiziCortesiaPanel);

    form.addOrReplace(creaBottoneAvanti(dettagliUtente));
    form.addOrReplace(creaBottoneIndietro(dettagliUtente));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    avanti =
        new FdcAjaxButton("avanti") {

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP btn avanti = " + datiCompleti.getObject());

            index = index + 1;

            if (index == 3
                && LabelFdCUtil.checkIfNotNull(datiCompleti.getObject())
                && LabelFdCUtil.checkIfNotNull(
                    datiCompleti.getObject().getDatiRegistrazioneAllerteCortesia())) {
              try {
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putwsUpdateUtente(datiCompleti.getObject());

              } catch (BusinessException | ApiException | IOException e) {

                log.debug("CP errore durante modifica dati cortesia: " + e.getMessage(), e);

                index = index - 1;

                error("Errore durante modifica dati");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DatiModificaUtenteAllerteCortesiaPanel.this);

            log.error("CP errore modifica dati allerta cortesia");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiModificaUtenteAllerteCortesiaPanel.avanti",
                    DatiModificaUtenteAllerteCortesiaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiModificaUtenteAllerteCortesiaPanel.this);

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
                    "DatiModificaUtenteAllerteCortesiaPanel.annulla",
                    DatiModificaUtenteAllerteCortesiaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

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
                    "DatiModificaUtenteAllerteCortesiaPanel.indietro",
                    DatiModificaUtenteAllerteCortesiaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {

    log.debug("CP getTargetPanel = " + index);
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {
      modificaDatiServiziCortesiaPanel.setVisible(true);
      modificaDatiServiziCortesiaPanel.setEnabled(true);
      esitoModificaDatiServiziCortesiaPanel.setVisible(false);
      target.add(modificaDatiServiziCortesiaPanel, esitoModificaDatiServiziCortesiaPanel);
      indietro.setVisible(false);

    } else if (index == 2) {

      modificaDatiServiziCortesiaPanel.setVisible(true);
      indietro.setVisible(true);
      esitoModificaDatiServiziCortesiaPanel.setVisible(false);
      modificaDatiServiziCortesiaPanel.setEnabled(false);
      target.add(modificaDatiServiziCortesiaPanel, esitoModificaDatiServiziCortesiaPanel);

    } else if (index == 3) {

      target.add(modificaDatiServiziCortesiaPanel, esitoModificaDatiServiziCortesiaPanel);

      modificaDatiServiziCortesiaPanel.setVisible(false);
      esitoModificaDatiServiziCortesiaPanel.setVisible(true);
      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);
    }

    return target;
  }
}
