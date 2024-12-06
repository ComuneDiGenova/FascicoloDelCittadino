package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.geoserver.apiclient.GeoserverRestControllerApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.GeoserverRestControllerApiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoGeoserver {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoGeoserver getInstance() {
    return new ServiceLocatorLivelloUnoGeoserver();
  }

  protected ResteasyWebTarget createResteasyWebTarget(String urlApi, Long timeout, String token)
      throws BusinessException {

    try {
      // JSON json = new JSON();

      client =
          new ResteasyClientBuilder()
              // .httpEngine(engine)
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              /*.register(json)*/ .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .connectionPoolSize(200)
              .connectionTTL(5, TimeUnit.SECONDS)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().getPath());
      return target;

    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoGeoserver] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoGeoserver] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_COMGE);
  }

  public GeoserverRestControllerApi getApiGeoserver() throws BusinessException {
    return new GeoserverRestControllerApiImpl(this.createGeoserverApi());
  }

  public GeoserverRestControllerApi createGeoserverApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_GEOSERVER;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(GeoserverRestControllerApi.class);
  }
}
