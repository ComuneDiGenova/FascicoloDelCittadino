package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tarieng;

import it.liguriadigitale.ponmetro.taririmborsieng.apiclient.RimborsiApi;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoGETResponse;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import it.liguriadigitale.ponmetro.taririmborsieng.model.NuovoRimborso;

public class ApiTariRimborsiEngImpl implements RimborsiApi {

  private RimborsiApi instance;

  public ApiTariRimborsiEngImpl(RimborsiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public IstanzaRimborsoGETResponse getRimborsi(String arg0) {
    return instance.getRimborsi(arg0);
  }

  @Override
  public IstanzaRimborsoPOSTResponse setRichiestaRimborso(NuovoRimborso arg0) {
    return instance.setRichiestaRimborso(arg0);
  }
}
