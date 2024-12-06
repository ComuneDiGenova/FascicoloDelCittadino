package it.liguriadigitale.ponmetro.portale.business.seggielettorali.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobali;
import it.liguriadigitale.ponmetro.portale.business.seggielettorali.service.ServiziSeggiElettorali;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.seggi.model.DatiPersonaliComponenteSeggio;
import it.liguriadigitale.ponmetro.seggi.model.Elezioni;
import it.liguriadigitale.ponmetro.seggi.model.EsitoInvioDati;
import it.liguriadigitale.ponmetro.seggi.model.InvioDatiPersonali;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSeggiElettoraliImpl implements ServiziSeggiElettorali {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_SEGGI = "Errore di connessione alle API Seggi Elettorali";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb(String ioCittadino) {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("imieidati", ioCittadino));
    listaBreadcrumb.add(new BreadcrumbFdC("serviziSeggiElettorali", "Servizi seggi elettorali"));

    return listaBreadcrumb;
  }

  @Override
  public Elezioni getElezioni() throws BusinessException, ApiException, IOException {

    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance().getApiSeggi().getElezioni();

    } catch (BusinessException e) {
      log.error("ServiziSeggiElettoraliImpl -- getElezioni: errore API seggi elettorali:", e);
      throw new BusinessException(ERRORE_API_SEGGI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getElezioni: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getElezioni: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("seggi elettorali"));
    }
  }

  @Override
  public DatiPersonaliComponenteSeggio getDatiPersonaliComponenteSeggio(
      String codiceFiscale, String identificativoElezione)
      throws BusinessException, ApiException, IOException {

    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiSeggi()
          .getDatiCittadino(codiceFiscale, identificativoElezione);

    } catch (BusinessException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getDatiPersonaliComponenteSeggio: errore API seggi elettorali:",
          e);
      throw new BusinessException(ERRORE_API_SEGGI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getDatiPersonaliComponenteSeggio: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- getDatiPersonaliComponenteSeggio: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("seggi elettorali"));
    }
  }

  @Override
  public EsitoInvioDati postDatiPersonaliComponenteSeggio(InvioDatiPersonali invioDatiPersonali)
      throws BusinessException, ApiException, IOException {

    try {
      log.info("inizio postDatiPersonaliComponenteSeggio");
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiSeggi()
          .setDatiCittadino(invioDatiPersonali);

    } catch (BusinessException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- postDatiPersonaliComponenteSeggio: errore API seggi elettorali:",
          e);
      throw new BusinessException(ERRORE_API_SEGGI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- postDatiPersonaliComponenteSeggio: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSeggiElettoraliImpl -- postDatiPersonaliComponenteSeggio: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("seggi elettorali"));
    }
  }
}
