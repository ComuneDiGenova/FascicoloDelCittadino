package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Richiedente;
import java.util.List;

public class RichiestaCertificatoAnagraficoPageStep6 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;
  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoAnagraficoPageStep6(
      PostRichiestaEmissioneCertificatoRequest postRichiestaEmissioneCertificatoRequest,
      InformazioniAccessorieCertificato informazioni) {
    super();
    this.informazioni = informazioni;
    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoAnagraficoPage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(postRichiestaEmissioneCertificatoRequest);
    StepPanel stepPanel = new StepPanel("stepPanel", 6, lista);
    addOrReplace(stepPanel);
    createFeedBackPanel();
  }

  private void creaForm(PostRichiestaEmissioneCertificatoRequest request) {

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          @Override
          public void addElementiForm() {
            PostRichiestaEmissioneCertificatoRequest modelloForm = getModelObject();

            log.debug("PostRichiestaEmissioneCertificatoRequest=" + request);
            add(
                new CardLabel<>(
                    "cfIntestatario",
                    modelloForm.getEstremiIntestatario().getCodiceFiscale(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    modelloForm.getEstremiIntestatario().getCognome(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario",
                    modelloForm.getEstremiIntestatario().getNome(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "tipo",
                    informazioni.getDescrizioneTipoCertificato(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "dataDa",
                    getDataCertificatoStorico(modelloForm),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "dataA",
                    informazioni.getDataA(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "utilizzo",
                    informazioni.getDescrizioneMotivazioneRichiesta(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "bollo",
                    modelloForm.getImportoBollo(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "dataBollo",
                    getDataMarcaBollo(modelloForm),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "diritti",
                    modelloForm.getImportoDirittiSegreteria(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "consegna",
                    modelloForm.getTipoConsegnaScelta(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "email", modelloForm.getEmail(), RichiestaCertificatoAnagraficoPageStep6.this));
            add(
                new CardLabel<>(
                    "mobile",
                    informazioni.getTelefonoContatto(),
                    RichiestaCertificatoAnagraficoPageStep6.this));
            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError download = creaDownload(modelloForm);
            add(download);
          }

          @SuppressWarnings("rawtypes")
          private BottoneAJAXDownloadWithError creaDownload(
              PostRichiestaEmissioneCertificatoRequest modelloForm) {
            BottoneAJAXDownloadWithError download =
                new BottoneAJAXDownloadWithError(
                    "downloadPdf", RichiestaCertificatoAnagraficoPageStep6.this) {

                  private static final long serialVersionUID = 2717167982774170002L;

                  @Override
                  protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
                    GetScaricoCertificatoResponse response;
                    try {
                      response =
                          ServiceLocator.getInstance()
                              .getCertificatiAnagrafe()
                              .getScaricoCertificatoDopoPost(
                                  modelloForm, informazioni, getUtente());
                      FileDaScaricare fileDaScaricare = new FileDaScaricare();
                      fileDaScaricare.setFileBytes(response.getFile());
                      fileDaScaricare.setFileName(response.getNomeFile());
                      fileDaScaricare.setEsitoOK(true);
                      return fileDaScaricare;
                    } catch (ApiException | BusinessException e) {
                      // ServiceLocator.getInstance().getCertificatiAnagrafe().inviaRichiestaPerEmail(request,
                      // informazioni);
                      String prefisso = "GNC-000-Server was unable to process request. --->";
                      String message = e.getMessage();
                      FileDaScaricare fileDaScaricare = new FileDaScaricare();
                      if (message.contains(prefisso)) {
                        log.error("ERRORE API: " + e);
                        fileDaScaricare.setMessaggioErrore(message.replace(prefisso, ""));
                      } else {
                        fileDaScaricare.setMessaggioErrore(message);
                      }
                      fileDaScaricare.setEsitoOK(false);
                      return fileDaScaricare;
                    }
                  }
                };
            return download;
          }

          public Object getDataCertificatoStorico(
              PostRichiestaEmissioneCertificatoRequest modelloForm) {
            return modelloForm.getDataCertificatoStorico() != null
                ? modelloForm.getDataCertificatoStorico()
                : null;
          }

          public Object getDataMarcaBollo(PostRichiestaEmissioneCertificatoRequest modelloForm) {
            return modelloForm.getDataMarcaBollo() != null ? modelloForm.getDataMarcaBollo() : null;
          }

          @SuppressWarnings("unused")
          private Richiedente datiRichiedenteUgualiUtenteCollegato() {
            Richiedente estremi = new Richiedente();
            estremi.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
            estremi.setNome(getUtente().getNome());
            estremi.setCognome(getUtente().getCognome());
            return estremi;
          }

          @Override
          protected void onSubmit() {}
        };
    addOrReplace(form);
  }
}
