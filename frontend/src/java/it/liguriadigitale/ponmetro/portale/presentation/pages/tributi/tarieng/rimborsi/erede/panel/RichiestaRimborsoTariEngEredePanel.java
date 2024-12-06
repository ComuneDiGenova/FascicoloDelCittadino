package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.erede.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.QuadroTributarioTariPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel.DatiRichiestaRimborsoBeneficiarioTariEngPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel.EsitoRichiestaRimborsoTariEngPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel.ListaFileAllegatiInLetturaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.EnteBeneficiarioRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class RichiestaRimborsoTariEngEredePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 8779026503674277164L;

  private int index;

  private StepPanel stepPanel;

  private DatiRichiestaRimborsoTariEngEredePanel datiRimborsoPanel;

  private DatiRichiestaRimborsoBeneficiarioTariEngPanel datiRimborsoBeneficiarioPanel;

  private EsitoRichiestaRimborsoTariEngPanel esitoRimborsoTariPanel;

  private ListaFileAllegatiInLetturaPanel listaAllegatiInLetturaPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private WebMarkupContainer containerInfoInvia;

  public RichiestaRimborsoTariEngEredePanel(String id, DatiRichiestaRimborsoTariEng datiRimborso) {
    super(id, datiRimborso);
    setOutputMarkupId(true);

    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiRimborso);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DatiRichiestaRimborsoTariEng datiRimborso = (DatiRichiestaRimborsoTariEng) dati;

    stepPanel =
        new StepPanel(
            "stepPanel", index, ServiceLocator.getInstance().getServiziTariEng().getListaStep());
    form.addOrReplace(stepPanel);

    datiRimborsoPanel =
        new DatiRichiestaRimborsoTariEngEredePanel("datiRimborsoPanel", datiRimborso, index);
    datiRimborsoPanel.setVisible(index == 1);
    form.addOrReplace(datiRimborsoPanel);

    datiRimborsoBeneficiarioPanel =
        new DatiRichiestaRimborsoBeneficiarioTariEngPanel(
            "datiRimborsoBeneficiarioPanel", datiRimborso, index);
    datiRimborsoBeneficiarioPanel.setVisible(index == 2);
    form.addOrReplace(datiRimborsoBeneficiarioPanel);

    listaAllegatiInLetturaPanel =
        new ListaFileAllegatiInLetturaPanel("listaAllegatiInLetturaPanel", datiRimborso, index);
    listaAllegatiInLetturaPanel.setVisible(index == 3);
    form.addOrReplace(listaAllegatiInLetturaPanel);

    esitoRimborsoTariPanel =
        new EsitoRichiestaRimborsoTariEngPanel("esitoRimborsoTariPanel", datiRimborso, index);
    esitoRimborsoTariPanel.setVisible(index == 4);
    form.addOrReplace(esitoRimborsoTariPanel);

    form.addOrReplace(creaBottoneAvanti(datiRimborso));
    form.addOrReplace(creaBottoneIndietro(datiRimborso));
    form.addOrReplace(creaBottoneAnnulla());

    containerInfoInvia = new WebMarkupContainer("containerInfoInvia");
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setVisible(index == 3);
    form.addOrReplace(containerInfoInvia);
  }

  private FdcAjaxButton creaBottoneAvanti(DatiRichiestaRimborsoTariEng datiRichiesta) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -3326816224925998309L;

          @SuppressWarnings("static-access")
          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP creaBottoneAvanti richiesta erede eng = " + datiRichiesta);

            index = index + 1;

            if (index == 2) {

              if (LabelFdCUtil.checkIfNotNull(datiRichiesta)) {

                if (LabelFdCUtil.checkIfNull(datiRichiesta.getIdDeb())
                    && LabelFdCUtil.checkIfNull(datiRichiesta.getNumeroDocumento())) {

                  index = index - 1;

                  error(
                      "Attenzione, è necessario inserire il numero documento o l'identificativo debitore");
                }

                if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getIdDeb())) {

                  String longInString = String.valueOf(datiRichiesta.getIdDeb());
                  if (PageUtil.isStringValid(longInString) && longInString.length() > 18) {
                    index = index - 1;

                    error("Identificativo debitore deve essere al massimo di 18 caratteri");
                  }
                }
              }
            }

            if (index == 3
                && !datiRichiesta
                    .getTipologiaRichiedente()
                    .value()
                    .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value())
                && ((LabelFdCUtil.checkIfNull(datiRichiesta.getListaAllegati()))
                    || (LabelFdCUtil.checkIfNotNull(datiRichiesta.getListaAllegati())
                        && LabelFdCUtil.checkEmptyList(datiRichiesta.getListaAllegati())))) {

              index = index - 1;

              error("Attenzione, è necessario allegare almeno un file");
            }

            if (index == 3
                && datiRichiesta
                    .getModalitaDiPagamento()
                    .value()
                    .equalsIgnoreCase(ModalitaPagamentoEnum.CAS.value())) {

              Double massimoInCassa = Double.valueOf(999.99);

              Double importo = Double.valueOf(0.0);

              if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTari())) {
                importo = importo.sum(importo, datiRichiesta.getEccTari());
              }

              if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTefa())) {
                importo = importo.sum(importo, datiRichiesta.getEccTefa());
              }

              if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTariR())) {
                importo = importo.sum(importo, datiRichiesta.getEccTariR());
              }

              log.debug("CP importo = " + importo);

              if (importo.compareTo(massimoInCassa) > 0) {
                index = index - 1;

                error(
                    "Attenzione, l'importo richiesto supera il massimo di 999,99 € ritirabili in Cassa");
              }
            }

            if (index == 3) {
              if (PageUtil.isStringValid(datiRichiesta.getCodiceFiscaleBeneficiario())
                  && PageUtil.isStringValid(datiRichiesta.getCodiceFiscaleRichiedente())
                  && datiRichiesta
                      .getCodiceFiscaleBeneficiario()
                      .equalsIgnoreCase(datiRichiesta.getCodiceFiscaleRichiedente())) {

                index = index - 1;

                error(
                    "Attenzione, il codice fiscale del richiedente non può coincidere con quello del beneficiario");
              }

              avanti.setLabel(
                  Model.of(
                      Application.get()
                          .getResourceSettings()
                          .getLocalizer()
                          .getString(
                              "RichiestaRimborsoTariEngEredePanel.invia",
                              RichiestaRimborsoTariEngEredePanel.this)));
            }

            if (index == 4) {
              try {

                if (LabelFdCUtil.checkIfNotNull(datiRichiesta)) {

                  log.debug(
                      "CP eccedenze prima = "
                          + datiRichiesta.isEccTariRichiedibile()
                          + "  - "
                          + datiRichiesta.isEccTefaRichiedibile()
                          + " - "
                          + datiRichiesta.isEccTariRealeRichiedibile());

                  setEccedenzeRichiedibili(datiRichiesta);

                  log.debug(
                      "CP eccedenze dopo = "
                          + datiRichiesta.isEccTariRichiedibile()
                          + "  - "
                          + datiRichiesta.isEccTefaRichiedibile()
                          + " - "
                          + datiRichiesta.isEccTariRealeRichiedibile());

                  if (datiRichiesta.isEccTariRichiedibile()) {

                    log.debug("isEccTariRichiedibile");

                    datiRichiesta.setEnteBeneficiarioTari(EnteBeneficiarioRimborsoEnum.ENTE);
                    datiRichiesta.setTipoRimborso(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO);

                    IstanzaRimborsoPOSTResponse responseRimborsoTARI =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTARI(datiRichiesta);

                    log.debug("CP responseRimborsoTARI = " + responseRimborsoTARI);

                    datiRichiesta.setResponsePostRimborsoTARI(responseRimborsoTARI);
                  }

                  if (datiRichiesta.isEccTefaRichiedibile()) {

                    log.debug("CP isEccTefaRichiedibile");

                    datiRichiesta.setEnteBeneficiarioTefa(EnteBeneficiarioRimborsoEnum.PROVINCIA);
                    datiRichiesta.setTipoRimborso(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO);

                    IstanzaRimborsoPOSTResponse responseRimborsoTEFA =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTEFA(datiRichiesta);

                    log.debug("CP responseRimborsoTEFA = " + responseRimborsoTEFA);

                    datiRichiesta.setResponsePostRimborsoTEFA(responseRimborsoTEFA);
                  }

                  if (datiRichiesta.isEccTariRealeRichiedibile()) {

                    log.debug("CP isEccTariRealeRichiedibile");

                    datiRichiesta.setEnteBeneficiarioTari(EnteBeneficiarioRimborsoEnum.ENTE);
                    datiRichiesta.setTipoRimborso(TipoRimborsoEnum.ECCEDENZAREALE);

                    IstanzaRimborsoPOSTResponse responseRimborsoTARI =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTARIREALE(datiRichiesta);

                    log.debug("CP responseRimborsoTARI Reale = " + responseRimborsoTARI);

                    datiRichiesta.setResponsePostRimborsoTARIREALE(responseRimborsoTARI);
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

                log.debug("CP errore POST rimborsi tari eng = " + messaggioRicevuto);

                Integer indexOf = messaggioRicevuto.indexOf(":");
                String messaggioDaVisualizzare =
                    messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

                error(messaggioDaVisualizzare);

              } catch (BusinessException e) {

                index = index - 1;
                error("Errore di sistema.");
                log.error("BusinessException gestito durante la chiamata delle API:", e);
              }
            }

            getTargetPanel(target, datiRichiesta);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoTariEngEredePanel.this);

            log.error("CP errore richiesta rimborso tari erede eng");
          }
        };

    if (index == 3) {
      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngEredePanel.invia",
                      RichiestaRimborsoTariEngEredePanel.this)));
    } else {
      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngEredePanel.avanti",
                      RichiestaRimborsoTariEngEredePanel.this)));
    }

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 2830354564887891653L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoTariEngEredePanel.this);

            form.clearInput();

            setResponsePage(new QuadroTributarioTariPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariEngEredePanel.annulla",
                    RichiestaRimborsoTariEngEredePanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      DatiRichiestaRimborsoTariEng datiRichiesta) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 2667893371632427204L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target, datiRichiesta);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariEngEredePanel.indietro",
                    RichiestaRimborsoTariEngEredePanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(
      AjaxRequestTarget target, DatiRichiestaRimborsoTariEng datiRimborso) {

    log.debug("CP getTargetPanel = " + index);

    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {

      indietro.setVisible(false);

      datiRimborsoPanel.setVisible(true);
      datiRimborsoPanel.setEnabled(true);

      datiRimborsoBeneficiarioPanel.setVisible(false);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngEredePanel.avanti",
                      RichiestaRimborsoTariEngEredePanel.this)));

      containerInfoInvia.setVisible(false);

    } else if (index == 2) {
      datiRimborsoPanel.setVisible(false);
      datiRimborsoPanel.setEnabled(false);

      datiRimborsoBeneficiarioPanel.setVisible(true);
      datiRimborsoBeneficiarioPanel.setEnabled(true);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      indietro.setVisible(true);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngEredePanel.avanti",
                      RichiestaRimborsoTariEngEredePanel.this)));

      containerInfoInvia.setVisible(false);

    } else if (index == 3) {
      datiRimborsoPanel.setVisible(true);
      datiRimborsoPanel.setEnabled(false);

      datiRimborsoBeneficiarioPanel.setVisible(true);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.fillDati(datiRimborso);
      listaAllegatiInLetturaPanel.setVisible(true);
      listaAllegatiInLetturaPanel.setEnabled(true);

      indietro.setVisible(true);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngEredePanel.invia",
                      RichiestaRimborsoTariEngEredePanel.this)));

      containerInfoInvia.setVisible(true);

    } else if (index == 4) {
      datiRimborsoPanel.setEnabled(false);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      datiRimborsoPanel.setVisible(false);
      datiRimborsoBeneficiarioPanel.setVisible(false);

      esitoRimborsoTariPanel.setVisible(true);
      esitoRimborsoTariPanel.setEnabled(true);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);

      containerInfoInvia.setVisible(false);

      esitoRimborsoTariPanel.esitoRimborso(datiRimborso);
    }

    target.add(this);

    return target;
  }

  private DatiRichiestaRimborsoTariEng setEccedenzeRichiedibili(
      DatiRichiestaRimborsoTariEng datiRichiesta) {

    Double importoZero = new Double(0.0);

    if (LabelFdCUtil.checkIfNotNull(datiRichiesta)) {

      if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTari())
          && Double.compare(datiRichiesta.getEccTari(), importoZero) > 0) {
        datiRichiesta.setEccTariRichiedibile(true);
      }

      if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTefa())
          && Double.compare(datiRichiesta.getEccTefa(), importoZero) > 0) {
        datiRichiesta.setEccTefaRichiedibile(true);
      }

      if (LabelFdCUtil.checkIfNotNull(datiRichiesta.getEccTariR())
          && Double.compare(datiRichiesta.getEccTariR(), importoZero) > 0) {
        datiRichiesta.setEccTariRealeRichiedibile(true);
      }
    }

    log.debug(
        "CP datiRichiesta =  "
            + datiRichiesta.isEccTariRichiedibile()
            + " - "
            + datiRichiesta.isEccTefaRichiedibile()
            + " - "
            + datiRichiesta.isEccTariRealeRichiedibile());

    return datiRichiesta;
  }
}
