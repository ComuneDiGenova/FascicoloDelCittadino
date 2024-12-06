package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.MieiVerbaliFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.MieiVerbaliPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class MieiVerbaliFormPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;

  public MieiVerbaliFormPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    MieiVerbaliFormPanel mieiVerbaliFormPanel =
        (MieiVerbaliFormPanel)
            new MieiVerbaliFormPanel("mieiVerbaliFormPanel").setRenderBodyOnly(true);
    mieiVerbaliFormPanel.fillDati(new Verbale());
    addOrReplace(mieiVerbaliFormPanel);

    EmptyPanel mieiVerbaliPanelEmpty =
        (EmptyPanel) new EmptyPanel("mieiVerbaliPanel").setRenderBodyOnly(true);
    addOrReplace(mieiVerbaliPanelEmpty);

    setOutputMarkupId(true);
  }

  public MieiVerbaliFormPage(List<Verbale> listaVerbali, Verbale verbale) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    MieiVerbaliFormPanel mieiVerbaliFormPanel =
        (MieiVerbaliFormPanel)
            new MieiVerbaliFormPanel("mieiVerbaliFormPanel").setRenderBodyOnly(true);
    mieiVerbaliFormPanel.fillDati(verbale);
    addOrReplace(mieiVerbaliFormPanel);

    MieiVerbaliPanel mieiVerbaliPanel =
        (MieiVerbaliPanel)
            new MieiVerbaliPanel("mieiVerbaliPanel", listaVerbali).setRenderBodyOnly(true);

    mieiVerbaliPanel.fillDati(listaVerbali);
    addOrReplace(mieiVerbaliPanel);

    setOutputMarkupId(true);
  }
}
