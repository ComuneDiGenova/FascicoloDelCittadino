package it.liguriadigitale.ponmetro.portale.business.arte.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Componenti;
import it.liguriadigitale.ponmetro.arte.model.Daticontr;
import it.liguriadigitale.ponmetro.arte.model.Fattura;
import it.liguriadigitale.ponmetro.arte.model.FatturaPdf;
import it.liguriadigitale.ponmetro.arte.model.Mora;
import it.liguriadigitale.ponmetro.portale.business.arte.service.ServiziArte;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziArteImpl implements ServiziArte {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_ARTE = "Errore di connessione alle API ARTE";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("arte", "Contratti ARTE"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbComponenti() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("arte", "Contratti ARTE"));
    listaBreadcrumb.add(new BreadcrumbFdC("componentiNucleoArte", "Componenti nucleo"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbFatture() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("arte", "Contratti ARTE"));
    listaBreadcrumb.add(new BreadcrumbFdC("fattureArte", "Fatture"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbMore() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("arte", "Contratti ARTE"));
    listaBreadcrumb.add(new BreadcrumbFdC("moreArte", "More"));

    return listaBreadcrumb;
  }

  @Override
  public Daticontr getDatiContratti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDatiContratti: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiArte().searchdaticontr(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziArteImpl -- getDatiContratti: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARTE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziArteImpl -- getDatiContratti: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziArteImpl -- getDatiContratti: errore durante la chiamata delle API ARTE ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARTE"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Componenti getComponenti(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getComponenti: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiArte().searchcomponenti(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziArteImpl -- getComponenti: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARTE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziArteImpl -- getComponenti: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziArteImpl -- getComponenti: errore durante la chiamata delle API ARTE ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARTE"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Mora getMore(String codiceFiscale, String idImmobile)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getMore: " + codiceFiscale + " - " + idImmobile);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiArte().searchmora(codiceFiscale, idImmobile);
    } catch (BusinessException e) {
      log.error("ServiziArteImpl -- getMore: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARTE);
    } catch (WebApplicationException e) {
      log.error("ServiziArteImpl -- getMore: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziArteImpl -- getMore: errore durante la chiamata delle API ARTE ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARTE"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Fattura> getListaFattureDiQuellAnno(List<Fattura> listaFatture, BigDecimal anno) {
    log.debug("CP listaFattureDiQuellAnno = " + anno);

    log.debug("CP lista fatture = " + listaFatture);

    List<Fattura> listaFattureDiQuellAnno = new ArrayList<Fattura>();

    // TODO controllare
    if (LabelFdCUtil.checkIfNotNull(listaFatture) && !LabelFdCUtil.checkEmptyList(listaFatture)) {
      listaFattureDiQuellAnno =
          listaFatture.stream()
              .filter(
                  elem ->
                      PageUtil.isStringValid(elem.getAnno())
                          && anno.compareTo(new BigDecimal(elem.getAnno())) == 0)
              .collect(Collectors.toList());
    }

    log.debug("CP lista fatture anno = " + listaFattureDiQuellAnno);

    return listaFattureDiQuellAnno;
  }

  @Override
  public FatturaPdf getFatturaPdf(String nomeFile)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getFatturaPdf: " + nomeFile);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiArte().getFattura(nomeFile);
    } catch (BusinessException e) {
      log.error("ServiziArteImpl -- getFatturaPdf: errore API ARTE:", e);
      throw new BusinessException(ERRORE_API_ARTE);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziArteImpl -- getFatturaPdf: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("ServiziArteImpl -- getFatturaPdf: errore durante la chiamata delle API ARTE ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("ARTE"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
