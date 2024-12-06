package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseGoadevBollettinoFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoGoadevBollettino extends ServiceLocatorLivelloUno {

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoGoadevBollettino getInstance() {
    return new ServiceLocatorLivelloUnoGoadevBollettino();
  }

  @Override
  protected ResteasyWebTarget createResteasyWebTarget(String urlApi, Long timeout, String token)
      throws BusinessException {

    /*
     * ClientConnectionManager cm = new PoolingClientConnectionManager();
     * HttpClient httpClient = new DefaultHttpClient(cm);
     * ApacheHttpClient4Engine engine = new
     * ApacheHttpClient4Engine(httpClient);
     */

    try {
      JSON json = new JSON();
      client =
          new ResteasyClientBuilder()
              // .httpEngine(engine)
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseGoadevBollettinoFilter.class)
              .register(new OAuth2Authenticator(token))
              .connectionPoolSize(200)
              .connectionTTL(5, TimeUnit.SECONDS)
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().getPath());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoGoadevBollettino] Errore durante la chiamata delle API Rest ",
          e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoDppVerbali] CHIUDO SOCKET HTTP");
      client.close();
    }
  }
}
