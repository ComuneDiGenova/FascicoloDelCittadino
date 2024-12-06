package it.liguriadigitale.ponmetro.api.business.scadenze.service;

import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.VScScadenzeResponse;

public interface ScadenzeInterface {

  public VScScadenzeResponse getListaScadenze();

  public VScScadenzeResponse getListaScadenzeByCittadino(Long idCittadino);
}
