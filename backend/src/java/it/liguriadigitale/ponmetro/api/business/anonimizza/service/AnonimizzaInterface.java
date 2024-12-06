package it.liguriadigitale.ponmetro.api.business.anonimizza.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.dto.UserAnonymous;

public interface AnonimizzaInterface {

  @Deprecated
  public UserAnonymous getIdUtenteAnonimo(String codiceFiscale) throws BusinessException;

  public String getCodiceFiscale(Long idAnonimo) throws BusinessException;

  @Deprecated
  public UserAnonymous gestioneNonResidenti(AnonimoData anonim) throws BusinessException;

  public UserAnonymous gestioneNonResidentiConCambioResidenza(AnonimoData anonim)
      throws BusinessException;
}
