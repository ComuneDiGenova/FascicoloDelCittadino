package it.liguriadigitale.ponmetro.portale.business.oggettismarriti.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.oggettismarriti.model.OggettiSmarriti;
import it.liguriadigitale.ponmetro.oggettismarriti.model.PayloadOggettiSmarriti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.oggettismarriti.service.ServiziOggettiSmarritiService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziOggettiSmarritiImpl implements ServiziOggettiSmarritiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_OGGETTI_SMARRITI =
      "Errore di connessione alle API Oggetti Smarriti";

  @Override
  public List<OggettiSmarriti> getListaOggettiSmarriti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziOggettiSmarritiImpl -- getListaOggettiSmarriti");

    try {
      PayloadOggettiSmarriti payloadOggettiSmarriti = new PayloadOggettiSmarriti();
      payloadOggettiSmarriti.setCF(codiceFiscale);

      //			String pwd  = BaseServiceImpl.OGGETTI_SMARRITI_PASSWORD;
      //			byte[] bytesPwd = pwd.getBytes(StandardCharsets.UTF_8);
      //			String utf8EncodedPwd = new String(bytesPwd, StandardCharsets.UTF_8);
      //
      //			String usr  = BaseServiceImpl.OGGETTI_SMARRITI_USERNAME;
      //			byte[] bytesUsr = usr.getBytes(StandardCharsets.UTF_8);
      //			String utf8EncodedUsr = new String(bytesUsr, StandardCharsets.UTF_8);
      //
      //			payloadOggettiSmarriti.setPassword(utf8EncodedPwd);
      //			payloadOggettiSmarriti.setUsername(utf8EncodedUsr);

      log.debug("Payload oggetti smarriti = " + payloadOggettiSmarriti);

      return ServiceLocatorLivelloUno.getInstance()
          .getApiOggettiSmarriti()
          .getOggettiSmarriti(payloadOggettiSmarriti);

    } catch (BusinessException e) {
      log.error(
          "ServiziOggettiSmarritiImpl -- getListaOggettiSmarriti: errore API oggetti smarriti:", e);
      throw new BusinessException(ERRORE_API_OGGETTI_SMARRITI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziOggettiSmarritiImpl -- getListaOggettiSmarriti: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziOggettiSmarritiImpl -- getListaOggettiSmarriti: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Oggetti Smarriti"));
    }
  }
}
