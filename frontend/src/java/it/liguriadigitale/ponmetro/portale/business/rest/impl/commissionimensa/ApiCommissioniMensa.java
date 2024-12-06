package it.liguriadigitale.ponmetro.portale.business.rest.impl.commissionimensa;

import it.liguriadigitale.ponmetro.commissionimensa.apiclient.AuditApi;
import it.liguriadigitale.ponmetro.commissionimensa.model.Allegato;
import it.liguriadigitale.ponmetro.commissionimensa.model.Audit;
import it.liguriadigitale.ponmetro.commissionimensa.model.Istituto;
import java.math.BigDecimal;
import java.util.List;

public class ApiCommissioniMensa implements AuditApi {

  private AuditApi instance;

  public ApiCommissioniMensa(AuditApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Allegato downloadAllegato(BigDecimal arg0) {
    return instance.downloadAllegato(arg0);
  }

  @Override
  public List<Audit> getAllAudit(String arg0) {
    return instance.getAllAudit(arg0);
  }

  @Override
  public List<Istituto> getIstituti() {
    return instance.getIstituti();
  }

  @Override
  public Istituto getIstituto(String arg0, String arg1) {
    return instance.getIstituto(arg0, arg1);
  }
}
