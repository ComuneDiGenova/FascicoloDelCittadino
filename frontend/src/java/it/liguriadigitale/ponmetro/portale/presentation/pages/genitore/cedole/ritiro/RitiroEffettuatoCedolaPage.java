package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.breadcrumb.BreadcrumbCedolePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro.panel.RitiroEffettuatoCedolaPanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class RitiroEffettuatoCedolaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public RitiroEffettuatoCedolaPage(Cedola cedola) {
    super();

    BreadcrumbCedolePanel breadcrumbMieiFigliPanel = new BreadcrumbCedolePanel("breadcrumbPanel");
    add(breadcrumbMieiFigliPanel);

    RitiroEffettuatoCedolaPanel panel =
        new RitiroEffettuatoCedolaPanel(
            "ritiro", cedola, trovaIscrittoDaCedola(cedola, getUtente()));
    add(panel);
  }

  public static UtenteServiziRistorazione trovaIscrittoDaCedola(Cedola cedola, Utente utente) {

    for (UtenteServiziRistorazione iscritto : utente.getListaFigli()) {
      if (iscritto.getCodiceFiscale().equalsIgnoreCase(cedola.getCodiceFiscaleMinore())) {
        return iscritto;
      }
    }
    return null;
  }
}
