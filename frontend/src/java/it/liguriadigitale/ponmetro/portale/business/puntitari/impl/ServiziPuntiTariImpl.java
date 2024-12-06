package it.liguriadigitale.ponmetro.portale.business.puntitari.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.puntitari.service.ServiziPuntiTariService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.puntitari.model.PuntiTari;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziPuntiTariImpl implements ServiziPuntiTariService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_PUNTI_TARI = "Errore di connessione alle API Punti TARI";

  @Override
  public PuntiTari getPuntiTari(String codiceFiscale, Integer anno)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPuntiTari: " + codiceFiscale + " " + anno);

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiPuntiTari().getPunti(codiceFiscale, anno);

    } catch (BusinessException e) {
      log.error("ServiziPuntiTariImpl -- getPuntiTari: errore API punti TARI:", e);
      throw new BusinessException(ERRORE_API_PUNTI_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPuntiTariImpl -- getPuntiTari: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPuntiTariImpl -- getPuntiTari: errore durante la chiamata delle API punti TARI ",
          e);

      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Punti Tari"));
    } finally {
      instance.closeConnection();
    }
  }
}
