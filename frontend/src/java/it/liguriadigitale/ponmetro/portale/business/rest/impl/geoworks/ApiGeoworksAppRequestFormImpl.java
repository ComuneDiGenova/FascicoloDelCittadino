package it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks;

import it.liguriadigitale.ponmetro.geoworks.apiclient.AppRequestFormsApi;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetRfStatusHistoryFromRfId200Response;
import it.liguriadigitale.ponmetro.geoworks.model.AppRequestFormsGetSingleRF200Response;

public class ApiGeoworksAppRequestFormImpl implements AppRequestFormsApi {

  private AppRequestFormsApi instance;

  public ApiGeoworksAppRequestFormImpl(AppRequestFormsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public AppRequestFormsGetRfStatusHistoryFromRfId200Response
      appRequestFormsGetRfStatusHistoryFromRfId(Integer arg0) {
    return instance.appRequestFormsGetRfStatusHistoryFromRfId(arg0);
  }

  @Override
  public AppRequestFormsGetSingleRF200Response appRequestFormsGetSingleRF(
      Integer arg0, Integer arg1, Integer arg2) {
    return instance.appRequestFormsGetSingleRF(arg0, arg1, arg2);
  }
}
