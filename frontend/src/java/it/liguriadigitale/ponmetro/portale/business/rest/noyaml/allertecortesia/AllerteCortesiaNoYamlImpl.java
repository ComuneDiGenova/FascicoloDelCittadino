package it.liguriadigitale.ponmetro.portale.business.rest.noyaml.allertecortesia;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.InpsResolver;
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

public class AllerteCortesiaNoYamlImpl implements AllerteCortesiaNoYamlService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public DettagliUtente getWsLoginEMAIL(String email, String pwd, String cf)
      throws BusinessException {
    log.debug("[AllerteCortesiaNoYamlImpl] getWsLoginEMAIL = " + email + " " + pwd + " " + cf);

    WebTarget target = impostaClient("wsLoginEMAIL");

    log.debug("CP target = " + target);

    Invocation.Builder builder =
        target
            .queryParam("EMAIL", email)
            .queryParam("PASSWORD_UTENTE", pwd)
            .queryParam("CF", cf)
            .request(MediaType.APPLICATION_JSON);

    log.debug("CP builder = " + builder);

    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      DettagliUtente output = response.readEntity(DettagliUtente.class);
      log.debug("Output parsing:" + output);
      return output;
    } else if (response.getStatus() == 418) {
      log.debug(
          "[AllerteCortesiaNoYamlImpl] Errore di WSO2: allerte cortesia recupero registrazione");
      return null;
    } else {
      throw new BusinessException(
          "Errore durante l'interrogazione a allerte cortesia recupero registrazione");
    }
  }

  private WebTarget impostaClient(String parameterPath) {
    InpsResolver resolver = new InpsResolver();
    Client client =
        ClientBuilder.newClient()
            .register(ApiRestLoggingFilter.class)
            .register(resolver)
            .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE));
    return client.target(BaseServiceImpl.API_ALLERTE_CORTESIA + parameterPath);
  }

  private Response getResponse(Invocation.Builder builder) {
    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    return response;
  }
}
