package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.IndividuiCollegatiForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaResponseGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class IndividuiCollegatiPanel extends BasePanel {

  private static final long serialVersionUID = 1960526340083243850L;

  private IndividuiCollegatiForm form = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private ItemRelazioneParentale relazioneParentale;

  private FeedbackPanel feedbackPanel;

  public IndividuiCollegatiPanel(
      String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);
    this.setVariazione(variazioniResidenza);

    fillDati(variazioniResidenza);
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    createFeedBackStep3Panel();

    form = new IndividuiCollegatiForm("form", variazioniResidenza, getUtente());

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    form.addOrReplace(creaBottoneAnnulla(variazioniResidenza));
    form.addOrReplace(creaBottoneIndietro(variazioniResidenza));

    form.setOutputMarkupId(true);

    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -8979045244531426571L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiPanel.this);

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())) {

              IndividuiCollegati almenoUnaPersonaSelezionata =
                  variazioniResidenza.getListaIndividuiCollegati().stream()
                      .filter(elem -> elem.isSelezionato())
                      .findAny()
                      .orElse(null);

              List<IndividuiCollegati> listaIndividuiCollegatiSelezionati =
                  variazioniResidenza.getListaIndividuiCollegati().stream()
                      .filter(elem -> LabelFdCUtil.checkIfNotNull(elem) && elem.isSelezionato())
                      .collect(Collectors.toList());

              boolean tuttiISelezionatiHannoParentela =
                  listaIndividuiCollegatiSelezionati.stream()
                      .allMatch(
                          elem -> LabelFdCUtil.checkIfNotNull(elem.getParentelaConCoabitante()));

              boolean tuttiISelezionatiHannoSelezionatoPatenti =
                  listaIndividuiCollegatiSelezionati.stream()
                      .allMatch(elem -> LabelFdCUtil.checkIfNotNull(elem.getPossiedePatenti()));

              boolean tuttiISelezionatiHannoSelezionatoVeicoli =
                  listaIndividuiCollegatiSelezionati.stream()
                      .allMatch(elem -> LabelFdCUtil.checkIfNotNull(elem.getPossiedeVeicoli()));

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                  && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())
                  && LabelFdCUtil.checkIfNotNull(
                      variazioniResidenza.getComboTipologiaIscrizione().getCodiceFDC())
                  && variazioniResidenza
                      .getComboTipologiaIscrizione()
                      .getCodiceFDC()
                      .equalsIgnoreCase("2")) {

                if (tuttiISelezionatiHannoParentela) {
                  if (tuttiISelezionatiHannoSelezionatoPatenti) {
                    if (tuttiISelezionatiHannoSelezionatoVeicoli) {
                      checkPratica(variazioniResidenza, almenoUnaPersonaSelezionata);
                    } else {
                      error("Attenzione, è necessario selezionare se si possiedono veicoli");
                    }
                  } else {
                    error("Attenzione, è necessario selezionare se si possiedono patenti");
                  }
                } else {
                  error("Attenzione, è necessario selezionare la parentela");
                }
              } else {
                if (tuttiISelezionatiHannoSelezionatoPatenti) {
                  if (tuttiISelezionatiHannoSelezionatoVeicoli) {
                    checkPratica(variazioniResidenza, almenoUnaPersonaSelezionata);
                  } else {
                    error("Attenzione, è necessario selezionare se si possiedono veicoli");
                  }
                } else {
                  error("Attenzione, è necessario selezionare se si possiedono patenti");
                }
              }
            }
          }

          private void checkPratica(
              VariazioniResidenza variazioniResidenza,
              IndividuiCollegati almenoUnaPersonaSelezionata) {
            if (LabelFdCUtil.checkIfNotNull(almenoUnaPersonaSelezionata)) {

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfCoabitante())) {

                try {
                  Residente datiResidenteCoabitante =
                      ServiceLocator.getInstance()
                          .getServiziAnagrafici()
                          .getDatiResidentePerApk(variazioniResidenza.getCfCoabitante());

                  if (LabelFdCUtil.checkIfNotNull(datiResidenteCoabitante)) {

                    inserisciPratica(variazioniResidenza);
                  } else {
                    error(
                        "Attenzione, la persona presso la quale si sta chiedendo di andare a vivere non risiede nel Comune di Genova.");
                  }
                } catch (BusinessException | ApiException e1) {
                  log.error("Errore durante get dati residente coabitante: " + e1.getMessage());
                }

              } else {
                inserisciPratica(variazioniResidenza);
              }
            } else {
              error("Attenzione, è necessario selezionare almeno una persona");
            }
          }

          private void inserisciPratica(VariazioniResidenza variazioniResidenza) {
            try {

              List<IndividuiCollegati> listaIndividuiCollegatiSelezionati =
                  new ArrayList<IndividuiCollegati>();

              listaIndividuiCollegatiSelezionati =
                  variazioniResidenza.getListaIndividuiCollegati().stream()
                      .filter(elem -> LabelFdCUtil.checkIfNotNull(elem) && elem.isSelezionato())
                      .collect(Collectors.toList());
              variazioniResidenza.setListaIndividuiCollegati(listaIndividuiCollegatiSelezionati);

              PostInserimentoPraticaSospesaResponseGenericResponse praticaSospesa =
                  ServiceLocator.getInstance()
                      .getServiziAnagrafici()
                      .inserimentoPraticaSospesa(variazioniResidenza);
              if (!praticaSospesa.getStatus().equalsIgnoreCase("KO")
                  && praticaSospesa.getResult() != null
                  && praticaSospesa.getResult().getIdPratica() != null) {

                index = index + 1;

                Integer idPratica = praticaSospesa.getResult().getIdPratica();
                variazioniResidenza.setIdPratica(idPratica);

                setResponsePage(new CambioIndirizzoPage(index, variazione));

              } else {
                error("Errore durante salvataggio dati: " + praticaSospesa.getStatusDescription());
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore post inserimento pratica " + e.getMessage());
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiPanel.this);

            if (!variazioniResidenza.getListaIndividuiCollegati().stream()
                .filter(
                    elem ->
                        (variazioniResidenza
                                    .getComboTipologiaIscrizione()
                                    .getCodiceFDC()
                                    .equalsIgnoreCase("2")
                                || variazioniResidenza
                                    .getComboTipologiaIscrizione()
                                    .getCodiceFDC()
                                    .equalsIgnoreCase("4"))
                            && elem.getParentelaConCoabitante() == null)
                .collect(Collectors.toList())
                .isEmpty()) {
              error("Seleziona Nuova Parentela");
            }
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("IndividuiCollegatiPanel.avanti", IndividuiCollegatiPanel.this)));

    avanti.setOutputMarkupId(true);

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 7700342628425713144L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiPanel.this);

            form.clearInput();

            setResponsePage(new CambioIndirizzoPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("IndividuiCollegatiPanel.annulla", IndividuiCollegatiPanel.this)));

    return annulla;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -6895743600075958066L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiPanel.this);

            index = index - 1;

            setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
          }
        };

    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("IndividuiCollegatiPanel.indietro", IndividuiCollegatiPanel.this)));

    return indietro;
  }

  protected FeedbackPanel createFeedBackStep3Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback3") {

          private static final long serialVersionUID = -2238952659117450572L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public VariazioniResidenza getVariazione() {
    return variazione;
  }

  public void setVariazione(VariazioniResidenza variazione) {
    this.variazione = variazione;
  }

  public IndividuiCollegatiForm getForm() {
    return form;
  }

  public void setForm(IndividuiCollegatiForm form) {
    this.form = form;
  }

  public ItemRelazioneParentale getRelazioneParentale() {
    return relazioneParentale;
  }

  public void setRelazioneParentale(ItemRelazioneParentale relazioneParentale) {
    this.relazioneParentale = relazioneParentale;
  }
}
