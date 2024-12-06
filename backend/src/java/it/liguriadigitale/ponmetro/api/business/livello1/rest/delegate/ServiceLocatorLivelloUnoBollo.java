package it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiVeicoloImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.ErrorResponseBolloAutoFilter;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.JSON;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.tassaauto.apiclient.TassaAutoApi;
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
  protected ResteasyWebTarget createApi(String urlApi, Long timeout, String token)
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
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().getPath());
      return target;
    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUno] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      client.close();
    }
  }

  private TassaAutoApi createTassaAutoApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_TASSA_AUTO;
    ResteasyWebTarget target = createApi(urlApi, -1L, BaseServiceImpl.TOKEN_COMGE);
    return target.proxy(TassaAutoApi.class);
  }

  public TassaAutoApi getApiTassaAuto() throws BusinessException {
    return new ApiVeicoloImpl(this.createTassaAutoApi());
  }
}
