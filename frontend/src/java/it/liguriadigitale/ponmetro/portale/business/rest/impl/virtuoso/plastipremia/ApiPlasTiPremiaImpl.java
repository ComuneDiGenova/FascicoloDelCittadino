package it.liguriadigitale.ponmetro.portale.business.rest.impl.virtuoso.plastipremia;

import it.liguriadigitale.ponmetro.plastipremia.apiclient.PlastipremiaApi;
import it.liguriadigitale.ponmetro.plastipremia.model.Informazioni;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiCoupon;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiPunti;
import it.liguriadigitale.ponmetro.plastipremia.model.Problem;

public class ApiPlasTiPremiaImpl implements PlastipremiaApi {

  private PlastipremiaApi instance;

  public ApiPlasTiPremiaImpl(PlastipremiaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public PlastiCoupon getCoupon(String arg0) {
    return instance.getCoupon(arg0);
  }

  @Override
  public Informazioni getMessage(String arg0) {
    return instance.getMessage(arg0);
  }

  @Override
  public PlastiPunti getPunti(String arg0) {
    return instance.getPunti(arg0);
  }

  @Override
  public Problem status() {
    return instance.status();
  }
}
