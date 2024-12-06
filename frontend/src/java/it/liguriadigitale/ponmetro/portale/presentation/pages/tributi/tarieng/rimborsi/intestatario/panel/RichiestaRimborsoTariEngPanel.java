package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel;

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
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.EnteBeneficiarioRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class RichiestaRimborsoTariEngPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 5360944546212511025L;

  private int index;

  private StepPanel stepPanel;

  private DatiRichiestaRimborsoTariEngPanel datiRimborsoPanel;

  private DatiRichiestaRimborsoBeneficiarioTariEngPanel datiRimborsoBeneficiarioPanel;

  private ListaFileAllegatiInLetturaPanel listaAllegatiInLetturaPanel;

  private EsitoRichiestaRimborsoTariEngPanel esitoRimborsoTariPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private WebMarkupContainer containerInfoInvia;

  public RichiestaRimborsoTariEngPanel(String id, DatiRichiestaRimborsoTariEng rimborso) {

    super(id, rimborso);
    setOutputMarkupId(true);

    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(rimborso);
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
        new DatiRichiestaRimborsoTariEngPanel("datiRimborsoPanel", datiRimborso, index);
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

  private FdcAjaxButton creaBottoneAvanti(DatiRichiestaRimborsoTariEng datiRimborso) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -3616457721114539243L;

          @SuppressWarnings("static-access")
          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            log.debug("CP click avanti rimborsi " + datiRimborso);

            if (index == 3
                && datiRimborso
                    .getModalitaDiPagamento()
                    .value()
                    .equalsIgnoreCase(ModalitaPagamentoEnum.CAS.value())) {
              Double massimoInCassa = Double.valueOf(999.99);

              Double importo = Double.valueOf(0.0);

              if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTari())) {
                importo = importo.sum(importo, datiRimborso.getEccTari());
              }

              if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTefa())) {
                importo = importo.sum(importo, datiRimborso.getEccTefa());
              }

              if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTariR())) {
                importo = importo.sum(importo, datiRimborso.getEccTariR());
              }

              log.debug("CP importo = " + importo);

              if (importo.compareTo(massimoInCassa) > 0) {
                index = index - 1;

                error(
                    "Attenzione, l'importo richiesto supera il massimo di 999,99 € ritirabili in Cassa");
              }
            }

            log.debug(
                "CP datiRimborso.getCodiceFiscaleDelegato() = "
                    + datiRimborso.getCodiceFiscaleDelegato());

            if (index == 3
                && PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                && ((LabelFdCUtil.checkIfNull(datiRimborso.getListaAllegati()))
                    || (LabelFdCUtil.checkIfNotNull(datiRimborso.getListaAllegati())
                        && LabelFdCUtil.checkEmptyList(datiRimborso.getListaAllegati())))) {

              index = index - 1;

              error("Attenzione, è necessario allegare almeno un file");
            }

            if (index == 4) {
              try {

                if (LabelFdCUtil.checkIfNotNull(datiRimborso)) {

                  if (datiRimborso.isEccTariRichiedibile()) {

                    log.debug("isEccTariRichiedibile");

                    datiRimborso.setEnteBeneficiarioTari(EnteBeneficiarioRimborsoEnum.ENTE);
                    datiRimborso.setTipoRimborso(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO);

                    IstanzaRimborsoPOSTResponse responseRimborsoTARI =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTARI(datiRimborso);

                    log.debug("CP responseRimborsoTARI = " + responseRimborsoTARI);

                    datiRimborso.setResponsePostRimborsoTARI(responseRimborsoTARI);
                  }

                  if (datiRimborso.isEccTefaRichiedibile()) {

                    log.debug("CP isEccTefaRichiedibile");

                    datiRimborso.setEnteBeneficiarioTefa(EnteBeneficiarioRimborsoEnum.PROVINCIA);
                    datiRimborso.setTipoRimborso(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO);

                    IstanzaRimborsoPOSTResponse responseRimborsoTEFA =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTEFA(datiRimborso);

                    log.debug("CP responseRimborsoTEFA = " + responseRimborsoTEFA);

                    datiRimborso.setResponsePostRimborsoTEFA(responseRimborsoTEFA);
                  }

                  if (datiRimborso.isEccTariRealeRichiedibile()) {

                    log.debug("CP isEccTariRealeRichiedibile");

                    datiRimborso.setEnteBeneficiarioTari(EnteBeneficiarioRimborsoEnum.ENTE);
                    datiRimborso.setTipoRimborso(TipoRimborsoEnum.ECCEDENZAREALE);

                    IstanzaRimborsoPOSTResponse responseRimborsoTARI =
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .setRichiestaRimborsoTARIREALE(datiRimborso);

                    log.debug("CP responseRimborsoTARI Reale = " + responseRimborsoTARI);

                    datiRimborso.setResponsePostRimborsoTARIREALE(responseRimborsoTARI);
                  }
                }

                // TODO controllare i SET nel business

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

            getTargetPanel(target, datiRimborso);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoTariEngPanel.this);

            log.error("CP errore richiesta rimborso tari eng");
          }
        };

    if (index == 3) {
      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngPanel.invia", RichiestaRimborsoTariEngPanel.this)));
    } else {
      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariEngPanel.avanti", RichiestaRimborsoTariEngPanel.this)));
    }

    avanti.setVisible(isRimborsoRichiedibile(datiRimborso));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 5077873414149737909L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoTariEngPanel.this);

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
                    "RichiestaRimborsoTariEngPanel.annulla", RichiestaRimborsoTariEngPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      DatiRichiestaRimborsoTariEng datiRimborso) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 687433858009824469L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target, datiRimborso);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariEngPanel.indietro", RichiestaRimborsoTariEngPanel.this)));

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
                      "RichiestaRimborsoTariEngPanel.avanti", RichiestaRimborsoTariEngPanel.this)));

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
                      "RichiestaRimborsoTariEngPanel.avanti", RichiestaRimborsoTariEngPanel.this)));

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
                      "RichiestaRimborsoTariEngPanel.invia", RichiestaRimborsoTariEngPanel.this)));

      containerInfoInvia.setVisible(true);

    } else if (index == 4) {
      datiRimborsoPanel.setEnabled(false);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      datiRimborsoPanel.setVisible(false);
      datiRimborsoBeneficiarioPanel.setVisible(false);
      esitoRimborsoTariPanel.setVisible(true);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);

      esitoRimborsoTariPanel.esitoRimborso(datiRimborso);

      containerInfoInvia.setVisible(false);
    }

    target.add(this);

    return target;
  }

  private boolean isRimborsoRichiedibile(DatiRichiestaRimborsoTariEng datiRichiesta) {
    return ServiceLocator.getInstance()
        .getServiziTariEng()
        .checkRimborsoIntestarioRichiedibile(datiRichiesta);
  }
}
