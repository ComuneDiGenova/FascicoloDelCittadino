package it.liguriadigitale.ponmetro.portale.business.mipGlobali.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.RiepilogoPagamentiPagoPA;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mipGlobali.service.ServiziMipGlobaliService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobali;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobaliRicevute;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicolo;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.RataDebitoMipFascicolo;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.TentativoDiPagamento;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.utilmieipagamenti.UtilMieiPagamenti;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziMipGlobaliImpl implements ServiziMipGlobaliService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_MIP_GLOBALI = "Errore di connessione alle API MIP GLOBALI";

  @Override
  public List<Debito> getRiepilogoPagamentiUtente(Utente utente, boolean flagSoloNonPagati)
      throws BusinessException, ApiException, IOException {
    try {

      LocalDate dataInizio = LocalDate.of(2020, 1, 1);
      LocalDate dataFine = LocalDate.now();

      List<Debito> listaDebito = new ArrayList<Debito>();
      List<Debito> sortedListaDebito = new ArrayList<Debito>();

      RiepilogoPagamentiPagoPA riepilogoPagamentiPagoPA =
          ServiceLocatorLivelloUnoMipGlobali.getInstance()
              .getApiRiepilogoPagamentiPagoPaGlobali()
              .riepilogoUtente(
                  utente.getCodiceFiscaleOperatore(), dataInizio, dataFine, flagSoloNonPagati);

      if (!riepilogoPagamentiPagoPA.getDebiti().isEmpty()) {
        listaDebito = riepilogoPagamentiPagoPA.getDebiti();
        sortedListaDebito =
            listaDebito.stream()
                .sorted(Comparator.comparing(Debito::getAnno).reversed())
                .collect(Collectors.toList());
      }

      return sortedListaDebito;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getRiepilogoPagamentiUtente: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiUtente: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public String getDescrizioneServizio(Utente utente, String servizio)
      throws BusinessException, ApiException {
    try {
      String descrizioneServizio = "";

      TipologiaEntrata tipologiaEntrata =
          ServiceLocatorLivelloUnoMipGlobali.getInstance()
              .getApiTipologiaEntrataGlobali()
              .dettaglioTipologiaEntrata(servizio);
      descrizioneServizio = tipologiaEntrata.getDescrizioneServizio();

      return descrizioneServizio;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getDescrizioneServizio: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getDescrizioneServizio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<TipologiaEntrata> getListaTipologieEntrateServizi()
      throws BusinessException, ApiException {
    try {
      List<TipologiaEntrata> listaTipologieEntrate =
          ServiceLocatorLivelloUno.getInstance()
              .getApiTipologiaEntrataGlobali()
              .listaTipologieEntrate(0, 100);

      return listaTipologieEntrate;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaTipologieEntrateServizi: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaTipologieEntrateServizi: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<TipologiaEntrata> getListaTipologieEntrateServiziFiltrata(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      List<Debito> listaDebitiUtente = getRiepilogoPagamentiUtente(utente, false);
      List<String> listaServiziUtente =
          listaDebitiUtente.stream().map(Debito::getServizio).collect(Collectors.toList());
      List<String> listaServiziUtenteDistinct =
          listaServiziUtente.stream().distinct().collect(Collectors.toList());

      List<TipologiaEntrata> listaTipologieEntrate = getListaTipologieEntrateServizi();

      List<TipologiaEntrata> listaTipologieEntrataFiltrate = new ArrayList<TipologiaEntrata>();

      listaTipologieEntrataFiltrate =
          listaTipologieEntrate.stream()
              .filter(elem -> listaServiziUtenteDistinct.contains(elem.getNomeServizio()))
              .collect(Collectors.toList());

      return listaTipologieEntrataFiltrate;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaTipologieEntrateServiziFiltrata: errore API MIP GLOBALI:",
          e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaTipologieEntrateServiziFiltrata: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<TipologiaEntrata>
      getListaTipologieEntrateServiziFiltrataSenzaVerbaliSenzaTariEngSenzaTariNetribe(Utente utente)
          throws BusinessException, ApiException, IOException {

    log.debug("CP getListaTipologieEntrateServiziFiltrataSenzaVerbali");
    List<TipologiaEntrata> listaTipologie = getListaTipologieEntrateServiziFiltrata(utente);
    List<TipologiaEntrata> listaTipologieSenzaVerbali = new ArrayList<TipologiaEntrata>();

    String servizioVerbali = "SANZIONI_PL";

    String servizioTariEngAvv = "TARI_GERI_AVV_NP";
    String servizioTariEngAcc = "TARI_GERI_ACC_NP";

    String servizioTariNetribeAvv = "TARI_ESPERTA_AVV_NP";

    listaTipologieSenzaVerbali =
        listaTipologie.stream()
            .filter(
                elem ->
                    !elem.getNomeServizio().equalsIgnoreCase(servizioVerbali)
                        && !elem.getNomeServizio().equalsIgnoreCase(servizioTariEngAvv)
                        && !elem.getNomeServizio().equalsIgnoreCase(servizioTariEngAcc)
                        && !elem.getNomeServizio().equalsIgnoreCase(servizioTariNetribeAvv))
            .collect(Collectors.toList());

    return listaTipologieSenzaVerbali;
  }

  @SuppressWarnings("unused")
  @Override
  public List<RicevutaPagamento> getListaRicevuteByCF(Utente utente)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPersona(utente.getCodiceFiscaleOperatore(), null);
    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getListaRicevuteByCF: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaRicevuteByCF: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public List<RicevutaPagamento> getListaRicevuteByIUV(
      Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerIUV(utente.getCodiceFiscaleOperatore(), iuv, esitoPositivo);
    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getListaRicevuteByIUV: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaRicevuteByIUV: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public RicevutaPagamento getRicevutaByIUVConEsitoOk(
      Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException {
    try {

      List<RicevutaPagamento> listaRicevuteByIuv =
          getListaRicevuteByIUV(utente, iuv, esitoPositivo);

      RicevutaPagamento ricevutaPagamento =
          listaRicevuteByIuv.stream()
              .filter(
                  elem ->
                      elem.getIuv().equalsIgnoreCase(iuv)
                          && elem.getEsitoPagamento() != null
                          && elem.getEsitoPagamento()
                              .toString()
                              .equalsIgnoreCase(EsitoPagamento.OK.toString()))
              .findAny()
              .orElse(new RicevutaPagamento());

      return ricevutaPagamento;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getRicevutaByIUVConEsitoOk: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRicevutaByIUVConEsitoOk: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @SuppressWarnings("unused")
  @Override
  public List<RicevutaPagamento> getListaRicevuteByCodiceAvviso(
      Utente utente, String codiceAvviso, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerCodiceAvviso(
              utente.getCodiceFiscaleOperatore(), codiceAvviso, esitoPositivo);
    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaRicevuteByCodiceAvviso: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaRicevuteByCodiceAvviso: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @SuppressWarnings("unused")
  @Override
  public List<RicevutaPagamento> getListaRicevuteByChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerChiave(
              utente.getCodiceFiscaleOperatore(), servizio, anno, numeroDocumento, null);
    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getListaRicevuteByChiave: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaRicevuteByChiave: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @Override
  public it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito
      getDebitoDaPagareDaCodiceAvviso(Utente utente, String codiceAvviso, boolean attualizza)
          throws BusinessException, ApiException, IOException {
    try {
      it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito dettaglioDebito =
          ServiceLocatorLivelloUnoMipGlobali.getInstance()
              .getDebitoApi()
              .dettaglioDebitoDaIdFiscaleECodiceAvviso(
                  utente.getCodiceFiscaleOperatore(), codiceAvviso, attualizza);

      return dettaglioDebito;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getDebitoDaPagareDaCodiceAvviso: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getDebitoDaPagareDaCodiceAvviso: errore WebApplicationException:"
              + e.getMessage());
      return new it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito();
    }
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali> getRiepilogoPagamentiDatiGeneraliUtenteFascicolo(
      Utente utente, boolean flagSoloNonPagati)
      throws BusinessException, ApiException, IOException {
    try {
      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGenerali =
          new ArrayList<DebitoMipFascicoloDatiGenerali>();

      List<Debito> listaDebitiMip = getRiepilogoPagamentiUtente(utente, flagSoloNonPagati);

      for (Debito debitoMip : listaDebitiMip) {
        String numeroDocumento = debitoMip.getNumeroDocumento();
        int indexOfUnderScore = numeroDocumento.lastIndexOf('_');
        String numeroDebito = numeroDocumento.substring(0, indexOfUnderScore);
        String numeroRata =
            numeroDocumento.substring(indexOfUnderScore + 1, numeroDocumento.length());
        Long annoDebito = debitoMip.getAnno();

        DebitoMipFascicoloDatiGenerali debitoDatiGenerali = new DebitoMipFascicoloDatiGenerali();
        RataDebitoMipFascicolo rataDebito = new RataDebitoMipFascicolo();

        if (listaDebitiConDatiGenerali.stream()
            .anyMatch(
                elem ->
                    elem.getNumeroDebito().equalsIgnoreCase(numeroDebito)
                        && elem.getAnno().equals(annoDebito))) {
          log.debug("CP debito gia presente ");

          DebitoMipFascicoloDatiGenerali debitoGeneralePresente =
              listaDebitiConDatiGenerali.stream()
                  .filter(elem -> elem.getNumeroDebito().equalsIgnoreCase(numeroDebito))
                  .findAny()
                  .orElse(new DebitoMipFascicoloDatiGenerali());

          List<RataDebitoMipFascicolo> listaRatePresenti =
              debitoGeneralePresente.getListaRateDebito();
          if (debitoMip.getCodiceAvviso() != null) {
            rataDebito.setCodiceAvviso(debitoMip.getCodiceAvviso());
          }
          rataDebito.setIuv(debitoMip.getIuv());
          boolean esitoRata = false;
          if (debitoMip.getEsitoPagamento() != null
              && debitoMip
                  .getEsitoPagamento()
                  .toString()
                  .equalsIgnoreCase(EsitoPagamento.OK.toString())) {
            esitoRata = true;
          }
          rataDebito.setServizio(debitoMip.getServizio());
          rataDebito.setEsitoRata(esitoRata);
          rataDebito.setNumeroDebito(numeroDebito);
          rataDebito.setNumeroRata(numeroRata);
          rataDebito.setDataCreazioneRata(debitoMip.getDataCreazione());

          List<Boolean> listaEsitiPresenti = debitoGeneralePresente.getEsitiRate();
          listaEsitiPresenti.add(esitoRata);
          debitoGeneralePresente.setEsitiRate(listaEsitiPresenti);

          listaRatePresenti.add(rataDebito);

          debitoGeneralePresente.setListaRateDebito(listaRatePresenti);

          debitoDatiGenerali = debitoGeneralePresente;

        } else {
          log.debug("CP debito non presente");

          debitoDatiGenerali.setAnno(debitoMip.getAnno());
          debitoDatiGenerali.setCausale(debitoMip.getCausale());
          debitoDatiGenerali.setDebitore(debitoMip.getDebitore());
          debitoDatiGenerali.setNumeroDebito(numeroDebito);
          debitoDatiGenerali.setServizio(debitoMip.getServizio());

          debitoDatiGenerali.setImportoDaPagare(debitoMip.getImportoDaPagare());
          debitoDatiGenerali.setImportoPagato(debitoMip.getImportoPagato());

          debitoDatiGenerali.setIuv(debitoMip.getIuv());

          if (debitoMip.getDataCreazione() != null) {
            debitoDatiGenerali.setDataCreazione(debitoMip.getDataCreazione());
          }

          List<RataDebitoMipFascicolo> listaRate = new ArrayList<RataDebitoMipFascicolo>();

          if (debitoMip.getCodiceAvviso() != null) {
            rataDebito.setCodiceAvviso(debitoMip.getCodiceAvviso());
          }
          rataDebito.setIuv(debitoMip.getIuv());
          boolean esitoRata = false;
          if (debitoMip.getEsitoPagamento() != null
              && debitoMip
                  .getEsitoPagamento()
                  .toString()
                  .equalsIgnoreCase(EsitoPagamento.OK.toString())) {
            esitoRata = true;
          }
          rataDebito.setServizio(debitoMip.getServizio());
          rataDebito.setEsitoRata(esitoRata);
          rataDebito.setNumeroDebito(numeroDebito);
          rataDebito.setNumeroRata(numeroRata);
          rataDebito.setDataCreazioneRata(debitoMip.getDataCreazione());

          List<Boolean> listaEsiti = new ArrayList<Boolean>();
          listaEsiti.add(esitoRata);
          debitoDatiGenerali.setEsitiRate(listaEsiti);

          listaRate.add(rataDebito);
          debitoDatiGenerali.setListaRateDebito(listaRate);

          listaDebitiConDatiGenerali.add(debitoDatiGenerali);
        }

        boolean esitoGeneraleDebito = false;
        if (debitoDatiGenerali.getEsitiRate().contains(false)) {
          esitoGeneraleDebito = false;
        } else {
          esitoGeneraleDebito = true;
        }
        debitoDatiGenerali.setEsitoDebito(esitoGeneraleDebito);
      }

      return listaDebitiConDatiGenerali;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicolo: errore API MIP GLOBALI:",
          e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicolo: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali>
      getRiepilogoPagamentiDatiGeneraliUtenteFascicoloOrdinatiPerDataCreazione(
          Utente utente, boolean flagSoloNonPagati)
          throws BusinessException, ApiException, IOException {
    try {
      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGenerali =
          getRiepilogoPagamentiDatiGeneraliUtenteFascicolo(utente, flagSoloNonPagati);
      List<DebitoMipFascicoloDatiGenerali> listaDebitiOrdinatiPerDataCreazione =
          new ArrayList<DebitoMipFascicoloDatiGenerali>();

      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGeneraliConDataCreazione =
          listaDebitiConDatiGenerali.stream()
              .filter(elem -> elem.getDataCreazione() != null)
              .collect(Collectors.toList());
      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGeneraliSenzaDataCreazione =
          listaDebitiConDatiGenerali.stream()
              .filter(elem -> elem.getDataCreazione() == null)
              .collect(Collectors.toList());

      listaDebitiOrdinatiPerDataCreazione =
          listaDebitiConDatiGeneraliConDataCreazione.stream()
              .sorted(
                  Comparator.nullsLast(
                          Comparator.comparing(DebitoMipFascicoloDatiGenerali::getDataCreazione))
                      .reversed())
              .collect(Collectors.toList());

      listaDebitiOrdinatiPerDataCreazione.addAll(listaDebitiConDatiGeneraliSenzaDataCreazione);

      listaDebitiOrdinatiPerDataCreazione =
          listaDebitiOrdinatiPerDataCreazione.stream()
              .sorted(
                  Comparator.nullsLast(
                          Comparator.comparing(DebitoMipFascicoloDatiGenerali::getAnno))
                      .reversed())
              .collect(Collectors.toList());

      return listaDebitiOrdinatiPerDataCreazione;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicoloOrdinatiPerDataCreazione: errore API MIP GLOBALI:",
          e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicoloOrdinatiPerDataCreazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali>
      getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltrati(
          Utente utente, boolean flagSoloNonPagati)
          throws BusinessException, ApiException, IOException {
    try {

      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGenerali =
          getRiepilogoPagamentiDatiGeneraliUtenteFascicoloOrdinatiPerDataCreazione(
              utente, flagSoloNonPagati);

      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGeneraliFiltrati =
          new ArrayList<DebitoMipFascicoloDatiGenerali>();

      if (flagSoloNonPagati) {
        listaDebitiConDatiGeneraliFiltrati =
            listaDebitiConDatiGenerali.stream()
                .filter(elem -> !elem.isEsitoDebito())
                .collect(Collectors.toList());
      } else {
        listaDebitiConDatiGeneraliFiltrati = listaDebitiConDatiGenerali;
      }
      return listaDebitiConDatiGeneraliFiltrati;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltrati: errore API MIP GLOBALI:",
          e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltrati: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali>
      getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltratiSenzaVerbaliSenzaTariEngSenzaTriNetribe(
          Utente utente, boolean flagSoloNonPagati)
          throws BusinessException, ApiException, IOException {

    List<DebitoMipFascicoloDatiGenerali> listaDebitiMip =
        getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltrati(utente, flagSoloNonPagati);
    List<DebitoMipFascicoloDatiGenerali> listaDebitoMipSenzaVerbaliSenzaTariEng =
        new ArrayList<DebitoMipFascicoloDatiGenerali>();

    String servizioVerbali = "SANZIONI_PL";

    String servizioTariEng = "TARI_GERI_AVV_NP";

    String servizioTariNetribeAvv = "TARI_ESPERTA_AVV_NP";

    listaDebitoMipSenzaVerbaliSenzaTariEng =
        listaDebitiMip.stream()
            .filter(
                elem ->
                    !elem.getServizio().equalsIgnoreCase(servizioVerbali)
                        && !elem.getServizio().equalsIgnoreCase(servizioTariEng)
                        && !elem.getServizio().equalsIgnoreCase(servizioTariNetribeAvv))
            .collect(Collectors.toList());

    return listaDebitoMipSenzaVerbaliSenzaTariEng;
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali> getListaDebitiGeneraliPerServizio(
      Utente utente, TipologiaEntrata tipologiaEntrata)
      throws BusinessException, ApiException, IOException {
    try {

      List<DebitoMipFascicoloDatiGenerali> listaDebiti =
          new ArrayList<DebitoMipFascicoloDatiGenerali>();

      LocalDate dataInizio = LocalDate.of(2020, 1, 1);
      LocalDate dataFine = LocalDate.now();
      String nomeServizio = tipologiaEntrata.getNomeServizio();

      List<Debito> listaDebitiMip =
          ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
              .getApiDebitoGlobali()
              .listaDebitiPersonaPerServizio(
                  utente.getCodiceFiscaleOperatore(), nomeServizio, dataInizio, dataFine);

      for (Debito debitoMip : listaDebitiMip) {

        String numeroDocumento = debitoMip.getNumeroDocumento();
        int indexOfUnderScore = numeroDocumento.lastIndexOf('_');
        String numeroDebito = numeroDocumento.substring(0, indexOfUnderScore);
        String numeroRata =
            numeroDocumento.substring(indexOfUnderScore + 1, numeroDocumento.length());

        Long annoDebito = debitoMip.getAnno();

        DebitoMipFascicoloDatiGenerali debitoDatiGenerali = new DebitoMipFascicoloDatiGenerali();
        RataDebitoMipFascicolo rataDebito = new RataDebitoMipFascicolo();

        if (listaDebiti.stream()
            .anyMatch(
                elem ->
                    elem.getNumeroDebito().equalsIgnoreCase(numeroDebito)
                        && elem.getAnno().equals(annoDebito))) {
          log.debug("CP debito gia presente ");

          DebitoMipFascicoloDatiGenerali debitoGeneralePresente =
              listaDebiti.stream()
                  .filter(elem -> elem.getNumeroDebito().equalsIgnoreCase(numeroDebito))
                  .findAny()
                  .orElse(new DebitoMipFascicoloDatiGenerali());

          List<RataDebitoMipFascicolo> listaRatePresenti =
              debitoGeneralePresente.getListaRateDebito();
          if (debitoMip.getCodiceAvviso() != null) {
            rataDebito.setCodiceAvviso(debitoMip.getCodiceAvviso());
          }
          rataDebito.setIuv(debitoMip.getIuv());
          boolean esitoRata = false;
          if (debitoMip.getEsitoPagamento() != null
              && debitoMip
                  .getEsitoPagamento()
                  .toString()
                  .equalsIgnoreCase(EsitoPagamento.OK.toString())) {
            esitoRata = true;
          }
          rataDebito.setEsitoRata(esitoRata);
          rataDebito.setNumeroDebito(numeroDebito);
          rataDebito.setNumeroRata(numeroRata);
          rataDebito.setDataCreazioneRata(debitoMip.getDataCreazione());

          List<Boolean> listaEsitiPresenti = debitoGeneralePresente.getEsitiRate();
          listaEsitiPresenti.add(esitoRata);
          debitoGeneralePresente.setEsitiRate(listaEsitiPresenti);

          listaRatePresenti.add(rataDebito);

          debitoGeneralePresente.setListaRateDebito(listaRatePresenti);

          debitoDatiGenerali = debitoGeneralePresente;

        } else {
          log.debug("CP debito non presente");

          debitoDatiGenerali.setAnno(debitoMip.getAnno());
          debitoDatiGenerali.setCausale(debitoMip.getCausale());
          debitoDatiGenerali.setDebitore(debitoMip.getDebitore());
          debitoDatiGenerali.setNumeroDebito(numeroDebito);
          debitoDatiGenerali.setServizio(debitoMip.getServizio());

          debitoDatiGenerali.setImportoDaPagare(debitoMip.getImportoDaPagare());
          debitoDatiGenerali.setImportoPagato(debitoMip.getImportoPagato());

          debitoDatiGenerali.setIuv(debitoMip.getIuv());

          if (debitoMip.getDataCreazione() != null) {
            debitoDatiGenerali.setDataCreazione(debitoMip.getDataCreazione());
          }

          List<RataDebitoMipFascicolo> listaRate = new ArrayList<RataDebitoMipFascicolo>();

          if (debitoMip.getCodiceAvviso() != null) {
            rataDebito.setCodiceAvviso(debitoMip.getCodiceAvviso());
          }
          rataDebito.setIuv(debitoMip.getIuv());
          boolean esitoRata = false;
          if (debitoMip.getEsitoPagamento() != null
              && debitoMip
                  .getEsitoPagamento()
                  .toString()
                  .equalsIgnoreCase(EsitoPagamento.OK.toString())) {
            esitoRata = true;
          }
          rataDebito.setEsitoRata(esitoRata);
          rataDebito.setNumeroDebito(numeroDebito);
          rataDebito.setNumeroRata(numeroRata);
          rataDebito.setDataCreazioneRata(debitoMip.getDataCreazione());

          List<Boolean> listaEsiti = new ArrayList<Boolean>();
          listaEsiti.add(esitoRata);
          debitoDatiGenerali.setEsitiRate(listaEsiti);

          listaRate.add(rataDebito);
          debitoDatiGenerali.setListaRateDebito(listaRate);

          listaDebiti.add(debitoDatiGenerali);
        }

        boolean esitoGeneraleDebito = false;
        if (debitoDatiGenerali.getEsitiRate().contains(false)) {
          esitoGeneraleDebito = false;
        } else {
          esitoGeneraleDebito = true;
        }
        debitoDatiGenerali.setEsitoDebito(esitoGeneraleDebito);
      }

      return listaDebiti;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaDebitiPersonaPerServizio: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaDebitiPersonaPerServizio: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<DebitoMipFascicoloDatiGenerali>();
    }
  }

  @Override
  public List<DebitoMipFascicoloDatiGenerali>
      getListaDebitiGeneraliPerServizioOrdinatiPerDataCreazione(
          Utente utente, TipologiaEntrata tipologiaEntrata)
          throws BusinessException, ApiException, IOException {
    try {
      List<DebitoMipFascicoloDatiGenerali> listaDebiti =
          getListaDebitiGeneraliPerServizio(utente, tipologiaEntrata);

      List<DebitoMipFascicoloDatiGenerali> listaDebitiOrdinatiPerDataCreazione =
          new ArrayList<DebitoMipFascicoloDatiGenerali>();

      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGeneraliConDataCreazione =
          listaDebiti.stream()
              .filter(elem -> elem.getDataCreazione() != null)
              .collect(Collectors.toList());
      List<DebitoMipFascicoloDatiGenerali> listaDebitiConDatiGeneraliSenzaDataCreazione =
          listaDebiti.stream()
              .filter(elem -> elem.getDataCreazione() == null)
              .collect(Collectors.toList());

      listaDebitiOrdinatiPerDataCreazione =
          listaDebitiConDatiGeneraliConDataCreazione.stream()
              .sorted(
                  Comparator.nullsLast(
                          Comparator.comparing(DebitoMipFascicoloDatiGenerali::getDataCreazione))
                      .reversed())
              .collect(Collectors.toList());

      listaDebitiOrdinatiPerDataCreazione.addAll(listaDebitiConDatiGeneraliSenzaDataCreazione);

      listaDebitiOrdinatiPerDataCreazione =
          listaDebitiOrdinatiPerDataCreazione.stream()
              .sorted(
                  Comparator.nullsLast(
                          Comparator.comparing(DebitoMipFascicoloDatiGenerali::getAnno))
                      .reversed())
              .collect(Collectors.toList());

      return listaDebitiOrdinatiPerDataCreazione;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaDebitiGeneraliPerServizioOrdinatiPerDataCreazione: errore API MIP GLOBALI:",
          e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getListaDebitiGeneraliPerServizioOrdinatiPerDataCreazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public DebitoMipFascicolo getDebitoMipFascicolo(
      Utente utente, DebitoMipFascicoloDatiGenerali debitoGenerale)
      throws BusinessException, ApiException, IOException {
    try {

      DebitoMipFascicolo debitoMipFascicolo = new DebitoMipFascicolo();

      debitoMipFascicolo.setServizio(debitoGenerale.getServizio());
      debitoMipFascicolo.setNumeroDebito(debitoGenerale.getNumeroDebito());
      debitoMipFascicolo.setAnno(debitoGenerale.getAnno());
      debitoMipFascicolo.setDebitore(debitoGenerale.getDebitore());
      debitoMipFascicolo.setCausale(debitoGenerale.getCausale());
      debitoMipFascicolo.setStatoDebito(debitoGenerale.isEsitoDebito());

      List<TentativoDiPagamento> listaPagamenti = new ArrayList<TentativoDiPagamento>();

      for (RataDebitoMipFascicolo rata : debitoGenerale.getListaRateDebito()) {
        TentativoDiPagamento tentativoDiPagamento = new TentativoDiPagamento();

        if (rata.isEsitoRata()) {
          // ricevuta

          boolean esitoPositivo = true;

          List<RicevutaPagamento> listaRicevute =
              ServiceLocator.getInstance()
                  .getServiziMipGlobali()
                  .getTutteRicevute(
                      utente,
                      rata.getIuv(),
                      rata.getCodiceAvviso(),
                      rata.getServizio(),
                      debitoGenerale.getAnno(),
                      rata.getNumeroDebito().concat(rata.getNumeroRata()),
                      esitoPositivo);
          RicevutaPagamento ricevutaPagamento =
              listaRicevute.stream()
                  .filter(
                      elem ->
                          elem.getNumeroDocumento()
                              .equalsIgnoreCase(
                                  rata.getNumeroDebito().concat("_").concat(rata.getNumeroRata())))
                  .findAny()
                  .orElse(new RicevutaPagamento());

          tentativoDiPagamento.setServizio(rata.getServizio());
          tentativoDiPagamento.setNumeroDocumento(rata.getNumeroDebito());
          tentativoDiPagamento.setRata(rata.getNumeroRata());

          if (ricevutaPagamento.getIuv() != null) {
            tentativoDiPagamento.setIuv(ricevutaPagamento.getIuv());
          }
          if (ricevutaPagamento.getCodiceAvviso() != null) {
            tentativoDiPagamento.setCodiceAvviso(ricevutaPagamento.getCodiceAvviso());
          }
          boolean esitoRata = false;
          if (ricevutaPagamento.getEsitoPagamento() != null
              && ricevutaPagamento.getEsitoPagamento().toString().equalsIgnoreCase("OK")) {
            esitoRata = true;
          }
          tentativoDiPagamento.setEsitoPagamento(esitoRata);
          tentativoDiPagamento.setImportoDaPagare(0.0);

          if (ricevutaPagamento.getImporto() != null) {
            tentativoDiPagamento.setImportoPagato(ricevutaPagamento.getImporto());
          }
          tentativoDiPagamento.setDataCreazione(rata.getDataCreazioneRata());

          if (ricevutaPagamento.getDataPagamento() != null) {
            tentativoDiPagamento.setDataPagamento(ricevutaPagamento.getDataPagamento());
          }
          tentativoDiPagamento.setAttualizzato("");

          if (ricevutaPagamento.getPdfRicevuta() != null
              && ricevutaPagamento.getPdfRicevuta().getNomeFile() != null) {
            tentativoDiPagamento.setNomeRicevuta(ricevutaPagamento.getPdfRicevuta().getNomeFile());
          }

          if (ricevutaPagamento.getPdfRicevuta() != null
              && ricevutaPagamento.getPdfRicevuta().getFile() != null) {
            tentativoDiPagamento.setPdfRicevuta(ricevutaPagamento.getPdfRicevuta().getFile());
          }

        } else {
          // debito

          if (rata.getCodiceAvviso() != null && !rata.getCodiceAvviso().isEmpty()) {

            boolean attualizza = true;

            it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito dettaglioDebito =
                getDebitoDaPagareDaCodiceAvviso(utente, rata.getCodiceAvviso(), attualizza);

            tentativoDiPagamento.setServizio(rata.getServizio());
            tentativoDiPagamento.setNumeroDocumento(rata.getNumeroDebito());
            tentativoDiPagamento.setRata(rata.getNumeroRata());
            tentativoDiPagamento.setIuv(dettaglioDebito.getIuv());
            if (dettaglioDebito.getCodiceAvviso() != null) {
              tentativoDiPagamento.setCodiceAvviso(dettaglioDebito.getCodiceAvviso());
            }
            boolean esitoRata = false;
            if (dettaglioDebito.getEsitoPagamento() != null
                && dettaglioDebito.getEsitoPagamento().toString().equalsIgnoreCase("OK")) {
              esitoRata = true;
            }
            tentativoDiPagamento.setEsitoPagamento(esitoRata);
            tentativoDiPagamento.setImportoDaPagare(dettaglioDebito.getImportoDaPagare());
            tentativoDiPagamento.setImportoPagato(dettaglioDebito.getImportoPagato());
            tentativoDiPagamento.setDataCreazione(dettaglioDebito.getDataCreazione());
            // tentativoDiPagamento.setDataPagamento();
            if (dettaglioDebito.getAttualizzato() != null) {
              tentativoDiPagamento.setAttualizzato(dettaglioDebito.getAttualizzato().toString());
            }
            // tentativoDiPagamento.setNomeRicevuta();
            // tentativoDiPagamento.setPdfRicevuta();

            tentativoDiPagamento.setAnno(dettaglioDebito.getAnno());

          } else {
            /* Questa parte e' per SDD */

            if (UtilMieiPagamenti.checkINotNull(rata.getIuv())
                && rata.getIuv().equalsIgnoreCase("-")) {
              debitoMipFascicolo.setIuv(rata.getIuv());
              debitoMipFascicolo.setImportoTotaleDaPagare(debitoGenerale.getImportoDaPagare());
              debitoMipFascicolo.setImportoTotalePagato(debitoGenerale.getImportoPagato());
            }

            /* Questa parte e' per iuv con RF */
            if (UtilMieiPagamenti.checkINotNull(rata.getIuv()) && rata.getIuv().startsWith("RF")) {

              boolean ottieniPdf = true;
              boolean attualizza = true;
              AvvisoPagamento avvisoPagamento =
                  getAvvisoPagamentoDaChiave(
                      utente,
                      rata.getServizio(),
                      debitoGenerale.getAnno(),
                      rata.getNumeroDebito().concat("_").concat(rata.getNumeroRata()),
                      ottieniPdf,
                      attualizza);

              it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito dettaglioDebito =
                  getDebitoDaPagareDaCodiceAvviso(
                      utente, avvisoPagamento.getCodiceAvviso(), attualizza);

              tentativoDiPagamento.setServizio(rata.getServizio());
              tentativoDiPagamento.setNumeroDocumento(rata.getNumeroDebito());
              tentativoDiPagamento.setRata(rata.getNumeroRata());
              if (avvisoPagamento.getCodiceAvviso() != null) {
                tentativoDiPagamento.setCodiceAvviso(avvisoPagamento.getCodiceAvviso());
                String iuvCalcolatoDaCodiceAvviso =
                    avvisoPagamento
                        .getCodiceAvviso()
                        .substring(3, avvisoPagamento.getCodiceAvviso().length());
                tentativoDiPagamento.setIuv(iuvCalcolatoDaCodiceAvviso);
              }
              boolean esitoRata = false;
              if (dettaglioDebito.getEsitoPagamento() != null
                  && dettaglioDebito.getEsitoPagamento().toString().equalsIgnoreCase("OK")) {
                esitoRata = true;
              }
              tentativoDiPagamento.setEsitoPagamento(esitoRata);
              tentativoDiPagamento.setImportoDaPagare(dettaglioDebito.getImportoDaPagare());
              tentativoDiPagamento.setImportoPagato(dettaglioDebito.getImportoPagato());
              tentativoDiPagamento.setDataCreazione(dettaglioDebito.getDataCreazione());
              if (dettaglioDebito.getAttualizzato() != null) {
                tentativoDiPagamento.setAttualizzato(dettaglioDebito.getAttualizzato().toString());
              }

              tentativoDiPagamento.setAnno(dettaglioDebito.getAnno());
            }
          }
        }

        listaPagamenti.add(tentativoDiPagamento);
      }

      debitoMipFascicolo.setTentativiDiPagamento(listaPagamenti);

      return debitoMipFascicolo;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getDebitoMipFascicolo: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getDebitoMipFascicolo: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    }
  }

  @Override
  public AvvisoPagamento getAvvisoPagamento(
      Utente utente, String codiceAvviso, boolean ottieniPdf, boolean attualizza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      AvvisoPagamento avvisoPagamento = new AvvisoPagamento();
      if (codiceAvviso != null) {
        avvisoPagamento =
            instance
                .getAvvisoPagamentoApi()
                .dettaglioAvvisoDaIdFiscaleECodiceAvviso(
                    utente.getCodiceFiscaleOperatore(), codiceAvviso, true, true);
      }
      return avvisoPagamento;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getAvvisoPagamento: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getAvvisoPagamento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagamento getAvvisoPagamentoDaChiave(
      Utente utente,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean ottieniPdf,
      boolean attualizza)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("CP getAvvisoPagamentoDaChiave");
      AvvisoPagamento avvisoPagamento = new AvvisoPagamento();
      avvisoPagamento =
          instance
              .getAvvisoPagamentoApi()
              .dettaglioAvvisoDaIdFiscaleEChiave(
                  utente.getCodiceFiscaleOperatore(),
                  servizio,
                  anno,
                  numeroDocumento,
                  ottieniPdf,
                  attualizza);
      return avvisoPagamento;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getAvvisoPagamentoDaChiave: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getAvvisoPagamentoDaChiave: errore WebApplicationException:"
              + e.getMessage());
      return new AvvisoPagamento();
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<RicevutaPagamento> getTutteRicevute(
      Utente utente,
      String iuv,
      String codiceAvviso,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean esitoPositivo)
      throws BusinessException, ApiException, IOException {
    try {
      List<RicevutaPagamento> listaRicevute = new ArrayList<RicevutaPagamento>();

      if (iuv != null && !iuv.isEmpty() && !iuv.equalsIgnoreCase("-") && iuv.startsWith("RF")) {
        listaRicevute = getListaRicevuteByIUV(utente, iuv, esitoPositivo);
      } else if (codiceAvviso != null && !codiceAvviso.isEmpty()) {
        listaRicevute = getListaRicevuteByCodiceAvviso(utente, codiceAvviso, esitoPositivo);
      } else {
        listaRicevute = getRicevuteByChiave(utente, servizio, anno, numeroDocumento, esitoPositivo);
      }

      return listaRicevute;

    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getTutteRicevute: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getTutteRicevute: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public List<RicevutaPagamento> getRicevuteByChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerChiave(
              utente.getCodiceFiscaleOperatore(), servizio, anno, numeroDocumento, esitoPositivo);
    } catch (BusinessException e) {
      log.error("ServiziMipGlobaliImpl -- getRicevuteByChiave: errore API MIP GLOBALI:", e);
      throw new BusinessException(ERRORE_API_MIP_GLOBALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGlobaliImpl -- getRicevuteByChiave: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }
}
