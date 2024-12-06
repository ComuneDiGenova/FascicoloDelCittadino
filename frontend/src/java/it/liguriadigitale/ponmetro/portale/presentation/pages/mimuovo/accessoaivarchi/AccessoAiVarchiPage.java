package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.accessoaivarchi;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransitsResult;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.accessoaivarchi.panel.AccessoAiVarchiPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class AccessoAiVarchiPage extends LayoutNoFeedBackPage {

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AccessoAiVarchiPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    AccessoAiVarchiPanel varchiPanel = new AccessoAiVarchiPanel("varchiPanel");
    varchiPanel.fillDati(popolalistaAccessoAiVarchi());
    addOrReplace(varchiPanel);

    setOutputMarkupId(true);
  }

  private List<SuspectTransit> popolalistaAccessoAiVarchi() {

    List<SuspectTransit> listaVarchi = new ArrayList<>();

    try {

      SuspectTransitsResult permitsListResult =
          ServiceLocator.getInstance()
              .getServiziAccessoAiVarchi()
              .getSuspectTransits(getUtente().getCodiceFiscaleOperatore());
      listaVarchi = permitsListResult.getSuspectTransits();

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Accesso ai Varchi"));
    }

    return listaVarchi;
  }
}
