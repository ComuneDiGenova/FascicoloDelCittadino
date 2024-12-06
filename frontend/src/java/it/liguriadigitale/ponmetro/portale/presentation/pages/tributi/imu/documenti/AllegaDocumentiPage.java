package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.panel.AllegaDocumentiPanel;

public class AllegaDocumentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1313213255646548L;

  public AllegaDocumentiPage() {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public AllegaDocumentiPage(RimborsoImu rimborso) {
    this();

    log.debug("[AllegaDocumentiPage] Rimborso: " + rimborso);

    AllegaDocumentiPanel allegaDocumentiPanel =
        new AllegaDocumentiPanel("allegaDoumentiPanel", rimborso);
    addOrReplace(allegaDocumentiPanel);
  }

  public AllegaDocumentiPage(Integer praticaRimborsoId, boolean readOnly) {
    // TODO Auto-generated constructor stub

    this();

    RimborsoImu rimborso = getDettaglioPraticaRimborso(praticaRimborsoId);

    AllegaDocumentiPanel allegaDocumentiPanel =
        new AllegaDocumentiPanel("allegaDoumentiPanel", rimborso, readOnly);
    addOrReplace(allegaDocumentiPanel);
  }

  private RimborsoImu getDettaglioPraticaRimborso(Integer praticaRimborsoId) {
    // TODO Auto-generated method stub
    try {
      return ServiceLocator.getInstance()
          .getServiziImuEng()
          .dettaglioPraticaRimborsoImu(praticaRimborsoId);

    } catch (ApiException | BusinessException e) {
      // TODO Auto-generated catch block
      log.debug("[getDettaglioPraticaRimborso] ");
      return new RimborsoImu();
    }
  }
}
