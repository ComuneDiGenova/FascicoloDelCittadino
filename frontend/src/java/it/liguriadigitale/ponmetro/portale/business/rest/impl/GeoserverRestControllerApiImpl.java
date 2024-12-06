package it.liguriadigitale.ponmetro.portale.business.rest.impl;

import it.liguriadigitale.ponmetro.geoserver.apiclient.GeoserverRestControllerApi;
import it.liguriadigitale.ponmetro.geoserver.model.FeatureCollection;

public class GeoserverRestControllerApiImpl implements GeoserverRestControllerApi {

  private GeoserverRestControllerApi instance;

  public GeoserverRestControllerApiImpl(GeoserverRestControllerApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public FeatureCollection getWfs(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      String arg4,
      String arg5,
      String arg6,
      String arg7,
      String arg8,
      String arg9) {
    return instance.getWfs(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  @Override
  public void getOws(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    instance.getOws(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  @Override
  public void getWms(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      String arg4,
      String arg5,
      String arg6,
      String arg7,
      String arg8,
      String arg9,
      String arg10) {
    instance.getWms(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
  }
}
