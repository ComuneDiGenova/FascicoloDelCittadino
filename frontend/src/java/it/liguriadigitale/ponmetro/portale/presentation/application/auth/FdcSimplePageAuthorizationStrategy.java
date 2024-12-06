package it.liguriadigitale.ponmetro.portale.presentation.application.auth;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.pojo.audit.builder.AuditDtoBuilder;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.business.globo.GloboFrontendInterface;
import it.liguriadigitale.ponmetro.portale.pojo.enums.TipoUtenteEnum;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.CONTEXT_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page403;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.page.SimplePageAuthorizationStrategy;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.http.WebRequest;

public class FdcSimplePageAuthorizationStrategy extends SimplePageAuthorizationStrategy {

  private static final String GLOBO = "/globo/";
  protected Log log = LogFactory.getLog(getClass());
  private String codiceMaggioli;
  private String codiceFDC;
  private static final String INVALIDO = "invalido";

  public FdcSimplePageAuthorizationStrategy(
      Class<LayoutBasePage> securePageSuperType, Class<UtenteNonAutorizzatoPage> signInPageClass) {
    super(securePageSuperType, signInPageClass);
  }

  @Override
  protected boolean isAuthorized() {

    return true;
  }

  @SuppressWarnings("deprecation")
  @Override
  protected <T extends Page> boolean isPageAuthorized(final Class<T> pageClass) {

    String simpleNamePaginaCorrente = pageClass.getSimpleName();
    log.debug("pageClass:" + simpleNamePaginaCorrente);
    LoginInSession session = (LoginInSession) Session.get();
    boolean isAutorizzata =
        !(PageUtil.isPaginaInElencoFornito(
            simpleNamePaginaCorrente, session.getPagineDisabilitate()));
    log.debug("pageClass:" + simpleNamePaginaCorrente + " isAutorizzata=" + isAutorizzata);
    creaAudit(session, simpleNamePaginaCorrente, isAutorizzata);
    log.debug("Page403.class != pageClass:" + (Page403.class != pageClass));
    if (Page403.class != pageClass) {
      checkUtenteInSessione(session);
    }

    return isAutorizzata;
  }

  private void creaAudit(LoginInSession session, String simpleName, boolean isAutorizzata) {

    Utente utente = session.getUtente();
    String idUtente = " NON LOGGATO ";

    if (utente != null && utente.getIdAnonimoComuneGenova() != null) {
      idUtente = utente.getIdAnonimoComuneGenova().toString();
      AuditDto dto =
          new AuditDtoBuilder()
              .setAmbienteDeploy(Constants.DEPLOY.toString())
              .setIdFCitt(utente.getIdAnonimoComuneGenova())
              .setIsAuthorized(isAutorizzata)
              .setNomePagina(simpleName)
              .setTimeStamp(LocalDateTime.now())
              .setSessionID(utente.getTokenId())
              .setTipoUtente(TipoUtenteEnum.getValoreNumerico(utente.isResidente()))
              .setSesso(utente.getSesso())
              .setAnnoNascita(String.valueOf(utente.getDataDiNascita().getYear()).substring(2))
              .setServizioEsterno(checkNomeServizioEsterno(simpleName))
              .build();

      ServiceLocator.getInstance().getServiziAudit().trace(dto);
    }
    if (isAutorizzata) {
      log.info(
          " AUTHORIZED --> utente: " + idUtente + " puo' avere accesso alla pagina: " + simpleName);
    }
  }

  private String checkNomeServizioEsterno(String simpleName) {
    if (simpleName.equalsIgnoreCase("GloboPage")) {
      try {
        WebRequest request = retrieveWebRequest();
        setCodiciByRequest(request);
        List<Node> servizi = getListaNodiGlobo();
        if (servizi == null
            || servizi.isEmpty()
            || codiceFDC == null
            || codiceFDC.equalsIgnoreCase(INVALIDO)) {
          log.debug(
              "Qualcosa e' andato storto. La lista servizi di globo o il codice fdc sono invalidi");
          return simpleName;
        }
        String titoloServizioPerId = "";
        if (codiceMaggioli != null && !codiceMaggioli.equalsIgnoreCase(INVALIDO)) {
          titoloServizioPerId = getTitoloServizioEsterno(codiceMaggioli, servizi, false);
        } else {
          titoloServizioPerId = getTitoloServizioEsterno(codiceFDC, servizi, true);
        }
        if (titoloServizioPerId.equalsIgnoreCase("")) {
          log.debug("titolo non trovato con ");
          logIfNotNull(codiceFDC, "codiceFDC");
          logIfNotNull(codiceMaggioli, "codiceMaggioli");
          return simpleName;
        }
        return titoloServizioPerId;

      } catch (Exception e) {
        log.debug("Errore in ricerca nome servizio esterno per compilazione audit = \n" + e);
        return simpleName;
      }
    } else {
      return "NO";
    }
  }

  private void logIfNotNull(String value, String nomeValue) {
    if (value != null) {
      log.debug(nomeValue.toUpperCase() + " = " + value);
    } else {
      log.debug(nomeValue.toUpperCase() + " = NULL");
    }
  }

  private String getTitoloServizioEsterno(String codice, List<Node> servizi, boolean isCodiceFDC) {
    String titoloServizioPerId = "";
    List<Node> nodiCombacianti = new ArrayList<>();
    log.debug(
        "getTitoloServizioEsterno ( codice = "
            + codice
            + " , servizi = "
            + servizi.size()
            + " , isCodiceFDC = "
            + isCodiceFDC
            + ")");
    for (Node nodo : servizi) {
      if (((isCodiceFDC) && (nodo.getCodice_fdc().toString().equalsIgnoreCase(codice)))
          || ((!isCodiceFDC) && (nodo.getCodice_maggioli().toString().equalsIgnoreCase(codice)))) {
        log.debug("NODO = " + nodo);
        if (nodo.getTitolo() != null) {
          nodiCombacianti.add(nodo);
        }
      }
    }
    if (nodiCombacianti == null || nodiCombacianti.isEmpty()) {
      log.debug("NODI COMBACIANTI NON TROVATI");
      return titoloServizioPerId;
    }
    if (nodiCombacianti.size() == 1) {
      log.debug("SINGOLO NODO TROVATO");
      return nodiCombacianti.get(0).getTitolo();
    }
    try {
      log.debug("Trovate piu' di una corrispondneza con codice fdc = " + codice);
      List<FunzioniDisponibili> listaFunzioni =
          ServiceLocator.getInstance().getServiziConfigurazione().getFunzioniAbilitate();
      for (FunzioniDisponibili funz : listaFunzioni) {
        if (funz.getIdFunz().toString().equalsIgnoreCase(codice)) {
          log.debug("FUNZIONE = " + funz);
          titoloServizioPerId = funz.getDescrizioneFunz();
        }
      }
    } catch (BusinessException e) {
      log.debug(
          "Errore in ricerca lista funzioni disponibili. FDCSImplePageAuthorizationStrategy > getTitoloServizioEsterno");
    }
    log.debug("TITOLO FUNZIONE ESTERNA = " + titoloServizioPerId);
    return titoloServizioPerId;
  }

  private List<Node> getListaNodiGlobo() throws BusinessException {
    ServiceLocator serviceLocator = ServiceLocator.getInstance();
    GloboFrontendInterface globo = serviceLocator.getGlobo();
    List<Node> servizi = globo.getServiziOnlineGlobo();
    return servizi;
  }

  private void setCodiciByRequest(WebRequest request) {

    String url = request.getUrl().toString();

    String codici = url.substring(url.indexOf(GLOBO) + GLOBO.length());
    String codiceFDC = INVALIDO;
    String codiceMaggioli = INVALIDO;
    if (codici.contains("/")) {
      int indexOfSeparator = codici.indexOf("/");
      codiceFDC = codici.substring(0, indexOfSeparator);
      codiceMaggioli = codici.substring(indexOfSeparator + 1);
    } else {
      codiceFDC = codici;
    }

    if (codiceFDC.contains("?")) {
      codiceFDC = codiceFDC.substring(0, codiceFDC.indexOf("?"));
    }
    if (codiceMaggioli.contains("?")) {
      codiceMaggioli = codiceMaggioli.substring(0, codiceMaggioli.indexOf("?"));
    }

    this.codiceFDC = codiceFDC;
    this.codiceMaggioli = codiceMaggioli;
    log.debug("CODICE FDC = " + codiceFDC);
    log.debug("CODICE MAGGIOLI = " + codiceMaggioli);
  }

  private WebRequest retrieveWebRequest() {
    final RequestCycle requestCycle = RequestCycle.get();
    final WebRequest request = (WebRequest) requestCycle.getRequest();
    return request;
  }

  private void checkUtenteInSessione(LoginInSession session) {
    Utente utenteSessione = session.getUtente();
    if (utenteSessione != null && utenteSessione.getIdAnonimoComuneGenova() != null) {
      log.debug("utenteSessione.isUtenteLoginLocale(): " + utenteSessione.isUtenteLoginLocale());
      WebRequest request = retrieveWebRequest();

      if (!utenteSessione.isUtenteLoginLocale()) {
        log.debug("utenteSessione.isUtenteLoginLocale(): " + utenteSessione.isUtenteLoginLocale());
        Utente utenteHeaders = Utente.getUtenteByHeaderRequest(request);
        log.debug("Utente Headers: " + utenteHeaders);

        if (!utenteHeaders
            .getCodiceFiscaleOperatore()
            .equalsIgnoreCase(utenteSessione.getCodiceFiscaleOperatore())) {
          log.debug("Utente Headers: DIVERSO");
          Utente newUtente = Utente.inizializzaUtenteByHeaderRequest(request);
          session.setUtente(newUtente);
          session.configuraPagineAbilitate();
        } else {
          log.debug("Utente Headers: UGUALE");
        }
      }
    }
  }

  @SuppressWarnings("unused")
  private void checkRedirectDelegabili(String simpleNamePaginaCorrente, LoginInSession session) {

    Utente utente = session.getUtente();
    log.info(" checkRedirectDelegabili");
    if (utente != null && utente.getIdAnonimoComuneGenova() != null) {

      log.info(" checkRedirectDelegabili --> utente: " + utente.getIdAnonimoComuneGenova());
      Application app = session.getApplication();
      boolean isUtenteDelegato = utente.isUtenteDelegato();
      boolean isApplicazioneDeleghe = app.getName().equalsIgnoreCase(CONTEXT_PATH.DELEGHE.name());
      log.info("  app.getName() --> " + app.getName());
      log.info(" isUtenteDelegato --> " + isUtenteDelegato);
      log.info(" isApplicazioneDeleghe --> " + isApplicazioneDeleghe);
      if (isApplicazioneDeleghe && !isUtenteDelegato) {
        log.error("REDIRECT APP DELEGHE  --> con utente delegato=" + utente.isUtenteDelegato());
        session.invalidate();
        String URL_SERVER = "http://localhost:8180";
        throw new RedirectToUrlException(URL_SERVER + "/portale/web/homepage/home");
      } else {
        log.error("PASSA");
      }
    }
  }
}
