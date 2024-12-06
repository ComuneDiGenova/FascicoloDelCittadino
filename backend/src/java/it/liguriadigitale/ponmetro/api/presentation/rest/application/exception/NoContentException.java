package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.WebApplicationException;

public class NoContentException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public NoContentException(String msg) {
    super(msg);
  }
}
