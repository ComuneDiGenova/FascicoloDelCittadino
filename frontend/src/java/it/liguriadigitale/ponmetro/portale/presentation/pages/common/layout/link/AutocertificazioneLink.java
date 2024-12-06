package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.link;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.application.session.AbstractLoginInSession;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.account.AutocertificazioneGenitorePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.PreLoadingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard1PrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.Link;

public class AutocertificazioneLink extends Link<Void> {

  private static final long serialVersionUID = 7025898748487251956L;

  private static Log log = LogFactory.getLog(AutocertificazioneLink.class);

  public AutocertificazioneLink(String id) {
    super(id);
  }

  @Override
  public void onClick() {
    setResponsePage(new AutocertificazioneGenitorePage());
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean isVisible() {

    boolean isVisible = true;

    Page currentPage = getPage();
    if ((currentPage.getClass().equals(Wizard1PrivacyPage.class))
        || (currentPage.getClass().equals(Wizard2AutocertificazionePage.class))
        || PreLoadingPage.class.isInstance(currentPage)) {
      return false;
    }

    AbstractLoginInSession<Utente> session = (AbstractLoginInSession<Utente>) Session.get();
    if (session != null) {
      Utente utente = session.getUtente();
      if (utente == null) {
        isVisible = false;
      } else if (utente.isResidente()) {
        if (!utente.isMaggiorenne()) {
          isVisible = false;
        }
        try {
          List<MinoreConvivente> listaMinoriConviventi =
              ServiceLocator.getInstance()
                  .getServizioDemografico()
                  .getFigliPerAutodichiarazione(utente);
          if (listaMinoriConviventi.isEmpty()) {
            isVisible = false;
          }
        } catch (BusinessException | ApiException e) {
          log.debug("Errore autocertificazione link " + e.getMessage());
        }
      } else {
        isVisible = true;
      }
    } else {
      isVisible = false;
    }

    return isVisible;
  }
}
