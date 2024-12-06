package it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia;

import it.liguriadigitale.ponmetro.edilizia.abitabilita.apiclient.DecretiApi;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaRequest;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaResponse;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AllegatoBody;
import java.util.List;

public class ApiEdiliziaAbitabilitaImpl implements DecretiApi {

  private DecretiApi instance;

  public ApiEdiliziaAbitabilitaImpl(DecretiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<AbitabilitaResponse> decretiPost(AbitabilitaRequest arg0) {
    return instance.decretiPost(arg0);
  }

  @Override
  public AllegatoBody decretoIdUdAllegatoNomeFileGet(Integer arg0, String arg1) {
    return instance.decretoIdUdAllegatoNomeFileGet(arg0, arg1);
  }
}
