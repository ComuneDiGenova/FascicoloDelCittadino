package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.apiclient.IstanzeVerbaliPlApi;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import java.util.List;

public class ApiServiziSupportoIstanzeVerbaliPlImpl implements IstanzeVerbaliPlApi {

  private IstanzeVerbaliPlApi instance;

  public ApiServiziSupportoIstanzeVerbaliPlImpl(IstanzeVerbaliPlApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<DatiDocumento> istanzeplCompleteDocumentiCodiceHermesGet(String arg0) {
    return instance.istanzeplCompleteDocumentiCodiceHermesGet(arg0);
  }

  @Override
  public List<DatiMotivo> istanzeplCompleteMotiviAndorWantequalSerieArticoliCodiceHermesGet(
      Boolean arg0, Boolean arg1, String arg2, String arg3, String arg4) {
    return instance.istanzeplCompleteMotiviAndorWantequalSerieArticoliCodiceHermesGet(
        arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public List<DatiMotivoSummary> istanzeplSummaryMotiviAndorWantequalSerieArticoliGet(
      Boolean arg0, Boolean arg1, String arg2, String arg3) {
    return instance.istanzeplSummaryMotiviAndorWantequalSerieArticoliGet(arg0, arg1, arg2, arg3);
  }

  @Override
  public List<DatiMotivoSummary> istanzeplSummaryMotivoCodiceHermesGet(String arg0) {
    // TODO Auto-generated method stub
    return instance.istanzeplSummaryMotivoCodiceHermesGet(arg0);
  }
}
