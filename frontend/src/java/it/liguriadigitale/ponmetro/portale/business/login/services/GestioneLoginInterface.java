package it.liguriadigitale.ponmetro.portale.business.login.services;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.util.Properties;
import org.apache.wicket.request.http.WebRequest;

public interface GestioneLoginInterface {

  public Utente getUtente(String username, String password, Properties applicazioni)
      throws BusinessException;

  public Utente getUtente(String username, Properties applicazioni) throws BusinessException;

  public Utente getUtenteFake(String username, String password, Properties applicazioni)
      throws BusinessException;

  public Utente getUtenteNAM(WebRequest request) throws BusinessException;

  public void logout(Utente utente);

  public boolean verificaCodiceFiscaleAutorizzato(String codiceFiscale) throws BusinessException;
}
