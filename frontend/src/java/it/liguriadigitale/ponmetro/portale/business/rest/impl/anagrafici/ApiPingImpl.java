package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.PingApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PingResultDto;

public class ApiPingImpl implements PingApi {

  private PingApi instance;

  public ApiPingImpl(PingApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public PingResultDto get() {
    return instance.get();
  }

  @Override
  public PingResultDto getWithAuth() {
    return instance.getWithAuth();
  }
}
