package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.VScScadenzeResponse;
import it.liguriadigitale.ponmetro.api.presentation.rest.scadenze.service.ScadenzeRestInterface;

public class ApiServiziScadenzeImpl implements ScadenzeRestInterface {

  private ScadenzeRestInterface instance;

  public ApiServiziScadenzeImpl(ScadenzeRestInterface createScadenzeApi) {
    instance = createScadenzeApi;
  }

  @Override
  public VScScadenzeResponse getListaScadenze() {
    return instance.getListaScadenze();
  }

  @Override
  public void verificaScadenzePersonalizzate(ScadenzePersonalizzateDto arg0) {
    instance.verificaScadenzePersonalizzate(arg0);
  }

  @Override
  public VScScadenzeResponse getListaScadenzeCittadino(Long arg0) {
    return instance.getListaScadenzeCittadino(arg0);
  }

  @Override
  public VScScadenzeResponse isUltimoAccessoScadenze24H(Long arg0) {
    return instance.isUltimoAccessoScadenze24H(arg0);
  }
}
