package it.liguriadigitale.ponmetro.api.business.livello1.rest.api;

import it.liguriadigitale.ponmetro.abbonamentiamt.apiclient.AmtApi;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Informazioni;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Problem;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Sanctions;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenze;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tickets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApiAbbonamentiAmtImpl implements AmtApi {

  private Log log = LogFactory.getLog(getClass());

  private AmtApi instance;

  public ApiAbbonamentiAmtImpl(AmtApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Tickets getTicket(String arg0) {
    return instance.getTicket(arg0);
  }

  @Override
  public Problem status() {
    return instance.status();
  }

  @Override
  public Scadenze getScadenze(String arg0) {
    return instance.getScadenze(arg0);
  }

  @Override
  public Sanctions getSanctions(String arg0) {
    return instance.getSanctions(arg0);
  }

  @Override
  public Informazioni getMessage(String arg0, String arg1) {
    return instance.getMessage(arg0, arg1);
  }
}
