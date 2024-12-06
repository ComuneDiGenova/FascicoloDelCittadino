package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.RicevutaPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import java.util.List;

public class ApiServiziPagamentiMipVerticaliRicevutaPagamento implements RicevutaPagamentoApi {

  private RicevutaPagamentoApi instance;

  public ApiServiziPagamentiMipVerticaliRicevutaPagamento(RicevutaPagamentoApi instance) {
    this.instance = instance;
  }

  @Override
  public List<RicevutaPagamento> listaRicevutePagamentoPerChiave(
      String arg0, String arg1, Long arg2, String arg3, Boolean arg4) {
    return instance.listaRicevutePagamentoPerChiave(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public List<RicevutaPagamento> listaRicevutePagamentoPerCodiceAvviso(
      String arg0, String arg1, Boolean arg2) {
    return instance.listaRicevutePagamentoPerCodiceAvviso(arg0, arg1, arg2);
  }

  @Override
  public List<RicevutaPagamento> listaRicevutePagamentoPerIUV(
      String arg0, String arg1, Boolean arg2) {
    return instance.listaRicevutePagamentoPerIUV(arg0, arg1, arg2);
  }

  @Override
  public List<RicevutaPagamento> listaRicevutePagamentoPersona(String arg0, Boolean arg1) {
    return instance.listaRicevutePagamentoPersona(arg0, arg1);
  }
}
