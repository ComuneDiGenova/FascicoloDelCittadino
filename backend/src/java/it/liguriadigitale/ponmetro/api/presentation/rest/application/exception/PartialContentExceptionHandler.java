package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;

@Provider
public class PartialContentExceptionHandler implements ExceptionMapper<PartialContentException> {
  @Override
  public Response toResponse(PartialContentException ex) {
    return Response.serverError().status(HttpStatus.SC_PARTIAL_CONTENT).build();
  }
}
