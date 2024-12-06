package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.ristorazione.ApiServiziRistorazionePortaleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseTeleriscaldamentoIren;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti extends ServiceLocatorLivelloUno {

  private ResteasyClient client;

  public static ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti getInstance() {
    return new ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti();
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
          "[ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti] Errore durante la chiamata delle API Rest ",
          e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 120L, BaseServiceImpl.TOKEN_COMGE);
  }

  public PortaleApi getApiIscrizioneNonResidenti() throws BusinessException {
    return new ApiServiziRistorazionePortaleImpl(this.createProxyIscrizioneNonResidenteApi());
  }

  private PortaleApi createProxyIscrizioneNonResidenteApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_RISTORAZIONE);
    return target.proxy(it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi.class);
  }
}
