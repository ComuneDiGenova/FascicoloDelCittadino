package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseBollettazioneMensaFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext arg0, ClientResponseContext responseContext)
      throws IOException {
    log.debug("CP ErrorResponseBollettazioneMensaFilter " + responseContext.getStatus());

    if (responseContext.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()) {

      log.debug(
          "[ErrorResponseBollettazioneMensaFilter] invocazione di:"
              + arg0.getUri()
              + arg0.getMethod());

      responseContext.setStatus(Status.NO_CONTENT.getStatusCode());
      responseContext.setStatusInfo(Status.NO_CONTENT);
    }
  }
}
