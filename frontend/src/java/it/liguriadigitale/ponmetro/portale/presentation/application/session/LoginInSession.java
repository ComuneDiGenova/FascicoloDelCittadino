package it.liguriadigitale.ponmetro.portale.presentation.application.session;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.pojo.common.FrameworkUtente;
import it.liguriadigitale.framework.presentation.components.application.session.AbstractLoginInSession;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

@SuppressWarnings("rawtypes")
public class LoginInSession extends AbstractLoginInSession {

  private static final long serialVersionUID = -9100593136750703305L;
  private Utente utente;
  private List<DatiCompletiFunzione> pagineDisabilitate;
  private List<FunzioniDisponibili> pagineAbilitate;

  public LoginInSession(final Request req) {
    super(req);
  }

  @Override
  public boolean authenticate(final String username, final String password) {

    Properties fileProp = loadProperties("/resources/webapp-custom.properties");
    log.debug("authenticate: " + utente);
    if (utente == null) {
      try {
        if (Constants.DEPLOY == TIPO_DEPLOY.SVILUPPO
            || Constants.DEPLOY == TIPO_DEPLOY.INTEGRAZIONE
            || Constants.DEPLOY == TIPO_DEPLOY.TEST_LOGIN
            || Constants.DEPLOY == TIPO_DEPLOY.DEMO) {
          utente =
              ServiceLocator.getInstance()
                  .getGestioneLogin()
                  .getUtenteFake(username, password, fileProp);
        } else {
          try {
            utente =
                ServiceLocator.getInstance().getGestioneLogin().getUtenteNAM(retrieveWebRequest());
            log.debug("utente is residente: " + utente.isResidente());
          } catch (final BusinessException e) {
            log.error("Errore durante il recupero degli headers NAM: ", e);
            throw new RestartResponseAtInterceptPageException(Page403.class);
          }
        }
        if ((pagineAbilitate == null) || (pagineAbilitate.isEmpty())) {
          configuraPagineAbilitate();
        }

        if ((pagineDisabilitate == null) || (pagineDisabilitate.isEmpty())) {
          configuraPagineDisabilitate();
        }

      } catch (BusinessException e) {
        log.error("Errore durante la fase di autenticazione: ", e);
        throw new RestartResponseAtInterceptPageException(Page403.class);
      }
    }
    log.debug("utente:" + utente.getCodiceFiscaleOperatore());
    return utente != null;
  }

  public void configuraPagineAbilitate() {
    // QUESTA E' una WHITELIST
    List<FunzioniDisponibili> nuovaLista = new ArrayList<>();
    try {
      List<FunzioniDisponibili> lista =
          ServiceLocator.getInstance().getServiziConfigurazione().getFunzioniAbilitate();

      for (FunzioniDisponibili funzione : lista) {
        // if (funzione.getDenominazioneFunz()!=null) {
        //	log.debug("configuraPagineAbilitate funzione in lista con nome
        // "+funzione.getDenominazioneFunz());
        // }

        if (Boolean.TRUE.equals(getUtente().isResidente())
            && Boolean.TRUE.equals(funzione.getFlagResidente())) {
          nuovaLista.add(funzione);
        }
        if (Boolean.FALSE.equals(getUtente().isResidente())
            && Boolean.TRUE.equals(funzione.getFlagNonResidente())) {
          nuovaLista.add(funzione);
        }
      }

    } catch (BusinessException e) {
      log.debug("Impossibile inizializzare funzioni");
    }
    log.debug("Lista pagine abilitate: CREATA");
    // log.debug("configuraPagineAbilitate nuovaLista=\n"+nuovaLista );
    setPagineAbilitate(nuovaLista);
  }

  @Deprecated
  private void configuraPagineDisabilitate() {
    // QUESTA E' una BLACKLIST
    pagineDisabilitate = new ArrayList<>();
    try {
      List<DatiCompletiFunzione> listaWidgetDaConfigurare =
          ServiceLocator.getInstance().getServiziConfigurazione().getListaFunzioni(getUtente());
      for (DatiCompletiFunzione funzione : listaWidgetDaConfigurare) {

        String nomeClasse = funzione.getDatiFunzione().getClassePagina();

        if (funzione.getDatiFunzione().getClassePagina() != null) {
          if (!funzione.getDatiSezione().getFlagAbilitazione()
              || !funzione.getDatiComparto().getFlagAbilitazione()
              || !funzione.getDatiFunzione().getFlagAbilitazione()) {

            pagineDisabilitate.add(funzione);

          } else {
            if (getUtente().isResidente() && !funzione.getDatiFunzione().getFlagResidente()) {
              pagineDisabilitate.add(funzione);
            } else if (!getUtente().isResidente()
                && !funzione.getDatiFunzione().getFlagNonResidente()) {
              pagineDisabilitate.add(funzione);
            }
          }
        }
      }
    } catch (BusinessException e) {
      log.error("[LoginSession] Impossibile configurare le pagine disabilitate");
    }
  }

  @Override
  public Roles getRoles() {
    return null;
  }

  private Properties loadProperties(final String filename) {
    Properties prop = new Properties();
    InputStream file = getClass().getResourceAsStream(filename);
    try {
      prop.load(file);
      file.close();
    } catch (IOException e) {
      log.error("Errore reperimento file" + e.getMessage());
      return null;
    }
    return prop;
  }

  @Override
  public Utente getUtente() {
    return utente;
  }

  public void setUtente(final Utente utente) {
    this.utente = utente;
  }

  @Override
  protected FrameworkUtente getUtenteBusiness(String username, String password)
      throws BusinessException {
    return null;
  }

  @Deprecated
  public List<DatiCompletiFunzione> getPagineDisabilitate() {
    return pagineDisabilitate;
  }

  public List<String> getNomiPagineAbilitate() {

    List<String> lista = new ArrayList<>();
    if (getPagineAbilitate() == null) {
      configuraPagineAbilitate();
    }
    for (FunzioniDisponibili funzione : getPagineAbilitate()) {
      lista.add(funzione.getClassePaginaStd());
    }
    return lista;
  }

  private WebRequest retrieveWebRequest() {
    final RequestCycle requestCycle = RequestCycle.get();
    final WebRequest request = (WebRequest) requestCycle.getRequest();
    return request;
  }

  public List<FunzioniDisponibili> getPagineAbilitate() {
    return pagineAbilitate;
  }

  public void setPagineAbilitate(List<FunzioniDisponibili> pagineAbilitate) {
    this.pagineAbilitate = pagineAbilitate;
  }
}
