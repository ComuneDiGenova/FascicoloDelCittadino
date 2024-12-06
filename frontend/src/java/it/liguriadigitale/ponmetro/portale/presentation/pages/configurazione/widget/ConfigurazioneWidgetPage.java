package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget.panel.ScegliWidgetHomePagePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.FiltraWidgetNotificheScadenzeUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.WidgetUtil;
import java.util.HashMap;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class ConfigurazioneWidgetPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5165457182230334438L;

  public ConfigurazioneWidgetPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurabili =
        popolaListaWidgetDaConfigurare();
    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetFiltrati =
        FiltraWidgetNotificheScadenzeUtil.filtraWidgetPerUtente(widgetConfigurabili, getUtente());
    List<DatiSezione> lista = WidgetUtil.getListaSezioniOrdinate(widgetFiltrati);

    ListView<DatiSezione> listView =
        new ListView<DatiSezione>("sezioni", lista) {

          private static final long serialVersionUID = 5815380780463935152L;

          @Override
          protected void populateItem(ListItem<DatiSezione> item) {
            item.add(
                new ScegliWidgetHomePagePanel(
                    "scegli", item.getModelObject(), widgetFiltrati.get(item.getModelObject())));
          }
        };
    add(listView);
  }

  private HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>
      popolaListaWidgetDaConfigurare() {

    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurabili =
        new HashMap<>();
    try {
      List<DatiVisualizzazioneSezioneWidget> listaWidgetDaConfigurare =
          ServiceLocator.getInstance()
              .getServiziConfigurazione()
              .getListaWidgetPerConfigurazione(getUtente());
      widgetConfigurabili = WidgetUtil.creaMappaDaElencoWidget(listaWidgetDaConfigurare);
    } catch (BusinessException e) {
      log.error("Errore nella chiamata al servizio: ", e);
    }
    log.debug("widgetConfigurabili:" + widgetConfigurabili.size());
    return widgetConfigurabili;
  }
}
