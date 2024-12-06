package it.liguriadigitale.ponmetro.portale.business.configurazione.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaPagine;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneHomePageInterface;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurazioneHomePageImpl implements ConfigurazioneHomePageInterface {

  private static final String GLOBO_PAGE = "GloboPage";
  private static final String ENABLE = "ENABLE";
  private static final String CHIAVE_PAGOPA = "HOME_PAGOPA";
  private static final String CHIAVE_SEGNALAZIONI_CZRM = "HOME_SEGNALAZIONI_CZRM";
  private static final String CHIAVE_MESSAGGI = "HOME_MESSAGGI";
  private static final String CHIAVE_SCADENZE = "HOME_SCADENZE";
  private static final String CHIAVE_RICERCA = "HOME_RICERCA";
  private static final String URL_CGE_COVID = "URL_CGE_COVID";
  private static final String URL_RL_FASCICOLO_SANITARIO = "URL_RL_FASCICOLO_SANITARIO";
  private static final String CHIAVE_PRIVACY = "HOME_PRIVACY";

  private static Log log = LogFactory.getLog(ConfigurazioneHomePageImpl.class);

  private boolean getChiaveBooleana(String chiave) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      log.debug("valore=" + valore);
      if (valore != null) {
        log.debug("1");
        return (valore.getValore().equalsIgnoreCase(ENABLE));
      } else {
        log.debug("2");
        return false;
      }
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      return false;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private String getStringa(String chiave) {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (valore != null) {
        return valore.getValore();
      } else {
        return null;
      }
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      return null;
    } catch (WebApplicationException e) {
      log.error("Errore API: ", e);
      return null;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public boolean isVisibilePagoPa() {
    boolean chiaveBooleana = getChiaveBooleana(CHIAVE_PAGOPA);
    log.debug("chiaveBooleana=" + chiaveBooleana);
    return chiaveBooleana;
  }

  @Override
  public boolean isVisibileSegnalazioniCzrm() {
    boolean chiaveBooleana = getChiaveBooleana(CHIAVE_SEGNALAZIONI_CZRM);
    log.debug("chiaveBooleana=" + chiaveBooleana);
    return chiaveBooleana;
  }

  @Override
  public boolean isVisibileMessaggi() {
    return getChiaveBooleana(CHIAVE_MESSAGGI);
  }

  @Override
  public boolean isVisibileScadenze() {
    return getChiaveBooleana(CHIAVE_SCADENZE);
  }

  @Override
  public String getUrlCovid() {
    return getStringa(URL_CGE_COVID);
  }

  @Override
  public String getUrlFascicoloSanitarioElettronico() {
    return getStringa(URL_RL_FASCICOLO_SANITARIO);
  }

  @Override
  public boolean isVisibileRicerca() {
    return getChiaveBooleana(CHIAVE_RICERCA);
  }

  @Override
  public boolean isVisibilePrivacy() {
    return getChiaveBooleana(CHIAVE_PRIVACY);
  }

  @Override
  public List<FunzioniDisponibili> getFunzioniBySezione(
      List<FunzioniDisponibili> listaFunzioni, Long idSezione) {

    log.debug("listaFunzioni+" + listaFunzioni.size());

    List<FunzioniDisponibili> listaFiltrata =
        listaFunzioni.stream()
            .filter(c -> idSezione.equals(c.getIdSez()))
            .collect(Collectors.toList());
    log.debug("Lista bySez:" + listaFiltrata.size());

    for (FunzioniDisponibili funzione : listaFiltrata) {
      log.debug("funzione: " + funzione);
    }

    // TODO per Parpinello QUI inserire ordinamento
    // utilizzare stream che fa order by su campo ORDINAMENTO_C

    return listaFiltrata;
  }

  @SuppressWarnings("unused")
  private List<FunzioniDisponibili> getFunzioniGlobo(List<FunzioniDisponibili> listaFunzioni) {

    log.debug("listaFunzioni=" + listaFunzioni.size());

    List<FunzioniDisponibili> listaFiltrata =
        listaFunzioni.stream()
            .filter(c -> GLOBO_PAGE.equalsIgnoreCase(c.getClassePaginaStd()))
            .collect(Collectors.toList());
    log.debug("Lista byGLOBO_PAGE:" + listaFiltrata.size());

    for (FunzioniDisponibili funzione : listaFiltrata) {
      log.debug("funzione: " + funzione);
    }
    return listaFiltrata;
  }

  // @Override
  // public List<FunzioneChiaveValore>
  // getListaLinkInEvidenzaOInRealizzazione(String chiave) {
  // List<FunzioneChiaveValore> lista = new ArrayList<>();
  //
  // try {
  // lista = ServiceLocatorLivelloUno.getInstance().getApiHomePage()
  // .getListaLinkInEvidenzaOInRealizzazione(chiave);
  //
  // } catch (BusinessException e) {
  // log.debug("Errore getListaLinkInEvidenzaOInRealizzazione = " +
  // e.getMessage());
  // }
  //
  // log.debug("CP lista getListaLinkInEvidenzaOInRealizzazione = " + lista);
  //
  // return lista;
  // }

  @Override
  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String chiave) {
    List<FunzioneChiaveValore> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      if (PageUtil.isStringValid(chiave)) {
        lista = instance.getApiHomePage().getListaFunzioniInEvidenza(chiave);
      }
    } catch (BusinessException e) {
      log.debug("Errore getListaFunzioniInEvidenza = " + e.getMessage());
    } finally {
      instance.closeConnection();
    }

    return lista;
  }

  @Override
  public List<ChiaveValore> getListaFunzioniInRealizzazione(String chiave) {
    List<ChiaveValore> lista = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      if (PageUtil.isStringValid(chiave)) {

        lista = instance.getApiHomePage().getListaFunzioniInRealizzazione(chiave);
      }
    } catch (BusinessException e) {
      log.debug("Errore getListaFunzioniInRealizzazione = " + e.getMessage());
    } finally {
      instance.closeConnection();
    }

    return lista;
  }

  @Override
  public String getStringaRisorsaDalDb(String chiave) {

    String stringaTrovata = getStringa(chiave);
    log.debug("Stringa trovata sul DB: " + stringaTrovata);
    return stringaTrovata;
  }

  @Override
  public List<SottopagineDisponibili> getElencoSottoPagine() {
    ListaPagine lista;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      lista = instance.getApiHomePage().getListaPagine();
      if (lista.getEsito().isEsito()) {
        return lista.getListaPagine();
      }
    } catch (BusinessException e) {
      log.debug("Errore getListaFunzioniInRealizzazione = " + e.getMessage());
    } finally {
      instance.closeConnection();
    }
    return new ArrayList<>();
  }
}
