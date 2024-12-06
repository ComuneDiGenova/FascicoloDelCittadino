package it.liguriadigitale.ponmetro.portale.business.rest.impl.inpsmodi;

import it.liguriadigitale.ponmetro.inps.modi.apiclient.InpsmodiApi;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsAttestazioneIsee200;
import it.liguriadigitale.ponmetro.inps.modi.model.ServiziInpsDichiarazioneIsee200;

public class ApiInpsModiImpl implements InpsmodiApi {

  private InpsmodiApi instance;

  public ApiInpsModiImpl(InpsmodiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ServiziInpsAttestazioneIsee200 getAttestazioneCF(String arg0, String arg1) {
    return instance.getAttestazioneCF(arg0, arg1);
  }

  @Override
  public ServiziInpsDichiarazioneIsee200 getDichiarazioneCF(String arg0, String arg1) {
    return instance.getDichiarazioneCF(arg0, arg1);
  }

  @Override
  public ConsultazioneAttestazioneCF200 postConsultazioneAttestazioneCF(
      ConsultazioneAttestazioneCFBody arg0, String arg1) {
    return instance.postConsultazioneAttestazioneCF(arg0, arg1);
  }

  @Override
  public ConsultazioneDichiarazioneCF200 postConsultazioneDichiarazioneCF(
      ConsultazioneDichiarazioneCFBody arg0, String arg1) {
    return instance.postConsultazioneDichiarazioneCF(arg0, arg1);
  }

  @Override
  public ConsultazioneIndicatoreCF200 postConsultazioneIndicatoreCF(
      ConsultazioneIndicatoreCFBody arg0, String arg1) {
    return instance.postConsultazioneIndicatoreCF(arg0, arg1);
  }
}
