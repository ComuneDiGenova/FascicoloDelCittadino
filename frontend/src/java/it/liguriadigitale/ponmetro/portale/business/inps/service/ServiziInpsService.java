package it.liguriadigitale.ponmetro.portale.business.inps.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.AttestazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.DichiarazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.time.LocalDate;

public interface ServiziInpsService {

  public AttestazioniISEE verificaPresentazioneISEE(Utente utente, LocalDate oggi)
      throws BusinessException;

  public DichiarazioniISEE getDichiarazioneISEE(Utente utente, LocalDate oggi)
      throws BusinessException;
}
