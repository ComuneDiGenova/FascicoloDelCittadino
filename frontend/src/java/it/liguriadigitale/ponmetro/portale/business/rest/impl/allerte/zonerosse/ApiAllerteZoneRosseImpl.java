package it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.zonerosse;

import it.liguriadigitale.ponmetro.allertezonarossa.apiclient.AllerteareearischioApi;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Componente;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import it.liguriadigitale.ponmetro.allertezonarossa.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Problem;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente;
import java.util.List;

public class ApiAllerteZoneRosseImpl implements AllerteareearischioApi {

  private AllerteareearischioApi instance;

  public ApiAllerteZoneRosseImpl(AllerteareearischioApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Indirizzo setCivico(Indirizzo arg0) {
    return instance.setCivico(arg0);
  }

  @Override
  public Problem setComponente(Componente arg0) {
    return instance.setComponente(arg0);
  }

  @Override
  public Contatto setTelefono(Contatto arg0) {
    return instance.setTelefono(arg0);
  }

  @Override
  public Utente setUtente(Utente arg0) {
    return instance.setUtente(arg0);
  }

  @Override
  public DettagliUtente getUtente(String arg0) {
    return instance.getUtente(arg0);
  }

  @Override
  public Problem deleteTelefono(Integer arg0) {
    return instance.deleteTelefono(arg0);
  }

  @Override
  public List<Lingua> getLingue() {
    return instance.getLingue();
  }

  @Override
  public Problem deleteComponente(Integer arg0, Integer arg1, String arg2) {
    return instance.deleteComponente(arg0, arg1, arg2);
  }
}
