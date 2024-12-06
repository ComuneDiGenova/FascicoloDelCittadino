package it.liguriadigitale.ponmetro.portale.business.rest.noyaml.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.InpsResolver;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnline;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnlineSettings;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class GloboNoYamlImpl implements GloboNoYamlService {

  // private static final String API_SPORTELLO_GLOBO =
  // "https://apitest.comune.genova.it:28243/sptel_sportello_telem/";
  // private static final String URN_SMART_COMUNE_GENOVA =
  // "https://smart.comune.genova.it/";
  private Log log = LogFactory.getLog(getClass());

  @Override
  public GloboServiziOnlineSettings serviziOnlineSettimgs() throws BusinessException {

    log.debug("[GloboRest] INIZIO parsing settings");
    WebTarget target = impostaClient("feed-card-servizi-online-settings" + ".json?fascicolo=true");
    Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      GloboServiziOnlineSettings output = response.readEntity(GloboServiziOnlineSettings.class);
      log.debug("Output parsing:" + output);
      return output;
    } else if (response.getStatus() == 418) {
      log.debug("[GloboRest] Errore: nessuna risposta trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione Globo");
    }
  }

  @Override
  public GloboServiziOnline serviziOnline() throws BusinessException {

    log.debug("[GloboRest] INIZIO parsing");
    WebTarget target = impostaClient("feed-card-servizi-online" + ".json");
    Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      GloboServiziOnline output = response.readEntity(GloboServiziOnline.class);
      log.debug("Output parsing:" + output);
      return output;
    } else if (response.getStatus() == 418) {
      log.debug("[GloboRest] Errore: nessuna risposta trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione Globo");
    }
  }

  @Override
  public List<PraticaGlobo> istanzeGlobo(String codiceFiscale) throws BusinessException {

    log.debug("[GloboRest] INIZIO chiamata istanze");
    WebTarget target = impostaClientIstanze("istanze?CodiceFiscale=" + codiceFiscale);
    Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      PraticaGlobo[] output = response.readEntity(PraticaGlobo[].class);
      log.debug("Output parsing:" + output);
      List<PraticaGlobo> lista = Arrays.asList(output);
      return lista;
    } else if (response.getStatus() == 418) {
      log.debug("[GloboRest] Errore: nessuna risposta trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione Globo");
    }
  }

  private WebTarget impostaClientIstanze(String parameterPath) {
    InpsResolver resolver = new InpsResolver();
    Client client =
        ClientBuilder.newClient()
            .register(ApiRestLoggingFilter.class)
            .register(resolver)
            .register(LogChiamateInUscitaInterceptor.class)
            .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE));
    return client.target(BaseServiceImpl.API_SPORTELLO_GLOBO + parameterPath);
  }

  private WebTarget impostaClient(String parameterPath) {
    InpsResolver resolver = new InpsResolver();
    Client client =
        ClientBuilder.newClient()
            .register(ApiRestLoggingFilter.class)
            .register(LogChiamateInUscitaInterceptor.class)
            .register(resolver)
            .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE));
    return client.target(BaseServiceImpl.URN_SMART_COMUNE_GENOVA + parameterPath);
  }

  private Response getResponse(Invocation.Builder builder) {
    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    return response;
  }
}
