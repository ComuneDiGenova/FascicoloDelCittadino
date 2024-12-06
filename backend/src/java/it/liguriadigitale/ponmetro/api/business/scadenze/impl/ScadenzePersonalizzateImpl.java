package it.liguriadigitale.ponmetro.api.business.scadenze.impl;

import it.liguriadigitale.ponmetro.api.business.scadenze.service.ScadenzePersonalizzateInterface;
import it.liguriadigitale.ponmetro.api.business.scadenze.thread.ScadenzeThreadPrincipale;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzePersonalizzateImpl implements ScadenzePersonalizzateInterface {

  private static Log log = LogFactory.getLog(ScadenzePersonalizzateImpl.class);

  @Override
  public void verificaScadenzePersonalizzate(ScadenzePersonalizzateDto utenteDto) {
    // usa un 3d
    // https://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
    log.debug("--- verificaScadenzePersonalizzate INIZIO---");
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new ScadenzeThreadPrincipale(utenteDto));
    executorService.shutdown();
    log.debug("--- verificaScadenzePersonalizzate FINE---");
  }
}
