package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step5;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.Step5IstanzaPlConfermaDatiIstanzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class Step5IstanzaPlConfermaDatiIstanzaPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Step5IstanzaPlConfermaDatiIstanzaForm form = null;

  public Step5IstanzaPlConfermaDatiIstanzaPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, index, datiRichiestaIstanzaPl);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    form = new Step5IstanzaPlConfermaDatiIstanzaForm("form", datiRichiestaIstanzaPl);

    log.error(
        "Step5IstanzaPlConfermaDatiIstanzaFormStep5IstanzaPlConfermaDatiIstanzaForm: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl);

    form.addOrReplace(creaBottoneAvanti(datiRichiestaIstanzaPl));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 1031626225565871794L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)) {
              try {
                EsitoOperazione esitoOperazione =
                    ServiceLocator.getInstance()
                        .getServiziMieiVerbali()
                        .concludiIstanza(datiRichiestaIstanzaPl);
                if (LabelFdCUtil.checkIfNotNull(esitoOperazione)
                    && esitoOperazione.getEsito().equalsIgnoreCase("OK")) {
                  index = index + 1;

                  datiRichiestaIstanzaPl.setEsitoInvioIstanza(esitoOperazione);

                  setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
                } else {
                  target.add(feedbackPanel);
                  error("Errore durante la convalida dell'istanza");
                }
              } catch (Exception e) {
                log.error("FRR errore post convalida istanza: ", e);
                error("Errore generico durante la convalida dell'istanza");
              }
            }
            target.add(feedbackPanel);
            target.add(Step5IstanzaPlConfermaDatiIstanzaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step5IstanzaPlConfermaDatiIstanzaPanel.this);

            log.error("Attenzione, errore step 5 richiesta istanza PL");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step5IstanzaPlConfermaDatiIstanzaPanel.avanti",
                    Step5IstanzaPlConfermaDatiIstanzaPanel.this)));

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(Step5IstanzaPlConfermaDatiIstanzaPanel.this);

            form.clearInput();

            index = index - 1;
            setResponsePage(new MieIstanzePage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step5IstanzaPlConfermaDatiIstanzaPanel.annulla",
                    Step5IstanzaPlConfermaDatiIstanzaPanel.this)));

    return annulla;
  }
}
