package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.TeleriscaldamentoPanel;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;

public class TeleriscaldamentoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7292441301623313521L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public TeleriscaldamentoPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziTeleriscaldamento().popolaListaMessaggi();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi)
                .setRenderBodyOnly(true)
                .setVisible(false);
    addOrReplace(messaggi);

    TeleriscaldamentoPanel teleriscaldamentoPanel =
        new TeleriscaldamentoPanel("teleriscaldamentoPanel");
    teleriscaldamentoPanel.fillDati(popolaDomande(getUtente().getCodiceFiscaleOperatore()));
    addOrReplace(teleriscaldamentoPanel);

    setOutputMarkupId(true);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private List<DomandaTeleriscaldamento> popolaDomande(String codiceFiscale) {
    List<DomandaTeleriscaldamento> domande = new ArrayList<>();
    try {
      domande =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getDomande(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaDomande TELERISCALDAMENTO: " + e.getMessage(), e);
    }
    return domande;
  }
}
