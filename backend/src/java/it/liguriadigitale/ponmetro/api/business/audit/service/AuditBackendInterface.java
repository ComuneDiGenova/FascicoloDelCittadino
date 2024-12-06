package it.liguriadigitale.ponmetro.api.business.audit.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import it.liguriadigitale.ponmetro.audit.model.AuditOutputList;
import java.time.LocalDate;
import java.util.List;

public interface AuditBackendInterface {

  public void trace(AuditDto audit) throws BusinessException;

  public List<AuditOutputList> estraiUltimiRecordAudit() throws BusinessException;

  public List<AuditOutputList> estraiRecordAuditPerIntervalloDate(
      LocalDate dataInizio, LocalDate dataFine) throws BusinessException;

  public boolean verificaUltimoAccesso(FdcAudit ricerca) throws BusinessException;
}
