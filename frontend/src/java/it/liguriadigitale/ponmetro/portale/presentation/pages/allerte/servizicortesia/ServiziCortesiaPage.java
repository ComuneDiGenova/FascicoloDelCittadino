package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1218182417013177691L;

  public ServiziCortesiaPage() {
    super();

    Utente.inizializzaPrivacyServiziCortesia(getUtente());

    if (getUtente().isServiziCortesiaPrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(new ServiziCortesiaConPrivacyPage());
    } else {
      throw new RestartResponseAtInterceptPageException(new ServiziCortesiaPrivacyPage());
    }
  }
}
