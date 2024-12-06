package it.liguriadigitale.ponmetro.portale.business.allerte.pericolosita.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.allerte.pericolosita.service.ServiziPericolosita;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziPericolositaImpl implements ServiziPericolosita {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_PERICOLOSITA =
      "Errore di connessione alle API Pericolosita";

  @Override
  public Features getPericolosita(
      String codiceStrada, String lettera, String numeroCivico, String colore)
      throws BusinessException, ApiException, IOException {
    log.debug(
        "CP getPericolosita: "
            + codiceStrada
            + " - "
            + lettera
            + " - "
            + numeroCivico
            + " - "
            + colore);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiPericolosita()
          .searchFeature(codiceStrada, lettera, numeroCivico, colore);
    } catch (BusinessException e) {
      log.error("ServiziPericolositaImpl -- getPericolosita: errore API Pericolosita:");
      throw new BusinessException(ERRORE_API_PERICOLOSITA);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPericolositaImpl -- getPericolosita: errore durante la chiamata delle API Pericolosita"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_PERICOLOSITA);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziPericolositaImpl -- getPericolosita: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPericolositaImpl -- getPericolosita: errore durante la chiamata delle API pericolosità ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("pericolosità"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
