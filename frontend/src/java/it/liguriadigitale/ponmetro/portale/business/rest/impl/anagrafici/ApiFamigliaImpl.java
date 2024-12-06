package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.FamigliaApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTipologiaFamigliaResponseGenericResponse;

public class ApiFamigliaImpl implements FamigliaApi {

  private FamigliaApi instance;

  public ApiFamigliaImpl(FamigliaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public GetElencoTipologiaFamigliaResponseGenericResponse getElencoTipologiaFamiglia() {
    return instance.getElencoTipologiaFamiglia();
  }
}
