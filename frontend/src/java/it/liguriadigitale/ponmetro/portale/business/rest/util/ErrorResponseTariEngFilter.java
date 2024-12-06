package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTErroreResponse;
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

public class ErrorResponseTariEngFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("[ErrorResponseTariEngFilter] Status=" + responseContext.getStatus());

    if (responseContext.getStatus() != Response.Status.OK.getStatusCode()) {

      log.debug(
          "[ErrorResponseTariEngFilter] invocazione di:"
              + requestContext.getUri()
              + requestContext.getMethod());

      String message = "Errore generico";
      IstanzaRimborsoPOSTErroreResponse errore = null;

      try {

        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          ObjectMapper mapper = new ObjectMapper();
          errore = mapper.readValue(messaggioJSON, IstanzaRimborsoPOSTErroreResponse.class);

          if (errore.getErrore() != null) {
            message = errore.getErrore().getMessaggioErrore();
          }

          log.debug("Messaggio errore POST Eng Rimborsi = " + message);
        }
      } catch (Exception e) {
        log.debug("[ErrorResponseTariEngFilter] Errore generico WSO", e);
        message = message + "=" + e.getMessage();
        throw new ServiceUnavailableException(message);
      }
      log.debug("[ErrorResponseTariEngFilter] Errore tari eng:" + message);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage(message));
    }
  }
}
