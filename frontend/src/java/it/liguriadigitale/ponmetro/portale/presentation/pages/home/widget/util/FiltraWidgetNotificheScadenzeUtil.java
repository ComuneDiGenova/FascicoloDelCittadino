package it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util;

import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FiltraWidgetNotificheScadenzeUtil {

  private static Log log = LogFactory.getLog(FiltraWidgetNotificheScadenzeUtil.class);

  private static final String MIEI_MEZZI = "MieiMezzi";
  private static final String IO_MI_MUOVO = "ioMiMuovo";
  private static final String IO_GENITORE = "ioGenitore";
  private static final String TRASPORTO_PRIVATO = "Trasporto privato";

  private FiltraWidgetNotificheScadenzeUtil() {
    super();
  }

  public static HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> filtraWidgetPerUtente(
      HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurabili,
      Utente utente) {

    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> listaDopoCheckFigli =
        filtraWidgetSenzaFigli(widgetConfigurabili, utente);
    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> listaFinale =
        filtraWidgetSenzaVeicoli(listaDopoCheckFigli, utente);
    return listaFinale;
  }

  public static List<DatiNotificaComparto> filtraNotifichePerUtente(
      List<DatiNotificaComparto> listaNotifiche, Utente utente) {

    List<DatiNotificaComparto> listaNotificheFiltrate1 =
        filtraNotificheSenzaFigli(listaNotifiche, utente);
    List<DatiNotificaComparto> listaNotificheFiltrate2 =
        filtraNotificheSenzaVeicoli(listaNotificheFiltrate1, utente);

    return listaNotificheFiltrate2;
  }

  public static List<VScScadenzeUt> filtraScadenze(
      List<VScScadenzeUt> listaFiltrata, Utente utente) {

    List<VScScadenzeUt> listaDopoCheckFigli = filtraScadenzeSenzaFigli(listaFiltrata, utente);
    List<VScScadenzeUt> listaFinale = filtraScadenzeSenzaVeicoli(listaDopoCheckFigli, utente);
    return listaFinale;
  }

  @SuppressWarnings("unchecked")
  private static HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>
      filtraWidgetSenzaVeicoli(
          HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurabili,
          Utente utente) {

    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetFiltrati =
        (HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>) widgetConfigurabili.clone();
    if (utente.getListaVeicoliAttivi().isEmpty()) {
      Set<DatiSezione> sezioni = widgetFiltrati.keySet();
      for (DatiSezione sezione : sezioni) {
        log.debug("sezione.getDenominazioneSez():" + sezione.getDenominazioneSez());
        if (sezione.getDenominazioneSez().equalsIgnoreCase(IO_MI_MUOVO)) {
          log.debug("trovato Io mi muovo");
          List<DatiVisualizzazioneSezioneWidget> listaFunzioni = widgetFiltrati.get(sezione);
          log.debug("listaFunzioni.size=" + listaFunzioni.size());
          listaFunzioni.removeIf(
              b -> b.getWidget().getDenominazioneWidg().equalsIgnoreCase(MIEI_MEZZI));
          log.debug("listaFunzioni.size=" + listaFunzioni.size());
        }
        log.debug("widgetFiltrati.get(sezione).size=" + widgetFiltrati.get(sezione).size());
      }
    }
    log.debug("widgetFiltrati=" + widgetFiltrati.size());
    return widgetFiltrati;
  }

  @SuppressWarnings("unchecked")
  private static HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>
      filtraWidgetSenzaFigli(
          HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurabili,
          Utente utente) {

    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetFiltrati =
        (HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>) widgetConfigurabili.clone();
    if (utente.getListaFigli() == null || utente.getListaFigli().isEmpty()) {
      Set<DatiSezione> sezioni = widgetFiltrati.keySet();
      log.debug("sezioni.size=" + sezioni.size());
      sezioni.removeIf(b -> b.getDenominazioneSez().equalsIgnoreCase(IO_GENITORE));
      log.debug("sezioni.size=" + sezioni.size());
    }
    return widgetFiltrati;
  }

  private static List<DatiNotificaComparto> filtraNotificheSenzaVeicoli(
      List<DatiNotificaComparto> listaNotifiche, Utente utente) {

    List<DatiNotificaComparto> listaNotificheFiltrate =
        new ArrayList<DatiNotificaComparto>(listaNotifiche);
    if (utente.getListaVeicoliAttivi() == null || utente.getListaVeicoliAttivi().isEmpty()) {
      listaNotificheFiltrate.removeIf(
          b -> b.getComparto().getDenominazioneComp().equalsIgnoreCase(TRASPORTO_PRIVATO));
    }
    return listaNotificheFiltrate;
  }

  private static List<DatiNotificaComparto> filtraNotificheSenzaFigli(
      List<DatiNotificaComparto> listaNotifiche, Utente utente) {

    List<DatiNotificaComparto> listaNotificheFiltrate =
        new ArrayList<DatiNotificaComparto>(listaNotifiche);
    if (utente.getListaFigli() == null || utente.getListaFigli().isEmpty()) {
      listaNotificheFiltrate.removeIf(
          b -> b.getSezione().getDenominazioneSez().equalsIgnoreCase(IO_GENITORE));
    }
    return listaNotificheFiltrate;
  }

  private static List<VScScadenzeUt> filtraScadenzeSenzaVeicoli(
      List<VScScadenzeUt> listaScadenze, Utente utente) {
    List<VScScadenzeUt> listaScadenzeFiltrate = new ArrayList<>(listaScadenze);
    if (utente.getListaVeicoliAttivi() != null && utente.getListaVeicoliAttivi().isEmpty()) {
      listaScadenzeFiltrate.removeIf(
          b -> b.getDenominazioneComp().equalsIgnoreCase(TRASPORTO_PRIVATO));
    }
    return listaScadenzeFiltrate;
  }

  private static List<VScScadenzeUt> filtraScadenzeSenzaFigli(
      List<VScScadenzeUt> listaScadenze, Utente utente) {
    List<VScScadenzeUt> listaScadenzeFiltrate = new ArrayList<>(listaScadenze);
    if (utente.getListaFigli() == null || utente.getListaFigli().isEmpty()) {
      listaScadenzeFiltrate.removeIf(b -> b.getDenominazioneSez().equalsIgnoreCase(IO_GENITORE));
    }
    return listaScadenzeFiltrate;
  }
}
