package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.riuso.mip.PaymentRequest;
import it.liguriadigitale.riuso.mip.PaymentRequest.AccountingData;
import it.liguriadigitale.riuso.mip.PaymentRequest.PaymentData;
import it.liguriadigitale.riuso.mip.PaymentRequest.ServiceData;
import it.liguriadigitale.riuso.mip.PaymentRequest.UserData;
import it.liguriadigitale.riuso.mip.PaymentRequest.UserDataExt;

public class PaymentRequestBuilder {

  protected String portaleID;
  protected String funzione;
  protected String urlDiRitorno;
  protected String urlDiErrore;
  protected String urlBack;
  protected String urlHome;
  protected String urlDiNotifica;
  protected String commitNotifica;
  protected String emailPortale;
  protected String notificaEsitiNegativi;
  protected String ritornaDatiSpecifici;
  protected PaymentData paymentData;
  protected UserData userData;
  protected UserDataExt userDataExt;
  protected ServiceData serviceData;
  protected AccountingData accountingData;

  public static PaymentRequestBuilder getInstance() {
    return new PaymentRequestBuilder();
  }

  public PaymentRequestBuilder setPortaleID(String portaleID) {
    this.portaleID = portaleID;
    return this;
  }

  public PaymentRequestBuilder setFunzione(String funzione) {
    this.funzione = funzione;
    return this;
  }

  public PaymentRequestBuilder setUrlDiRitorno(String urlDiRitorno) {
    this.urlDiRitorno = urlDiRitorno;
    return this;
  }

  public PaymentRequestBuilder setUrlDiErrore(String urlDiErrore) {
    this.urlDiErrore = urlDiErrore;
    return this;
  }

  public PaymentRequestBuilder setUrlBack(String urlBack) {
    this.urlBack = urlBack;
    return this;
  }

  public PaymentRequestBuilder setUrlHome(String urlHome) {
    this.urlHome = urlHome;
    return this;
  }

  public PaymentRequestBuilder setUrlDiNotifica(String urlDiNotifica) {
    this.urlDiNotifica = urlDiNotifica;
    return this;
  }

  public PaymentRequestBuilder setCommitNotifica(String commitNotifica) {
    this.commitNotifica = commitNotifica;
    return this;
  }

  public PaymentRequestBuilder setEmailPortale(String emailPortale) {
    this.emailPortale = emailPortale;
    return this;
  }

  public PaymentRequestBuilder setNotificaEsitiNegativi(String notificaEsitiNegativi) {
    this.notificaEsitiNegativi = notificaEsitiNegativi;
    return this;
  }

  public PaymentRequestBuilder setRitornaDatiSpecifici(String ritornaDatiSpecifici) {
    this.ritornaDatiSpecifici = ritornaDatiSpecifici;
    return this;
  }

  public PaymentRequestBuilder setPaymentData(PaymentData paymentData) {
    this.paymentData = paymentData;
    return this;
  }

  public PaymentRequestBuilder setUserData(UserData userData) {
    this.userData = userData;
    return this;
  }

  public PaymentRequestBuilder setUserDataExt(UserDataExt userDataExt) {
    this.userDataExt = userDataExt;
    return this;
  }

  public PaymentRequestBuilder setServiceData(ServiceData serviceData) {
    this.serviceData = serviceData;
    return this;
  }

  public PaymentRequestBuilder setAccountingData(AccountingData accountingData) {
    this.accountingData = accountingData;
    return this;
  }

  public PaymentRequest build() {
    PaymentRequest request = new PaymentRequest();
    request.setAccountingData(accountingData);
    request.setCommitNotifica(commitNotifica);
    request.setEmailPortale(emailPortale);
    request.setFunzione(funzione);
    request.setNotificaEsitiNegativi(notificaEsitiNegativi);
    request.setPaymentData(paymentData);
    request.setPortaleID(portaleID);
    request.setRitornaDatiSpecifici(ritornaDatiSpecifici);
    request.setServiceData(serviceData);
    request.setURLBack(urlBack);
    request.setURLDiErrore(urlDiErrore);
    request.setURLDiNotifica(urlDiNotifica);
    request.setURLDiRitorno(urlDiRitorno);
    request.setURLHome(urlHome);
    request.setUserData(userData);
    request.setUserDataExt(userDataExt);
    return request;
  }
}
