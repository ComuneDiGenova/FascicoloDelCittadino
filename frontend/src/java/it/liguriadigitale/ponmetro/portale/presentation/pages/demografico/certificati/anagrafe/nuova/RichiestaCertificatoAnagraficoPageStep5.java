package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Richiedente;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoAnagraficoPageStep5 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;
  private InformazioniAccessorieCertificato informazioni;
  private boolean isOnline;

  public RichiestaCertificatoAnagraficoPageStep5(
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
    StepPanel stepPanel = new StepPanel("stepPanel", 5, lista);
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
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    modelloForm.getEstremiIntestatario().getCognome(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario",
                    modelloForm.getEstremiIntestatario().getNome(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "tipo",
                    informazioni.getDescrizioneTipoCertificato(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "dataStorico",
                    getDataCertificatoStorico(modelloForm),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "dataDa",
                    informazioni.getDataDA(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "dataA",
                    informazioni.getDataA(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "utilizzo",
                    informazioni.getDescrizioneMotivazioneRichiesta(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "bollo",
                    modelloForm.getImportoBollo(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "dataBollo",
                    getDataMarcaBollo(modelloForm),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "diritti",
                    modelloForm.getImportoDirittiSegreteria(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "consegna",
                    modelloForm.getTipoConsegnaScelta(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "email", modelloForm.getEmail(), RichiestaCertificatoAnagraficoPageStep5.this));
            add(
                new CardLabel<>(
                    "mobile",
                    informazioni.getTelefonoContatto(),
                    RichiestaCertificatoAnagraficoPageStep5.this));
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

          private Richiedente datiRichiedenteUgualiUtenteCollegato() {
            Richiedente estremi = new Richiedente();
            estremi.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
            estremi.setNome(getUtente().getNome());
            estremi.setCognome(getUtente().getCognome());
            return estremi;
          }

          @Override
          protected void onSubmit() {

            PostRichiestaEmissioneCertificatoRequest request = modificaRequestaPrimadellaPost();
            try {
              if (isOnline) {
                setResponsePage(new RichiestaCertificatoAnagraficoPageStep6(request, informazioni));
              } else {
                boolean esito;
                esito =
                    ServiceLocator.getInstance()
                        .getCertificatiAnagrafe()
                        .inviaRichiestaPerEmailConPost(request, informazioni);
                if (esito) {
                  info("Richiesta inoltrata all'ufficio competente. Sarai contattata/o via email.");
                  setEnabled(false);
                } else {
                  error("Impossibile inoltrare la richiesta via e-mail");
                }
              }
            } catch (Exception e) {
              error("Impossibile inviare email: " + e.getMessage());
              log.error("Impossibile contattare APK: ", e);
            }
          }

          private PostRichiestaEmissioneCertificatoRequest modificaRequestaPrimadellaPost() {
            PostRichiestaEmissioneCertificatoRequest request = getModelObject();
            request.setEstremiRichiedente(datiRichiedenteUgualiUtenteCollegato());
            //				if (request.getCodiceUtilizzo() != null &&
            // request.getCodiceUtilizzo().equalsIgnoreCase("11")) {
            //					request.setCodiceUtilizzo(null);
            //				}
            log.debug("modello: " + request);
            return request;
          }
        };

    addOrReplace(form);
    form.addOrReplace(creaBottoneAnnulla());
    form.addOrReplace(creaBottoneIndietro(request));
    boolean visibilitaSubmit = checkCertificabilita(request);
    log.debug("visibilitaSubmit=" + visibilitaSubmit);
    Button bottone = creaBottoneInvia(form);
    // bottone.setVisible(visibilitaSubmit);
    form.addOrReplace(bottone);
  }

  private boolean checkCertificabilita(PostRichiestaEmissioneCertificatoRequest request) {
    isOnline = ServiceLocator.getInstance().getCertificatiAnagrafe().isCittadinoResidente(request);

    if (!isOnline) {
      warn(
          "Attenzione: Il certificato richiesto non è disponibile, per i non residenti, nella banca dati informatica. La tua richiesta verrà inoltrata all'ufficio competente, riceverai il certificato o eventuali comunicazioni via e-mail.");
      informazioni.setResidenteComuneGenova(false);
    } else {
      informazioni.setResidenteComuneGenova(true);
      isOnline =
          ServiceLocator.getInstance()
              .getCertificatiAnagrafe()
              .getCertificabilita(request, informazioni);
      if (!isOnline) {
        warn(
            "Attenzione: Il certificato richiesto non è disponibile nella banca dati informatica. La tua richiesta verrà inoltrata all'ufficio competente, riceverai il certificato o eventuali comunicazioni via e-mail.");
      }
    }
    return isOnline;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(
      PostRichiestaEmissioneCertificatoRequest request) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep5.this);
            log.debug("CP click indietro " + request);
            if (isRichiestoBollo(request)) {
              setResponsePage(new RichiestaCertificatoAnagraficoPageStep4(request, informazioni));
            } else {
              setResponsePage(new RichiestaCertificatoAnagraficoPageStep3(request, informazioni));
            }
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoAnagraficoPage.indietro",
                    RichiestaCertificatoAnagraficoPageStep5.this)));
    return indietro;
  }

  protected boolean isRichiestoBollo(PostRichiestaEmissioneCertificatoRequest request) {
    // TODO controllare utilizzo
    return false;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep5.this);

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
                    RichiestaCertificatoAnagraficoPageStep5.this)));

    return annulla;
  }

  private Button creaBottoneInvia(@SuppressWarnings("rawtypes") AbstracFrameworkForm form) {
    Button invia =
        new Button("invia") {

          private static final long serialVersionUID = -3601258772787073817L;
        };
    return invia;
  }
}
