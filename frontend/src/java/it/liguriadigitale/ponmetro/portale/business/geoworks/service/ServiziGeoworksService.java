package it.liguriadigitale.ponmetro.portale.business.geoworks.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetRfStatusHistoryFromRfId200Response;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetSingleRF200Response;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsSearchSearch200Response;
import it.liguriadigitale.ponmetro.geoworks.model.RequestFormsListDto;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ServiziGeoworksService {

  AppRequestFormsGetRfStatusHistoryFromRfId200Response GetRfStatusHistoryFromRfId(Integer rfId)
      throws BusinessException, ApiException, IOException;

  AppRequestFormsGetSingleRF200Response GetSingleRF(Integer rfId, Integer opeType, Integer opId)
      throws BusinessException, ApiException, IOException;

  AppRequestFormsSearchSearch200Response Search(
      String codiceFiscale, GeoworksFunzioni funzione, List<GeoworksServizi> listaServizi)
      throws BusinessException, ApiException, IOException;

  List<RequestFormsListDto> listaItemsFromSearch(
      String codiceFiscale, GeoworksFunzioni funzione, List<GeoworksServizi> listaServizi)
      throws BusinessException, ApiException, IOException;

  File getDownload(String guId) throws BusinessException, ApiException, IOException;

  File postDownload(String guId) throws BusinessException, ApiException, IOException;
}
