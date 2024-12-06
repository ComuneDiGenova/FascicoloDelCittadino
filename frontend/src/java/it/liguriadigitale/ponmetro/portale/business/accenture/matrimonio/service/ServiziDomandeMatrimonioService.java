package it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiMatrimonio;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;
import java.util.List;

public interface ServiziDomandeMatrimonioService {

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
      getMatrimoniRichiedenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response
      getMatrimoniConiugeByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException;

  //	ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
  // getListaMatrimoni(String codiceFiscale)
  //			throws BusinessException, IOException, ApiException;

  List<DatiMatrimonio> getListaDomandeMatrimonioRichiedenteConiuge(String codiceFiscale)
      throws BusinessException, IOException, ApiException;

  List<DatiMatrimonio> getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia(
      String codiceFiscale, TipologiaRichiestaEnum tipologia)
      throws BusinessException, IOException, ApiException;
}
