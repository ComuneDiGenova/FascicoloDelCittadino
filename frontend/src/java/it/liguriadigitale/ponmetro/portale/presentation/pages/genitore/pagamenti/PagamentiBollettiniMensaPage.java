package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.PagamentiBollettiniMensaRisultatiRicercaPanel;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class PagamentiBollettiniMensaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1053299625666474778L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PagamentiBollettiniMensaPage() {
    super();

    initPage(getUtente());

    //		List<BreadcrumbFdC> listaBreadcrumb = ServiceLocator.getInstance().getServiziRistorazione()
    //				.getListaBreadcrumbPagamenti();
    //		FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel",
    // listaBreadcrumb);
    //		addOrReplace(breadcrumbPanel);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  private void initPage(final Utente utente) {
    PagamentiBollettiniMensaRisultatiRicercaPanel bollettiniPanel =
        new PagamentiBollettiniMensaRisultatiRicercaPanel("bollettiniPanel");
    bollettiniPanel.fillDati(popolaBollettiniEstesi());
    add(bollettiniPanel);
  }

  private List<DatiPagamentiBollettiniRiepilogativiEstesi> popolaBollettiniEstesi() {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getBollettiniImpegnato(getUtente());
    } catch (BusinessException | ApiException e) {
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
