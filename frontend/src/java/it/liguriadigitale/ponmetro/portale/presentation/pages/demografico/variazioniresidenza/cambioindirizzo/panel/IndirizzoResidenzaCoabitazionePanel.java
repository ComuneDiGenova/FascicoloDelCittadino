package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasAddress;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniDiResidenzaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioEnum;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.IndirizzoResidenzaCoabitazioneForm;
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

public class IndirizzoResidenzaCoabitazionePanel extends BasePanel {

  private static final long serialVersionUID = -6343824620315478258L;

  private IndirizzoResidenzaCoabitazioneForm form = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer alertViario = new WebMarkupContainer("alertViario");

  private WebMarkupContainer alertNumeroPratica = new WebMarkupContainer("alertNumeroPratica");

  private WebMarkupContainer alertComune = new WebMarkupContainer("alertComune");

  private WebMarkupContainer alertCoabitanteUgualeLoggato =
      new WebMarkupContainer("alertCoabitanteUgualeLoggato");

  public IndirizzoResidenzaCoabitazionePanel(
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

    alertNumeroPratica.setVisible(false);
    alertNumeroPratica.setOutputMarkupId(true);

    alertComune.setVisible(false);
    alertComune.setOutputMarkupId(true);

    alertCoabitanteUgualeLoggato.setVisible(false);
    alertCoabitanteUgualeLoggato.setOutputMarkupId(true);

    form = new IndirizzoResidenzaCoabitazioneForm("form", variazioniResidenza, getUtente());

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    form.addOrReplace(creaBottoneAnnulla(variazioniResidenza));
    form.addOrReplace(creaBottoneIndietro(variazioniResidenza));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    addOrReplace(form);

    addOrReplace(alertViario);
    addOrReplace(alertNumeroPratica);
    addOrReplace(alertComune);
    addOrReplace(alertCoabitanteUgualeLoggato);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -338088289232273624L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaCoabitazionePanel.this);

            try {

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

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getAutocompleteViario())) {

                if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                    && LabelFdCUtil.checkIfNotNull(
                        variazioniResidenza.getTipoVariazioneDiResidenza())
                    && PageUtil.isStringValid(variazioniResidenza.getCfCoabitante())) {

                  if (variazioniResidenza
                      .getTipoVariazioneDiResidenza()
                      .equals(VariazioniDiResidenzaEnum.CAMBIO_INDIRIZZO)) {

                    if (!variazioniResidenza
                        .getCfCoabitante()
                        .equalsIgnoreCase(variazioniResidenza.getCfRichiedente())) {

                      continua(variazioniResidenza);

                    } else {
                      alertCoabitanteUgualeLoggato.setVisible(true);
                      onError();
                    }

                  } else if (variazioniResidenza
                      .getTipoVariazioneDiResidenza()
                      .equals(VariazioniDiResidenzaEnum.RICHIESTA_RESIDENZA)) {

                    continua(variazioniResidenza);
                  }
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

          private void continua(VariazioniResidenza variazioniResidenza)
              throws BusinessException, ApiException, IOException {

            if (VariazioniResidenzaUtil.checkNumeroPratica(variazioniResidenza)) {
              if (VariazioniResidenzaUtil.checkComune(variazioniResidenza.getComuneAgEntrate())
                  && VariazioniResidenzaUtil.checkComune(
                      variazioniResidenza.getComuneNascitaProprietario())
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
                  variazioniResidenza.setTipoVia(
                      variazioniResidenza.getSelectToponomastica().getTipoVia());
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
                    variazioniResidenza.setCodiceComuneResidenzaProprietario(idComuneResidenzaProp);
                  }
                }

                if (PageUtil.isStringValid(variazioniResidenza.getCfCoabitante())) {
                  Residente datiResidenteCoabitante =
                      ServiceLocator.getInstance()
                          .getServiziAnagrafici()
                          .getDatiResidentePerApk(variazioniResidenza.getCfCoabitante());

                  if (LabelFdCUtil.checkIfNotNull(datiResidenteCoabitante)) {
                    ResidenteCpvHasAddress address = datiResidenteCoabitante.getCpvHasAddress();

                    if (LabelFdCUtil.checkIfNotNull(address)) {
                      String tipoVia = address.getClvToponymQualifier();
                      String descrizioneVia = address.getClvOfficialStreetName();
                      String numeroCivico = address.getClvStreenNumberOnly();
                      String esponente = address.getClvStreetNumberExponent();
                      String scala = address.getClvStair();
                      String interno = address.getClvFlatNumberOnly();
                      String internoEsponente = address.getClvFlatExponent();
                      String colore = address.getClvStreetNumberColor();

                      boolean checkTipoVia =
                          tipoVia.equalsIgnoreCase(variazioniResidenza.getTipoVia());

                      boolean checkDescrizioneVia =
                          descrizioneVia.equalsIgnoreCase(variazioniResidenza.getViario());

                      String numeroCivicoToponomastica = null;
                      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCivico())) {
                        numeroCivicoToponomastica = String.valueOf(variazioniResidenza.getCivico());
                      }
                      boolean checkNumeroCivico =
                          numeroCivico.equalsIgnoreCase(numeroCivicoToponomastica);

                      String esponenteToponomastica = "";
                      if (variazioniResidenza.getEsponente() != null) {
                        esponenteToponomastica = variazioniResidenza.getEsponente();
                      }
                      boolean checkEsponente = esponente.equalsIgnoreCase(esponenteToponomastica);

                      String scalaToponomastica = "";
                      if (variazioniResidenza.getScala() != null) {
                        scalaToponomastica = variazioniResidenza.getScala();
                      }
                      boolean checkScala = scala.equalsIgnoreCase(scalaToponomastica);

                      boolean checkInterno =
                          interno.equalsIgnoreCase(variazioniResidenza.getInterno());

                      String intenoEsponenteToponomastica = "";
                      if (variazioniResidenza.getInternoEsponente() != null) {
                        intenoEsponenteToponomastica = variazioniResidenza.getInternoEsponente();
                      }
                      boolean checkInternoEsponente =
                          internoEsponente.equalsIgnoreCase(intenoEsponenteToponomastica);

                      boolean checkColore =
                          colore.equalsIgnoreCase(variazioniResidenza.getColore());

                      if (checkTipoVia
                          && checkDescrizioneVia
                          && checkNumeroCivico
                          && checkEsponente
                          && checkScala
                          && checkInterno
                          && checkInternoEsponente
                          && checkColore) {

                        index = index + 1;

                        if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
                          setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
                        } else {
                          setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
                        }
                      } else {
                        error(
                            "L'indirizzo inserito non corrisponde all'indirizzo di residenza effettivo. Verifica di averlo inserito correttamente");
                        onError();
                      }
                    }
                  } else {
                    error(
                        "Il codice fiscale "
                            + variazioniResidenza.getCfCoabitante()
                            + " non risulta associato ad un cittadino residente nel Comune di Genova");
                    onError();
                  }
                }

              } else {

                alertNumeroPratica.setVisible(false);

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
              alertNumeroPratica.setVisible(true);
              onError();
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore step 2 cambio indirizzo");
            target.add(IndirizzoResidenzaCoabitazionePanel.this);
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "IndirizzoResidenzaCoabitazionePanel.avanti",
                    IndirizzoResidenzaCoabitazionePanel.this)));

    avanti.setOutputMarkupId(true);

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -1836786378421045559L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaCoabitazionePanel.this);

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
                    "IndirizzoResidenzaCoabitazionePanel.annulla",
                    IndirizzoResidenzaCoabitazionePanel.this)));

    return annulla;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(IndirizzoResidenzaCoabitazionePanel.this);

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
                    "IndirizzoResidenzaCoabitazionePanel.indietro",
                    IndirizzoResidenzaCoabitazionePanel.this)));

    return indietro;
  }

  public IndirizzoResidenzaCoabitazioneForm getForm() {
    return form;
  }

  public void setForm(IndirizzoResidenzaCoabitazioneForm form) {
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
        new NotificationPanel("feedback2Coab") {

          private static final long serialVersionUID = 8753127548998998347L;

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
