package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.riuso.mip.PaymentRequest.PaymentData;

public class PaymentDataBuilder {

  protected String numeroOperazione;
  protected String valuta;
  protected String importo;
  protected String importoCommissioni;
  protected String calcoloCommissioni;

  public static PaymentDataBuilder getInstance() {
    return new PaymentDataBuilder();
  }

  public PaymentDataBuilder setNumeroOperazione(String numeroOperazione) {
    this.numeroOperazione = numeroOperazione;
    return this;
  }

  public PaymentDataBuilder setValuta(String valuta) {
    this.valuta = valuta;
    return this;
  }

  public PaymentDataBuilder setImporto(String importo) {
    this.importo = importo;
    return this;
  }

  public PaymentDataBuilder setImportoCommissioni(String importoCommissioni) {
    this.importoCommissioni = importoCommissioni;
    return this;
  }

  public PaymentDataBuilder setCalcoloCommissioni(String calcoloCommissioni) {
    this.calcoloCommissioni = calcoloCommissioni;
    return this;
  }

  public PaymentData build() {
    PaymentData paymentData = new PaymentData();
    paymentData.setCalcoloCommissioni(calcoloCommissioni);
    paymentData.setImporto(importo);
    paymentData.setImportoCommissioni(importoCommissioni);
    paymentData.setNumeroOperazione(numeroOperazione);
    paymentData.setValuta(valuta);
    return paymentData;
  }
}
