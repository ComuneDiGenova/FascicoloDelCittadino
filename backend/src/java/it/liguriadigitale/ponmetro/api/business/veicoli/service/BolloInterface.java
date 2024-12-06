package it.liguriadigitale.ponmetro.api.business.veicoli.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import java.util.List;

public interface BolloInterface {

  public List<Bollo> getListaDettagliBolli(VeicoloDto veicolo) throws BusinessException;
}
