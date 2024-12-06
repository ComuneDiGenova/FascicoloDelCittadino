package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.geoworkshelper.apiclient.GeoworksHelperApi;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.util.List;

public class ApiGeoworksHelperImpl implements GeoworksHelperApi {

  private GeoworksHelperApi instance;

  public ApiGeoworksHelperImpl(GeoworksHelperApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<GeoworksFunzioni> getGeoworksFunzioni() {
    return instance.getGeoworksFunzioni();
  }

  @Override
  public List<GeoworksServizi> getGeoworksServizi(String arg0) {
    return instance.getGeoworksServizi(arg0);
  }

  @Override
  public GeoworksFunzioni getGeoworksFunzione(String arg0) {
    return instance.getGeoworksFunzione(arg0);
  }
}
