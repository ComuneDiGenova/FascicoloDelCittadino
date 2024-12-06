package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.CertificatoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova.form.PostRichiestaEmissioneCertificatoRequestExtend;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Certificato;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoAnagraficoPageStep2 extends LayoutNoFeedBackPage {

  private static final String _STORICO_DI_RESIDENZA_ALLADATA_ID = "9";
  private static final String _STORICO_DI_RESIDENZA_ID = "20";

  private static final long serialVersionUID = -5975503781218096405L;

  private InformazioniAccessorieCertificato informazioni;
  private boolean flagDefault;
  private boolean valoredefaultRichiededataInizioFine;
  private boolean valoredefaultRichiedeDataDa;

  public RichiestaCertificatoAnagraficoPageStep2(
      Intestatario intestatario,
      boolean flagChiamatoDaIndietroPage3,
      InformazioniAccessorieCertificato informazioniRicevute) {
    super();
    informazioni = new InformazioniAccessorieCertificato();

    if (flagChiamatoDaIndietroPage3) {
      this.informazioni = informazioniRicevute;
      log.debug("informazioniRicevute.certificato : " + informazioniRicevute.getCertificato());
      this.flagDefault = flagChiamatoDaIndietroPage3;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoAnagraficoPage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(intestatario);
    createFeedBackPanel();
    StepPanel stepPanel = new StepPanel("stepPanel", 2, lista);
    addOrReplace(stepPanel);
    setOutputMarkupId(true);
  }

  public RichiestaCertificatoAnagraficoPageStep2(
      Intestatario estremiRicevuti, InformazioniAccessorieCertificato informazioniRicevute) {
    this(estremiRicevuti, true, informazioniRicevute);
  }

  public RichiestaCertificatoAnagraficoPageStep2(Intestatario intestatario) {
    this(intestatario, false, new InformazioniAccessorieCertificato());
  }

  private void creaForm(Intestatario intestatario) {

    PostRichiestaEmissioneCertificatoRequestExtend request =
        new PostRichiestaEmissioneCertificatoRequestExtend();
    LocalDate dataCertificatoStoricoDa = request.getDataCertificatoStorico();
    // final LocalDate dataCertificatoStoricoDa =
    // getDataCertificatoStorico(dataCertificatoStoricoOffset);
    final LocalDate dataCertificatoStoricoA = informazioni.getDataA();

    request.setEstremiIntestatario(intestatario);
    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequestExtend> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequestExtend>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          DropDownChoice<Certificato> combo;
          FdCLocalDateTextfield dataInizio;
          FdCLocalDateTextfield dataFine;
          FdCLocalDateTextfield dataDa;

          @Override
          public void addElementiForm() {
            creaCombo();
            creaTextboxDataInizio();
            creaTextboxDataFine();
            creaTextboxdataDa();
          }

          public void creaTextboxdataDa() {
            dataDa = new FdCLocalDateTextfield("dataDa", Model.of(dataCertificatoStoricoDa));
            dataDa.addNoFutureValidator();
            dataDa.setLabel(Model.of("Alla data"));
            dataDa.setRequired(true);
            dataDa.setVisible(false);
            dataDa.setVisible(false);
            if (valoredefaultRichiedeDataDa) {
              dataDa.setVisible(valoredefaultRichiededataInizioFine);
              dataDa.setModelObject(informazioni.getDataDA());
            }
            add(dataDa);
          }

          public void creaTextboxDataInizio() {
            dataInizio =
                new FdCLocalDateTextfield("dataInizio", Model.of(dataCertificatoStoricoDa));
            dataInizio.addNoFutureValidator();
            dataInizio.setLabel(Model.of("Data da"));
            dataInizio.setRequired(true);
            dataInizio.setVisible(false);
            if (valoredefaultRichiededataInizioFine) {
              dataInizio.setVisible(valoredefaultRichiededataInizioFine);
              dataInizio.setModelObject(informazioni.getDataDA());
            }
            add(dataInizio);
          }

          public void creaTextboxDataFine() {
            dataFine = new FdCLocalDateTextfield("dataFine", Model.of(dataCertificatoStoricoA));
            dataFine.addNoFutureValidator();
            dataFine.setLabel(Model.of("Data a"));
            dataFine.setRequired(false);
            dataFine.setVisible(false);
            if (valoredefaultRichiededataInizioFine) {
              dataFine.setVisible(valoredefaultRichiededataInizioFine);
              dataFine.setModelObject(informazioni.getDataA());
            }
            add(dataFine);
          }

          @Override
          protected void onSubmit() {
            log.debug("modello onSubmit: " + getModelObject());
            PostRichiestaEmissioneCertificatoRequest modelloForm = copioDatiComboSuModelloForm();
            log.debug(
                "tipo certificato: "
                    + modelloForm.getTipoCertificato()
                    + " Informazioni: "
                    + informazioni.getCertificato());
            setResponsePage(new RichiestaCertificatoAnagraficoPageStep3(modelloForm, informazioni));
          }

          private String getDescrizioneCertificato() {
            Certificato cert = combo.getModelObject();
            return cert.getDescrizione();
          }

          private PostRichiestaEmissioneCertificatoRequest copioDatiComboSuModelloForm() {
            PostRichiestaEmissioneCertificatoRequest modelloForm = getModelObject();
            log.debug("modello copioDatiComboSuModelloForm: " + modelloForm);
            Certificato cert = combo.getModelObject();
            modelloForm.setCodiceCertificato(cert.getCodice());
            modelloForm.setImportoBollo(cert.getImportoBollo());
            modelloForm.setImportoDirittiSegreteria(cert.getImportoDirittiSegreteria());
            informazioni.setDescrizioneTipoCertificato(getDescrizioneCertificato());

            if (dataDa.getModelObject() != null) {
              informazioni.setDataDA(dataDa.getModelObject());
            }

            if (isTipoCertificatoRichiedeDataDa(cert)) {
              modelloForm.setDataCertificatoStorico(dataDa.getModelObject());
              informazioni.setDataA(null);
              informazioni.setDataDA(null);
            } else if (isTipoCertificatoRichiedeDataInizioFine(cert)) {
              informazioni.setDataA(dataFine.getModelObject());
              informazioni.setDataDA(dataInizio.getModelObject());
              modelloForm.setDataCertificatoStorico(null);
            } else {
              modelloForm.setDataCertificatoStorico(null);
              informazioni.setDataA(null);
              informazioni.setDataDA(null);
            }
            log.debug("dataCertificatoStorico: " + modelloForm.getDataCertificatoStorico());
            log.debug("dataDa: " + informazioni.getDataDA());
            log.debug("dataA: " + informazioni.getDataA());
            return modelloForm;
          }

          @SuppressWarnings("unchecked")
          private void creaCombo() {

            valoredefaultRichiededataInizioFine = false;
            valoredefaultRichiedeDataDa = false;

            CertificatoRenderer choiceRenderer = new CertificatoRenderer();
            Certificato cert = null;
            String codiceCertificato = getModelObject().getTipoCertificato();
            if (StringUtils.isNotEmpty(codiceCertificato)) {
              log.debug("codiceCertificato=" + codiceCertificato);
              cert = new Certificato();
              cert.setCodice(codiceCertificato);
            }
            combo =
                this.creaDropDownChoice(
                    this.getTipiCertificato(), "combo", choiceRenderer, Model.of(cert));
            combo.setOutputMarkupId(true);
            this.add(combo);
            combo.add(creaBehaviorComboTipoCertificato());
            setDefaultValue();
          }

          private void setDefaultValue() {
            if (flagDefault) {
              combo.setModelObject(informazioni.getCertificato());
              log.debug(
                  "data a: " + informazioni.getDataA() + "data da :" + informazioni.getDataDA());
              valoredefaultRichiededataInizioFine =
                  isTipoCertificatoRichiedeDataInizioFine(informazioni.getCertificato());
              valoredefaultRichiedeDataDa =
                  isTipoCertificatoRichiedeDataDa(informazioni.getCertificato());
            }
          }

          private boolean isTipoCertificatoRichiedeDataDa(Certificato model) {
            return (model.getCodice().equalsIgnoreCase(_STORICO_DI_RESIDENZA_ID));
          }

          private boolean isTipoCertificatoRichiedeDataInizioFine(Certificato model) {
            return (model.getCodice().equalsIgnoreCase(_STORICO_DI_RESIDENZA_ALLADATA_ID));
          }

          private AjaxFormComponentUpdatingBehavior creaBehaviorComboTipoCertificato() {
            return new AjaxFormComponentUpdatingBehavior("change") {
              private static final long serialVersionUID = 729725369588897805L;

              private Log log = LogFactory.getLog(getClass());

              @Override
              protected void onUpdate(AjaxRequestTarget target) {

                flagDefault = false;
                valoredefaultRichiededataInizioFine = false;
                valoredefaultRichiedeDataDa = false;

                Certificato model = combo.getModelObject();
                log.debug("modello onUpdate: " + model);
                informazioni.setCertificato(model);
                boolean tipoCertificatoRichiedeDataInizioFine =
                    isTipoCertificatoRichiedeDataInizioFine(model);
                dataInizio.setVisible(tipoCertificatoRichiedeDataInizioFine);
                dataFine.setVisible(tipoCertificatoRichiedeDataInizioFine);
                if (!tipoCertificatoRichiedeDataInizioFine) {
                  dataInizio.setModelObject(null);
                  dataFine.setModel(null);
                }
                boolean tipoCertificatoRichiedeDataDa = isTipoCertificatoRichiedeDataDa(model);
                dataDa.setVisible(tipoCertificatoRichiedeDataDa);
                if (!tipoCertificatoRichiedeDataDa) {
                  dataDa.setModelObject(null);
                }
                target.add(RichiestaCertificatoAnagraficoPageStep2.this);
              }
            };
          }

          private List<Certificato> getTipiCertificato() {
            List<Certificato> lista =
                ServiceLocator.getInstance()
                    .getCertificatiAnagrafe()
                    .getCertificati(getUtente(), request);
            log.debug("lista: " + lista.size());
            return lista;
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
        };

    addOrReplace(form);
    form.addOrReplace(creaBottoneAnnulla());
    form.addOrReplace(creaBottoneIndietro(intestatario));
  }

  // private LocalDate getDataCertificatoStorico(OffsetDateTime
  // dataCertificatoStoricoOffset) {
  // if (dataCertificatoStoricoOffset != null) {
  //
  // return
  // LocalDateUtil.convertiOffsetDateTimePerApk(dataCertificatoStoricoOffset);
  // } else {
  // return null;
  // }
  // }

  private LaddaAjaxLink<Object> creaBottoneIndietro(Intestatario intestatario) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep2.this);
            log.debug("click indietro " + intestatario);
            setResponsePage(new RichiestaCertificatoAnagraficoPage(intestatario));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoAnagraficoPage.indietro",
                    RichiestaCertificatoAnagraficoPageStep2.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep2.this);
            setResponsePage(new CertificatoAnagraficoPage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoAnagraficoPage.annulla",
                    RichiestaCertificatoAnagraficoPageStep2.this)));

    return annulla;
  }
}
