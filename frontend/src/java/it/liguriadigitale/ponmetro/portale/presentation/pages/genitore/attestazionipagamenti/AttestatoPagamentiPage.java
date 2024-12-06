package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.attestazionipagamenti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.attestazionipagamenti.panel.AttestatoPagamentiPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class AttestatoPagamentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4676657845160185841L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AttestatoPagamentiPage(UtenteServiziRistorazione iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziRistorazione().popolaListaMessaggi();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    AttestatoPagamentiPanel attestatoPagamentiPanel =
        new AttestatoPagamentiPanel("attestatoPagamentiPanel", iscrizione);
    attestatoPagamentiPanel.fillDati(popolaListaAttestati(iscrizione));
    addOrReplace(attestatoPagamentiPanel);

    setOutputMarkupId(true);
  }

  private List<AttestazioniDiPagamento> popolaListaAttestati(UtenteServiziRistorazione iscrizione) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getAttestazioniPagamento(iscrizione.getCodiceFiscale());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Attestazioni pagamento mensa"));
    }
  }
}
