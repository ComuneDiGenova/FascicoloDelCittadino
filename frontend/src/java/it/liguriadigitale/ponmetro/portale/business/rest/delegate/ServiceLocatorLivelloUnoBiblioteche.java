package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche.ApiServiziUtenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche.ApiTabelleBaseImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche.ApiTabelleCircolazioneImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche.ApiTabelleUtenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche.ApiUtenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseBibliotecheFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.ServiziUtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleBaseApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleCircolazioneApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleUtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.UtenteApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoBiblioteche {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoBiblioteche getInstance() {
    return new ServiceLocatorLivelloUnoBiblioteche();
  }

  protected ResteasyWebTarget createResteasyWebTarget(String urlApi, Long timeout, String token)
      throws BusinessException {
    try {
      JSON json = new JSON();
      client =
          new ResteasyClientBuilder()
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseBibliotecheFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().toASCIIString());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoBiblioteche] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoBiblioteche] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_COMGE);
  }

  @SuppressWarnings("unused")
  private ResteasyWebTarget createApiWSO2RL(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_RL);
  }

  public ServiziUtenteApi createServiziUtenteApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(ServiziUtenteApi.class);
  }

  public ServiziUtenteApi getApiServiziUtente() throws BusinessException {
    return new ApiServiziUtenteImpl(this.createServiziUtenteApi());
  }

  public TabelleBaseApi createTabelleBaseApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TabelleBaseApi.class);
  }

  public TabelleBaseApi getApiTabelleBase() throws BusinessException {
    return new ApiTabelleBaseImpl(createTabelleBaseApi());
  }

  public TabelleCircolazioneApi createTabelleCircolazioneApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TabelleCircolazioneApi.class);
  }

  public TabelleCircolazioneApi getApiTabelleCircolazione() throws BusinessException {
    return new ApiTabelleCircolazioneImpl(createTabelleCircolazioneApi());
  }

  public TabelleUtenteApi createTabelleUtenteApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TabelleUtenteApi.class);
  }

  public TabelleUtenteApi getApiTabelleUtente() throws BusinessException {
    return new ApiTabelleUtenteImpl(createTabelleUtenteApi());
  }

  public UtenteApi createUtenteApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(UtenteApi.class);
  }

  public UtenteApi getApiUtente() throws BusinessException {
    return new ApiUtenteImpl(createUtenteApi());
  }
}
