package it.liguriadigitale.ponmetro.api.business.audit.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.audit.service.AuditBackendInterface;
import it.liguriadigitale.ponmetro.api.business.audit.thread.AuditProcessoSalvataggio;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.integration.dao.query.RicercaUltimoAccessoFdcAuditDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VAuditFromDateDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VFdcAuditDAO;
import it.liguriadigitale.ponmetro.api.pojo.audit.AuditDto;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import it.liguriadigitale.ponmetro.api.pojo.audit.VFdcAudit;
import it.liguriadigitale.ponmetro.api.pojo.audit.builder.AuditOutputListBuilder;
import it.liguriadigitale.ponmetro.audit.model.AuditOutputList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditBackendImpl implements AuditBackendInterface {

  private static Log log = LogFactory.getLog(AuditBackendImpl.class);

  @Override
  public void trace(AuditDto audit) {

    log.debug("--- AuditBackendImpl trace INIZIO---");
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new AuditProcessoSalvataggio(audit));
    executorService.shutdown();
    log.debug("--- AuditBackendImpl trace FINE---");
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List estraiUltimiRecordAudit() throws BusinessException {

    log.debug("--- AuditBackendImpl estraiUltimiRecordAudit INIZIO---");
    VFdcAudit fdcaudit = new VFdcAudit();
    VFdcAuditDAO dao = new VFdcAuditDAO(fdcaudit);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<VFdcAudit> lista = helper.cercaOggetti();
    return convertiInListOutput(lista);
  }

  private List<AuditOutputList> convertiInListOutput(List<VFdcAudit> lista) {

    log.debug("AuditBackendImpl > convertiInListOutput");
    List<AuditOutputList> listaOutput = new ArrayList<>();
    for (VFdcAudit record : lista) {
      ZoneId zona = ZoneId.of("Europe/Rome");
      AuditOutputList output =
          AuditOutputListBuilder.getInstance()
              .setComparto(record.getDenominazioneComp())
              .setSezione(record.getDescrizioneSez())
              .setFunzione(record.getDescrizioneFunz())
              .setAmbienteDeploy(record.getAmbiente())
              .setIsAutorizzato(record.getAutorizzato())
              .setNomePagina(record.getNomePagina())
              .setPersonId(record.getIdFcitt())
              .setSessionToken(record.getSessionId())
              .setTimeStamp(
                  record.getTimeStamp().atOffset(zona.getRules().getOffset(LocalDateTime.now())))
              .setTipoUtente(record.getTipoUtente())
              // .setAnnoNascita(record.getAnnoNascita()) TODO
              // .setServiziEsterni(record.getServizioEsterno())
              // .setSesso(record.getSesso())
              .build();
      listaOutput.add(output);
    }
    return listaOutput;
  }

  private List<AuditOutputList> getListaProva() {
    List<AuditOutputList> listaRecord = new ArrayList<AuditOutputList>();
    AuditOutputList record = new AuditOutputList();
    record.setTimeStamp(OffsetDateTime.now());
    record.setAmbienteDeploy("PROVA");
    listaRecord.add(record);
    return listaRecord;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List estraiRecordAuditPerIntervalloDate(LocalDate dataInizio, LocalDate dataFine)
      throws BusinessException {
    VFdcAudit data = new VFdcAudit();
    data.setTimeStamp(dataInizio.atStartOfDay());
    VAuditFromDateDAO dao = new VAuditFromDateDAO(data);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<VFdcAudit> lista = helper.cercaOggetti();
    return convertiInListOutput(lista);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean verificaUltimoAccesso(FdcAudit ricerca) throws BusinessException {

    RicercaUltimoAccessoFdcAuditDAO dao = new RicercaUltimoAccessoFdcAuditDAO(ricerca);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<FdcAudit> lista = helper.cercaOggetti();
    if (lista != null) {
      return lista.isEmpty();
    } else {
      return false;
    }
  }
}
