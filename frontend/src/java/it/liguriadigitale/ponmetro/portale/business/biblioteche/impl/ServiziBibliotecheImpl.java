package it.liguriadigitale.ponmetro.portale.business.biblioteche.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.biblioteche.service.ServiziBibliotecheService;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mail.SendMailUtil;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoBiblioteche;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggio;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggioBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.DocIdentita;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.IndirizzoUtenteFull;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject1;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteAbil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.GenderEnum;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.LinComEnum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziBibliotecheImpl implements ServiziBibliotecheService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_BIBLIOTECHE = "Errore di connessione alle API Biblioteche";

  private static final Locale localeItaly = Locale.ITALY;

  @Override
  public List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> getUtenteByDocIdentita(
      String codiceFiscale) throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getUtenteByDocIdentita " + codiceFiscale);

      String tipoDocIdentita = "CF";
      // String numeroDocIdentita = utente.getCodiceFiscaleOperatore();

      List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaUtenteSebina =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiUtente()
              .findUtenteByDocIdentita(tipoDocIdentita, codiceFiscale);

      return listaUtenteSebina;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getUtenteByDocIdentita: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getUtenteByDocIdentita: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getUtenteByDocIdentita: errore durante la chiamata delle API biblioteche ",
          e);
      // throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));

      throw new BusinessException("RuntimeException Biblioteche non raggiungibile");
    }
  }

  @Override
  public Long getIdUtenteSebina(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getIdUtenteSebina");

      Long idUtente = null;

      List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaUtente =
          getUtenteByDocIdentita(codiceFiscale);
      if (listaUtente.size() == 1) {
        idUtente = listaUtente.get(0).getId();
      }

      return idUtente;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getIdUtenteSebina: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getIdUtenteSebina: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getIdUtenteSebina: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }

  @Override
  public List<Movimento> getMovimentiCorrentiUtenteById(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getMovimentiCorrentiUtenteById");

      Long utenteId = getIdUtenteSebina(codiceFiscale);
      List<Movimento> listaMovimenti =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiServiziUtente()
              .findMovimentiCorrentiUtenteById(utenteId, null);

      List<Movimento> sortedListaMovimenti =
          listaMovimenti.stream()
              .sorted(Comparator.comparing(Movimento::getDtInizio).reversed())
              .collect(Collectors.toList());

      return sortedListaMovimenti;

    } catch (BusinessException e) {
      log.error(
          "ServiziBibliotecheImpl -- getMovimentiCorrentiUtenteById: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getMovimentiCorrentiUtenteById: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getMovimentiCorrentiUtenteById: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTDocIdentita(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaTDocIdentita");

      List<TabellaRecord> listaTabellaRecord = new ArrayList<TabellaRecord>();
      listaTabellaRecord =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleUtente()
              .listaTabellaTDocIdentita(localeItaly.toString());

      return listaTabellaRecord;
    } catch (BusinessException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTDocIdentita: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTDocIdentita: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaTDocIdentita: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaPaeseRecord> getListaTabellaPaese()
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaPaese");

      List<TabellaPaeseRecord> listaTabellaPaese = new ArrayList<TabellaPaeseRecord>();

      listaTabellaPaese =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleBase()
              .listaTabellaPaese(null);

      return listaTabellaPaese;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaPaese: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaPaese: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaPaese: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaPaeseRecord> getPaesi(String input)
      throws BusinessException, ApiException, IOException {
    log.debug("[ServiziBibliotecheImpl] getPaesi = " + input);

    String inputToUpper = input.toUpperCase();

    List<TabellaPaeseRecord> listaTuttiPaesi = getListaTabellaPaese();

    List<TabellaPaeseRecord> listaPaesiFiltrati = new ArrayList<TabellaPaeseRecord>();

    listaPaesiFiltrati =
        listaTuttiPaesi.stream()
            .filter(
                elem ->
                    LabelFdCUtil.checkIfNotNull(elem)
                        && LabelFdCUtil.checkIfNotNull(elem.getDscr())
                        && elem.getDscr().contains(inputToUpper))
            .collect(Collectors.toList());

    return listaPaesiFiltrati;
  }

  @Override
  public List<TabellaPaeseRecord> getPaeseNazionalita(String nazionalitaDemografico)
      throws BusinessException, ApiException, IOException {
    log.debug("[ServiziBibliotecheImpl] getPaeseNazionalita = " + nazionalitaDemografico);

    List<TabellaPaeseRecord> listaTuttiPaesi = getListaTabellaPaese();

    List<TabellaPaeseRecord> listaPaesiFiltrati = new ArrayList<TabellaPaeseRecord>();

    listaPaesiFiltrati =
        listaTuttiPaesi.stream()
            .filter(
                elem ->
                    LabelFdCUtil.checkIfNotNull(elem)
                        && PageUtil.isStringValid(elem.getCdIstat())
                        && elem.getCdIstat().equalsIgnoreCase(nazionalitaDemografico))
            .collect(Collectors.toList());

    return listaPaesiFiltrati;
  }

  @Override
  public List<TabellaRecord> getListaTabellaBiblio(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaBiblio");

      List<TabellaRecord> listaTabellaBiblio = new ArrayList<TabellaRecord>();
      listaTabellaBiblio =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleBase()
              .listaTabellaBiblio(localeItaly.toString());

      return listaTabellaBiblio;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaBiblio: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaBiblio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaBiblio: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaLingua(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaLingua");

      List<TabellaRecord> listaTabellaLingua = new ArrayList<TabellaRecord>();
      listaTabellaLingua =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleBase()
              .listaTabellaLingua(localeItaly.toString());

      return listaTabellaLingua;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaLingua: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaLingua: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaLingua: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaProv(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaProv");

      List<TabellaRecord> listaTabellaProv =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleUtente()
              .listaTabellaProv();

      return listaTabellaProv;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaProv: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaProv: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaProv: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTpv(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaTpv");

      List<TabellaRecord> listaTabellaTpv = new ArrayList<TabellaRecord>();
      listaTabellaTpv =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleUtente()
              .listaTabellaTpv(localeItaly.toString());
      return listaTabellaTpv;
    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaTpv: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTpv: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaTpv: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTts(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaTts");

      List<TabellaRecord> listaTabellaTts = new ArrayList<TabellaRecord>();
      listaTabellaTts =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleUtente()
              .listaTabellaTts(localeItaly.toString());
      return listaTabellaTts;
    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaTts: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTts: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaTts: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTut()
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaTut");

      List<TabellaRecord> listaTabellaTut = new ArrayList<TabellaRecord>();
      listaTabellaTut =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleUtente()
              .listaTabellaTut(localeItaly.toString());
      return listaTabellaTut;
    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaTut: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTut: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaTut: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public String getCodiceTipoUtente() throws BusinessException, ApiException, IOException {
    log.debug("[ServiziBibliotecheImpl] getDescrizioneTipoUtente ");

    String lettore = "Lettore";
    TabellaRecord recordTipo =
        getListaTabellaTut().stream()
            .filter(elem -> elem.getDscr().equalsIgnoreCase(lettore))
            .findAny()
            .orElse(null);
    String codiceTipoUtente = "";
    if (recordTipo != null) {
      codiceTipoUtente = recordTipo.getCd();
    }
    return codiceTipoUtente;
  }

  @Override
  public void cancellaMovimentoAnnullabile(String codiceFiscale, Movimento movimento)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug(
          "[ServiziBibliotecheImpl] cancellaMovimentoAnnullabile "
              + getIdUtenteSebina(codiceFiscale)
              + " - "
              + movimento.getId());

      ServiceLocatorLivelloUnoBiblioteche.getInstance()
          .getApiServiziUtente()
          .deleteMovimentoUtenteById(getIdUtenteSebina(codiceFiscale), movimento.getId());

    } catch (BusinessException e) {
      log.error(
          "ServiziBibliotecheImpl -- cancellaMovimentoAnnullabile: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- cancellaMovimentoAnnullabile: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- cancellaMovimentoAnnullabile: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTmov(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaTmov ");

      List<TabellaRecord> listaTipoMovimenti = new ArrayList<TabellaRecord>();
      listaTipoMovimenti =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleCircolazione()
              .listaTabellaTmov(localeItaly.toString());
      return listaTipoMovimenti;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaTmov: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaTmov: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaTmov: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public String getDescrizioneTipoMovimento(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException {

    log.debug("[ServiziBibliotecheImpl] getDescrizioneTipoMovimento ");

    TabellaRecord recordTipo =
        getListaTabellaTmov(utente).stream()
            .filter(elem -> elem.getCd().equalsIgnoreCase(movimento.getCdTipo()))
            .findAny()
            .orElse(null);
    String descrizioneMovimento = "";
    if (recordTipo != null) {
      descrizioneMovimento = recordTipo.getDscr();
    }
    return descrizioneMovimento;
  }

  @Override
  public List<TabellaRecord> getListaTabellaStam(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaStam ");

      List<TabellaRecord> listaStatoMovimenti = new ArrayList<TabellaRecord>();
      listaStatoMovimenti =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleCircolazione()
              .listaTabellaStam(localeItaly.toString());
      return listaStatoMovimenti;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaStam: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaStam: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaStam: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public String getDescrizioneStatoMovimento(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException {
    log.debug("[ServiziBibliotecheImpl] getDescrizioneStatoMovimento ");

    TabellaRecord recordStato =
        getListaTabellaStam(utente).stream()
            .filter(elem -> elem.getCd().equalsIgnoreCase(movimento.getCdStato()))
            .findAny()
            .orElse(null);
    String descrizioneStato = "";
    if (recordStato != null) {
      descrizioneStato = recordStato.getDscr();
    }
    return descrizioneStato;
  }

  @Override
  public List<TabellaRecord> getListaTabellaBiblio(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaTabellaBiblio ");

      List<TabellaRecord> listaBiblioteche = new ArrayList<TabellaRecord>();
      listaBiblioteche =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleBase()
              .listaTabellaBiblio(localeItaly.toString());
      return listaBiblioteche;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaTabellaBiblio: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaTabellaBiblio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaTabellaBiblio: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public String getDescrizioneBiblioteca(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException {
    log.debug("[ServiziBibliotecheImpl] getDescrizioneBiblioteca ");

    TabellaRecord recordTabella =
        getListaTabellaBiblio(utente).stream()
            .filter(elem -> elem.getCd().equalsIgnoreCase(movimento.getCdBib()))
            .findAny()
            .orElse(null);
    String descrizioneBiblioteca = "";
    if (recordTabella != null) {
      descrizioneBiblioteca = recordTabella.getDscr();
    }
    return descrizioneBiblioteca;
  }

  @Override
  public void iscriviBibliotecheSebina(Utente utente, BibliotecheIscrizione bibliotecheIscrizione)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] iscriviBibliotecheSebina ");

      UtenteFull utenteFullSebina = new UtenteFull();

      utenteFullSebina.setSn(bibliotecheIscrizione.getCognome());
      utenteFullSebina.setGivenName(bibliotecheIscrizione.getNome());
      utenteFullSebina.setPlaceOfBirth(bibliotecheIscrizione.getLuogoNascita());
      utenteFullSebina.setDtNasc(bibliotecheIscrizione.getDataNascita());
      utenteFullSebina.setCf(bibliotecheIscrizione.getCodiceFiscale());

      GenderEnum genderEnum;
      if (bibliotecheIscrizione.getSesso().equalsIgnoreCase(GenderEnum.M.toString())) {
        genderEnum = GenderEnum.M;
      } else {
        genderEnum = GenderEnum.F;
      }
      utenteFullSebina.setGender(genderEnum);

      IndirizzoUtenteFull indirizzoUtenteFull = new IndirizzoUtenteFull();
      indirizzoUtenteFull.setVia(bibliotecheIscrizione.getIndirizzoResidenza());
      indirizzoUtenteFull.setCap(bibliotecheIscrizione.getCapResidenza());
      indirizzoUtenteFull.setCitta(bibliotecheIscrizione.getCittaResidenza());
      indirizzoUtenteFull.setProv(bibliotecheIscrizione.getProvinciaResidenza());
      indirizzoUtenteFull.setPaese(bibliotecheIscrizione.getStatoResidenza());

      utenteFullSebina.setResidenza(indirizzoUtenteFull);

      if (LabelFdCUtil.checkIfNotNull(bibliotecheIscrizione.getAutocompleteCittadinanza())) {
        utenteFullSebina.setCountry(bibliotecheIscrizione.getAutocompleteCittadinanza().getCd());
      }

      utenteFullSebina.setIndPrinc(bibliotecheIscrizione.getIndirizzoPrincipale());
      utenteFullSebina.setMail(bibliotecheIscrizione.getEmail());
      utenteFullSebina.setMobile(bibliotecheIscrizione.getCellulare());

      utenteFullSebina.setRecPref(bibliotecheIscrizione.getRecapitoPreferenziale());

      utenteFullSebina.setTut(bibliotecheIscrizione.getCodiceTipoUtente());
      utenteFullSebina.setAutTratt(bibliotecheIscrizione.isAutorizzazioneTrattamentoDati());

      String codiceBerio = "BE";
      utenteFullSebina.setBiblIscriz(codiceBerio);

      // workaraound per problema sebina HTTP 500
      utenteFullSebina.setLinCom(LinComEnum.IT);

      DocIdentita utenteFullDocIdent = new DocIdentita();
      utenteFullDocIdent.setNum(bibliotecheIscrizione.getNumeroCI());
      String tipoCI = "1";
      utenteFullDocIdent.setTipo(tipoCI);
      String ente = "Comune";
      utenteFullDocIdent.setEnte(ente);
      utenteFullDocIdent.setLuogo(bibliotecheIscrizione.getLuogoCI());
      utenteFullDocIdent.setDtRil(bibliotecheIscrizione.getDataRilascioCI());
      utenteFullDocIdent.setDtScad(bibliotecheIscrizione.getDataScadenzaCI());
      utenteFullSebina.setDocIdent(utenteFullDocIdent);

      if (!utente
          .getCodiceFiscaleOperatore()
          .equalsIgnoreCase(bibliotecheIscrizione.getCodiceFiscale())) {
        DocIdentita utenteFullDocIdentGenitore = new DocIdentita();
        utenteFullDocIdentGenitore.setNum(bibliotecheIscrizione.getNumeroCIGenitore());
        utenteFullDocIdentGenitore.setTipo(tipoCI);
        utenteFullDocIdentGenitore.setEnte(ente);
        utenteFullDocIdentGenitore.setLuogo(bibliotecheIscrizione.getLuogoCIGenitore());
        utenteFullDocIdentGenitore.setDtRil(bibliotecheIscrizione.getDataRilascioCIGenitore());
        utenteFullDocIdentGenitore.setDtScad(bibliotecheIscrizione.getDataScadenzaCIGenitore());
        utenteFullSebina.setDocIdentTutore(utenteFullDocIdentGenitore);

        String cognomeGenitore = "";
        String nomeGenitore = "";
        if (PageUtil.isStringValid(bibliotecheIscrizione.getCognomeGenitore())) {
          cognomeGenitore = bibliotecheIscrizione.getCognomeGenitore();
        }
        if (PageUtil.isStringValid(bibliotecheIscrizione.getNomeGenitore())) {
          nomeGenitore = bibliotecheIscrizione.getNomeGenitore();
        }

        utenteFullSebina.setNote(cognomeGenitore.concat("/").concat(nomeGenitore));

      } else {
        utenteFullSebina.setDocIdentTutore(null);
        utenteFullSebina.setNote(null);
      }

      log.debug("CP utenteFullSebina prima di POST = " + utenteFullSebina);

      String ctrlSebina = "CF";
      ServiceLocatorLivelloUnoBiblioteche.getInstance()
          .getApiUtente()
          .postUtente(utenteFullSebina, ctrlSebina);

      ServiceLocator.getInstance().getServiziConfigurazione().presaVisioneSebina(utente);

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- iscriviBibliotecheSebina: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- iscriviBibliotecheSebina: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- iscriviBibliotecheSebina: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getListaCodiciPrestiti() throws ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaCodiciPrestiti");

      List<String> listaCodiciPrestiti = new ArrayList<String>();
      listaCodiciPrestiti.add("PE");
      listaCodiciPrestiti.add("PS");
      listaCodiciPrestiti.add("PIL");
      listaCodiciPrestiti.add("PB");
      return listaCodiciPrestiti;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaCodiciPrestiti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaCodiciPrestiti: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<Movimento> getListaPrestiti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaPrestiti " + codiceFiscale);

      List<Movimento> listaPrestiti = new ArrayList<Movimento>();
      List<Movimento> listaTuttiMovimenti = getMovimentiCorrentiUtenteById(codiceFiscale);
      List<String> listaCodiciPrestiti = getListaCodiciPrestiti();

      listaPrestiti =
          listaTuttiMovimenti.stream()
              .filter(
                  elem ->
                      listaCodiciPrestiti.stream()
                          .anyMatch(tipo -> elem.getCdTipo().equalsIgnoreCase(tipo)))
              .collect(Collectors.toList());

      return listaPrestiti;
    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaPrestiti: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaPrestiti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaPrestiti: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getListaCodiciPrenotazioni()
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaCodiciPrestiti");

      List<String> listaCodiciPrenotazioni = new ArrayList<String>();
      listaCodiciPrenotazioni.add("PRE");
      listaCodiciPrenotazioni.add("PREB");
      return listaCodiciPrenotazioni;
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaCodiciPrestiti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaCodiciPrestiti: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<Movimento> getListaPrenotazioni(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getListaPrenotazioni " + codiceFiscale);

      List<Movimento> listaPrenotazioni = new ArrayList<Movimento>();
      List<Movimento> listaTuttiMovimenti = getMovimentiCorrentiUtenteById(codiceFiscale);
      List<String> listaCodiciPrenotazioni = getListaCodiciPrenotazioni();

      listaPrenotazioni =
          listaTuttiMovimenti.stream()
              .filter(
                  elem ->
                      listaCodiciPrenotazioni.stream()
                          .anyMatch(tipo -> elem.getCdTipo().equalsIgnoreCase(tipo)))
              .collect(Collectors.toList());

      return listaPrenotazioni;
    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getListaPrestiti: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getListaPrestiti: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getListaPrestiti: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getStatiVerde() throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getStatiVerde");

      List<String> listaVerdi = new ArrayList<String>();
      listaVerdi.add("attivo");
      listaVerdi.add("notificato");
      return listaVerdi;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getStatiVerde: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getStatiVerde: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getStatiGiallo() throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getStatiGiallo");

      List<String> listaGiallo = new ArrayList<String>();
      listaGiallo.add("prorogato");
      listaGiallo.add("asospeso");
      listaGiallo.add("notificare");
      return listaGiallo;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getStatiGiallo: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getStatiGiallo: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getStatiRosso() throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getStatiRosso");

      List<String> listaRosso = new ArrayList<String>();
      listaRosso.add("dasollecit");
      listaRosso.add("sollecit");
      listaRosso.add("scaduto");
      return listaRosso;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getStatiRosso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getStatiRosso: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<String> getDigitale() throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getDigitale");

      List<String> listaDigitale = new ArrayList<String>();
      listaDigitale.add("PREB");
      listaDigitale.add("PB");
      return listaDigitale;

    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getDigitale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getDigitale: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io leggo"));
    }
  }

  @Override
  public List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> getUtenteByCf(
      String codiceFiscale) throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] getUtenteByCf " + codiceFiscale);

      String tipoDocIdentita = "CF";

      List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaUtenteSebina =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiUtente()
              .findUtenteByDocIdentita(tipoDocIdentita, codiceFiscale);

      return listaUtenteSebina;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- getUtenteByCf: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- getUtenteByCf: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- getUtenteByCf: errore durante la chiamata delle API biblioteche ",
          e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    }
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbServiziPerMinori() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("serviziPerMinori", "Servizi per i miei figli"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbInternet() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("serviziPerMinori", "Servizi per i miei figli"));
    listaBreadcrumb.add(new BreadcrumbFdC("abilitaInternet", "Abilita Internet"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbIscrizione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("iscrizioneBiblioteche", "Iscrizione alle biblioteche comunali"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbMovimenti() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("movimenti", "I miei prestiti e prenotazioni"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRiepilogo() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbIscrizioneBambini() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("serviziPerMinori", "Servizi per i miei figli"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("iscrizioneBiblioteche", "Iscrizione alle biblioteche comunali"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbMovimentiBambini() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("serviziPerMinori", "Servizi per i miei figli"));
    listaBreadcrumb.add(new BreadcrumbFdC("movimenti", "Prestiti e prenotazioni"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbModificaDati() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioLeggo", "io Leggo"));
    listaBreadcrumb.add(new BreadcrumbFdC("modificaDatiSebina", "Modifica dati"));

    return listaBreadcrumb;
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("In corso");
    legenda1.setStile("badge bg-success");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("In scadenza");
    legenda2.setStile("badge bg-warning");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Scaduti");
    legenda3.setStile("badge bg-danger");
    listaLegenda.add(legenda3);

    return listaLegenda;
  }

  @Override
  public List<UtenteAbil> findUtenteAbil(Long utenteId, String cdAbil, String cdBib)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziBibliotecheImpl] findUtenteAbil " + utenteId);

      List<UtenteAbil> listaAbilitazioni = new ArrayList<UtenteAbil>();

      listaAbilitazioni =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiUtente()
              .findUtenteAbil(utenteId, cdAbil, cdBib);

      log.debug("CP listaAbilitazioni = " + listaAbilitazioni);

      return listaAbilitazioni;

    } catch (BusinessException e) {
      log.error("ServiziBibliotecheImpl -- findUtenteAbil: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBibliotecheImpl -- findUtenteAbil: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziBibliotecheImpl -- findUtenteAbil: errore durante la chiamata delle API biblioteche ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }

  @Override
  public void patchUtenteAbil(
      Long utenteId, String cdAbil, InlineObject1 inlineObject1, List<String> cdBib)
      throws BusinessException, ApiException, IOException {

    log.debug(
        "CP patchUtenteAbil = " + utenteId + "\n" + cdAbil + "\n" + inlineObject1 + "\n" + cdBib);

    ServiceLocatorLivelloUnoBiblioteche.getInstance()
        .getApiUtente()
        .patchUtenteAbil(utenteId, cdAbil, inlineObject1, cdBib);
  }

  @Override
  public void patchUtenteById(Long utenteId, InlineObject inLineObject)
      throws BusinessException, ApiException, IOException {
    log.debug("CP patchUtenteById = " + utenteId + "\n" + inLineObject + "\n");

    ServiceLocatorLivelloUnoBiblioteche.getInstance()
        .getApiUtente()
        .patchUtenteById(utenteId, inLineObject);
  }

  @Override
  public boolean inviaMailSegnalazioneBambino(
      ComponenteNucleo componenteNucleo, Utente utente, List<Long> listaIdSebina) {

    String mailFrom = BaseServiceImpl.FROM_ADDRESS;
    String mailCC = null;
    if (BaseServiceImpl.CC_ADDRESS != null && !BaseServiceImpl.CC_ADDRESS.isEmpty()) {
      mailCC = BaseServiceImpl.CC_ADDRESS;
    }

    String oggetto = "Verificare CF";

    Residente bambino = componenteNucleo.getDatiCittadino();
    if (LabelFdCUtil.checkIfNotNull(bambino)) {
      String cognomeNomeBambino =
          SendMailUtil.getEscapedHTML(
              bambino.getCpvFamilyName().concat(" ").concat(bambino.getCpvGivenName()));
      String codiceFiscaleBambino = bambino.getCpvTaxCode();

      String mail = utente.getMail();

      String mailbody = "Dati minore: ";
      mailbody += "<p>" + "Cognome e nome " + cognomeNomeBambino + "</p>";
      mailbody += "<p>" + "CF " + codiceFiscaleBambino + "</p>";

      mailbody += "<p>" + "Dati genitore: " + "</p>";
      mailbody +=
          "<p>"
              + "Cognome e nome "
              + utente.getCognome().concat(" ").concat(utente.getNome())
              + "</p>";
      mailbody += "<p>" + "CF " + utente.getCodiceFiscaleOperatore() + "</p>";
      mailbody += "<p>" + "e-mail " + mail + "</p>";

      ContenutoMessaggio contenutoMessaggio =
          new ContenutoMessaggioBuilder()
              .getInstance()
              .setTo(BaseServiceImpl.TO_ADDRESS)
              .setCc(mailCC)
              .setBcc("")
              .setFrom(mailFrom)
              .setSubject(oggetto)
              .setText(mailbody)
              .setSmtpServer(BaseServiceImpl.HOST_SMTP)
              .build();

      try {
        SendMailUtil.getInstance().sendTextMail(contenutoMessaggio);
      } catch (MessagingException e) {
        log.error("Errore durante l'invio della Mail segnalazione: " + e.getMessage());
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean inviaMailSegnalazione(Utente utente, List<Long> listaIdSebina) {

    String mailFrom = BaseServiceImpl.FROM_ADDRESS;
    String mailCC = null;
    if (BaseServiceImpl.CC_ADDRESS != null && !BaseServiceImpl.CC_ADDRESS.isEmpty()) {
      mailCC = BaseServiceImpl.CC_ADDRESS;
    }
    String oggetto = "Verificare registrazione utente Sebina";
    if (Constants.DEPLOY != Constants.TIPO_DEPLOY.ESERCIZIO) {
      oggetto = oggetto + " - " + Constants.DEPLOY.toString();
    }

    String cognomeNome =
        SendMailUtil.getEscapedHTML(utente.getCognome().concat(" ").concat(utente.getNome()));
    String codiceFiscale = utente.getCodiceFiscaleOperatore();
    String natoA = utente.getDatiCittadinoResidente().getCpvHasBirthPlace().getClvCity();
    String natoIl =
        LocalDateUtil.getDataFormatoEuropeo(utente.getDatiCittadinoResidente().getCpvDateOfBirth());
    String sesso = utente.getDatiCittadinoResidente().getCpvHasSex();
    String mail = utente.getMail();

    String nato = "nata a ";
    if (sesso.equalsIgnoreCase("M")) {
      nato = "nato a ";
    }

    String mailbody = "<p>" + cognomeNome + "</p>";
    mailbody += "<p>" + nato.concat(natoA).concat(" ").concat("il ").concat(natoIl) + "</p>";
    mailbody += "<p>" + "CF " + codiceFiscale + "</p>";
    mailbody += "<p>" + "e-mail " + mail + "</p>";
    mailbody +=
        "<p>Verificare la registrazione dell'utente nella banca dati Sebina, non trovo la corrispondenza con dati SPID.</p>";

    ContenutoMessaggio contenutoMessaggio =
        new ContenutoMessaggioBuilder()
            .getInstance()
            .setTo(BaseServiceImpl.TO_ADDRESS)
            .setCc(mailCC)
            .setBcc("")
            .setFrom(mailFrom)
            .setSubject(oggetto)
            .setText(mailbody)
            .setSmtpServer(BaseServiceImpl.HOST_SMTP)
            .build();

    try {
      SendMailUtil.getInstance().sendHtmlMail(contenutoMessaggio);
    } catch (MessagingException e) {
      log.error("Errore durante l'invio della Mail segnalazione: " + e.getMessage());
      return false;
    }
    return true;
  }
}
