package it.liguriadigitale.ponmetro.portale.business.rest.impl.caitel;

import it.liguriadigitale.ponmetro.catastoimpianti.apiclient.CaitelApi;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.ImpiantiTermici;

public class ApiCaitelImpl implements CaitelApi {

  private CaitelApi instance;

  public ApiCaitelImpl(CaitelApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ImpiantiTermici getResponsabile(String arg0) {
    return instance.getResponsabile(arg0);
  }

  @Override
  public Documento getDocumento(String arg0, String arg1, String arg2, String arg3) {
    return instance.getDocumento(arg0, arg1, arg2, arg3);
  }
}
