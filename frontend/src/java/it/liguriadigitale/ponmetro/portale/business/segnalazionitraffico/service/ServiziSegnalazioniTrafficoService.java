package it.liguriadigitale.ponmetro.portale.business.segnalazionitraffico.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import java.util.List;

public interface ServiziSegnalazioniTrafficoService {

  public List<DatiSegnalazioneTraffico> getListSegnalazioniTraffico()
      throws BusinessException, ApiException;
}
