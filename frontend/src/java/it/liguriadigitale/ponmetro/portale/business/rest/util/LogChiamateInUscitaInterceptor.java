package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogChiamateInUscitaInterceptor implements ClientRequestFilter {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext context) throws IOException {

    log.trace("[LogChiamateInUscitaInterceptor] INIZIO ---");

    @SuppressWarnings("unused")
    MultivaluedMap<String, Object> headers = context.getHeaders();
    String method = context.getMethod();
    URI uri = context.getUri();

    log.debug("uri= " + uri.getPath() + " metodo:" + method);
  }
}
