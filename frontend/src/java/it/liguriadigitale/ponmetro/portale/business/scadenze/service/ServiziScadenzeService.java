package it.liguriadigitale.ponmetro.portale.business.scadenze.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.util.List;

public interface ServiziScadenzeService {

  public List<VScScadenzeUt> getListaScadenzeFiltrate(int mese, int anno, Utente utente)
      throws BusinessException, ApiException;

  public void inizializzaScadenze(Utente utente) throws BusinessException;
}
