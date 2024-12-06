package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.RicorsiAlPrefettoApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsiAlPrefettoCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsiResponse;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsoAlPrefetto;

public class ApiRicorsiAlPrefettoImpl implements RicorsiAlPrefettoApi {

  private RicorsiAlPrefettoApi instance;

  public ApiRicorsiAlPrefettoImpl(RicorsiAlPrefettoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public RicorsiAlPrefettoCollection getRicorsiAlPrefetto(String arg0) {
    return instance.getRicorsiAlPrefetto(arg0);
  }

  @Override
  public RicorsiResponse postRicorsoAlPrefetto(RicorsoAlPrefetto arg0) {
    return instance.postRicorsoAlPrefetto(arg0);
  }
}
