package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.Problem;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseGoadevBollettinoFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext arg0, ClientResponseContext responseContext)
      throws IOException {
    log.debug("CP filter goadev bollettino " + responseContext.getStatus());

    if (responseContext.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
        || responseContext.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()) {
      log.debug("entro in if");

      log.debug(
          "[ErrorResponseGoadevBollettinoFilter] invocazione di:"
              + arg0.getUri()
              + arg0.getMethod());

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
        log.debug("[ErrorResponseMipGlobaliFilter] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
      }
      // Aggiunto per JIRA FASCITT-591 per diete speciali
      log.debug("[ErrorResponseMipGlobaliFilter] Errore Goadev:" + message);
      throw new WebApplicationException(message, responseContext.getStatus());
    }
  }
}
