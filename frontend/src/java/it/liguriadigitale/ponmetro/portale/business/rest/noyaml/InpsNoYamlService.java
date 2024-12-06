package it.liguriadigitale.ponmetro.portale.business.rest.noyaml;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.AttestazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.DichiarazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.time.LocalDate;

public interface InpsNoYamlService {

  public AttestazioniISEE attestazioneIsee(Utente utente, LocalDate oggi) throws BusinessException;

  public DichiarazioniISEE dichiarazioneIsee(Utente utente, LocalDate oggi)
      throws BusinessException;
}
