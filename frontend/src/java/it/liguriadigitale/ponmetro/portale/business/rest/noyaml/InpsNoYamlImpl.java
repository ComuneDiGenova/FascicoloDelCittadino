package it.liguriadigitale.ponmetro.portale.business.rest.noyaml;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.util.InpsResolver;
import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.AttestazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.DichiarazioniISEE;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.time.LocalDate;
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

public class InpsNoYamlImpl implements InpsNoYamlService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public AttestazioniISEE attestazioneIsee(Utente utente, LocalDate dataCombo)
      throws BusinessException {

    log.debug("[AttestazioniISEE] INIZIO parsing");
    // Default pattern [yyyy-MM-dd]
    WebTarget target = impostaClient("servizi-inps/attestazione-isee/");
    LocalDate oggi = LocalDate.now();
    Invocation.Builder builder =
        target
            .path(utente.getCodiceFiscaleOperatore())
            .path(oggi.toString())
            .request(MediaType.APPLICATION_JSON);
    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      AttestazioniISEE output = response.readEntity(AttestazioniISEE.class);
      log.debug("Output parsing:" + output);
      return output;
    } else if (response.getStatus() == 418 || response.getStatus() == 400) {
      log.debug("[AttestazioniISEE] Errore di WSO2: nessuna attestazione trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione a attestazione-isee");
    }
  }

  @Override
  public DichiarazioniISEE dichiarazioneIsee(Utente utente, LocalDate dataCombo)
      throws BusinessException {

    log.debug("[DichiarazioniISEE] INIZIO parsing");
    // String primoGennaio = LocalDate.of(oggi.getYear(), Month.JANUARY,
    // 1).toString();
    WebTarget target = impostaClient("servizi-inps/dichiarazione-isee/");
    LocalDate oggi = LocalDate.now();
    Invocation.Builder builder =
        target
            .path(utente.getCodiceFiscaleOperatore())
            .path(oggi.toString())
            .request(MediaType.APPLICATION_JSON);
    Response response = getResponse(builder);
    log.debug("Response	:" + response.getStatus());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      DichiarazioniISEE output = response.readEntity(DichiarazioniISEE.class);
      log.debug("Output parsing:" + output);
      return output;
    } else if (response.getStatus() == 418 || response.getStatus() == 400) {
      log.debug("[AttestazioniISEE] Errore di WSO2: nessuna dichiarazione trovata");
      return null;
    } else {
      throw new BusinessException("Errore durante l'interrogazione a dichiarazione-isee");
    }
  }

  private WebTarget impostaClient(String parameterPath) {
    InpsResolver resolver = new InpsResolver();
    Client client =
        ClientBuilder.newClient()
            .register(ApiRestLoggingFilter.class)
            .register(resolver)
            .register(new OAuth2Authenticator(BaseServiceImpl.TOKEN_COMGE));
    return client.target(BaseServiceImpl.API_COM_GE_INPS + parameterPath);
  }

  private Response getResponse(Invocation.Builder builder) {
    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    return response;
  }
}
