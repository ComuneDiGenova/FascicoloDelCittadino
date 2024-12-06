package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoCodiceFiscale;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoTesto;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.ComponenteNucleoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.builder.StepFdCBuilder;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Intestatario;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;

public class RichiestaCertificatoAnagraficoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  public RichiestaCertificatoAnagraficoPage(Intestatario intestatario) {
    super();
    inizializzaPannelli();
    creaForm(intestatario);
    createFeedBackPanel();
  }

  public RichiestaCertificatoAnagraficoPage() {
    super();
    inizializzaPannelli();
    creaForm(datiIntestatarioUgualiUtenteCollegato());
    createFeedBackPanel();
  }

  private void inizializzaPannelli() {
    // BreadcrumbCertificatiAnagrafePanel breadcrumbPanel = new
    // BreadcrumbCertificatiAnagrafePanel("breadcrumbPanel");
    // addOrReplace(breadcrumbPanel);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);

    List<StepFdC> lista =
        RichiestaCertificatoAnagraficoPage.inizializzaStepRichiestaCertificatoAnagrafico();
    StepPanel stepPanel = new StepPanel("stepPanel", 1, lista);
    addOrReplace(stepPanel);
  }

  private Intestatario datiIntestatarioUgualiUtenteCollegato() {
    Intestatario estremiIntestatario = new Intestatario();
    estremiIntestatario.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    estremiIntestatario.setNome(getUtente().getNome());
    estremiIntestatario.setCognome(getUtente().getCognome());
    return estremiIntestatario;
  }

  private void creaForm(Intestatario intestatario) {

    AbstracFrameworkForm<Intestatario> form =
        new AbstracFrameworkForm<Intestatario>("form", intestatario) {

          private static final long serialVersionUID = -3105269996270741631L;

          DropDownChoice<ComponenteNucleo> combo;
          CampoCodiceFiscale campoCf;
          CampoTesto cognome;
          CampoTesto nome;

          @Override
          public void addElementiForm() {

            getNucleoFamigliare();
            creaCombo();

            campoCf = new CampoCodiceFiscale("codiceFiscale");
            cognome = new CampoTesto("cognome");
            nome = new CampoTesto("nome");
            campoCf.setOutputMarkupId(true);
            cognome.setOutputMarkupId(true);
            nome.setOutputMarkupId(true);
            campoCf.setRequired(true);
            cognome.setRequired(true);
            nome.setRequired(true);
            addOrReplace(campoCf);
            addOrReplace(cognome);
            addOrReplace(nome);
          }

          @SuppressWarnings("unused")
          private AjaxFormComponentUpdatingBehavior creaOnChangeAjaxBehaviorSenzaIndicator() {

            return new AjaxFormComponentUpdatingBehavior("blur") {

              private static final long serialVersionUID = 4280187067745432696L;

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                cognome.setModelObject("");
                nome.setModelObject("");
                target.add(getParent());
              }
            };
          }

          @SuppressWarnings("unchecked")
          private void creaCombo() {
            ComponenteNucleoRenderer choiceRenderer = new ComponenteNucleoRenderer();
            ComponenteNucleo componente = new ComponenteNucleo();
            Residente datiCittadino = new Residente();
            datiCittadino.setCpvTaxCode(getModelObject().getCodiceFiscale());
            componente.setDatiCittadino(datiCittadino);
            combo =
                this.creaDropDownChoice(
                    this.getNucleoFamigliare(), "combo", choiceRenderer, Model.of(componente));
            combo.setOutputMarkupId(true);
            combo.add(creaBehaviorComboNucleo());
            this.add(combo);
          }

          public AjaxFormComponentUpdatingBehavior creaBehaviorComboNucleo() {
            return new AjaxFormComponentUpdatingBehavior("change") {
              private static final long serialVersionUID = 729725369588897805L;

              private Log log = LogFactory.getLog(getClass());

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                ComponenteNucleo model = combo.getModelObject();
                log.debug("modello: " + model);
                Residente residente = model.getDatiCittadino();
                campoCf.setModelObject(residente.getCpvTaxCode());
                cognome.setModelObject(residente.getCpvFamilyName());
                nome.setModelObject(residente.getCpvGivenName());
                target.add(RichiestaCertificatoAnagraficoPage.this);
              }
            };
          }

          private List<ComponenteNucleo> getNucleoFamigliare() {
            return getUtente().getListaNucleoFamiliareConviventiEAutodichiarati();
          }

          @Override
          protected void onSubmit() {
            log.debug("modello: " + getModelObject());
            setResponsePage(new RichiestaCertificatoAnagraficoPageStep2(getModelObject()));
          }
        };

    addOrReplace(form);
    form.addOrReplace(creaBottoneAnnulla());
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPage.this);
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
                    RichiestaCertificatoAnagraficoPage.this)));
    return annulla;
  }

  public static List<StepFdC> inizializzaStepRichiestaCertificatoAnagrafico() {
    List<StepFdC> lista = new ArrayList<>();
    StepFdC step1 = StepFdCBuilder.getInstance().addDescrizione("Per chi").addIndice(1).build();
    StepFdC step2 =
        StepFdCBuilder.getInstance().addDescrizione("Quale certificato").addIndice(2).build();
    StepFdC step3 =
        StepFdCBuilder.getInstance().addDescrizione("Per quale motivo").addIndice(3).build();
    StepFdC step4 = StepFdCBuilder.getInstance().addDescrizione("Bollo").addIndice(4).build();
    StepFdC step5 = StepFdCBuilder.getInstance().addDescrizione("Riepilogo").addIndice(5).build();
    StepFdC step6 = StepFdCBuilder.getInstance().addDescrizione("Scarica").addIndice(6).build();
    lista.add(step1);
    lista.add(step2);
    lista.add(step3);
    lista.add(step4);
    lista.add(step5);
    lista.add(step6);
    return lista;
  }
}
