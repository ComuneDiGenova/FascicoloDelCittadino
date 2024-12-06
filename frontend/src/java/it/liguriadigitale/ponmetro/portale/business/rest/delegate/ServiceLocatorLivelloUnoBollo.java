package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseBolloAutoFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoBollo extends ServiceLocatorLivelloUno {

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoBollo getInstance() {
    return new ServiceLocatorLivelloUnoBollo();
  }

  @Override
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
              .register(ErrorResponseBolloAutoFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().getPath());
      return target;
    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUnoBollo] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoBollo] CHIUDO SOCKET HTTP");
      client.close();
    }
  }
}
