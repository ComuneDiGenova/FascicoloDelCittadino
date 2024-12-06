package it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiServiziUtenteImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.api.ApiTabelleCircolazioneImpl;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.ErrorResponseFilterBiblioteche;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.util.JSON;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.ServiziUtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleCircolazioneApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoBiblioteche {

  protected Log log = LogFactory.getLog(getClass());
  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoBiblioteche getInstance() {
    return new ServiceLocatorLivelloUnoBiblioteche();
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
              .register(ErrorResponseFilterBiblioteche.class)
              .register(new OAuth2Authenticator(token))
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri().toASCIIString());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoBiblioteche] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_COMGE);
  }

  public ServiziUtenteApi createServiziUtenteApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(ServiziUtenteApi.class);
  }

  public ServiziUtenteApi getApiServiziUtente() throws BusinessException {
    return new ApiServiziUtenteImpl(this.createServiziUtenteApi());
  }

  public TabelleCircolazioneApi createTabelleCircolazioneApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BIBLIOTECHE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TabelleCircolazioneApi.class);
  }

  public TabelleCircolazioneApi getApiTabelleCircolazione() throws BusinessException {
    return new ApiTabelleCircolazioneImpl(createTabelleCircolazioneApi());
  }
}
