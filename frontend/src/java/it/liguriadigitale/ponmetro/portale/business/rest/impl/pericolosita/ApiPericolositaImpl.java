package it.liguriadigitale.ponmetro.portale.business.rest.impl.pericolosita;

import it.liguriadigitale.ponmetro.pericolosita.apiclient.PericolositaApi;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;

public class ApiPericolositaImpl implements PericolositaApi {

  private PericolositaApi instance;

  public ApiPericolositaImpl(PericolositaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Features searchFeature(String arg0, String arg1, String arg2, String arg3) {
    return instance.searchFeature(arg0, arg1, arg2, arg3);
  }
}
