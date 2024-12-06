package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.lista;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.lista.panel.ElencaIscrizioniPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.lista.panel.breadcrumb.BreadcrumbElencaIscrizioniPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;

public class ElencaIscrizioniPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4304512382083329473L;

  public ElencaIscrizioniPage() {

    creaPanelLista(getUtente().getListaFigli());

    BreadcrumbElencaIscrizioniPanel breadcrumbElencaIscrizioniPanel =
        new BreadcrumbElencaIscrizioniPanel("breadcrumbPanel");
    add(breadcrumbElencaIscrizioniPanel);
  }

  public ElencaIscrizioniPage(List<UtenteServiziRistorazione> lista) {
    log.debug("[ElencaIscrizioniPage] figli iscritti: " + lista.size());
    creaPanelLista(lista);
  }

  private void creaPanelLista(List<UtenteServiziRistorazione> lista) {
    ElencaIscrizioniPanel panel = new ElencaIscrizioniPanel(lista);
    add(panel);
  }
}
