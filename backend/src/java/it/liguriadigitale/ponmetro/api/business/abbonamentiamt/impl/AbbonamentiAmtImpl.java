package it.liguriadigitale.ponmetro.api.business.abbonamentiamt.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tickets;
import it.liguriadigitale.ponmetro.api.business.abbonamentiamt.service.AbbonamentiAmtInterface;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AbbonamentoAmtDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbbonamentiAmtImpl implements AbbonamentiAmtInterface {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_AMT = "Errore di connessione alle API AMT";

  @Override
  public List<Tessera> getAbbonamentiAmtDelLoggato(AbbonamentoAmtDto abbonamentoAmtDto)
      throws BusinessException {
    log.debug("[AbbonamentiAmtImpl] getAbbonamentiAmtDelLoggato --- INIZIO: " + abbonamentoAmtDto);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<Tessera> listaAbbonamenti = new ArrayList<Tessera>();
      Tickets ticket =
          instance.getApiAbbonamentiAmt().getTicket(abbonamentoAmtDto.getCodiceFiscale());
      if (ticket != null && ticket.getTessere() != null) {
        listaAbbonamenti = ticket.getTessere();
      }

      return listaAbbonamenti;
    } catch (BusinessException e) {
      log.error("AbbonamentiAmtImpl -- getAbbonamentiAmtDelLoggato: errore API AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "AbbonamentiAmtImpl -- getAbbonamentiAmtDelLoggato: errore nella Response:"
              + e.getMessage());
      throw new BusinessException(e.getResponse() + "  " + e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "AbbonamentiAmtImpl -- getAbbonamentiAmtDelLoggato: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new BusinessException(ERRORE_API_AMT);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public LocalDate getDataFineValidita(AbbonamentoAmtDto abbonamentoAmtDto)
      throws BusinessException {
    log.debug("[AbbonamentiAmtImpl] getDataFineValidita --- INIZIO " + abbonamentoAmtDto);

    try {
      LocalDate dataFine = null;
      if (abbonamentoAmtDto.getFineValidita() != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataFine = LocalDate.parse(abbonamentoAmtDto.getFineValidita(), formatter);
      }
      return dataFine;
    } catch (WebApplicationException e) {
      log.error(
          "AbbonamentiAmtImpl -- getDataFineValidita: errore nella Response:" + e.getMessage());
      throw new BusinessException(e.getResponse() + "  " + e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "AbbonamentiAmtImpl -- getDataFineValidita: errore durante la chiamata delle API AMT: ",
          e);
      throw new BusinessException(ERRORE_API_AMT);
    }
  }
}
