package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.integration.dao.funzioni.FMsgScadenzaOldDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.ScadenzaPassata;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TemplateScadenzeRunnable {

  private static Log log = LogFactory.getLog(TemplateScadenzeRunnable.class);

  protected LocalDateTime controllaDataInvioMessaggio(
      LocalDate dataScadenza, Long idScadenza, ScadenzePersonalizzateDto utenteDto) {
    LocalDateTime dataInvioMessaggio = dataScadenza.minusMonths(1).atStartOfDay();
    dataInvioMessaggio = seDataNelPassatoAvvertiUtente(dataInvioMessaggio, idScadenza, utenteDto);
    return dataInvioMessaggio;
  }

  protected LocalDateTime seDataNelPassatoAvvertiUtente(
      LocalDateTime dataInvioMessaggio, Long idScadenza, ScadenzePersonalizzateDto utenteDto) {
    if (dataInvioMessaggio.isBefore(LocalDateTime.now())) {
      log.debug("Trovata scadenza nel passato:" + utenteDto);
      avviaBatchMessaggio(idScadenza, utenteDto);
      return LocalDateTime.now();
    } else {
      return dataInvioMessaggio;
    }
  }

  protected void avviaBatchMessaggio(Long idScadenza, ScadenzePersonalizzateDto utenteDto) {
    log.debug("avviaBatchMessaggio INIZIO");
    ScadenzaPassata scadenza = new ScadenzaPassata();
    scadenza.setIdFCitt(utenteDto.getIdFcitt());
    scadenza.setIdScadenza(idScadenza);

    PonMetroDatasourceTransactionManager manager =
        new PonMetroDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {
            log.debug("PonMetroDatasourceTransactionManager INIZIO");
            FMsgScadenzaOldDAO dao = new FMsgScadenzaOldDAO(scadenza);
            List<Long> result = dao.retrieveWhere(paramConnection);
            // TODO solo se errore scrivere nei log
            if (!result.isEmpty()) {
              log.debug("Valore di ritorno della funzione FMsgScadenzaOldDAO=" + result.get(0));
            }
          }
        };
    try {
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("Impossibile richiamare la funzione: ", e);
    }
  }
}
