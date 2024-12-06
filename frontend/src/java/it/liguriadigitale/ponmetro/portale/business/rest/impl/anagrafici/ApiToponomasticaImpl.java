package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.ToponomasticaApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCittadinanzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceComuneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceStatoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetToponomasticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetVerificaToponomasticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetViarioResponseGenericResponse;

public class ApiToponomasticaImpl implements ToponomasticaApi {

  private ToponomasticaApi instance;

  public ApiToponomasticaImpl(ToponomasticaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public GetCittadinanzaResponseGenericResponse getCittadinanza(String arg0, String arg1) {
    return instance.getCittadinanza(arg0, arg1);
  }

  @Override
  public GetCodiceComuneResponseGenericResponse getCodiceComune(Integer arg0, String arg1) {
    return instance.getCodiceComune(arg0, arg1);
  }

  @Override
  public GetCodiceStatoResponseGenericResponse getCodiceStato(String arg0, String arg1) {
    return instance.getCodiceStato(arg0, arg1);
  }

  @Override
  public GetToponomasticaResponseGenericResponse getToponomastica(
      Integer arg0, Integer arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
    return instance.getToponomastica(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
  }

  @Override
  public GetVerificaToponomasticaResponseGenericResponse getVerificaToponomastica(Integer arg0) {
    return instance.getVerificaToponomastica(arg0);
  }

  @Override
  public GetViarioResponseGenericResponse getViario(Integer arg0, String arg1) {
    return instance.getViario(arg0, arg1);
  }
}
