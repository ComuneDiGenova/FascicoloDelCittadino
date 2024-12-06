package it.liguriadigitale.ponmetro.portale.business.rest.util;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class SensorAuthenticator implements ClientRequestFilter {
  private final String authHeader;

  public SensorAuthenticator(String token) {
    this.authHeader = "Basic " + token;
  }

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    requestContext.getHeaders().putSingle("x-SensorAuth", this.authHeader);
  }
}
