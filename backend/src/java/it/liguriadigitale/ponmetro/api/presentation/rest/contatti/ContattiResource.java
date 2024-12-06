package it.liguriadigitale.ponmetro.api.presentation.rest.contatti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.contattiutente.apiclient.ContattiUtenteApi;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContattiResource implements ContattiUtenteApi {

  private static Log log = LogFactory.getLog(ContattiResource.class);

  @Override
  public List<ContattiUtente> getContatti(Long idFcitt) {
    try {
      return ServiceLocator.getInstance().getContattiUtente().getContattiUtente(idFcitt);
    } catch (BusinessException e) {
      log.error("Errore durante getContatti utente: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare contatti utente");
    }
  }

  @Override
  public void upsertContatti(Long idFcitt, String tipo, ContattiUtente contatti) {
    log.debug("upsertContatti " + idFcitt + " - " + contatti);
    try {
      ServiceLocator.getInstance()
          .getContattiUtente()
          .inserisciAggiornaContatto(idFcitt, tipo, contatti);
    } catch (BusinessException e) {
      log.error("Errore durante upsertContatti utente: " + e.getMessage());
      throw new BadRequestException("Impossibile inserire o aggiornare contatti utente");
    }
  }

  @Override
  public void deleteContatti(Long idFcitt, String contatti) {
    log.debug("deleteContatti " + idFcitt + " - " + contatti);
    try {
      ServiceLocator.getInstance().getContattiUtente().cancellaContatto(idFcitt, contatti);
    } catch (BusinessException e) {
      log.error("Errore durante deleteContatti utente: " + e.getMessage());
      throw new BadRequestException("Impossibile cancellare contatti utente");
    }
  }
}
