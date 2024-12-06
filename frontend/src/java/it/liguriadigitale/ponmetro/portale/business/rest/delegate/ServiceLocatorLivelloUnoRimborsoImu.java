package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.api.presentation.rest.catasto.service.CategorieCatastaliApi;
import it.liguriadigitale.ponmetro.api.presentation.rest.home.service.HomeRestInterface;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiCategorieCatastaliImp;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziHomePageImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.TributiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseIstanzeRimborsoFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.tributi.apiclient.FascicoloDelContribuenteRestControllerApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoRimborsoImu extends ServiceLocatorLivelloUno {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  private static final CharSequence URL_API_PROD_COMUNE_GENOVA = "apiprod.comune.genova.it:28243";

  public static ServiceLocatorLivelloUnoRimborsoImu getInstance() {
    return new ServiceLocatorLivelloUnoRimborsoImu();
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
              .register(ErrorResponseIstanzeRimborsoFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().toASCIIString());
      return target;
    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUnoTariEng] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoRimborsoImu] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_COMGE);
  }

  public FascicoloDelContribuenteRestControllerApi getApiTributi() throws BusinessException {
    return new TributiImpl(this.createProxyTributiApi());
  }

  private FascicoloDelContribuenteRestControllerApi createProxyTributiApi()
      throws BusinessException {
    ResteasyWebTarget target = getResteasyWebTargetByUrl(BaseServiceImpl.API_IMU_RIMBORSI_ENG);
    return target.proxy(FascicoloDelContribuenteRestControllerApi.class);
  }

  private ResteasyWebTarget getResteasyWebTargetByUrl(String urlApi) throws BusinessException {
    ResteasyWebTarget target;
    if (urlApi.contains(URL_API_PROD_COMUNE_GENOVA)) target = createWebTargetCallProduzione(urlApi);
    else target = createWebTarget(urlApi);
    return target;
  }

  private ResteasyWebTarget createWebTargetCallProduzione(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_CALL_PRODUZIONE_TO_TEST);
  }

  public HomeRestInterface getApiHomePage() throws BusinessException {
    return new ApiServiziHomePageImpl(createProxyHomePageApi());
  }

  private HomeRestInterface createProxyHomePageApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_SCADENZE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(HomeRestInterface.class);
  }

  public CategorieCatastaliApi getApiCategorieCatastali() throws BusinessException {
    return new ApiCategorieCatastaliImp(createProxyCategorieCatastaliApi());
  }

  private CategorieCatastaliApi createProxyCategorieCatastaliApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_CONFIGURAZIONE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(CategorieCatastaliApi.class);
  }
}
