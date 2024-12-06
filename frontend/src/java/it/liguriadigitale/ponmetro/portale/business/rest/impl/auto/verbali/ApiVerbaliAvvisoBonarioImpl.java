package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.VerbaliAvvisoBonarioApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;

public class ApiVerbaliAvvisoBonarioImpl implements VerbaliAvvisoBonarioApi {

  VerbaliAvvisoBonarioApi instance;

  public ApiVerbaliAvvisoBonarioImpl(VerbaliAvvisoBonarioApi instance) {
    super();

    this.instance = instance;
  }

  @Override
  public AvvisoBonario getAvvisoBonario(String arg0) {
    // TODO Auto-generated method stub
    return this.instance.getAvvisoBonario(arg0);
  }
}
