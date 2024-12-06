package it.liguriadigitale.ponmetro.portale.business.accessoaivarchi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransitsResult;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziAccessoAiVarchiService {

  SuspectTransitsResult getSuspectTransits(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  String getValoreDaDb(String chiave);
}
