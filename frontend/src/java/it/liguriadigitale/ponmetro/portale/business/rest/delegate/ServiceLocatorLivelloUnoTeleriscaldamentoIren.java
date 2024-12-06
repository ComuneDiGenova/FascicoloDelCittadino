package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.teleriscaldamento.ApiTeleriscaldamentoIrenImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseTeleriscaldamentoIren;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.apiclient.InserimentoRichiestaBonusApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoTeleriscaldamentoIren extends ServiceLocatorLivelloUno {

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoTeleriscaldamentoIren getInstance() {
    return new ServiceLocatorLivelloUnoTeleriscaldamentoIren();
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
              .register(ErrorResponseTeleriscaldamentoIren.class)
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
          "[ServiceLocatorLivelloUnoTeleriscaldamentoIren] Errore durante la chiamata delle API Rest ",
          e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoTeleriscaldamentoIren] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 90L, BaseServiceImpl.TOKEN_COMGE);
  }

  private InserimentoRichiestaBonusApi createTeleriscaldamentoIrenApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_TELERISCALDAMENTO_IREN;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(InserimentoRichiestaBonusApi.class);
  }

  @Override
  public InserimentoRichiestaBonusApi getApiTeleriscaldamentoIren() throws BusinessException {
    return new ApiTeleriscaldamentoIrenImpl(this.createTeleriscaldamentoIrenApi());
  }
}
