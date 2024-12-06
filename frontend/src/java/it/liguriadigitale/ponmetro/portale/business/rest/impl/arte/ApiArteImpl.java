package it.liguriadigitale.ponmetro.portale.business.rest.impl.arte;

import it.liguriadigitale.ponmetro.arte.apiclient.ArteApi;
import it.liguriadigitale.ponmetro.arte.model.Componenti;
import it.liguriadigitale.ponmetro.arte.model.Daticontr;
import it.liguriadigitale.ponmetro.arte.model.FatturaPdf;
import it.liguriadigitale.ponmetro.arte.model.Mora;

public class ApiArteImpl implements ArteApi {

  private ArteApi instance;

  public ApiArteImpl(ArteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public FatturaPdf getFattura(String arg0) {
    return instance.getFattura(arg0);
  }

  @Override
  public Componenti searchcomponenti(String arg0) {
    return instance.searchcomponenti(arg0);
  }

  @Override
  public Daticontr searchdaticontr(String arg0) {
    return instance.searchdaticontr(arg0);
  }

  @Override
  public Mora searchmora(String arg0, String arg1) {
    return instance.searchmora(arg0, arg1);
  }
}
