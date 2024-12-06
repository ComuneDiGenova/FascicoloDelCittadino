package it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia;

import it.liguriadigitale.ponmetro.edilizia.condono.apiclient.CondoniApi;
import it.liguriadigitale.ponmetro.edilizia.condono.model.AllegatoBody;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponse;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponseCompleta;
import java.util.List;

public class ApiEdiliziaCondonoImpl implements CondoniApi {

  private CondoniApi instance;

  public ApiEdiliziaCondonoImpl(CondoniApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<CondonoResponse> condoniSoggettoGet(String arg0) {
    return instance.condoniSoggettoGet(arg0);
  }

  @Override
  public CondonoResponseCompleta condonoNumeroPraticaAnnoPraticaGet(Integer arg0, Integer arg1) {
    return instance.condonoNumeroPraticaAnnoPraticaGet(arg0, arg1);
  }

  @Override
  public AllegatoBody
      condonoNumeroPraticaAnnoPraticaNumeroProvvedimentoAnnoProvvedimentoAllegatoNomeFileGet(
          Integer arg0, Integer arg1, Integer arg2, Integer arg3, String arg4) {
    return instance
        .condonoNumeroPraticaAnnoPraticaNumeroProvvedimentoAnnoProvvedimentoAllegatoNomeFileGet(
            arg0, arg1, arg2, arg3, arg4);
  }
}
