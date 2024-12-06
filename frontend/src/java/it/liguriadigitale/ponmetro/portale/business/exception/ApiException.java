package it.liguriadigitale.ponmetro.portale.business.exception;

import javax.ws.rs.core.Response;

public class ApiException extends Exception {

  private static final long serialVersionUID = 4101117680823972892L;

  private final transient Response response;
  private final String myMessage;

  public ApiException(Response response, String mymessage) {
    this.response = response;
    myMessage = mymessage;
  }

  public String getMyMessage() {
    return myMessage;
  }

  public Response getResponse() {
    return response;
  }
}
