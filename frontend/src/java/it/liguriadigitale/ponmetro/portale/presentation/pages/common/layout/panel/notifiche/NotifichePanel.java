package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.notifiche;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.application.session.AbstractLoginInSession;
import it.liguriadigitale.framework.presentation.components.link.BaseLink;
import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoHomePagoPA;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoHomeRicercaGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.SalvaContattiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.MessaggiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.ScadenzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.Date;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.resource.IResourceStream;

public class NotifichePanel extends FrameworkWebPanel {

  private static final long serialVersionUID = -1264296706185857837L;

  private LayoutBasePage currentPage;

  public NotifichePanel(String id, LayoutBasePage currentPage) {
    super(id);

    this.currentPage = currentPage;
    this.isVisibleNotifichePanel(currentPage);

    this.generateMessaggiCounter();

    this.openScadenze();
    this.openMessaggi();
    this.openPagamenti();
    this.openPrivacy();

    openGloboRicerca();

    addOrReplace(creaBottonePrenotaAppuntamento());
  }

  private void openGloboRicerca() {
    @SuppressWarnings("unchecked")
    LinkDinamicoHomeRicercaGlobo<Object> linkGlobo =
        new LinkDinamicoHomeRicercaGlobo(
            "linkGlobo",
            new LinkDinamicoFunzioneData(
                "NotifichePanel.globo", "GloboRicercaPage", "NotifichePanel.globo"),
            null,
            NotifichePanel.this,
            "icon-cerca") {

          private static final long serialVersionUID = 5286554532372701128L;

          @Override
          public boolean isVisible() {
            return super.isVisible() && checkVisibilitaRicerca() && !getUtente().isUtenteDelegato();
          }
        };
    linkGlobo.setRenderBodyOnly(true);
    add(linkGlobo);
  }

  private boolean isVisibleNotifichePanel(LayoutBasePage currentPage) {
    boolean visiblePanel = true;
    if ((currentPage.getPage().getClass().equals(Wizard1PrivacyPage.class))
        || (currentPage.getPage().getClass().equals(Wizard2AutocertificazionePage.class))
        || (currentPage.getPage().getClass().equals(SalvaContattiPage.class))) {
      visiblePanel = false;
    }
    return visiblePanel;
  }

  @Override
  public boolean isVisible() {
    return isVisibleNotifichePanel(currentPage);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void openScadenze() {
    BaseLink<Void> baseLink = new BaseLink("linkscadenze", ScadenzePage.class);
    baseLink.setVisible(checkVisibilitaScadenze());
    add(baseLink);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void openMessaggi() {
    BaseLink<Void> baseLink = new BaseLink("linkMessaggi", MessaggiPage.class);
    baseLink.setVisible(checkVisibilitaMessaggi());
    add(baseLink);
  }

  private void openPagamenti() {
    @SuppressWarnings({"rawtypes", "unchecked"})
    LinkDinamicoHomePagoPA<Object> linkPagamenti =
        new LinkDinamicoHomePagoPA(
            "linkPagamenti",
            new LinkDinamicoFunzioneData(
                "NotifichePanel.pagamenti", "PagamentiPage", "NotifichePanel.pagamenti"),
            null,
            NotifichePanel.this,
            "pagoPa") {

          private static final long serialVersionUID = 1199525203810688652L;

          @Override
          public boolean isVisible() {
            return super.isVisible() && checkVisibilitaPagoPa() && !getUtente().isUtenteDelegato();
          }
        };
    linkPagamenti.setRenderBodyOnly(true);
    add(linkPagamenti);
  }

  private boolean checkVisibilitaPagoPa() {
    return ServiceLocator.getInstance().getServiziHomePage().isVisibilePagoPa();
  }

  private boolean checkVisibilitaMessaggi() {
    return ServiceLocator.getInstance().getServiziHomePage().isVisibileMessaggi();
  }

  private boolean checkVisibilitaScadenze() {
    return ServiceLocator.getInstance().getServiziHomePage().isVisibileScadenze();
  }

  private boolean checkVisibilitaRicerca() {
    return ServiceLocator.getInstance().getServiziHomePage().isVisibileRicerca();
  }

  private boolean checkVisibilitaPrivacy() {
    return ServiceLocator.getInstance().getServiziHomePage().isVisibilePrivacy();
  }

  private void generateMessaggiCounter() {

    try {
      Utente utente = this.getUtente();
      Long countMessaggi =
          ServiceLocator.getInstance().getServiziMessaggi().getNumeroMessaggiDaLeggere(utente);
      NotEmptyLabel labelCountMessage = new NotEmptyLabel("messaggi-counter", countMessaggi);
      labelCountMessage.setVisible(countMessaggi.intValue() > 0);
      add(labelCountMessage);

    } catch (BusinessException e) {
      log.debug("Errore durante generateMessaggiCounter", e);
    } catch (ApiException e) {
      log.debug("Errore durante generateMessaggiCounter", e);
    }
  }

  @Override
  public Utente getUtente() {
    AbstractLoginInSession<?> session = (AbstractLoginInSession<?>) this.getSession();
    return (Utente) session.getUtente();
  }

  private void openPrivacy() {
    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = 7452959216701596860L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativa(getUtente());
            } catch (final BusinessException e) {
              log.error(
                  "[NotifichePanel] Errore durante scaricamento dell'informativa privacy: "
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
        new AjaxLink<Page>("linkPrivacy") {

          private static final long serialVersionUID = 2650500364527836527L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {

            download.initiate(target);
          }
        };

    linkDownload.setVisible(checkVisibilitaPrivacy() && !getUtente().isUtenteDelegato());

    add(linkDownload);
  }

  @SuppressWarnings("unchecked")
  private LinkDinamicoLaddaFunzione<Object> creaBottonePrenotaAppuntamento() {

    @SuppressWarnings("rawtypes")
    LinkDinamicoLaddaFunzione<Object> prenotazioni =
        new LinkDinamicoLaddaFunzione(
            "linkPrenotaAppuntamento",
            new LinkDinamicoFunzioneData(
                "NotifichePanel.prenotazioni",
                "PrenotazioneAppuntamentoPage",
                "NotifichePanel.pagamenti"),
            null,
            NotifichePanel.this,
            "icon-orologio");

    return prenotazioni;
  }
}
