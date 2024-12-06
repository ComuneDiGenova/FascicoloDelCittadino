package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.application.session.AbstractLoginInSession;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.BaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.PreLoadingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard3AutocertificazioneNonResidentePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;

public class PrivacyForm extends Form<Void> {

  private static final long serialVersionUID = -5232230972652995877L;
  private Log log = LogFactory.getLog(this.getClass());

  private LaddaAjaxButton btnConferma;

  private WebMarkupContainer container;

  public PrivacyForm(String id) {
    super(id);
    addContainer();
    add(new Label("nome", " " + getUtente().getNome()));
    addDownloadLink();
    addBottoneApriFascicolo();
    addToggle();
    createFeedBackPanel();
  }

  private void addContainer() {
    container = new WebMarkupContainer("container");
    container.setOutputMarkupId(true);
    container.setVisible(false);
    add(container);
  }

  private void addToggle() {

    Boolean accettato = false;
    AjaxCheckBox check =
        new AjaxCheckBox("accetto", new Model<Boolean>(accettato)) {

          private static final long serialVersionUID = 9191900306595442761L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            String valoreSelezionato = getDefaultModelObjectAsString();
            log.debug("[PrivacyForm] The selected value is " + valoreSelezionato);
            btnConferma.setEnabled(getModelObject());
            target.add(btnConferma);
          }
        };
    container.add(check);
  }

  private void addDownloadLink() {
    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = 3410635555932389732L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativa(getUtente());
            } catch (final BusinessException e) {
              log.error(
                  "[PrivacyForm] Errore durante scaricamento dell'informativa privacy: "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {

            final String dataFileName = DateUtil.toStringInteropFromDate(new Date());
            return getUtente().getIdAnonimoComuneGenova()
                + "_"
                + dataFileName
                + "_ConsensoAperturaFdC_v"
                + getUtente().getUltimaVersioneInformativaPrivacy()
                + ".pdf";
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnDownload") {

          private static final long serialVersionUID = 7962664774086189475L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {

            target.add(btnConferma, PrivacyForm.this);
            download.initiate(target);
            container.setVisible(true);
          }
        };
    add(linkDownload);
  }

  private void addBottoneApriFascicolo() {
    btnConferma =
        new LaddaAjaxButton("apri", Type.Primary) {

          private static final long serialVersionUID = 8543613061035770496L;

          @SuppressWarnings("unchecked")
          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            try {
              log.debug("onClick:");
              PrivacyForm.this.setEnabled(false);
              target.add(container, PrivacyForm.this);

              PrivacyResponse response =
                  ServiceLocator.getInstance().getServiziConfigurazione().presaVisione(getUtente());
              if (response.getEsito().isEsito()) {
                getUtente().apriFascicolo(true);
                List<MinoreConvivente> listaMinoriConviventi =
                    ServiceLocator.getInstance()
                        .getServizioDemografico()
                        .getFigliPerAutodichiarazione(getUtente());

                if (LocalDateUtil.isMaggiorenne(getUtente().getDataDiNascita())) {
                  if (listaMinoriConviventi.isEmpty()) {
                    if (getUtente().isResidente()) {
                      log.debug("onClick: isResidente - BaseLandingPage");
                      // throw new
                      // RestartResponseAtInterceptPageException(BaseLandingPage.class);
                      if (getUtente().getClassePagina() != null) {
                        setResponsePage(getUtente().getClassePagina());
                      } else {
                        setResponsePage(PreLoadingPage.class);
                      }

                    } else {
                      log.debug("onClick: Wizard3AutocertificazioneNonResidentePage");
                      setResponsePage(Wizard3AutocertificazioneNonResidentePage.class);
                    }
                  } else {
                    log.debug("onClick: Wizard2AutocertificazionePage");
                    setResponsePage(Wizard2AutocertificazionePage.class);
                  }
                } else {
                  log.debug("onClick: nonResidente - BaseLandingPage");
                  throw new RestartResponseAtInterceptPageException(BaseLandingPage.class);
                  // setResponsePage(BaseLandingPage.class);
                }
              } else {
                error("Errore" + response.getEsito().getEccezione());
              }

            } catch (BusinessException | ApiException e) {
              log.error("Errore:", e);
              error("errore:" + e.getMessage());
            }
            log.debug("onClick: FINE");
          }
        };
    btnConferma.setOutputMarkupId(true);
    btnConferma.setOutputMarkupPlaceholderTag(true);
    btnConferma.setEnabled(false);
    btnConferma.setLabel(Model.of("Procedi"));
    addOrReplace(btnConferma);
  }

  @SuppressWarnings("rawtypes")
  protected Utente getUtente() {
    AbstractLoginInSession loginSession = (AbstractLoginInSession) this.getSession();
    if (loginSession != null && loginSession.getUtente() != null) {
      Utente utente = (Utente) loginSession.getUtente();
      log.debug("[PORTALE] Utente loggato: " + utente.getLogin());
      return utente;
    } else {
      return null;
    }
  }

  protected void createFeedBackPanel() {
    NotificationPanel feedback = new NotificationPanel("feedback");
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }
}
