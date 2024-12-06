package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCModalitaPagamentoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoSuContoCorrentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.ModalitaPagametoTariEngRender;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel.DatiPersonaliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso.form.RimborsoVerbale;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Rimborsi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RichiestaRimborsoPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 1898754541215845L;

  FdcAjaxButton _invia;
  FdcAjaxButton prosegui;
  FdcAjaxButton indietro;
  FdCButtonBootstrapAjaxLink<Object> _annulla;
  RimborsoVerbale rimborso;

  FdCPagamentoSuContoCorrentePanel containerBonifico;
  DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;

  WebMarkupContainer esito;
  WebMarkupContainer container;
  WebMarkupContainer contanti;
  FdcAjaxButton invia;
  FdCButtonBootstrapAjaxLink<Object> annulla;
  int _index = 1;
  Rimborsi rimborsoHermes;

  private StepPanel _stepPanel;

  private enum Step {
    Home,
    DatiPersonali,
    Riepilogo,
    Esito
  };

  public RichiestaRimborsoPanel(
      String id, RimborsoVerbale pojo, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, pojo);
    // TODO Auto-generated constructor stub
    this.datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;

    _stepPanel = new StepPanel("stepPanel", 1, getSteps());

    fillDati(pojo);
  }

  private List<StepFdC> getSteps() {
    // TODO Auto-generated method stub
    List<StepFdC> lista = new ArrayList<StepFdC>();
    lista.add(new StepFdC("Dati", 1));
    lista.add(new StepFdC("Riepilogo", 2));
    lista.add(new StepFdC("Esito", 3));

    return lista;
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    super.fillDati(dati);

    rimborsoHermes = new Rimborsi();

    form.addOrReplace(_stepPanel);

    rimborso = (RimborsoVerbale) dati;
    DettaglioVerbale dettaglioVerbale = datiRichiestaIstanzaPl.getDettaglioVerbaleDiPartenza();

    container = new WebMarkupContainer("container");
    container.setOutputMarkupId(true);

    List<ModalitaPagamentoEnum> listaModalitaPagamento =
        ServiceLocator.getInstance().getServiziTariEng().getListaModalitaPagamento();

    FdCModalitaPagamentoDropDownChoice<?> tipoLiquidazioneDropDownChoise =
        new FdCModalitaPagamentoDropDownChoice(
            "tipoLiquidazione",
            new PropertyModel(rimborso, "tipoLiquidazione"),
            new ModalitaPagametoTariEngRender(),
            listaModalitaPagamento);
    tipoLiquidazioneDropDownChoise.setLabel(Model.of("Tipo Liquidazione"));
    tipoLiquidazioneDropDownChoise.setRequired(true);
    tipoLiquidazioneDropDownChoise.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 198796541318987L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            containerBonifico.setVisible(
                tipoLiquidazioneDropDownChoise.getValue().equals(ModalitaPagamentoEnum.CAB.name()));
            contanti.setVisible(
                tipoLiquidazioneDropDownChoise.getValue().equals(ModalitaPagamentoEnum.CAS.name()));

            if (tipoLiquidazioneDropDownChoise
                .getValue()
                .equals(ModalitaPagamentoEnum.CAS.name())) {
              rimborso.setIban(null);
              rimborso.setSwift(null);
            }

            target.add(tipoLiquidazioneDropDownChoise, containerBonifico, contanti);
          }
        });

    container.addOrReplace(tipoLiquidazioneDropDownChoise);

    containerBonifico = new FdCPagamentoSuContoCorrentePanel("bonifico", rimborso, false, false);
    containerBonifico.setOutputMarkupId(true);
    containerBonifico.setVisible(false);

    esito = new WebMarkupContainer("esito");
    esito.setOutputMarkupId(true);
    esito.setVisible(false);
    form.addOrReplace(esito);

    DatiPersonaliPanel datiPersonaliPanel =
        new DatiPersonaliPanel(
            "datiPersonaliPanel",
            new CompoundPropertyModel<DatiRichiestaIstanzaPl>(datiRichiestaIstanzaPl));
    container.addOrReplace(datiPersonaliPanel);

    container.addOrReplace(containerBonifico);

    container.addOrReplace(createFdCTextField(dettaglioVerbale));

    FdCTextFieldCurrency<Component> importo =
        new FdCTextFieldCurrency<Component>(
            "importo",
            new PropertyModel<Double>(dettaglioVerbale, "importo"),
            RichiestaRimborsoPanel.this,
            "€");
    importo.setEnabled(false);
    importo.setVisible(false);
    // importo.getTextField().add(new CurrencyFormattingBehavior(" €"));
    container.addOrReplace(importo);

    contanti = new WebMarkupContainer("contanti");
    contanti.setOutputMarkupId(true);
    contanti.setOutputMarkupPlaceholderTag(true);
    contanti.setVisible(false);
    form.addOrReplace(contanti);

    form.addOrReplace(container);
    form.addOrReplace(prosegui = creaBottoneProsegui());
    form.addOrReplace(invia = creaBottoneInvia());
    form.addOrReplace(annulla = creaBottoneAnnulla(dettaglioVerbale));
    form.addOrReplace(indietro = creaBottoneIndietro());

    stepDatiPersonali();
  }

  private FdcAjaxButton creaBottoneProsegui() {
    // TODO Auto-generated method stub
    FdcAjaxButton button =
        new FdcAjaxButton("prosegui") {
          private static final long serialVersionUID = 1898754541215846L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            _index = _index + 1;
            getTargetPanel(target, new Rimborsi());
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(feedbackPanel);
          }
        };

    button.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRimborsoPanel.prosegui", RichiestaRimborsoPanel.this)));

    button.setOutputMarkupId(true);

    return button;
  }

  private FdcAjaxButton creaBottoneIndietro() {
    // TODO Auto-generated method stub
    FdcAjaxButton button =
        new FdcAjaxButton("indietro") {
          private static final long serialVersionUID = 1898754541215846L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            _index = _index - 1;
            getTargetPanel(target, rimborsoHermes);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(feedbackPanel);
          }
        };

    button.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRimborsoPanel.indietro", RichiestaRimborsoPanel.this)));
    button.setOutputMarkupId(true);
    return button;
  }

  private FdCTextField<Component> createFdCTextField(DettaglioVerbale dettaglioVerbale) {

    FdCTextField<Component> field =
        new FdCTextField<Component>(
            "numeroVerbale",
            new PropertyModel<String>(dettaglioVerbale, "numeroAvviso"),
            RichiestaRimborsoPanel.this);
    field.setEnabled(false);
    field.setRequired(false);

    return field;
  }

  private FdcAjaxButton creaBottoneInvia() {
    _invia =
        new FdcAjaxButton("invia") {

          /** */
          private static final long serialVersionUID = 1898754541215846L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            try {
              if (LabelFdCUtil.checkIfNull(rimborso)) {
                return;
              }

              rimborsoHermes.setTipoLiquidazione(rimborso.getTipoLiquidazioneHermes());
              rimborsoHermes.setIban(rimborso.getIban());
              rimborsoHermes.setSwift(rimborso.getSwift());

              datiRichiestaIstanzaPl.setRimborso(rimborsoHermes);

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

            } catch (BusinessException | ApiException | IOException e) {
              // TODO Auto-generated catch block
              log.debug("[EsitoInserisciConludi] Errore: " + e);
              error("Errore durante la richiesta di inserimento ISTANZA");
            } catch (Exception ex) {
              log.debug("[EsitoInserisciConludi] Errore: " + ex);
              error("Errore durante la richiesta di inserimento ISTANZA");
            }

            getTargetPanel(target, rimborsoHermes);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(feedbackPanel);
          }
        };

    _invia.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRimborsoPanel.invia", RichiestaRimborsoPanel.this)));

    _invia.setOutputMarkupId(true);

    return _invia;
  }

  private void stepEsito() {
    // TODO Auto-generated method stub
    esito.setVisible(true);
    container.setVisible(false);
    containerBonifico.setVisible(false);
    invia.setVisible(false);
    annulla.setVisible(false);
    indietro.setVisible(false);
    contanti.setVisible(false);
  }

  private void stepDatiPersonali() {
    // TODO Auto-generated method stub
    esito.setVisible(false);
    container.setVisible(true);
    invia.setVisible(false);
    annulla.setVisible(true);
    indietro.setVisible(false);
    prosegui.setVisible(true);
  }

  private void stepRiepilogo() {
    // TODO Auto-generated method stub
    esito.setVisible(false);
    container.setVisible(true).setEnabled(false);
    containerBonifico.setEnabled(false);
    invia.setVisible(true);
    annulla.setVisible(true);
    indietro.setVisible(true);
    prosegui.setVisible(false);
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target, Object datiDomanda) {
    this._stepPanel.setIndexStep(_index);
    target.add(_stepPanel, feedbackPanel, invia, annulla, indietro);
    log.debug("[getTargetPanel] Next or Previous Step: " + Step.values()[_index]);

    switch (Step.values()[_index]) {
        // DatiPersonali
      case DatiPersonali:
        stepDatiPersonali();
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

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla(DettaglioVerbale dettaglioVerbale) {
    this._annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          /** */
          private static final long serialVersionUID = 1898754541215847L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoPanel.this);
            form.clearInput();
            setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));
          }
        };

    _annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaRimborsoPanel.annulla", RichiestaRimborsoPanel.this)));
    _annulla.setOutputMarkupId(true);
    return _annulla;
  }
}
