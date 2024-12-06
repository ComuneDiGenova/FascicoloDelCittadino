package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.form.IndividuiCollegatiResidenzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaResponseGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class IndividuiCollegatiResidenzaPanel extends BasePanel {

  private static final long serialVersionUID = 2850246698951487110L;

  private IndividuiCollegatiResidenzaForm form = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione;

  private WebMarkupContainer alertStatoComune = new WebMarkupContainer("alertStatoComune");

  private WebMarkupContainer alertStato = new WebMarkupContainer("alertStato");

  private WebMarkupContainer alertComune = new WebMarkupContainer("alertComune");

  private WebMarkupContainer alertCittadinanza = new WebMarkupContainer("alertCittadinanza");

  private WebMarkupContainer alertProfessione = new WebMarkupContainer("alertProfessione");

  private WebMarkupContainer alertResidente = new WebMarkupContainer("alertResidente");

  private FeedbackPanel feedbackPanel;

  public IndividuiCollegatiResidenzaPanel(
      String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);
    this.setVariazione(variazioniResidenza);

    this.individuiCollegatiImmigrazione = new IndividuiCollegatiImmigrazione();

    fillDati(variazioniResidenza);
  }

  public IndividuiCollegatiResidenzaPanel(
      String id,
      Integer index,
      VariazioniResidenza variazioniResidenza,
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);
    this.setVariazione(variazioniResidenza);

    this.individuiCollegatiImmigrazione = individuiCollegatiImmigrazione;

    fillDati(variazioniResidenza);
  }

  @Override
  public void fillDati(Object dati) {

    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    alertStatoComune.setOutputMarkupId(true);
    alertStatoComune.setOutputMarkupPlaceholderTag(true);
    alertStatoComune.setVisible(false);
    addOrReplace(alertStatoComune);

    alertComune.setOutputMarkupId(true);
    alertComune.setOutputMarkupPlaceholderTag(true);
    alertComune.setVisible(false);
    addOrReplace(alertComune);

    alertStato.setOutputMarkupId(true);
    alertStato.setOutputMarkupPlaceholderTag(true);
    alertStato.setVisible(false);
    addOrReplace(alertStato);

    alertCittadinanza.setOutputMarkupId(true);
    alertCittadinanza.setOutputMarkupPlaceholderTag(true);
    alertCittadinanza.setVisible(false);
    addOrReplace(alertCittadinanza);

    alertProfessione.setOutputMarkupId(true);
    alertProfessione.setOutputMarkupPlaceholderTag(true);
    alertProfessione.setVisible(false);
    addOrReplace(alertProfessione);

    alertResidente.setOutputMarkupId(true);
    alertResidente.setOutputMarkupPlaceholderTag(true);
    alertResidente.setVisible(false);
    addOrReplace(alertResidente);

    feedbackPanel = createFeedBackStep3Panel();

    form =
        new IndividuiCollegatiResidenzaForm(
            "form",
            variazioniResidenza,
            individuiCollegatiImmigrazione,
            getUtente(),
            feedbackPanel);

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    form.addOrReplace(creaBottoneAnnulla(variazioniResidenza));
    form.addOrReplace(creaBottoneIndietro(variazioniResidenza));
    form.addOrReplace(creaBottoneAggiungi(individuiCollegatiImmigrazione));

    form.setOutputMarkupId(true);

    addOrReplace(form);
  }

  public IndividuiCollegatiResidenzaForm getForm() {
    return form;
  }

  public void setForm(IndividuiCollegatiResidenzaForm form) {
    this.form = form;
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

  public IndividuiCollegatiImmigrazione getIndividuiCollegatiImmigrazione() {
    return individuiCollegatiImmigrazione;
  }

  public void setIndividuiCollegatiImmigrazione(
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione) {
    this.individuiCollegatiImmigrazione = individuiCollegatiImmigrazione;
  }

  private LaddaAjaxLink<Object> creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> avanti =
        new LaddaAjaxLink<Object>("avanti", Type.Primary) {

          private static final long serialVersionUID = 4682159494165439769L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiResidenzaPanel.this);

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(
                    variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
                && !LabelFdCUtil.checkEmptyList(
                    variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {

              int contatoreIndividui = -1;
              for (IndividuiCollegatiImmigrazione elem :
                  variazioniResidenza.getListaIndividuiCollegatiImmigrazione()) {
                elem.setCodiceIndividuo(String.valueOf(contatoreIndividui));
                contatoreIndividui = contatoreIndividui - 1;
              }

              try {

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

                  setResponsePage(new RichiestaResidenzaPage(index, variazione));

                } else {
                  error(
                      "Errore durante salvataggio dati: " + praticaSospesa.getStatusDescription());
                }

              } catch (BusinessException | ApiException | IOException e) {
                log.error("CP errore post inserimento pratica " + e.getMessage());
              }

            } else {
              error("Attenzione, è necessario aggiungere almeno una persona");
            }
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaPanel.avanti",
                    IndividuiCollegatiResidenzaPanel.this)));

    avanti.setOutputMarkupId(true);

    avanti.setVisible(
        LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && LabelFdCUtil.checkIfNotNull(
                variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
            && !LabelFdCUtil.checkEmptyList(
                variazioniResidenza.getListaIndividuiCollegatiImmigrazione()));

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -9137101545894700103L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiResidenzaPanel.this);

            form.clearInput();

            setResponsePage(new RichiestaResidenzaPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaPanel.annulla",
                    IndividuiCollegatiResidenzaPanel.this)));

    return annulla;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 5199743454622823419L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiResidenzaPanel.this);

            index = index - 1;

            setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
          }
        };

    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaPanel.indietro",
                    IndividuiCollegatiResidenzaPanel.this)));

    return indietro;
  }

  private LaddaAjaxButton creaBottoneAggiungi(
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione) {
    LaddaAjaxButton aggiungi =
        new LaddaAjaxButton("aggiungi", Type.Primary) {

          private static final long serialVersionUID = 1298484282934091238L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(IndividuiCollegatiResidenzaPanel.this);

            try {

              if (VariazioniResidenzaUtil.checkIfIndividuoAggiuntoIsLoggatoIsResidente(
                  individuiCollegatiImmigrazione)) {

                if (VariazioniResidenzaUtil.checkComune(
                        individuiCollegatiImmigrazione.getComuneMatrimonio())
                    && VariazioniResidenzaUtil.checkComune(
                        individuiCollegatiImmigrazione.getComuneCi())
                    && VariazioniResidenzaUtil.checkComune(
                        individuiCollegatiImmigrazione.getComuneNascita())
                    && VariazioniResidenzaUtil.checkCittadinanza(
                        individuiCollegatiImmigrazione.getCittadinanza())
                    && VariazioniResidenzaUtil.checkProfessione(
                        individuiCollegatiImmigrazione.getProfessione())) {

                  if (PageUtil.isStringValid(individuiCollegatiImmigrazione.getNome())
                      && PageUtil.isStringValid(individuiCollegatiImmigrazione.getCognome())) {
                    individuiCollegatiImmigrazione.setNominativo(
                        individuiCollegatiImmigrazione
                            .getCognome()
                            .concat(" ")
                            .concat(individuiCollegatiImmigrazione.getNome()));
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      individuiCollegatiImmigrazione.getComuneMatrimonio())) {
                    String[] comuneMatrimonio =
                        individuiCollegatiImmigrazione.getComuneMatrimonio().split("-");
                    String idComuneMatrimonio = comuneMatrimonio[comuneMatrimonio.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(comuneMatrimonio)) {
                      individuiCollegatiImmigrazione.setCodiceComuneMatrimonio(idComuneMatrimonio);
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      individuiCollegatiImmigrazione.getComuneNascita())) {
                    String[] comuneNascita =
                        individuiCollegatiImmigrazione.getComuneNascita().split("-");
                    String idComuneNascita = comuneNascita[comuneNascita.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(comuneNascita)) {
                      individuiCollegatiImmigrazione.setCodiceComuneNascita(idComuneNascita);
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      individuiCollegatiImmigrazione.getComuneNascitaEstero())) {

                    if (individuiCollegatiImmigrazione.getComuneNascitaEstero().contains("-")) {
                      String[] statoNascita =
                          individuiCollegatiImmigrazione.getComuneNascitaEstero().split("-");
                      String idStatoNascita = statoNascita[statoNascita.length - 1];
                      if (LabelFdCUtil.checkIfNotNull(statoNascita)) {
                        individuiCollegatiImmigrazione.setCodiceComuneNascita(idStatoNascita);
                      }
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(individuiCollegatiImmigrazione.getComuneCi())) {
                    String[] comuneCi = individuiCollegatiImmigrazione.getComuneCi().split("-");
                    String idComuneCi = comuneCi[comuneCi.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(comuneCi)) {
                      individuiCollegatiImmigrazione.setCodiceComuneRilascioCI(idComuneCi);
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(variazione)
                      && LabelFdCUtil.checkIfNotNull(individuiCollegatiImmigrazione)) {
                    individuiCollegatiImmigrazione.setEta(
                        LocalDateUtil.calcolaEta(individuiCollegatiImmigrazione.getDataNascita()));
                  }

                  List<IndividuiCollegatiImmigrazione> listaIndividuiCollegatiImmigrazione =
                      new ArrayList<IndividuiCollegatiImmigrazione>();
                  if (LabelFdCUtil.checkIfNotNull(
                      variazione.getListaIndividuiCollegatiImmigrazione())) {
                    listaIndividuiCollegatiImmigrazione =
                        variazione.getListaIndividuiCollegatiImmigrazione();
                  }
                  listaIndividuiCollegatiImmigrazione.add(individuiCollegatiImmigrazione);

                  variazione.setAggiungi(true);

                  variazione.setListaIndividuiCollegatiImmigrazione(
                      listaIndividuiCollegatiImmigrazione);

                  setResponsePage(new RichiestaResidenzaPage(index, variazione));

                } else {
                  alertComune.setVisible(
                      (!VariazioniResidenzaUtil.checkComune(
                              individuiCollegatiImmigrazione.getComuneMatrimonio()))
                          || (!VariazioniResidenzaUtil.checkComune(
                              individuiCollegatiImmigrazione.getComuneCi()))
                          || (!VariazioniResidenzaUtil.checkComune(
                              individuiCollegatiImmigrazione.getComuneNascita())));

                  alertCittadinanza.setVisible(
                      !VariazioniResidenzaUtil.checkCittadinanza(
                          individuiCollegatiImmigrazione.getCittadinanza()));

                  alertProfessione.setVisible(
                      !VariazioniResidenzaUtil.checkProfessione(
                          individuiCollegatiImmigrazione.getProfessione()));

                  onError();
                }
              } else {
                alertResidente.setVisible(
                    !VariazioniResidenzaUtil.checkIfIndividuoAggiuntoIsLoggatoIsResidente(
                        individuiCollegatiImmigrazione));

                onError();
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore durante aggiungi individuo: " + e.getMessage());
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore step 3 richiesta indirizzo");
            target.add(IndividuiCollegatiResidenzaPanel.this);
          }
        };
    aggiungi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndividuiCollegatiResidenzaPanel.aggiungi",
                    IndividuiCollegatiResidenzaPanel.this)));

    aggiungi.setOutputMarkupId(true);

    return aggiungi;
  }

  protected FeedbackPanel createFeedBackStep3Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback3") {

          private static final long serialVersionUID = -349571325056835356L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }
}
