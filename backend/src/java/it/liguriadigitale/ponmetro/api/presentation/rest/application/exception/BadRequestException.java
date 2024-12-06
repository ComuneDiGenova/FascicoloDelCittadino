package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.WebApplicationException;

public class BadRequestException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public BadRequestException(String msg) {
    super(msg);
  }
}
