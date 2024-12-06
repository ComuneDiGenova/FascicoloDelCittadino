package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.noprintable.BolloPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.AssicurazionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.breadcrumb.BreadcrumbAssicurazionePanel;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;

public class AssicurazionePage extends BolloPage {

  private static final long serialVersionUID = -782294125698121730L;

  public AssicurazionePage() {
    super();
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AssicurazionePanel panel = new AssicurazionePanel("assicurazionePanel");
    add(panel);
  }

  public AssicurazionePage(Veicolo veicolo) {
    super();

    BreadcrumbAssicurazionePanel breadcrumbAssicurazionePanel =
        new BreadcrumbAssicurazionePanel("breadcrumbPanel");
    add(breadcrumbAssicurazionePanel);

    AssicurazionePanel panel = new AssicurazionePanel("assicurazionePanel", veicolo);
    add(panel);
  }
}
