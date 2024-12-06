package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorResponseTeleriscaldamentoIren implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext arg0, ClientResponseContext responseContext)
      throws IOException {
    log.debug("CP ErrorResponseTeleriscaldamentoIren " + responseContext.getStatus());

    if (responseContext.getStatus() != Response.Status.OK.getStatusCode()) {
      log.debug("entro in if != 200");

      responseContext.setStatus(Response.Status.NO_CONTENT.getStatusCode());
    }
  }
}
