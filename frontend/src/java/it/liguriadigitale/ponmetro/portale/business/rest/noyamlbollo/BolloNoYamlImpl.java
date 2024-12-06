package it.liguriadigitale.ponmetro.portale.business.rest.noyamlbollo;

import de.agilecoders.wicket.jquery.util.Json;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.InpsResolver;
import it.liguriadigitale.ponmetro.tassaauto.model.VeicoliAttivi;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class BolloNoYamlImpl implements BolloNoYamlService {

  private static Log log = LogFactory.getLog(BolloNoYamlImpl.class);

  @Override
  public VeicoliAttivi getVeicoliAttivi(String cf) throws BusinessException {
    WebTarget target = impostaClient("veicoliAttivi/");
    Invocation.Builder builder = target.path(cf).request(MediaType.APPLICATION_JSON);

    Response response = getResponse(builder);

    if (response.getStatus() == 200) {
      String outputString = response.readEntity(String.class);
      log.debug("json outputString bollo:" + outputString);
      VeicoliAttivi veicoliAttivi = Json.fromJson(outputString, VeicoliAttivi.class);
      log.debug("veicoliAttivi " + veicoliAttivi);
      return veicoliAttivi;
    } else if (response.getStatus() == 418) {
      log.debug("[BolloNoYamlImpl] Errore di WSO2: nessuna attestazione trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione a attestazione-isee");
    }
  }

  private WebTarget impostaClient(String parameterPath) {
    InpsResolver resolver = new InpsResolver();
    Client client =
        ClientBuilder.newClient()
            .register(ApiRestLoggingFilter.class)
            .register(resolver)
            .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE));
    return client.target(BaseServiceImpl.API_COM_GE_TASSA_AUTO + parameterPath);
  }

  private Response getResponse(Invocation.Builder builder) {
    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    return response;
  }
}
