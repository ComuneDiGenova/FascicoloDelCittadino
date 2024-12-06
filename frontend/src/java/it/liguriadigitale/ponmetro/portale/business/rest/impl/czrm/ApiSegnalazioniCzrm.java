package it.liguriadigitale.ponmetro.portale.business.rest.impl.czrm;

import it.liguriadigitale.ponmetro.segnalazioni.czrm.apiclient.SegnalazioniCzrmApi;
import it.liguriadigitale.ponmetro.segnalazioni.czrm.model.SegnalazioneCzrm;
import java.util.List;

public class ApiSegnalazioniCzrm implements SegnalazioniCzrmApi {

  private SegnalazioniCzrmApi instance;

  public ApiSegnalazioniCzrm(SegnalazioniCzrmApi api) {
    instance = api;
  }

  @Override
  public List<SegnalazioneCzrm> getSegnalazioniCzrm(String arg0) {
    return instance.getSegnalazioniCzrm(arg0);
  }
}
