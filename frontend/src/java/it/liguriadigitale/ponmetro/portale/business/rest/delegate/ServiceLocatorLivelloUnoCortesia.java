package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.apiclient.GeorefToponomasticaApi;
import it.liguriadigitale.ponmetro.allertecortesia.apiclient.RegistrazioneServiziCortesiaApi;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.apiclient.DecretiApi;
import it.liguriadigitale.ponmetro.edilizia.condono.apiclient.CondoniApi;
import it.liguriadigitale.ponmetro.edilizia.pratiche.apiclient.PraticheApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia.ApiAllerteCortesiaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia.ApiGeorefToponomasticaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia.ApiEdiliziaAbitabilitaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia.ApiEdiliziaCondonoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia.ApiEdiliziaPraticheImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseCortesiaFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoCortesia {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoCortesia getInstance() {
    return new ServiceLocatorLivelloUnoCortesia();
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
              .register(ErrorResponseCortesiaFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().toASCIIString());
      return target;
    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUnoCortesia] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoCortesia] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_COMGE);
  }

  private RegistrazioneServiziCortesiaApi createAllerteCortesiaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ALLERTE_CORTESIA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(RegistrazioneServiziCortesiaApi.class);
  }

  private GeorefToponomasticaApi createGeorefToponomesticaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_GEOREF_TOPONOMASTICA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(GeorefToponomasticaApi.class);
  }

  public GeorefToponomasticaApi getApiGeorefToponomastica() throws BusinessException {
    return new ApiGeorefToponomasticaImpl(this.createGeorefToponomesticaApi());
  }

  public RegistrazioneServiziCortesiaApi getApiAllerteCortesia() throws BusinessException {
    return new ApiAllerteCortesiaImpl(this.createAllerteCortesiaApi());
  }

  public CondoniApi createEdiliziaCondonoApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_EDILIZIA_CONDONO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(CondoniApi.class);
  }

  public CondoniApi getApiEdiliziaCondono() throws BusinessException {
    return new ApiEdiliziaCondonoImpl(this.createEdiliziaCondonoApi());
  }

  public PraticheApi createEdiliziaPraticheApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_EDILIZIA_PRATICHE;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(PraticheApi.class);
  }

  public PraticheApi getApiEdiliziaPratiche() throws BusinessException {
    return new ApiEdiliziaPraticheImpl(this.createEdiliziaPraticheApi());
  }

  public DecretiApi createEdiliziaAbitabilitaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_EDILIZIA_ABITABILITA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(DecretiApi.class);
  }

  public DecretiApi getApiEdiliziaAbitabilita() throws BusinessException {
    return new ApiEdiliziaAbitabilitaImpl(this.createEdiliziaAbitabilitaApi());
  }
}
