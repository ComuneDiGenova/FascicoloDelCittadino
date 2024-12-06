package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.riuso.mip.PaymentRequest.UserDataExt;
import it.liguriadigitale.riuso.mip.PaymentRequest.UserDataExt.UtenteLogin;

public class UserDataExtBuilder {

  private UtenteLogin utenteLogin;

  public static UserDataExtBuilder getInstance() {
    return new UserDataExtBuilder();
  }

  public UserDataExtBuilder setUtenteLogin(UtenteLogin utenteLogin) {
    this.utenteLogin = utenteLogin;
    return this;
  }

  public UserDataExt build() {
    UserDataExt userDataExt = new UserDataExt();
    userDataExt.setUtenteLogin(utenteLogin);
    return userDataExt;
  }
}
