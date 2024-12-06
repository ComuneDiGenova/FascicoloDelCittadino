package it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.pagopa.GestionePagoPAInterface;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import java.math.BigDecimal;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.flow.RedirectToUrlException;

public class PagamentoOnLinePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5132915525336121116L;

  private static final String URI_SERVER_PAGAMENTO = BaseServiceImpl.URI_SERVER_PAGAMENTO;
  private static final String ACTION_REDIRECT_PAGAMENTO = "pagamentoesterno.do";

  public PagamentoOnLinePage() {
    this(datiPerProva());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public PagamentoOnLinePage(MipData data) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    paga(data);
  }

  private static MipData datiPerProva() {
    MipData data = new MipData();
    data.setIdServizio("1000");
    data.setNumeroDocumento("9999");
    data.setImportoAvviso(BigDecimal.ONE);
    return data;
  }

  private void paga(MipData data) {

    checkData(data);

    // redirect della richiesta al server nodo di Genova
    log.debug("Inizio - pagamento");
    GestionePagoPAInterface service = ServiceLocator.getInstance().getServiziPagoPA();
    try {

      String xmlBufferRichiesta = service.preparaPagamento(data);
      log.debug(
          "FACCIO REDIRECT su URL del COMUNE " + URI_SERVER_PAGAMENTO + ACTION_REDIRECT_PAGAMENTO);
      throw new RedirectToUrlException(
          URI_SERVER_PAGAMENTO + ACTION_REDIRECT_PAGAMENTO + "?buffer=" + xmlBufferRichiesta);
    } catch (BusinessException e) {
      log.error("Errore grave durante la chiamata: ", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private void checkData(MipData data) {

    if (data.getUtente() == null) {
      data.setUtente(getUtente());
    }
  }
}
