package it.liguriadigitale.ponmetro.portale.business.login.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.login.services.GestioneLoginInterface;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.Properties;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.http.WebRequest;

public class GestioneLoginImpl extends BaseServiceImpl implements GestioneLoginInterface {

  private static Log log = LogFactory.getLog(GestioneLoginImpl.class);

  /** Metodo che mi restituisce un utente tramite SRM */
  @Override
  public Utente getUtente(String username, final String password, final Properties applicazioni)
      throws BusinessException {

    Utente utente = null;
    username = StringUtils.trim(username);
    username = StringUtils.capitalize(username);
    utente = Utente.getUtenteByUserName(username);

    return utente;
  }

  /** Metodo che mi restituisce un utente da mettere in sessione senza passare pwd a SRM */
  @Override
  public Utente getUtente(String username, Properties applicazioni) throws BusinessException {
    log.debug("[GestioneLoginImpl] login corretto");
    Utente utente = null;
    username = StringUtils.trim(username);
    username = StringUtils.capitalize(username);
    utente = Utente.getUtenteByUserName(username);
    return utente;
  }

  /** Metodo che mi restituisce un utente da mettere in sessione senza chiamare SRM */
  @Override
  public Utente getUtenteFake(String username, String password, Properties applicazioni)
      throws BusinessException {
    username = StringUtils.trim(username);
    username = StringUtils.capitalize(username);
    return Utente.getUtenteByUserName(username);
  }

  @Override
  public Utente getUtenteNAM(WebRequest request) throws BusinessException {
    final Utente utente = Utente.inizializzaUtenteByHeaderRequest(request);
    return utente;
  }

  @Override
  public void logout(final Utente utente) {}

  @Override
  public boolean verificaCodiceFiscaleAutorizzato(String codiceFiscale) throws BusinessException {
    log.debug("[GestioneLoginImpl] verifica autorizzazione del codice fiscale");
    if (StringUtils.isNotEmpty(codiceFiscale)) {
      ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
      return gestioneCloseConnection(instance, codiceFiscale);
    } else if (Constants.DEPLOY == Constants.TIPO_DEPLOY.SVILUPPO) {
      return true;
    } else {
      log.debug("codiceFiscale nullo o vuoto");
      return false;
    }
  }

  private boolean gestioneCloseConnection(ServiceLocatorLivelloUno instance, String codiceFiscale) {
    try {
      return instance.getApiHomePage().getAbilitazioneIngresso(codiceFiscale);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("LOGIN"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
