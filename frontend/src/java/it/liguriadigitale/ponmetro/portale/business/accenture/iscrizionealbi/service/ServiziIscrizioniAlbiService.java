package it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DatiAccount;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviate;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziIscrizioniAlbiService {

  DatiAccount getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
      throws BusinessException, IOException, ApiException;

  DatiAccount getRecuperoUtenteByIdAccenture(String idAccenture)
      throws BusinessException, IOException, ApiException;

  boolean isPresentTornataByTypo(String tipo);

  DomandeInviate getListaDomandeIscrizioniAlbi(String codiceFiscale, TipologiaRichiestaEnum tipo)
      throws BusinessException, IOException, ApiException;
}
