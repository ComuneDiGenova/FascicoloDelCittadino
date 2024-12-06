package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step3;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.Step3IstanzaPlAltriVerbaliIstanzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class Step3IstanzaPlAltriVerbaliIstanzaPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Step3IstanzaPlAltriVerbaliIstanzaForm form = null;

  private Integer index;

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public Step3IstanzaPlAltriVerbaliIstanzaPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, index, datiRichiestaIstanzaPl);
    this.datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;

    log.debug("CP Step3IstanzaPlAltriVerbaliIstanzaPanel = " + index);

    setIndex(index);

    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    form = new Step3IstanzaPlAltriVerbaliIstanzaForm("form", datiRichiestaIstanzaPl, getIndex());

    log.debug(
        "Step3IstanzaPlAltriVerbaliIstanzaFormStep3IstanzaPlAltriVerbaliIstanzaForm: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl.getCodiceHermesAnagrafica());

    form.setComponentToRefresh(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);
    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAvanti(datiRichiestaIstanzaPl));
    form.addOrReplace(creaBottoneIndietro(datiRichiestaIstanzaPl));
    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);

    Label motivoScelto = new Label("motivoScelto", getStringaMotivoScelto(datiRichiestaIstanzaPl));
    form.addOrReplace(motivoScelto);
  }

  private LaddaAjaxButton creaBottoneAvanti(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "Step3IstanzaPlAltriVerbaliIstanzaPanel creaBottoneAvanti -- AVANTI ------------");
            if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)) {
              if ("01".equalsIgnoreCase(datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato())
                  && datiRichiestaIstanzaPl.getNumeroVerbaliUlterioriCompatibiliSelezionati() < 1) {
                error("Per il motivo scelto, e' necessario selezionare almeno un verbale");
              } else {
                try {
                  log.debug(
                      "Step3IstanzaPlAltriVerbaliIstanzaPanel creaBottoneAvanti -- provo ad inserire ------------");
                  Istanza istanza =
                      ServiceLocator.getInstance()
                          .getServiziMieiVerbali()
                          .inserisciIstanza(datiRichiestaIstanzaPl);
                  log.debug(
                      "Step3IstanzaPlAltriVerbaliIstanzaPanel creaBottoneAvanti -- inserisciIstanza ------------");
                  if (istanza != null && istanza.getId() != null && istanza.getId() != 0) {

                    index = index + 1;

                    log.debug(
                        "Step3IstanzaPlAltriVerbaliIstanzaPanel ISTANZAAAAAAAAAAAAa :::: "
                            + istanza);

                    datiRichiestaIstanzaPl.setIstanza(istanza);

                    setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
                  } else {
                    error("Errore durante la richiesta di inserimento ISTANZA");
                  }

                } catch (Exception e) {
                  log.error("FRR errore post inserimento pratica: " + e.getMessage());
                  log.error("FRR errore post inserimento pratica STACKK : ", e);
                  error("Errore durante la richiesta di inserimento ISTANZA");
                }
              }
            } else {
              error("Dati nulli, riprovare o contattare l'amministratore di sistema");
            }
            target.add(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);
          }

          // index = index + 1;
          //
          // setResponsePage(new CreaIstanzaPLPage(index,
          // datiRichiestaIstanzaPl));
          // }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);

            log.error("Attenzione, errore step 3 richiesta istanza PL");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step3IstanzaPlAltriVerbaliIstanzaPanel.avanti",
                    Step3IstanzaPlAltriVerbaliIstanzaPanel.this)));
    /*
     * boolean boEnable = false; log.
     * error("FRRFRRFRR1 creaBottoneAvanti getCodiceHermesMotivoSelezionato " +
     * datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato()); if
     * ("01".equalsIgnoreCase(datiRichiestaIstanzaPl.
     * getCodiceHermesMotivoSelezionato())) { int numero = datiRichiestaIstanzaPl.
     * getNumeroVerbaliUlterioriCompatibiliSelezionati();
     * log.error("FRRFRRFRR1 creaBottoneAvanti numeronumeronumeronumero " + numero);
     * boEnable = numero > 0; } else { boEnable = true; }
     * log.error("FRRFRRFRR1 creaBottoneAvanti boEnable " + boEnable);
     * avanti.setEnabled(boEnable);
     */
    return avanti;
  }

  private LaddaAjaxButton creaBottoneIndietro(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    LaddaAjaxButton indietro =
        new LaddaAjaxButton("indietro", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);

            index = index - 1;

            setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);

            log.error("Attenzione, errore step 3 richiesta istanza PL");
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step3IstanzaPlAltriVerbaliIstanzaPanel.indietro",
                    Step3IstanzaPlAltriVerbaliIstanzaPanel.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(Step3IstanzaPlAltriVerbaliIstanzaPanel.this);

            form.clearInput();
            index = index - 1;
            setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step3IstanzaPlAltriVerbaliIstanzaPanel.annulla",
                    Step3IstanzaPlAltriVerbaliIstanzaPanel.this)));

    return annulla;
  }

  private String getStringaMotivoScelto(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    String key = "IstanzePlCodiceHermes." + datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    return getString(key);
  }
}
