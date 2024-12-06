package it.liguriadigitale.ponmetro.portale.business.ricorsialprefetto.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsiAlPrefettoCollection;

public interface ServiziRicorsiAlPrefettoService {

  RicorsiAlPrefettoCollection getListaRircorsiAlPrefetto(String codiceFiscale)
      throws BusinessException, ApiException;
}
