package it.liguriadigitale.ponmetro.portale.business.rest.noyamlbollo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.tassaauto.model.VeicoliAttivi;

public interface BolloNoYamlService {

  VeicoliAttivi getVeicoliAttivi(String cf) throws BusinessException;
}
