package it.liguriadigitale.ponmetro.portale.business.geoworks.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.util.List;

public interface ServiziGeoworksHelperService {

  List<GeoworksFunzioni> getGeoworksFunzioni() throws BusinessException;

  GeoworksFunzioni getGeoworksFunzione(String tipoFunz) throws BusinessException;

  List<GeoworksServizi> getGeoworksServizi(String tipoFunz) throws BusinessException;
}
