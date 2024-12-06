package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class BibliotecheMovimentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5456229316639764819L;

  public BibliotecheMovimentiPage() {
    super();

    if (getUtente().isSebinaPrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(new BibliotecheMovimentiDettaglioPage());
    } else {
      throw new RestartResponseAtInterceptPageException(new BibliotecheMovimentiPrivacyPage());
    }
  }

  public BibliotecheMovimentiPage(ComponenteNucleo bambino, boolean maggiorenne) {
    super();

    if (getUtente().isSebinaPrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(
          new BibliotecheMovimentiDettaglioPage(bambino));
    } else {
      throw new RestartResponseAtInterceptPageException(
          new BibliotecheMovimentiPrivacyPage(bambino, maggiorenne));
    }
  }
}
