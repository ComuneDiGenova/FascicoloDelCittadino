package it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WidgetUtil {

  private static Log log = LogFactory.getLog(WidgetUtil.class);

  private WidgetUtil() {
    super();
  }

  public static List<DatiSezione> getListaSezioniOrdinate(
      Map<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurati) {
    Set<DatiSezione> sezioni = widgetConfigurati.keySet();
    List<DatiSezione> listaSezioni = new ArrayList<>();
    listaSezioni.addAll(sezioni);
    Collections.sort(
        listaSezioni,
        (DatiSezione p1, DatiSezione p2) ->
            convertiOrdinamentoInNumero(p1).compareTo(convertiOrdinamentoInNumero(p2)));
    return listaSezioni;
  }

  private static Integer convertiOrdinamentoInNumero(DatiSezione datiSezione) {
    String valore = datiSezione.getOrdinamento();
    try {
      return Integer.parseInt(valore);
    } catch (NumberFormatException e) {
      log.warn("l'ordinamento dei dati sezione " + datiSezione.getDenominazioneSez() + " e' nullo");
      return 0;
    }
  }

  public static List<DatiVisualizzazioneSezioneWidget> creaListaWidgetPerSezione(
      DatiSezione sezione, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    List<DatiVisualizzazioneSezioneWidget> listaFinale = new ArrayList<>();
    for (DatiVisualizzazioneSezioneWidget sezioneWidget : listaWidget) {
      if (sezioneWidget.getSezione().getIdSezione().equalsIgnoreCase(sezione.getIdSezione())) {
        if (sezioneWidget.getWidget().getDenominazioneWidg() != null) {
          listaFinale.add(sezioneWidget);
        }
      }
    }
    return listaFinale;
  }

  public static Set<DatiSezione> creaElencoSezioniDaListaWidget(
      List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    Set<DatiSezione> elencoSezioni = new HashSet<>();
    for (DatiVisualizzazioneSezioneWidget sezioneWidget : listaWidget) {
      if (nonTrovataSezione(sezioneWidget.getSezione(), elencoSezioni)) {
        elencoSezioni.add(sezioneWidget.getSezione());
      }
    }
    return elencoSezioni;
  }

  private static boolean nonTrovataSezione(
      DatiSezione sezioneWidget, Set<DatiSezione> elencoSezioni) {
    boolean notFound = true;
    for (DatiSezione sezioneInSet : elencoSezioni) {
      if (sezioneInSet.getIdSezione().equalsIgnoreCase(sezioneWidget.getIdSezione())) {
        notFound = false;
      }
    }
    return notFound;
  }

  public static HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>>
      creaMappaDaElencoWidget(List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    HashMap<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurati =
        new HashMap<>();
    Set<DatiSezione> elencoSezioni;
    if (listaWidget != null && !listaWidget.isEmpty()) {
      elencoSezioni = WidgetUtil.creaElencoSezioniDaListaWidget(listaWidget);
      for (DatiSezione sezione : elencoSezioni) {
        List<DatiVisualizzazioneSezioneWidget> listaWidgetPerSezione =
            WidgetUtil.creaListaWidgetPerSezione(sezione, listaWidget);
        if (!listaWidgetPerSezione.isEmpty()) widgetConfigurati.put(sezione, listaWidgetPerSezione);
      }
    }
    return widgetConfigurati;
  }
}
