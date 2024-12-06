package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziInvioDatiPage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Errore;
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

public class ErrorResponseDppVerbaliFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("[ErrorResponseDppVerbaliFilter] Status=" + responseContext.getStatus());

    if (responseContext.getStatus() != Response.Status.OK.getStatusCode()) {

      log.debug(
          "[ErrorResponseDppVerbaliFilter] invocazione di:"
              + requestContext.getUri()
              + requestContext.getMethod());

      String message = "Errore generico";
      Errore errore = null;

      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          errore = mapper.readValue(messaggioJSON, Errore.class);
          message = errore.getDetail();

          log.debug("CP message dpp = " + message);
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseDppVerbaliFilter] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        throw new ServiceUnavailableException(message);
      }
      log.debug("[ErrorResponseDppVerbaliFilter] Errore dpp verbali:" + message);

      String messaggioErrore = "dichiarazione punti patente".concat(" ").concat(message);

      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziInvioDatiPage(messaggioErrore));
    }
  }
}
