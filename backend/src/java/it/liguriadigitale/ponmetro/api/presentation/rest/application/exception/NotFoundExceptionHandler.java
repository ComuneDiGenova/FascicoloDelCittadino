package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {
  @Override
  public Response toResponse(NotFoundException ex) {
    return Response.serverError().status(HttpStatus.SC_NOT_FOUND).build();
  }
}
