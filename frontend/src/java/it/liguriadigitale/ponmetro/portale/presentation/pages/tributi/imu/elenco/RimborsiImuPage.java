package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco.panel.RimborsiImuPanel;
import java.util.ArrayList;
import java.util.List;

public class RimborsiImuPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1987987542154893215L;

  public RimborsiImuPage() {
    super();

    RimborsiImuPanel panel = new RimborsiImuPanel("rimborsiImuPanel");
    addOrReplace(panel);

    log.debug("[RichiestaRateizzazionePage] Recupero il breadcrump per RichiestaRateizzazionePage");
    List<BreadcrumbFdC> listaBreadcrumb = getBreadcrumb();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public List<BreadcrumbFdC> getBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioContribuente", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("rimborsiIMU", "Rimborsi IMU"));

    return listaBreadcrumb;
  }
}
