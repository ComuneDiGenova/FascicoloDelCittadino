package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliPage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale.StatoEnum;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

public class HeaderMieiVerbaliPanel extends BasePanel {

  private static final long serialVersionUID = 1L;

  public HeaderMieiVerbaliPanel(String id) {
    super(id);
    setOutputMarkupId(true);
    createFeedBackPanel();

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  @Override
  public void fillDati(Object dati) {

    addOrReplace(creaLinkFlagVerbaleAperto());
    addOrReplace(creaLinkFlagVerbaleInAttesa());
    addOrReplace(creaLinkFlagVerbaleArchiviato());

    addOrReplace(creaLinkFlagVerbaleOggettoDiRicorso());
    addOrReplace(creaLinkFlagVerbaleInvioAdAutorita());

    addOrReplace(creaLinkFlagVerbaleIscrittoARuolo());

    addOrReplace(creaLinkFlagVerbaleTutti());

    AjaxLink<Void> btnRicercaVerbale =
        new AjaxLink<Void>("btnRicercaVerbale") {

          private static final long serialVersionUID = -7855672513687844115L;

          @Override
          public void onClick(AjaxRequestTarget arg0) {
            setResponsePage(new MieiVerbaliFormPage());
          }
        };
    addOrReplace(btnRicercaVerbale);
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleAperto() {
    LaddaAjaxLink<Object> linkFlagVerbaleAperto =
        new LaddaAjaxLink<Object>("flagVerbaleAperto", Type.Link) {

          private static final long serialVersionUID = 1448579012199876827L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.APERTO.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleAperto.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.aperti", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleAperto;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleArchiviato() {
    LaddaAjaxLink<Object> linkFlagVerbaleArchiviato =
        new LaddaAjaxLink<Object>("flagVerbaleArchiviato", Type.Link) {

          private static final long serialVersionUID = -5242808317119039167L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.ARCHIVIATO.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleArchiviato.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.archiviati", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleArchiviato;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleInAttesa() {
    LaddaAjaxLink<Object> linkFlagVerbaleInAttesa =
        new LaddaAjaxLink<Object>("flagVerbaleInAttesa", Type.Link) {

          private static final long serialVersionUID = -5861904044981288022L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.IN_ATTESA_DI_VERIFICA.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleInAttesa.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.inAttesa", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleInAttesa;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleOggettoDiRicorso() {
    LaddaAjaxLink<Object> linkFlagVerbaleOggettoDiRicorso =
        new LaddaAjaxLink<Object>("flagVerbaleOggettoRicorso", Type.Link) {

          private static final long serialVersionUID = -1811629690499999872L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.OGGETTO_DI_RICORSO.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleOggettoDiRicorso.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "HeaderMieiVerbaliPanel.oggettoDiRicorso", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleOggettoDiRicorso;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleInvioAdAutorita() {
    LaddaAjaxLink<Object> linkFlagVerbaleInvioAdAutorita =
        new LaddaAjaxLink<Object>("flagVerbaleInvioAdAutorita", Type.Link) {

          private static final long serialVersionUID = -4151085000859717022L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.INVIO_AD_AUTORITA_COMPETENTE.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleInvioAdAutorita.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.invioAdAutorita", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleInvioAdAutorita;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleIscrittoARuolo() {
    LaddaAjaxLink<Object> linkFlagVerbaleIscrittoARuolo =
        new LaddaAjaxLink<Object>("flagVerbaleIscrittoARuolo", Type.Link) {

          private static final long serialVersionUID = -4151085000859717022L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            String statoVerbale = StatoEnum.ISCRITTO_A_RUOLO.value();
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getListaTuttiVerbaliConFiltroStatoDaBadge(getUtente(), statoVerbale);

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge, statoVerbale));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleIscrittoARuolo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.iscrittoARuolo", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleIscrittoARuolo;
  }

  private LaddaAjaxLink<Object> creaLinkFlagVerbaleTutti() {
    LaddaAjaxLink<Object> linkFlagVerbaleTutti =
        new LaddaAjaxLink<Object>("flagTuttiVerbali", Type.Link) {

          private static final long serialVersionUID = 3942362546672137188L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieiVerbaliPanel.this);
            try {
              List<Verbale> listaVerbaliFiltratiDaBadge =
                  ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());

              setResponsePage(new MieiVerbaliPage(listaVerbaliFiltratiDaBadge));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(
                  new ErroreServiziPage("i miei verbali"));
            }
          }
        };

    linkFlagVerbaleTutti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieiVerbaliPanel.tutti", HeaderMieiVerbaliPanel.this)));
    return linkFlagVerbaleTutti;
  }
}
