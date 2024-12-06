package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.AttoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.CertificatoStatoCivilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Atto;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RichiestaCertificatoStatoCivilePageStep2 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;
  private boolean flagDefault;
  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoStatoCivilePageStep2(
      Intestatario intestatario,
      boolean flagChiamatoDaIndietroPage3,
      InformazioniAccessorieCertificato informazioniRicevute) {
    super();
    informazioni = informazioniRicevute;
    flagDefault = flagChiamatoDaIndietroPage3;
    log.debug("flagDefault: " + flagDefault);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoStatoCivilePage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(intestatario);
    StepPanel stepPanel = new StepPanel("stepPanel", 2, lista);
    addOrReplace(stepPanel);
  }

  public RichiestaCertificatoStatoCivilePageStep2(
      Intestatario intestatario, InformazioniAccessorieCertificato informazioni) {
    this(intestatario, true, informazioni);
  }

  public RichiestaCertificatoStatoCivilePageStep2(Intestatario intestatario) {
    this(intestatario, false, new InformazioniAccessorieCertificato());
  }

  private void creaForm(Intestatario intestatario) {

    PostRichiestaEmissioneCertificatoAttoRequest request =
        new PostRichiestaEmissioneCertificatoAttoRequest();
    request.setEstremiIntestatario(intestatario);
    LocalDate dataCertificatoStorico = request.getDataCertificatoStorico();

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          DropDownChoice<Atto> combo;
          FdCLocalDateTextfield dataInizio;
          WebMarkupContainer containerInfoData;

          @Override
          public void addElementiForm() {
            creaComboAtti();
            creaTextboxDataDa();
            createFeedBackPanel();
          }

          @SuppressWarnings({"unchecked", "rawtypes"})
          public void creaTextboxDataDa() {

            log.debug("CP dataCertificatoStorico = " + dataCertificatoStorico);

            // dataInizio = new FdCLocalDateTextfield("dataDa",
            // Model.of(dataCertificatoStorico));
            dataInizio =
                new FdCLocalDateTextfield(
                    "dataDa", new PropertyModel(getModelObject(), "dataCertificatoStorico"));

            dataInizio.addNoFutureValidator();
            dataInizio.setRequired(true);
            // if (dataCertificatoStorico == null) {
            // dataInizio.setVisible(false);
            // } else {
            // dataInizio.setVisible(true);
            // }
            dataInizio.setLabel(Model.of("Data evento"));
            dataInizio.addNoFutureAndNoTodayValidator();
            dataInizio.setOutputMarkupId(true);
            dataInizio.setOutputMarkupPlaceholderTag(true);
            add(dataInizio);

            if (flagDefault) {
              log.debug("SETMODELOBJECT DEFAULT:" + informazioni.getDataDA());
              dataInizio.setModelObject(informazioni.getDataDA());
            }

            containerInfoData = new WebMarkupContainer("containerInfoData");
            containerInfoData.setOutputMarkupId(true);
            containerInfoData.setOutputMarkupPlaceholderTag(true);
            containerInfoData.setVisible(false);
            addOrReplace(containerInfoData);
          }

          private List<Atto> getAtti() {
            List<Atto> lista =
                ServiceLocator.getInstance()
                    .getCertificatiStatoCivile()
                    .getRicercaAtti(getUtente(), request);
            log.debug("lista: " + lista.size());
            return lista;
          }

          @SuppressWarnings("unchecked")
          private void creaComboAtti() {
            AttoRenderer choiceRenderer = new AttoRenderer();
            Atto atto = null;
            String codiceCertificato = getModelObject().getCodiceCertificato();
            if (StringUtils.isNotEmpty(codiceCertificato)) {
              atto = new Atto();
              atto.setCodice(codiceCertificato);
            }
            combo =
                this.creaDropDownChoice(
                    this.getAtti(), "comboAtti", choiceRenderer, Model.of(atto));
            combo.setOutputMarkupId(true);
            this.add(combo);
            combo.add(creaBehaviorComboTipoCertificato());
            setDefaultValue();
          }

          private void setDefaultValue() {
            if (flagDefault) {
              log.debug("SETMODELOBJECT DEFAULT:" + informazioni.getAtto());
              combo.setModelObject(informazioni.getAtto());
            }
          }

          private AjaxFormComponentUpdatingBehavior creaBehaviorComboTipoCertificato() {
            return new AjaxFormComponentUpdatingBehavior("change") {
              private static final long serialVersionUID = 729725369588897805L;

              private Log log = LogFactory.getLog(getClass());

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                Atto model = combo.getModelObject();
                log.debug("modello: " + model);
                dataInizio.setLabel(Model.of("Data evento"));
                informazioni.setAtto(model);

                Label testoInfo = new Label("testoInfo", "");
                if (LabelFdCUtil.checkIfNotNull(model)
                    && PageUtil.isStringValid(model.getCodice())) {
                  if (model.getCodice().equalsIgnoreCase("na01c")
                      || model.getCodice().equalsIgnoreCase("na08")
                      || model.getCodice().equalsIgnoreCase("na09")
                      || model.getCodice().equalsIgnoreCase("na10")) {
                    testoInfo = new Label("testoInfo", "Indicare data nascita");

                    try {
                      if (LabelFdCUtil.checkIfNotNull(request)
                          && LabelFdCUtil.checkIfNotNull(request.getEstremiIntestatario())
                          && PageUtil.isStringValid(
                              request.getEstremiIntestatario().getCodiceFiscale())) {
                        String cfCertificato = request.getEstremiIntestatario().getCodiceFiscale();

                        Residente datiResidente =
                            ServiceLocator.getInstance()
                                .getServiziAnagrafici()
                                .getDatiResidentePerApk(cfCertificato);

                        if (LabelFdCUtil.checkIfNotNull(datiResidente)
                            && LabelFdCUtil.checkIfNotNull(datiResidente.getCpvDateOfBirth())) {
                          LocalDate dataNascita = datiResidente.getCpvDateOfBirth();
                          request.setDataCertificatoStorico(dataNascita);
                        }
                      }
                    } catch (BusinessException | ApiException e) {
                      log.error(
                          "Errore get dati residente per certificato stato civile: "
                              + e.getMessage());
                    }

                  } else if (model.getCodice().equalsIgnoreCase("mo07")
                      || model.getCodice().equalsIgnoreCase("mo08")) {
                    testoInfo = new Label("testoInfo", "Indicare data morte");

                    request.setDataCertificatoStorico(null);
                  } else if (model.getCodice().equalsIgnoreCase("ma07")
                      || model.getCodice().equalsIgnoreCase("ma08")) {
                    testoInfo = new Label("testoInfo", "Indicare data matrimonio");

                    request.setDataCertificatoStorico(null);
                  } else if (model.getCodice().equalsIgnoreCase("uc02")) {
                    testoInfo = new Label("testoInfo", "Indicare data unione civile");

                    request.setDataCertificatoStorico(null);
                  } else {
                    containerInfoData.addOrReplace(testoInfo);
                    containerInfoData.setVisible(false);

                    request.setDataCertificatoStorico(null);
                  }

                  containerInfoData.addOrReplace(testoInfo);
                  containerInfoData.setVisible(true);

                } else {
                  containerInfoData.addOrReplace(testoInfo);
                  containerInfoData.setVisible(false);

                  request.setDataCertificatoStorico(null);
                }

                target.add(RichiestaCertificatoStatoCivilePageStep2.this);
              }

              @SuppressWarnings("unused")
              private boolean isTipoCertificatoRichiedeData(Atto model) {
                if (isTipoCertificatoRichiedeAltraPersona()) {
                  return true;
                } else {
                  return (model.getDescrizione().contains("morte"));
                }
              }
            };
          }

          private void createFeedBackPanel() {
            NotificationPanel feedback =
                new NotificationPanel("feedback") {

                  private static final long serialVersionUID = -883302032153540620L;

                  /* PANEL senza X per la chiusura */

                  @Override
                  protected boolean isCloseButtonVisible() {
                    return false;
                  }
                };
            feedback.setOutputMarkupId(true);
            this.add(feedback);
          }

          @Override
          protected void onSubmit() {
            PostRichiestaEmissioneCertificatoAttoRequest modelloForm =
                copioDatiComboSuModelloForm();
            informazioni = creoInformazioniAccessorie();
            log.debug("onSubmit informazioni.atto: " + informazioni.getAtto());

            if (isTipoCertificatoRichiedeAltraPersona()) {
              setResponsePage(
                  new RichiestaCertificatoStatoCivilePageStep3(modelloForm, informazioni));
            } else {
              setResponsePage(
                  new RichiestaCertificatoStatoCivilePageStep4(modelloForm, informazioni));
            }
          }

          private boolean isTipoCertificatoRichiedeAltraPersona() {

            if (combo.getModelObject().getDescrizione().contains("matrimonio")) {
              return true;
            } else if (combo.getModelObject().getDescrizione().contains("unione civile")) {
              return true;
            } else if (combo.getModelObject().getDescrizione().contains("Unione Civile")) {
              return true;
            } else if (combo.getModelObject().getDescrizione().contains("Matrimonio")) {
              return true;
            } else {
              return false;
            }
          }

          private InformazioniAccessorieCertificato creoInformazioniAccessorie() {
            Atto atto = combo.getModelObject();
            informazioni.setDescrizioneTipoCertificato(atto.getDescrizione());
            return informazioni;
          }

          private PostRichiestaEmissioneCertificatoAttoRequest copioDatiComboSuModelloForm() {
            PostRichiestaEmissioneCertificatoAttoRequest modelloForm = getModelObject();
            Atto atto = combo.getModelObject();
            modelloForm.setCodiceCertificato(atto.getCodice());
            LocalDate dataDa = dataInizio.getModelObject();
            informazioni.setDataDA(dataDa);
            log.debug("dataDa: " + dataDa);
            if (dataDa == null) {
              modelloForm.setDataCertificatoStorico(null);
            } else {
              // modelloForm.setDataCertificatoStorico(LocalDateUtil.getOffsetDateTime(dataDa));
              modelloForm.setDataCertificatoStorico(dataDa);
            }
            log.debug("modelloCombo: " + modelloForm);
            return modelloForm;
          }
        };

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    // addOrReplace(boxMessaggi);

    addOrReplace(form);
    form.addOrReplace(creaBottoneAnnulla());
    form.addOrReplace(creaBottoneIndietro(intestatario));
    form.addOrReplace(boxMessaggi);
  }

  @SuppressWarnings("unused")
  private LocalDate getDataCertificatoStorico(OffsetDateTime dataCertificatoStoricoOffset) {
    if (dataCertificatoStoricoOffset != null) {
      return dataCertificatoStoricoOffset.toLocalDate();
    } else {
      return null;
    }
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(Intestatario intestatario) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep2.this);
            log.debug("CP click indietro " + intestatario);
            setResponsePage(new RichiestaCertificatoStatoCivilePage(intestatario));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoStatoCivilePage.indietro",
                    RichiestaCertificatoStatoCivilePageStep2.this)));
    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep2.this);

            setResponsePage(new CertificatoStatoCivilePage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoStatoCivilePage.annulla",
                    RichiestaCertificatoStatoCivilePageStep2.this)));

    return annulla;
  }
}
