package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.header;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.account.AutocertificazioneGenitorePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.home.HomePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.RedirectBaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.PreLoadingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import java.io.IOException;
import org.apache.commons.lang.WordUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

public class HeaderPanel extends FrameworkWebPanel {

  private static final long serialVersionUID = -1264296706185857837L;

  public HeaderPanel(String id, LayoutBasePage currentPage) {
    super(id);
    setOutputMarkupId(false);
    Link<Void> logoHomePageLink = logoHomePageLink(currentPage);

    this.add(creaLabelNomeUtente("utenteLoggatoDropDown"));
    this.add(logoHomePageLink);
    this.add(new NonCachingImage("immagine", getAvatar()));
    this.add(new UtenteLoggatoPanel("azioniUtenteLoggato", currentPage));

    logoHomePageLink.add(creaLabelNomeUtente("utenteLoggato"));
    logoHomePageLink.add(new NotEmptyLabel("delegaNome", getUsernameDelega(getUtente())));
    logoHomePageLink.add(new NotEmptyLabel("delegaId", getUtente().getIdDelega()));
  }

  private Label creaLabelNomeUtente(String wicketId) {
    String nomeUtente = getUsernameLogin(getUtente());
    return new Label(wicketId, nomeUtente);
  }

  private String getUsernameLogin(Utente utente) {
    String nomeUtente = "";
    log.debug("getUsernameLogin login:" + utente.getLogin());
    log.debug("getUsernameLogin nome:" + utente.getNome());
    log.debug("getUsernameLogin cognome:" + utente.getCognome());
    if ((utente.getNome() != null) && (utente.getCognome() != null)) {
      String nomeCognome = utente.getNome().concat(" ").concat(utente.getCognome());
      nomeUtente = WordUtils.capitalizeFully(nomeCognome);
    } else if (utente.getLogin() != null) {
      nomeUtente = WordUtils.capitalizeFully(utente.getLogin());
    }
    return nomeUtente;
  }

  private String getUsernameDelega(Utente utente) {
    String nomeUtente = "";
    if ((utente.getDelegatoNome() != null) && (utente.getDelegatoCognome() != null)) {
      String nomeCognome = utente.getDelegatoNome().concat(" ").concat(utente.getDelegatoCognome());
      nomeUtente = WordUtils.capitalizeFully(nomeCognome);
    } else {
      nomeUtente = utente.getDelegatoCodiceFiscale();
    }
    return nomeUtente;
  }

  private Link<Void> logoHomePageLink(LayoutBasePage currentPage) {

    Link<Void> homePage =
        new Link<Void>("logoHomePage") {

          private static final long serialVersionUID = -7759705956836051788L;

          @Override
          public void onClick() {
            this.setResponsePage(RedirectBaseLandingPage.class);
          }

          @Override
          public boolean isEnabled() {
            return isAttivoDuranteWizardPrimoAccesso(currentPage)
                && !getUtente().isUtenteDelegato();
          }
        };
    boolean linkEnabled = disabilitaSeHomePage(currentPage);
    homePage.setEnabled(linkEnabled);

    return homePage;
  }

  private boolean disabilitaSeHomePage(LayoutBasePage currentPage) {
    boolean linkEnabled = true;
    if (HomePage.class.isInstance(currentPage)) linkEnabled = false;
    return linkEnabled;
  }

  @SuppressWarnings("unused")
  private Link<Void> textHomePageLink(LayoutBasePage currentPage) {

    Link<Void> homePage =
        new Link<Void>("textHomePage") {

          private static final long serialVersionUID = -7759705956836051788L;

          @Override
          public void onClick() {
            this.setResponsePage(HomePage.class);
          }
        };
    boolean linkEnabled = disabilitaSeHomePage(currentPage);
    homePage.setEnabled(linkEnabled);
    return homePage;
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

  private boolean isAttivoDuranteWizardPrimoAccesso(LayoutBasePage currentPage) {

    if (Wizard1PrivacyPage.class.isInstance(currentPage)
        || Wizard2AutocertificazionePage.class.isInstance(currentPage)
        || PreLoadingPage.class.isInstance(currentPage)) {
      return false;
    } else if (AutocertificazioneGenitorePage.class.isInstance(currentPage)) {
      log.debug("istanza : AutocertificazioneGenitorePage");
      AutocertificazioneGenitorePage page = (AutocertificazioneGenitorePage) getPage();
      return !page.checkMappa();
    } else {
      return true;
    }
  }

  private IResource getAvatar() {

    return new DynamicImageResource() {

      private static final long serialVersionUID = -5718780936386736508L;

      @Override
      protected byte[] getImageData(IResource.Attributes attributes) {
        log.debug("sono sul metodo getAvatar");
        try {
          return ServiceLocator.getInstance()
              .getServiziConfigurazione()
              .getImagineCaricata(getUtente());
        } catch (BusinessException | ApiException | IOException e) {
          log.error("Errore durante il caricamento dell'avatar: ", e);
          return new byte[1];
        }
      }
    };
  }
}
