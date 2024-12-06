package it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DatiAccount;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviate;
import java.io.IOException;

public interface ServiziTrasportoBambiniDisabiliService {

  DatiAccount getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
      throws BusinessException, IOException, ApiException;

  DatiAccount getRecuperoUtenteByIdAccenture(String idAccenture)
      throws BusinessException, IOException, ApiException;

  DomandeInviate getListaDomandeTrasportoBambiniDisabili(String codiceFiscale)
      throws BusinessException, IOException, ApiException;
}
