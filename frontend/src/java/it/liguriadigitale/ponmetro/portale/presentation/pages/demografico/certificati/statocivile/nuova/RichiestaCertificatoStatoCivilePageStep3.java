package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoCodiceFiscale;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoTesto;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.CertificatoStatoCivilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoStatoCivilePageStep3 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  private PostRichiestaEmissioneCertificatoAttoRequest request;
  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoStatoCivilePageStep3(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni) {
    super();
    this.request = request;
    this.informazioni = informazioni;
    inizializzaPannelli();
    creaForm(request.getEstremiAltraPersona());
  }

  private void inizializzaPannelli() {
    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoStatoCivilePage.inizializzaStepRichiestaCertificatoAnagrafico();
    StepPanel stepPanel = new StepPanel("stepPanel", 3, lista);
    addOrReplace(stepPanel);
  }

  private void creaForm(Intestatario intestatario) {

    if (intestatario == null) {
      intestatario = new Intestatario();
    }
    AbstracFrameworkForm<Intestatario> form =
        new AbstracFrameworkForm<Intestatario>("form", intestatario) {

          private static final long serialVersionUID = -3105269996270741631L;

          CampoCodiceFiscale campoCf;
          CampoTesto cognome;
          CampoTesto nome;

          @Override
          public void addElementiForm() {

            campoCf = new CampoCodiceFiscale("codiceFiscale");
            cognome = new CampoTesto("cognome");
            nome = new CampoTesto("nome");
            campoCf.setOutputMarkupId(true);
            cognome.setOutputMarkupId(true);
            nome.setOutputMarkupId(true);
            addOrReplace(campoCf);
            addOrReplace(cognome);
            addOrReplace(nome);
            createFeedBackPanel();
          }

          @Override
          protected void onSubmit() {
            log.debug("modello: " + getModelObject());

            request.setEstremiAltraPersona(getModelObject());

            if (LabelFdCUtil.checkIfNotNull(request)
                && LabelFdCUtil.checkIfNotNull(request.getEstremiAltraPersona())
                && PageUtil.isStringValid(request.getEstremiAltraPersona().getCodiceFiscale())
                && LabelFdCUtil.checkIfNotNull(request.getEstremiIntestatario())
                && PageUtil.isStringValid(request.getEstremiIntestatario().getCodiceFiscale())) {
              if (request
                  .getEstremiAltraPersona()
                  .getCodiceFiscale()
                  .equalsIgnoreCase(request.getEstremiIntestatario().getCodiceFiscale())) {
                error("Attenzione, i codici fiscali inseriti non possono essere uguali");
              } else {
                setResponsePage(
                    new RichiestaCertificatoStatoCivilePageStep4(request, informazioni));
              }
            }
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
    form.addOrReplace(creaBottoneIndietro(request));
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(
      PostRichiestaEmissioneCertificatoAttoRequest postRichiestaEmissioneCertificatoAttoRequest) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep3.this);
            log.debug("CP click indietro " + postRichiestaEmissioneCertificatoAttoRequest);
            setResponsePage(
                new RichiestaCertificatoStatoCivilePageStep2(
                    postRichiestaEmissioneCertificatoAttoRequest.getEstremiIntestatario(),
                    informazioni));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoStatoCivilePage.indietro",
                    RichiestaCertificatoStatoCivilePageStep3.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep3.this);
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
                    RichiestaCertificatoStatoCivilePageStep3.this)));
    return annulla;
  }
}
