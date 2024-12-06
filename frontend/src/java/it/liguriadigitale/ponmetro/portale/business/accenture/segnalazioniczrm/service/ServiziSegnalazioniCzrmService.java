package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.util.SegnalazioniCzrmUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountIdCasesGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsEmailMessageIDGet200Response;
import java.io.IOException;

public interface ServiziSegnalazioniCzrmService {

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
          throws BusinessException, IOException, ApiException;

  ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      getRecuperoUtenteByIdAccenture(String idAccenture)
          throws BusinessException, IOException, ApiException;

  ServicesDataV590SobjectsAccountIdCasesGet200Response getListaSegnalazioniByIdUtenteAccenture(
      String idAccenture) throws BusinessException, IOException, ApiException;

  SegnalazioniCzrmUtil getListaSegnalazioniCzrm(String codiceFiscale)
      throws BusinessException, IOException, ApiException;

  ServicesDataV590SobjectsCaseIdEmailsGet200Response getListaMailSegnalazione(
      String idAccenture, String emailAccenture)
      throws BusinessException, IOException, ApiException;

  ServicesDataV590SobjectsCaseIdCommentiRGet200Response getListaCommentiSegnalazione(
      String idAccenture, String nomeCognome) throws BusinessException, IOException, ApiException;

  ServicesDataV590SobjectsEmailMessageIDGet200Response getMailDaCommento(String idEmailMessage)
      throws BusinessException, IOException, ApiException;
}
