package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.CertificatoAnagrafeApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiPersonaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoCartaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoConsegnaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetUtilizzoCertificatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoSCResponseGenericResponse;

public class ApiCertificatiAnagrafe implements CertificatoAnagrafeApi {

  private CertificatoAnagrafeApi instance;

  public ApiCertificatiAnagrafe(CertificatoAnagrafeApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public GetCertificatiResponseGenericResponse getCertificati() {
    return instance.getCertificati();
  }

  @Override
  public GetCertificatiPersonaResponseGenericResponse getCertificatiPersona(String var1) {
    return instance.getCertificatiPersona(var1);
  }

  @Override
  public GetScaricoCertificatoResponseGenericResponse getScaricoCertificato(
      String var1, String var2, String var3) {
    return instance.getScaricoCertificato(var1, var2, var3);
  }

  @Override
  public GetUtilizzoCertificatiResponseGenericResponse getUtilizzoCertificati() {
    return instance.getUtilizzoCertificati();
  }

  @Override
  public PostRichiestaEmissioneCertificatoSCResponseGenericResponse
      postRichiestaEmissioneCertificato(PostRichiestaEmissioneCertificatoRequest var1) {
    return instance.postRichiestaEmissioneCertificato(var1);
  }

  @Override
  public GetTipoCartaResponseGenericResponse getTipoCarta() {
    return instance.getTipoCarta();
  }

  @Override
  public GetTipoConsegnaResponseGenericResponse getTipoConsegna() {
    return instance.getTipoConsegna();
  }
}
