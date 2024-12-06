package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.globali;

import it.liguriadigitale.ponmetro.pagamenti.mip.globali.apiclient.DebitoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.Debito;
import java.time.LocalDate;
import java.util.List;

public class ApiDebitoImpl implements DebitoApi {

  private DebitoApi instance;

  public ApiDebitoImpl(DebitoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<Debito> listaDebitiPersonaPerServizio(
      String var1, String var2, LocalDate var3, LocalDate var4) {
    return instance.listaDebitiPersonaPerServizio(var1, var2, var3, var4);
  }
}
