package it.liguriadigitale.ponmetro.portale.business.mieiverbali.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mieiverbali.service.ServiziMieiVerbaliService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoDppVerbali;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatoIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Anagrafica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Anagrafica.CpvHasSexEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasAddress;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Contatto;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiAutodichiarazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraAvviso;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiRichiedenteIstanze;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Furto;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.GeneraAvvisoResult;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportoPagato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IstanzeCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.NonProprietario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.PuntiPatente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RichiestaDPP;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.TargaErrata;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.VerbaleCollection;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziMieiVerbaliImpl implements ServiziMieiVerbaliService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_VERBALI = "Errore di connessione alle API Verbali";

  @Override
  public VerbaleCollection getVerbaliByCF(Utente utente) throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      VerbaleCollection verbaleCollection =
          instance
              .getApiVerbaliContravvenzioni()
              .onGetFines(
                  utente.getCodiceFiscaleOperatore(), null, null, null, null, null, null, null);

      List<Verbale> listaVerbali = verbaleCollection.getVerbali();
      List<Verbale> listaVerbaliSorted = new ArrayList<>();

      if (listaVerbali != null) {
        listaVerbaliSorted =
            listaVerbali.stream()
                .sorted(Comparator.comparing(Verbale::getDataAccertamento).reversed())
                .collect(Collectors.toList());
      }

      verbaleCollection.setVerbali(listaVerbaliSorted);

      return verbaleCollection;

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getVerbaliByCF: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getVerbaliByCF: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getVerbaliByCF: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Verbale> getVerbaliByTargaDataInizioProprieta(Utente utente)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<Veicolo> listaVeicoliAttivi = utente.getListaVeicoliAttivi();
      Map<String, String> mapTargaDataInizioProprieta = new HashMap<>();
      if (listaVeicoliAttivi != null) {
        mapTargaDataInizioProprieta =
            listaVeicoliAttivi.stream()
                .collect(Collectors.toMap(Veicolo::getTarga, Veicolo::getDataInizioProprieta));
      }

      List<Verbale> listaVerbali = new ArrayList<>();

      for (Map.Entry<String, String> veicoloAttivo : mapTargaDataInizioProprieta.entrySet()) {
        LocalDate localDateInizio =
            LocalDateUtil.convertiDaFormatoEuropeo(veicoloAttivo.getValue());

        VerbaleCollection verbaleCollection =
            instance
                .getApiVerbaliContravvenzioni()
                .onGetFines(
                    utente.getCodiceFiscaleOperatore(),
                    veicoloAttivo.getKey(),
                    null,
                    null,
                    null,
                    null,
                    localDateInizio,
                    null);

        if (verbaleCollection.getVerbali() != null) {
          listaVerbali.addAll(verbaleCollection.getVerbali());
        }
      }

      return listaVerbali;

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getVerbaliByTargaDataNascita: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getVerbaliByTargaDataNascita: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getVerbaliByTargaDataNascita: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @Override
  public List<Verbale> getTuttiVerbali(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      List<Verbale> verbaliByCF = getVerbaliByCF(utente).getVerbali();
      List<Verbale> verbaliByTargaDataInizioProprieta =
          getVerbaliByTargaDataInizioProprieta(utente);

      List<Verbale> listaVerbali =
          Stream.concat(verbaliByCF.stream(), verbaliByTargaDataInizioProprieta.stream())
              .collect(Collectors.toList());

      List<Verbale> listaVerbaliSenzaDuplicati =
          listaVerbali.stream()
              .filter(distinctByKey(elem -> elem.getNumeroProtocollo()))
              .collect(Collectors.toList());

      List<Verbale> listaVerbaliSenzaDuplicatiOrdinatiPerData =
          listaVerbaliSenzaDuplicati.stream()
              .sorted(Comparator.comparing(Verbale::getDataAccertamento).reversed())
              .collect(Collectors.toList());

      return listaVerbaliSenzaDuplicatiOrdinatiPerData;

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getTuttiVerbali: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getTuttiVerbali: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getTuttiVerbali: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }

  @Override
  public List<Verbale> getVerbaleByNumeroProtocollo(Utente utente, String numeroProtocollo)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      VerbaleCollection verbaleCollection =
          instance
              .getApiVerbaliContravvenzioni()
              .onGetFines(
                  null,
                  null,
                  utente.getNome().toUpperCase(),
                  utente.getCognome().toUpperCase(),
                  utente.getDataDiNascita(),
                  numeroProtocollo,
                  null,
                  null);

      List<Verbale> listaVerbaliByNumeroProtocollo = new ArrayList<>();

      if (verbaleCollection.getVerbali() != null) {
        listaVerbaliByNumeroProtocollo = verbaleCollection.getVerbali();
      }

      return listaVerbaliByNumeroProtocollo;

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocollo: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocollo: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocollo: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Verbale> getVerbaleByNumeroProtocolloSenzaAnagrafica(
      Utente utente, String numeroAvviso) throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      List<Verbale> listaVerbaliByNumeroAvvisoSenzaAnagrafica = new ArrayList<>();

      List<Veicolo> listaVeicoliAttivi = utente.getListaVeicoliAttivi();
      Map<String, String> mapTargaDataInizioProprieta = new HashMap<>();
      mapTargaDataInizioProprieta =
          listaVeicoliAttivi.stream()
              .collect(Collectors.toMap(Veicolo::getTarga, Veicolo::getDataInizioProprieta));

      for (Map.Entry<String, String> veicoloAttivo : mapTargaDataInizioProprieta.entrySet()) {
        LocalDate localDateInizio =
            LocalDateUtil.convertiDaFormatoEuropeo(veicoloAttivo.getValue());

        VerbaleCollection verbaleCollection =
            instance
                .getApiVerbaliContravvenzioni()
                .onGetFines(
                    null,
                    veicoloAttivo.getKey(),
                    null,
                    null,
                    null,
                    numeroAvviso,
                    localDateInizio,
                    null);

        if (verbaleCollection.getVerbali() != null) {
          listaVerbaliByNumeroAvvisoSenzaAnagrafica.addAll(verbaleCollection.getVerbali());
        }
      }

      List<Verbale> verbaleNumeroAvviso =
          listaVerbaliByNumeroAvvisoSenzaAnagrafica.stream()
              .filter(elem -> elem.getNumeroAvviso().equalsIgnoreCase(numeroAvviso))
              .collect(Collectors.toList());

      return verbaleNumeroAvviso;

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocolloSenzaAnagrafica: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocolloSenzaAnagrafica: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getVerbaleByNumeroProtocolloSenzaAnagrafica: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Verbale> getRicercaVerbaleByNumeroAvviso(Utente utente, Verbale verbale)
      throws BusinessException, ApiException, IOException {
    try {
      String numeroAvviso = verbale.getNumeroAvviso();

      List<Verbale> verbaliByNumeroAvviso = getVerbaleByNumeroProtocollo(utente, numeroAvviso);
      List<Verbale> verbaliByNumeroAvvisoSenzaAnagrafica =
          getVerbaleByNumeroProtocolloSenzaAnagrafica(utente, numeroAvviso);

      List<Verbale> listaVerbali =
          Stream.concat(
                  verbaliByNumeroAvviso.stream(), verbaliByNumeroAvvisoSenzaAnagrafica.stream())
              .collect(Collectors.toList());

      List<Verbale> listaVerbaliSenzaDuplicati =
          listaVerbali.stream()
              .filter(distinctByKey(elem -> elem.getNumeroProtocollo()))
              .collect(Collectors.toList());

      return listaVerbaliSenzaDuplicati;

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getRicercaVerbaleByNumeroAvviso: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getRicercaVerbaleByNumeroAvviso: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getRicercaVerbaleByNumeroAvviso: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }

  @Override
  public DettaglioVerbale getDettaglioVerbale(String numeroProtocollo, Utente utente)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      DettaglioVerbale dettaglioVerbale =
          instance
              .getApiVerbaliContravvenzioni()
              .getFineDetail(numeroProtocollo, utente.getCodiceFiscaleOperatore());

      List<ImportoPagato> listaImportoPagato = dettaglioVerbale.getImportiPagati();
      if (listaImportoPagato != null) {
        listaImportoPagato.removeIf(Objects::isNull);
      }

      return dettaglioVerbale;

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getDettaglioVerbale: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getDettaglioVerbale: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getDettaglioVerbale: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Verbale> getListaTuttiVerbaliConFiltroStatoDaBadge(Utente utente, String statoVerbale)
      throws BusinessException, ApiException, IOException {
    try {
      List<Verbale> listaVerbaliConFiltroStatoDaBadge = new ArrayList<>();
      List<Verbale> listaTuttiVerbali = getTuttiVerbali(utente);

      if (listaTuttiVerbali != null && !listaTuttiVerbali.isEmpty()) {
        listaVerbaliConFiltroStatoDaBadge =
            listaTuttiVerbali.stream()
                .filter(elem -> elem.getStato().equalsIgnoreCase(statoVerbale))
                .collect(Collectors.toList());
      }

      return listaVerbaliConFiltroStatoDaBadge;

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getListaTuttiVerbaliConFiltroStatoDaBadge: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getListaTuttiVerbaliConFiltroStatoDaBadge: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getListaTuttiVerbaliConFiltroStatoDaBadge: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }

  @Override
  public FileAllegato getFileAllegatoAlVerbaleByAllegatoId(
      Utente utente, String numeroProtocollo, String allegatoId)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiAllegatiAlVerbale()
          .getFineAttachment(numeroProtocollo, allegatoId, null);
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbale: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbale: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbale: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getFileAllegatoAlVerbaleByAllegatoUri(
      Utente utente, String numeroProtocollo, String allegatoUri)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiAllegatiAlVerbale()
          .getFineAttachment(numeroProtocollo, null, allegatoUri);
    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbaleByAllegatoUri: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbaleByAllegatoUri: errore nella Response:",
          e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieiVerbaliImpl -- getFileAllegatoAlVerbaleByAllegatoUri: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GeneraAvvisoResult setGeneraAvviso(DatiGeneraAvviso datiGeneraAvviso)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("CP dati genera avviso = " + datiGeneraAvviso);
      return instance.getApiVerbaliContravvenzioni().setGeneraAvviso(datiGeneraAvviso);
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- setGeneraAvviso: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- setGeneraAvviso: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  // ISTANZE PL
  @Override
  public IstanzeCollection getIstanzeByVerbale(Verbale verbale)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiIstanzeRicorsi()
          .getFineCancellationsRequest(verbale.getNumeroProtocollo());
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Istanza inserisciIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException {
    // chiamato da STEP3
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[InserisciIstanza] Istanza da inserire: " + datiRichiestaIstanzaPl);
      DatiIstanza datiIstanza = new DatiIstanza();
      // si crea senza allegati datiIstanza.setAllegati(allegati);
      // i titoli autorizzativi non sono previsti in questo momento
      // datiIstanza.addTitoliAutorizzativiItem(titoliAutorizzativiItem)

      datiIstanza.setVerbali(new ArrayList<Verbale>());
      log.debug(
          "11 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      datiIstanza.addVerbaliItem(
          datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza().getVerbale());
      for (DatiCompletiVerbale datiCompletiVerbale :
          datiRichiestaIstanzaPl.getVerbaliUlterioriCompatibili()) {
        if (datiCompletiVerbale.isSelezionato()) {
          datiIstanza.addVerbaliItem(datiCompletiVerbale.getVerbale());
        }
      }
      datiIstanza.dataIstanza(LocalDate.now());
      log.debug(
          "22 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);

      /* furto */
      Furto furto = new Furto();
      furto.setAutodichiarazione(datiRichiestaIstanzaPl.getAutodichiarazioneFurto());
      furto.setAutodichiarazione(datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento());
      DatiAutodichiarazione datiAutodichiarazione = new DatiAutodichiarazione();
      datiAutodichiarazione.setComuneForzaPolizia(
          datiRichiestaIstanzaPl.getRiconsegnatoPoliziaComune());
      datiAutodichiarazione.setDataFurto(datiRichiestaIstanzaPl.getFurtoData());
      log.debug(
          "33 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      datiAutodichiarazione.setDataRiconsegna(datiRichiestaIstanzaPl.getRiconsegnatoData());
      datiAutodichiarazione.setDataRitrovamento(datiRichiestaIstanzaPl.getRitrovamentoData());
      datiAutodichiarazione.setForzaPolizia(datiRichiestaIstanzaPl.getRiconsegnatoPolizia());
      datiAutodichiarazione.setMarca(datiRichiestaIstanzaPl.getAutodichiarazioneMarca());
      datiAutodichiarazione.setModello(datiRichiestaIstanzaPl.getAutodichiarazioneModello());
      datiAutodichiarazione.setRiconsegnato(
          LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getRiconsegnatoPolizia()));
      datiAutodichiarazione.setRitrovato(
          LabelFdCUtil.checkIfNotNull(
              datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento()));
      datiAutodichiarazione.setTarga(datiRichiestaIstanzaPl.getAutodichiarazioneTarga());
      log.debug(
          "44 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      furto.setDatiAutodichiarazione(datiAutodichiarazione);

      log.debug(
          "441 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "datiAutodichiarazione: "
              + datiAutodichiarazione);
      datiIstanza.setFurto(furto);
      log.debug(
          "442 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "datiIstanza: "
              + datiIstanza);

      /* Anagrafica */
      DatiRichiedenteIstanze datiRichiedenteIstanze = new DatiRichiedenteIstanze();
      datiRichiedenteIstanze.setCodiceAnagraficaHermes(
          datiRichiestaIstanzaPl.getCodiceHermesAnagrafica());
      log.debug(
          "443 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "datiRichiestaIstanzaPl.getCodiceHermesAnagrafica(): "
              + datiRichiestaIstanzaPl.getCodiceHermesAnagrafica());
      Anagrafica anagrafica = new Anagrafica();

      // stringhe stringa vuota
      log.debug(
          "55 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      anagrafica.setCpvDateOfDeath(null);
      anagrafica.setCpvPersonID("");
      anagrafica.setCpvTaxCode("");
      anagrafica.setGenovaOntoIDCardIssuingMunicipality("");
      anagrafica.setGenovaOntoIDCardNumber("");
      anagrafica.setGenovaStatoCivile("");
      log.debug(
          "66 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      anagrafica.setCpvTaxCode(datiRichiestaIstanzaPl.getRichiedenteCf());
      anagrafica.setCpvFamilyName(datiRichiestaIstanzaPl.getRichiedenteCognome());
      anagrafica.setCpvGivenName(datiRichiestaIstanzaPl.getRichiedenteNome());
      anagrafica.setCpvDateOfBirth(datiRichiestaIstanzaPl.getNascitaData());
      log.debug(
          "77 ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);
      anagrafica.setRdfsLabel(
          datiRichiestaIstanzaPl.getRichiedenteCognome()
              + " "
              + datiRichiestaIstanzaPl.getRichiedenteNome());
      anagrafica.setCpvHasSex(
          CpvHasSexEnum.fromValue(datiRichiestaIstanzaPl.getUtente().getSesso()));
      AnagraficaCpvHasBirthPlace anagraficaCpvHasBirthPlace = new AnagraficaCpvHasBirthPlace();
      anagraficaCpvHasBirthPlace.setClvCity(datiRichiestaIstanzaPl.getNascitaComune());
      anagrafica.setCpvHasBirthPlace(anagraficaCpvHasBirthPlace);

      AnagraficaCpvHasAddress anagraficaCpvHasAddress = new AnagraficaCpvHasAddress();

      String fullAddress = "";
      fullAddress = datiRichiestaIstanzaPl.getResidenzaVia();

      anagraficaCpvHasAddress.setClvCity(datiRichiestaIstanzaPl.getResidenzaComune());
      anagraficaCpvHasAddress.setClvOfficialStreetName(datiRichiestaIstanzaPl.getResidenzaVia());

      if (LabelFdCUtil.checkIfNotNull(
          datiRichiestaIstanzaPl.getResidenzaNumeroCivicoSoloNumero())) {
        anagraficaCpvHasAddress.setClvStreetNumber(
            String.valueOf(datiRichiestaIstanzaPl.getResidenzaNumeroCivicoSoloNumero()));

        fullAddress =
            fullAddress
                .concat(" ")
                .concat(
                    String.valueOf(datiRichiestaIstanzaPl.getResidenzaNumeroCivicoSoloNumero()));
      }

      anagraficaCpvHasAddress.setClvStreetNumberExponent(
          datiRichiestaIstanzaPl.getResidenzaNumeroCivicoEsponente());
      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getResidenzaNumeroCivicoEsponente())) {
        fullAddress =
            fullAddress
                .concat(" ")
                .concat(datiRichiestaIstanzaPl.getResidenzaNumeroCivicoEsponente());
      }

      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getResidenzaInterno())) {
        // anagraficaCpvHasAddress.setClvFlatNumberOnly(String.valueOf(datiRichiestaIstanzaPl.getResidenzaInterno()));
        fullAddress =
            fullAddress
                .concat(" ")
                .concat(String.valueOf(datiRichiestaIstanzaPl.getResidenzaInterno()));
      }

      // anagraficaCpvHasAddress.setClvFlatExponent(datiRichiestaIstanzaPl.getResidenzaInternoEsponente());
      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getResidenzaInternoEsponente())) {
        fullAddress =
            fullAddress.concat(" ").concat(datiRichiestaIstanzaPl.getResidenzaInternoEsponente());
      }

      // anagraficaCpvHasAddress.setClvStreetNumberColor(datiRichiestaIstanzaPl.getResidenzaColore());
      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getResidenzaColore())) {
        fullAddress = fullAddress.concat(" ").concat(datiRichiestaIstanzaPl.getResidenzaColore());
      }

      anagraficaCpvHasAddress.setClvFullAddress(fullAddress);

      anagrafica.setCpvHasAddress(anagraficaCpvHasAddress);

      List<Contatto> contattiRichiedente = new ArrayList<>();
      Contatto contattoTel = new Contatto();
      contattoTel.setRecapito(datiRichiestaIstanzaPl.getRichiedenteTelefono());
      contattoTel.setTipo("telefono");
      contattiRichiedente.add(contattoTel);
      Contatto contattoMail = new Contatto();
      contattoMail.setRecapito(datiRichiestaIstanzaPl.getRichiedenteEmail());
      contattoMail.setTipo("email");
      contattiRichiedente.add(contattoMail);
      anagrafica.setContatti(contattiRichiedente);
      log.debug("datiIstanza PRIMA=" + datiIstanza.getRichiedente());
      datiRichiedenteIstanze.setDatiAnagraficiRichiedente(new ArrayList<Anagrafica>());
      datiRichiedenteIstanze.addDatiAnagraficiRichiedenteItem(anagrafica);
      datiIstanza.setRichiedente(datiRichiedenteIstanze);
      datiIstanza.proprietarioETitolarePermessoDisabile(
          datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare());
      // datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato()
      String codiceHermesParlante = datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato();

      log.debug(
          "[Codice Hermes Parlante] Codice Hermes: "
              + datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato());
      if ("03".equalsIgnoreCase(codiceHermesParlante)) {
        // if
        // (datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento())
        // {
        // codiceHermesParlante +="-SI";
        // } else {
        // codiceHermesParlante +="-NO";
        // }
      }
      datiIstanza.setTipoIstanza(codiceHermesParlante);
      log.debug("datiIstanza DOPO =" + datiIstanza.getRichiedente());
      log.debug(
          "ServiziMieiVerbaliImpl -- PROVO A CHIAMARE ORA:::: "
              + "numeroVerbalePartenza: "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza()
              + "datiIstanza:::::::: "
              + datiIstanza);

      // Codice Hermes 43
      datiIstanza.setDatiISEE(datiRichiestaIstanzaPl.getDatiISEE());

      // Codice Hermes 42
      datiIstanza.setRimborso(datiRichiestaIstanzaPl.getRimborso());

      TargaErrata targaErrata = new TargaErrata();
      targaErrata.setMarca(datiRichiestaIstanzaPl.getMarca());
      targaErrata.setMiaMacchina(datiRichiestaIstanzaPl.getMiaMacchina().equalsIgnoreCase("No"));
      targaErrata.setModello(datiRichiestaIstanzaPl.getModello());
      if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getNoteTargaErrata())) {
        targaErrata.setNote(
            "Note inviate dal richiedente: " + datiRichiestaIstanzaPl.getNoteTargaErrata());
      }

      datiIstanza.setTargaErrata(targaErrata);

      NonProprietario nonProprietario = new NonProprietario();
      nonProprietario.setDataFineProprieta(datiRichiestaIstanzaPl.getDataFineProprieta());
      nonProprietario.setDataInizioProprieta(datiRichiestaIstanzaPl.getDataInizioProprieta());
      nonProprietario.setMaiMia(!datiRichiestaIstanzaPl.getMaiMia().equalsIgnoreCase("No"));

      datiIstanza.setNonProprietario(nonProprietario);

      log.debug("CP body prima di post = " + datiIstanza);

      return instance
          .getApiIstanzeRicorsi()
          .setCancellationRequestToVerbaleId(
              datiRichiestaIstanzaPl.getNumeroVerbalePartenza(), datiIstanza);

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public EsitoOperazione inserisciAllegatoAdIstanza(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, AllegatoIstanza allegatoIstanza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.error(
          "FRR inserisciAllegatoAdIstanza "
              + datiRichiestaIstanzaPl
              + " allegatoIstanza: "
              + allegatoIstanza);
      return instance
          .getApiIstanzeRicorsi()
          .inserimentoAllegato(
              datiRichiestaIstanzaPl.getNumeroVerbalePartenza(),
              "" + datiRichiestaIstanzaPl.getIstanza().getId(),
              datiRichiestaIstanzaPl.getIstanza().getAnno().intValue(),
              allegatoIstanza);

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciAllegatoAdIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- inserisciAllegatoAdIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Istanza getDatiIstanza(Verbale verbale, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.error(
          "FRR getDatiIstanza verbale: " + verbale + " istanzaId: " + istanzaId + " anno: " + anno);

      Istanza istanza =
          instance
              .getApiIstanzeRicorsi()
              .getFineCancellationRequest(verbale.getNumeroProtocollo(), istanzaId, anno);

      log.error("FRR getDatiIstanza TROVATAAAAAAAAAAAAA: " + istanza);
      return istanza;
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getDatiIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- getDatiIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public EsitoOperazione deleteIstanza(Verbale verbale, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "FRR deleteIstanza verbale: " + verbale + " istanzaId: " + istanzaId + " anno: " + anno);

      return instance
          .getApiIstanzeRicorsi()
          .deleteCancellationRequestInFine(
              Integer.getInteger(verbale.getNumeroProtocollo()),
              Integer.getInteger(istanzaId),
              anno);
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- deleteIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- deleteIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getAllegatoIstanza(
      Verbale verbale, String istanzaId, Integer anno, String idDocumentoIstanza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "FRR getAllegatoIstanza verbale: " + verbale == null
              ? "null"
              : verbale
                  + " istanzaId: "
                  + istanzaId
                  + " anno: "
                  + anno
                  + " idDocumentoIstanza: "
                  + idDocumentoIstanza);

      return instance
          .getApiIstanzeRicorsi()
          .getDocumentoIstanza(
              verbale == null ? "ss" : verbale.getNumeroProtocollo(),
              istanzaId,
              idDocumentoIstanza,
              anno);
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getAllegatoIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- getAllegatoIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public EsitoOperazione deleteAllegatoIstanza(
      Verbale verbale, String istanzaId, Integer anno, String idDocumentoIstanza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "FRR deleteAllegatoIstanza verbale: "
              + verbale
              + " istanzaId: "
              + istanzaId
              + " anno: "
              + anno
              + " idDocumentoIstanza: "
              + idDocumentoIstanza);

      return instance
          .getApiIstanzeRicorsi()
          .getFineCancellationAllegato(
              verbale.getNumeroProtocollo(), istanzaId, idDocumentoIstanza, anno);
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getAllegatoIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiVerbaliImpl -- getAllegatoIstanza: errore nella Response:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public EsitoOperazione concludiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("FRR concludiIstanza datiRichiestaIstanzaPl: ");
      log.debug(
          "datiRichiestaIstanzaPl.getNumeroVerbalePartenza(): "
              + datiRichiestaIstanzaPl.getNumeroVerbalePartenza());
      log.debug(
          "datiRichiestaIstanzaPl.getIstanza().getId(): "
              + datiRichiestaIstanzaPl.getIstanza().getId());
      log.debug(
          "datiRichiestaIstanzaPl.getIstanza().getAnno().intValue():"
              + datiRichiestaIstanzaPl.getIstanza().getAnno());
      return instance
          .getApiIstanzeRicorsi()
          .addResumeInstance(
              datiRichiestaIstanzaPl.getNumeroVerbalePartenza(),
              "" + datiRichiestaIstanzaPl.getIstanza().getId(),
              datiRichiestaIstanzaPl.getIstanza().getAnno().intValue());
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- concludiIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- concludiIstanza: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AllegatiCollection getAllegatiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException {
    return getAllegatiIstanza(
        datiRichiestaIstanzaPl.getNumeroVerbalePartenza(),
        "" + datiRichiestaIstanzaPl.getIstanza().getId(),
        datiRichiestaIstanzaPl.getIstanza().getAnno().intValue());
  }

  @Override
  public AllegatiCollection getAllegatiIstanza(
      String numeroProtocollo, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "FRR getAllegatiIstanza numeroProtocollo: "
              + numeroProtocollo
              + "istanzaId: "
              + istanzaId
              + "anno: "
              + anno);
      return instance
          .getApiAllegatiAllIstanzaRicorso()
          .getCancellationRequestAttachments(numeroProtocollo, istanzaId, anno);

    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getAllegatiIstanza: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getAllegatiIstanza: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<String> getMarcheVeicoli() throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      return instance.getApiIstanzeRicorsi().getListaMarcheVeicoli();
    } catch (BusinessException e) {
      log.error("ServiziMieiVerbaliImpl -- getMarcheVeicoli: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- getMarcheVeicoli: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  // DPP

  @Override
  public PuntiPatente dichiarazionePuntiPatenteConducente(
      Utente utente, String numeroAvviso, RichiestaDPP richiestaDpp)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("CP dichiarazionePuntiPatenteConducente " + utente.getCodiceFiscaleOperatore());
      return ServiceLocatorLivelloUnoDppVerbali.getInstance()
          .getApiDichiarazionePuntiPatente()
          .setDPPConducenteToVerbaleId(numeroAvviso, richiestaDpp);

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteConducente: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteConducente: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public PuntiPatente dichiarazionePuntiPatenteProprietario(
      Utente utente, String numeroAvviso, RichiestaDPP richiestaDpp)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("CP dichiarazionePuntiPatenteProprietario " + utente.getCodiceFiscaleOperatore());
      return ServiceLocatorLivelloUnoDppVerbali.getInstance()
          .getApiDichiarazionePuntiPatente()
          .setDPPToVerbaleId(numeroAvviso, richiestaDpp);

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Informativa", 1));
    listaStep.add(new StepFdC("Dati", 2));
    listaStep.add(new StepFdC("ISEE", 3));
    listaStep.add(new StepFdC("Riepilogo", 4));
    listaStep.add(new StepFdC("Esito", 5));

    return listaStep;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRichiestaRate() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("iMieiVerbali", "I Miei Verbali"));
    listaBreadcrumb.add(new BreadcrumbFdC("richiestaRate", "Richiesta di Rateizzazione"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborso() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("iMieiVerbali", "I Miei Verbali"));
    listaBreadcrumb.add(new BreadcrumbFdC("richiestaRimborso", "Richiesta di Rimborso"));

    return listaBreadcrumb;
  }

  @Override
  public EsitoOperazione inserimentoISEE(
      String numeroProtocollo, String istanzaId, Integer anno, DatiISEE datiISEE)
      throws BusinessException, ApiException {
    try {

      log.debug("Inserimento Dati ISEE Numero Protocollo: " + numeroProtocollo);

      return ServiceLocatorLivelloUno.getInstance()
          .getApiIstanzeRicorsi()
          .inserimentoISEE(numeroProtocollo, istanzaId, anno, datiISEE);
    } catch (BusinessException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }

  @Override
  public EsitoOperazione inserisciConcludiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException {

    Istanza istanza = this.inserisciIstanza(datiRichiestaIstanzaPl);
    EsitoOperazione esito = null;

    if (LabelFdCUtil.checkIfNotNull(istanza)
        && LabelFdCUtil.checkIfNotNull(istanza.getId())
        && istanza.getId() != 0) {

      log.debug("[IstanzaDaConcludere] Istanza: " + istanza);
      datiRichiestaIstanzaPl.setIstanza(istanza);

      esito = concludiIstanza(datiRichiestaIstanzaPl);
    }

    return esito;
  }

  @Override
  public AvvisoBonario getAvvisoBonario(String id) throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUno instance = new ServiceLocatorLivelloUno();

    try {
      return instance.getVerbaliAvvisoBonarioApi().getAvvisoBonario(id);
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziMieiVerbaliImpl -- getAvvisoBonario: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiVerbaliImpl -- dichiarazionePuntiPatenteProprietario: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i Miei Verbali"));
    }
  }
}
