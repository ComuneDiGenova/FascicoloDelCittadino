package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo;

import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.MioNucleoPanel;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;

public class MioNucleoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6342911094566865695L;

  @SuppressWarnings("rawtypes")
  public MioNucleoPage() {
    super();

    if (Constants.LAZY_PANEL) {
      createLazyPanelMioNucleo("mioNucleo");
    } else {
      add(creaMioNucleoPanel("mioNucleo"));
    }

    @SuppressWarnings("unchecked")
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  @SuppressWarnings("rawtypes")
  private void createLazyPanelMioNucleo(String id) {
    AjaxLazyLoadPanel ajaxLazyLoadPanel =
        new AjaxLazyLoadPanel(id) {

          private static final long serialVersionUID = -1621006830078864694L;

          @Override
          public Component getLazyLoadComponent(String id) {
            return creaMioNucleoPanel(id).setRenderBodyOnly(true);
          }
        };
    add(ajaxLazyLoadPanel);
  }

  private MioNucleoPanel creaMioNucleoPanel(String id) {
    return new MioNucleoPanel(id);
  }
}
