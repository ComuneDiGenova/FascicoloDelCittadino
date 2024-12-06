package it.liguriadigitale.ponmetro.portale.business.geoworks.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetRfStatusHistoryFromRfId200Response;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetSingleRF200Response;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsSearchSearch200Response;
import it.liguriadigitale.ponmetro.geoworks.model.ItemDTO;
import it.liguriadigitale.ponmetro.geoworks.model.RequestFormsListDto;
import it.liguriadigitale.ponmetro.geoworks.model.RequestFormsSearchDto;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.geoworks.service.ServiziGeoworksService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziGeoworksImpl implements ServiziGeoworksService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_GEOWORKS = "Errore di connessione alle API Geoworks";

  @Override
  public AppRequestFormsGetRfStatusHistoryFromRfId200Response GetRfStatusHistoryFromRfId(
      Integer rfId) throws BusinessException, ApiException, IOException {
    log.debug("ServiziGeoworksImpl -- GetRfStatusHistoryFromRfId");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAppRequestForms().appRequestFormsGetRfStatusHistoryFromRfId(rfId);
    } catch (BusinessException e) {
      log.error("ServiziGeoworksImpl -- GetRfStatusHistoryFromRfId: errore API Geoworks:", e);
      throw new BusinessException(ERRORE_API_GEOWORKS);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGeoworksImpl -- GetRfStatusHistoryFromRfId: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGeoworksImpl -- GetRfStatusHistoryFromRfId: errore durante la chiamata delle API Geoworks ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AppRequestFormsGetSingleRF200Response GetSingleRF(
      Integer rfId, Integer opeType, Integer opId)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziGeoworksImpl -- GetSingleRF");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAppRequestForms().appRequestFormsGetSingleRF(rfId, opeType, opId);
    } catch (BusinessException e) {
      log.error("ServiziGeoworksImpl -- GetSingleRF: errore API Geoworks:", e);
      throw new BusinessException(ERRORE_API_GEOWORKS);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGeoworksImpl -- GetSingleRF: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGeoworksImpl -- GetSingleRF: errore durante la chiamata delle API Geoworks ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public AppRequestFormsSearchSearch200Response Search(
      String codiceFiscale, GeoworksFunzioni funzione, List<GeoworksServizi> listaServizi)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziGeoworksImpl -- Search");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      RequestFormsSearchDto body = new RequestFormsSearchDto();

      List<ItemDTO> listaItemDTO = new ArrayList<ItemDTO>();

      for (GeoworksServizi servizio : listaServizi) {
        if (LabelFdCUtil.checkIfNotNull(servizio)
            && LabelFdCUtil.checkIfNotNull(servizio.getIdServizio())) {
          ItemDTO itemDTO = new ItemDTO();
          itemDTO.setId(servizio.getIdServizio().intValue());
        }
      }

      body.setSelectedRequestType(listaItemDTO);

      body.setSelectedOwner(codiceFiscale);

      log.debug("ServiziGeoworksImpl -- Search body = " + body.getSelectedRequestType());

      return instance.getApiAppRequestFormsSearch().appRequestFormsSearchSearch(body);
    } catch (BusinessException e) {
      log.error("ServiziGeoworksImpl -- Search: errore API Geoworks:", e);
      throw new BusinessException(ERRORE_API_GEOWORKS);
    } catch (WebApplicationException e) {
      log.error("ServiziGeoworksImpl -- Search: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziGeoworksImpl -- Search: errore durante la chiamata delle API Geoworks ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<RequestFormsListDto> listaItemsFromSearch(
      String codiceFiscale, GeoworksFunzioni funzione, List<GeoworksServizi> listaServizi)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziGeoworksImpl -- listaItemsFromSearch");

    List<RequestFormsListDto> lista = new ArrayList<RequestFormsListDto>();

    AppRequestFormsSearchSearch200Response search = Search(codiceFiscale, funzione, listaServizi);
    if (LabelFdCUtil.checkIfNotNull(search)
        && LabelFdCUtil.checkIfNotNull(search.getResult())
        && LabelFdCUtil.checkIfNotNull(search.getResult().getItems())) {
      lista = search.getResult().getItems();
    }

    return lista;
  }

  @Override
  public File getDownload(String guId) throws BusinessException, ApiException, IOException {
    log.debug("Search -- getDownload");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiDownload().getDownload(guId);
    } catch (BusinessException e) {
      log.error("ServiziGeoworksImpl -- getDownload: errore API Geoworks:", e);
      throw new BusinessException(ERRORE_API_GEOWORKS);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGeoworksImpl -- getDownload: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGeoworksImpl -- getDownload: errore durante la chiamata delle API Geoworks ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public File postDownload(String guId) throws BusinessException, ApiException, IOException {
    log.debug("Search -- postDownload");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiDownload().postDownload(guId);
    } catch (BusinessException e) {
      log.error("ServiziGeoworksImpl -- postDownload: errore API Geoworks:", e);
      throw new BusinessException(ERRORE_API_GEOWORKS);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziGeoworksImpl -- postDownload: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziGeoworksImpl -- postDownload: errore durante la chiamata delle API Geoworks ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
