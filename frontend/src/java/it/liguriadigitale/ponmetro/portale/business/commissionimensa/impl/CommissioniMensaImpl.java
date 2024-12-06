package it.liguriadigitale.ponmetro.portale.business.commissionimensa.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.commissionimensa.model.Allegato;
import it.liguriadigitale.ponmetro.commissionimensa.model.Audit;
import it.liguriadigitale.ponmetro.commissionimensa.model.Istituto;
import it.liguriadigitale.ponmetro.portale.business.commissionimensa.service.CommissioniMensaService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class CommissioniMensaImpl implements CommissioniMensaService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_COMMISSIONI_MENSA =
      "Errore di connessione alle API Commissioni Mensa";

  @Override
  public List<Istituto> getIstituti() throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[CommissioniMensaImpl] getIstituti ");
      return instance.getApiCommissioniMensa().getIstituti();

    } catch (BusinessException e) {
      log.error("CommissioniMensaImpl -- getIstituti: errore API Commissioni Mensa:", e);
      throw new BusinessException(ERRORE_API_COMMISSIONI_MENSA);
    } catch (WebApplicationException e) {
      log.error(
          "CommissioniMensaImpl -- getIstituti: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "CommissioniMensaImpl -- getIstituti: errore durante la chiamata delle API Commissioni Mensa ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Commissioni mensa"));

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Audit> getAllAudit(String codiceScuola)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[CommissioniMensaImpl] getAudit " + codiceScuola);
      return instance.getApiCommissioniMensa().getAllAudit(codiceScuola);

    } catch (BusinessException e) {
      log.error("CommissioniMensaImpl -- getAudit: errore API Commissioni Mensa:", e);
      throw new BusinessException(ERRORE_API_COMMISSIONI_MENSA);
    } catch (WebApplicationException e) {
      log.error(
          "CommissioniMensaImpl -- getAudit: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "CommissioniMensaImpl -- getAudit: errore durante la chiamata delle API Commissioni Mensa ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("anni scolastici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Allegato getPdfVerbale(BigDecimal idVerbale)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("[CommissioniMensaImpl] getPdfVerbale " + idVerbale);
      return instance.getApiCommissioniMensa().downloadAllegato(idVerbale);

    } catch (BusinessException e) {
      log.error("CommissioniMensaImpl -- getPdfVerbale: errore API Commissioni Mensa:", e);
      throw new BusinessException(ERRORE_API_COMMISSIONI_MENSA);
    } catch (WebApplicationException e) {
      log.error(
          "CommissioniMensaImpl -- getPdfVerbale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "CommissioniMensaImpl -- getPdfVerbale: errore durante la chiamata delle API Commissioni Mensa ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("anni scolastici"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
