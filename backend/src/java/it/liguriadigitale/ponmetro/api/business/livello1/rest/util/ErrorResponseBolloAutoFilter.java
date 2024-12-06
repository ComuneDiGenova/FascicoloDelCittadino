package it.liguriadigitale.ponmetro.api.business.livello1.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.tassaauto.model.Errore;
import java.io.IOException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseBolloAutoFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    // for non-200 response, deal with the custom error messages
    if (responseContext.getStatus() >= Response.Status.BAD_REQUEST.getStatusCode()) {
      log.debug("[ErrorResponseBolloAutoFilter] Status=" + responseContext.getStatus());
      String message = "Errore generico";
      Errore errore = null;
      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          errore = mapper.readValue(messaggioJSON, Errore.class);
          message = errore.getDetail();
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseBolloAutoFilter] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        throw new ServiceUnavailableException(message);
      }
      log.debug("[ErrorResponseBolloAutoFilter] Errore Bollo:" + message);
      throw new WebApplicationException(message);
    }
  }
}

/*




*/
