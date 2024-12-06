package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.IndividuoApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetVerificaCredibilitaResponseGenericResponse;

public class ApiIndividuoImpl implements IndividuoApi {

  private IndividuoApi instance;

  public ApiIndividuoImpl(IndividuoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public GetVerificaCredibilitaResponseGenericResponse getVerificaCredibilita(String arg0) {
    return instance.getVerificaCredibilita(arg0);
  }
}
