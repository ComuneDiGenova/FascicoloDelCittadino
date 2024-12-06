package it.liguriadigitale.ponmetro.portale.business.inpsmodi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsAttestazioneIsee200;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsDichiarazioneIsee200;

public interface ServiziInpsModiImplService {

  public ConsultazioneAttestazioneCF200 consultazioneAttestazioneCF(
      ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody) throws BusinessException;

  public ConsultazioneDichiarazioneCF200 consultazioneDichiarazioneCF(
      ConsultazioneDichiarazioneCFBody consultazioneDichiarazioneCFBody) throws BusinessException;

  ConsultazioneAttestazioneCF200 consultazioneAttestazioneCFPerBorse(
      ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody) throws BusinessException;

  public ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF(
      ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody) throws BusinessException;

  public ServiziInpsAttestazioneIsee200 attestazioneIsee(String codiceFiscale, String dataValidita)
      throws BusinessException;

  public ServiziInpsDichiarazioneIsee200 dichiarazioneISEE(
      String codiceFiscale, String dataValidita) throws BusinessException;
}
