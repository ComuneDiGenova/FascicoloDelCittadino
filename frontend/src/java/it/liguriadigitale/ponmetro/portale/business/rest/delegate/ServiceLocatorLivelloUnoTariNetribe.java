package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariAgevolazioneTariffariaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariBollettinoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariDocumentiPdfImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariRimborsoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariSintesiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseTariNetribe;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseTariRimborsiNetribe;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.AgevolazioneTariffariaTariApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.BollettinoApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.DocumentiPdfApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.RimborsoApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.SintesiApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoTariNetribe extends ServiceLocatorLivelloUno {

  private ResteasyClientBuilder builder;
  private ResteasyClient client;

  private boolean isRimborsiOContributo;

  public static ServiceLocatorLivelloUnoTariNetribe getInstance() {
    return new ServiceLocatorLivelloUnoTariNetribe();
  }

  public ServiceLocatorLivelloUnoTariNetribe getInstance(boolean isRimborsiOContributo) {
    this.isRimborsiOContributo = isRimborsiOContributo;
    return new ServiceLocatorLivelloUnoTariNetribe();
  }

  @Override
  protected ResteasyWebTarget createResteasyWebTarget(String urlApi, Long timeout, String token)
      throws BusinessException {

    log.debug(
        "ServiceLocatorLivelloUnoTariNetribe isRimborsiOContributo = " + isRimborsiOContributo);

    try {
      JSON json = new JSON();
      builder =
          new ResteasyClientBuilder()
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(new OAuth2Authenticator(token))
              .connectionPoolSize(200)
              .connectionTTL(5, TimeUnit.SECONDS)
              .register(LogChiamateInUscitaInterceptor.class);

      if (isRimborsiOContributo) {
        builder.register(ErrorResponseTariRimborsiNetribe.class);
      } else {
        builder.register(ErrorResponseTariNetribe.class);
      }

      client = builder.build();

      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().getPath());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoTariNetribe] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  @Override
  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoTariNetribe] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 120L, BaseServiceImpl.TOKEN_COMGE);
  }

  public SintesiApi getApiTariNetribeSintesi() throws BusinessException {
    return new TariSintesiImpl(this.createProxyTariNetribeSintesiApi());
  }

  private SintesiApi createProxyTariNetribeSintesiApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(SintesiApi.class);
  }

  public BollettinoApi getApiTariNetribeBollettino() throws BusinessException {
    return new TariBollettinoImpl(this.createProxyTariNtribeBollettinoApi());
  }

  private BollettinoApi createProxyTariNtribeBollettinoApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(BollettinoApi.class);
  }

  public AgevolazioneTariffariaTariApi getApiTariAgevolazioneTariffaria() throws BusinessException {
    return new TariAgevolazioneTariffariaImpl(this.createProxyTariAgevolazioneTariffaria());
  }

  private AgevolazioneTariffariaTariApi createProxyTariAgevolazioneTariffaria()
      throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(AgevolazioneTariffariaTariApi.class);
  }

  public DocumentiPdfApi getApiTariDocumentiPdfApi() throws BusinessException {
    return new TariDocumentiPdfImpl(this.createProxyTariDocumentiPdfApi());
  }
  ;

  private DocumentiPdfApi createProxyTariDocumentiPdfApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(DocumentiPdfApi.class);
  }

  public RimborsoApi getApiTariRimborso() throws BusinessException {
    return new TariRimborsoImpl(this.createProxyTariRimborsoApi());
  }
  ;

  private RimborsoApi createProxyTariRimborsoApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(RimborsoApi.class);
  }
}
