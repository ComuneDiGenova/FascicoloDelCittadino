package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.panel.ServiziSeggiElettoraliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.seggi.model.DatiPersonaliComponenteSeggio;
import it.liguriadigitale.ponmetro.seggi.model.Elezioni;
import java.io.IOException;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSeggiElettoraliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7037694758861911977L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ServiziSeggiElettoraliPage() {
    super();

    Elezioni elezioni;
    DatiPersonaliComponenteSeggio datiPersonaliComponenteSeggio;

    try {
      elezioni = ServiceLocator.getInstance().getServiziSeggiElettorali().getElezioni();

      datiPersonaliComponenteSeggio =
          ServiceLocator.getInstance()
              .getServiziSeggiElettorali()
              .getDatiPersonaliComponenteSeggio(
                  getUtente().getCodiceFiscaleOperatore(), elezioni.getIdentificativoElezione());

      if (datiPersonaliComponenteSeggio == null)
        datiPersonaliComponenteSeggio = new DatiPersonaliComponenteSeggio();

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("seggi elettorali"));
    }

    ServiziSeggiElettoraliPanel serviziSeggiElettoraliPanel =
        new ServiziSeggiElettoraliPanel(
            "seggiElettoraliPanel", elezioni, datiPersonaliComponenteSeggio);

    addOrReplace(serviziSeggiElettoraliPanel);
    setOutputMarkupId(true);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);
  }
}
