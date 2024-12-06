package it.liguriadigitale.ponmetro.portale.business.anagrafici.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaGenitori;
import it.liguriadigitale.ponmetro.portale.business.anagrafici.service.ServiziAnagraficiService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.DocumentoCaricato;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoAllegato;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCittadinanzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceComuneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceStatoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDichiarazionePrecompilataResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDocumentiPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoIndividuiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoParenteleResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoPraticheResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoStatoCivileResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTipologiaFamigliaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTitolaritaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProfessioniResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProvenienzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoIscrizioneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTitoliStudioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetToponomasticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetViarioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Individuo;
import it.liguriadigitale.ponmetro.servizianagrafici.model.IndividuoImmi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Professione;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PutCambioStatoPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.RequestCambioIndirizzo;
import it.liguriadigitale.ponmetro.servizianagrafici.model.RequestImmigrazione;
import it.liguriadigitale.ponmetro.servizianagrafici.model.StringGenericResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziAnagraficiImpl implements ServiziAnagraficiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_APK = "Errore di connessione alle API APK";

  @Override
  public GetTipoIscrizioneResponseGenericResponse getTipologiaIscrizione(Integer tipoPratica)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getTipologiaIscrizione " + tipoPratica);
      return instance.getApiPratica().getTipoIscrizione(tipoPratica);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getTipologiaIscrizione: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipologiaIscrizione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipologiaIscrizione: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Variazioni di indirizzo"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetViarioResponseGenericResponse getViario(Integer idVia, String descrizioneVia)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiToponomastica().getViario(idVia, descrizioneVia);
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getViario: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getViario: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziAnagraficiImpl -- getViario: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("viario"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoTitolaritaResponseGenericResponse getTipoOccupazione()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPratica().getElencoTitolarita();
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getTipoOccupazione: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipoOccupazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipoOccupazione: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("tipologia occupazione"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetCodiceComuneResponseGenericResponse getTuttiComuniApkappa(
      Integer codice, String descrizione) throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiToponomastica().getCodiceComune(codice, descrizione);
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getComuni: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getComuni: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziAnagraficiImpl -- getComuni: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Comuni"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoTipologiaFamigliaResponseGenericResponse getTipologiaFamiglia()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiFamiglia().getElencoTipologiaFamiglia();
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getTipologiaFamiglia: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipologiaFamiglia: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTipologiaFamiglia: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("tipologia famiglia"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetCodiceStatoResponseGenericResponse getStati(String codice, String descrizione)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiToponomastica().getCodiceStato(codice, descrizione);
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getStati: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getStati: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziAnagraficiImpl -- getStati: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Stati"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetToponomasticaResponseGenericResponse getToponomasticaConSelect2(
      FeaturesGeoserver featuresGeoserver) throws BusinessException, ApiException, IOException {
    try {
      log.debug("[ServiziAnagraficiImpl] getToponomasticaConSelect2 " + featuresGeoserver);

      Integer codStrada = Integer.parseInt(featuresGeoserver.getCOD_STRADA());

      Integer numeroCivico = Integer.parseInt(featuresGeoserver.getNUMERO());

      String esponente = featuresGeoserver.getLETTERA();

      String colore = featuresGeoserver.getCOLORE();

      log.debug(
          "CP getToponomasticaConSelect2 = "
              + codStrada
              + " - "
              + numeroCivico
              + " - "
              + esponente
              + " - "
              + colore);

      return ServiceLocatorLivelloUno.getInstance()
          .getApiToponomastica()
          .getToponomastica(codStrada, numeroCivico, esponente, null, null, null, colore);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getToponomasticaConSelect2: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getToponomasticaConSelect2: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getToponomastica: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("toponomastica"));
    }
  }

  @SuppressWarnings("unused")
  @Override
  public PostInserimentoPraticaSospesaResponseGenericResponse inserimentoPraticaSospesa(
      VariazioniResidenza variazioniResidenza) throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] inserimentoPraticaSospesa " + variazioniResidenza);

      PostInserimentoPraticaSospesaRequest richiesta = new PostInserimentoPraticaSospesaRequest();

      RequestCambioIndirizzo cambioIndirizzo = new RequestCambioIndirizzo();
      RequestImmigrazione richiestaResidenza = new RequestImmigrazione();

      if (variazioniResidenza.getTipoVariazioneDiResidenza().getId() == 1) {

        log.debug("CP cambio indirizzo");

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          cambioIndirizzo.setTipoIscrizione(
              Integer.parseInt(variazioniResidenza.getComboTipologiaIscrizione().getCodice()));
        } else {
          cambioIndirizzo.setTipoIscrizione(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDataDecorrenza())) {
          cambioIndirizzo.setDataDecorrenza(variazioniResidenza.getDataDecorrenza());

        } else {
          cambioIndirizzo.setDataDecorrenza(null);
        }

        cambioIndirizzo.setIdVia(variazioniResidenza.getIdVia());
        cambioIndirizzo.setNumeroCivico(variazioniResidenza.getCivico());
        cambioIndirizzo.setEsponente(variazioniResidenza.getEsponente());
        cambioIndirizzo.setScala(variazioniResidenza.getScala());
        cambioIndirizzo.setInterno(variazioniResidenza.getInterno());
        cambioIndirizzo.setPiano(variazioniResidenza.getPiano());
        cambioIndirizzo.setInternoEsponente(variazioniResidenza.getInternoEsponente());
        cambioIndirizzo.setIdToponomastica(variazioniResidenza.getIdToponomastica());
        cambioIndirizzo.setColore(variazioniResidenza.getColore());

        cambioIndirizzo.setEmail(variazioniResidenza.getEmailRichiedente());
        cambioIndirizzo.setTelefono(variazioniResidenza.getTelefonoRichiedente());
        cambioIndirizzo.setCellulare(variazioniResidenza.getSecondoTelefonoRichiedente());
        cambioIndirizzo.setPec(variazioniResidenza.getPecRichiedente());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipoOccupazione())) {
          cambioIndirizzo.setTitolaritaImmobile(
              variazioniResidenza.getComboTipoOccupazione().getCodice());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCodiceComuneAgEntrate())) {
          cambioIndirizzo.setCodiceComuneAgenziaEntrate(
              Integer.parseInt(variazioniResidenza.getCodiceComuneAgEntrate()));
        } else {
          cambioIndirizzo.setCodiceComuneAgenziaEntrate(null);
        }

        cambioIndirizzo.setDataContratto(variazioniResidenza.getDataContratto());

        cambioIndirizzo.setNumeroContratto(variazioniResidenza.getNumeroContratto());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfProprietario())) {
          Residente datiResidenteProprietario =
              getDatiResidentePerApk(variazioniResidenza.getCfProprietario());

          if (LabelFdCUtil.checkIfNotNull(datiResidenteProprietario)) {
            String idDemograficoProprietario = datiResidenteProprietario.getCpvPersonID();
            cambioIndirizzo.setCodiceProprietario(Integer.valueOf(idDemograficoProprietario));
          } else {
            cambioIndirizzo.setCodiceProprietario(null);
          }
        } else {
          cambioIndirizzo.setCodiceProprietario(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeProprietario())) {
          cambioIndirizzo.setNomeProprietario(
              variazioniResidenza.getNomeProprietario().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeProprietario())) {
          cambioIndirizzo.setCognomeProprietario(
              variazioniResidenza.getCognomeProprietario().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getRagioneSocialeProprietario())) {
          cambioIndirizzo.setRagioneSocialeProprietario(
              variazioniResidenza.getRagioneSocialeProprietario().toUpperCase());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCodiceComuneNascitaProprietario())) {
          cambioIndirizzo.setComuneNascitaProprietario(
              Integer.parseInt(variazioniResidenza.getCodiceComuneNascitaProprietario()));
        } else {
          cambioIndirizzo.setComuneNascitaProprietario(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfProprietario())) {
          cambioIndirizzo.setCodiceFiscaleProprietario(
              variazioniResidenza.getCfProprietario().toUpperCase());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getEmailProprietario())) {
          cambioIndirizzo.setEmailProprietario(variazioniResidenza.getEmailProprietario());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTelefonoProprietario())) {
          cambioIndirizzo.setTelefonoProprietario(variazioniResidenza.getTelefonoProprietario());
        }

        cambioIndirizzo.setDataNascitaProprietario(
            variazioniResidenza.getDataNascitaProprietario());

        if (LabelFdCUtil.checkIfNotNull(
            variazioniResidenza.getCodiceComuneResidenzaProprietario())) {
          cambioIndirizzo.setComuneResidenzaProprietario(
              Integer.parseInt(variazioniResidenza.getCodiceComuneResidenzaProprietario()));
        } else {
          cambioIndirizzo.setComuneResidenzaProprietario(null);
        }

        cambioIndirizzo.setIndirizzoResidenzaProprietario(
            variazioniResidenza.getIndirizzoProprietario());

        cambioIndirizzo.setVincoliAffettivi(variazioniResidenza.isVincoliAffettivi());

        cambioIndirizzo.setSezioneCatasto(variazioniResidenza.getSezione());
        cambioIndirizzo.setFoglioCatasto(variazioniResidenza.getFoglio());
        cambioIndirizzo.setNumeroCatasto(variazioniResidenza.getNumero());
        cambioIndirizzo.setSubalternoCatasto(variazioniResidenza.getSubalterno());

        cambioIndirizzo.setDescrizioneTitolo(variazioniResidenza.getDescrizioneTitolo());

        cambioIndirizzo.setCodiceUtente(variazioniResidenza.getPersonIdDemografico());
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeRichiedente())) {
          cambioIndirizzo.setNomeUtente(variazioniResidenza.getNomeRichiedente().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeRichiedente())) {
          cambioIndirizzo.setCognomeUtente(
              variazioniResidenza.getCognomeRichiedente().toUpperCase());
        }
        cambioIndirizzo.setCodiceFamigliaUtente(variazioniResidenza.getRegisteredFamilyId());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          cambioIndirizzo.setTipoScissione(
              Integer.parseInt(variazioniResidenza.getComboTipologiaIscrizione().getCodice()));
        } else {
          cambioIndirizzo.setTipoScissione(null);
        }

        String codiceFamigliaCoabitante = null;
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())
            && (variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("2")
                || variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("4"))
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfCoabitante())) {
          Residente datiResidenteCoabitante =
              ServiceLocator.getInstance()
                  .getServizioDemografico()
                  .getDatiResidente(variazioniResidenza.getCfCoabitante());

          if (LabelFdCUtil.checkIfNotNull(datiResidenteCoabitante)) {
            List<ItemRelazioneParentale> registeredFamily =
                datiResidenteCoabitante.getCpvInRegisteredFamily().getCpvBelongsToFamily();
            if (LabelFdCUtil.checkIfNotNull(registeredFamily)) {

              ItemRelazioneParentale intestatarioSchedaCoabitante =
                  registeredFamily.stream()
                      .filter(elem -> elem.getCpvParentType().equalsIgnoreCase("IS"))
                      .findAny()
                      .orElse(null);
              if (LabelFdCUtil.checkIfNotNull(intestatarioSchedaCoabitante)) {
                String cfIntestatarioSchedaCoabitante =
                    intestatarioSchedaCoabitante.getCpvComponentTaxCode();

                Residente datiResidenteIntestatarioSchedaCoabitante =
                    ServiceLocator.getInstance()
                        .getServizioDemografico()
                        .getDatiResidente(cfIntestatarioSchedaCoabitante);

                if (LabelFdCUtil.checkIfNotNull(datiResidenteIntestatarioSchedaCoabitante)) {
                  String idDemograficoIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvPersonID();
                  String nomeIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvGivenName();
                  String cognomeIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvFamilyName();
                  LocalDate dataNascitaIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvDateOfBirth();
                  codiceFamigliaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante
                          .getCpvInRegisteredFamily()
                          .getCpvRegisteredFamilyID();

                  if (LabelFdCUtil.checkIfNotNull(nomeIntestatarioSchedaCoabitante)) {
                    variazioniResidenza.setNomeCoabitante(
                        nomeIntestatarioSchedaCoabitante.toUpperCase());
                  }
                  if (LabelFdCUtil.checkIfNotNull(cognomeIntestatarioSchedaCoabitante)) {
                    variazioniResidenza.setCognomeCoabitante(
                        cognomeIntestatarioSchedaCoabitante.toUpperCase());
                  }

                  if (LabelFdCUtil.checkIfNotNull(idDemograficoIntestatarioSchedaCoabitante)) {
                    cambioIndirizzo.setCodiceIndividuoIs(
                        Integer.parseInt(idDemograficoIntestatarioSchedaCoabitante));

                    variazioniResidenza.setCodiceCoabitante(
                        idDemograficoIntestatarioSchedaCoabitante);
                  } else {
                    cambioIndirizzo.setCodiceIndividuoIs(null);
                  }

                  if (LabelFdCUtil.checkIfNotNull(nomeIntestatarioSchedaCoabitante)) {
                    cambioIndirizzo.setNomeIs(nomeIntestatarioSchedaCoabitante.toUpperCase());
                  }
                  if (LabelFdCUtil.checkIfNotNull(cognomeIntestatarioSchedaCoabitante)) {
                    cambioIndirizzo.setCognomeIs(cognomeIntestatarioSchedaCoabitante);
                  }
                  if (LabelFdCUtil.checkIfNotNull(dataNascitaIntestatarioSchedaCoabitante)) {
                    cambioIndirizzo.setDataNascitaIs(dataNascitaIntestatarioSchedaCoabitante);
                  }

                  ResidenteCpvHasBirthPlace datiNascitaIntestatarioScehdaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvHasBirthPlace();
                  if (LabelFdCUtil.checkIfNotNull(datiNascitaIntestatarioScehdaCoabitante)
                      && PageUtil.isStringValid(
                          datiNascitaIntestatarioScehdaCoabitante.getClvCity())) {
                    cambioIndirizzo.setComuneNascitaIs(
                        datiResidenteCoabitante.getCpvHasBirthPlace().getClvCity());
                  }

                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaFamiglia())
                      && variazioniResidenza
                          .getComboTipologiaFamiglia()
                          .getCodice()
                          .equalsIgnoreCase("2")) {
                    cambioIndirizzo.setIdPraticaIs(
                        Integer.parseInt(variazioniResidenza.getNumeroPratica()));
                  }

                  if (LabelFdCUtil.checkIfNotNull(codiceFamigliaCoabitante)) {
                    cambioIndirizzo.setCodiceNuovaFamiglia(
                        Integer.parseInt(codiceFamigliaCoabitante));
                  }
                }
              }
            }
          }
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaFamiglia())
            && variazioniResidenza.getComboTipologiaFamiglia().getCodice().equalsIgnoreCase("2")) {
          cambioIndirizzo.setCodiceNuovaFamiglia(Integer.parseInt(codiceFamigliaCoabitante));
        }

        List<Individuo> listaIndividui = new ArrayList<Individuo>();
        for (IndividuiCollegati ic : variazioniResidenza.getListaIndividuiCollegati()) {
          Individuo individuo = new Individuo();

          individuo.setCodiceIndividuo(Integer.parseInt(ic.getIdDemografico()));

          if (LabelFdCUtil.checkIfNotNull(ic.getParentelaConCoabitante())) {
            individuo.setrParNew(ic.getParentelaConCoabitante().getCodice());
          } else {
            individuo.setrParNew(ic.getParentela());
          }

          if (PageUtil.isStringValid(ic.getPossiedePatenti())) {
            if (ic.getPossiedePatenti().equalsIgnoreCase("Sì")) {
              individuo.hasPatenti(true);
            } else {
              individuo.hasPatenti(false);
            }
          }

          if (PageUtil.isStringValid(ic.getPossiedeVeicoli())) {
            if (ic.getPossiedeVeicoli().equalsIgnoreCase("Sì")) {
              individuo.hasVeicoli(true);
            } else {
              individuo.hasVeicoli(false);
            }
          }

          if (PageUtil.isStringValid(ic.getCf())) {
            individuo.setCodiceFiscaleIndividuo(ic.getCf());
          }

          listaIndividui.add(individuo);
        }
        cambioIndirizzo.setIndividui(listaIndividui);
        cambioIndirizzo.setPatenti(null);
        cambioIndirizzo.setVeicoli(null);

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          cambioIndirizzo.setCodiceFDC(
              variazioniResidenza.getComboTipologiaIscrizione().getCodiceFDC());
        }

        richiesta.setRequestCambioIndirizzo(cambioIndirizzo);
        richiesta.setRequestImmigrazione(null);

      } else {
        log.debug("CP richiesta residenza");

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          richiestaResidenza.setTipoIscrizione(
              Integer.parseInt(variazioniResidenza.getComboTipologiaIscrizione().getCodice()));
        } else {
          richiestaResidenza.setTipoIscrizione(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          richiestaResidenza.setCodiceFDC(
              variazioniResidenza.getComboTipologiaIscrizione().getCodiceFDC());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeRichiedente())) {
          richiestaResidenza.setCognomeUtente(
              variazioniResidenza.getCognomeRichiedente().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeRichiedente())) {
          richiestaResidenza.setNomeUtente(variazioniResidenza.getNomeRichiedente().toUpperCase());
        }
        richiestaResidenza.setCodiceDichiarante(variazioniResidenza.getPersonIdDemografico());
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfRichiedente())) {
          richiestaResidenza.setCodiceFiscaleDichiarante(
              variazioniResidenza.getCfRichiedente().toUpperCase());
        }

        // QUI SETTO COABITANTE COME DICE BONINI
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())) {
          richiestaResidenza.setTipoRichiesta(
              Integer.parseInt(variazioniResidenza.getComboTipologiaIscrizione().getCodice()));

          if (LabelFdCUtil.checkIfNotNull(
              variazioniResidenza.getComboTipologiaIscrizione().getCodiceFDC())) {
            if (variazioniResidenza
                .getComboTipologiaIscrizione()
                .getCodiceFDC()
                .equalsIgnoreCase("1")) {
              richiestaResidenza.setCoabitante(0);
            } else if ((variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("2"))
                || (variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("4"))) {
              richiestaResidenza.setCoabitante(-1);
            }
          }
        } else {
          richiestaResidenza.setTipoRichiesta(null);
          richiestaResidenza.setCoabitante(null);
        }

        richiestaResidenza.setDescComuneProvenienza(
            variazioniResidenza.getDescrizioneComuneProvenienza());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCodiceComuneProvenienza())) {
          richiestaResidenza.setIdComuneProvenienza(
              Integer.parseInt(variazioniResidenza.getCodiceComuneProvenienza()));
        } else {
          richiestaResidenza.setIdComuneProvenienza(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDataDecorrenza())) {
          richiestaResidenza.setDataDecorrenza(variazioniResidenza.getDataDecorrenza());
        } else {
          richiestaResidenza.setDataDecorrenza(null);
        }

        richiestaResidenza.setIdVia(variazioniResidenza.getIdVia());
        richiestaResidenza.setNumeroCivico(variazioniResidenza.getCivico());
        richiestaResidenza.setEsponente(variazioniResidenza.getEsponente());
        richiestaResidenza.setScala(variazioniResidenza.getScala());
        richiestaResidenza.setInterno(variazioniResidenza.getInterno());
        richiestaResidenza.setPiano(variazioniResidenza.getPiano());
        richiestaResidenza.setInternoEsponente(variazioniResidenza.getInternoEsponente());
        richiestaResidenza.setIdToponomastica(variazioniResidenza.getIdToponomastica());
        richiestaResidenza.setColore(variazioniResidenza.getColore());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboProvenienza())) {
          richiestaResidenza.setTipoInserimento(
              Integer.parseInt(variazioniResidenza.getComboProvenienza().getCodice()));
        } else {
          richiestaResidenza.setTipoInserimento(null);
        }

        richiestaResidenza.setCodiceStatoProvenienza(
            variazioniResidenza.getCodiceStatoProvenienza());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaIscrizione())
            && LabelFdCUtil.checkIfNotNull(
                variazioniResidenza.getComboTipologiaIscrizione().getCodiceFDC())
            && (variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("2")
                || variazioniResidenza
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("4"))) {

          Residente datiResidenteCoabitante =
              getDatiResidentePerApk(variazioniResidenza.getCfCoabitante());

          if (LabelFdCUtil.checkIfNotNull(datiResidenteCoabitante)) {

            List<ItemRelazioneParentale> registeredFamily =
                datiResidenteCoabitante.getCpvInRegisteredFamily().getCpvBelongsToFamily();
            if (LabelFdCUtil.checkIfNotNull(registeredFamily)) {

              ItemRelazioneParentale intestatarioSchedaCoabitante =
                  registeredFamily.stream()
                      .filter(elem -> elem.getCpvParentType().equalsIgnoreCase("IS"))
                      .findAny()
                      .orElse(null);
              if (LabelFdCUtil.checkIfNotNull(intestatarioSchedaCoabitante)) {
                String cfIntestatarioSchedaCoabitante =
                    intestatarioSchedaCoabitante.getCpvComponentTaxCode();

                Residente datiResidenteIntestatarioSchedaCoabitante =
                    getDatiResidentePerApk(cfIntestatarioSchedaCoabitante);

                if (LabelFdCUtil.checkIfNotNull(datiResidenteIntestatarioSchedaCoabitante)) {
                  String idDemograficoIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvPersonID();

                  // COMMENTO PER BONINI CHE DICE CHE VUOLE 0
                  // o -1 COME BOOLEANO
                  // if
                  // (LabelFdCUtil.checkIfNotNull(idDemograficoIntestatarioSchedaCoabitante))
                  // {
                  // richiestaResidenza.setCoabitante(
                  // Integer.parseInt(idDemograficoIntestatarioSchedaCoabitante));
                  //
                  // richiestaResidenza.setCoabitante(
                  // Integer.parseInt(idDemograficoIntestatarioSchedaCoabitante));
                  // } else {
                  // richiestaResidenza.setCoabitante(null);
                  // }

                  String nomeIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvGivenName();
                  String cognomeIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvFamilyName();
                  LocalDate dataNascitaIntestatarioSchedaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvDateOfBirth();

                  ResidenteCpvHasBirthPlace datiNascitaIntestatarioScehdaCoabitante =
                      datiResidenteIntestatarioSchedaCoabitante.getCpvHasBirthPlace();
                  if (LabelFdCUtil.checkIfNotNull(datiNascitaIntestatarioScehdaCoabitante)
                      && PageUtil.isStringValid(
                          datiNascitaIntestatarioScehdaCoabitante.getClvCity())) {
                    richiestaResidenza.setComuneNascitaIs(
                        datiResidenteCoabitante.getCpvHasBirthPlace().getClvCity());
                  }

                  if (LabelFdCUtil.checkIfNotNull(nomeIntestatarioSchedaCoabitante)) {
                    variazioniResidenza.setNomeCoabitante(
                        nomeIntestatarioSchedaCoabitante.toUpperCase());
                  }
                  if (LabelFdCUtil.checkIfNotNull(cognomeIntestatarioSchedaCoabitante)) {
                    variazioniResidenza.setCognomeCoabitante(
                        cognomeIntestatarioSchedaCoabitante.toUpperCase());
                  }
                  if (LabelFdCUtil.checkIfNotNull(dataNascitaIntestatarioSchedaCoabitante)) {
                    variazioniResidenza.setDataNascitaCoabitante(
                        dataNascitaIntestatarioSchedaCoabitante);
                  }

                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeCoabitante())) {
                    richiestaResidenza.setCogonmeIs(
                        variazioniResidenza.getCognomeCoabitante().toUpperCase());
                  }
                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeCoabitante())) {
                    richiestaResidenza.setNomeIs(
                        variazioniResidenza.getNomeCoabitante().toUpperCase());
                  }

                  if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDataNascitaCoabitante())) {
                    richiestaResidenza.setDataNascitaIs(
                        variazioniResidenza.getDataNascitaCoabitante());
                  }

                  if (LabelFdCUtil.checkIfNotNull(
                      datiResidenteIntestatarioSchedaCoabitante.getCpvInRegisteredFamily())) {
                    String codiceFamigliaCoabitante =
                        datiResidenteIntestatarioSchedaCoabitante
                            .getCpvInRegisteredFamily()
                            .getCpvRegisteredFamilyID();
                    if (LabelFdCUtil.checkIfNotNull(codiceFamigliaCoabitante)) {
                      richiestaResidenza.setCodiceFamiglia(
                          Integer.parseInt(codiceFamigliaCoabitante));
                    }
                  }
                }
              }
            }
          }
        }

        richiestaResidenza.setEmail(variazioniResidenza.getEmailRichiedente());
        richiestaResidenza.setTelefono(variazioniResidenza.getTelefonoRichiedente());
        richiestaResidenza.setCellulare(variazioniResidenza.getSecondoTelefonoRichiedente());
        richiestaResidenza.setPec(variazioniResidenza.getPecRichiedente());

        richiestaResidenza.setSezioneCatasto(variazioniResidenza.getSezione());
        richiestaResidenza.setFoglioCatasto(variazioniResidenza.getFoglio());
        richiestaResidenza.setNumeroCatasto(variazioniResidenza.getNumero());
        richiestaResidenza.setSubalternoCatasto(variazioniResidenza.getSubalterno());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipoOccupazione())) {
          richiestaResidenza.setTitolaritaImmobile(
              variazioniResidenza.getComboTipoOccupazione().getCodice());
        } else {
          richiestaResidenza.setTitolaritaImmobile(null);
        }
        richiestaResidenza.setDescrizioneTitolo(variazioniResidenza.getDescrizioneTitolo());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCodiceComuneAgEntrate())) {
          richiestaResidenza.setCodiceComuneAgenziaEntrate(
              Integer.parseInt(variazioniResidenza.getCodiceComuneAgEntrate()));
        } else {
          richiestaResidenza.setCodiceComuneAgenziaEntrate(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDataContratto())) {
          richiestaResidenza.setDataContratto(variazioniResidenza.getDataContratto());
        } else {
          richiestaResidenza.setDataContratto(null);
        }

        richiestaResidenza.setNumeroContratto(variazioniResidenza.getNumeroContratto());

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfProprietario())) {
          Residente datiResidenteProprietario =
              getDatiResidentePerApk(variazioniResidenza.getCfProprietario());

          if (LabelFdCUtil.checkIfNotNull(datiResidenteProprietario)) {
            String idDemograficoProprietario = datiResidenteProprietario.getCpvPersonID();
            richiestaResidenza.setCodiceProprietario(Integer.valueOf(idDemograficoProprietario));
          } else {
            richiestaResidenza.setCodiceProprietario(null);
          }
        } else {
          richiestaResidenza.setCodiceProprietario(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeProprietario())) {
          richiestaResidenza.setNomeProprietario(
              variazioniResidenza.getNomeProprietario().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeProprietario())) {
          richiestaResidenza.setCognomeProprietario(
              variazioniResidenza.getCognomeProprietario().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getRagioneSocialeProprietario())) {
          richiestaResidenza.setRagioneSocialeProprietario(
              variazioniResidenza.getRagioneSocialeProprietario().toUpperCase());
        }
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCfProprietario())) {
          richiestaResidenza.setCodiceFiscaleProprietario(
              variazioniResidenza.getCfProprietario().toUpperCase());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCodiceComuneNascitaProprietario())) {
          richiestaResidenza.setComuneNascitaProprietario(
              Integer.parseInt(variazioniResidenza.getCodiceComuneNascitaProprietario()));
        } else {
          richiestaResidenza.setComuneNascitaProprietario(null);
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getEmailProprietario())) {
          richiestaResidenza.setEmailProprietario(variazioniResidenza.getEmailProprietario());
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTelefonoProprietario())) {
          richiestaResidenza.setTelefonoProprietario(variazioniResidenza.getTelefonoProprietario());
        }

        richiestaResidenza.setDataNascitaProprietario(
            variazioniResidenza.getDataNascitaProprietario());

        if (LabelFdCUtil.checkIfNotNull(
            variazioniResidenza.getCodiceComuneResidenzaProprietario())) {
          richiestaResidenza.setComuneResidenzaProprietario(
              Integer.parseInt(variazioniResidenza.getCodiceComuneResidenzaProprietario()));
        } else {
          richiestaResidenza.setComuneResidenzaProprietario(null);
        }

        richiestaResidenza.setIndirizzoResidenzaProprietario(
            variazioniResidenza.getIndirizzoProprietario());

        List<IndividuoImmi> listaIndividuiImmi = new ArrayList<IndividuoImmi>();
        for (IndividuiCollegatiImmigrazione elem :
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione()) {
          IndividuoImmi individuoImmi = new IndividuoImmi();

          if (LabelFdCUtil.checkIfNotNull(elem.getCognome())) {
            individuoImmi.setCognome(elem.getCognome().toUpperCase());
          }
          if (LabelFdCUtil.checkIfNotNull(elem.getNome())) {
            individuoImmi.setNome(elem.getNome().toUpperCase());
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getSesso())) {
            if (elem.getSesso().equalsIgnoreCase("Maschio")) {
              individuoImmi.setSesso("M");
            } else if (elem.getSesso().equalsIgnoreCase("Femmina")) {
              individuoImmi.setSesso("F");
            }
          } else {
            individuoImmi.setSesso(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getDataNascita())) {
            individuoImmi.setDataNascita(elem.getDataNascita());
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getCodiceComuneNascita())) {
            individuoImmi.setCodiceComuneNascita(Integer.parseInt(elem.getCodiceComuneNascita()));
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getCf())) {
            individuoImmi.setCodiceFiscale(elem.getCf().toUpperCase());
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getParentela())) {
            individuoImmi.setrParentela(elem.getParentela().getCodice());
          } else {
            individuoImmi.setrParentela(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getStatoCivile())) {
            individuoImmi.setrStatoCivile(elem.getStatoCivile().getDescrizione());
          } else {
            individuoImmi.setrStatoCivile(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getCognomeConiuge())) {
            individuoImmi.setCognomeConiuge(elem.getCognomeConiuge().toUpperCase());
          }
          if (LabelFdCUtil.checkIfNotNull(elem.getNomeConiuge())) {
            individuoImmi.setNomeConiuge(elem.getNomeConiuge().toUpperCase());
          }
          if (LabelFdCUtil.checkIfNotNull(elem.getDataMatrimonio())) {
            individuoImmi.setDataMatrimonio(elem.getDataMatrimonio());
          } else {
            individuoImmi.setDataMatrimonio(null);
          }
          if (LabelFdCUtil.checkIfNotNull(elem.getCodiceComuneMatrimonio())) {
            individuoImmi.setComuneMatrimonio(Integer.parseInt(elem.getCodiceComuneMatrimonio()));
          } else {
            individuoImmi.setComuneMatrimonio(null);
          }

          individuoImmi.setNumeroCartaIdentita(elem.getNumeroCI());

          if (LabelFdCUtil.checkIfNotNull(elem.getDataRilascioCI())) {
            individuoImmi.setDataCartaIdentita(elem.getDataRilascioCI());
          } else {
            individuoImmi.setDataCartaIdentita(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getDataScadenzaCI())) {
            individuoImmi.setDataScadenzaCartaIdentita(elem.getDataScadenzaCI());
          } else {
            individuoImmi.setDataScadenzaCartaIdentita(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getCodiceComuneRilascioCI())) {
            individuoImmi.setComuneCartaIdentita(
                Integer.parseInt(elem.getCodiceComuneRilascioCI()));
          } else {
            individuoImmi.setComuneCartaIdentita(null);
          }
          individuoImmi.setEspatrio(elem.isEspatrio());

          if (LabelFdCUtil.checkIfNotNull(elem.getProfessione())) {

            String[] professione = elem.getProfessione().split("-");
            String idProfessione = professione[professione.length - 1];

            individuoImmi.setCodiceProfessione(Integer.parseInt(idProfessione));
          } else {
            individuoImmi.setCodiceProfessione(null);
          }

          if (LabelFdCUtil.checkIfNotNull(elem.getTitoloDiStudio())) {
            individuoImmi.setCodiceTitoloStudio(
                Integer.parseInt(elem.getTitoloDiStudio().getCodice()));
          } else {
            individuoImmi.setCodiceTitoloStudio(null);
          }

          if (PageUtil.isStringValid(elem.getPatenti())) {
            if (elem.getPatenti().equalsIgnoreCase("Sì")) {
              individuoImmi.setHasPatente(true);
            } else {
              individuoImmi.setHasPatente(false);
            }
          }

          if (PageUtil.isStringValid(elem.getVeicoli())) {
            if (elem.getVeicoli().equalsIgnoreCase("Sì")) {
              individuoImmi.setHasVeicolo(true);
            } else {
              individuoImmi.setHasVeicolo(false);
            }
          }

          // // TODO da capire
          // individuoImmi.setNumeroPSogg(numeroPSogg);
          // individuoImmi.setCodiceQuestura(codiceQuestura);
          // individuoImmi.setHasScadenzaPSogg(hasScadenzaPSogg);
          // individuoImmi.setDataRilascioPSogg(dataRilascioPSogg);
          // individuoImmi.setDataScadenzaPSogg(dataScadenzaPSogg);
          //

          if (LabelFdCUtil.checkIfNotNull(elem.getCittadinanza())) {
            String[] cittadinanza = elem.getCittadinanza().split("-");
            String idCittadinanza = cittadinanza[cittadinanza.length - 1];

            individuoImmi.setStatoCittadinanza(idCittadinanza);
          }

          individuoImmi.setCodiceIndividuo(Integer.parseInt(elem.getCodiceIndividuo()));

          listaIndividuiImmi.add(individuoImmi);
        }
        richiestaResidenza.setIndividui(listaIndividuiImmi);

        richiestaResidenza.setVincoliAffettivi(variazioniResidenza.isVincoliAffettivi());

        richiestaResidenza.setPatenti(null);

        richiestaResidenza.setVeicoli(null);

        richiesta.setRequestCambioIndirizzo(null);
        richiesta.setRequestImmigrazione(richiestaResidenza);
      }

      log.debug("CP cambio indirizzo POST = " + cambioIndirizzo);

      log.debug("CP richiesta residenza POST = " + richiestaResidenza);
      return instance.getApiPratica().postInserimentoPraticaSospesa(richiesta);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- inserimentoPraticaSospesa: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- inserimentoPraticaSospesa: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- inserimentoPraticaSospesa: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("variazioni di indirizzo"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetDichiarazionePrecompilataResponseGenericResponse getDichiarazionePrecompilata(
      Integer idPratica, String codiceFiscaleRichiedente)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "CP getDichiarazionePrecompilata = " + idPratica + " - " + codiceFiscaleRichiedente);
      GetDichiarazionePrecompilataResponseGenericResponse risposta =
          instance
              .getApiPratica()
              .getDichiarazionePrecompilata(idPratica, codiceFiscaleRichiedente);

      return risposta;
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getDichiarazionePrecompilata: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDichiarazionePrecompilata: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDichiarazionePrecompilata: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("dichiarazione precompilata"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetCittadinanzaResponseGenericResponse getCittadinanza(String codice, String descrizione)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiToponomastica().getCittadinanza(codice, descrizione);
    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getCittadinanza: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getCittadinanza: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getCittadinanza: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Cittadinanze"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoStatoCivileResponseGenericResponse getStatoCivile()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getStatoCivile");
      return instance.getApiPratica().getElencoStatoCivile();

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getStatoCivile: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getStatoCivile: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getStatoCivile: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("stato civile"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoParenteleResponseGenericResponse getElencoParentele()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getElencoParentele");
      return instance.getApiPratica().getElencoParentele();

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getElencoParentele: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoParentele: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoParentele: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("parentele"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetProfessioniResponseGenericResponse getProfessioni()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getProfessioni");
      return instance.getApiPratica().getProfessioni();

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getProfessioni: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getProfessioni: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getProfessioni: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("professioni"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetTitoliStudioResponseGenericResponse getTitoliDiStudio()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getTitoliDiStudio");
      return instance.getApiPratica().getTitoliStudio();

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getTitoliDiStudio: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTitoliDiStudio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getTitoliDiStudio: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("titoli di studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoPraticheResponseGenericResponse getElencoPratiche(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getElencoPratiche " + codiceFiscale);
      return instance.getApiPratica().getElencoPratiche(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getElencoPratiche: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoPratiche: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoPratiche: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("elenco pratiche"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PostInserimentoAllegatiResponseGenericResponse inserimentoAllegato(
      Integer idPratica, DocumentoCaricato documento)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] inserimentoAllegato " + idPratica);

      PostInserimentoAllegatiRequest richiesta = new PostInserimentoAllegatiRequest();
      richiesta.setIdPratica(idPratica);

      DocumentoAllegato documentoAllegato = new DocumentoAllegato();
      documentoAllegato.setNome(documento.getNomeFile());
      documentoAllegato.setContenuto(documento.getByteDocumentoUpload());
      richiesta.setDocumentoAllegato(documentoAllegato);

      return instance.getApiPratica().postInserimentoAllegati(richiesta);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- inserimentoAllegato: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- inserimentoAllegato: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- inserimentoAllegato: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("inserimento allegato"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetDocumentiPraticaResponseGenericResponse getDocumentiPratica(
      Integer idPratica, boolean attivo) throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getDocumentiPratica ");
      return instance.getApiPratica().getDocumentiPratica(idPratica, attivo);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getDocumentiPratica: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDocumentiPratica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDocumentiPratica: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("documenti pratica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PutCambioStatoPraticaResponseGenericResponse inviaPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] inviaPratica " + idPratica);
      return instance.getApiPratica().putCambioStatoPratica(idPratica);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- inviaPratica: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- inviaPratica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- inviaPratica: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("invio pratica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public StringGenericResponse cancellaAllegatoCaricato(Integer idAllegato)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] cancellaAllegatoCaricato " + idAllegato);
      return instance.getApiPratica().delAllegato(idAllegato);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- cancellaAllegatoCaricato: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- cancellaAllegatoCaricato: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- cancellaAllegatoCaricato: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("cancella documento"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetDatiGeneraliPraticaResponseGenericResponse getDatiGeneraliPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getDatiGeneraliPratica " + idPratica);
      return instance.getApiPratica().getDatiGeneraliPratica(idPratica);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getDatiGeneraliPratica: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDatiGeneraliPratica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDatiGeneraliPratica: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("dati generali pratica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetElencoIndividuiResponseGenericResponse getElencoIndividuiCollegati(Integer idPratica)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getElencoIndividuiCollegati " + idPratica);
      return instance.getApiPratica().getElencoIndividui(idPratica);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getElencoIndividuiCollegati: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoIndividuiCollegati: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getElencoIndividuiCollegati: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("individui collegati"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public StringGenericResponse cancellaPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] cancellaPratica " + idPratica);
      return instance.getApiPratica().delPratica(idPratica);

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- cancellaPratica: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- cancellaPratica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- cancellaPratica: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cancella pratica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public GetProvenienzaResponseGenericResponse getProvenienza()
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[ServiziAnagraficiImpl] getProvenienza");
      return instance.getApiPratica().getProvenienza();

    } catch (BusinessException e) {
      log.error("ServiziAnagraficiImpl -- getProvenienza: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getProvenienza: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getProvenienza: errore durante la chiamata delle API APK ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("provenienza"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Residente getDatiResidentePerApk(String codiceFiscale)
      throws BusinessException, ApiException {
    log.debug("ServiziAnagraficiImpl -- getDatiResidentePerApk");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Residente residente =
          instance.getApiDemografico().demograficoResidenteCodiceFiscaleGet(codiceFiscale);
      if (residente != null) {
        log.debug(
            "isResidente: " + residente.getCpvFamilyName() + " " + residente.getCpvGivenName());
        return residente;
      } else {
        log.debug("CP non residente");
        return null;
      }
    } catch (BusinessException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDatiResidentePerApk: errore di business nel recupero dei dati residente da codice fiscale:",
          e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDatiResidentePerApk: errore API nel recupero dei dati residente da codice fiscale:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- getDatiResidentePerApk: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("anagrafica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public <T> boolean isCardMinorenniVisibile(List<T> listaIndividui)
      throws BusinessException, ApiException {
    try {
      log.debug("[ServiziAnagraficiImpl] isEntrambiGenitoriNelNucleoCheSiSposta");

      List<Boolean> listaRisultati = new ArrayList<Boolean>();

      List<String> listaCfMinorenni = new ArrayList<String>();
      List<String> listaCfMaggiorenni = new ArrayList<String>();

      for (T elem : listaIndividui) {
        if (elem instanceof IndividuiCollegati) {
          if (((IndividuiCollegati) elem).getEta() < 18) {
            listaCfMinorenni.add(((IndividuiCollegati) elem).getCf());
          } else {
            listaCfMaggiorenni.add(((IndividuiCollegati) elem).getCf());
          }
        } else if (elem instanceof IndividuiCollegatiImmigrazione) {
          if (((IndividuiCollegatiImmigrazione) elem).getEta() < 18) {
            listaCfMinorenni.add(((IndividuiCollegatiImmigrazione) elem).getCf());
          } else {
            listaCfMaggiorenni.add(((IndividuiCollegatiImmigrazione) elem).getCf());
          }
        }
      }

      for (String minore : listaCfMinorenni) {

        if (PageUtil.isStringValid(minore)) {
          StrutturaGenitori genitori =
              ServiceLocator.getInstance().getServizioDemografico().getGenitoriDaCfBambino(minore);
          if (LabelFdCUtil.checkIfNotNull(genitori)) {
            if (LabelFdCUtil.checkIfNotNull(genitori.getCpvHasParentalRelationshipWith())) {

              String mammaPresente = "";
              String papaPresente = "";

              if (LabelFdCUtil.checkIfNotNull(
                  genitori.getCpvHasParentalRelationshipWith().getCpvHasMother())) {
                String cfMamma = genitori.getCpvHasParentalRelationshipWith().getCpvHasMother();

                mammaPresente =
                    listaCfMaggiorenni.stream()
                        .filter(elem -> elem.equalsIgnoreCase(cfMamma))
                        .findAny()
                        .orElse(null);
              }
              if (LabelFdCUtil.checkIfNotNull(
                  genitori.getCpvHasParentalRelationshipWith().getCpvHasFather())) {
                String cfPapa = genitori.getCpvHasParentalRelationshipWith().getCpvHasFather();

                papaPresente =
                    listaCfMaggiorenni.stream()
                        .filter(elem -> elem.equalsIgnoreCase(cfPapa))
                        .findAny()
                        .orElse(null);
              }

              if (PageUtil.isStringValid(mammaPresente) && PageUtil.isStringValid(papaPresente)) {
                if (PageUtil.isStringValid(mammaPresente) && PageUtil.isStringValid(papaPresente)) {
                  listaRisultati.add(true);
                } else {
                  listaRisultati.add(false);
                }
              } else {
                listaRisultati.add(false);
              }
            }
          }

        } else {
          listaRisultati.add(false);
        }
      }

      if (listaRisultati.contains(false)) {
        return false;
      } else {
        return true;
      }

    } catch (BusinessException e) {
      log.error(
          "ServiziAnagraficiImpl -- isEntrambiGenitoriNelNucleoCheSiSposta: errore API APK:", e);
      throw new BusinessException(ERRORE_API_APK);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAnagraficiImpl -- isEntrambiGenitoriNelNucleoCheSiSposta: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAnagraficiImpl -- isEntrambiGenitoriNelNucleoCheSiSposta: errore durante la chiamata delle API APK ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("variazioni di indirizzo"));
    }
  }

  @Override
  public List<Professione> getProfessioniFiltrate(String input) {
    List<Professione> listaProfessioni = new ArrayList<Professione>();

    try {
      GetProfessioniResponseGenericResponse tutteProfessioni = getProfessioni();
      if (LabelFdCUtil.checkIfNotNull(tutteProfessioni)
          && LabelFdCUtil.checkIfNotNull(tutteProfessioni.getResult())
          && LabelFdCUtil.checkIfNotNull(tutteProfessioni.getResult().getProfessioni())) {

        listaProfessioni =
            tutteProfessioni.getResult().getProfessioni().stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem)
                            && PageUtil.isStringValid(elem.getDescrizioneM())
                            && StringUtils.containsIgnoreCase(elem.getDescrizioneM(), input))
                .collect(Collectors.toList());
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore get professioni APK: " + e.getMessage(), e);
    }

    return listaProfessioni;
  }

  @Override
  public Professione getProfessioniFiltrateByCodice(String codiceProfessione) {
    Professione professione = null;
    Integer codice = Integer.parseInt(codiceProfessione);

    try {
      GetProfessioniResponseGenericResponse tutteProfessioni = getProfessioni();
      if (LabelFdCUtil.checkIfNotNull(tutteProfessioni)
          && LabelFdCUtil.checkIfNotNull(tutteProfessioni.getResult())
          && LabelFdCUtil.checkIfNotNull(tutteProfessioni.getResult().getProfessioni())) {

        professione =
            tutteProfessioni.getResult().getProfessioni().stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem)
                            && LabelFdCUtil.checkIfNotNull(elem.getCodice())
                            && elem.getCodice().equals(codice))
                .findAny()
                .orElse(null);
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore get professioni APK: " + e.getMessage(), e);
    }

    return professione;
  }
}
