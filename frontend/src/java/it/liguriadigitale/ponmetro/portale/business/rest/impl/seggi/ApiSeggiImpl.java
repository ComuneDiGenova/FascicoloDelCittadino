package it.liguriadigitale.ponmetro.portale.business.rest.impl.seggi;

import it.liguriadigitale.ponmetro.seggi.apiclient.SeggielettoraliApi;
import it.liguriadigitale.ponmetro.seggi.model.DatiPersonaliComponenteSeggio;
import it.liguriadigitale.ponmetro.seggi.model.Elezioni;
import it.liguriadigitale.ponmetro.seggi.model.EsitoInvioDati;
import it.liguriadigitale.ponmetro.seggi.model.InvioDatiPersonali;

public class ApiSeggiImpl implements SeggielettoraliApi {

  private SeggielettoraliApi instance;

  public ApiSeggiImpl(SeggielettoraliApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DatiPersonaliComponenteSeggio getDatiCittadino(String arg0, String arg1) {
    return instance.getDatiCittadino(arg0, arg1);
  }

  @Override
  public Elezioni getElezioni() {
    return instance.getElezioni();
  }

  @Override
  public EsitoInvioDati setDatiCittadino(InvioDatiPersonali arg0) {
    return instance.setDatiCittadino(arg0);
  }
}
