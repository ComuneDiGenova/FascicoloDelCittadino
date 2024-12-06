package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati.panel.ModificaDatiPersonaliBibliotechePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import java.util.List;

public class ModificaDatiPersonaliBibliotechePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8813252787178907050L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ModificaDatiPersonaliBibliotechePage(
      Utente utenteSebina, boolean isAggiornamentoDatiTutore) {
    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance().getServiziBiblioteche().getListaBreadcrumbModificaDati();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ModificaDatiPersonaliBibliotechePanel modificaDatiPanel =
        new ModificaDatiPersonaliBibliotechePanel(
            "modificaDatiPanel", utenteSebina, isAggiornamentoDatiTutore);
    addOrReplace(modificaDatiPanel);

    setOutputMarkupId(true);
  }
}
