package it.liguriadigitale.ponmetro.portale.presentation.pages.arte;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Daticontr;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel.ContrattiArtePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.io.IOException;

public class ContrattiArtePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3281057827816586731L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ContrattiArtePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ContrattiArtePanel artePanel = new ContrattiArtePanel("artePanel");
    artePanel.fillDati(popolaContrattiArte(getUtente().getCodiceFiscaleOperatore()));
    addOrReplace(artePanel);

    setOutputMarkupId(true);
  }

  private Daticontr popolaContrattiArte(String codiceFiscale) {
    Daticontr dati = null;
    try {
      dati = ServiceLocator.getInstance().getServiziArte().getDatiContratti(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaContrattiArte: " + e.getMessage(), e);
    }
    return dati;
  }
}
