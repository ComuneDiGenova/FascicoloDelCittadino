package it.liguriadigitale.ponmetro.portale.business.edilizia.condono.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.condono.model.AllegatoBody;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponse;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponseCompleta;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.util.List;

public interface ServiziEdiliziaCondono {

  List<BreadcrumbFdC> getListaBreadcrumbCondoni();

  List<CondonoResponse> getCondoni(String codiceFiscale) throws BusinessException, ApiException;

  CondonoResponseCompleta getDettaglioCondono(int numeroPratica, int annoPratica)
      throws BusinessException, ApiException;

  AllegatoBody getFilePDF(
      int numeroPratica,
      int annoPratica,
      int numeroProvvedimento,
      int annoProvvedimento,
      String nomeFile)
      throws BusinessException, ApiException;
}
