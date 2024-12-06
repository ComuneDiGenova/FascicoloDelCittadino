package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.portale.presentation.common.bolloAuto.BolloAutoErroriPage;
import it.liguriadigitale.ponmetro.tassaauto.model.Errore;
import java.io.IOException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ErrorResponseBolloAutoFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    // for non-200 response, deal with the custom error messages
    log.debug("[ErrorResponseBolloAutoFilter] Status=" + responseContext.getStatus());
    if (responseContext.getStatus() >= Response.Status.BAD_REQUEST.getStatusCode()) {
      String message = "Errore generico";
      Errore errore = null;
      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          errore = mapper.readValue(messaggioJSON, Errore.class);
          message = errore.getDetail();

          log.debug("CP message = " + message);
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseBolloAutoFilter] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        throw new ServiceUnavailableException(message);
      }
      log.debug("[ErrorResponseBolloAutoFilter] Errore Bollo:" + message);
      // throw new WebApplicationException(message);
      throw new RestartResponseAtInterceptPageException(new BolloAutoErroriPage(message));
    }
  }
}

/*




*/
