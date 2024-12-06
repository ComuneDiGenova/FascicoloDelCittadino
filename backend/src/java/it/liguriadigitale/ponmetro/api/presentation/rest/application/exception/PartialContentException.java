package it.liguriadigitale.ponmetro.api.presentation.rest.application.exception;

import javax.ws.rs.WebApplicationException;

public class PartialContentException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public PartialContentException(String msg) {
    super(msg);
  }
}
