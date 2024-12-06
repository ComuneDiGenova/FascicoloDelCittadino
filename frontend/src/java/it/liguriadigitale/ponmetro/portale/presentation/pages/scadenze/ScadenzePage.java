package it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.FiltraWidgetNotificheScadenzeUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.panel.CalendarioScadenzePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.panel.ListaScadenzePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.panel.breadcrumb.BreadcrumbScadenzePanel;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.basic.Label;

public class ScadenzePage extends LayoutNoFeedBackPage {

  private Log log = LogFactory.getLog(getClass());

  private static final long serialVersionUID = 8428611431468821575L;

  public ScadenzePage() {
    this(LocalDate.now());
  }

  public ScadenzePage(LocalDate day) {
    log.debug("ScadenzePage");
    inizializzaScadenze();
    BreadcrumbScadenzePanel breadcrumbScadenzePanel = new BreadcrumbScadenzePanel("breadcrumb");
    add(breadcrumbScadenzePanel);
    String meseInCorso = day.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    int iAnnoInCorso = day.getYear();
    int iMeseInCorso = day.getMonthValue();
    String scadenzeMese = "Scadenze del mese di " + meseInCorso + " " + iAnnoInCorso;
    Label mese = new Label("mese", scadenzeMese);
    add(mese);
    List<VScScadenzeUt> lista = getListaScadenze(iMeseInCorso, iAnnoInCorso);
    creaPanelCalendario(day, lista);
    creaPanelScadenze(day, lista);
  }

  private void creaPanelCalendario(LocalDate day, List<VScScadenzeUt> lista) {
    CalendarioScadenzePanel panelCalendario = new CalendarioScadenzePanel(day, lista);
    add(panelCalendario);
  }

  private void creaPanelScadenze(LocalDate localDate, List<VScScadenzeUt> lista) {
    ListaScadenzePanel panelScadenze = new ListaScadenzePanel(localDate, lista);
    add(panelScadenze);
  }

  private List<VScScadenzeUt> getListaScadenze(int mese, int anno) {
    List<VScScadenzeUt> listaFiltrata = new ArrayList<VScScadenzeUt>();
    try {
      final Utente utenteLoggato = getUtente();
      listaFiltrata =
          ServiceLocator.getInstance()
              .getServiziScadenze()
              .getListaScadenzeFiltrate(mese, anno, utenteLoggato);
      listaFiltrata =
          FiltraWidgetNotificheScadenzeUtil.filtraScadenze(listaFiltrata, utenteLoggato);

    } catch (BusinessException e) {
      log.error(" -- errore getListaScadenze:", e);
    } catch (ApiException e) {
      log.error(" -- errore getListaScadenze:", e);
    }
    return listaFiltrata;
  }

  private void inizializzaScadenze() {
    try {
      ServiceLocator.getInstance().getServiziScadenze().inizializzaScadenze(getUtente());
    } catch (BusinessException e) {
      log.error(" inizializzaScadenze() " + e.getMessage());
    }
  }
}
