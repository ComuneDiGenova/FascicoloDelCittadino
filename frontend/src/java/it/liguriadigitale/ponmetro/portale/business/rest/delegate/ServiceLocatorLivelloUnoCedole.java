package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.DomandaApi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.UtilsApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati.ApiDomandaPermessiPersonalizzatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati.ApiUtilsPermessiPersonalizzatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole.ApiCedoleLibrarieImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole.ApiUtilitaCedoleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseCedoleFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.CedoleFdCApi;
import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.PortaleFdcApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoCedole {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoCedole getInstance() {
    return new ServiceLocatorLivelloUnoCedole();
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
              .register(ErrorResponseCedoleFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .connectionPoolSize(200)
              .connectionTTL(5, TimeUnit.SECONDS)
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
      log.debug("[ServiceLocatorLivelloUno] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  @SuppressWarnings("unused")
  private ResteasyWebTarget createWebTargetCallProduzione(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_CALL_PRODUZIONE_TO_TEST);
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_COMGE);
  }

  public CedoleFdCApi getApiCedoleLibrarie() throws BusinessException {
    return new ApiCedoleLibrarieImpl(this.createProxyCedoleLibrarieApi());
  }

  private CedoleFdCApi createProxyCedoleLibrarieApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CEDOLE_LIBRARIE);
    return target.proxy(CedoleFdCApi.class);
  }

  public PortaleFdcApi getApiUtilitaCedoleLibrarie() throws BusinessException {
    return new ApiUtilitaCedoleImpl(this.createProxyUtilitaCedoleLibrarieApi());
  }

  private PortaleFdcApi createProxyUtilitaCedoleLibrarieApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CEDOLE_LIBRARIE);
    return target.proxy(PortaleFdcApi.class);
  }

  DomandaApi createDomandaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PERMESSI_PERSONALIZZATI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(DomandaApi.class);
  }

  public DomandaApi getApiDomanda() throws BusinessException {
    return new ApiDomandaPermessiPersonalizzatiImpl(this.createDomandaApi());
  }

  UtilsApi createUtilsApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PERMESSI_PERSONALIZZATI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(UtilsApi.class);
  }

  public UtilsApi getApiUtils() throws BusinessException {
    return new ApiUtilsPermessiPersonalizzatiImpl(this.createUtilsApi());
  }
}
