package it.liguriadigitale.ponmetro.portale.business.rest.impl.arpal;

import it.liguriadigitale.ponmetro.arpal.apiclient.SimpaApi;
import it.liguriadigitale.ponmetro.arpal.model.Impianti;
import java.io.File;

public class ApiSimpa implements SimpaApi {

  private SimpaApi instance;

  public ApiSimpa(SimpaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public File getDocumentoS(String arg0) {
    return instance.getDocumentoS(arg0);
  }

  @Override
  public Impianti getUtenteS(String arg0) {
    return instance.getUtenteS(arg0);
  }
}
