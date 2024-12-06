package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.veicoli.impl.AssicurazioneImpl;
import it.liguriadigitale.ponmetro.api.business.veicoli.service.AssicurazioneInterface;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.MovimentoBibliotecaDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzeThreadPrincipale implements Runnable {
  private static Log log = LogFactory.getLog(ScadenzeThreadPrincipale.class);

  private ScadenzePersonalizzateDto utenteDto;

  public ScadenzeThreadPrincipale(ScadenzePersonalizzateDto utenteDto) {
    super();
    this.utenteDto = utenteDto;
  }

  private Collection<? extends Callable<Boolean>> inizializzaProcessiAssicurazioneBollo(
      ScadenzePersonalizzateDto utenteDto) throws BusinessException {
    List<Callable<Boolean>> tasks = new ArrayList<>();

    for (final VeicoloDto veicolo : utenteDto.getVeicoliAttivi()) {
      if (veicolo != null) {
        ScadenzaBolloAutoRun taskBollo = new ScadenzaBolloAutoRun(utenteDto, veicolo);
        VerificaAssicurazioneVeicoli assicurazione = chiamaApiAssicurazione(veicolo);
        ScadenzaAssicurazioneAutoRun taskAssicurazione =
            new ScadenzaAssicurazioneAutoRun(utenteDto, assicurazione);
        ScadenzaRevisioneAutoRun taskRevisione =
            new ScadenzaRevisioneAutoRun(utenteDto, assicurazione);
        tasks.add(taskBollo);
        tasks.add(taskAssicurazione);
        tasks.add(taskRevisione);
      }
    }
    return tasks;
  }

  private VerificaAssicurazioneVeicoli chiamaApiAssicurazione(VeicoloDto veicoloDto)
      throws BusinessException {

    AssicurazioneInterface service = new AssicurazioneImpl();
    VerificaAssicurazioneVeicoli assicurazione = service.getAssicurazione(veicoloDto);
    return assicurazione;
  }

  private Collection<? extends Callable<Boolean>> inizializzaProcessiBiblioteca(
      ScadenzePersonalizzateDto utenteDto) {
    log.debug("inizializzaProcessiBiblioteca");

    List<Callable<Boolean>> tasks = new ArrayList<>();

    for (final MovimentoBibliotecaDto movimentoBibliotecaDto : utenteDto.getMovimentiBiblioteca()) {
      if (movimentoBibliotecaDto != null) {
        ScadenzaBibliotecaAutoRun taskBiblioteca =
            new ScadenzaBibliotecaAutoRun(utenteDto, movimentoBibliotecaDto);
        tasks.add(taskBiblioteca);
      }
    }
    return tasks;
  }

  @Override
  public void run() {
    log.debug("principale in corso...");
    Thread ct = Thread.currentThread();
    ct.setName("Thread principale");

    ExecutorService executor = Executors.newWorkStealingPool();
    List<Callable<Boolean>> tasks = new ArrayList<>();

    if (utenteDto.getVeicoliAttivi() != null) {
      try {
        tasks.addAll(inizializzaProcessiAssicurazioneBollo(utenteDto));
      } catch (BusinessException e) {
        log.error("Errore durante l'esecuzione del thread principale:", e);
      }
    }
    if (utenteDto.getDataScadenza() != null) {
      tasks.add(new ScadenzaCartaIdentitaRun(utenteDto));
    }
    if (utenteDto.getMovimentiBiblioteca() != null) {
      tasks.addAll(inizializzaProcessiBiblioteca(utenteDto));
    }

    if (utenteDto.getAbbonamentiAmt() != null) {
      tasks.add(new ScadenzaAbbonamentiAmtAutoRun(utenteDto));
    }

    if (utenteDto.getPermessiAreaBlu() != null) {
      tasks.add(new ScadenzaAreaBluAutoRun(utenteDto));
    }

    try {

      executor.invokeAll(tasks);
      log.debug("attempt to shutdown executor");
      executor.shutdown();
    } catch (InterruptedException e) {
      log.error("tasks interrupted");
      Thread.currentThread().interrupt();
    } finally {
      if (!executor.isTerminated()) {
        log.error("cancel non-finished tasks");
      }
      executor.shutdownNow();
      log.debug("shutdown finished");
    }
  }
}
