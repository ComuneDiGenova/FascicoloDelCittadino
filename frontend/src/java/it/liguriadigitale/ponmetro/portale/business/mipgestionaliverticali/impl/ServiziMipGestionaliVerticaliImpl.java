package it.liguriadigitale.ponmetro.portale.business.mipgestionaliverticali.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.AvvisoPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.DebitoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.RicevutaPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.TipologiaEntrataApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mipgestionaliverticali.service.ServiziMipGestionaliVerticaliService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobali;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobaliRicevute;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziMipGestionaliVerticaliImpl implements ServiziMipGestionaliVerticaliService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_MIP_VERTICALI =
      "Errore di connessione alle API MIP VERTICALI";

  @Override
  public List<AvvisoPagamento> getListaTipologieEntrateByCf(String cf)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInGetListaTipologieEntrateByCf(instance, cf);
  }

  private List<AvvisoPagamento> closeConnectionInGetListaTipologieEntrateByCf(
      ServiceLocatorLivelloUno instance, String cf) {
    try {
      AvvisoPagamentoApi avvisoPagamentoApi = instance.getAvvisoPagamentoApi();
      return avvisoPagamentoApi.listaAvvisiPersona(cf);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("MIP"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Debito getListaTipologieEntrateNonAttualizzaByCfIuv(String cf, String iuv)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInGetListaTipologieEntrateNonAttualizzaByCfIuv(instance, cf, iuv);
  }

  private Debito closeConnectionInGetListaTipologieEntrateNonAttualizzaByCfIuv(
      ServiceLocatorLivelloUno instance, String cf, String iuv) {
    try {
      DebitoApi debitoApi = instance.getDebitoApi();
      return debitoApi.dettaglioDebitoDaIdFiscaleEIUV(cf, iuv, false);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("MIP"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<RicevutaPagamento> getListaRicevutePagamentoPositivePersonaByCfAndEsito(
      String cf, boolean esito) throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInGetListaRicevutePagamentoPositivePersonaByCfAndEsito(
        instance, cf, esito);
  }

  private List<RicevutaPagamento>
      closeConnectionInGetListaRicevutePagamentoPositivePersonaByCfAndEsito(
          ServiceLocatorLivelloUno instance, String cf, boolean esito) {

    try {
      RicevutaPagamentoApi ricevutaPagamentoApi = instance.getRicevutaPagamentoApi();
      return ricevutaPagamentoApi.listaRicevutePagamentoPersona(cf, esito);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("MIP"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<RicevutaPagamento> getListaRicevutePagamentoPositivePersonaByCf(String cf)
      throws BusinessException, ApiException {
    return getListaRicevutePagamentoPositivePersonaByCfAndEsito(cf, true);
  }

  @Override
  public List<RicevutaPagamento> getListaRicevutePagamentoNegativePersonaByCf(String cf)
      throws BusinessException, ApiException {
    return getListaRicevutePagamentoPositivePersonaByCfAndEsito(cf, false);
  }

  @Override
  public List<TipologiaEntrata> getListaTipologieEntrate() throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInGetListaTipologieEntrate(instance);
  }

  private List<TipologiaEntrata> closeConnectionInGetListaTipologieEntrate(
      ServiceLocatorLivelloUno instance) {
    try {
      TipologiaEntrataApi tipologiaEntrataApi = instance.getTipologiaEntrataApi();
      return tipologiaEntrataApi.listaTipologieEntrate(-1, -1);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("MIP"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<AvvisoPagamento> getListaAvvisiPagamentiPerCf(Utente utente)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      List<AvvisoPagamento> listaAvvisoPagamento =
          instance.getAvvisoPagamentoApi().listaAvvisiPersona(utente.getCodiceFiscaleOperatore());
      log.debug("CP listaAvvisoPagamento = " + listaAvvisoPagamento);
      return listaAvvisoPagamento;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getListaAvvisiPagamentiPerCf: errore API MIP verticali:",
          e);
      throw new BusinessException(ERRORE_API_MIP_VERTICALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getListaAvvisiPagamentiPerCf: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMipGestionaliVerticaliImpl -- getListaAvvisiPagamentiPerCf: errore durante la chiamata delle API MIP verticale ",
          e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagamento getAvvisoPagamentoDaCfAnonimoECodiceAvviso(
      String codiceAvviso, boolean pdf, boolean attualizza) throws BusinessException, ApiException {
    AvvisoPagamento avvisoPagamento = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String anonimo = "ANONIMO";
      avvisoPagamento =
          instance
              .getAvvisoPagamentoApi()
              .dettaglioAvvisoDaIdFiscaleECodiceAvviso(anonimo, codiceAvviso, pdf, attualizza);

      return avvisoPagamento;
    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfAnonimoECodiceAvviso: errore API MIP verticali:",
          e);
      throw new BusinessException(ERRORE_API_MIP_VERTICALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfAnonimoECodiceAvviso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfAnonimoECodiceAvviso: errore durante la chiamata delle API MIP verticale ",
          e);
      return avvisoPagamento;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AvvisoPagamento getAvvisoPagamentoDaCfECodiceAvviso(
      Utente utente, String codiceAvviso, boolean pdf, boolean attualizza)
      throws BusinessException, ApiException {

    AvvisoPagamento avvisoPagamento = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      avvisoPagamento =
          instance
              .getAvvisoPagamentoApi()
              .dettaglioAvvisoDaIdFiscaleECodiceAvviso(
                  utente.getCodiceFiscaleOperatore(), codiceAvviso, pdf, attualizza);
      log.debug("CP avvisoPagamento con codice avviso " + avvisoPagamento);

      return avvisoPagamento;
    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfECodiceAvviso: errore API MIP verticali:",
          e);
      throw new BusinessException(ERRORE_API_MIP_VERTICALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfECodiceAvviso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamentoDaCfECodiceAvviso: errore durante la chiamata delle API MIP verticale ",
          e);
      return avvisoPagamento;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Debito getDebitoByCfServizioAnnoNumeroDocumento(
      String cf, String servizio, Long anno, String numeroDocumento, boolean attualizzaPagamento)
      throws BusinessException, ApiException {
    try {
      log.debug("CP getDebitoByCfServizioAnnoNumeroDocumento");
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getDebitoApi()
          .dettaglioDebitoDaIdFiscaleEChiave(
              cf, servizio, anno, numeroDocumento, attualizzaPagamento);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getDebitoByCfServizioAnnoNumeroDocumento: errore WebApplicationException:"
              + e.getMessage());
      return new Debito();
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMipGestionaliVerticaliImpl -- getDebitoByCfServizioAnnoNumeroDocumento: errore durante la chiamata delle API MIP verticale ",
          e);
      return new Debito();
    }
  }

  @Override
  public List<RicevutaPagamento> getRicevutaDaIuv(Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException {
    try {
      log.debug("CP getRicevutaDaIuv " + utente.getCodiceFiscaleOperatore() + " - " + iuv);

      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerIUV(utente.getCodiceFiscaleOperatore(), iuv, esitoPositivo);

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaDaIuv: errore API MIP Verticali:", e);
      throw new BusinessException(ERRORE_API_MIP_VERTICALI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaDaIuv: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public AvvisoPagamento getAvvisoPagamento(
      String cf,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean ottieniPdf,
      boolean attualizza)
      throws BusinessException, ApiException {
    try {
      log.debug("CP getAvvisoPagamento");
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getAvvisoPagamentoApi()
          .dettaglioAvvisoDaIdFiscaleEChiave(
              cf, servizio, anno, numeroDocumento, ottieniPdf, attualizza);

    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamento: errore WebApplicationException:"
              + e.getMessage());
      return new AvvisoPagamento();
    } catch (RuntimeException e) {
      log.debug(
          "ServiziMipGestionaliVerticaliImpl -- getAvvisoPagamento: errore durante la chiamata delle API MIP verticale ",
          e);
      return new AvvisoPagamento();
    }
  }

  @Override
  public List<RicevutaPagamento> getRicevutaDaCodiceAvviso(
      Utente utente, String codiceAvviso, boolean esitoPositivo)
      throws BusinessException, ApiException {
    try {
      log.debug(
          "CP getRicevutaDaCodiceAvviso "
              + utente.getCodiceFiscaleOperatore()
              + " - "
              + codiceAvviso);

      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerCodiceAvviso(
              utente.getCodiceFiscaleOperatore(), codiceAvviso, esitoPositivo);

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaDaCodiceAvviso: errore API MIP Verticali:",
          e);
      // FRR TRY return empty list instead of throw new
      // BusinessException(ERRORE_API_MIP_VERTICALI);
      return new ArrayList<RicevutaPagamento>();
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaDaCodiceAvviso: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public List<RicevutaPagamento> getRicevutaPerChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento, boolean esitoPositivo)
      throws BusinessException, ApiException {
    try {
      log.debug(
          "CP getRicevutaPerChiave "
              + utente.getCodiceFiscaleOperatore()
              + " - "
              + numeroDocumento);

      return ServiceLocatorLivelloUnoMipGlobaliRicevute.getInstance()
          .getRicevutaPagamentoApi()
          .listaRicevutePagamentoPerChiave(
              utente.getCodiceFiscaleOperatore(), servizio, anno, numeroDocumento, esitoPositivo);

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaPerChiave: errore API MIP Verticali:",
          e);

      // FRR TRY return empty list instead of throw new
      // BusinessException(ERRORE_API_MIP_VERTICALI);
      return new ArrayList<RicevutaPagamento>();

    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaPerChiave: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }

  @Override
  public List<RicevutaPagamento> getRicevutaPerBollettinoMensa(
      Utente utente, DatiPagamentiBollettiniRiepilogativiEstesi datiBollettino)
      throws BusinessException, ApiException {
    try {

      log.debug(
          "CP getRicevutaPerBollettinoMensa = "
              + datiBollettino.getIdentificativoDebito()
              + " - "
              + datiBollettino.getIdentificativoDebito().concat("_0"));

      List<RicevutaPagamento> listaRicevutePerBollettinoMensa = new ArrayList<RicevutaPagamento>();

      String servizioScuola = "SCUOLA_RISTO";

      if (datiBollettino.getIuv() != null
          && !datiBollettino.getIuv().isEmpty()
          && !datiBollettino.getIuv().equalsIgnoreCase("-")
          && datiBollettino.getIuv().startsWith("RF")) {
        listaRicevutePerBollettinoMensa = getRicevutaDaIuv(utente, datiBollettino.getIuv(), true);
      } else if (datiBollettino.getCodiceAvviso() != null
          && !datiBollettino.getCodiceAvviso().isEmpty()) {
        listaRicevutePerBollettinoMensa =
            getRicevutaDaCodiceAvviso(utente, datiBollettino.getCodiceAvviso(), true);
      } else {
        listaRicevutePerBollettinoMensa =
            getRicevutaPerChiave(
                utente,
                servizioScuola,
                datiBollettino.getAnnoRiferimento().longValue(),
                datiBollettino.getIdentificativoDebito().concat("_0"),
                true);
      }

      return listaRicevutePerBollettinoMensa;

    } catch (BusinessException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaPerBollettinoMensa: errore API MIP Verticali:",
          e);

      // FRR TRY return empty list instead of throw new
      // BusinessException(ERRORE_API_MIP_VERTICALI);
      return new ArrayList<RicevutaPagamento>();

    } catch (WebApplicationException e) {
      log.error(
          "ServiziMipGestionaliVerticaliImpl -- getRicevutaPerBollettinoMensa: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<RicevutaPagamento>();
    }
  }
}
