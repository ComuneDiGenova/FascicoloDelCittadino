package it.liguriadigitale.ponmetro.portale.business.helpCzRm;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.segnalazioni.czrm.model.SegnalazioneCzrm;
import java.util.List;

public interface IHelpCzRMService {

  List<CzrmSottoFascicoli> getSottofascicolo() throws ApiException, BusinessException;

  List<CzrmServizi> getServizi() throws BusinessException, ApiException;

  List<CzrmServizi> getServizi(String sottofascicolo) throws BusinessException;

  List<SegnalazioneCzrm> getListaSegnalazioniCzrm(String codiceFiscale)
      throws BusinessException, ApiException;

  List<SegnalazioneCzrm> getListaSegnalazioniCzrmFinto(String codiceFiscale)
      throws BusinessException, ApiException;
}
