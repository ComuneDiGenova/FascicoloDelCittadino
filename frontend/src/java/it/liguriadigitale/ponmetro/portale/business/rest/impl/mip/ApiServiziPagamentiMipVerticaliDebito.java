package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.DebitoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;

public class ApiServiziPagamentiMipVerticaliDebito implements DebitoApi {

  private DebitoApi instance;

  public ApiServiziPagamentiMipVerticaliDebito(DebitoApi instance) {
    this.instance = instance;
  }

  @Override
  public Debito dettaglioDebitoDaIdFiscaleEChiave(
      String arg0, String arg1, Long arg2, String arg3, Boolean arg4) {
    return instance.dettaglioDebitoDaIdFiscaleEChiave(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public Debito dettaglioDebitoDaIdFiscaleECodiceAvviso(String arg0, String arg1, Boolean arg2) {
    return instance.dettaglioDebitoDaIdFiscaleECodiceAvviso(arg0, arg1, arg2);
  }

  @Override
  public Debito dettaglioDebitoDaIdFiscaleEIUV(String arg0, String arg1, Boolean arg2) {
    return instance.dettaglioDebitoDaIdFiscaleEIUV(arg0, arg1, arg2);
  }
}
