package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.panel.RiepilogoMieiDatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.panel.breadcrumb.BreadcrumbRiepilogoMieiDatiPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class RiepilogoMieiDatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4244675609347959169L;

  public RiepilogoMieiDatiPage(PageParameters parameters) {
    super(parameters);

    log.debug("RiepilogoMieiDatiPage");
    BreadcrumbRiepilogoMieiDatiPanel breadcrumbRiepilogoMieiDatiPanel =
        new BreadcrumbRiepilogoMieiDatiPanel("breadcrumbRiepilogoMieiDati");
    add(breadcrumbRiepilogoMieiDatiPanel);

    RiepilogoMieiDatiPanel mieiDatiPanel =
        new RiepilogoMieiDatiPanel("mieiDatiPanel", getMyPageParameters());
    add(mieiDatiPanel);
  }
}
