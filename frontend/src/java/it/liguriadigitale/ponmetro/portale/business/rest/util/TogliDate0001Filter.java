package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TogliDate0001Filter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug(
        "[TogliDate0001Filter] -- filtro per togliere date fasulle " + responseContext.getStatus());

    if (responseContext.getStatus() == Response.Status.OK.getStatusCode()) {

      log.debug(
          "[TogliDate0001Filter] invocazione di:"
              + requestContext.getUri()
              + requestContext.getMethod());

      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));

          log.debug("[TogliDate0001Filter] messaggioJSON " + messaggioJSON);
          String replaced = messaggioJSON.replaceAll("0001-01-01T00:00:00", "");
          log.debug("[TogliDate0001Filter] replaced " + replaced);
          byte[] bytes = replaced.getBytes();
          ByteArrayInputStream is = new ByteArrayInputStream(bytes);
          responseContext.setEntityStream(is);
        }
      } catch (Exception e) {
        log.debug("[TogliDate0001Filter] Errore generico ", e);
      }
    }
  }
}
