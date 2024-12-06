package it.liguriadigitale.ponmetro.portale.business.puntitari.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.puntitari.model.PuntiTari;
import java.io.IOException;

public interface ServiziPuntiTariService {

  PuntiTari getPuntiTari(String codiceFiscale, Integer anno)
      throws BusinessException, ApiException, IOException;
}
