package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.avvisoBonario;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.avvisoBonario.panel.DettaglioAvvisoBonarioPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;

public class DettaglioAvvisoBonarioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DettaglioAvvisoBonarioPage(int index) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public DettaglioAvvisoBonarioPage(
      int index, AvvisoBonario avvisoBonario, DettaglioVerbale verbale) {
    this(index);

    DettaglioAvvisoBonarioPanel avvisoBonarioPanel =
        new DettaglioAvvisoBonarioPanel("avvisoBonarioPanel", avvisoBonario, verbale);
    addOrReplace(avvisoBonarioPanel);
  }
}
