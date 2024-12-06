package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudioiban;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.BorsaStudioIBAN;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudioiban.panel.BorseStudioModificaIbanPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BorseStudioModificaIbanPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7653079392597772966L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BorseStudioModificaIbanPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<BorsaStudioIBAN> listaPratiche =
        popolaPraticheIban(getUtente().getCodiceFiscaleOperatore());
    BorseStudioModificaIbanPanel borseDiStudioIbanPanel =
        new BorseStudioModificaIbanPanel("borseDiStudioIbanPanel", listaPratiche);
    addOrReplace(borseDiStudioIbanPanel);

    setOutputMarkupId(true);
  }

  private List<BorsaStudioIBAN> popolaPraticheIban(String codiceFiscale) {
    List<BorsaStudioIBAN> listaPratiche = new ArrayList<>();
    try {
      listaPratiche =
          ServiceLocator.getInstance()
              .getServiziBorseDiStudio()
              .getListaBorseStudioIban(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaPraticheIbanFinto: " + e.getMessage(), e);
    }
    return listaPratiche;
  }
}
