package it.liguriadigitale.ponmetro.portale.business.rest.impl.controlliAlbi;

import it.liguriadigitale.ponmetro.controlli.albi.apiclient.ControlliAlbiElettoraliApi;
import it.liguriadigitale.ponmetro.controlli.albi.model.Elettorali;
import it.liguriadigitale.ponmetro.controlli.albi.model.Schedario;

public class ApiControlliAlbiImpl implements ControlliAlbiElettoraliApi {

  // CONTROLLI ALBI DA VEDERE
  private ControlliAlbiElettoraliApi instance;

  public ApiControlliAlbiImpl(ControlliAlbiElettoraliApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Elettorali presidenti(String arg0) {
    return instance.presidenti(arg0);
  }

  @Override
  public Schedario schedario(String arg0) {
    return instance.schedario(arg0);
  }

  @Override
  public Elettorali scrutatori(String arg0) {
    return instance.scrutatori(arg0);
  }
}
