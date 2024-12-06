package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiDettaglioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IResourceStream;

public class BibliotecheMovimentiPrivacyForm extends AbstracFrameworkForm<BibliotecheIscrizione> {

  private static final long serialVersionUID = 3140704796987545682L;

  private Log log = LogFactory.getLog(this.getClass());

  private LaddaAjaxLink<Object> btnConferma;

  private WebMarkupContainer containerPrivacy;

  private Utente utente;

  WebMarkupContainer alertToggle = new WebMarkupContainer("alertToggle");

  private Boolean maggiorenne;

  private ComponenteNucleo bambino;

  public BibliotecheMovimentiPrivacyForm(String id, BibliotecheIscrizione model, Utente utente) {
    super(id, model);
    this.setUtente(utente);
    addElementiForm(utente);
  }

  public BibliotecheMovimentiPrivacyForm(
      String id, BibliotecheIscrizione model, Utente utente, Boolean maggiorenne) {
    super(id, model);
    this.setUtente(utente);
    this.setMaggiorenne(maggiorenne);
    addElementiForm(utente);
  }

  public BibliotecheMovimentiPrivacyForm(
      String id,
      BibliotecheIscrizione model,
      Utente utente,
      Boolean maggiorenne,
      ComponenteNucleo bambino) {
    super(id, model);
    this.setUtente(utente);
    this.setMaggiorenne(maggiorenne);
    this.setBambino(bambino);
    addElementiForm(utente);
  }

  private void addInfo() {
    String nome = getUtente().getNome();
    String info =
        getString("BibliotecheMovimentiPrivacyForm.ciao")
            .concat(" ")
            .concat(nome)
            .concat(getString("BibliotecheMovimentiPrivacyForm.info"));
    Label infoLabel = new Label("nome", info);
    addOrReplace(infoLabel);
  }

  private void addContainerPrivacy() {
    containerPrivacy = new WebMarkupContainer("containerPrivacy");
    containerPrivacy.setOutputMarkupId(true);
    boolean visibilitaContainerPrivacy = false;
    if (getModelObject().isAutorizzazioneTrattamentoDati()) {
      visibilitaContainerPrivacy = true;
    }
    containerPrivacy.setVisible(visibilitaContainerPrivacy);
    containerPrivacy.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerPrivacy);
  }

  private void addDownloadLink() {
    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = 8189916125148718792L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativaSebina(getUtente());
            } catch (final BusinessException e) {
              log.error(
                  "[BibliotecheMovimentiIscrizionePrivacyForm] Errore durante scaricamento dell'informativa privacy Sebina: "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy Sebina");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {
            return getString("BibliotecheMovimentiPrivacyForm.nomeFile");
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnDownload") {

          private static final long serialVersionUID = 562860070843086644L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {
            target.add(btnConferma);
            target.add(containerPrivacy);
            download.initiate(target);
            containerPrivacy.setVisible(true);
          }
        };

    add(linkDownload);
  }

  private void addTogglePrivacy() {
    AjaxCheckBox autorizzazioneTrattamentoDati =
        new AjaxCheckBox(
            "accetto",
            new PropertyModel<Boolean>(getModelObject(), "autorizzazioneTrattamentoDati")) {

          private static final long serialVersionUID = -8755213958322256854L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            btnConferma.setEnabled(getModelObject());
            target.add(btnConferma);
          }
        };
    autorizzazioneTrattamentoDati.setRequired(true);
    autorizzazioneTrattamentoDati.setOutputMarkupId(true);
    autorizzazioneTrattamentoDati.setOutputMarkupPlaceholderTag(true);
    containerPrivacy.addOrReplace(autorizzazioneTrattamentoDati);
  }

  private void addToggleMail() {
    CheckBox autorizzazioneMail =
        new CheckBox("mail", new PropertyModel<Boolean>(getModelObject(), "autorizzazioneMail"));
    autorizzazioneMail.setRequired(false);
    autorizzazioneMail.setOutputMarkupId(true);
    autorizzazioneMail.setOutputMarkupPlaceholderTag(true);
    containerPrivacy.addOrReplace(autorizzazioneMail);
  }

  private void addAlertToggle() {
    alertToggle.setOutputMarkupId(true);
    alertToggle.setOutputMarkupPlaceholderTag(true);
    alertToggle.setVisible(false);
    addOrReplace(alertToggle);
  }

  private void addBottoneProcedi() {
    btnConferma =
        new LaddaAjaxLink<Object>("procedi", Type.Primary) {

          private static final long serialVersionUID = 3733855216289179017L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            try {
              if (checkToggle()) {
                PrivacyResponse privacyResponse =
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .presaVisioneSebina(getUtente());

                if (privacyResponse.getEsito().isEsito()) {

                  getUtente().setSebinaPrivacyNonAccettata(privacyResponse.getEsito().isEsito());

                  if (getMaggiorenne() && LabelFdCUtil.checkIfNull(bambino)) {
                    setResponsePage(new BibliotecheMovimentiDettaglioPage());
                  } else {
                    setResponsePage(new BibliotecheMovimentiDettaglioPage(getBambino()));
                  }

                } else {
                  setResponsePage(new ErroreServiziPage("io leggo"));
                }
              } else {
                alertToggle.setVisible(true);
              }

            } catch (BusinessException e) {
              log.error("Errore accettazione privacy Sebina:", e);
              error("errore:" + e.getMessage());
            }
            target.add(BibliotecheMovimentiPrivacyForm.this);
          }
        };

    btnConferma.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "BibliotecheMovimentiPrivacyForm.procedi",
                    BibliotecheMovimentiPrivacyForm.this)));

    btnConferma.setOutputMarkupId(true);
    btnConferma.setEnabled(false);

    addOrReplace(btnConferma);
  }

  private boolean checkToggle() {
    return getModelObject().isAutorizzazioneTrattamentoDati();
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public void addElementiForm(Utente utente) {
    addInfo();
    addContainerPrivacy();
    addDownloadLink();
    addTogglePrivacy();
    addToggleMail();
    addBottoneProcedi();
    addAlertToggle();
  }

  @Override
  public void addElementiForm() {}

  public Boolean getMaggiorenne() {
    return maggiorenne;
  }

  public void setMaggiorenne(Boolean maggiorenne) {
    this.maggiorenne = maggiorenne;
  }

  public ComponenteNucleo getBambino() {
    return bambino;
  }

  public void setBambino(ComponenteNucleo bambino) {
    this.bambino = bambino;
  }
}
