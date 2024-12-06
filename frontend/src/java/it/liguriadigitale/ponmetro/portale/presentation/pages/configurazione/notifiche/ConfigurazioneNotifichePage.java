package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.notifiche;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.notifiche.panel.SelezionaNotificheCompartiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.FiltraWidgetNotificheScadenzeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConfigurazioneNotifichePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6342911094566865695L;

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE ConfigurazionePage");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }

  public ConfigurazioneNotifichePage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    SelezionaNotificheCompartiPanel panel = creaPanelCfgNotificheComparti();
    add(panel);
  }

  // Utility function
  private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  private SelezionaNotificheCompartiPanel creaPanelCfgNotificheComparti() {

    SelezionaNotificheCompartiPanel messaggiPanel =
        new SelezionaNotificheCompartiPanel("notificheCompartiPanel");
    List<DatiNotificaComparto> listaNotifiche = getListaDatiNotificaComparto();
    List<DatiNotificaComparto> listaFiltrata =
        FiltraWidgetNotificheScadenzeUtil.filtraNotifichePerUtente(listaNotifiche, getUtente());
    messaggiPanel.fillDati(listaFiltrata);
    return messaggiPanel;
  }

  private List<DatiNotificaComparto> getListaDatiNotificaComparto() {

    List<DatiNotificaComparto> listaDatiNotificaComparto = new ArrayList<DatiNotificaComparto>();
    try {
      log.debug(
          "ConfigurazioneNotificheCompartiPage in getListaDatiNotificaComparto() con utente = "
              + getUtente());
      listaDatiNotificaComparto =
          ServiceLocator.getInstance()
              .getServiziConfigurazione()
              .getListaTutteNotificheComparti(getUtente());
      // SERVIZIO restituisce tutto lo storico
      // prendo una volta solo ogni comparto, per evitare di fare vedere
      // più volte quelli "chiusi"
      if (listaDatiNotificaComparto != null && !listaDatiNotificaComparto.isEmpty()) {
        listaDatiNotificaComparto =
            listaDatiNotificaComparto.stream()
                .filter(Objects::nonNull)
                .filter(distinctByKey(ithElem -> ithElem.getComparto().getIdComparto()))
                .collect(Collectors.toList());
      }
    } catch (BusinessException e) {
      log.error(" -- errore getListaDatiNotificaComparto:", e);
    } catch (ApiException e) {
      log.error(" -- errore getListaDatiNotificaComparto:", e);
    }
    return listaDatiNotificaComparto;
  }
}
