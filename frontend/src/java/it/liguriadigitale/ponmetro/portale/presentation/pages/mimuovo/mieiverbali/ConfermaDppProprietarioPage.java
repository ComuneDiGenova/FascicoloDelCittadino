package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.ConfermaDppProprietarioPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;

public class ConfermaDppProprietarioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1870380081375221775L;

  public ConfermaDppProprietarioPage(
      PuntiPatenteProprietario patenteProprietario, Verbale verbale) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfermaDppProprietarioPanel confermaDppProprietarioPanel =
        new ConfermaDppProprietarioPanel(patenteProprietario);
    addOrReplace(confermaDppProprietarioPanel);
  }
}
