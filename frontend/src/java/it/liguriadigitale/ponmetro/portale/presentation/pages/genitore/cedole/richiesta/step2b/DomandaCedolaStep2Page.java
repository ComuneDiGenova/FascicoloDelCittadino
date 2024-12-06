package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b.panel.DomandaCedolaStep2Panel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.DatiBambinoPanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;

public class DomandaCedolaStep2Page extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public DomandaCedolaStep2Page(CedoleMinore cedolaMinore, DomandaCedola domandaCedola) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DomandaCedolaStep2Panel panel =
        new DomandaCedolaStep2Panel("step2", cedolaMinore, domandaCedola);
    add(panel);
    DatiBambinoPanel bambinoPanel = new DatiBambinoPanel("bambino", cedolaMinore);
    add(bambinoPanel);

    log.debug("cedolaMinore: " + cedolaMinore);
  }
}
