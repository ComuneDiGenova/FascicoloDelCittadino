package it.liguriadigitale.ponmetro.portale.business.accessoaivarchi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransitsResult;
import it.liguriadigitale.ponmetro.portale.business.accessoaivarchi.service.ServiziAccessoAiVarchiService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziAccessoAiVarchiImpl implements ServiziAccessoAiVarchiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_DOMANDE_MATRIMONIO =
      "Errore di connessione alle API Brav Accesso ai Varchi";

  @Override
  public SuspectTransitsResult getSuspectTransits(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getSuspectTransits: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      return instance.getApiGenovaParcheggi().suspectTransits(codiceFiscale);

    } catch (BusinessException e) {
      log.error(
          "ServiziAccessoAiVarchiImpl -- getSuspectTransits: errore API Genova Parcheggi:", e);
      throw new BusinessException(ERRORE_API_DOMANDE_MATRIMONIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAccessoAiVarchiImpl -- getSuspectTransits: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAccessoAiVarchiImpl -- getSuspectTransits: errore durante la chiamata delle API Genova Parcheggi ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Accesso ai Varchi"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public String getValoreDaDb(String chiave) {
    String data = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        data = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante get in accesso varchi da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return data;
  }
}
