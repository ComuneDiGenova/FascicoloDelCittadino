package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.Problem;
import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseMipGlobaliFilterRicevute implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("CP filter mip globali RICEVUTE " + responseContext.getStatus());

    if (responseContext.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {

      log.debug(
          "[ErrorResponseMipGlobaliFilterRicevute] invocazione di:"
              + requestContext.getUri()
              + requestContext.getMethod());

      String message = "Errore generico";
      Problem problem = null;

      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          problem = mapper.readValue(messaggioJSON, Problem.class);
          message = problem.getDetail();

          log.debug("CP messaggio " + message);
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseMipGlobaliFilterRicevute] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        // throw new ServiceUnavailableException(message);
      }
      log.debug("[ErrorResponseMipGlobaliFilterRicevute] Errore Mip Globali:" + message);
      // throw new WebApplicationException(message);

    }
  }
}
