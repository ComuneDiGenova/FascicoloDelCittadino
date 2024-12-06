package it.liguriadigitale.ponmetro.portale.business.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoNoYaml;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.ListaGlobo;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnlineSettings;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Tag;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.ricerca.RicercaGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.CONTEXT_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.application.FUNZIONI_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GloboFrontendImpl implements GloboFrontendInterface {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public List<VCfgTCatGlobo> getFunzioni() throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ListaGlobo result = instance.getApiServiziGloboBackend().getListaFunzioni();
      if (result.getEsito().isEsito()) {
        List<VCfgTCatGlobo> listaFunzioni = result.getListaFunzioni();
        log.debug("lista VCfgTCatGlobo:" + listaFunzioni.size());
        return listaFunzioni;
      } else {
        String eccezione = result.getEsito().getEccezione();
        throw new BusinessException(eccezione);
      }
    } catch (BusinessException e) {
      String errore = "Errore durante la chiamata al Backend";
      log.error(errore + ": ", e);
      throw new BusinessException(errore);
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<PraticaGlobo> getPraticheGlobo(Utente utente) throws BusinessException {

    List<PraticaGlobo> lista =
        ServiceLocatorLivelloUnoNoYaml.getInstance()
            .getApiGloboSenzaYaml()
            .istanzeGlobo(utente.getCodiceFiscaleOperatore());
    log.debug("lista= " + lista.size());
    return lista;
  }

  private List<VCfgTCatGlobo> filtroPerComparto(
      List<VCfgTCatGlobo> listaFunzioni, Long idComparto) {

    List<VCfgTCatGlobo> listaFiltrata =
        listaFunzioni.stream()
            .filter(c -> idComparto.equals(c.getIdComp()))
            .collect(Collectors.toList());
    return listaFiltrata;
  }

  @Override
  public List<VCfgTCatGlobo> getFunzioniGloboPerComparto(Long idComparto) throws BusinessException {
    List<VCfgTCatGlobo> lista = getFunzioni();
    List<VCfgTCatGlobo> listaFiltrata = filtroPerComparto(lista, idComparto);
    Set<VCfgTCatGlobo> uniqueSet = new HashSet<>(listaFiltrata);
    List<VCfgTCatGlobo> listaFinale = new ArrayList<>();
    listaFinale.addAll(uniqueSet);
    return listaFinale;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb(
      List<VCfgTCatGlobo> listaFiltrataProcedimenti,
      List<FunzioniDisponibili> listaFunzioniDisponibili,
      Utente utente) {

    String contextPath = CONTEXT_PATH.getPath(utente);
    VCfgTCatGlobo funzioneGlobo = listaFiltrataProcedimenti.get(0);
    FunzioniDisponibili compartoFdC =
        ricavaCompartoAttuale(funzioneGlobo, listaFunzioniDisponibili);
    log.debug("Funzione Globo per BreadcrumbFdC: " + funzioneGlobo);
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();
    listaBreadcrumb.add(new BreadcrumbFdC(contextPath + "/homepage/home", "Home"));
    listaBreadcrumb.add(generaBreadCrumbSezione(compartoFdC, utente));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            contextPath + "/nondelegabili/globo/" + funzioneGlobo.getIdFunz(),
            funzioneGlobo.getDescrizioneFunz()));
    if (listaFiltrataProcedimenti.size() < 2) {
      listaBreadcrumb.add(
          new BreadcrumbFdC(
              contextPath
                  + "/nondelegabili/globo/"
                  + funzioneGlobo.getIdFunz()
                  + "/"
                  + funzioneGlobo.getIdProcedimento(),
              funzioneGlobo.getDenominazioneProcedimento()));
    }
    return listaBreadcrumb;
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

  private FunzioniDisponibili ricavaCompartoAttuale(
      VCfgTCatGlobo funzioneGlobo, List<FunzioniDisponibili> listaFunzioniDisponibili) {

    List<FunzioniDisponibili> listaFiltrata =
        listaFunzioniDisponibili.stream()
            .filter(c -> funzioneGlobo.getIdComp().equals(c.getIdComp()))
            .collect(Collectors.toList());

    if (!listaFiltrata.isEmpty()) {
      return listaFiltrata.get(0);
    } else {
      return new FunzioniDisponibili();
    }
  }

  @Override
  public List<Node> getServiziOnlineGlobo() throws BusinessException {
    try {
      List<Node> lista =
          ServiceLocatorLivelloUnoNoYaml.getInstance()
              .getApiGloboSenzaYaml()
              .serviziOnline()
              .getNodes();
      List<Node> listaSenzaTagFascicoloNull = togliTagFascicoloNull(lista);
      List<Node> listaSenzaCodiceMaggioliNull = togliCodiceMaggioliNull(listaSenzaTagFascicoloNull);
      return listaSenzaCodiceMaggioliNull;
    } catch (BusinessException e) {
      log.error("Errore", e);
      throw new BusinessException("Errore GloboServiziOnline");
    }
  }

  private List<Node> togliCodiceMaggioliNull(List<Node> lista) {
    List<Node> listaFiltrata = new ArrayList<>();
    for (Node nodo : lista) {
      if (nodo.getCodice_maggioli() != null) {
        listaFiltrata.add(nodo);
      }
    }
    return listaFiltrata;
  }

  private List<Node> togliTagFascicoloNull(List<Node> lista) {
    List<Node> listaFiltrata = new ArrayList<>();
    for (Node nodo : lista) {
      if (nodo.getTag_fascicolo() != null && !nodo.getTag_argomenti().isEmpty()) {
        listaFiltrata.add(nodo);
      }
    }
    return listaFiltrata;
  }

  @Override
  public GloboServiziOnlineSettings getServiziOnlineGloboSettings() throws BusinessException {
    try {
      return ServiceLocatorLivelloUnoNoYaml.getInstance()
          .getApiGloboSenzaYaml()
          .serviziOnlineSettimgs();
    } catch (BusinessException e) {
      log.error("Errore", e);
      throw new BusinessException("Errore GloboServiziOnlineSettings");
    }
  }

  @Override
  public List<Node> filtraLista(List<Node> listaNodi, RicercaGlobo ricerca, Utente utente) {
    log.debug("RicercaGlobo:" + ricerca);
    Tag valoreCombo = ricerca.getCombo();
    if (ricerca.getComboChild() != null
        && StringUtils.isNotBlank(ricerca.getComboChild().getId())) {
      valoreCombo = ricerca.getComboChild();
    }

    List<Node> listaFiltrata = new ArrayList<>();
    if (valoreCombo != null && StringUtils.isNotBlank(valoreCombo.getId())) {
      for (Node nodo : listaNodi) {
        for (Tag argomento : nodo.getTag_argomenti()) {
          if (argomento.getId().equals(valoreCombo.getId())) {
            listaFiltrata.add(nodo);
            log.debug(
                "argomento.getId()" + argomento.getId() + "  trovato: " + valoreCombo.getId());
          }
        }
      }
    }
    log.debug("listaFiltrata.size=" + listaFiltrata.size());
    List<Node> listaProcedimenti =
        aggiungiProcedimentiPerTestoLibero(
            listaFiltrata, listaNodi, ricerca.getTestoLibero(), utente);
    return filtraSoloServiziAiCittadini(listaProcedimenti);
  }

  private List<Node> aggiungiProcedimentiPerTestoLibero(
      List<Node> listaFiltrata, List<Node> listaNodi, String testoLibero, Utente utente) {
    log.debug("testoLibero=" + testoLibero);
    List<Node> listaProcedimenti = new ArrayList<>();
    if (StringUtils.isNotEmpty(testoLibero)) {
      for (Node nodo : listaNodi) {
        boolean found = false;
        String testoLiberoSubstring =
            StringUtils.substring(testoLibero, 0, testoLibero.length() - 1);
        if (nodo.getTitolo().toLowerCase().contains(testoLiberoSubstring.toLowerCase())) {
          found = controlloSuAbilitazioneFunzione(nodo, utente);
        }
        for (Tag argomento : nodo.getTag_argomenti()) {
          if (argomento.getName().toLowerCase().contains(testoLiberoSubstring.toLowerCase())) {
            found = controlloSuAbilitazioneFunzione(nodo, utente);
          }
        }
        if (found) {
          listaProcedimenti.add(nodo);
        }
      }

      listaProcedimenti.addAll(listaFiltrata);
      log.debug("listaProcedimenti.size=" + listaProcedimenti.size());
      return listaProcedimenti;
    } else {
      return listaFiltrata;
    }
  }

  private boolean controlloSuAbilitazioneFunzione(Node nodo, Utente utente) {
    log.debug("NODO = " + nodo);
    List<FunzioniDisponibili> listaFunzioni = new ArrayList<>();
    List<VCfgTCatGlobo> listaFunzioniGlobo = new ArrayList<>();
    try {
      listaFunzioniGlobo = ServiceLocator.getInstance().getGlobo().getFunzioni();
      listaFunzioni =
          ServiceLocator.getInstance().getServiziConfigurazione().getFunzioniAbilitate();
    } catch (BusinessException e) {
      log.error(
          "ERRORE IN RICERCA FUNZIONI ABILITATE IN controlloSuFunzioneDisabilitata < GloboFrontendImpl = "
              + e);
    }
    if (checkValiditaListeENodo(nodo, listaFunzioni, listaFunzioniGlobo)) {
      for (VCfgTCatGlobo funzioneGlobo : listaFunzioniGlobo) {
        if (funzioneGlobo != null && funzioneGlobo.getIdMaggioli() != null) {
          if (nodo.getCodice_maggioli().equals(funzioneGlobo.getIdMaggioli())) {
            log.debug("FUNZIONE GLOBO TROVATA = " + funzioneGlobo);
            return funzioneGlobo.getFlagAbilitazione()
                && funzioneGlobo.getFlagAbilitazioneGlobo()
                && checkFlagResidenza(
                    funzioneGlobo.getFlagNonResidente(), funzioneGlobo.getFlagResidente(), utente)
                && checkFlagResidenza(
                    funzioneGlobo.getFlagNonResidenteGlobo(),
                    funzioneGlobo.getFlagResidenteGlobo(),
                    utente);
          }
        }
      }

      log.debug(
          "NODO ["
              + nodo.getTitolo()
              + " / "
              + nodo.getCodice_maggioli()
              + " / "
              + nodo.getCodice_fdc()
              + "] "
              + "non ha una corrispondenza di IdFunz in lista globo");

      for (FunzioniDisponibili funzione : listaFunzioni) {
        if (funzione != null && funzione.getDescrizioneFunz() != null) {
          if (funzione.getDescrizioneFunz().equalsIgnoreCase(nodo.getTitolo())) {

            log.debug("FUNZIONE TROVATA VIA DESCRIZIONE= " + funzione);
            return funzione.getFlagAbilitazioneFunz()
                && checkFlagResidenza(
                    funzione.getFlagNonResidente(), funzione.getFlagResidente(), utente);

          } else if (funzione.getIdFunz().equals(nodo.getCodice_fdc())) {

            log.debug("FUNZIONE TROVATA VIA IDFUNZ= " + funzione);

            checkCorrispondenzaTitoli(nodo, funzione, listaFunzioni);

            return funzione.getFlagAbilitazioneFunz()
                && checkFlagResidenza(
                    funzione.getFlagNonResidente(), funzione.getFlagResidente(), utente);

          } else {
            log.debug(
                "Funzione NON trovata in lista Funzioni Disponibili per codice_fdc / id_funz [ "
                    + nodo.getCodice_fdc()
                    + " ]");
          }
        }
      }
    }
    return false;
  }

  private void checkCorrispondenzaTitoli(
      Node nodo, FunzioniDisponibili funzione, List<FunzioniDisponibili> listaFunzioni) {

    String trovato = "";
    if (funzione.getDescrizioneFunz().equalsIgnoreCase(nodo.getTitolo())) {
      for (FunzioniDisponibili elem : listaFunzioni) {
        if (elem != null
            && elem.getDescrizioneFunz() != null
            && elem.getDescrizioneFunz().equalsIgnoreCase(nodo.getTitolo())) {
          trovato =
              "\nTrovata corrispondenza in id_funz = [ "
                  + funzione.getIdFunz()
                  + " ]\nTitolo funzione = "
                  + funzione.getDescrizioneFunz();
        }
      }
      if (trovato.isEmpty()) {
        trovato =
            "\nTitolo non trovato nelle descrzioni_funz delle Funzioni Disponibili, possibile funzione disattivata";
      }
    }

    log.debug(
        "\ncodice_fdc / id_funz = [ "
            + funzione.getIdFunz()
            + " ] "
            + "\nTitolo nodo = "
            + nodo.getTitolo()
            + "\nTitolo funzione = "
            + funzione.getDescrizioneFunz()
            + trovato);
  }

  private boolean checkValiditaListeENodo(
      Node nodo, List<FunzioniDisponibili> listaFunzioni, List<VCfgTCatGlobo> listaFunzioniGlobo) {
    return listaFunzioni != null
        && !listaFunzioni.isEmpty()
        && listaFunzioniGlobo != null
        && !listaFunzioniGlobo.isEmpty()
        && nodo != null
        && nodo.getTitolo() != null
        && nodo.getCodice_maggioli() != null;
  }

  private boolean checkFlagResidenza(
      Boolean flagNonResidente, Boolean flagResidente, Utente utente) {
    return (flagNonResidente && !utente.isResidente()) || (flagResidente && utente.isResidente());
  }

  private List<Node> filtraSoloServiziAiCittadini(List<Node> listaFiltrata) {
    List<Node> listaSoloServiziAiCittadini = new ArrayList<>();
    for (Node nodo : listaFiltrata) {
      for (Tag tag : nodo.getTag_target()) {
        if ("Cittadini".equalsIgnoreCase(tag.getName())) {
          listaSoloServiziAiCittadini.add(nodo);
        }
      }
    }
    log.debug("listaSoloServiziAiCittadini:" + listaSoloServiziAiCittadini.size());
    return listaSoloServiziAiCittadini;
  }
}
