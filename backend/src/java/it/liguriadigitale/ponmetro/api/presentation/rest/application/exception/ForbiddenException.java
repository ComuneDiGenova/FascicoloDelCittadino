package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.WebApplicationException;

public class ForbiddenException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public ForbiddenException(String msg) {
    super(msg);
  }
}
