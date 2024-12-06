package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.catastoimpianti.model.ImpiantiTermici;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel.CaitelPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaitelPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5263993775365651137L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public CaitelPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    CaitelPanel caitelPanel = new CaitelPanel("caitelPanel");
    caitelPanel.fillDati(popolaImpianti(getUtente().getCodiceFiscaleOperatore()));
    addOrReplace(caitelPanel);

    setOutputMarkupId(true);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private List<Impianto> popolaImpianti(String codiceFiscale) {
    List<Impianto> listaImpianti = new ArrayList<Impianto>();
    try {
      ImpiantiTermici impianti =
          ServiceLocator.getInstance().getServiziImpiantiTermici().getImpianti(codiceFiscale);
      if (LabelFdCUtil.checkIfNotNull(impianti)) {
        listaImpianti = impianti.getImpianti();
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaImpianti: " + e.getMessage(), e);
    }
    return listaImpianti;
  }
}
