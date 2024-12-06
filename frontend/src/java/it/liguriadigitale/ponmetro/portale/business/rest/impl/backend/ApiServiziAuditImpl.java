package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.presentation.rest.audit.service.AuditRestInterface;

public class ApiServiziAuditImpl implements AuditRestInterface {

  private AuditRestInterface instance;

  public ApiServiziAuditImpl(AuditRestInterface proxy) {
    instance = proxy;
  }

  @Override
  public void trace(AuditDto arg0) {
    instance.trace(arg0);
  }
}
