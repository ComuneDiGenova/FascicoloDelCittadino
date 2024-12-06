package it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.util.resource.IResourceStream;

public class PrivacyServizioForm extends Form<Void> {

  private static final long serialVersionUID = -3913855107874866332L;

  private Log log = LogFactory.getLog(this.getClass());

  private LaddaAjaxButton btnConferma;

  private WebMarkupContainer container;

  private Utente utente;

  private PrivacyServizio privacyServizio;

  public PrivacyServizioForm(String id, PrivacyServizio privacyServizio) {

    super(id);

    this.utente = privacyServizio.getUtente();
    this.privacyServizio = privacyServizio;

    addContainer();
    add(new Label("nome", " " + utente.getNome()));
    add(new Label("nomePartecipata", " " + privacyServizio.getNomePartecipata()));
    addDownloadLink();
    addBottoneApriFascicolo();
    addToggle();
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

          private static final long serialVersionUID = -252443192830471115L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            String valoreSelezionato = getDefaultModelObjectAsString();
            log.debug("[PrivacyServizioForm] The selected value is " + valoreSelezionato);
            btnConferma.setEnabled(getModelObject());
            target.add(btnConferma);
          }
        };
    container.add(check);
  }

  private void addDownloadLink() {
    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = -6974918345874463701L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {

              stampa = ServiceLocator.getInstance().getServiziConfigurazione();

              if (privacyServizio.getCodicePrivacy().equalsIgnoreCase(BaseServiceImpl.COD_BRAV)) {
                pdf = stampa.getInformativaServiziBrav(utente);
              } else if (privacyServizio
                  .getCodicePrivacy()
                  .equalsIgnoreCase(BaseServiceImpl.COD_CANONEIDRICO)) {
                pdf = stampa.getInformativaServiziCanoneIdrico(utente);
              }

            } catch (final BusinessException e) {
              log.error(
                  "[PrivacyServizioForm] Errore durante scaricamento dell'informativa privacy: "
                      + privacyServizio.getMessaggioErrore()
                      + " "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {
            return getString("PrivacyServizioForm.nomeFile");
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnDownload") {

          private static final long serialVersionUID = 6871970623021557577L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {

            target.add(btnConferma, PrivacyServizioForm.this);
            download.initiate(target);
            container.setVisible(true);
          }
        };
    add(linkDownload);
  }

  private void addBottoneApriFascicolo() {
    btnConferma =
        new LaddaAjaxButton("procedi", Type.Primary) {

          private static final long serialVersionUID = 9115740617175588745L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            try {
              log.debug("onClick:");
              PrivacyServizioForm.this.setEnabled(false);
              target.add(container, PrivacyServizioForm.this);

              PrivacyResponse privacyResponse = null;

              if (privacyServizio.getCodicePrivacy().equalsIgnoreCase(BaseServiceImpl.COD_BRAV)) {
                privacyResponse =
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .presaVisioneServiziBrav(utente);
              } else if (privacyServizio
                  .getCodicePrivacy()
                  .equalsIgnoreCase(BaseServiceImpl.COD_CANONEIDRICO)) {
                privacyResponse =
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .presaVisioneServiziCanoneIdrico(utente);
              }

              if (privacyResponse != null && privacyResponse.getEsito().isEsito()) {
                utente.setServiziBravPrivacyNonAccettata(privacyResponse.getEsito().isEsito());
                IRequestablePage responsePage = privacyServizio.getPaginaSuCuiAtterrare();

                setResponsePage(responsePage.getClass());

              } else {
                setResponsePage(new ErroreServiziPage(privacyServizio.getMessaggioErrore()));
              }

            } catch (BusinessException e) {
              log.error("Errore privacy: " + privacyServizio.getMessaggioErrore(), e);
              error("Errore privacy servizi:" + e.getMessage());
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
}
