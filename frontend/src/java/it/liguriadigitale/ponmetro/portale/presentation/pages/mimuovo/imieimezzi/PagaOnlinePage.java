package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.ponmetro.portale.pojo.bollo.PagamentoDto;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.PagaOnlinePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.breadcrumb.BreadcrumbPagaOnlinePanel;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;

public class PagaOnlinePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3422318334157406421L;

  public PagaOnlinePage() {
    super();
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PagaOnlinePanel panel =
        (PagaOnlinePanel) new PagaOnlinePanel("pagaOnlinePanel").setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }

  public PagaOnlinePage(PagamentoDto dto) {
    this(dto.getBollo(), dto.getDettaglioCalcoloBollo(), dto.getVeicolo());
  }

  public PagaOnlinePage(Bollo bollo, DettaglioCalcoloBollo dettaglioCalcoloBollo, Veicolo veicolo) {
    super();

    BreadcrumbPagaOnlinePanel breadcrumbPagaOnlinePanel =
        new BreadcrumbPagaOnlinePanel("breadcrumbPanel");
    add(breadcrumbPagaOnlinePanel);

    PagaOnlinePanel panel =
        (PagaOnlinePanel)
            new PagaOnlinePanel("pagaOnlinePanel", bollo, dettaglioCalcoloBollo, veicolo)
                .setRenderBodyOnly(true);
    panel.fillDati("");
    add(panel);
  }
}
