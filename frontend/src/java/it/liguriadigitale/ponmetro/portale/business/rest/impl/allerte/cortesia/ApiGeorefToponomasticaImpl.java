package it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia;

import it.liguriadigitale.ponmetro.allertecortesia.apiclient.GeorefToponomasticaApi;
import it.liguriadigitale.ponmetro.allertecortesia.model.Risposta;

public class ApiGeorefToponomasticaImpl implements GeorefToponomasticaApi {

  private GeorefToponomasticaApi instance;

  public ApiGeorefToponomasticaImpl(GeorefToponomasticaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Risposta rstGetCiviciRaggruppati(String arg0) {
    return instance.rstGetCiviciRaggruppati(arg0);
  }
}
