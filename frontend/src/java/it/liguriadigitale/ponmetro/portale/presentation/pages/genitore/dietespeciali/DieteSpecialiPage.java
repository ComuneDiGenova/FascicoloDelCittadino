package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.DieteSpecialiPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.ArrayList;
import java.util.List;

public class DieteSpecialiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5931431189221652429L;

  public DieteSpecialiPage() {
    this(null);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DieteSpecialiPage(UtenteServiziRistorazione iscrizione) {
    super();

    if (iscrizione == null) {
      if (getSession().getAttribute("iscrittoDieteSpeciali") != null) {
        iscrizione = (UtenteServiziRistorazione) getSession().getAttribute("iscrittoDieteSpeciali");
      }
    } else {
      getSession().setAttribute("iscrittoDieteSpeciali", iscrizione);
    }

    List<DatiDietaSpeciale> listaDieteSpeciali =
        popolaListaDieteSpeciali(iscrizione.getCodiceFiscale());
    DieteSpecialiPanel dieteSpecialiPanel = new DieteSpecialiPanel(iscrizione, listaDieteSpeciali);
    addOrReplace(dieteSpecialiPanel);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private List<DatiDietaSpeciale> popolaListaDieteSpeciali(String codiceFiscaleIscritto) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getDieteSpeciali(codiceFiscaleIscritto);
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      return new ArrayList<DatiDietaSpeciale>();
    }
  }
}
