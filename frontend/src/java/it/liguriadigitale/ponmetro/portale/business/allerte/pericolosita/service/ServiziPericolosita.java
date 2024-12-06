package it.liguriadigitale.ponmetro.portale.business.allerte.pericolosita.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziPericolosita {

  Features getPericolosita(String codiceStrada, String lettera, String numeroCivico, String colore)
      throws BusinessException, ApiException, IOException;
}
