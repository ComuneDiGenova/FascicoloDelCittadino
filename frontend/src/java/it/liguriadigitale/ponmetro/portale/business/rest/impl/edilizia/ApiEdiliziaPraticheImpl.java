package it.liguriadigitale.ponmetro.portale.business.rest.impl.edilizia;

import it.liguriadigitale.ponmetro.edilizia.pratiche.apiclient.PraticheApi;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratica;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratiche;

public class ApiEdiliziaPraticheImpl implements PraticheApi {

  private PraticheApi instance;

  public ApiEdiliziaPraticheImpl(PraticheApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Pratica getDettagliPratica(String arg0) {
    return instance.getDettagliPratica(arg0);
  }

  @Override
  public Pratiche getPratiche(String arg0) {
    return instance.getPratiche(arg0);
  }
}
