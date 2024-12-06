package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.genovaparcheggi;

import it.liguriadigitale.ponmetro.genovaparcheggi.apiclient.GeparkApi;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.AvvisoPagoPA;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Customer;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.CustomerId;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.CustomerPermitInfo;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.CustomerPermitTypeId;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.GeneraAvvisoPagoPARequest;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.NewPermit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitConfigurations;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitsListResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.RinnovaRequest;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransitsResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Token;
import java.util.List;

public class ApiGenovaParcheggiImpl implements GeparkApi {

  private GeparkApi instance;

  public ApiGenovaParcheggiImpl(GeparkApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<PermitAllowedAction> allowedActions(Integer arg0) {
    return instance.allowedActions(arg0);
  }

  @Override
  public CustomerId bindCustomer(Customer arg0) {
    return instance.bindCustomer(arg0);
  }

  @Override
  public void cancel(RinnovaRequest arg0) {
    instance.cancel(arg0);
  }

  @Override
  public AvvisoPagoPA generaAvvisoPagoPA(GeneraAvvisoPagoPARequest arg0) {
    return instance.generaAvvisoPagoPA(arg0);
  }

  @Override
  public String getPDF(Integer arg0) {
    return instance.getPDF(arg0);
  }

  @Override
  public CustomerPermitInfo newPermit(NewPermit arg0) {
    return instance.newPermit(arg0);
  }

  @Override
  public PermitConfigurations newPermitConfigurations(CustomerPermitTypeId arg0) {
    return instance.newPermitConfigurations(arg0);
  }

  @Override
  public List<PermitVerificationResult> permitVerification(String arg0, String arg1) {
    return instance.permitVerification(arg0, arg1);
  }

  @Override
  public PermitsListResult permitsList(String arg0) {
    return instance.permitsList(arg0);
  }

  @Override
  public void rinnova(RinnovaRequest arg0) {
    instance.rinnova(arg0);
  }

  @Override
  public void status() {
    instance.status();
  }

  @Override
  public Token token(Object arg0) {
    return instance.token(arg0);
  }

  @Override
  public SuspectTransitsResult suspectTransits(String arg0) {
    return instance.suspectTransits(arg0);
  }
}
