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

public class TogliSpaziFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("unused")
  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    log.debug("[TogliSpaziFilter] -- CP filter mip globali " + responseContext.getStatus());

    if (responseContext.getStatus() == Response.Status.OK.getStatusCode()) {

      log.debug(
          "[TogliSpaziFilter] invocazione di:"
              + requestContext.getUri()
              + requestContext.getMethod());

      try {
        if (responseContext.getEntityStream() != null) {
          String messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));

          log.debug("[TogliSpaziFilter] messaggioJSON " + messaggioJSON);
          String replaced = messaggioJSON.replaceAll("\\s+", " ");
          log.debug("[TogliSpaziFilter] replaced " + replaced);
          byte[] bytes = replaced.getBytes();
          ByteArrayInputStream is = new ByteArrayInputStream(bytes);
          // responseContext.setEntityStream(is);
        }
      } catch (Exception e) {
        log.debug("[TogliSpaziFilter] Errore generico ", e);
      }
    }
  }
}
