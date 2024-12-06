package it.liguriadigitale.ponmetro.api.business.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import java.util.List;

public interface ServiziGloboInterface {

  public List ricercaFunzioniGlobo() throws BusinessException;
}
