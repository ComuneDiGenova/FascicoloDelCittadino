package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.bandirealgim.apiclient.AnnouncementApi;
import it.liguriadigitale.ponmetro.bandirealgim.apiclient.AnnouncementQuestionsApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.bandirealgim.ApiAnnouncementApiBandiRealGimImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.bandirealgim.ApiAnnouncementQuestionsBandiRealGimImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseBibliotecheFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.portale.business.rest.util.RealGimFilter;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUnoScaiRealGimm {

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUnoScaiRealGimm getInstance() {
    return new ServiceLocatorLivelloUnoScaiRealGimm();
  }

  protected ResteasyWebTarget createResteasyWebTarget(
      String urlApi, Long timeout, String tokenComune, String tokenScaiRealGimm)
      throws BusinessException {
    try {

      log.debug("ServiceLocatorLivelloUnoScaiRealGimm -- createResteasyWebTarget");

      JSON json = new JSON();
      client =
          new ResteasyClientBuilder()
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseBibliotecheFilter.class)
              .register(new OAuth2Authenticator(tokenComune))
              .register(RealGimFilter.class)
              .register(LogChiamateInUscitaInterceptor.class)
              .build();

      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));

      if (target.getUri() != null) log.debug("target: " + target.getUri().toASCIIString());
      return target;
    } catch (Exception e) {
      log.error(
          "[ServiceLocatorLivelloUnoScaiRealGimm] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUnoScaiRealGimm] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(
        urlApi, -1L, BaseServiceImpl.TOKEN_COMGE, BaseServiceImpl.TOKEN_SCAI_REALGIM);
  }

  private AnnouncementApi createAnnouncementApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_REALGIM_BANDI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AnnouncementApi.class);
  }

  public AnnouncementApi getApiAnnouncement() throws BusinessException {
    return new ApiAnnouncementApiBandiRealGimImpl(this.createAnnouncementApi());
  }

  private AnnouncementQuestionsApi createAnnouncementQuestionsApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_REALGIM_BANDI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AnnouncementQuestionsApi.class);
  }

  public AnnouncementQuestionsApi getApiAnnouncementQuestions() throws BusinessException {
    return new ApiAnnouncementQuestionsBandiRealGimImpl(this.createAnnouncementQuestionsApi());
  }
}
