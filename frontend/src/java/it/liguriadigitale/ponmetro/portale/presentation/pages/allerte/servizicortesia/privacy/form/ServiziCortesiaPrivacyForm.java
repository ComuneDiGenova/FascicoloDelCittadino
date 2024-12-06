package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.privacy.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
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
import org.apache.wicket.util.resource.IResourceStream;

public class ServiziCortesiaPrivacyForm extends Form<Void> {

  private static final long serialVersionUID = -8307586741253414674L;

  private Log log = LogFactory.getLog(this.getClass());

  private LaddaAjaxButton btnConferma;

  private WebMarkupContainer container;

  private Utente utente;

  public ServiziCortesiaPrivacyForm(String id, Utente utente) {
    super(id);

    this.utente = utente;

    addContainer();
    add(new Label("nome", " " + utente.getNome()));
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

          private static final long serialVersionUID = -3261225734815187193L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            String valoreSelezionato = getDefaultModelObjectAsString();
            log.debug("[ServiziCortesiaPrivacyForm] The selected value is " + valoreSelezionato);
            btnConferma.setEnabled(getModelObject());
            target.add(btnConferma);
          }
        };
    container.add(check);
  }

  private void addDownloadLink() {
    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = 4070594401840261669L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativaServiziCortesia(utente);
            } catch (final BusinessException e) {
              log.error(
                  "[ServiziCortesiaPrivacyForm] Errore durante scaricamento dell'informativa privacy servizi cortesia: "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy servizi cortesia");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {
            return getString("ServiziCortesiaPrivacyForm.nomeFile");
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnDownload") {

          private static final long serialVersionUID = -4470448654659426937L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {

            target.add(btnConferma, ServiziCortesiaPrivacyForm.this);
            download.initiate(target);
            container.setVisible(true);
          }
        };
    add(linkDownload);
  }

  private void addBottoneApriFascicolo() {
    btnConferma =
        new LaddaAjaxButton("procedi", Type.Primary) {

          private static final long serialVersionUID = 8622417218067704423L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            try {
              log.debug("onClick:");
              ServiziCortesiaPrivacyForm.this.setEnabled(false);
              target.add(container, ServiziCortesiaPrivacyForm.this);

              PrivacyResponse privacyResponse =
                  ServiceLocator.getInstance()
                      .getServiziConfigurazione()
                      .presaVisioneServiziCortesia(utente);

              if (privacyResponse.getEsito().isEsito()) {

                utente.setServiziCortesiaPrivacyNonAccettata(privacyResponse.getEsito().isEsito());

                setResponsePage(new ServiziCortesiaConPrivacyPage());

              } else {
                setResponsePage(new ErroreServiziPage("allerte cortesia"));
              }

            } catch (BusinessException e) {
              log.error("Errore:", e);
              error("Errore privacu servizi cortesia:" + e.getMessage());
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
