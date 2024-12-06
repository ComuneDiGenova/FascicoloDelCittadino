package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.anonim.apiclient.BackendApi;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;

public class ApiAnonimizzaNonResidentiImpl implements BackendApi {

  private BackendApi instance;

  public ApiAnonimizzaNonResidentiImpl(BackendApi createProxyBackendApi) {
    instance = createProxyBackendApi;
  }

  @Override
  public IdCPVPerson anonimizzaPost(AnonimoData arg0) {
    return instance.anonimizzaPost(arg0);
  }
}
