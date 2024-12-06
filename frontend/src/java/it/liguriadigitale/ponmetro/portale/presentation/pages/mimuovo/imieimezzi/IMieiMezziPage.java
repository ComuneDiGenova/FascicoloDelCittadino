package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.noprintable.BolloPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.IMieiMezziPanel;

public class IMieiMezziPage extends BolloPage {

  private static final long serialVersionUID = -7319009828739115221L;

  public IMieiMezziPage() {
    super();

    if (getUtente().isBolloNonRaggiungibile()) {
      setResponsePage(new ErroreServiziPage("i miei mezzi "));
    } else {
      @SuppressWarnings({"unchecked", "rawtypes"})
      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel(
              "breadcrumbPanel",
              MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
      addOrReplace(breadcrumbPanel);

      IMieiMezziPanel panel =
          (IMieiMezziPanel) new IMieiMezziPanel("iMieiMezziPanel").setRenderBodyOnly(true);
      add(panel);
    }
  }
}
