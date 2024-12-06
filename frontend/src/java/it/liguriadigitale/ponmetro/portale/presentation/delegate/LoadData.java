package it.liguriadigitale.ponmetro.portale.presentation.delegate;

import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import org.apache.wicket.Session;

public class LoadData {

  public static Residente caricaMieiDatiResidente(Session mySession) {
    LoginInSession session = (LoginInSession) mySession;
    return getUtente(session).getDatiCittadinoResidente();
    //		try {
    //			if (getUtente(session).getDatiCittadinoResidente() == null) {
    //				getUtente(session).setDatiCittadinoResidente(
    //
    //	ServiceLocator.getInstance().getServizioDemografico().getDatiResidente(getUtente(session)));
    //			}
    //			return getUtente(session).getDatiCittadinoResidente();
    //		} catch (BusinessException e) {
    //			log.debug("Errore durante la chiamata delle API", e);
    //			throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    //		} catch (ApiException e) {
    //			log.debug("Errore durante la chiamata delle API", e);
    //			throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    //		}
  }

  private static Utente getUtente(LoginInSession session) {
    return session.getUtente();
  }
}
