package it.liguriadigitale.ponmetro.api.business.veicoli.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;

public interface AssicurazioneInterface {

  public VerificaAssicurazioneVeicoli getAssicurazione(VeicoloDto veicoloDto)
      throws BusinessException;
}
