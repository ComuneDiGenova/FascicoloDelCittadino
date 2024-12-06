package it.liguriadigitale.ponmetro.portale.business.scadenze.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AbbonamentoAmtDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AreaBluDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.MovimentoBibliotecaDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.VScScadenzeResponse;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.scadenze.service.ServiziScadenzeService;
import it.liguriadigitale.ponmetro.portale.pojo.amt.ScadenzaAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.amt.TesseraAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.NucleoFamiglia;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziScadenzeImpl implements ServiziScadenzeService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public List<VScScadenzeUt> getListaScadenzeFiltrate(int mese, int anno, Utente utente)
      throws BusinessException, ApiException {
    log.debug("getListaScadenzeFiltrate --- INIZIO");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInGetListaScadenzeFiltrate(instance, mese, anno, utente);
  }

  private List<VScScadenzeUt> closeConnectionInGetListaScadenzeFiltrate(
      ServiceLocatorLivelloUno instance, int mese, int anno, Utente utente) {
    try {
      List<VScScadenzeUt> listaScadenze =
          instance
              .getApiScadenze()
              .getListaScadenzeCittadino(utente.getIdAnonimoComuneGenova())
              .getListaScadenze();
      return listaScadenze.stream()
          .filter(item -> LocalDateTimeUtil.isInLocalDateTime(item.getDataScadenza(), mese, anno))
          .collect(Collectors.toList());

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ServiziScadenze"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public void inizializzaScadenze(Utente utente) throws BusinessException {
    log.debug("inizializzaScadenze --- INIZIO " + utente.getListaAbbonamentiAmtDelLoggato());

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    inizializzaScadenzeConCloseConnection(instance, utente);
    log.debug("inizializzaScadenze --- FINE");
  }

  private void inizializzaScadenzeConCloseConnection(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      scadenzePerNucleo(utente);
      ScadenzePersonalizzateDto scadenze = inizializzaScadenzeDto(utente);
      VScScadenzeResponse verificaScadenze =
          instance.getApiScadenze().isUltimoAccessoScadenze24H(utente.getIdAnonimoComuneGenova());
      if (verificaScadenze.getEsito() != null && verificaScadenze.getEsito().isEsito()) {
        log.debug("verificaScadenze");
        instance.getApiScadenze().verificaScadenzePersonalizzate(scadenze);
      }
      log.debug("no verificaScadenze");
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ServiziScadenze"));
    } finally {
      instance.closeConnection();
    }
  }

  public void scadenzePerNucleo(Utente utente) throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    closeConnectionInScadenzePerNucleo(instance, utente);
  }

  private void closeConnectionInScadenzePerNucleo(
      ServiceLocatorLivelloUno instance, Utente utente) {
    ScadenzePersonalizzateDto objScadenzeNucleo = new ScadenzePersonalizzateDto();
    List<AbbonamentoAmtDto> listaNucleo = new ArrayList<>();
    try {
      // for (ComponenteNucleo componente :
      // utente.getListaNucleoFamiliareConviventiEAutodichiarati()) {

      List<ComponenteNucleo> listaNucleoAmt =
          ServiceLocator.getInstance()
              .getServiziAbbonamentiAmt()
              .inizializzaListaFamigliaAMT(utente);

      for (ComponenteNucleo componente : listaNucleoAmt) {
        if (!componente.getCodiceFiscale().equalsIgnoreCase(utente.getCodiceFiscaleOperatore())) {
          ScadenzePersonalizzateDto scadenzaComponenteNucleo = creaScadenza(utente, componente);
          inizializzaScadenzeDtoComponenteNucleo(scadenzaComponenteNucleo, componente);
          log.debug(" scadenzaComponenteNucleo: " + scadenzaComponenteNucleo);
          instance.getApiScadenze().verificaScadenzePersonalizzate(scadenzaComponenteNucleo);
          listaNucleo.add(aggiungiComponenteAMT(componente, utente));
        }
      }
      if (!listaNucleo.isEmpty()) {
        objScadenzeNucleo.setIdFcitt(utente.getIdAnonimoComuneGenova());
        objScadenzeNucleo.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
        objScadenzeNucleo.setAbbonamentiAmt(listaNucleo);
        instance.getApiScadenze().verificaScadenzePersonalizzate(objScadenzeNucleo);
      }
    } catch (BusinessException | WebApplicationException | ApiException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ServiziScadenze"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private AbbonamentoAmtDto aggiungiComponenteAMT(ComponenteNucleo componente, Utente utente) {
    AbbonamentoAmtDto objAbbonamento = new AbbonamentoAmtDto();
    if (componente.getCodiceFiscale() != null) {
      objAbbonamento.setCodiceFiscale(componente.getCodiceFiscale());
    }
    if (getDatiCittadino(componente).getCpvFamilyName() != null) {
      objAbbonamento.setCognome(getDatiCittadino(componente).getCpvFamilyName());
    }
    if (getDatiCittadino(componente).getCpvGivenName() != null) {
      objAbbonamento.setNome(componente.getDatiCittadino().getCpvGivenName());
    }
    return objAbbonamento;
  }

  private ScadenzePersonalizzateDto creaScadenza(Utente utente, ComponenteNucleo componente) {

    ScadenzePersonalizzateDto scadenza = new ScadenzePersonalizzateDto();
    scadenza.setCodiceFiscale(componente.getCodiceFiscale());
    scadenza.setIdFcitt(utente.getIdAnonimoComuneGenova());
    scadenza.setNome(getDatiCittadino(componente).getCpvGivenName());
    scadenza.setCognome(getDatiCittadino(componente).getCpvFamilyName());
    return scadenza;
  }

  private Residente getDatiCittadino(ComponenteNucleo componente) {
    if (componente.getDatiCittadino() != null) {
      return componente.getDatiCittadino();
    } else {
      return new Residente();
    }
  }

  private void inizializzaScadenzeDtoComponenteNucleo(
      ScadenzePersonalizzateDto scadenzaComponenteNucleo, ComponenteNucleo componente) {

    if (componente.isResidenteComuneGenova() && getDatiCittadino(componente) != null) {

      LocalDate validitaCartaIdentita =
          getDatiCittadino(componente).getGenovaOntoIDCardValidUntilDate();
      scadenzaComponenteNucleo.setDataScadenza(validitaCartaIdentita);
    }
    //		List<AbbonamentoAmtDto> listaAbbonamentiAmtDelSingolo =
    // ricavaAbbonamentiDelSingoloComponenteNucleo(componente);
    //		scadenzaComponenteNucleo.setAbbonamentiAmt(listaAbbonamentiAmtDelSingolo);
  }

  public List<AbbonamentoAmtDto> ricavaAbbonamentiDelSingoloComponenteNucleo(
      ComponenteNucleo componente) {
    NucleoFamiglia nucleoFamiglia = new NucleoFamiglia();
    List<ComponenteNucleo> lista = new ArrayList<>();
    List<AbbonamentoAmtDto> listaAbbonamentiAmtDelSingolo = new ArrayList<>();
    lista.add(componente);
    nucleoFamiglia.setListaCfNucleo(lista);

    log.debug("ricavaAbbonamentiDelSingoloComponenteNucleo_test ");

    try {

      List<ScadenzaAmtEsteso> listaScadenze =
          ServiceLocator.getInstance()
              .getServiziAbbonamentiAmt()
              .listaTuttiAbbonamentiAmtScadenze(nucleoFamiglia);

      creaListaAbbonamentiDTOScadenze(listaScadenze, listaAbbonamentiAmtDelSingolo);
      log.debug("listaAbbonamentiAmtDelSingolo=" + listaAbbonamentiAmtDelSingolo);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore scadenze AMT: ");
    }
    return listaAbbonamentiAmtDelSingolo;
  }

  private ScadenzePersonalizzateDto inizializzaScadenzeDto(Utente utente) {
    LocalDate validitaCartaIdentita;
    if (utente.isResidente()) {
      validitaCartaIdentita =
          utente.getDatiCittadinoResidente().getGenovaOntoIDCardValidUntilDate();
    } else {
      validitaCartaIdentita = null;
    }

    ScadenzePersonalizzateDto scadenze =
        new ScadenzePersonalizzateDto(
            utente.getCodiceFiscaleOperatore(),
            validitaCartaIdentita,
            utente.getIdAnonimoComuneGenova());
    scadenze.setNome(utente.getNome());
    scadenze.setCognome(utente.getCognome());
    List<VeicoloDto> listaTarghe = new ArrayList<>();

    if (utente.getListaVeicoliAttivi() != null) {
      for (Veicolo veicolo : utente.getListaVeicoliAttivi()) {
        VeicoloDto dto = new VeicoloDto();
        dto.setTarga(veicolo.getTarga());
        dto.setTipoVeicolo(veicolo.getTipoVeicolo());
        dto.setDataInizioProprieta(veicolo.getDataInizioProprieta());
        dto.setIdProprietario(veicolo.getIdProprietario());
        dto.setIdVeicolo(veicolo.getIdVeicolo());
        listaTarghe.add(dto);
      }
      scadenze.setVeicoliAttivi(listaTarghe);
    }

    List<MovimentoBibliotecaDto> listaMovimentiBiblioteca = new ArrayList<MovimentoBibliotecaDto>();
    if (utente.getListaMovimentiBiblioteche() != null) {
      for (Movimento movimento : utente.getListaMovimentiBiblioteche()) {
        MovimentoBibliotecaDto movimentoBibliotecaDto = new MovimentoBibliotecaDto();
        movimentoBibliotecaDto.setIdUtente(
            utente.getListaUtenteBiblioteche().get(0).getId().intValue());
        movimentoBibliotecaDto.setId(movimento.getId().intValue());
        movimentoBibliotecaDto.setCdBib(movimento.getCdBib());
        movimentoBibliotecaDto.setDsTipo(movimento.getCdTipo());
        movimentoBibliotecaDto.setStato(movimento.getCdStato());
        movimentoBibliotecaDto.setDtInizio(
            LocalDateUtil.getDataFormatoEuropeo(movimento.getDtInizio()));
        if (movimento.getDtStimaFine() != null) {
          movimentoBibliotecaDto.setDtStimaFine(
              LocalDateUtil.getDataFormatoEuropeo(movimento.getDtStimaFine()));
        }
        movimentoBibliotecaDto.setScaduto(movimento.getScaduto());
        listaMovimentiBiblioteca.add(movimentoBibliotecaDto);
      }
    }
    scadenze.setMovimentiBiblioteca(listaMovimentiBiblioteca);

    List<AbbonamentoAmtDto> listaAbbonamentiAmtDelLoggato =
        ricavaAbbonamentiDelSingoloComponenteNucleo(utente.getUtenteLoggatoComeComponenteNucleo());
    scadenze.setAbbonamentiAmt(listaAbbonamentiAmtDelLoggato);

    List<AreaBluDto> listaPermessiAreaBlu = new ArrayList<AreaBluDto>();
    if (utente.getListaPermessiAreaBlu() != null
        && utente.getListaPermessiAreaBlu().getPermitsList() != null) {
      for (Permit permesso : utente.getListaPermessiAreaBlu().getPermitsList()) {
        AreaBluDto areaBluDto = new AreaBluDto();
        areaBluDto.setCategoryDescription(permesso.getCategoryDescription());
        areaBluDto.setPermitCode(permesso.getPermitCode());
        if (LabelFdCUtil.checkIfNotNull(permesso.getValidTo())) {
          LocalDateTime dataScadenza = permesso.getValidTo().toLocalDateTime();
          areaBluDto.setValidTo(dataScadenza);
        }
        areaBluDto.setZoneDescription(permesso.getZoneDescription());
        listaPermessiAreaBlu.add(areaBluDto);
      }
    }
    scadenze.setPermessiAreaBlu(listaPermessiAreaBlu);

    return scadenze;
  }

  public void creaListaAbbonamentiDTO(
      List<TesseraAmtEsteso> listaTessereAMT,
      List<AbbonamentoAmtDto> listaAbbonamentiAmtDelLoggato) {
    for (TesseraAmtEsteso tessera : listaTessereAMT) {
      AbbonamentoAmtDto abbonamentoAmtDto = new AbbonamentoAmtDto();
      abbonamentoAmtDto.setCodiceFiscale(tessera.getCodiceFiscale());
      abbonamentoAmtDto.setCityPass(tessera.getCityPass());
      abbonamentoAmtDto.setDescrizioneTessera(tessera.getDescrizioneTessera());
      abbonamentoAmtDto.setFineValidita(
          LocalDateUtil.getDataFormatoEuropeo(tessera.getFineValidita()));
      listaAbbonamentiAmtDelLoggato.add(abbonamentoAmtDto);
    }
  }

  public void creaListaAbbonamentiDTOScadenze(
      List<ScadenzaAmtEsteso> listaTessereAMT,
      List<AbbonamentoAmtDto> listaAbbonamentiAmtDelLoggato) {
    for (ScadenzaAmtEsteso tessera : listaTessereAMT) {
      AbbonamentoAmtDto abbonamentoAmtDto = new AbbonamentoAmtDto();
      abbonamentoAmtDto.setCodiceFiscale(tessera.getCf());
      abbonamentoAmtDto.setCityPass(tessera.getCityPass());
      abbonamentoAmtDto.setDescrizioneTessera(tessera.getDescrizioneTessera());
      abbonamentoAmtDto.setFineValidita(
          LocalDateUtil.getDataFormatoEuropeo(tessera.getFineValidita()));

      listaAbbonamentiAmtDelLoggato.add(abbonamentoAmtDto);
    }
  }
}
