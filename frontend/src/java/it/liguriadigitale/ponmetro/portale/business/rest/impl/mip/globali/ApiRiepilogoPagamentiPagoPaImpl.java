package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.globali;

import it.liguriadigitale.ponmetro.pagamenti.mip.globali.apiclient.RiepilogoPagamentiPagoPaApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.RiepilogoPagamentiPagoPA;
import java.time.LocalDate;

public class ApiRiepilogoPagamentiPagoPaImpl implements RiepilogoPagamentiPagoPaApi {

  private RiepilogoPagamentiPagoPaApi instance;

  public ApiRiepilogoPagamentiPagoPaImpl(RiepilogoPagamentiPagoPaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public RiepilogoPagamentiPagoPA riepilogoUtente(
      String var1, LocalDate var2, LocalDate var3, Boolean var4) {
    return instance.riepilogoUtente(var1, var2, var3, var4);
  }
}
