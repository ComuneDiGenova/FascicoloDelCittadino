package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.validator.NumeroCellulareValidator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.CertificatoStatoCivilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RichiestaCertificatoStatoCivilePageStep4 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;
  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoStatoCivilePageStep4(
      PostRichiestaEmissioneCertificatoAttoRequest postRichiestaEmissioneCertificatoAttoRequest,
      InformazioniAccessorieCertificato informazioni) {
    super();
    this.informazioni = informazioni;
    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoStatoCivilePage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(postRichiestaEmissioneCertificatoAttoRequest);
    StepPanel stepPanel = new StepPanel("stepPanel", 4, lista);
    addOrReplace(stepPanel);
  }

  private void creaForm(
      PostRichiestaEmissioneCertificatoAttoRequest postRichiestaEmissioneCertificatoAttoRequest) {

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoAttoRequest>(
            "form", postRichiestaEmissioneCertificatoAttoRequest) {

          private static final long serialVersionUID = -3105269996270741631L;

          EmailTextField email;
          TextField<String> contattoTelefonico;

          @Override
          public void addElementiForm() {
            email = new EmailTextField("mail", new PropertyModel<String>(getUtente(), "mail"));
            email.setLabel(Model.of("Email"));
            email.setRequired(true);
            contattoTelefonico =
                new TextField<String>("mobile", new PropertyModel<String>(getUtente(), "mobile"));
            contattoTelefonico.add(new NumeroCellulareValidator());
            contattoTelefonico.setLabel(Model.of("Telefono"));
            contattoTelefonico.setRequired(true);
            addOrReplace(contattoTelefonico);
            addOrReplace(email);
            createFeedBackPanel();
          }

          @Override
          protected void onSubmit() {
            log.debug("modello: " + getModelObject());
            copioDatiComboSuInformazioniAccessorie();
            PostRichiestaEmissioneCertificatoAttoRequest modelloForm =
                copioDatiComboSuModelloForm();
            setResponsePage(
                new RichiestaCertificatoStatoCivilePageStep5(modelloForm, informazioni));
          }

          private void copioDatiComboSuInformazioniAccessorie() {
            if (contattoTelefonico.getModelObject() != null) {
              informazioni.setTelefonoContatto(contattoTelefonico.getModelObject());
            }
          }

          private PostRichiestaEmissioneCertificatoAttoRequest copioDatiComboSuModelloForm() {
            PostRichiestaEmissioneCertificatoAttoRequest modelloForm = getModelObject();
            log.debug("modello: " + modelloForm);
            modelloForm.setEmail(email.getModelObject());
            return modelloForm;
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
    form.addOrReplace(creaBottoneIndietro(postRichiestaEmissioneCertificatoAttoRequest));
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(
      PostRichiestaEmissioneCertificatoAttoRequest postRichiestaEmissioneCertificatoAttoRequest) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep4.this);
            log.debug("CP click indietro " + postRichiestaEmissioneCertificatoAttoRequest);
            if (isTipoCertificatoRichiedeAltraPersona()) {
              setResponsePage(
                  new RichiestaCertificatoStatoCivilePageStep3(
                      postRichiestaEmissioneCertificatoAttoRequest, informazioni));
            } else {
              setResponsePage(
                  new RichiestaCertificatoStatoCivilePageStep2(
                      postRichiestaEmissioneCertificatoAttoRequest.getEstremiIntestatario(),
                      informazioni));
            }
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoStatoCivilePage.indietro",
                    RichiestaCertificatoStatoCivilePageStep4.this)));

    return indietro;
  }

  private boolean isTipoCertificatoRichiedeAltraPersona() {

    if (informazioni.getDescrizioneTipoCertificato().contains("matrimonio")) {
      return true;
    } else if (informazioni.getDescrizioneTipoCertificato().contains("unione civile")) {
      return true;
    } else if (informazioni.getDescrizioneTipoCertificato().contains("Unione Civile")) {
      return true;
    } else if (informazioni.getDescrizioneTipoCertificato().contains("Matrimonio")) {
      return true;
    } else {
      return false;
    }
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoStatoCivilePageStep4.this);
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
                    RichiestaCertificatoStatoCivilePageStep4.this)));

    return annulla;
  }
}
