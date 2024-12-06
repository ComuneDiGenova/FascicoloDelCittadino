package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.panel.RiepilogoIscrizioniPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RiepilogoIscrizioniPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiepilogoIscrizioniPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiepilogoIscrizioniPanel panel = new RiepilogoIscrizioniPanel("riepilogoPanel");
    List<UtenteServiziRistorazione> lista = getUtente().getListaFigli();
    List<UtenteServiziRistorazione> sortedLista =
        lista.stream()
            .sorted(Comparator.comparing(UtenteServiziRistorazione::getDataNascita))
            .collect(Collectors.toList());
    panel.fillDati(sortedLista);
    add(panel);
  }
}
