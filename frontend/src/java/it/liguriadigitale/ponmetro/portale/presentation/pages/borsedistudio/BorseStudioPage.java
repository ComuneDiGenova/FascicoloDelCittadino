package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.panel.BorseStudioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;

public class BorseStudioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7517236787440862377L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BorseStudioPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<Legenda> listaLegenda =
        ServiceLocator.getInstance().getServiziBorseDiStudio().getListaLegenda();
    LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
    addOrReplace(legendaPanel);

    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    try {
      listaMessaggi =
          ServiceLocator.getInstance().getServiziBorseDiStudio().popolaListaMessaggiBorseDiStudio();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore messaggi borse studio " + e.getMessage(), e);
    }

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<Pratica> listaPratiche = popolaPratiche(getUtente().getCodiceFiscaleOperatore());
    BorseStudioPanel borseDiStudioPanel = new BorseStudioPanel("borseDiStudioPanel", listaPratiche);
    addOrReplace(borseDiStudioPanel);

    setOutputMarkupId(true);
  }

  private List<Pratica> popolaPratiche(String codiceFiscale) {
    List<Pratica> listaPratiche = new ArrayList<>();
    try {
      listaPratiche =
          ServiceLocator.getInstance()
              .getServiziBorseDiStudio()
              .listaPraticheBorseDiStudio(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaPratiche: " + e.getMessage(), e);
    }
    return listaPratiche;
  }
}
