package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.DettaglioVerbaliPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class DettaglioVerbaliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -9178141878105090784L;

  public Verbale verbale;

  public DettaglioVerbaliPage(Verbale verbale) {
    this(verbale, verbale.getNumeroProtocollo());
  }

  public DettaglioVerbaliPage(DettaglioVerbale dettaglio) {
    this(null, dettaglio.getNumeroProtocollo());
  }

  private DettaglioVerbaliPage(Verbale verbale, String numeroProtocollo) {
    super();
    this.verbale = verbale;

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DettaglioVerbaliPanel dettaglioVerbaliPanel =
        (DettaglioVerbaliPanel)
            new DettaglioVerbaliPanel("dettaglioVerbaliPanel", verbale).setRenderBodyOnly(true);

    DettaglioVerbale dettaglioVerbale = popolaDettagliVerbale(numeroProtocollo);
    dettaglioVerbaliPanel.fillDati(dettaglioVerbale);

    add(dettaglioVerbaliPanel);

    setOutputMarkupId(true);
  }

  private DettaglioVerbale popolaDettagliVerbale(String numeroProtocollo) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMieiVerbali()
          .getDettaglioVerbale(numeroProtocollo, getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
