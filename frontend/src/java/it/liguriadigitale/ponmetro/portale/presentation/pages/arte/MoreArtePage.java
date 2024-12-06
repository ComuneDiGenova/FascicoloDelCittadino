package it.liguriadigitale.ponmetro.portale.presentation.pages.arte;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Mora;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel.MoreArtePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.io.IOException;

public class MoreArtePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2987770052293473450L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public MoreArtePage(String idImmobile) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    MoreArtePanel morePanel = new MoreArtePanel("morePanel");
    morePanel.fillDati(popolaMore(getUtente().getCodiceFiscaleOperatore(), idImmobile));
    addOrReplace(morePanel);

    setOutputMarkupId(true);
  }

  private Mora popolaMore(String codiceFiscale, String idImmobile) {
    Mora dati = null;
    try {
      dati = ServiceLocator.getInstance().getServiziArte().getMore(codiceFiscale, idImmobile);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaMore: " + e.getMessage(), e);
    }
    log.debug("CP popola more = " + dati);
    return dati;
  }

  // private Mora popolaMoreFinto(String codiceFiscale, String idImmobile) {
  // Mora dati = new Mora();
  //
  // List<FatturaNonPagata> lista = new ArrayList<>();
  // FatturaNonPagata f1 = new FatturaNonPagata();
  //
  // f1.setId(1);
  // f1.setIdImmobile(idImmobile);
  // f1.setAnno(new BigDecimal(2021));
  // f1.setMese(new BigDecimal(1));
  // f1.setScadenza(LocalDate.of(2022, 1, 1));
  // f1.setEmesso("100");
  // f1.setIncassato("100");
  // f1.setMora("aaa");
  // f1.setNomePdf("Prova.pdf");
  //
  // lista.add(f1);
  // lista.add(f1);
  // lista.add(f1);
  // lista.add(f1);
  // lista.add(f1);
  //
  // dati.setFattureNonPagate(lista);
  // return dati;
  // }

}
