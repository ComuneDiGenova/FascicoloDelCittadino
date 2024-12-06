package it.liguriadigitale.ponmetro.portale.business.tributi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.tributi.service.ServiziTributiInterface;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.DettaglioProprietaUtenzaExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.SchedaTributoExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.TributiExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.ElencoVersamentiExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.TributiScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.common.tributi.TributiErrorPage;
import it.liguriadigitale.ponmetro.tributi.model.QuadroTributario;
import it.liguriadigitale.ponmetro.tributi.model.Scadenze;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziTributiImpl extends BaseServiceImpl implements ServiziTributiInterface {

  private static Log log = LogFactory.getLog(ServiziTributiImpl.class);
  private static final String ERRORE_API_TRIBUTI_MUNICIPIA =
      "Errore di connessione alle API Tributi Municipia";

  @Override
  public ElencoVersamentiExt getElencoVersamentiExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    ElencoVersamentiExt elencoVersamentiExt = new ElencoVersamentiExt();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      PojoUtils.copyAndReturn(
          elencoVersamentiExt,
          instance.getApiTributi().getElencoVersamenti(utente.getCodiceFiscaleOperatore()));
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return elencoVersamentiExt;
  }

  @Override
  public ElencoVersamentiExt getElencoVersamentiExtFilteredByYear(
      ElencoVersamentiExt toFilter, Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    ElencoVersamentiExt elencoVersamentiExtToReturn = new ElencoVersamentiExt();
    try {
      // Gestisco anche se arg is null
      ElencoVersamentiExt elencoVersamentiExt =
          toFilter == null ? getElencoVersamentiExt(utente) : toFilter;
      PojoUtils.copyAndReturn(elencoVersamentiExtToReturn, elencoVersamentiExt);
      // PEr filtrare successivamente
      elencoVersamentiExtToReturn.setAnnoDaTenere(anno);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    }
    return elencoVersamentiExtToReturn;
  }

  @Override
  public ElencoVersamentiExt getElencoVersamentiExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getElencoVersamentiExtFilteredByYear(null, utente, anno);
  }

  @Override
  public List<SchedaTributoExt> getSchedaTributoExt(String uri)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<SchedaTributoExt> schedaTributoExt = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      schedaTributoExt =
          instance.getApiTributi().getSchedaTributo(uri).stream()
              .map(parent -> PojoUtils.copyAndReturn(new SchedaTributoExt(), parent))
              .collect(Collectors.toList());
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return schedaTributoExt;
  }

  @Override
  public List<TributiExt> getTributiExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<TributiExt> tributiExt = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      tributiExt =
          instance.getApiTributi().getQuadroTributario(utente.getCodiceFiscaleOperatore()).stream()
              .map(parent -> PojoUtils.copyAndReturn(new TributiExt(), parent))
              .collect(Collectors.toList());
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return tributiExt;
  }

  @Override
  public List<TributiExt> getTributiExtFilteredByYear(
      List<TributiExt> listToFilter, Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<TributiExt> tributiExtByYear = new ArrayList<TributiExt>();
    try {
      List<TributiExt> tributiExt = listToFilter == null ? getTributiExt(utente) : listToFilter;
      // List<TributiExt> tributiExtByYear = getTributiExt(utente);
      log.debug("getTributiExtFilteredByYear " + anno);
      log.debug("getTributiExtFilteredByYear " + tributiExt);
      // remove anni diversi da anno
      // da rifare - stream?
      for (TributiExt tributoExt : tributiExt) {
        for (QuadroTributario dettaglio : tributoExt.getDettaglio()) {
          if (anno.compareTo(dettaglio.getAnno().intValue()) == 0) {
            log.debug("TRTRTRTRTRTRRTR " + dettaglio);
            // Per ora solo uno,
            TributiExt toAdd = new TributiExt();
            log.debug("1111111111111 ");
            toAdd.setTributo(tributoExt.getTributo());
            log.debug("222222222222 ");
            toAdd.addDettaglio(dettaglio);
            log.debug("3333333333 ");
            tributiExtByYear.add(toAdd);
          }
        }
      }
      log.debug("getTributiExtFilteredByYear " + tributiExtByYear);

    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    }
    return tributiExtByYear;
  }

  @Override
  public List<TributiExt> getTributiExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getTributiExtFilteredByYear(null, utente, anno);
  }

  @Override
  public List<ProprietaUtenzeExt> getProprietaUtenzeExt(Utente utente, String annoRiferimento)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    List<ProprietaUtenzeExt> proprietaUtenzeExt = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      proprietaUtenzeExt =
          instance
              .getApiTributi()
              .getProprietaUtenze(utente.getCodiceFiscaleOperatore(), annoRiferimento)
              .stream()
              .map(parent -> PojoUtils.copyAndReturn(new ProprietaUtenzeExt(), parent))
              .collect(Collectors.toList());
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return proprietaUtenzeExt;
  }

  @Override
  public DettaglioProprietaUtenzaExt getDettaglioProprietaUtenzaExt(ProprietaUtenzeExt proprieta)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    DettaglioProprietaUtenzaExt dettaglioProprietaUtenzaExt = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      dettaglioProprietaUtenzaExt =
          PojoUtils.copyAndReturn(
              new DettaglioProprietaUtenzaExt(),
              instance.getApiTributi().getDettaglioProprietaUtenze(proprieta.getUriutenza()));

    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return dettaglioProprietaUtenzaExt;
  }

  @Override
  public TributiScadenzeExt getScadenzeExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    TributiScadenzeExt scadenzeExt = new TributiScadenzeExt();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Scadenze scadenze =
          instance.getApiTributi().getElencoScadenze(utente.getCodiceFiscaleOperatore());
      PojoUtils.copyAndReturn(scadenzeExt, scadenze);
      log.debug(
          "scadenzeExtscadenzeExtscadenzeExtscadenzeExtscadenzeExtscadenzeExt: " + scadenzeExt);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    } finally {
      instance.closeConnection();
    }
    return scadenzeExt;
  }

  @Override
  public TributiScadenzeExt getScadenzeExtFilteredByYear(
      TributiScadenzeExt toFilter, Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    TributiScadenzeExt scadenzeExtToReturn = new TributiScadenzeExt();
    try {
      // Gestisco anche se arg is null
      TributiScadenzeExt scadenzeExt = toFilter == null ? getScadenzeExt(utente) : toFilter;
      PojoUtils.copyAndReturn(scadenzeExtToReturn, scadenzeExt);
      log.debug("ScadenzeExtScadenzeExt: " + scadenzeExt);
      // Per filtrare successivamente
      scadenzeExtToReturn.setAnnoDaTenere(anno);
      log.debug(
          "scadenzeExtToReturnscadenzeExtToReturnscadenzeExtToReturn: " + scadenzeExtToReturn);
    } catch (Exception e) {
      manageException(
          e,
          e.getMessage(),
          ERRORE_API_TRIBUTI_MUNICIPIA,
          getMethodName(),
          getClass().getName(),
          TributiErrorPage.class);
    }
    return scadenzeExtToReturn;
  }

  @Override
  public TributiScadenzeExt getScadenzeExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    return getScadenzeExtFilteredByYear(null, utente, anno);
  }
}
