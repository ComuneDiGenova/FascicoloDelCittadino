package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.StatoRimborsoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.AllegaDocumentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco.RimborsiImuPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class RichiestaRimborsoImuPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 187987564891315L;
  private StepPanel _stepPanel;
  private int _index = 1;

  WebMarkupContainer containerStep1;
  WebMarkupContainer containerStep2;
  WebMarkupContainer containerStep3;
  WebMarkupContainer containerStep4;
  WebMarkupContainer containerStep6;
  WebMarkupContainer infoAllegati;

  FdcAjaxButton prosegui;
  FdcAjaxButton invia;
  FdCButtonBootstrapAjaxLink<Object> annulla;
  FdcAjaxButton indietro;
  LaddaAjaxLink<Object> allegaFile;

  RimborsoImu rimborso;
  DatiImmobiliPanel datiImmobili;
  DatiVersamentiPanel datiVersamento;

  private enum Step {
    Home,
    DatiSottoscritto,
    Annualita,
    Immobile,
    Modalita_Rimborso,
    Riepilogo,
    Esito
  };

  public RichiestaRimborsoImuPanel(String id, RimborsoImu pojo) {
    super(id, pojo);
    // TODO Auto-generated constructor stub
    _stepPanel = new StepPanel("stepPanel", 1, getStep());

    this.fillDati(pojo);
  }

  private List<StepFdC> getStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati Sottoscritto", 1));
    listaStep.add(new StepFdC("Annualità", 2));
    listaStep.add(new StepFdC("Immobili", 3));
    listaStep.add(new StepFdC("Modalità Rimborso", 4));
    listaStep.add(new StepFdC("Riepilogo", 5));
    listaStep.add(new StepFdC("Esito", 6));

    return listaStep;
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    super.fillDati(dati);

    rimborso = (RimborsoImu) dati;

    form.addOrReplace(_stepPanel);

    containerStep1 = new WebMarkupContainer("containerStep1");
    DatiSottoscrittoPanel datiSottoscritto =
        new DatiSottoscrittoPanel("datiSottoscrittoPanel", rimborso);
    containerStep1.addOrReplace(datiSottoscritto);

    containerStep2 = new WebMarkupContainer("containerStep2");
    datiVersamento = new DatiVersamentiPanel("datiVersamentiPanel", rimborso);
    containerStep2.addOrReplace(datiVersamento);

    containerStep3 = new WebMarkupContainer("containerStep3");
    datiImmobili = new DatiImmobiliPanel("datiImmobiliPanel", rimborso);
    containerStep3.addOrReplace(datiImmobili);

    containerStep4 = new WebMarkupContainer("containerStep4");
    DatiModalitaRimborsoPanel datiModalitaRimborso =
        new DatiModalitaRimborsoPanel("datiModalitaPagamentoPanel", rimborso);
    containerStep4.addOrReplace(datiModalitaRimborso);

    containerStep6 = new WebMarkupContainer("containerStep6");

    form.addOrReplace(containerStep1);
    form.addOrReplace(containerStep2);
    form.addOrReplace(containerStep3);
    form.addOrReplace(containerStep4);
    form.addOrReplace(containerStep6);

    form.addOrReplace(indietro = creaBottoneIndietro());
    form.addOrReplace(prosegui = creaBottoneProsegui());
    form.addOrReplace(invia = creaBottoneInviaPratica());

    creaBottoneAnnulla();

    form.addOrReplace(annulla);

    allegaFile =
        new LaddaAjaxLink<Object>("btnAllegaFile", Type.Success) {
          private static final long serialVersionUID = 18979654132131548L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug("[AllegaFile] Allega file rimborso Imu");
            rimborso.setStato(StatoRimborsoEnum.C);
            setResponsePage(new AllegaDocumentiPage(rimborso));
          }
        };

    allegaFile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoImuPanel.allegaFile", RichiestaRimborsoImuPanel.this)));

    containerStep6.addOrReplace(allegaFile);

    infoAllegati = new WebMarkupContainer("infoAllegati");

    String urlDelegaRiscossione = RichiestaRimborsoImuHelper.getUrl("URL_DELEGA_RISCOSSIONE_IMU");
    String labelDelegaRiscossione =
        getLocalizer()
            .getString("RichiestaRimborsoImuPanel.delegaCoerede", RichiestaRimborsoImuPanel.this);
    infoAllegati.addOrReplace(
        RichiestaRimborsoImuHelper.createExtenalLink(
            "linkDelegaRiscossione", urlDelegaRiscossione, labelDelegaRiscossione));

    String urlAutocertificazioneEredi =
        RichiestaRimborsoImuHelper.getUrl("URL_AUTOCERTIFICAZIONE_EREDI_IMU");
    String labelAutocertificazioneEredi =
        getLocalizer()
            .getString(
                "RichiestaRimborsoImuPanel.autocertificazioneEredi",
                RichiestaRimborsoImuPanel.this);
    infoAllegati.addOrReplace(
        RichiestaRimborsoImuHelper.createExtenalLink(
            "autocertificazionErediLink",
            urlAutocertificazioneEredi,
            labelAutocertificazioneEredi));

    form.addOrReplace(infoAllegati);

    form.setMultiPart(true);
    stepDatiSottoscritto();
  }

  private FdcAjaxButton creaBottoneIndietro() {
    FdcAjaxButton button =
        new FdcAjaxButton("indietro") {
          private static final long serialVersionUID = 1798754641188L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            super.onSubmit(target);

            _index = _index - 1;
            getTargetPanel(target, rimborso);
          }
        };

    button.setDefaultFormProcessing(false);
    return button;
  }

  private FdcAjaxButton creaBottoneProsegui() {
    return new FdcAjaxButton("prosegui") {
      private static final long serialVersionUID = 1798754641188L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        // TODO Auto-generated method stub
        // super.onSubmit(target);

        Step step = Step.values()[_index];
        String checkImmobili = RichiestaRimborsoImuHelper.checkImmobili(rimborso);
        String checkAnnualita = RichiestaRimborsoImuHelper.checkAnnualita(rimborso);
        String checkCondizioni = RichiestaRimborsoImuHelper.checkConoscenza(rimborso);

        log.debug("[checkImmobili]" + checkImmobili);

        if (!checkAnnualita.isEmpty() && step.equals(Step.Annualita)) {
          error(getLocalizer().getString(checkAnnualita, RichiestaRimborsoImuPanel.this));
        } else if (!checkImmobili.isEmpty() && step.equals(Step.Immobile)) {
          error(getLocalizer().getString(checkImmobili, RichiestaRimborsoImuPanel.this));
        } else if (!checkCondizioni.isEmpty() && step.equals(Step.Modalita_Rimborso)) {
          error(getLocalizer().getString(checkCondizioni, RichiestaRimborsoImuPanel.this));
        } else {
          _index = _index + 1;
        }

        getTargetPanel(target, rimborso);
      }

      @Override
      protected void onError(AjaxRequestTarget target) {
        // TODO Auto-generated method stub
        target.add(feedbackPanel);
      }
    };
  }

  private void creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {
          private static final long serialVersionUID = 1798754641188L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            setResponsePage(new RimborsiImuPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRimborsoImuPanel.annulla", RichiestaRimborsoImuPanel.this)));
  }

  private FdcAjaxButton creaBottoneInviaPratica() {
    return new FdcAjaxButton("invia") {
      private static final long serialVersionUID = 1798754641188L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        // TODO Auto-generated method stub
        super.onSubmit(target);

        IstanzaRimborso istanza = salvaPraticaRimborso();

        if (LabelFdCUtil.checkIfNull(istanza)) {
          error(
              getLocalizer()
                  .getString(
                      "RichiestaRimborsoImuPanel.salvaErrore", RichiestaRimborsoImuPanel.this));
        }

        if (LabelFdCUtil.checkIfNotNull(istanza.getMessaggioErrore())) {
          log.debug("[InviaPratica] Errore: " + istanza.getMessaggioErrore());
          error(istanza.getMessaggioErrore());
        }

        if (LabelFdCUtil.checkIfNotNull(istanza.getProgressivoIstanzaRimborso())) {
          log.debug(
              "[Invia] Istanza Rimborso ricevuta: " + istanza.getProgressivoIstanzaRimborso());
          log.debug("[AllegaFile] Allega file rimborso Imu");
          rimborso.setStato(StatoRimborsoEnum.C);
          rimborso.setUriPratica(
              Integer.valueOf(istanza.getProgressivoIstanzaRimborso().intValue()));

          setResponsePage(
              new AllegaDocumentiPage(
                  Integer.valueOf(istanza.getProgressivoIstanzaRimborso().intValue()), false));
        }

        getTargetPanel(target, rimborso);
      }
    };
  }

  // True: se la pratica di rimborso è stata inviata con successo
  // False: altrimenti
  private IstanzaRimborso salvaPraticaRimborso() {
    try {

      return ServiceLocator.getInstance().getServiziImuEng().praticaRimborsoImu(rimborso);

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.debug(
          "[salvaPraticaRimborso-BusinessException] Errore nell'invio della pratica di rimborso: "
              + e.getMessage());
      IstanzaRimborso is = new IstanzaRimborso();
      is.setMessaggioErrore(e.getMessage());
      return is;

    } catch (ApiException e) {
      // TODO Auto-generated catch block
      log.debug(
          "[salvaPraticaRimborso-ApiException] Errore nell'invio della pratica di rimborso: "
              + e.getMyMessage());
      IstanzaRimborso is = new IstanzaRimborso();
      is.setMessaggioErrore(e.getMyMessage());
      return is;
    }
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target, Object datiDomanda) {
    this._stepPanel.setIndexStep(_index);
    target.add(_stepPanel, feedbackPanel, invia, annulla, prosegui, indietro);

    switch (Step.values()[_index]) {
      case DatiSottoscritto:
        stepDatiSottoscritto();
        target.add(this);
        break;
      case Annualita:
        stepAnnualita();
        target.add(this);
        break;
      case Immobile:
        stepImmobili();
        target.add(this);
        break;
      case Modalita_Rimborso:
        stepModalitaRimborso();
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

  private void stepEsito() {
    indietro.setVisible(false);
    invia.setVisible(false);
    prosegui.setVisible(false);
    annulla.setVisible(false);

    containerStep1.setVisible(false);
    containerStep2.setVisible(false);
    containerStep3.setVisible(false);
    containerStep4.setVisible(false);
    containerStep6.setVisible(true);
    infoAllegati.setVisible(false);
  }

  private void stepRiepilogo() {
    indietro.setVisible(true);
    invia.setVisible(true);
    prosegui.setVisible(false);
    annulla.setVisible(true);

    containerStep1.setVisible(true).setEnabled(false);
    containerStep2.setVisible(true);
    datiVersamento.disableUI();
    containerStep3.setVisible(true);
    datiImmobili.disableUI();
    containerStep4.setVisible(true).setEnabled(false);
    containerStep6.setVisible(false);
    infoAllegati.setVisible(false);
  }

  private void stepModalitaRimborso() {
    // TODO Auto-generated method stub
    indietro.setVisible(true);
    invia.setVisible(false);
    prosegui.setVisible(true);
    annulla.setVisible(true);

    containerStep1.setVisible(false).setEnabled(true);
    containerStep2.setVisible(false).setEnabled(true);
    containerStep3.setVisible(false).setEnabled(true);
    containerStep4.setVisible(true).setEnabled(true);
    containerStep6.setVisible(false).setEnabled(true);
    infoAllegati.setVisible(false);
  }

  private void stepImmobili() {
    // TODO Auto-generated method stub
    indietro.setVisible(true);
    invia.setVisible(false);
    prosegui.setVisible(true);
    annulla.setVisible(true);

    containerStep1.setVisible(false).setEnabled(true);
    containerStep2.setVisible(false).setEnabled(true);
    containerStep3.setVisible(true).setEnabled(true);
    containerStep4.setVisible(false).setEnabled(true);
    containerStep6.setVisible(false).setEnabled(true);
    infoAllegati.setVisible(false);

    datiImmobili.enableUI();
  }

  private void stepAnnualita() {
    // TODO Auto-generated method stub
    indietro.setVisible(true);
    invia.setVisible(false);
    prosegui.setVisible(true);
    annulla.setVisible(true);

    containerStep1.setVisible(false).setEnabled(true);
    containerStep2.setVisible(true).setEnabled(true);
    containerStep3.setVisible(false).setEnabled(true);
    containerStep4.setVisible(false).setEnabled(true);
    containerStep6.setVisible(false).setEnabled(true);
    infoAllegati.setVisible(false);

    datiVersamento.enableUI();
  }

  private void stepDatiSottoscritto() {
    // TODO Auto-generated method stub
    indietro.setVisible(false);
    invia.setVisible(false);
    prosegui.setVisible(true);
    annulla.setVisible(true);

    containerStep1.setVisible(true).setEnabled(true);
    containerStep2.setVisible(false).setEnabled(true);
    containerStep3.setVisible(false).setEnabled(true);
    containerStep4.setVisible(false).setEnabled(true);
    containerStep6.setVisible(false).setEnabled(true);
    infoAllegati.setVisible(true);
  }
}
