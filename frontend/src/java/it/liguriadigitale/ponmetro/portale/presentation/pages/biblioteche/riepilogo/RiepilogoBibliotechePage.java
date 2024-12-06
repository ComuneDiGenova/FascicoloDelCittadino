package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.panel.RiepilogoBibliotechePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class RiepilogoBibliotechePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8404439516337839392L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiepilogoBibliotechePage() {
    super();

    Utente.inizializzaPrivacySebina(getUtente());

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(getIdSezione(), getUtente()));
    addOrReplace(breadcrumbPanel);

    log.debug("creazione oggetto con id ioLeggoPanel in RiepilogoBibliotechePage");

    RiepilogoBibliotechePanel bibliotechePanel = new RiepilogoBibliotechePanel("ioLeggoPanel");
    addOrReplace(bibliotechePanel);
  }

  private String getIdSezione() {
    // idSezione su DB
    return "7";
  }
}
