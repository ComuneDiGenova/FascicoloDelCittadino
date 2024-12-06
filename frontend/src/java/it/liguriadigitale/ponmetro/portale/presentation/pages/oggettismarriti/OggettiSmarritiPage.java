package it.liguriadigitale.ponmetro.portale.presentation.pages.oggettismarriti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.oggettismarriti.model.OggettiSmarriti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.oggettismarriti.panel.OggettiSmarritiPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OggettiSmarritiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4576474455468097692L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public OggettiSmarritiPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<OggettiSmarriti> listaOggettiSmarriti =
        popolaListaOggettiSmarriti(getUtente().getCodiceFiscaleOperatore());
    OggettiSmarritiPanel oggettiPanel =
        new OggettiSmarritiPanel("oggettiPanel", listaOggettiSmarriti);
    addOrReplace(oggettiPanel);

    setOutputMarkupId(true);
  }

  private List<OggettiSmarriti> popolaListaOggettiSmarriti(String codiceFiscale) {
    List<OggettiSmarriti> lista = new ArrayList<>();

    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziOggettiSmarriti()
              .getListaOggettiSmarriti(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaListaOggettiSmarriti: " + e.getMessage(), e);
    }

    return lista;
  }
}
