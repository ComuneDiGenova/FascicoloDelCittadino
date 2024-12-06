package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.nuova;

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
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoAttiResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Richiedente;
import java.util.List;
import org.apache.wicket.Component;

public class RichiestaCertificatoStatoCivilePageStep6 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoStatoCivilePageStep6(
      PostRichiestaEmissioneCertificatoAttoRequest postRichiestaEmissioneCertificatoAttoRequest,
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
        RichiestaCertificatoStatoCivilePage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(postRichiestaEmissioneCertificatoAttoRequest);
    StepPanel stepPanel = new StepPanel("stepPanel", 6, lista);
    addOrReplace(stepPanel);
    createFeedBackPanel();
    log.debug(
        "postRichiestaEmissioneCertificatoAttoRequest:"
            + postRichiestaEmissioneCertificatoAttoRequest);
  }

  private void creaForm(PostRichiestaEmissioneCertificatoAttoRequest request) {

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          @Override
          public void addElementiForm() {
            PostRichiestaEmissioneCertificatoAttoRequest modelloForm = getModelObject();

            add(
                new CardLabel<>(
                    "cfIntestatario",
                    modelloForm.getEstremiIntestatario().getCodiceFiscale(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    modelloForm.getEstremiIntestatario().getCognome(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario",
                    modelloForm.getEstremiIntestatario().getNome(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "tipo",
                    informazioni.getDescrizioneTipoCertificato(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "consegna",
                    modelloForm.getTipoConsegnaScelta(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "diritti",
                    modelloForm.getImportoDirittiSegreteria(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "email",
                    modelloForm.getEmail(),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "cfIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getCodiceFiscale()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getCognome()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getNome()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep6.this));
            add(
                new CardLabel<>(
                    "dataEvento",
                    getDataEvento(modelloForm),
                    RichiestaCertificatoStatoCivilePageStep6.this));
            BottoneAJAXDownloadWithError<Component> download = creaDownload(modelloForm);
            add(download);
          }

          private BottoneAJAXDownloadWithError<Component> creaDownload(
              PostRichiestaEmissioneCertificatoAttoRequest modelloForm) {
            return new BottoneAJAXDownloadWithError<Component>(
                "downloadPdf", RichiestaCertificatoStatoCivilePageStep6.this) {

              private static final long serialVersionUID = 2717167982774170002L;

              @Override
              protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
                GetScaricoCertificatoAttiResponse response;
                try {
                  response =
                      ServiceLocator.getInstance()
                          .getCertificatiStatoCivile()
                          .getScaricoCertificatoDopoPost(modelloForm, getUtente());
                  FileDaScaricare fileDaScaricare = new FileDaScaricare();
                  fileDaScaricare.setFileBytes(response.getFile());
                  fileDaScaricare.setFileName(response.getNomeFile());
                  fileDaScaricare.setEsitoOK(true);
                  return fileDaScaricare;
                } catch (ApiException | BusinessException e) {
                  ServiceLocator.getInstance()
                      .getCertificatiStatoCivile()
                      .inviaRichiestaPerEmail(request, informazioni);
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
          }

          @SuppressWarnings("unused")
          private Richiedente datiRichiedenteUgualiUtenteCollegato() {
            Richiedente estremi = new Richiedente();
            estremi.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
            estremi.setNome(getUtente().getNome());
            estremi.setCognome(getUtente().getCognome());
            return estremi;
          }

          public Object getDataEvento(PostRichiestaEmissioneCertificatoAttoRequest modelloForm) {
            return modelloForm.getDataCertificatoStorico() != null
                ? modelloForm.getDataCertificatoStorico()
                : null;
          }

          @Override
          protected void onSubmit() {}
        };
    addOrReplace(form);
  }
}
