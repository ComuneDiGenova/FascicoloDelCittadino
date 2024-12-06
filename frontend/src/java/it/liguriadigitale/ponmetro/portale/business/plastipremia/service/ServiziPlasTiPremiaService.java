package it.liguriadigitale.ponmetro.portale.business.plastipremia.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.plastipremia.model.Informazioni;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiCoupon;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiPunti;
import it.liguriadigitale.ponmetro.plastipremia.model.Problem;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;

public interface ServiziPlasTiPremiaService {

  PlastiCoupon getCoupon(String codiceFiscale) throws BusinessException, ApiException, IOException;

  Informazioni getMessage(String codiceFiscale) throws BusinessException, ApiException, IOException;

  PlastiPunti getPunti(String codiceFiscale) throws BusinessException, ApiException, IOException;

  Problem status() throws BusinessException, ApiException, IOException;
}
