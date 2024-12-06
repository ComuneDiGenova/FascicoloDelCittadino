package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaIstanza;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.MieIstanzeFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.MieIstanzePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.breadcrumb.BreadcrumbMieIstanzeFormPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class MieIstanzeFormPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1604523889928204045L;

  public MieIstanzeFormPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    MieIstanzeFormPanel mieIstanzeFormPanel =
        (MieIstanzeFormPanel)
            new MieIstanzeFormPanel("mieIstanzeFormPanel").setRenderBodyOnly(true);
    mieIstanzeFormPanel.fillDati(new RicercaIstanza());
    addOrReplace(mieIstanzeFormPanel);

    EmptyPanel mieiIstanzaPanelEmpty =
        (EmptyPanel) new EmptyPanel("mieIstanzePanel").setRenderBodyOnly(true);
    addOrReplace(mieiIstanzaPanelEmpty);

    setOutputMarkupId(true);
  }

  public MieIstanzeFormPage(List<Istanza> listaIstanze, RicercaIstanza ricercaIstanza) {
    super();

    BreadcrumbMieIstanzeFormPanel breadcrumbMieIstanzeFormPanel =
        (BreadcrumbMieIstanzeFormPanel)
            new BreadcrumbMieIstanzeFormPanel("breadcrumbPanel").setRenderBodyOnly(true);
    addOrReplace(breadcrumbMieIstanzeFormPanel);

    MieIstanzeFormPanel mieIstanzeFormPanel =
        (MieIstanzeFormPanel)
            new MieIstanzeFormPanel("mieIstanzeFormPanel").setRenderBodyOnly(true);
    mieIstanzeFormPanel.fillDati(ricercaIstanza);
    addOrReplace(mieIstanzeFormPanel);

    MieIstanzePanel mieIstanzePanel =
        (MieIstanzePanel) new MieIstanzePanel("mieIstanzePanel").setRenderBodyOnly(true);
    mieIstanzePanel.fillDati(listaIstanze);
    addOrReplace(mieIstanzePanel);

    setOutputMarkupId(true);
  }

  public MieIstanzeFormPage(
      List<Istanza> listaIstanze, RicercaIstanza istanzaCercata, boolean cercaIstanza) {
    super();

    BreadcrumbMieIstanzeFormPanel breadcrumbMieIstanzeFormPanel =
        (BreadcrumbMieIstanzeFormPanel)
            new BreadcrumbMieIstanzeFormPanel("breadcrumbPanel").setRenderBodyOnly(true);
    addOrReplace(breadcrumbMieIstanzeFormPanel);

    MieIstanzeFormPanel mieIstanzeFormPanel =
        (MieIstanzeFormPanel)
            new MieIstanzeFormPanel("mieIstanzeFormPanel").setRenderBodyOnly(true);
    mieIstanzeFormPanel.fillDati(istanzaCercata);
    addOrReplace(mieIstanzeFormPanel);

    MieIstanzePanel mieIstanzePanel =
        (MieIstanzePanel)
            new MieIstanzePanel("mieIstanzePanel", cercaIstanza).setRenderBodyOnly(true);
    mieIstanzePanel.fillDati(listaIstanze);
    addOrReplace(mieIstanzePanel);

    setOutputMarkupId(true);
  }
}
