package it.liguriadigitale.ponmetro.portale.presentation.pages.arte;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Componenti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel.ComponentiNucleoFamiliareArtePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.io.IOException;

public class ComponentiNucleoFamiliareArtePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6825575512033096092L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ComponentiNucleoFamiliareArtePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ComponentiNucleoFamiliareArtePanel componentiPanel =
        new ComponentiNucleoFamiliareArtePanel("componentiPanel");
    componentiPanel.fillDati(popolaComponenti(getUtente().getCodiceFiscaleOperatore()));
    addOrReplace(componentiPanel);

    setOutputMarkupId(true);
  }

  // con servizio vero
  private Componenti popolaComponenti(String codiceFiscale) {
    Componenti dati = null;
    try {
      dati = ServiceLocator.getInstance().getServiziArte().getComponenti(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaContrattiArte: " + e.getMessage(), e);
    }
    return dati;
  }

  // private Componenti popolaComponentiFinto(String codiceFiscale) {
  // Componenti componenti = new Componenti();
  //
  // List<Componente> listaComponenti = new ArrayList<>();
  //
  // Componente componente1 = new Componente();
  // componente1.setIdComponente(1);
  // componente1.setNome("Pino");
  // componente1.setCognome("Rossi");
  // componente1.setcFisc("RSSPNI80A01D969W");
  // componente1.setDataInizio(LocalDate.of(2022, 1, 25));
  // listaComponenti.add(componente1);
  //
  // listaComponenti.add(componente1);
  // listaComponenti.add(componente1);
  // listaComponenti.add(componente1);
  // listaComponenti.add(componente1);
  //
  // componenti.setComponentiNucleo(listaComponenti);
  //
  // return componenti;
  // }
}
