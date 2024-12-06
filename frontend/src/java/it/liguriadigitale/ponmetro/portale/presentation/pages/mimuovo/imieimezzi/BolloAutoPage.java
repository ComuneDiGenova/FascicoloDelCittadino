package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.noprintable.BolloPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.BolloAutoPanel;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;

public class BolloAutoPage extends BolloPage {

  private static final long serialVersionUID = -8403674730184820958L;

  public BolloAutoPage() {
    super();

    if (getUtente().isBolloNonRaggiungibile()) {
      setResponsePage(new ErroreServiziPage("i miei mezzi "));
    } else {
      @SuppressWarnings({"rawtypes", "unchecked"})
      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel(
              "breadcrumbPanel",
              MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
      addOrReplace(breadcrumbPanel);

      BolloAutoPanel panel =
          (BolloAutoPanel) new BolloAutoPanel("bolloAutoPanel").setRenderBodyOnly(true);
      panel.fillDati("");
      add(panel);
    }
  }

  public BolloAutoPage(Veicolo veicolo) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    BolloAutoPanel panel =
        (BolloAutoPanel) new BolloAutoPanel("bolloAutoPanel", veicolo).setRenderBodyOnly(true);
    add(panel);
  }
}
