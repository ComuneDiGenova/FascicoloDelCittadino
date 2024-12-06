package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali;

import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.RevocaDietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.RevocaDietaSpecialePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.ArrayList;
import java.util.List;

public class RevocaDietaSpecialePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3397768425814369275L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RevocaDietaSpecialePage(
      DatiDietaSpeciale dieta, UtenteServiziRistorazione iscritto, Integer index) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    RevocaDietaSpecialePanel revocaDietaPanel =
        new RevocaDietaSpecialePanel(dieta, iscritto, index);
    addOrReplace(revocaDietaPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RevocaDietaSpecialePage(
      DatiDietaSpeciale dieta,
      UtenteServiziRistorazione iscritto,
      Integer index,
      String messaggioFeedback,
      RevocaDietaSpeciale revocaDietaSpeciale,
      boolean esitoRevoca) {
    super();

    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance().getServiziRistorazione().getListaBreadcrumbDieteSpeciali();
    FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    RevocaDietaSpecialePanel revocaDietaPanel =
        new RevocaDietaSpecialePanel(
            dieta, iscritto, index, messaggioFeedback, revocaDietaSpeciale, esitoRevoca);
    addOrReplace(revocaDietaPanel);
  }

  private List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC(getString("RevocaDietaSpecialePage.dati"), 1));
    listaStep.add(new StepFdC(getString("RevocaDietaSpecialePage.esito"), 2));

    return listaStep;
  }
}
