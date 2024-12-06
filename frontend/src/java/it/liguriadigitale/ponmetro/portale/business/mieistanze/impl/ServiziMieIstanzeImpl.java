package it.liguriadigitale.ponmetro.portale.business.mieistanze.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mieistanze.service.ServiziMieIstanzeService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaIstanza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraListaDettagliVerbali;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbaleCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IstanzeCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.NumeroProtocolloVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziMieIstanzeImpl implements ServiziMieIstanzeService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_VERBALI_ISTANZE =
      "Errore di connessione alle API Verbali Istanze";

  private static final int NUMERO_THREAD = 4;
  private static final int TIMEOUT_THREAD = 130;

  @Override
  public List<Istanza> getIstanze(Utente utente)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<Istanza> listaIstanze = new ArrayList<Istanza>();

      log.debug("CP getIstanze " + utente.getCodiceFiscaleOperatore());
      IstanzeCollection istanzeCollection =
          instance
              .getApiIstanzeRicorsi()
              .getCancellationsRequest(utente.getCodiceFiscaleOperatore());
      if (istanzeCollection.getIstanze() != null && !istanzeCollection.getIstanze().isEmpty()) {
        listaIstanze = istanzeCollection.getIstanze();
      }

      return listaIstanze;

    } catch (BusinessException e) {
      log.error("ServiziMieIstanzeImpl -- getIstanze: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI_ISTANZE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getIstanze: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieIstanzeImpl -- getIstanze: errore durante la chiamata delle API verbali istanze",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Istanza> getListaTutteIstanzeConFiltroStatoDaBadge(Utente utente, String statoIstanza)
      throws BusinessException, ApiException, IOException {
    try {
      log.debug("CP getListaTutteIstanzeConFiltroStatoDaBadge " + statoIstanza);

      List<Istanza> listaIstanzeConFiltroStatoDaBadge = new ArrayList<Istanza>();
      List<Istanza> listaTutteIstanze = getIstanze(utente);

      if (!("TUTTE".equalsIgnoreCase(statoIstanza))
          && listaTutteIstanze != null
          && !listaTutteIstanze.isEmpty()) {
        listaIstanzeConFiltroStatoDaBadge =
            listaTutteIstanze.stream()
                .filter(elem -> elem.getStatoCodice().equalsIgnoreCase(statoIstanza))
                .collect(Collectors.toList());
      } else {
        listaIstanzeConFiltroStatoDaBadge = listaTutteIstanze;
      }

      return listaIstanzeConFiltroStatoDaBadge;
    } catch (BusinessException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getListaTutteIstanzeConFiltroStatoDaBadge: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI_ISTANZE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getListaTutteIstanzeConFiltroStatoDaBadge: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieIstanzeImpl -- getListaTutteIstanzeConFiltroStatoDaBadge: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
    }
  }

  /*
   * @Override public List<Istanza> getIstanzeByNumeroProtocollo(Utente utente,
   * String numeroProtocollo) throws BusinessException, ApiException, IOException
   * { try { List<Istanza> listaIstanze = new ArrayList<Istanza>();
   *
   * IstanzeCollection istanzeCollection =
   * ServiceLocatorLivelloUno.getInstance().getApiIstanzeRicorsi()
   * .getFineCancellationsRequest(numeroProtocollo); if
   * (istanzeCollection.getIstanze() != null &&
   * !istanzeCollection.getIstanze().isEmpty()) { listaIstanze =
   * istanzeCollection.getIstanze(); }
   *
   * return listaIstanze;
   *
   * } catch (BusinessException e) { log.
   * error("ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore API verbali:"
   * , e); throw new BusinessException(ERRORE_API_VERBALI_ISTANZE); } catch
   * (WebApplicationException e) { log.
   * error("ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore nella Response:"
   * + e.getMessage()); throw new ApiException(e.getResponse(), e.getMessage()); }
   * catch (RuntimeException e) { log.debug(
   * "ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore durante la chiamata delle API verbali "
   * , e); throw new
   * RestartResponseAtInterceptPageException(VerbaliErrorPage.class); } }
   */

  @Override
  public List<Istanza> getIstanzeByNumeroProtocollo(Utente utente, RicercaIstanza ricercaIstanza)
      throws BusinessException, ApiException, IOException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String numeroProtocollo = ricercaIstanza.getNumeroProtocollo();

      String codiceFiscaleUtente = utente.getCodiceFiscaleOperatore();

      List<Istanza> listaIstanze = new ArrayList<Istanza>();
      IstanzeCollection istanzeCollection =
          instance.getApiIstanzeRicorsi().getFineCancellationsRequest(numeroProtocollo);
      if (istanzeCollection.getIstanze() != null && !istanzeCollection.getIstanze().isEmpty()) {
        listaIstanze = istanzeCollection.getIstanze();
        listaIstanze =
            listaIstanze.stream()
                .filter(
                    elem ->
                        elem.getSoggetto().getCpvTaxCode().equalsIgnoreCase(codiceFiscaleUtente))
                .collect(Collectors.toList());
      }

      return listaIstanze;

    } catch (BusinessException e) {
      log.error("ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore API verbali:", e);
      throw new BusinessException(ERRORE_API_VERBALI_ISTANZE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieIstanzeImpl -- getIstanzeByNumeroProtocollo: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Istanza getIstanzaByNumeroProtocolloNumeroIstanza(
      Utente utente, String numeroProcollo, String numerIstanza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      // TODO modifica per YAML 1.9.1
      Istanza istanza =
          instance
              .getApiIstanzeRicorsi()
              .getFineCancellationRequest(numeroProcollo, numerIstanza, null);
      return istanza;
    } catch (BusinessException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getIstanzaByNumeroProtocolloNumeroIstanza: errore API verbali:",
          e);
      throw new BusinessException(ERRORE_API_VERBALI_ISTANZE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieIstanzeImpl -- getIstanzaByNumeroProtocolloNumeroIstanza: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMieIstanzeImpl -- getIstanzaByNumeroProtocolloNumeroIstanza: errore durante la chiamata delle API verbali ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
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
  public DatiRichiestaIstanzaPl popolaMappaMultiThread(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, Utente utente)
      throws BusinessException, ApiException, IOException {
    log.debug("INIZIO popolamappa " + LocalDateTime.now());
    List<Verbale> lista = datiRichiestaIstanzaPl.getListTuttiVerbali();
    Long nProcessati = 0L;
    final List<List<Verbale>> listaDiListe = suddividiLista(lista, NUMERO_THREAD);
    final ExecutorService ex = Executors.newFixedThreadPool(NUMERO_THREAD);
    final List<Callable<List<DettaglioVerbale>>> tasks = new ArrayList<>();
    Integer i = 1;
    for (final List<Verbale> sottoInsieme : listaDiListe) {
      log.debug("VerbaliThread --- popola task " + i + " con n.elementi: " + sottoInsieme.size());
      String thread = "Thread" + i;
      tasks.add(
          () -> {
            return processaListaDaCoda(sottoInsieme, thread, utente, datiRichiestaIstanzaPl);
          });
      i++;
    }
    try {
      for (final Future<List<DettaglioVerbale>> future :
          ex.invokeAll(tasks, TIMEOUT_THREAD, TimeUnit.MINUTES)) {
        final List<DettaglioVerbale> res = future.get();
        log.debug("CodaThread --- ricevuti verbali:" + res.size());
        nProcessati = nProcessati + res.size();
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("ExecutionException --- run errore nei thread", e);
      ex.shutdown();
    }
    log.debug("Totale processati:" + nProcessati);
    log.debug("FINE popolamappa " + LocalDateTime.now());
    return datiRichiestaIstanzaPl;
  }

  @Override
  public DatiRichiestaIstanzaPl popolaMappaUnicoServizio(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, Utente utente)
      throws BusinessException, ApiException, IOException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInPopolaMappaUnicoServizio(instance, datiRichiestaIstanzaPl, utente);
  }

  private DatiRichiestaIstanzaPl closeConnectionInPopolaMappaUnicoServizio(
      ServiceLocatorLivelloUno instance,
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl,
      Utente utente) {
    try {
      log.debug("INIZIO popolamappa " + LocalDateTime.now());
      List<Verbale> listaVerbali = datiRichiestaIstanzaPl.getListTuttiVerbali();
      DatiGeneraListaDettagliVerbali datiInput = new DatiGeneraListaDettagliVerbali();
      datiInput.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
      List<NumeroProtocolloVerbale> listaProtocolliVerbali = new ArrayList<>();
      for (Verbale verbale : listaVerbali) {
        NumeroProtocolloVerbale numeroProtocollo = new NumeroProtocolloVerbale();
        numeroProtocollo.setNumeroProtocollo(verbale.getNumeroProtocollo());
        listaProtocolliVerbali.add(numeroProtocollo);
      }
      datiInput.setListaProtocolliVerbali(listaProtocolliVerbali);
      DettaglioVerbaleCollection dettaglioVerbali =
          instance.getApiVerbaliContravvenzioni().listaDettagliVerbali(datiInput);
      for (DettaglioVerbale dettaglioVerbale : dettaglioVerbali.getVerbali()) {
        datiRichiestaIstanzaPl.addMapDettaglioVerbale(
            dettaglioVerbale.getNumeroProtocollo(), utente, dettaglioVerbale);
      }
      log.debug("FINE popolamappa " + LocalDateTime.now());
      return datiRichiestaIstanzaPl;

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ServiziMieIstanzeImpl"));
    } finally {

      instance.closeConnection();
    }
  }

  private List<DettaglioVerbale> processaListaDaCoda(
      List<Verbale> sottoInsieme,
      String thread,
      Utente utente,
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    List<DettaglioVerbale> lista = new ArrayList<>();
    for (Verbale verbale : sottoInsieme) {
      DettaglioVerbale dettaglioVerbale =
          datiRichiestaIstanzaPl.getMapDettaglioVerbale(verbale.getNumeroProtocollo(), utente);
      if (dettaglioVerbale == null) {
        try {
          dettaglioVerbale =
              ServiceLocator.getInstance()
                  .getServiziMieiVerbali()
                  .getDettaglioVerbale(verbale.getNumeroProtocollo(), utente);
          datiRichiestaIstanzaPl.addMapDettaglioVerbale(
              verbale.getNumeroProtocollo(), utente, dettaglioVerbale);
        } catch (BusinessException | ApiException | IOException e) {
          log.debug("Errore chiamata WSO2 verbali: ", e);
        }
      }
    }
    return lista;
  }

  private static <T> List<List<T>> suddividiLista(List<T> objs, final int N) {
    return new ArrayList<>(
        IntStream.range(0, objs.size())
            .boxed()
            .collect(
                Collectors.groupingBy(
                    e -> e % N, Collectors.mapping(e -> objs.get(e), Collectors.toList())))
            .values());
  }
}
