package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.util.SegnalazioniCzrmUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel.HelpCzRMDomandePanel;
import java.io.IOException;

public class HelpCzRMDomandePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5159290503982426750L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public HelpCzRMDomandePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    SegnalazioniCzrmUtil listaSegnalazioni =
        popolaSegnalazioni(getUtente().getCodiceFiscaleOperatore());
    HelpCzRMDomandePanel segnalazioniCzrmPanel =
        new HelpCzRMDomandePanel("segnalazioniCzrmPanel", listaSegnalazioni);
    addOrReplace(segnalazioniCzrmPanel);

    setOutputMarkupId(true);
  }

  private SegnalazioniCzrmUtil popolaSegnalazioni(String codiceFiscale) {
    log.debug("HelpCzRMDomandePage popolaSegnalazioni");

    SegnalazioniCzrmUtil segnalazioni = null;
    try {
      return ServiceLocator.getInstance()
          .getSegnalazioniAccenture()
          .getListaSegnalazioniCzrm(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaSegnalazioni: " + e.getMessage(), e);
    }
    return segnalazioni;
  }
}
