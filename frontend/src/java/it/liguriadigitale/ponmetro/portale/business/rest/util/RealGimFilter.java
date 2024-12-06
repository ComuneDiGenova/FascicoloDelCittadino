package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Provider
public class RealGimFilter implements ClientRequestFilter, WriterInterceptor {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void aroundWriteTo(WriterInterceptorContext arg0)
      throws IOException, WebApplicationException {
    // TODO Auto-generated method stub

  }

  @Override
  public void filter(ClientRequestContext request) throws IOException {

    request.getHeaders().add("AuthenticationRealGimm", "ZmFzY2ljb2xvLmNpdHRhZGlubzp6aTNqRjVaITQ=");
  }
}
