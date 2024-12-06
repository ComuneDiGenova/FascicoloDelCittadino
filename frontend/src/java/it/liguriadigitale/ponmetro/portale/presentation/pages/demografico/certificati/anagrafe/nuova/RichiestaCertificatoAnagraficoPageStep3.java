package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.UtilizzoItemRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.validator.NumeroCellulareValidator;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.UtilizzoItem;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RichiestaCertificatoAnagraficoPageStep3 extends LayoutNoFeedBackPage {

  private static final String CARTA_LIBERA = "1";
  private static final String BOLLO = "2";
  private static final double BOLLO_EURO = 16.0d;

  private static final long serialVersionUID = -5975503781218096405L;

  private InformazioniAccessorieCertificato informazioni;
  private PostRichiestaEmissioneCertificatoRequest richiestaRicevuta;

  public RichiestaCertificatoAnagraficoPageStep3(
      PostRichiestaEmissioneCertificatoRequest postRichiestaEmissioneCertificatoRequest,
      InformazioniAccessorieCertificato informazioni) {
    super();
    this.informazioni = informazioni;
    this.richiestaRicevuta = postRichiestaEmissioneCertificatoRequest;
    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
    List<StepFdC> lista =
        RichiestaCertificatoAnagraficoPage.inizializzaStepRichiestaCertificatoAnagrafico();
    creaForm(postRichiestaEmissioneCertificatoRequest);
    StepPanel stepPanel = new StepPanel("stepPanel", 3, lista);
    addOrReplace(stepPanel);
  }

  private void creaForm(PostRichiestaEmissioneCertificatoRequest request) {
    log.debug("PostRichiestaEmissioneCertificatoRequest=" + request);

    AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest> form =
        new AbstracFrameworkForm<PostRichiestaEmissioneCertificatoRequest>("form", request) {

          private static final long serialVersionUID = -3105269996270741631L;

          DropDownChoice<UtilizzoItem> comboUtilizzo;
          EmailTextField email;
          TextField<String> contattoTelefonico;

          @Override
          public void addElementiForm() {
            creaComboUtilizo();
            email = new EmailTextField("mail", new PropertyModel<String>(getUtente(), "mail"));
            email.setLabel(Model.of("Email"));
            email.setRequired(true);
            contattoTelefonico =
                new TextField<String>("mobile", new PropertyModel<String>(getUtente(), "mobile"));
            contattoTelefonico.add(new NumeroCellulareValidator());
            contattoTelefonico.setRequired(true);
            contattoTelefonico.setLabel(Model.of("Telefono"));
            addOrReplace(contattoTelefonico);
            addOrReplace(email);
            createFeedBackPanel();
            @SuppressWarnings("unchecked")
            AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
                AlertBoxPanelBuilder.getInstance()
                    .addClazz(RichiestaCertificatoAnagraficoPageStep3.this.getPageClass())
                    .build();
            addOrReplace(boxMessaggi);
          }

          @Override
          protected void onSubmit() {
            log.debug("mail:" + getUtente().getMail());

            PostRichiestaEmissioneCertificatoRequest modelloForm = copioDatiComboSuModelloForm();
            copioDatiComboSuInformazioniAccessorie();
            if (isRichiestoBollo(modelloForm)) {
              setResponsePage(
                  new RichiestaCertificatoAnagraficoPageStep4(modelloForm, informazioni));
            } else {
              setResponsePage(
                  new RichiestaCertificatoAnagraficoPageStep5(modelloForm, informazioni));
            }
          }

          private void copioDatiComboSuInformazioniAccessorie() {
            if (comboUtilizzo.getModelObject().getCodice() != null) {
              informazioni.setDescrizioneMotivazioneRichiesta(
                  comboUtilizzo.getModelObject().getDescrizione());
            }
            if (contattoTelefonico.getModelObject() != null) {
              informazioni.setTelefonoContatto(contattoTelefonico.getModelObject());
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

          private PostRichiestaEmissioneCertificatoRequest copioDatiComboSuModelloForm() {
            PostRichiestaEmissioneCertificatoRequest modelloForm = getModelObject();
            log.debug("modello: " + modelloForm);
            if (isEsenteBollo()) {
              modelloForm.setTipoCertificato(CARTA_LIBERA);
              modelloForm.setImportoBollo(0.0d);
            } else {
              modelloForm.setImportoBollo(BOLLO_EURO);

              modelloForm.setTipoCertificato(BOLLO);
            }
            modelloForm.setEmail(email.getModelObject());
            if (comboUtilizzo.getModelObject() != null) {
              modelloForm.setCodiceUtilizzo(comboUtilizzo.getModelObject().getCodice());
            }
            return modelloForm;
          }

          private boolean isEsenteBollo() {
            return comboUtilizzo.getModelObject() != null
                && comboUtilizzo.getModelObject().getEsente();
          }

          @SuppressWarnings("unchecked")
          private void creaComboUtilizo() {
            UtilizzoItemRenderer choiceRenderer = new UtilizzoItemRenderer();
            UtilizzoItem item = new UtilizzoItem();
            String codiceUtilizzo = getModelObject().getCodiceUtilizzo();
            if (codiceUtilizzo != null && StringUtils.isNotEmpty(codiceUtilizzo)) {
              item.setCodice(null);
            }

            comboUtilizzo =
                this.creaDropDownChoice(
                    this.getUtilizziCertificato(), "comboUtilizzo", choiceRenderer, Model.of(item));
            comboUtilizzo.setOutputMarkupId(true);
            comboUtilizzo.setNullValid(false);
            this.add(comboUtilizzo);
          }

          private List<UtilizzoItem> getUtilizziCertificato() {
            List<UtilizzoItem> lista =
                ServiceLocator.getInstance().getCertificatiAnagrafe().getUtilizzoCertificati();
            lista.stream()
                .sorted(Comparator.comparing(UtilizzoItem::getCodice).reversed())
                .collect(Collectors.toList());
            log.debug("lista: " + lista.size());
            return lista;
          }

          private boolean isRichiestoBollo(PostRichiestaEmissioneCertificatoRequest modelObject) {
            if (modelObject.getImportoBollo() != null && modelObject.getImportoBollo() > 0.0d) {
              return true;
            } else {
              return false;
            }
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
            target.add(RichiestaCertificatoAnagraficoPageStep3.this);
            log.debug("CP click indietro " + request);
            setResponsePage(
                new RichiestaCertificatoAnagraficoPageStep2(
                    request.getEstremiIntestatario(), informazioni));
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaCertificatoAnagraficoPage.indietro",
                    RichiestaCertificatoAnagraficoPageStep3.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaCertificatoAnagraficoPageStep3.this);

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
                    RichiestaCertificatoAnagraficoPageStep3.this)));

    return annulla;
  }
}
