package it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.apiclient.AmtApi;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiAbbonamentiAmtImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiAssicurazioneVeicoloImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiServiziDemograficoImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.ErrorResponseFilter;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.JSON;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.apiclient.AssicurazioneVeicoloApi;
import it.liguriadigitale.ponmetro.demografico.apiclient.PortaleApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUno {

  protected Log log = LogFactory.getLog(getClass());
  private ResteasyClient client;

  public static ServiceLocatorLivelloUno getInstance() {
    return new ServiceLocatorLivelloUno();
  }

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
              .register(ErrorResponseFilter.class)
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

  public void closeConnection() {
    if (client != null) {
      client.close();
    }
  }

  private ResteasyWebTarget createApi(String urlApi) throws BusinessException {
    try {
      JSON json = new JSON();
      ResteasyClient client =
          new ResteasyClientBuilder()
              .register(json)
              .register(Log.class)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseFilter.class)
              .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE))
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      return target;
    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUno] Errore durante la chiamiata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  private PortaleApi createDemograficoApi() throws BusinessException {

    ResteasyWebTarget target = createApi(BaseServiceImpl.API_COM_GE_DEMOGRAFICO);
    log.debug("URL:" + target.getUri().toASCIIString());
    return target.proxy(PortaleApi.class);
  }

  private AssicurazioneVeicoloApi createAssicurazioneApi() throws BusinessException {

    ResteasyWebTarget target = createApi(BaseServiceImpl.API_COM_GE_ASSICURAZIONE_VEICOLO);
    log.debug("URL:" + target.getUri().toASCIIString());
    return target.proxy(AssicurazioneVeicoloApi.class);
  }

  public PortaleApi getApiDemografico() throws BusinessException {
    return new ApiServiziDemograficoImpl(this.createDemograficoApi());
  }

  public AssicurazioneVeicoloApi getAssicurazioneVeicoloApi() throws BusinessException {
    return new ApiAssicurazioneVeicoloImpl(createAssicurazioneApi());
  }

  private AmtApi createAbbonamentiAmtApi() throws BusinessException {
    ResteasyWebTarget target = createApi(BaseServiceImpl.API_ABBONAMENTI_AMT);
    log.debug("URL:" + target.getUri().toASCIIString());
    return target.proxy(AmtApi.class);
  }

  public AmtApi getApiAbbonamentiAmt() throws BusinessException {
    return new ApiAbbonamentiAmtImpl(this.createAbbonamentiAmtApi());
  }
}
