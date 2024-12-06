package it.liguriadigitale.ponmetro.portale.business.supportoistanzeverbalipl.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.supportoistanzeverbalipl.service.ServiziSupportoIstanzeVerbaliPLInterface;
import it.liguriadigitale.ponmetro.portale.presentation.common.tributi.TributiErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSupportoIstanzeVerbaliPLImpl extends BaseServiceImpl
    implements ServiziSupportoIstanzeVerbaliPLInterface {

  private static Log log = LogFactory.getLog(ServiziSupportoIstanzeVerbaliPLImpl.class);

  private static final String ERRORE_API_SUPPORTO_ISTANZE_VERBALI_PL =
      "Errore di connessione al backend per API Supporto Istanze Verbali PL";

  private List<DatiMotivoSummary> getElencoMotiviSummary(
      Boolean equal, String serie, List<String> articoli)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<DatiMotivoSummary> listDatiMotivoSummary = new ArrayList<DatiMotivoSummary>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String articoliDaMandare = articoli == null || articoli.isEmpty() ? "-" : articoli.get(0);
      for (int i = 1; articoli != null && i < articoli.size(); i++) {
        articoliDaMandare += "-" + articoli.get(i);
      }
      log.error(
          "getElencoMotiviSummary::::: equal: "
              + equal
              + ", serie: "
              + serie
              + ", articoliDaMandare: "
              + articoliDaMandare);
      listDatiMotivoSummary =
          instance
              .getApiIstanzeVerbaliPl()
              .istanzeplSummaryMotiviAndorWantequalSerieArticoliGet(
                  false, equal, serie, articoliDaMandare);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_SUPPORTO_ISTANZE_VERBALI_PL,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return listDatiMotivoSummary;
  }

  @Override
  public List<DatiMotivoSummary> getElencoMotiviUgualiCodiceASummary(String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<DatiMotivoSummary> listDatiMotivoSummary = new ArrayList<DatiMotivoSummary>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      listDatiMotivoSummary =
          instance.getApiIstanzeVerbaliPl().istanzeplSummaryMotivoCodiceHermesGet(codiceHermes);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_SUPPORTO_ISTANZE_VERBALI_PL,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return listDatiMotivoSummary;
  }

  @Override
  public List<DatiMotivoSummary> getElencoMotiviDiversiDaSummary(
      String serie, List<String> articoli)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getElencoMotiviSummary(false, serie, articoli);
  }

  @Override
  public List<DatiMotivoSummary> getElencoMotiviUgualiASummary(String serie, List<String> articoli)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getElencoMotiviSummary(true, serie, articoli);
  }

  private List<DatiMotivo> getElencoMotivi(
      Boolean equal, String serie, List<String> articoli, String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<DatiMotivo> listDatiMotivo = new ArrayList<DatiMotivo>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String articoliDaMandare = articoli == null || articoli.isEmpty() ? "-" : articoli.get(0);
      for (int i = 1; articoli != null && i < articoli.size(); i++) {
        articoliDaMandare += "-" + articoli.get(i);
      }

      listDatiMotivo =
          instance
              .getApiIstanzeVerbaliPl()
              .istanzeplCompleteMotiviAndorWantequalSerieArticoliCodiceHermesGet(
                  false, equal, serie, articoliDaMandare, codiceHermes);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_SUPPORTO_ISTANZE_VERBALI_PL,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return listDatiMotivo;
  }

  @Override
  public List<DatiMotivo> getElencoMotiviDiversiDa(
      String serie, List<String> articoli, String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getElencoMotivi(false, serie, articoli, codiceHermes);
  }

  @Override
  public List<DatiMotivo> getElencoMotiviUgualiA(
      String serie, List<String> articoli, String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getElencoMotivi(true, serie, articoli, codiceHermes);
  }

  @Override
  public List<DatiDocumento> getElencoDocumenti(String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<DatiDocumento> listDatiDocumento = new ArrayList<DatiDocumento>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      listDatiDocumento =
          instance.getApiIstanzeVerbaliPl().istanzeplCompleteDocumentiCodiceHermesGet(codiceHermes);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_SUPPORTO_ISTANZE_VERBALI_PL,
          getMethodName(),
          getClass().getName(),
          ErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return listDatiDocumento;
  }
}
