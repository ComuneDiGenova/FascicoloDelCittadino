package it.liguriadigitale.ponmetro.portale.business.seggielettorali.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.seggi.model.DatiPersonaliComponenteSeggio;
import it.liguriadigitale.ponmetro.seggi.model.Elezioni;
import it.liguriadigitale.ponmetro.seggi.model.EsitoInvioDati;
import it.liguriadigitale.ponmetro.seggi.model.InvioDatiPersonali;
import java.io.IOException;
import java.util.List;

public interface ServiziSeggiElettorali {

  List<BreadcrumbFdC> getListaBreadcrumb(String ioCittadino);

  Elezioni getElezioni() throws BusinessException, ApiException, IOException;

  DatiPersonaliComponenteSeggio getDatiPersonaliComponenteSeggio(
      String codiceFiscale, String identificativoElezione)
      throws BusinessException, ApiException, IOException;

  EsitoInvioDati postDatiPersonaliComponenteSeggio(InvioDatiPersonali invioDatiPersonali)
      throws BusinessException, ApiException, IOException;
}
