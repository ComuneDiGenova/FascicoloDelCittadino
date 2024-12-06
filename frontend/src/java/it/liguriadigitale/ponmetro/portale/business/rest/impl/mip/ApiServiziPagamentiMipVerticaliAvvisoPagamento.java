package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.AvvisoPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public class ApiServiziPagamentiMipVerticaliAvvisoPagamento implements AvvisoPagamentoApi {

  private AvvisoPagamentoApi instance;

  public ApiServiziPagamentiMipVerticaliAvvisoPagamento(AvvisoPagamentoApi instance) {
    this.instance = instance;
  }

  @Override
  public AvvisoPagamento dettaglioAvvisoDaIdFiscaleEChiave(
      String arg0, String arg1, Long arg2, String arg3, Boolean arg4, Boolean arg5) {
    return instance.dettaglioAvvisoDaIdFiscaleEChiave(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  @Override
  public AvvisoPagamento dettaglioAvvisoDaIdFiscaleECodiceAvviso(
      String arg0, String arg1, Boolean arg2, Boolean arg3) {
    return instance.dettaglioAvvisoDaIdFiscaleECodiceAvviso(arg0, arg1, arg2, arg3);
  }

  @Override
  public AvvisoPagamento dettaglioAvvisoDaIdFiscaleEIUV(
      String arg0, String arg1, Boolean arg2, Boolean arg3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<AvvisoPagamento> listaAvvisiPersona(String arg0) {
    return instance.listaAvvisiPersona(arg0);
  }
}
