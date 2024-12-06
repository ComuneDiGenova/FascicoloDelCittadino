package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.panel.BiblioInternetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteAbil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class BiblioInternetPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1955484800492425929L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BiblioInternetPage(Utente utenteSebina) {
    super();

    if (getUtente().isSebinaPrivacyNonAccettata()) {

      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel(
              "breadcrumbPanel",
              MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
      addOrReplace(breadcrumbPanel);

      List<UtenteAbil> abilitazioni = getAbilitazioni(utenteSebina);
      BiblioInternetPanel biblioInternetPanel =
          new BiblioInternetPanel("biblioInternetPanel", utenteSebina, abilitazioni);
      addOrReplace(biblioInternetPanel);
    } else {
      Boolean maggiorenne = false;
      throw new RestartResponseAtInterceptPageException(
          new BibliotecheMovimentiPrivacyPage(maggiorenne));
    }

    setOutputMarkupId(true);
  }

  private List<UtenteAbil> getAbilitazioni(Utente utenteSebina) {
    List<UtenteAbil> lista = new ArrayList<UtenteAbil>();

    String servizioInternet = "M_INTERNET";
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziBiblioteche()
              .findUtenteAbil(utenteSebina.getId(), servizioInternet, null);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore GET abilitazioni " + e.getMessage());
    }

    return lista;
  }
}
