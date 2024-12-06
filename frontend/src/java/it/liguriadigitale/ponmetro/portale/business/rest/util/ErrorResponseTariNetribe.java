package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.tarinetribe.model.Problem;
import java.io.IOException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseTariNetribe implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("[ErrorResponseTariNetribe] Status=" + responseContext.getStatus());

    if (responseContext.getStatus() != Response.Status.OK.getStatusCode()) {

      String message = "Errore generico";
      Problem errore = null;
      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          errore = mapper.readValue(messaggioJSON, Problem.class);
          message = errore.getMoreInfo();
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseTariRimborsiNetribe] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        throw new ServiceUnavailableException(message);
      }

      Response.Status status = Response.Status.fromStatusCode(responseContext.getStatus());
      switch (status) {
        case BAD_REQUEST:
          gestioneErrore400(responseContext);
          break;
        default:
          throw new WebApplicationException(message);
      }
    }
  }

  private void gestioneErrore400(ClientResponseContext response) {
    log.debug("ErrorResponseTariNetribe gestioneErrore400");
    if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {

      response.setStatus(Status.NO_CONTENT.getStatusCode());
      response.setStatusInfo(Status.NO_CONTENT);

      log.debug("[From404to204Filter] getStatus=" + response.getStatus());
    }
  }
}
