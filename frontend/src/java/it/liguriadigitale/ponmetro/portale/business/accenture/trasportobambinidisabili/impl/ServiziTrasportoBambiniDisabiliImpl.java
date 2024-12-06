package it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.service.ServiziTrasportoBambiniDisabiliService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DatiAccount;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviate;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziTrasportoBambiniDisabiliImpl implements ServiziTrasportoBambiniDisabiliService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TRASPORTO_BAMBINI_DISABILI =
      "Errore di connessione alle API Domande Trasporto Bambini Disabili";

  @Override
  public DatiAccount getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
      throws BusinessException, IOException, ApiException {

    log.debug(
        "ServiziTrasportoBambiniDisabiliImpl getRecuperoUtenteByCodiceFiscale = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    DatiAccount response = null;

    try {
      response = instance.getApiTrasporti().datiAccountByCF(codiceFiscale);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByCodiceFiscale: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_TRASPORTO_BAMBINI_DISABILI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta trasporto alunni disabili"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DatiAccount getRecuperoUtenteByIdAccenture(String idAccenture)
      throws BusinessException, IOException, ApiException {

    log.debug(
        "ServiziTrasportoBambiniDisabiliImpl getRecuperoUtenteByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    DatiAccount response = null;

    try {
      response = instance.getApiTrasporti().datiAccountById(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByIdAccenture: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_TRASPORTO_BAMBINI_DISABILI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getRecuperoUtenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta trasporto alunni disabili"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DomandeInviate getListaDomandeTrasportoBambiniDisabili(String codiceFiscale)
      throws BusinessException, IOException, ApiException {
    log.debug(
        "ServiziTrasportoBambiniDisabiliImpl getListaDomandeTrasportoBambiniDisabili = "
            + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    try {

      DatiAccount utenteAccentureByCf = getRecuperoUtenteByCodiceFiscale(codiceFiscale);

      if (LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
        return null;
      }

      String idUtenteAccentureByCf = utenteAccentureByCf.getId();

      log.debug("idUtenteAccentureByCf = " + idUtenteAccentureByCf);

      DatiAccount utemteById = getRecuperoUtenteByIdAccenture(idUtenteAccentureByCf);
      log.debug("utemteById = " + utemteById);

      DomandeInviate domande = instance.getApiTrasporti().richiesteInviate(idUtenteAccentureByCf);

      log.debug("CP lista trasporti bambini disabili = " + domande);

      return domande;

    } catch (BusinessException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getListaDomandeTrasportoBambiniDisabili: errore API ACENTURE:",
          e);
      throw new BusinessException(ERRORE_API_TRASPORTO_BAMBINI_DISABILI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getListaDomandeTrasportoBambiniDisabili: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTrasportoBambiniDisabiliImpl -- getListaDomandeTrasportoBambiniDisabili: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richieste trasporto alunni disabili"));
    }
  }
}
