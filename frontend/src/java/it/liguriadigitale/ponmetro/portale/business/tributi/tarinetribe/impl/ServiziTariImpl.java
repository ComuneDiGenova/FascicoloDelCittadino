package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoTariNetribe;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariService;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.RataDaPagareTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.Rata;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public class ServiziTariImpl implements ServiziTariService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TARI_NETRIBE =
      "Errore di connessione alle API Tari Netribe";

  @Override
  public List<TARIResult> getSintesiTariAnnoCorrente(String codiceFiscale, Integer anno)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getSintesiTariAnnoCorrente: ");
    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();
    try {

      return instance
          .getApiTariNetribeSintesi()
          .getSintesiTARIAnnoCorrente(codiceFiscale, anno, null);

    } catch (BusinessException e) {
      log.error("ServiziTariImpl -- getSintesiTariAnnoCorrente: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI_NETRIBE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariImpl -- getSintesiTariAnnoCorrente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariImpl -- getSintesiTariAnnoCorrente: errore durante la chiamata delle API TARI Netrive ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("la mia TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getPdfNetribe(String codiceIdentificativoFile)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPdfNetribe: ");
    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();
    try {

      return instance
          .getApiTariDocumentiPdfApi()
          .getAgevolazioneTariffariaTariPdf(codiceIdentificativoFile);

    } catch (BusinessException e) {
      log.error("ServiziTariImpl -- getPdfNetribe: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI_NETRIBE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariImpl -- getPdfNetribe: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariImpl -- getPdfNetribe: errore durante la chiamata delle API TARI Netrive ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("la mia TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DatiFileAllegatoNetribe datiFileAllegatoNetribe(String codiceIdentificativoFile)
      throws BusinessException, ApiException, IOException {
    log.debug("CP datiFileAllegatoNetribe: ");
    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();

    DatiFileAllegatoNetribe datiFileAllegato = new DatiFileAllegatoNetribe();

    try {
      FileAllegato fileNetribe =
          instance
              .getApiTariDocumentiPdfApi()
              .getAgevolazioneTariffariaTariPdf(codiceIdentificativoFile);

      if (LabelFdCUtil.checkIfNotNull(fileNetribe)) {
        datiFileAllegato.setScaricato(true);
        datiFileAllegato.setFileAllegatoNetribe(fileNetribe);
      }

    } catch (BusinessException e) {
      log.error("ServiziTariImpl -- getPdfNetribe: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI_NETRIBE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariImpl -- getPdfNetribe: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariImpl -- getPdfNetribe: errore durante la chiamata delle API TARI Netrive ",
          e);

      String erroreRicevuto = e.getMessage();
      String[] splitted = erroreRicevuto.split(":");
      String messaggioErrore = splitted[splitted.length - 1];

      byte[] messaggioErroreBytes = messaggioErrore.getBytes();

      String utf8EncodedMessaggioErroreBytes =
          new String(messaggioErroreBytes, StandardCharsets.UTF_8);

      datiFileAllegato.setScaricato(false);
      datiFileAllegato.setMessaggioErrore(utf8EncodedMessaggioErroreBytes);

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }

    return datiFileAllegato;
  }

  @Override
  public Map<LocalDate, List<Rata>> getListaRateRaggruppatePerDataScadenza(
      List<Rata> rateizzazione) {
    log.debug("CP rateizzazione = " + rateizzazione);

    Map<LocalDate, List<Rata>> listaRateRaggruppatePerDateScadenza =
        new HashMap<LocalDate, List<Rata>>();

    if (LabelFdCUtil.checkIfNotNull(rateizzazione)) {
      listaRateRaggruppatePerDateScadenza =
          rateizzazione.stream().collect(Collectors.groupingBy(Rata::getDataScadenzaRata));
      log.debug("CP listaRateRaggruppatePerDateScadenza = " + listaRateRaggruppatePerDateScadenza);
    }

    return listaRateRaggruppatePerDateScadenza;
  }

  @Override
  public Rata getRataUnica(List<Rata> rateizzazione, String iuvTotaleDocumento) {
    Comparator<Rata> comparatorDataScadenza =
        Comparator.comparing(Rata::getNumRata, Comparator.nullsFirst(Comparator.naturalOrder()));

    List<Rata> rateizzazioneOrdinatePerNumeroRata =
        rateizzazione.stream().sorted(comparatorDataScadenza).collect(Collectors.toList());

    log.debug("CP rateizzazioneOrdinatePerNumeroRata = " + rateizzazioneOrdinatePerNumeroRata);

    Rata rataUnica = new Rata();

    Double importoTotaleRataUnica =
        rateizzazioneOrdinatePerNumeroRata.stream()
            .map(elem -> elem.getImportoRata())
            .collect(Collectors.summingDouble(Double::doubleValue));

    rataUnica.setDataScadenzaRata(rateizzazioneOrdinatePerNumeroRata.get(0).getDataScadenzaRata());
    rataUnica.setIuvRata(iuvTotaleDocumento);
    rataUnica.setImportoRata(importoTotaleRataUnica);

    return rataUnica;
  }

  @Override
  public Debito getDebitoMIPDaIUV(String cf, String iuv) throws BusinessException, ApiException {
    log.debug("CP getDebitoMIPDaIUV");

    Debito debitoMIP = null;

    try {

      if (PageUtil.isStringValid(iuv)) {
        debitoMIP =
            ServiceLocator.getInstance()
                .getServiziMipVerticali()
                .getListaTipologieEntrateNonAttualizzaByCfIuv(cf, iuv);
      }

    } catch (BusinessException e) {
      log.error("ServiziTariImpl -- getDebitoMIPDaIUV: errore API TARI Netribe:");
      throw new BusinessException(ERRORE_API_TARI_NETRIBE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariImpl -- getDebitoMIPDaIUV: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_NETRIBE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariImpl -- getDebitoMIPDaIUV: errore WebApplicationException:" + e.getMessage());
    }

    return debitoMIP;
  }

  @Override
  public RicevutaPagamento getRicevutaMipDaIUV(Utente utente, String iuv)
      throws BusinessException, ApiException {
    log.debug("CP getRicevutaMipDaIUV");

    RicevutaPagamento ricevutaMIP = null;

    try {

      List<RicevutaPagamento> listaRicevute =
          ServiceLocator.getInstance().getServiziMipVerticali().getRicevutaDaIuv(utente, iuv, true);

      if (LabelFdCUtil.checkIfNotNull(listaRicevute)
          && !LabelFdCUtil.checkEmptyList(listaRicevute)) {
        ricevutaMIP =
            listaRicevute.stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem.getEsitoPagamento())
                            && elem.getEsitoPagamento().equals(EsitoPagamento.OK))
                .findAny()
                .orElse(null);
      }

      return ricevutaMIP;

    } catch (BusinessException e) {
      log.error("ServiziTariImpl -- getRicevutaMipDaIUV: errore API TARI Netribe:");
      throw new BusinessException(ERRORE_API_TARI_NETRIBE);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariImpl -- getRicevutaMipDaIUV: errore durante la chiamata delle API TARI Netribe"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_NETRIBE);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariImpl -- getRicevutaMipDaIUV: errore WebApplicationException:"
              + e.getMessage());

      return ricevutaMIP;
    }
  }

  @Override
  public AbstractLink creaBtnPaga(
      RataDaPagareTariNetribe rataDaPagare, Utente utente, String wicketID) {

    log.debug("CP creaBtnPaga = " + rataDaPagare);

    MipData data = new MipData();
    if (LabelFdCUtil.checkIfNotNull(rataDaPagare)) {
      data = creaMipData(rataDaPagare, utente);
    }
    return creaBottoneMipPagoPA(rataDaPagare, wicketID, data);
  }

  @Override
  public MipData creaMipData(RataDaPagareTariNetribe rataDaPagare, Utente utente) {

    log.debug("CP creaMipData = " + rataDaPagare);

    MipData data = new MipData();

    data.setImportoAvviso(BigDecimal.valueOf(rataDaPagare.getImporto()));
    data.setNumeroDocumento(rataDaPagare.getIuv());
    data.setIdServizio(rataDaPagare.getIdServizio());
    data.setUtente(utente);

    log.debug("CP mip data = " + data);

    return data;
  }

  @Override
  public Link<Void> creaBottoneMipPagoPA(
      RataDaPagareTariNetribe rataDaPagare, String wicketID, MipData data) {
    @SuppressWarnings("rawtypes")
    Link<Void> link =
        new Link<Void>(wicketID) {

          @Override
          public void onClick() {
            PagamentoOnLinePage page = new PagamentoOnLinePage(data);
            setResponsePage(page);
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    return link;
  }

  @Override
  public String getValoreDaDb(String chiave) {
    log.debug("chiave = " + chiave);
    String data = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        data = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante get in tari netribe da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return data;
  }
}
