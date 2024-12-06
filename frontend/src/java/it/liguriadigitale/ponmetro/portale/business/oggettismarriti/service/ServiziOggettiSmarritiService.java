package it.liguriadigitale.ponmetro.portale.business.oggettismarriti.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.oggettismarriti.model.OggettiSmarriti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;
import java.util.List;

public interface ServiziOggettiSmarritiService {

  List<OggettiSmarriti> getListaOggettiSmarriti(String codiceFiscale)
      throws BusinessException, ApiException, IOException;
}
