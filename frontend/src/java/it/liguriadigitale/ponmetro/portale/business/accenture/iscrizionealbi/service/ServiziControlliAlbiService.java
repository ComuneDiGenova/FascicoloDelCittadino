package it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.controlli.albi.model.Elettorali;
import it.liguriadigitale.ponmetro.controlli.albi.model.Schedario;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziControlliAlbiService {

  Elettorali getPresidenti(String codiceFiscale)
      throws BusinessException, IOException, ApiException;

  Schedario getSchedario(String codiceFiscale) throws BusinessException, IOException, ApiException;

  Elettorali getScrutatori(String codiceFiscale)
      throws BusinessException, IOException, ApiException;
}
