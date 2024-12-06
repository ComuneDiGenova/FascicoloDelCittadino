package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionHandler
    implements ExceptionMapper<ResourceNotFoundException> {

  @Override
  public Response toResponse(ResourceNotFoundException ex) {

    // String msg = ex.getMessage();
    return Response.serverError().status(204).build();
  }
}
