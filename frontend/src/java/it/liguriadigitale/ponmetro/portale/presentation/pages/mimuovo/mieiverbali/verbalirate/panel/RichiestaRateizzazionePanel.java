package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RichiestaRateizzazionePanel extends FdcCardFormPanel {

  private enum Step {
    Home,
    Informativa,
    DatiPersonali,
    DatiIsee,
    Riepilogo,
    Esito
  };

  private FdCButtonBootstrapAjaxLink<Object> _annulla;
  private FdcAjaxButton _avanti;
  private FdcAjaxButton _indietro;
  private FdcAjaxButton _invia;
  private FdcAjaxButton _completa;

  private StepPanel _stepPanel;
  private int _index = 1;

  WebMarkupContainer _containerStep1;
  WebMarkupContainer _containerStep2;
  WebMarkupContainer _containerStep3;
  WebMarkupContainer _proseguiInfo;
  WebMarkupContainer containerPrivacy;

  WebMarkupContainer containerInfopagina;
  DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;

  private ConsultazioneAttestazioneCF200 attestazioni;

  private boolean daCompletare = false;

  public RichiestaRateizzazionePanel(
      String id, CompoundPropertyModel<DatiRichiestaIstanzaPl> datiRichiestaIstanzaPl) {
    super(id, datiRichiestaIstanzaPl);
    // TODO Auto-generated constructor stub

    _stepPanel =
        new StepPanel(
            "stepPanel", 1, ServiceLocator.getInstance().getServiziMieiVerbali().getListaStep());
    _index = 1;

    fillDati(datiRichiestaIstanzaPl);
  }

  public RichiestaRateizzazionePanel(
      String id, CompoundPropertyModel<DatiRichiestaIstanzaPl> datiRichiestaIstanzaPl, int step) {
    // TODO Auto-generated constructor stub
    super(id, datiRichiestaIstanzaPl);

    _index = step;
    _stepPanel =
        new StepPanel(
            "stepPanel", step, ServiceLocator.getInstance().getServiziMieiVerbali().getListaStep());
    daCompletare = true;

    fillDati(datiRichiestaIstanzaPl);
  }

  private static final long serialVersionUID = -5104707682493429070L;

  @Override
  public void fillDati(Object dati) {

    super.fillDati(dati);

    containerInfopagina = new WebMarkupContainer("containerInfopagina");
    form.addOrReplace(containerInfopagina);

    @SuppressWarnings("unchecked")
    CompoundPropertyModel<DatiRichiestaIstanzaPl> cDatiRichiestaIstanzaPl =
        (CompoundPropertyModel<DatiRichiestaIstanzaPl>) dati;
    this.datiRichiestaIstanzaPl = cDatiRichiestaIstanzaPl.getObject();

    DettaglioVerbale dettaglioVerbale =
        cDatiRichiestaIstanzaPl.getObject().getDettaglioVerbaleDiPartenza();

    _containerStep1 = new WebMarkupContainer("containerStep1");

    DatiPersonaliPanel datiPersonaliPanel =
        new DatiPersonaliPanel("datiPersonaliPanel", cDatiRichiestaIstanzaPl);
    _proseguiInfo = new WebMarkupContainer("proseguiInfo");

    form.addOrReplace(_stepPanel);
    _containerStep1.addOrReplace(datiPersonaliPanel);
    _containerStep1.addOrReplace(createFdCTextField(dettaglioVerbale));
    _containerStep1.addOrReplace(_proseguiInfo);
    _containerStep1.setVisible(true);

    form.addOrReplace(_containerStep1);

    _containerStep2 = new WebMarkupContainer("containerStep2");
    attestazioni = getAttestazioniISEE();

    DatiIseePanel datiIseePanel =
        new DatiIseePanel("datiIseePanel", attestazioni, cDatiRichiestaIstanzaPl.getObject());

    _containerStep2.addOrReplace(datiIseePanel);
    _containerStep2.setVisible(false);
    form.addOrReplace(_containerStep2);

    _containerStep3 = new WebMarkupContainer("containerStep3");
    _containerStep3.setVisible(false);
    form.addOrReplace(_containerStep3);

    form.addOrReplace(creaBottoneIndietro(dettaglioVerbale));
    form.addOrReplace(creaBottoneAvanti(cDatiRichiestaIstanzaPl.getObject()));
    form.addOrReplace(creaBottoneAnnulla(dettaglioVerbale));
    form.addOrReplace(creaBottoneInvia(cDatiRichiestaIstanzaPl.getObject()));
    form.addOrReplace(creaButtoneCompleta(cDatiRichiestaIstanzaPl.getObject()));

    _indietro.setVisible(false);
    _invia.setVisible(false);
    _completa.setVisible(false);

    containerPrivacy = new WebMarkupContainer("containerPrivacy");
    InformativaPanel info =
        new InformativaPanel("informativaPanel", cDatiRichiestaIstanzaPl.getObject());
    containerPrivacy.addOrReplace(info);

    form.addOrReplace(containerPrivacy);

    if (daCompletare) {
      stepDatiIsee();
    } else {
      stepInformativa();
    }
  }

  private FdCTextField<Component> createFdCTextField(DettaglioVerbale dettaglioVerbale) {

    FdCTextField<Component> field =
        new FdCTextField<Component>(
            "numeroVerbale",
            new PropertyModel<String>(dettaglioVerbale, "numeroAvviso"),
            RichiestaRateizzazionePanel.this);
    field.setEnabled(false);
    field.setRequired(false);

    return field;
  }

  private FdcAjaxButton creaBottoneInvia(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    _invia =
        new FdcAjaxButton("invia") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)) {
              try {
                log.debug("[creaBottoneInvia] Inserimento Istanza ");

                log.debug("[creaBottoneInvia] Utente: " + datiRichiestaIstanzaPl.getUtente());

                EsitoOperazione esito =
                    ServiceLocator.getInstance()
                        .getServiziMieiVerbali()
                        .inserisciConcludiIstanza(datiRichiestaIstanzaPl);

                if (LabelFdCUtil.checkIfNotNull(esito) && esito.getEsito().equalsIgnoreCase("OK")) {

                  datiRichiestaIstanzaPl.setEsitoInvioIstanza(esito);
                  _index = _index + 1;

                } else {
                  log.debug(
                      "[EsitoInserisciConludi] Esito Istanza: "
                          + esito.getEsito()
                          + " "
                          + esito.getDescrizione());
                  error("Errore durante la richiesta di inserimento ISTANZA");
                }

              } catch (ApiException | BusinessException | IOException e) {
                log.error("FRR errore post inserimento pratica: " + e.getMessage());
                log.error("FRR errore post inserimento pratica STACKK : ", e);
                error("Errore durante la richiesta di inserimento ISTANZA");
              } catch (Exception e) {
                log.error("FRR errore post inserimento pratica: " + e.getMessage());
                log.error("FRR errore post inserimento pratica STACKK : ", e);
                error("Errore durante la richiesta di inserimento ISTANZA");
              }

            } else {
              error("Dati nulli, riprovare o contattare l'amministratore di sistema");
            }

            getTargetPanel(target, datiRichiestaIstanzaPl);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRateizzazionePanel.this);

            log.error("[FdcAjaxButton - creaBottoneAvanti] Error Target");
          }
        };

    _invia.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRateizzazionePanel.invia", RichiestaRateizzazionePanel.this)));

    return _invia;
  }

  private FdcAjaxButton creaButtoneCompleta(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    _completa =
        new FdcAjaxButton("completa") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug(
                "[CompletaIstanzaRateizzazione] Completa istanza invia Dati ISEE "
                    + datiRichiestaIstanzaPl);
            log.debug(
                "[CompletaIstanzaRateizzazione] Dati ISEE " + datiRichiestaIstanzaPl.getDatiISEE());

            if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)) {

              Istanza istanza = datiRichiestaIstanzaPl.getIstanza();
              DatiISEE datiISEE = datiRichiestaIstanzaPl.getDatiISEE();

              if (LabelFdCUtil.checkIfNull(istanza) || LabelFdCUtil.checkIfNull(datiISEE)) {
                log.debug("[creaButtoneCompleta] Istana o dati ISEE Null");
                log.debug("[creaButtoneCompleta] Istanza: " + istanza);
                log.debug("[creaButtoneCompleta] Dati ISEE: " + datiISEE);
                error("Errore generico durante l'invio dell'ISEE");
                return;
              }

              try {
                EsitoOperazione esitoOperazione =
                    ServiceLocator.getInstance()
                        .getServiziMieiVerbali()
                        .inserimentoISEE(
                            datiRichiestaIstanzaPl
                                .getDettaglioVerbaleDiPartenza()
                                .getNumeroProtocollo(),
                            istanza.getId().toString(),
                            istanza.getAnno().intValue(),
                            datiISEE);

                if (LabelFdCUtil.checkIfNotNull(esitoOperazione)
                    && esitoOperazione.getEsito().equalsIgnoreCase("OK")) {

                  datiRichiestaIstanzaPl.setEsitoInvioIstanza(esitoOperazione);

                  _index = _index + 1;
                }

              } catch (BusinessException | ApiException | IOException e) {

                log.debug(
                    "[CompletaIstanzaRateizzazione] Impossibile completare l'invio dei Dati ISEE "
                        + datiISEE);
                log.debug(
                    "[CompletaIstanzaRateizzazione] Impossibile completare l'istanza: " + istanza);
                error("Errore generico durante l'invio dell'ISEE");
              } catch (Exception e) {
                log.debug(
                    "[CompletaIstanzaRateizzazione] Impossibile completare l'invio dei Dati ISEE "
                        + datiISEE);
                log.debug(
                    "[CompletaIstanzaRateizzazione] Impossibile completare l'istanza: " + istanza);
                error("Errore generico durante l'invio dell'ISEE");
              }
            }

            getTargetPanel(target, datiRichiestaIstanzaPl);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRateizzazionePanel.this);

            log.error("[CompletaIstanzaRateizzazione] Error Target");
          }
        };

    return _completa;
  }

  private FdcAjaxButton creaBottoneAvanti(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    _avanti =
        new FdcAjaxButton("avanti") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "[RichiestaRateizzazionePanel - creaBottoneAvanti - onSubmit] Next Step" + _index);

            if (Step.values()[_index] == Step.Informativa
                && !datiRichiestaIstanzaPl.isCheckInformativaRateizzazione()) {
              error(
                  "Prima di proseguire è necessario sottoscrivere il trattamento dei dati personali");
            } else {
              _index = _index + 1;
            }

            getTargetPanel(target, datiRichiestaIstanzaPl);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRateizzazionePanel.this);

            log.error("[FdcAjaxButton - creaBottoneAvanti] Error Target");
          }
        };

    _avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRateizzazionePanel.avanti", RichiestaRateizzazionePanel.this)));

    return _avanti;
  }

  private FdcAjaxButton creaBottoneIndietro(DettaglioVerbale dettaglioVerbale) {
    _indietro =
        new FdcAjaxButton("indietro") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "[RichiestaRateizzazionePanel - creaBottoneAvanti - onSubmit] Next Step " + _index);

            _index = _index - 1;
            getTargetPanel(target, dettaglioVerbale);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRateizzazionePanel.this);

            log.error("[FdcAjaxButton - creaBottoneAvanti] Error Target");
          }
        };

    _indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRateizzazionePanel.indietro", RichiestaRateizzazionePanel.this)));

    _indietro.setDefaultFormProcessing(false);

    return _indietro;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla(DettaglioVerbale dettaglioVerbale) {
    this._annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaRateizzazionePanel.this);
            form.clearInput();
            setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));
          }
        };

    _annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRateizzazionePanel.annulla", RichiestaRateizzazionePanel.this)));

    return _annulla;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target, Object datiDomanda) {
    this._stepPanel.setIndexStep(_index);
    target.add(_stepPanel, feedbackPanel, _indietro, _avanti, _annulla);
    log.debug("[getTargetPanel] Next or Previous Step: " + Step.values()[_index]);

    switch (Step.values()[_index]) {
        // DatiPersonali
      case Informativa:
        stepInformativa();
        target.add(this);
        break;
      case DatiPersonali:
        stepDatiPersonali();
        target.add(this);
        break;
      case DatiIsee:
        stepDatiIsee();
        target.add(this);
        break;
      case Riepilogo:
        stepRiepilogo();
        target.add(this);
        break;
      case Esito:
        stepEsito();
        target.add(this);
        break;
      default:
        break;
    }

    return target;
  }

  /// TODO: Modificare con il servizio nuovo
  private ConsultazioneAttestazioneCF200 getAttestazioniISEE() {
    try {
      String tipoIndicatore = "Ordinario";
      return InpsModiHelper.getAttestazioneISEE(
          getUtente(),
          PrestazioneDaErogareEnum.Z9_99,
          StatodomandaPrestazioneEnum.EROGATA,
          tipoIndicatore);
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("Errore attestazione isee in richiesta rateizzazione = " + e.getMessage(), e);
      return null;
    }
  }

  private void stepInformativa() {
    // TODO Auto-generated method stub
    containerPrivacy.setVisible(true);
    _containerStep1.setVisible(false);
    _containerStep2.setVisible(false);
    _indietro.setVisible(false);
    _proseguiInfo.setVisible(false);
    _avanti.setVisible(true);
    _invia.setVisible(false);
    containerInfopagina.setVisible(false);
  }

  private void stepDatiIsee() {
    log.debug("[StepIsee] Presentabile: " + datiRichiestaIstanzaPl.isPresentabile());

    _containerStep1.setVisible(false);
    _containerStep2.setVisible(true);
    containerPrivacy.setVisible(false);
    _containerStep2.setEnabled(true);
    _avanti.setVisible(datiRichiestaIstanzaPl.isPresentabile());
    _invia.setVisible(false);
    _completa.setVisible(false);
    _indietro.setVisible(!daCompletare && datiRichiestaIstanzaPl.isPresentabile());

    if (LabelFdCUtil.checkIfNull(attestazioni) && daCompletare) {
      _avanti.setVisible(false);
    }

    containerInfopagina.setVisible(false);
  }

  private void stepDatiPersonali() {
    containerPrivacy.setVisible(false);
    _containerStep1.setVisible(true);
    _containerStep2.setVisible(false);
    _indietro.setVisible(true);
    _proseguiInfo.setVisible(true);
    _containerStep1.setEnabled(true);
    _avanti.setVisible(true);
    _invia.setVisible(false);
    containerInfopagina.setVisible(true);
  }

  private void stepRiepilogo() {
    containerPrivacy.setVisible(false);
    _containerStep1.setVisible(true);
    _containerStep2.setVisible(true);
    _containerStep1.setEnabled(false);
    _containerStep2.setEnabled(false);
    _indietro.setVisible(true);
    _proseguiInfo.setVisible(false);
    _avanti.setVisible(false);
    _invia.setVisible(!daCompletare);
    _completa.setVisible(daCompletare && LabelFdCUtil.checkIfNotNull(attestazioni));

    containerInfopagina.setVisible(false);
  }

  private void stepEsito() {
    containerPrivacy.setVisible(false);
    _containerStep3.setVisible(true);
    _indietro.setVisible(false);
    _proseguiInfo.setVisible(false);
    _avanti.setVisible(false);
    _invia.setVisible(false);
    _containerStep1.setVisible(false);
    _containerStep2.setVisible(false);
    containerInfopagina.setVisible(false);
    _annulla.setVisible(false);
    _completa.setVisible(false);
  }
}
