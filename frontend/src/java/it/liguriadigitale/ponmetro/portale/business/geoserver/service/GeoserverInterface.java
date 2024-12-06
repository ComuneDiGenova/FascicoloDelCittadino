package it.liguriadigitale.ponmetro.portale.business.geoserver.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoserver.model.FeatureCollection;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.util.List;

public interface GeoserverInterface {

  FeatureCollection getWfs(String input) throws BusinessException, ApiException;

  List<FeaturesGeoserver> getGeoserver(String input) throws BusinessException, ApiException;

  FeaturesGeoserver getToponomasticaResidenzaUtenteLoggato(Utente utente)
      throws BusinessException, ApiException;

  FeatureCollection getWfsByCodiceStradaNumeroCivico(String codiceViaDomicilio, String numeroCivico)
      throws BusinessException, ApiException;

  FeaturesGeoserver getGeoserverByCodiceStradaNumeroCivico(
      String codiceViaDomicilio, String numeroCivico, String viaDomicilio)
      throws BusinessException, ApiException;
}
