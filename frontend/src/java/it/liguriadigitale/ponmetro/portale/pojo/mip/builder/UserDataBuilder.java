package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.riuso.mip.PaymentRequest.UserData;

public class UserDataBuilder {

  protected String emailUtente;
  protected String identificativoUtente;
  protected String userID;
  protected String tokenID;

  public static UserDataBuilder getInstance() {
    return new UserDataBuilder();
  }

  public UserDataBuilder setEmailUtente(String emailUtente) {

    if (PageUtil.isStringValid(emailUtente)) {
      this.emailUtente = emailUtente;
    } else {
      this.emailUtente = BaseServiceImpl.EMAIL_PAGAMENTO_DEFAULT;
    }

    return this;
  }

  public UserDataBuilder setIdentificativoUtente(String identificativoUtente) {
    this.identificativoUtente = identificativoUtente;
    return this;
  }

  public UserDataBuilder setUserID(String userID) {
    this.userID = userID;
    return this;
  }

  public UserDataBuilder setTokenID(String tokenID) {
    this.tokenID = tokenID;
    return this;
  }

  public UserData build() {
    UserData data = new UserData();
    data.setEmailUtente(emailUtente);
    data.setIdentificativoUtente(identificativoUtente);
    data.setTokenID(tokenID);
    data.setUserID(userID);
    return data;
  }
}
