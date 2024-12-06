package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.panel.PrenotazioneCedolaPanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class PrenotazioneCedolaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public PrenotazioneCedolaPage(Cedola cedola) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PrenotazioneCedolaPanel panel =
        new PrenotazioneCedolaPanel("ritiro", trovaIscrittoDaCedola(cedola, getUtente()), cedola);
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
