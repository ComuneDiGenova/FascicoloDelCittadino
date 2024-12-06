package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;

@Provider
public class NoContentExceptionHandler implements ExceptionMapper<NoContentException> {
  @Override
  public Response toResponse(NoContentException ex) {
    return Response.serverError().status(HttpStatus.SC_NO_CONTENT).build();
  }
}
