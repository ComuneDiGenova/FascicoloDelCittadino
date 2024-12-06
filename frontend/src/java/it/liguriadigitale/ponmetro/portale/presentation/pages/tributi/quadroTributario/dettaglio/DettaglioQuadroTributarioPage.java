package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.SchedaTributoExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.common.tributi.TributiErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.panel.DovutiVersatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.panel.OggettiPanel;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;

public class DettaglioQuadroTributarioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;

  public DettaglioQuadroTributarioPage(
      Utente utente, String uri, AttributeAppender atIcon, Integer annoSelezionato) {
    this(
        utente,
        HomeWebApplication.ID_DETTAGLIO_OFQUADROTRIBUTARIO_OFTRIBUTI_OFHOME,
        uri,
        atIcon,
        annoSelezionato);
  }

  public DettaglioQuadroTributarioPage(
      Utente utente, String idPage, String uri, AttributeAppender atIcon, Integer annoSelezionato) {
    super();
    initPage(idPage, uri, atIcon, annoSelezionato);
  }

  private void initPage(
      final String idPage, final String uri, AttributeAppender atIcon, Integer annoSelezionato) {
    // 1 - breadcrumb > path per navigare nel sito
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<SchedaTributoExt> listSchedaTributoExt = getListSchedaTributoExt(uri);

    // Seppur un array devo prendere il primo perche non dovrebbe essere
    // array ma uno solo /get by uri - uri id univico quindi uno solo)
    SchedaTributoExt schedaTributoExt = listSchedaTributoExt.get(0);

    DovutiVersatiPanel dovutiVersatiPanel = new DovutiVersatiPanel("dovutiVersatiPanel");
    dovutiVersatiPanel.fillDati(schedaTributoExt.getDovutiVersatiExt());
    add(dovutiVersatiPanel);

    OggettiPanel oggettiPanel = new OggettiPanel("oggettiPanel");
    oggettiPanel.fillDati(schedaTributoExt.getOggettiExt());
    add(oggettiPanel);
  }

  private List<SchedaTributoExt> getListSchedaTributoExt(final String uri) {
    try {
      return ServiceLocator.getInstance().getServiziQuadroTributario().getSchedaTributoExt(uri);
    } catch (ApiException e) {
      throw new RestartResponseAtInterceptPageException(TributiErrorPage.class);
    } catch (BusinessException e) {
      return null;
    }
  }
}
