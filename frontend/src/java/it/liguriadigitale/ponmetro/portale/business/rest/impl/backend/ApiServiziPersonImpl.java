package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;

public class ApiServiziPersonImpl implements PortaleApi {

  private PortaleApi instance;

  public ApiServiziPersonImpl(
      it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi createPersonApi) {
    super();
    instance = createPersonApi;
  }

  @Override
  public IdCPVPerson cpvPersonCodiceFiscaleGet(String arg0) {
    return instance.cpvPersonCodiceFiscaleGet(arg0);
  }
}
