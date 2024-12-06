package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step4;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.Step4IstanzaPlCaricaDocumentiIstanzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class Step4IstanzaPlCaricaDocumentiIstanzaPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Step4IstanzaPlCaricaDocumentiIstanzaForm form = null;

  public Step4IstanzaPlCaricaDocumentiIstanzaPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, index, datiRichiestaIstanzaPl);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    form =
        new Step4IstanzaPlCaricaDocumentiIstanzaForm("form", datiRichiestaIstanzaPl, feedbackPanel);

    log.error(
        "Step4IstanzaPlCaricaDocumentiIstanzaFormStep4IstanzaPlCaricaDocumentiIstanzaForm: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl);

    form.addOrReplace(creaBottoneAvanti(datiRichiestaIstanzaPl));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAnnulla(datiRichiestaIstanzaPl));
    addOrReplace(form);
    Label motivoScelto = new Label("motivoScelto", getStringaMotivoScelto(datiRichiestaIstanzaPl));
    form.addOrReplace(motivoScelto);
  }

  private LaddaAjaxButton creaBottoneAvanti(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 1031626225565871794L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(Step4IstanzaPlCaricaDocumentiIstanzaPanel.this);

            if (
            /*
             * datiRichiestaIstanzaPl.getDocumentiDaInviare().size() ==
             * datiRichiestaIstanzaPl .getDocumentiCaricatiHeader().size()
             */
            form.isObbligatoriCaricatiTutti(null)) {
              index = index + 1;
              log.debug(
                  "datiRichiestaIstanzaPl.getDocumentiDaInviare().size() - datiRichiestaIstanzaPl.getDocumentiCaricatiHeader().size()="
                      + (datiRichiestaIstanzaPl.getDocumentiDaInviare().size()
                          - datiRichiestaIstanzaPl.getDocumentiCaricatiHeader().size()));
              setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
            } else {
              log.error(
                  "datiRichiestaIstanzaPl.getDocumentiDaInviare().size() - datiRichiestaIstanzaPl.getDocumentiCaricatiHeader().size()="
                      + (datiRichiestaIstanzaPl.getDocumentiDaInviare().size()
                          - datiRichiestaIstanzaPl.getDocumentiCaricatiHeader().size()));
              log.debug("Per poter procedere e' necessario caricare tutti i file obbligatori.");
              warn("Per poter procedere e' necessario caricare tutti i file obbligatori.");
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step4IstanzaPlCaricaDocumentiIstanzaPanel.this);
            log.error("Attenzione, errore step 4 richiesta istanza PL");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaPanel.avanti",
                    Step4IstanzaPlCaricaDocumentiIstanzaPanel.this)));
    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(Step4IstanzaPlCaricaDocumentiIstanzaPanel.this);

            form.clearInput();
            // index = index - 1;
            // setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));

            if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl)
                && LabelFdCUtil.checkIfNotNull(
                    datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza())) {
              setResponsePage(
                  new DettaglioVerbaliPage(
                      datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza().getVerbale()));
            }
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step4IstanzaPlCaricaDocumentiIstanzaPanel.annulla",
                    Step4IstanzaPlCaricaDocumentiIstanzaPanel.this)));

    return annulla;
  }

  private String getStringaMotivoScelto(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    String key = "IstanzePlCodiceHermes." + datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    return getString(key);
  }
}
