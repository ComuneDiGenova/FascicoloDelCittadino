package it.liguriadigitale.ponmetro.portale.business.rest.noyaml.allertecortesia;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;

public interface AllerteCortesiaNoYamlService {

  DettagliUtente getWsLoginEMAIL(String email, String pwd, String cf) throws BusinessException;
}
