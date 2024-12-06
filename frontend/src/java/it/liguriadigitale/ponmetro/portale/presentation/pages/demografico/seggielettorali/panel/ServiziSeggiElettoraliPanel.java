package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCIbanField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCToggles;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.seggi.model.DatiPersonaliComponenteSeggio;
import it.liguriadigitale.ponmetro.seggi.model.Elezioni;
import it.liguriadigitale.ponmetro.seggi.model.EsitoInvioDati;
import it.liguriadigitale.ponmetro.seggi.model.InvioDatiPersonali;
import java.io.IOException;
import java.time.LocalDate;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ServiziSeggiElettoraliPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 66884533221603716L;

  Elezioni elezioni;
  DatiPersonaliComponenteSeggio datiPersonaliComponenteSeggio;

  // FdcCardWebMarkupContainer fdcCardWebMarkupContainer;

  WebMarkupContainer wmkMessaggio;

  WebMarkupContainer wmkMessaggioPagamentoIncontanti;
  Label lblMessaggio;

  WebMarkupContainer wmkFormInserimentoIban;

  Label lblDescTipoElezioni;
  Label lblDescPeriodo;

  FdCToggles toggleTipoPagamento;
  FdCIbanField codiceIBAN;

  FdcAjaxButton btnSalva;

  WebMarkupContainer wmkMessaggioInfoConfermaIban;

  Label labelComboTipoPagamento;

  public ServiziSeggiElettoraliPanel(
      String id, Elezioni elezioni, DatiPersonaliComponenteSeggio datiPersonaliComponenteSeggio) {
    super(id, datiPersonaliComponenteSeggio);
    this.elezioni = elezioni;
    this.datiPersonaliComponenteSeggio = datiPersonaliComponenteSeggio;
    setOutputMarkupId(true);
    fillDati(datiPersonaliComponenteSeggio);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    CompoundPropertyModel<DatiPersonaliComponenteSeggio> datiPersonaliComponenteSeggioModel =
        new CompoundPropertyModel<>(datiPersonaliComponenteSeggio);

    boolean formInserimentoVisibile = false;
    boolean messaggioVisibile = false;
    String messaggio = "";

    String periodo =
        "Gli inserimenti sono consentiti solo dal "
            + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataInizio())
            + " al "
            + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataFine());

    if (isFuoriPeriodoElettorale(elezioni.getDataFine())) {
      messaggio =
          "Non &egrave; possibile effettuare comunicazioni in merito ai componenti di seggio perch&egrave; il servizio &egrave; disponibile solo in periodo elettorale.";
      setConCornice(true);
      formInserimentoVisibile = !(datiPersonaliComponenteSeggio.getPresenteNelSeggio() == null);
      messaggioVisibile = true;
      setEnabled(false);
    } else if (isPrimaPeriodoElettorale(elezioni.getDataInizio())) {
      messaggio =
          "Sarà possibile effettuare comunicazioni in merito ai componenti di seggio tra il giorno "
              + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataInizio())
              + " ed il giorno "
              + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataFine())
              + ". Si prega di effettuare l'operazione in quei giorni.";
      setConCornice(true);

      formInserimentoVisibile = !(datiPersonaliComponenteSeggio.getPresenteNelSeggio() == null);

      messaggioVisibile = true;
      setEnabled(false);
    } else if (datiPersonaliComponenteSeggio.getPresenteNelSeggio() == null
        && isInPeriodoElettorale(elezioni.getDataInizio(), elezioni.getDataFine())) {

      messaggio =
          "Il servizio è disponibile solo tra il giorno "
              + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataInizio())
              + " ed il giorno "
              + LocalDateUtil.getDataFormatoEuropeo(elezioni.getDataFine())
              + ". limitatamente ai componenti dei seggi elettorali: presidenti, segretari e scrutatori."
              + "Per qualsiasi necessità si prega di inviare una mail all’ufficio elettorale: <a href=\"mailto:elettorale@comune.genova.it\">elettorale@comune.genova.it</a>";

      setConCornice(false);
      formInserimentoVisibile = false;
      messaggioVisibile = true;
      setEnabled(false);
    } else if (elezioni.getDataInizio() == null && elezioni.getDataFine() == null) {
      messaggio =
          "Il servizio non è ancora disponibile. "
              + "Per qualsiasi necessità si prega di inviare una mail all’ufficio elettorale: <a href=\"mailto:elettorale@comune.genova.it\">elettorale@comune.genova.it</a>";

      setConCornice(false);
      formInserimentoVisibile = false;
      messaggioVisibile = true;
      setEnabled(false);
    } else {
      messaggio = "E' possibile effettuare comunicazioni in merito ai componenti di seggio.";
      setConCornice(true);
      formInserimentoVisibile = true;
      messaggioVisibile = false;
      setEnabled(true);
    }

    wmkMessaggio = new WebMarkupContainer("wmkMessaggio");
    wmkMessaggio.setVisible(messaggioVisibile);
    form.addOrReplace(wmkMessaggio);

    lblMessaggio = new Label("lblMessaggio", Model.of(messaggio));
    lblMessaggio.setEscapeModelStrings(false);
    wmkMessaggio.add(lblMessaggio);

    wmkFormInserimentoIban = new WebMarkupContainer("wmkFormInserimentoIban");
    wmkFormInserimentoIban.setVisible(formInserimentoVisibile);
    super.form.addOrReplace(wmkFormInserimentoIban);

    boolean tipoPagamento;
    String tipoPagamentoStr;
    if (datiPersonaliComponenteSeggio.getIban() != null
        && !datiPersonaliComponenteSeggio.getIban().equals("")) {
      tipoPagamento = true;

      tipoPagamentoStr = "Sì";

    } else {
      tipoPagamento = false;

      tipoPagamentoStr = "No";
    }

    wmkFormInserimentoIban.addOrReplace(
        lblDescTipoElezioni = new Label("lblDescTipoElezioni", elezioni.getDescrizione()));

    Label lblDescPeriodo = new Label("lblDescPeriodo", periodo);
    lblDescPeriodo.setVisible(
        isInPeriodoElettorale(elezioni.getDataInizio(), elezioni.getDataFine()));
    wmkFormInserimentoIban.addOrReplace(lblDescPeriodo);

    IModel<String> tipoPagamentoStrModel = Model.of(tipoPagamentoStr);

    SiNoDropDownChoice comboTipoPagamento =
        new SiNoDropDownChoice("comboTipoPagamento", tipoPagamentoStrModel);

    comboTipoPagamento.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -1806716467859288911L;

          @SuppressWarnings("unchecked")
          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            log.debug("CP click su combo seggi " + comboTipoPagamento.getValue());

            codiceIBAN.setVisible(comboTipoPagamento.getValue().equalsIgnoreCase("0"));
            codiceIBAN.setRequired(comboTipoPagamento.getValue().equalsIgnoreCase("0"));

            if (comboTipoPagamento.getValue().equalsIgnoreCase("1")) {
              codiceIBAN.getTextField().setModelObject(null);
            }

            wmkMessaggioPagamentoIncontanti.setVisible(
                comboTipoPagamento.getValue().equalsIgnoreCase("1"));

            wmkMessaggioInfoConfermaIban.setVisible(
                comboTipoPagamento.getValue().equalsIgnoreCase("0")
                    && tipoPagamento
                    && LabelFdCUtil.checkIfNotNull(codiceIBAN.getTextField().getModelObject()));

            target.add(codiceIBAN, wmkMessaggioPagamentoIncontanti, wmkMessaggioInfoConfermaIban);
          }
        });
    wmkFormInserimentoIban.addOrReplace(comboTipoPagamento);

    wmkFormInserimentoIban.addOrReplace(
        codiceIBAN =
            new FdCIbanField<>(
                "codiceIBAN",
                datiPersonaliComponenteSeggioModel.bind("iban"),
                ServiziSeggiElettoraliPanel.this));

    codiceIBAN.setVisible(tipoPagamento);
    codiceIBAN.setRequired(tipoPagamento);

    wmkFormInserimentoIban.addOrReplace(
        btnSalva =
            new FdcAjaxButton("btnSalva") {

              private static final long serialVersionUID = 4792068628183196880L;

              @Override
              protected void onSubmit(AjaxRequestTarget target) {
                target.add(getPage());

                try {

                  DatiPersonaliComponenteSeggio datiPersonaliCS =
                      ((DatiPersonaliComponenteSeggio) form.getModelObject());

                  InvioDatiPersonali invioDatiPersonali = new InvioDatiPersonali();
                  invioDatiPersonali.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());

                  if (datiPersonaliCS.getIban() != null) {
                    invioDatiPersonali.setIban(datiPersonaliCS.getIban().toUpperCase());
                  }
                  invioDatiPersonali.setIdentificativoElezione(
                      datiPersonaliCS.getIdentificativoElezione());

                  EsitoInvioDati esitoInvioDati =
                      ServiceLocator.getInstance()
                          .getServiziSeggiElettorali()
                          .postDatiPersonaliComponenteSeggio(invioDatiPersonali);

                  datiPersonaliComponenteSeggio =
                      ServiceLocator.getInstance()
                          .getServiziSeggiElettorali()
                          .getDatiPersonaliComponenteSeggio(
                              getUtente().getCodiceFiscaleOperatore(),
                              elezioni.getIdentificativoElezione());

                  if (Boolean.TRUE.equals(esitoInvioDati.getEsito()))
                    success("Operazione eseguita correttamente.");
                  else error(esitoInvioDati.getMessaggio());

                } catch (BusinessException | ApiException | IOException e) {
                  log.debug("Errore durante la chiamata delle API", e);
                  throw new RestartResponseAtInterceptPageException(
                      new ErroreServiziPage("seggi elettorali"));
                }
              }

              @Override
              protected void onError(AjaxRequestTarget target) {
                target.add(getPage());
              }
            });

    wmkFormInserimentoIban.addOrReplace(
        wmkMessaggioPagamentoIncontanti =
            new WebMarkupContainer("wmkMessaggioPagamentoIncontanti"));

    wmkMessaggioPagamentoIncontanti.setOutputMarkupId(true);
    wmkMessaggioPagamentoIncontanti.setOutputMarkupPlaceholderTag(true);

    wmkMessaggioPagamentoIncontanti.setVisible(!tipoPagamento);

    wmkFormInserimentoIban.addOrReplace(
        wmkMessaggioInfoConfermaIban = new WebMarkupContainer("wmkMessaggioInfoConfermaIban"));

    wmkMessaggioInfoConfermaIban.setOutputMarkupId(true);
    wmkMessaggioInfoConfermaIban.setOutputMarkupPlaceholderTag(true);

    wmkMessaggioInfoConfermaIban.setVisible(
        tipoPagamento && datiPersonaliComponenteSeggio.getPresenteNelSeggio());

    String valoreLabelComboTipoPagamento =
        tipoPagamento && datiPersonaliComponenteSeggio.getPresenteNelSeggio() == true
            ? getString("ServiziSeggiElettoraliPanel.tipoPagamentoConfermaIban")
            : getString("ServiziSeggiElettoraliPanel.tipoPagamento");

    labelComboTipoPagamento = new Label("labelComboTipoPagamento", valoreLabelComboTipoPagamento);
    labelComboTipoPagamento.setOutputMarkupId(true);
    labelComboTipoPagamento.setOutputMarkupPlaceholderTag(true);
    wmkFormInserimentoIban.addOrReplace(labelComboTipoPagamento);
  }

  private boolean isInPeriodoElettorale(LocalDate dataInizio, LocalDate dataFine) {
    LocalDate oggi = LocalDate.now();
    return ((LabelFdCUtil.checkIfNotNull(dataInizio) && LabelFdCUtil.checkIfNotNull(dataFine))
        && ((oggi.isEqual(dataInizio) || oggi.isEqual(dataFine))
            || (oggi.isAfter(dataInizio) && oggi.isBefore(dataFine))));
  }

  private boolean isPrimaPeriodoElettorale(LocalDate dataInizio) {
    LocalDate oggi = LocalDate.now();
    return LabelFdCUtil.checkIfNotNull(dataInizio) && oggi.isBefore(dataInizio);
  }

  private boolean isFuoriPeriodoElettorale(LocalDate dataFine) {
    LocalDate oggi = LocalDate.now();
    return LabelFdCUtil.checkIfNotNull(dataFine) && oggi.isAfter(dataFine);
  }
}
