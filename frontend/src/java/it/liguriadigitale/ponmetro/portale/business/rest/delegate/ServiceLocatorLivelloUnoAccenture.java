package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.accenture.apiclient.DefaultApi;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.iscrizioni.albi.apiclient.IscrizioniAlbiElettoraliApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.accenture.ApiAccentureImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.accenture.ApiRichiestaAssistenzaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.iscrizionealbi.ApiIscrizioneAlbiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazionedannibeniprivati.ApiSegnalazioneDanniBeniPrivatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.trasportodisabili.ApiTrasportoDisabiliImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseBibliotecheFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.richiesta.assistenza.apiclient.RichiestaAssistenzaApi;
import it.liguriadigitale.ponmetro.trasporto.disabili.apiclient.TrasportoBambiniDisabiliAScuolaApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoAccenture {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoAccenture getInstance() {
    return new ServiceLocatorLivelloUnoAccenture();
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
              .register(ErrorResponseBibliotecheFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null)
        log.debug(
            "target Accenture: "
                + target.getUri().toASCIIString()
                + "\n token accenture = "
                + token);
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoAccenture] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API Accenture");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoAccenture] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, -1L, BaseServiceImpl.TOKEN_ACCENTURE);
  }

  public DefaultApi createServiziAccentureApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(DefaultApi.class);
  }

  public DefaultApi getApiAccenture() throws BusinessException {
    return new ApiAccentureImpl(this.createServiziAccentureApi());
  }

  public TrasportoBambiniDisabiliAScuolaApi serviziTrasportiDisabili() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TrasportoBambiniDisabiliAScuolaApi.class);
  }

  public TrasportoBambiniDisabiliAScuolaApi getApiTrasporti() throws BusinessException {
    return new ApiTrasportoDisabiliImpl(this.serviziTrasportiDisabili());
  }

  public IscrizioniAlbiElettoraliApi serviziIscrizioneAlbo() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(IscrizioniAlbiElettoraliApi.class);
  }

  public IscrizioniAlbiElettoraliApi getApiIscrizioneAlbo() throws BusinessException {
    return new ApiIscrizioneAlbiImpl(this.serviziIscrizioneAlbo());
  }

  public RichiestaAssistenzaApi createServiziRichiestaAssistenzaApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(RichiestaAssistenzaApi.class);
  }

  public RichiestaAssistenzaApi getApiRichiestaAssistenza() throws BusinessException {
    return new ApiRichiestaAssistenzaImpl(this.createServiziRichiestaAssistenzaApi());
  }

  public it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.apiclient.DefaultApi
      serviziSegnalazioneDaniBeniPrivati() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(
        it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.apiclient.DefaultApi.class);
  }

  public it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.apiclient.DefaultApi
      getApiSegnalazioneDaniBeniPrivati() throws BusinessException {
    return new ApiSegnalazioneDanniBeniPrivatiImpl(this.serviziSegnalazioneDaniBeniPrivati());
  }
}
