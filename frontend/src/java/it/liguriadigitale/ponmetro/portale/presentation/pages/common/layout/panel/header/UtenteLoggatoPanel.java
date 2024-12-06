package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.header;

import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.CONTEXT_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.link.AutocertificazioneLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo.RiepilogoPersonalizzazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.SalvaContattiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.PreLoadingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.flow.RedirectToUrlException;

public class UtenteLoggatoPanel extends FrameworkWebPanel {

  private static final long serialVersionUID = -4000152146069613921L;

  static final String URL_SODDISFAZIONE =
      "https://forms.office.com/pages/responsepage.aspx?id=-qqHhIpVokKdwG1JGQY1A8qCL3dVHwxIjOSnZN7nIuhUM1M5VkFLQkRKVEgzRVVORDlXMEpPVVgyMy4u";

  static final String URL_GESTIONE_INFORMATIVA_TRATTAMENTO_DATI =
      "https://www.cape-suite.eu/cape-dashboard-saml";

  public UtenteLoggatoPanel(String id, LayoutBasePage currentPage) {
    super(id);
    creaFunzioni(currentPage);
    setOutputMarkupId(false);
  }

  public boolean isFunzioneVisible() {
    return !getUtente().isUtenteDelegato();
  }

  @Override
  public Utente getUtente() {
    LoginInSession loginSession = (LoginInSession) getSession();

    if ((loginSession != null) && (loginSession.getUtente() != null)) {
      Utente utente = loginSession.getUtente();
      return utente;
    }
    return null;
  }

  private void creaFunzioni(LayoutBasePage currentPage) {

    Link<Void> impostazioniLink = impostazioniLink(currentPage);
    addOrReplace(impostazioniLink);

    Link<Void> logoAutocertificazionePageLink = autocertificazioneLink(currentPage);
    add(logoAutocertificazionePageLink);

    ExternalLink linkSoddisfazione = new ExternalLink("linkSoddisfazione", URL_SODDISFAZIONE);
    linkSoddisfazione.setVisible(isAttivoDuranteWizardPrimoAccesso(currentPage));
    addOrReplace(linkSoddisfazione);

    add(logOutLink());
  }

  private Link<Void> impostazioniLink(LayoutBasePage currentPage) {
    return new Link<Void>("impostazioni") {

      @Override
      public void onClick() {
        this.setResponsePage(RiepilogoPersonalizzazionePage.class);
      }

      @Override
      public boolean isVisible() {
        return isAttivoDuranteWizardPrimoAccesso(currentPage) && isFunzioneVisible();
      }
    };
  }

  private boolean isAttivoDuranteWizardPrimoAccesso(LayoutBasePage currentPage) {

    if (Wizard1PrivacyPage.class.isInstance(currentPage)
        || Wizard2AutocertificazionePage.class.isInstance(currentPage)
        || PreLoadingPage.class.isInstance(currentPage)
        || SalvaContattiPage.class.isInstance(currentPage)) {
      return false;
    }
    //		else if (AutocertificazioneGenitorePage.class.isInstance(currentPage)) {
    //			log.debug("istanza : AutocertificazioneGenitorePage");
    //			AutocertificazioneGenitorePage page = (AutocertificazioneGenitorePage) getPage();
    //			return !page.checkMappa();
    //		}
    else {
      return true;
    }
  }

  private Link<Void> logOutLink() {
    return new Link<Void>("logOut") {

      private static final long serialVersionUID = -1780091475907393718L;

      @Override
      public void onClick() {

        String returnUrl = "?return=https://www.comune.genova.it ";
        // + BaseServiceImpl.URL_LOGOUT_GLOBO;

        log.debug(" >>> LOGOUTPAGE <<< ");
        String serverUrl = BaseServiceImpl.URL_LOGOUT_FDC;
        log.debug(" >>> serverUrl: " + serverUrl);
        String url = "/Shibboleth.sso/Logout" + returnUrl;

        log.debug(" >>> LogOutPage(), redirect to url: " + url);
        log.debug("getPageRelativePath()=" + getPageRelativePath());
        log.debug("getPage()=" + getPage());
        log.debug("getPage().getClass()=" + getPage().getClass());
        String simpleNamePaginaCorrente = getPage().getClass().getSimpleName();
        log.debug("getPage().getClass().getSimpleName()=" + simpleNamePaginaCorrente);
        BreadcrumbFdC breadcrumb =
            MapBreadcrumbsFdc.getBreadcrumbCorrente(getPage().getClass().getSimpleName());
        String contextPath = "";
        if (breadcrumb != null) {
          contextPath = getPathCartellaPerLogout(breadcrumb);
          log.debug("breadcrumb.getMountedPath=" + contextPath);
        } else {
          log.debug("pagina non trovata in MapBreadcrumbsFdc");
        }
        String redirectUrl = getRedirectUrl(serverUrl, url, simpleNamePaginaCorrente, contextPath);
        invalidateSession();
        throw new RedirectToUrlException(redirectUrl);
      }

      private String getRedirectUrl(
          String serverUrl, String url, String simpleNamePaginaCorrente, String contextPath) {
        String redirectUrl;
        if (getUtente().isUtenteDelegato() || contextPath.contains("web/delegabili")) {
          log.debug("PAGINA DELEGATA O UTENTE CON DELEGA=" + simpleNamePaginaCorrente);
          redirectUrl = serverUrl + contextPath + url;
        } else {
          if (simpleNamePaginaCorrente.equalsIgnoreCase("HomePage")
              || simpleNamePaginaCorrente.equalsIgnoreCase("RedirectBaseLandingPage")) {
            log.debug("HOME PAGE=" + simpleNamePaginaCorrente);
            redirectUrl = serverUrl + CONTEXT_PATH.NORMALE.getPath() + "/homepage/home" + url;
          } else {
            log.debug("ALTRA PAGINA=" + simpleNamePaginaCorrente);
            redirectUrl = serverUrl + url;
          }
        }
        log.debug("redirectUrl=" + redirectUrl);
        return redirectUrl;
      }

      private String getPathCartellaPerLogout(BreadcrumbFdC breadcrumb) {
        String contextPath;
        if (StringUtils.isNotBlank(breadcrumb.getNomeClassePaginaPadre())) {
          breadcrumb =
              MapBreadcrumbsFdc.getBreadcrumbCorrente(breadcrumb.getNomeClassePaginaPadre());
        }
        contextPath = breadcrumb.getContextPath() + breadcrumb.getMountedPath();
        return contextPath;
      }
    };
  }

  private void invalidateSession() {
    log.debug(" >>> LogOutPage.invalidateSession() ");
    LoginInSession session = (LoginInSession) getSession();
    // FRR 20200102 controllo su logout null pointer
    synchronized (session) {
      if (session != null) {
        Utente utente = null;
        if (session != null) {
          utente = session.getUtente();
        }
        String cognomeUtente = utente != null ? utente.getCognome() : " nullo ";
        log.debug(" >>> session() - cognome utente " + cognomeUtente + " NULL ");
        log.debug(" >>> setto utente a null");
        if (session != null) {
          session.setUtente(null);
        }
        log.debug(" >>> invalido...");
        if (session != null) {
          session.invalidate();
        }
      }
    }
    log.debug(" ESCO DA invalidateSession() ");
  }

  private Link<Void> autocertificazioneLink(LayoutBasePage currentPage) {
    return new AutocertificazioneLink("autocertificazione") {

      private static final long serialVersionUID = -1754819109027047581L;

      @Override
      public boolean isVisible() {
        return super.isVisible() && isFunzioneVisible();
      }
    };
  }
}
