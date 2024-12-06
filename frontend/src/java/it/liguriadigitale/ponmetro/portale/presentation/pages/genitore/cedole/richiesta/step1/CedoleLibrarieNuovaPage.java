package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.RichiestaCedola;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1.panel.DomandaCedolaStep1Panel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.DatiBambinoPanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.ajax.markup.html.AjaxLink;

public class CedoleLibrarieNuovaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  private UtenteServiziRistorazione iscritto;
  private DomandaCedola domanda;
  private AjaxLink<Void> linkprosegui;

  public CedoleLibrarieNuovaPage() {
    this(null);
  }

  public CedoleLibrarieNuovaPage(CedoleMinore cedolaMinore, DomandaCedola domanda) {
    super();
    iscritto = cedolaMinore.getMinore();
    this.domanda = domanda;
    setOutputMarkupId(true);

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DomandaCedolaStep1Panel panel = new DomandaCedolaStep1Panel("step1", cedolaMinore, domanda);
    add(panel);
    DatiBambinoPanel bambinoPanel = new DatiBambinoPanel("bambino", cedolaMinore);
    add(bambinoPanel);
  }

  public CedoleLibrarieNuovaPage(RichiestaCedola richiestaCedola) {
    super();

    if (richiestaCedola == null) {
      if (getSession().getAttribute("richiestaCedola") != null) {
        richiestaCedola = (RichiestaCedola) getSession().getAttribute("richiestaCedola");
      }
    } else {
      getSession().setAttribute("richiestaCedola", richiestaCedola);
    }

    iscritto = richiestaCedola.getCedolaMinore().getMinore();
    domanda = richiestaCedola.getDomanda();

    setOutputMarkupId(true);
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DomandaCedolaStep1Panel panel =
        new DomandaCedolaStep1Panel("step1", richiestaCedola.getCedolaMinore(), domanda);
    addOrReplace(panel);
    DatiBambinoPanel bambinoPanel =
        new DatiBambinoPanel("bambino", richiestaCedola.getCedolaMinore());
    addOrReplace(bambinoPanel);
  }
}
