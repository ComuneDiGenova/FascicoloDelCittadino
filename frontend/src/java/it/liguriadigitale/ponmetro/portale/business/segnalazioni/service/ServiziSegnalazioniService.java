package it.liguriadigitale.ponmetro.portale.business.segnalazioni.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.segnalazioni.model.PostCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.Stat;
import java.io.IOException;

public interface ServiziSegnalazioniService {

  PostCollection getPostCittadino(Utente utente)
      throws BusinessException, ApiException, IOException;

  Stat getSegnalazioniInviateAperteInCarico(Utente utente)
      throws BusinessException, ApiException, IOException;
}
