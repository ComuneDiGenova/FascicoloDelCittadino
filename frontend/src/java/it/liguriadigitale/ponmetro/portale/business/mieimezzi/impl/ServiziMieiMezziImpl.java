package it.liguriadigitale.ponmetro.portale.business.mieimezzi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.TipoVeicolo;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mieimezzi.service.ServiziMieiMezziService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoBollo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiAvvioPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiPagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettagliBolloMezzi;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import it.liguriadigitale.ponmetro.tassaauto.model.EsitoAnnullamentoPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.Pagamenti;
import it.liguriadigitale.ponmetro.tassaauto.model.PagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.VeicoliAttivi;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziMieiMezziImpl implements ServiziMieiMezziService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_VEICOLI = "Errore di connessione alle API Veicolo";

  @Override
  public List<Veicolo> getListaVeicoli(Utente utente)
      throws BusinessException, IOException, ApiException {

    ServiceLocatorLivelloUnoBollo instance = ServiceLocatorLivelloUnoBollo.getInstance();

    List<Veicolo> listaVeicoli = new ArrayList<Veicolo>();

    try {
      log.debug("[ServiziMieiMezziImpl] getListaVeicoli --- INIZIO");

      VeicoliAttivi veicoliAttivi =
          instance.getApiTassaAuto().getVeicoliAttivi(utente.getCodiceFiscaleOperatore());

      if (veicoliAttivi != null) {
        listaVeicoli = veicoliAttivi.getListaVeicoliAttivi();
      }

      List<Veicolo> sortedListaVeicoli =
          listaVeicoli.stream()
              .sorted(Comparator.comparing(Veicolo::getDataInizioProprieta).reversed())
              .collect(Collectors.toList());

      return sortedListaVeicoli;
    } catch (BusinessException e) {
      log.error("ServiziMieiMezziImpl -- getListaVeicoli: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (ServiceUnavailableException e) {
      log.error("ServiziMieiMezziImpl -- getListaVeicoli: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- getListaVeicoli: errore RuntimeException nella Response:"
              + e.getMessage());

      // throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
      throw new BusinessException("RuntimeException Bollo Auto non raggiungibile");

    } finally {
      log.debug("close connection bollo");
      instance.closeConnection();
    }
  }

  @Override
  public DettaglioVeicolo getDettagliVeicolo(Veicolo veicolo)
      throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] getDettagliVeicolo --- INIZIO");
    try {
      return ServiceLocatorLivelloUnoBollo.getInstance()
          .getApiTassaAuto()
          .getDettagliVeicoloAttivo(String.valueOf(veicolo.getIdVeicolo()));
    } catch (BusinessException e) {
      log.error("ServiziMieiMezziImpl -- getDettagliVeicolo: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiMezziImpl -- getDettagliVeicolo: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- getDettagliVeicolo: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public List<Bollo> getListaDettagliBolli(Veicolo veicolo) throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] getListaDettagliBolli --- INIZIO");
    try {
      List<Bollo> listaBolli = new ArrayList<Bollo>();

      DettagliBolloMezzi dettagliBolloMezzi =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .getCalcolaSituazione(
                  veicolo
                      .getDataInizioProprieta()
                      .substring(
                          veicolo.getDataInizioProprieta().length() - 4,
                          veicolo.getDataInizioProprieta().length()),
                  String.valueOf(veicolo.getIdVeicolo()),
                  String.valueOf(veicolo.getIdProprietario()));

      listaBolli = dettagliBolloMezzi.getSituazionePagamenti();

      List<Bollo> sortedListaBolli =
          listaBolli.stream()
              .sorted(Comparator.comparing(Bollo::getAnno).reversed())
              .limit(4)
              .collect(Collectors.toList());

      return sortedListaBolli;
    } catch (BusinessException e) {
      log.error("ServiziMieiMezziImpl -- getListaDettagliBolli: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiMezziImpl -- getListaDettagliBolli: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- getListaDettagliBolli: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public DettaglioCalcoloBollo getCalcolaBollo(Veicolo veicolo, Bollo bollo)
      throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] getCalcolaBollo --- INIZIO");
    try {
      String[] scadenzaArray = bollo.getScadenza().split("/");
      String scadenza = scadenzaArray[1].concat(scadenzaArray[0]);

      DettaglioCalcoloBollo dettaglioCalcoloBollo =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .getCalcola(String.valueOf(veicolo.getIdVeicolo()), scadenza, bollo.getValidita());

      return dettaglioCalcoloBollo;
    } catch (BusinessException e) {
      log.error(
          "ServiziMieiMezziImpl -- getCalcolaBollo: BusinessException errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiMezziImpl -- getCalcolaBollo: WebApplicationException errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- getCalcolaBollo: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public List<PagamentoBollo> getPagamenti(Veicolo veicolo, Bollo bollo)
      throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] getPagamenti --- INIZIO");
    try {
      String annoBollo = bollo.getScadenza().substring(3);
      Pagamenti pagamenti =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .getPagamento(annoBollo, String.valueOf(veicolo.getIdVeicolo()));

      List<PagamentoBollo> listaPagamentoBollo = pagamenti.getPagamenti();

      return listaPagamentoBollo;

    } catch (BusinessException e) {
      log.error("ServiziMieiMezziImpl -- getPagamenti: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error("ServiziMieiMezziImpl -- getPagamenti: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- getPagamenti: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public DatiAvvioPagamento creaRichiestaPagamentoPagoPa(
      Veicolo veicolo, Bollo bollo, Utente utente) throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] creaRichiestaPagamentoPagoPa --- INIZIO");
    try {
      String[] scadenzaBolloSplitted = bollo.getScadenza().split("/");
      String scadenza = scadenzaBolloSplitted[1].concat(scadenzaBolloSplitted[0]);

      DatiPagamentoBollo datiPagamentoBollo = new DatiPagamentoBollo();
      datiPagamentoBollo.setEmail(utente.getMail());
      datiPagamentoBollo.setIdVeicolo(veicolo.getIdVeicolo());
      datiPagamentoBollo.setMesiValidita(Integer.parseInt(bollo.getValidita()));

      datiPagamentoBollo.setScadenza(Integer.parseInt(scadenza));

      // controllo che non siamo in produzione
      datiPagamentoBollo.setMode(BaseServiceImpl.AMBIENTE_PAGAMENTO_BOLLO);
      datiPagamentoBollo.setUrlKO(BaseServiceImpl.URL_PAGOPA_BOLLO_KO);
      datiPagamentoBollo.setUrlOK(BaseServiceImpl.URL_PAGOPA_BOLLO_OK);

      DatiAvvioPagamento datiAvvioPagamento =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .postPagoTassaPagoPa(datiPagamentoBollo);

      return datiAvvioPagamento;

    } catch (BusinessException e) {
      log.error("ServiziMieiMezziImpl -- creaRichiestaPagamentoPagoPa: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiMezziImpl -- creaRichiestaPagamentoPagoPa: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- creaRichiestaPagamentoPagoPa: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public EsitoAnnullamentoPagamento annullaPrenotazionePagamentoPagoPa(
      DatiAvvioPagamento datiAvvioPagamento) throws BusinessException, ApiException {
    log.debug("[ServiziMieiMezziImpl] annullaPrenotazionePagamentoPagoPa --- INIZIO");
    try {
      EsitoAnnullamentoPagamento esitoAnnullamentoPagamento =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .postAnnullaPrenotazionePagoPa(datiAvvioPagamento.getCodTrans());

      return esitoAnnullamentoPagamento;

    } catch (BusinessException e) {
      log.error(
          "ServiziMieiMezziImpl -- annullaPrenotazionePagamentoPagoPa: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziMieiMezziImpl -- annullaPrenotazionePagamentoPagoPa: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziMieiMezziImpl -- annullaPrenotazionePagamentoPagoPa: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("i miei mezzi "));
    }
  }

  @Override
  public VerificaAssicurazioneVeicoli getAssicurazione(Veicolo veicolo)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String targa = veicolo.getTarga();

      TipoVeicolo tipoVeicolo = null;
      String tipoVeicoloValue = veicolo.getTipoVeicolo();
      if (tipoVeicoloValue.equalsIgnoreCase("AUTOVEICOLO")) {
        tipoVeicolo = TipoVeicolo.AUTOVEICOLO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("MOTOVEICOLO")) {
        tipoVeicolo = TipoVeicolo.MOTOVEICOLO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("RIMORCHIO")) {
        tipoVeicolo = TipoVeicolo.RIMORCHIO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("CICLOMOTORE")) {
        tipoVeicolo = TipoVeicolo.CICLOMOTORE;
      }

      String pattern = "yyyy-MM-dd";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      String dataRiferimento = simpleDateFormat.format(new Date());

      VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli =
          new VerificaAssicurazioneVeicoli();
      verificaAssicurazioneVeicoli =
          instance
              .getApiAssicurazioneVeicolo()
              .verificaCoperturaAssicurativaDatiAnagraficiTargaCodiceTipoVeicoloDataRiferimentoGet(
                  targa, tipoVeicolo, dataRiferimento);
      return verificaAssicurazioneVeicoli;
    } catch (WebApplicationException e) {
      log.debug("2 Errore durante la chiamata delle API assicurazione webapplExc ", e);
      throw new ApiException(e.getResponse(), e.getLocalizedMessage());
    } catch (RuntimeException e) {
      log.debug("3 Errore durante la chiamata delle API assicurazione webapplExc ", e);
      // throw new RestartResponseAtInterceptPageException(AssicurazioneRevisioneErrorPage.class);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("assicurazione e revisione"));
    } catch (BusinessException e) {
      log.error(
          "4 ServiziMieiMezziImpl -- getAssicurazione: errore API veicoli BusinessException :", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
