package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici.widget;

import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiSintetico.SituazionePagamentiEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class StatoPagamentiWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -9182203433450880176L;

  public StatoPagamentiWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    UtenteServiziRistorazione iscritto = null;
    Label statoPagamenti = new Label("statoPagamenti", getStatoPagamenti(iscritto));

    add(statoPagamenti);
  }

  @Override
  protected void mostraIcona() {}

  public IModel<String> getStatoPagamenti(final UtenteServiziRistorazione iscritto) {

    boolean pagati = true;
    ExecutorService ex = Executors.newWorkStealingPool();
    List<Callable<Boolean>> tasks = new ArrayList<>();
    Calendar now = Calendar.getInstance();
    List<String> anniScolastici = new ArrayList<>();
    anniScolastici.add(String.valueOf(now.get(Calendar.YEAR) - 1));
    anniScolastici.add(String.valueOf(now.get(Calendar.YEAR)));

    for (final String annoScolastico : anniScolastici) {
      tasks.add(
          new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
              return ServiceLocator.getInstance()
                  .getServiziRistorazione()
                  .statoPagamenti(iscritto.getCodiceFiscale(), annoScolastico);
            }
          });
    }

    try {
      for (final Future<Boolean> future : ex.invokeAll(tasks)) {
        if (future != null) {
          Boolean res = future.get();
          if (res != null) {
            pagati = pagati && res.booleanValue();
          }
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("StatoPagamentiWidget -- getStatoPagamenti(Utente) errore nei thread", e);
      ex.shutdown();
    }

    return new Model<>(
        (pagati
            ? SituazionePagamentiEnum.IN_REGOLA_CON_I_PAGAMENTI.value()
            : SituazionePagamentiEnum.NON_IN_REGOLA_CON_I_PAGAMENTI.value()));
  }
}
