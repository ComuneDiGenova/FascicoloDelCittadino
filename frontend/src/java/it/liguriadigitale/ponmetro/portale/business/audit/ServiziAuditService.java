package it.liguriadigitale.ponmetro.portale.business.audit;

import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;

public interface ServiziAuditService {

  public void trace(AuditDto dto);
}
