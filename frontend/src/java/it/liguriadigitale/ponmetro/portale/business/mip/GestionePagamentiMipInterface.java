package it.liguriadigitale.ponmetro.portale.business.mip;

import it.liguriadigitale.riuso.mip.PaymentRequest;

public interface GestionePagamentiMipInterface {

  public PaymentRequest generaPaymentRequest();

  public String preparaBufferProtocollo(PaymentRequest request);
}
