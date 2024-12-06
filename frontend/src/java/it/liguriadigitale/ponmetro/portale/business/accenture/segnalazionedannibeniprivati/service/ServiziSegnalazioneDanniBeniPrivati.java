package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response;
import java.io.IOException;

public interface ServiziSegnalazioneDanniBeniPrivati {

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response getListSegnalazioneDanniBeniPrivati(
      String codiceFiscale, TipologiaRichiestaEnum tipo)
      throws BusinessException, IOException, ApiException;
}
