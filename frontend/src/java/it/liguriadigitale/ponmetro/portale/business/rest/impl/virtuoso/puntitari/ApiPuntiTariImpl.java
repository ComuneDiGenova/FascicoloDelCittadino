package it.liguriadigitale.ponmetro.portale.business.rest.impl.virtuoso.puntitari;

import it.liguriadigitale.ponmetro.puntitari.apiclient.AmiuApi;
import it.liguriadigitale.ponmetro.puntitari.model.Informazioni;
import it.liguriadigitale.ponmetro.puntitari.model.Problem;
import it.liguriadigitale.ponmetro.puntitari.model.PuntiTari;

public class ApiPuntiTariImpl implements AmiuApi {

  private AmiuApi instance;

  public ApiPuntiTariImpl(AmiuApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Informazioni getMessage(String arg0) {
    return instance.getMessage(arg0);
  }

  @Override
  public PuntiTari getPunti(String arg0, Integer arg1) {
    return instance.getPunti(arg0, arg1);
  }

  @Override
  public Problem status() {
    return instance.status();
  }
}
