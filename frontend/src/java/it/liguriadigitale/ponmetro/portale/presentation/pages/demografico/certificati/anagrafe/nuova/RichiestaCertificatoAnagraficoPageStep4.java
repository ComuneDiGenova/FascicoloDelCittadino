package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoTesto;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova.common.AvvertenzaConScadenzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoAnagraficoPageStep4 extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  private InformazioniAccessorieCertificato informazioni;

  public RichiestaCertificatoAnagraficoPageStep4(
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
    StepPanel stepPanel = new StepPanel("stepPanel", 4, lista);
    addOrReplace(stepPanel);
    addOrReplace(new AvvertenzaConScadenzaPanel());
  }

  private void creaForm(PostRichiestaEmissioneCertificatoRequest request) {

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          CampoTesto marcaBollo;
          FdCLocalDateTextfield dataBollo;

          @Override
          public void addElementiForm() {

            IModel<String> modelMarcabollo = Model.of("");
            IModel<LocalDate> modelDataBollo = new Model<>();
            marcaBollo = new CampoTesto("marcaBollo", modelMarcabollo);
            marcaBollo.setLabel(Model.of("Marca da bollo"));
            dataBollo = new FdCLocalDateTextfield("dataBollo", modelDataBollo);
            dataBollo.addNoFutureValidator();
            dataBollo.setLabel(Model.of("Data marca da bollo"));
            marcaBollo.setRequired(true);
            dataBollo.setRequired(true);
            addOrReplace(marcaBollo);
            addOrReplace(dataBollo);
            createFeedBackPanel();
          }

          @Override
          protected void onSubmit() {
            PostRichiestaEmissioneCertificatoRequest modelloForm = copioDatiComboSuModelloForm();
            setResponsePage(new RichiestaCertificatoAnagraficoPageStep5(modelloForm, informazioni));
          }

          private PostRichiestaEmissioneCertificatoRequest copioDatiComboSuModelloForm() {
            PostRichiestaEmissioneCertificatoRequest modelloForm = getModelObject();
            log.debug("modello: " + modelloForm);
            LocalDate dataMarcaBollo = dataBollo.getModelObject();
            // modelloForm.setDataMarcaBollo(LocalDateUtil.getOffsetDateTime(dataMarcaBollo));
            modelloForm.setDataMarcaBollo(dataMarcaBollo);
            modelloForm.setNumeroMarcaBollo(marcaBollo.getModelObject());
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
    form.addOrReplace(creaBottoneIndietro(request));
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro(
      PostRichiestaEmissioneCertificatoRequest request) {
    LaddaAjaxLink<Object> indietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = 4638203351758954575L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep4.this);
            log.debug("CP click indietro " + request);
            log.debug("informazionii: " + informazioni);
            setResponsePage(new RichiestaCertificatoAnagraficoPageStep3(request, informazioni));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoAnagraficoPage.indietro",
                    RichiestaCertificatoAnagraficoPageStep4.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep4.this);

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
                    RichiestaCertificatoAnagraficoPageStep4.this)));

    return annulla;
  }
}
