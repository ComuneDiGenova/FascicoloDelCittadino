package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.WrapperGetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.CertificatoStatoCivilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Richiedente;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoStatoCivilePageStep5 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  private InformazioniAccessorieCertificato informazioni;
  private boolean isOnline;

  public RichiestaCertificatoStatoCivilePageStep5(
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
    StepPanel stepPanel = new StepPanel("stepPanel", 5, lista);
    addOrReplace(stepPanel);
    createFeedBackPanel();
    log.debug(
        "postRichiestaEmissioneCertificatoAttoRequest:"
            + postRichiestaEmissioneCertificatoAttoRequest);
  }

  private void creaForm(PostRichiestaEmissioneCertificatoAttoRequest request) {

    isOnline = checkCertificabilita(request);
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
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    modelloForm.getEstremiIntestatario().getCognome(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario",
                    modelloForm.getEstremiIntestatario().getNome(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "tipo",
                    informazioni.getDescrizioneTipoCertificato(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "consegna",
                    modelloForm.getTipoConsegnaScelta(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "diritti",
                    modelloForm.getImportoDirittiSegreteria(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "email",
                    modelloForm.getEmail(),
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "cfIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getCodiceFiscale()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "cognomeIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getCognome()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "nomeIntestatario2",
                    modelloForm.getEstremiAltraPersona() != null
                        ? modelloForm.getEstremiAltraPersona().getNome()
                        : "",
                    RichiestaCertificatoStatoCivilePageStep5.this));
            add(
                new CardLabel<>(
                    "dataEvento",
                    getDataEvento(modelloForm),
                    RichiestaCertificatoStatoCivilePageStep5.this));
          }

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
          protected void onSubmit() {
            PostRichiestaEmissioneCertificatoAttoRequest request = getModelObject();
            request.setEstremiRichiedente(datiRichiedenteUgualiUtenteCollegato());
            request.setEmail(getUtente().getMail());
            log.debug("modello: " + request);
            if (isOnline) {
              setResponsePage(new RichiestaCertificatoStatoCivilePageStep6(request, informazioni));
            } else {
              try {
                boolean esito =
                    ServiceLocator.getInstance()
                        .getCertificatiStatoCivile()
                        .inviaRichiestaPerEmailConPost(request, informazioni);
                if (esito) {
                  info("Richiesta inoltrata all'ufficio competente. Sarai contattata/o via email.");
                  setEnabled(false);
                } else {
                  error("Impossibile inoltrare la richiesta via e-mail");
                }
              } catch (Exception e) {
                error("Impossibile contattare backend: " + e.getMessage());
                log.error("Impossibile contattare APK: ", e);
              }
            }
          }
        };
    addOrReplace(form);
    form.addOrReplace(creaBottoneAnnulla());
    form.addOrReplace(creaBottoneIndietro(request));
    Button bottone = creaBottoneInvia(form);
    form.addOrReplace(bottone);
  }

  private boolean checkCertificabilita(PostRichiestaEmissioneCertificatoAttoRequest request) {

    try {
      WrapperGetSelezioneAttoResponseGenericResponse wrapper =
          ServiceLocator.getInstance()
              .getCertificatiStatoCivile()
              .checkCertificatoRichiedibile(request, informazioni);
      log.debug("RISPOSTA=" + wrapper);
      if (wrapper != null && !wrapper.isResult()) {
        warn(
            "Attenzione: il certificato richiesto non è disponibile nella banca dati informatica. Con il bottone \"Richiedi\" la tua richiesta sarà inoltrata all'ufficio competente, riceverai il certificato o eventuali comunicazioni via e-mail.");
        return false;
      } else {
        if (wrapper.getResponse() != null && wrapper.getResponse().getResult() != null) {
          request.setIdAtto(wrapper.getResponse().getResult().getIdAtto());
        }
        info("Certificato disponibile in formato digitale");
        return true;
      }
    } catch (BusinessException e) {
      error(e.getMessage());
      return false;
    }
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(
      PostRichiestaEmissioneCertificatoAttoRequest request) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep5.this);
            log.debug("CP click indietro " + request);
            setResponsePage(new RichiestaCertificatoStatoCivilePageStep4(request, informazioni));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoStatoCivilePage.indietro",
                    RichiestaCertificatoStatoCivilePageStep5.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep5.this);

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
                    RichiestaCertificatoStatoCivilePageStep5.this)));

    return annulla;
  }

  private Button creaBottoneInvia(@SuppressWarnings("rawtypes") AbstracFrameworkForm form) {
    Button invia =
        new Button("invia") {

          private static final long serialVersionUID = -5269371962796000761L;
        };
    return invia;
  }
}
