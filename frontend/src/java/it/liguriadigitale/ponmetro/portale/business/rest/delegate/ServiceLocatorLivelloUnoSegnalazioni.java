package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni.ApiPostsImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni.ApiStatsImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.portale.business.rest.util.SensorAuthenticator;
import it.liguriadigitale.ponmetro.segnalazioni.apiclient.PostsApi;
import it.liguriadigitale.ponmetro.segnalazioni.apiclient.StatisticsApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoSegnalazioni {

  private ResteasyClient client;

  protected Log log = LogFactory.getLog(getClass());

  public static ServiceLocatorLivelloUnoSegnalazioni getInstance() {
    return new ServiceLocatorLivelloUnoSegnalazioni();
  }

  protected ResteasyWebTarget createResteasyWebTarget(
      String urlApi, Long timeout, String tokenComune, String tokenSegnalazioni)
      throws BusinessException {
    try {
      JSON json = new JSON();
      ResteasyClient client =
          new ResteasyClientBuilder()
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseFilter.class)
              .register(new OAuth2Authenticator(tokenComune))
              .register(new SensorAuthenticator(tokenSegnalazioni))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null)
        log.debug("target SLLUSegnalazioni: " + target.getUri().getPath());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoSegnalazioni] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUno] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(
        urlApi, -1L, BaseServiceImpl.TOKEN_COMGE, BaseServiceImpl.TOKEN_SEGNALAZIONI);
  }

  public PostsApi getApiSegnalazioniPost() throws BusinessException {
    return new ApiPostsImpl(this.createProxyPostApi());
  }

  private PostsApi createProxyPostApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_SEGNALAZIONI);
    return target.proxy(PostsApi.class);
  }

  public StatisticsApi getApiSegnalazioniStat() throws BusinessException {
    return new ApiStatsImpl(this.createProxyStatApi());
  }

  private StatisticsApi createProxyStatApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_SEGNALAZIONI);
    return target.proxy(StatisticsApi.class);
  }
}
