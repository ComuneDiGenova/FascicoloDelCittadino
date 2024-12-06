package it.liguriadigitale.ponmetro.portale.business.arte.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Componenti;
import it.liguriadigitale.ponmetro.arte.model.Daticontr;
import it.liguriadigitale.ponmetro.arte.model.Fattura;
import it.liguriadigitale.ponmetro.arte.model.FatturaPdf;
import it.liguriadigitale.ponmetro.arte.model.Mora;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ServiziArte {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<BreadcrumbFdC> getListaBreadcrumbComponenti();

  List<BreadcrumbFdC> getListaBreadcrumbFatture();

  List<BreadcrumbFdC> getListaBreadcrumbMore();

  Daticontr getDatiContratti(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Componenti getComponenti(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Mora getMore(String codiceFiscale, String idImmobile)
      throws BusinessException, ApiException, IOException;

  List<Fattura> getListaFattureDiQuellAnno(List<Fattura> listaFatture, BigDecimal annoDaMostrare);

  FatturaPdf getFatturaPdf(String nomeFile) throws BusinessException, ApiException, IOException;
}
