package it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.CONTEXT_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.application.FUNZIONI_PATH;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;

public class MapBreadcrumbsFdc implements Serializable {

  private static final long serialVersionUID = -5114510278269848937L;

  private static final Log log = LogFactory.getLog(MapBreadcrumbsFdc.class);

  protected static Map<String, BreadcrumbFdC> mappaBreadcrumbs;

  static {
    mappaBreadcrumbs = new HashMap<>();
  }

  public static void addBreadcrumb(String key, BreadcrumbFdC elemento) {
    mappaBreadcrumbs.put(key, elemento);
  }

  public static List<BreadcrumbFdC> getListaBreadcrumbDaVisualizzare(
      Class<? extends Component> clazz, Utente utente) {
    String nomePaginaAttuale = clazz.getSimpleName();
    return getListaBreadcrumbDaVisualizzare(nomePaginaAttuale, utente);
  }

  public static List<BreadcrumbFdC> getListaBreadcrumbDaVisualizzare(
      String nomePaginaAttuale, Utente utente) {

    List<BreadcrumbFdC> lista = new ArrayList<>();
    log.debug("getListaBreadcrumbDaVisualizzare:");
    log.debug("keys:" + mappaBreadcrumbs.keySet());
    BreadcrumbFdC ultimaBriciola = mappaBreadcrumbs.get(nomePaginaAttuale);
    log.debug("nomePaginaAttuale: " + nomePaginaAttuale + "--> " + ultimaBriciola);
    if (ultimaBriciola != null) {
      lista.add(ultimaBriciola);
      int evitaLoopInfinito = 0;
      while (StringUtils.isNotBlank(ultimaBriciola.getNomeClassePaginaPadre())) {
        ultimaBriciola = mappaBreadcrumbs.get(ultimaBriciola.getNomeClassePaginaPadre());
        lista.add(ultimaBriciola);
        log.debug(
            "nomePaginaAttuale: "
                + ultimaBriciola.getNomeClassePaginaPadre()
                + "--> "
                + ultimaBriciola);
        evitaLoopInfinito++;
        if (evitaLoopInfinito > 10) {
          log.error(
              "ERRORE -- nomePaginaAttuale = NomeClassePaginaPadre -- verificare metadati su db");
          break;
        }
      }
      if (ultimaBriciola.getFunzione().getIdSez() > 0) {
        lista.add(generaBreadCrumbSezione(ultimaBriciola.getFunzione(), utente));
      }
      lista.add(creaBreadCrumbHomePage(ultimaBriciola.getContextPath(), utente.isUtenteDelegato()));
    }
    Collections.reverse(lista);
    log.debug("lista: " + lista.size());
    return lista;
  }

  public static List<BreadcrumbFdC> getListaBreadcrumbPerPaginaRiepilogo(
      String idSezione, Utente utente) {
    DatiSezione datiSezione = ricavaDatiSezioneDaIdSezione(idSezione, utente);
    return getListaBreadcrumbPerPaginaRiepilogo(datiSezione, utente);
  }

  public static List<BreadcrumbFdC> getListaBreadcrumbPerPaginaRiepilogo(
      DatiSezione datiSezione, Utente utente) {
    FunzioniDisponibili compartoFdC = new FunzioniDisponibili();
    compartoFdC.setDenominazioneSez(datiSezione.getDenominazioneSez());
    compartoFdC.setDescrizioneSez(datiSezione.getDescrizioneSez());
    return getListaBreadcrumbPerPaginaRiepilogo(compartoFdC, utente);
  }

  public static BreadcrumbFdC getBreadcrumbCorrente(String nomePaginaAttuale) {
    return mappaBreadcrumbs.get(nomePaginaAttuale);
  }

  private static List<BreadcrumbFdC> getListaBreadcrumbPerPaginaRiepilogo(
      FunzioniDisponibili compartoFdC, Utente utente) {
    List<BreadcrumbFdC> lista = new ArrayList<>();
    String contextPath = CONTEXT_PATH.getPath(utente);
    lista.add(generaBreadCrumbSezione(compartoFdC, utente));
    lista.add(creaBreadCrumbHomePage(contextPath, utente.isUtenteDelegato()));
    Collections.reverse(lista);
    log.debug("lista: " + lista.size());
    return lista;
  }

  private static BreadcrumbFdC generaBreadCrumbSezione(
      FunzioniDisponibili compartoFdC, Utente utente) {
    String contextPath = CONTEXT_PATH.getPath(utente);
    if ("ioCittadino".equalsIgnoreCase(compartoFdC.getDenominazioneSez())) {
      return new BreadcrumbFdC(
          contextPath + "/" + FUNZIONI_PATH.NON_DELEGABILI.getPath() + "/imieidati",
          "io " + getNominativoCapoFamiglia(utente));
    } else {
      return new BreadcrumbFdC(
          contextPath
              + "/"
              + FUNZIONI_PATH.NON_DELEGABILI.getPath()
              + "/"
              + compartoFdC.getDenominazioneSez(),
          compartoFdC.getDescrizioneSez());
    }
  }

  private static String getNominativoCapoFamiglia(Utente capoFamiglia) {
    StringBuilder builder = new StringBuilder();
    return builder
        .append(capoFamiglia.getCognome())
        .append(" ")
        .append(capoFamiglia.getNome())
        .toString();
  }

  private static BreadcrumbFdC creaBreadCrumbHomePage(String contextPath, boolean isDelegato) {
    String pathHome = contextPath + "/homepage/home";
    if (isDelegato) {
      pathHome = "";
    }
    BreadcrumbFdC home = new BreadcrumbFdC(pathHome, "Home");
    home.setNomeClassePaginaPadre("");
    return home;
  }

  public static DatiSezione ricavaDatiSezioneDaIdSezione(String idSezione, Utente utente) {
    log.debug("idSezione:" + idSezione);
    if (!StringUtils.isEmpty(idSezione)) {
      for (DatiSezione sezione : utente.getWidgetConfigurati().keySet()) {
        if (idSezione.equalsIgnoreCase(sezione.getIdSezione())) {
          log.debug("return idSezione:" + idSezione);
          return sezione;
        }
      }
      log.debug("idSezione non trovata in keySet");
      return new DatiSezione();
    } else {
      log.debug("idSezione vuoto:" + idSezione);
      return new DatiSezione();
    }
  }
}
