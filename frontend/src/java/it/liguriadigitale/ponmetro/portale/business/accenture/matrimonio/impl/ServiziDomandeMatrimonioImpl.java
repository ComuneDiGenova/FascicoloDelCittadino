package it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.service.ServiziDomandeMatrimonioService;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiConiugeMatrimonioMapper;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiMatrimonio;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiRichiedenteMatrimonioMapper;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.modelmapper.ModelMapper;

public class ServiziDomandeMatrimonioImpl implements ServiziDomandeMatrimonioService {

  private Log log = LogFactory.getLog(getClass());

  private TipologiaRichiestaEnum tipologia;

  private static final String ERRORE_API_DOMANDE_MATRIMONIO =
      "Errore di connessione alle API Domande matrimonio";

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException {

    log.debug("ServiziDomandeMatrimonioImpl getRecuperoUtenteByCodiceFiscale = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {
      response =
          instance
              .getApiAccenture()
              .servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(codiceFiscale);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByCodiceFiscale: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage(getTestoByNomeServizio()));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private String getTestoByNomeServizio() {

    if (tipologia == null) {
      return "Stato Civile";
    } else {
      switch (tipologia) {
        case MATRIMONIO:
          return "Pubblicazione di Matrimonio";
        case SEPARAZIONEDIVORZIO:
          return "Separazione o Divorzio";
        case TRASCRIZIONEMATRIMONIO:
          return "Trascrizioni di Matrimonio";
        case TRASCRIZIONESCIGLIOMENTO:
          return "Trascrizioni di Scioglimento";
        case UNIONECIVILE:
          return "Richiesta di Unione Civile";
        default:
          return "Stato Civile";
      }
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {

    log.debug("ServiziDomandeMatrimonioImpl getRecuperoUtenteByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response response = null;

    try {
      response = instance.getApiAccenture().servicesDataV580SobjectsAccountIdGet(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByIdAccenture: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getRecuperoUtenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage(getTestoByNomeServizio()));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
      getMatrimoniRichiedenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {
    log.debug("ServiziDomandeMatrimonioImpl getMatrimoniRichiedenteByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response response =
        null;

    try {
      response =
          instance
              .getApiAccenture()
              .servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniRichiedenteByIdAccenture: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniRichiedenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniRichiedenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage(getTestoByNomeServizio()));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response
      getMatrimoniConiugeByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException {
    log.debug("ServiziDomandeMatrimonioImpl getMatrimoniConiugeByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response response =
        null;

    try {
      response =
          instance
              .getApiAccenture()
              .servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(
                  idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniConiugeByIdAccenture: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniConiugeByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getMatrimoniConiugeByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage(getTestoByNomeServizio()));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  //	@Override
  //	public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
  // getListaMatrimoni(
  //			String codiceFiscale) throws BusinessException, IOException, ApiException {
  //		log.debug("ServiziDomandeMatrimonioImpl getListaSegnalazioniCzrm = " + codiceFiscale);
  //
  //		ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();
  //
  //		ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response response =
  // null;
  //
  //		try {
  //
  //			ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response utenteAccentureByCf
  // =	getRecuperoUtenteByCodiceFiscale(codiceFiscale);
  //
  //			if(LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
  //				return response;
  //			}
  //
  //			String idUtenteAccentureByCf= utenteAccentureByCf.getId();
  //
  //			response = 	getMatrimoniByIdAccenture(idUtenteAccentureByCf);
  //
  //			return response;
  //		}catch (BusinessException e) {
  //			log.error("ServiziDomandeMatrimonioImpl -- getListaMatrimoni: errore API ACENTURE:", e);
  //			throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
  //		} catch (WebApplicationException e) {
  //			log.error("ServiziDomandeMatrimonioImpl -- getListaMatrimoni: errore
  // WebApplicationException:"
  //					+ e.getMessage());
  //			throw new ApiException(e.getResponse(), e.getMessage());
  //		} catch (RuntimeException e) {
  //			log.error(
  //					"ServiziDomandeMatrimonioImpl -- getListaMatrimoni: errore durante la chiamata delle API
  // ACCENTURE ",
  //					e);
  //			throw new RestartResponseAtInterceptPageException(new
  // ErroreServiziPage(getTestoByNomeServizio()));
  //		} finally {
  //			log.debug("close connection");
  //			instance.closeConnection();
  //		}
  //	}

  @Override
  public List<DatiMatrimonio> getListaDomandeMatrimonioRichiedenteConiuge(String codiceFiscale)
      throws BusinessException, IOException, ApiException {
    log.debug(
        "ServiziDomandeMatrimonioImpl getListaDomandeMatrimonioRichiedenteConiuge = "
            + codiceFiscale);

    List<DatiMatrimonio> lista = new ArrayList<DatiMatrimonio>();

    try {

      ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response utenteAccentureByCf =
          getRecuperoUtenteByCodiceFiscale(codiceFiscale);

      if (LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
        return lista;
      }

      String idUtenteAccentureByCf = utenteAccentureByCf.getId();

      ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
          responseRichiedente = getMatrimoniRichiedenteByIdAccenture(idUtenteAccentureByCf);

      if (LabelFdCUtil.checkIfNotNull(responseRichiedente)
          && LabelFdCUtil.checkIfNotNull(responseRichiedente.getRecords())) {

        for (ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200ResponseRecordsInner
            elemRichiedente : responseRichiedente.getRecords()) {
          ModelMapper modelMapperRichiedente = new ModelMapper();
          modelMapperRichiedente.addMappings(new DatiRichiedenteMatrimonioMapper());
          DatiMatrimonio datiMatrimonioRichiedente =
              modelMapperRichiedente.map(elemRichiedente, DatiMatrimonio.class);
          lista.add(datiMatrimonioRichiedente);
        }
      }

      ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response
          responseConiuge = getMatrimoniConiugeByIdAccenture(idUtenteAccentureByCf);

      if (LabelFdCUtil.checkIfNotNull(responseConiuge)
          && LabelFdCUtil.checkIfNotNull(responseConiuge.getRecords())) {

        for (ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200ResponseRecordsInner
            elemConiuge : responseConiuge.getRecords()) {
          ModelMapper modelMapperConiuge = new ModelMapper();
          modelMapperConiuge.addMappings(new DatiConiugeMatrimonioMapper());
          DatiMatrimonio datiMatrimonioRichiedente =
              modelMapperConiuge.map(elemConiuge, DatiMatrimonio.class);
          lista.add(datiMatrimonioRichiedente);
        }
      }

      log.debug("CP lista matrimoni = " + lista);

      return lista;

    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getListaDomandeMatrimonioRichiedenteConiuge: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getListaDomandeMatrimonioRichiedenteConiuge: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeMatrimonioImpl -- getListaDomandeMatrimonioRichiedenteConiuge: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage(getTestoByNomeServizio()));
    }
  }

  @Override
  public List<DatiMatrimonio> getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia(
      String codiceFiscale, TipologiaRichiestaEnum tipologia)
      throws BusinessException, IOException, ApiException {

    this.tipologia = tipologia;

    log.debug(
        "CP getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia = " + tipologia.toString());

    List<DatiMatrimonio> listaTutteRichieste =
        getListaDomandeMatrimonioRichiedenteConiuge(codiceFiscale);

    List<DatiMatrimonio> listaRichiesteFiltrate = new ArrayList<DatiMatrimonio>();

    if (tipologia.equals(TipologiaRichiestaEnum.MATRIMONIO)) {
      listaRichiesteFiltrate =
          listaTutteRichieste.stream()
              .filter(
                  elem ->
                      (LabelFdCUtil.checkIfNull(elem.getTipologiaRichiesta()))
                          || (elem.getTipologiaRichiesta().equalsIgnoreCase(tipologia.toString())))
              .collect(Collectors.toList());
    } else {
      listaRichiesteFiltrate =
          listaTutteRichieste.stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem.getTipologiaRichiesta())
                          && elem.getTipologiaRichiesta().equalsIgnoreCase(tipologia.toString()))
              .collect(Collectors.toList());
    }

    return listaRichiesteFiltrate;
  }
}
