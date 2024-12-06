package it.liguriadigitale.ponmetro.api.business.geoworks.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTServizi;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.util.List;

public interface GeoworksService {

  List<GeoworksTFunzioni> selectGeoworksFunzioni() throws BusinessException;

  List<GeoworksTServizi> selectGeoworksServizi(String tipoFunz) throws BusinessException;

  List<GeoworksFunzioni> getGeoworksFunzioni() throws BusinessException;

  List<GeoworksServizi> getGeoworksServizi(String tipoFunz) throws BusinessException;

  GeoworksTFunzioni selectGeoworksFunzione(String tipoFunz) throws BusinessException;

  GeoworksFunzioni getGeoworksFunzione(String tipoFunz) throws BusinessException;
}
