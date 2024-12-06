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
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseTariRimborsiNetribe implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("[ErrorResponseTariRimborsiNetribe] Status=" + responseContext.getStatus());

    if (responseContext.getStatus() != Response.Status.OK.getStatusCode()) {

      log.debug("[ErrorResponseTariRimborsiNetribe] Status=" + responseContext.getStatus());
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
      log.debug("[ErrorResponseTariRimborsiNetribe] Errore Rimborsi TARI:" + message);
      throw new WebApplicationException(message);
    }
  }
}
