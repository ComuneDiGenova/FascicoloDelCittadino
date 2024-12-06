package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.GloboBackendInterface;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.ListaGlobo;

public class ApiServiziGloboBackend implements GloboBackendInterface {

  private GloboBackendInterface instance;

  public ApiServiziGloboBackend(GloboBackendInterface instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ListaGlobo getListaFunzioni() {
    return instance.getListaFunzioni();
  }
}
