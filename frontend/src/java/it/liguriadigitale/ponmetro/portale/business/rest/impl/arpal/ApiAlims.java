package it.liguriadigitale.ponmetro.portale.business.rest.impl.arpal;

import it.liguriadigitale.ponmetro.arpal.apiclient.AlimsApi;
import it.liguriadigitale.ponmetro.arpal.model.AnalisiVerifiche;
import java.io.File;

public class ApiAlims implements AlimsApi {

  private AlimsApi instance;

  public ApiAlims(AlimsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public File getDocumentoA(String arg0, String arg1) {
    return instance.getDocumentoA(arg0, arg1);
  }

  @Override
  public AnalisiVerifiche getUtenteA(String arg0) {
    return instance.getUtenteA(arg0);
  }
}
