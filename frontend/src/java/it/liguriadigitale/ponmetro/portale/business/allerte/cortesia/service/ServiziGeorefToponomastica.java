package it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.Risposta;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziGeorefToponomastica {

  Risposta getWsGetGeorefToponomastica(String strada)
      throws BusinessException, ApiException, IOException;
}
