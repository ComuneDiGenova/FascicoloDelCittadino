package it.liguriadigitale.ponmetro.portale.presentation.pages.arpal;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.arpal.model.ItemA;
import it.liguriadigitale.ponmetro.arpal.model.ItemS;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arpal.panel.PraticheArpalAlimsPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arpal.panel.PraticheArpalSimpaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;

public class PraticheArpalPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6826242279970673419L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public PraticheArpalPage() throws ApiException, BusinessException {
    super();

    createFeedBackPanel();
    feedBackErroreMessageSimpaAlims();

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziArpal().popolaListaMessaggiArpal();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PraticheArpalAlimsPanel alimsArpalPanel =
        new PraticheArpalAlimsPanel("alimsArpalPanel", popolaPraticheArpalAlims());

    PraticheArpalSimpaPanel simpaArpalPanel =
        new PraticheArpalSimpaPanel("simpaArpalPanel", popalaPraticheArpalSimpa());

    addOrReplace(alimsArpalPanel);
    addOrReplace(simpaArpalPanel);
    setOutputMarkupId(true);
  }

  private List<ItemA> popolaPraticheArpalAlims() {

    List<ItemA> lista = new ArrayList<ItemA>();
    try {
      lista =
          ServiceLocator.getInstance().getServiziArpal().elencoProveLaboratorioAlims(getUtente());
      log.debug(" lista alims : " + lista);
    } catch (ApiException | BusinessException e) {
      log.error("Errore API: ", e);
      error("Errore durante la chiamata ad Arpal- ALIMS");
    }

    return lista;
  }

  private List<ItemS> popalaPraticheArpalSimpa() {

    List<ItemS> lista = new ArrayList<ItemS>();
    try {
      lista =
          ServiceLocator.getInstance().getServiziArpal().elencoProveLaboratorioSimpa(getUtente());
      log.debug(" lista simpa : " + lista);
    } catch (ApiException | BusinessException e) {
      log.error("Errore API: ", e);
      error("Errore durante la chiamata ad Arpal - SIMPA");
    }

    return lista;
  }

  private void feedBackErroreMessageSimpaAlims() {

    @SuppressWarnings("unused")
    List<ItemS> simpa = new ArrayList<ItemS>();
    @SuppressWarnings("unused")
    List<ItemA> alims = new ArrayList<ItemA>();
    try {
      simpa =
          ServiceLocator.getInstance().getServiziArpal().elencoProveLaboratorioSimpa(getUtente());
      alims =
          ServiceLocator.getInstance().getServiziArpal().elencoProveLaboratorioAlims(getUtente());
    } catch (ApiException | BusinessException e) {
      log.error("Errore API: ", e);
      error("Non risultano né prove di laboratorio né verbali di verifica impianti.");
    }
  }
}
