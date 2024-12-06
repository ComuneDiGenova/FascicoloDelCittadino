package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioEnum;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.IndirizzoResidenzaNuovoForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class IndirizzoResidenzaNuovoPanel extends BasePanel {

  private static final long serialVersionUID = 2419817311251141675L;

  private IndirizzoResidenzaNuovoForm form = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer alertViario = new WebMarkupContainer("alertViario");

  private WebMarkupContainer alertComune = new WebMarkupContainer("alertComune");

  public IndirizzoResidenzaNuovoPanel(
      String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.setVariazione(variazioniResidenza);

    fillDati(variazioniResidenza);
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    createFeedBackStep2Panel();

    alertViario.setVisible(false);
    alertViario.setOutputMarkupId(true);

    alertComune.setVisible(false);
    alertComune.setOutputMarkupId(true);

    form = new IndirizzoResidenzaNuovoForm("form", variazioniResidenza, getUtente());

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    form.addOrReplace(creaBottoneAnnulla(variazioniResidenza));
    form.addOrReplace(creaBottoneIndietro(variazioniResidenza));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    addOrReplace(form);

    addOrReplace(alertViario);
    addOrReplace(alertComune);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 6018267445510360466L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaNuovoPanel.this);

            if (variazioniResidenza.getNazionalita() == null) {
              if (getUtente()
                  .getDatiCittadinoResidente()
                  .getCpvHasCitizenship()
                  .getClvHasIdentifier()
                  .equalsIgnoreCase("100")) {
                variazioniResidenza.setNazionalita(NazionalitaProprietarioEnum.ITALIANO);

              } else {
                variazioniResidenza.setNazionalita(NazionalitaProprietarioEnum.STRANIERO);
              }
            }

            try {

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getAutocompleteViario())) {

                boolean controlloComuneNascita = true;
                if (!variazioniResidenza
                    .getComboTipoOccupazione()
                    .getCodice()
                    .equalsIgnoreCase("01")) {
                  controlloComuneNascita =
                      VariazioniResidenzaUtil.checkComune(
                          variazioniResidenza.getComuneNascitaProprietario());
                }

                if (VariazioniResidenzaUtil.checkComune(variazioniResidenza.getComuneAgEntrate())
                    /*&& VariazioniResidenzaUtil.checkComune(variazioniResidenza.getComuneNascitaProprietario()) */
                    && controlloComuneNascita
                    && VariazioniResidenzaUtil.checkComune(
                        variazioniResidenza.getComuneResidenzaProprietario())) {

                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getSelectToponomastica())) {
                    variazioniResidenza.setIdVia(
                        variazioniResidenza.getSelectToponomastica().getIdVia());
                    variazioniResidenza.setCivico(
                        variazioniResidenza.getSelectToponomastica().getNumeroCivico());
                    variazioniResidenza.setEsponente(
                        variazioniResidenza.getSelectToponomastica().getEsponente());
                    variazioniResidenza.setViario(
                        variazioniResidenza.getSelectToponomastica().getDescrizioneVia());
                    variazioniResidenza.setTipoVia(
                        variazioniResidenza.getSelectToponomastica().getTipoVia());
                    variazioniResidenza.setColore(
                        variazioniResidenza.getSelectToponomastica().getColore());
                    variazioniResidenza.setScala(
                        variazioniResidenza.getSelectToponomastica().getScala());
                    variazioniResidenza.setInterno(
                        variazioniResidenza.getSelectToponomastica().getInterno());
                    variazioniResidenza.setInternoEsponente(
                        variazioniResidenza.getSelectToponomastica().getInternoEsponente());
                    variazioniResidenza.setPiano(
                        variazioniResidenza.getSelectToponomastica().getPiano());
                    variazioniResidenza.setIdToponomastica(
                        variazioniResidenza.getSelectToponomastica().getCodiceToponomastica());
                  }

                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComuneAgEntrate())) {
                    String[] comuneAgEntrate = variazioniResidenza.getComuneAgEntrate().split("-");
                    String idComuneAgEntrate = comuneAgEntrate[comuneAgEntrate.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(idComuneAgEntrate)) {
                      variazioniResidenza.setCodiceComuneAgEntrate(idComuneAgEntrate);
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      variazioniResidenza.getComuneNascitaProprietario())) {
                    String[] comuneNascitaProp =
                        variazioniResidenza.getComuneNascitaProprietario().split("-");
                    String idComuneNascitaProp = comuneNascitaProp[comuneNascitaProp.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(idComuneNascitaProp)) {
                      variazioniResidenza.setCodiceComuneNascitaProprietario(idComuneNascitaProp);
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      variazioniResidenza.getComuneResidenzaProprietario())) {
                    String[] comuneResidenzaProp =
                        variazioniResidenza.getComuneResidenzaProprietario().split("-");
                    String idComuneResidenzaProp =
                        comuneResidenzaProp[comuneResidenzaProp.length - 1];
                    if (LabelFdCUtil.checkIfNotNull(idComuneResidenzaProp)) {
                      variazioniResidenza.setCodiceComuneResidenzaProprietario(
                          idComuneResidenzaProp);
                    }
                  }

                  if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
                    index = index + 1;
                    setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
                  } else {
                    index = index + 1;
                    setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
                  }

                } else {

                  boolean checkComuni =
                      !VariazioniResidenzaUtil.checkComune(variazioniResidenza.getComuneAgEntrate())
                          || (PageUtil.isStringValid(
                                  variazioniResidenza.getCountryNascitaProprietario())
                              && variazioniResidenza
                                  .getCountryNascitaProprietario()
                                  .equalsIgnoreCase("ITALIA")
                              && !VariazioniResidenzaUtil.checkComune(
                                  variazioniResidenza.getComuneNascitaProprietario()))
                          || !VariazioniResidenzaUtil.checkComune(
                              variazioniResidenza.getComuneResidenzaProprietario());
                  alertComune.setVisible(checkComuni);

                  onError();
                }

              } else {
                error("Attenzione, inserire via e numero civico");

                onError();
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante on submit step 2 cambio indirizzo: " + e.getMessage());

              onError();
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore step 2 cambio indirizzo: ");
            target.add(IndirizzoResidenzaNuovoPanel.this);
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoResidenzaNuovoPanel.avanti", IndirizzoResidenzaNuovoPanel.this)));

    avanti.setOutputMarkupId(true);

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -8059601490565411455L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaNuovoPanel.this);

            form.clearInput();

            if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
              setResponsePage(new CambioIndirizzoPage());
            } else {
              setResponsePage(new RichiestaResidenzaPage());
            }
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoResidenzaNuovoPanel.annulla", IndirizzoResidenzaNuovoPanel.this)));

    return annulla;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -108536943281479316L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaNuovoPanel.this);

            index = index - 1;

            if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
              setResponsePage(new CambioIndirizzoPage(index, variazione));
            } else {
              setResponsePage(new RichiestaResidenzaPage(index, variazione));
            }
          }
        };

    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoResidenzaNuovoPanel.indietro", IndirizzoResidenzaNuovoPanel.this)));

    return indietro;
  }

  public IndirizzoResidenzaNuovoForm getForm() {
    return form;
  }

  public void setForm(IndirizzoResidenzaNuovoForm form) {
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

  protected FeedbackPanel createFeedBackStep2Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback2") {

          private static final long serialVersionUID = -8510097378330901001L;

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
