package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.WebApplicationException;

public class ResourceNotFoundException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String msg) {
    super(msg);
  }
}
