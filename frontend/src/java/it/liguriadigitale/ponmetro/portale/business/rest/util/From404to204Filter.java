package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class From404to204Filter implements ClientResponseFilter {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext arg0, ClientResponseContext response) throws IOException {
    log.debug("From404to204Filter -- INIZIO");

    if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
      log.debug("[From404to204Filter] getAllowedMethods=" + response.getAllowedMethods());
      log.debug("[From404to204Filter] getHeaders=" + response.getHeaders());
      log.debug("[From404to204Filter] hasEntity=" + response.hasEntity());
      log.debug("[From404to204Filter] getEntityTag=" + response.getEntityTag());
      log.debug("[From404to204Filter] invocazione di:" + arg0.getUri() + arg0.getMethod());

      response.setStatus(Status.NO_CONTENT.getStatusCode());
      response.setStatusInfo(Status.NO_CONTENT);

      log.debug("[From404to204Filter] getStatus=" + response.getStatus());
    }
  }
}
