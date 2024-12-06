package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;

@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {
  @Override
  public Response toResponse(BadRequestException ex) {
    return Response.serverError().status(HttpStatus.SC_BAD_REQUEST).build();
  }
}
