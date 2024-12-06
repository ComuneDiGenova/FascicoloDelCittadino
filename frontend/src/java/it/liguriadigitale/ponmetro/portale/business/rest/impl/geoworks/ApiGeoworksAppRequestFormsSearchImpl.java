package it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks;

import it.liguriadigitale.ponmetro.geoworks.apiclient.AppRequestFormsSearchApi;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsSearchSearch200Response;
import it.liguriadigitale.ponmetro.geoworks.model.RequestFormsSearchDto;

public class ApiGeoworksAppRequestFormsSearchImpl implements AppRequestFormsSearchApi {

  private AppRequestFormsSearchApi instance;

  public ApiGeoworksAppRequestFormsSearchImpl(AppRequestFormsSearchApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public AppRequestFormsSearchSearch200Response appRequestFormsSearchSearch(
      RequestFormsSearchDto arg0) {
    return instance.appRequestFormsSearchSearch(arg0);
  }
}
