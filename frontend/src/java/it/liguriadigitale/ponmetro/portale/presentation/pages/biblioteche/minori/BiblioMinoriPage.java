package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.panel.BiblioMinoriPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class BiblioMinoriPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2380124193417157624L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BiblioMinoriPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    BiblioMinoriPanel biblioMinoriPanel = new BiblioMinoriPanel("biblioMinoriPanel");
    biblioMinoriPanel.fillDati(getUtente().listaFigliInNucleoAllargatoCoresidentiUnder18());
    addOrReplace(biblioMinoriPanel);

    setOutputMarkupId(true);
  }
}
