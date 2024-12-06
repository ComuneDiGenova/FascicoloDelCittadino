package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class CedoleLibrariePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1218182417013177691L;

  public CedoleLibrariePage(UtenteServiziRistorazione iscritto) {
    super();

    Utente.inizializzaPrivacyServiziCedole(getUtente());

    if (getUtente().isServiziCedolePrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(new CedoleLibrarieConPrivacyPage(iscritto));
    } else {
      throw new RestartResponseAtInterceptPageException(new CedoleLibrariePrivacyPage(iscritto));
    }
  }
}
