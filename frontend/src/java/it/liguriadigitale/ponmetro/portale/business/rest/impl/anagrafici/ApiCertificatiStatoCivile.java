package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.CertificatoStatoCivileApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCertificatiAttiPersonaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetRicercaAttiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoAttiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoConsegnaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoSCResponseGenericResponse;
import java.time.LocalDate;

public class ApiCertificatiStatoCivile implements CertificatoStatoCivileApi {

  public CertificatoStatoCivileApi instance;

  public ApiCertificatiStatoCivile(CertificatoStatoCivileApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public GetCertificatiAttiPersonaResponseGenericResponse getCertificatiAttiPersona(String var1) {
    return instance.getCertificatiAttiPersona(var1);
  }

  @Override
  public GetRicercaAttiResponseGenericResponse getRicercaAtti() {
    return instance.getRicercaAtti();
  }

  @Override
  public GetScaricoCertificatoAttiResponseGenericResponse getScaricoCertificatoAtti(
      String var1, String var2, String var3) {
    return instance.getScaricoCertificatoAtti(var1, var2, var3);
  }

  @Override
  public GetTipoConsegnaResponseGenericResponse getTipoConsegna2() {
    return instance.getTipoConsegna2();
  }

  //	@Override
  //	public GetSelezioneAttoResponseGenericResponse getSelezioneAtto(String arg0, String arg1,
  // OffsetDateTime arg2,
  //			String arg3) {
  //		return instance.getSelezioneAtto(arg0, arg1, arg2, arg3);
  //	}

  @Override
  public GetSelezioneAttoResponseGenericResponse getSelezioneAtto(
      String arg0, String arg1, LocalDate arg2, String arg3) {
    return instance.getSelezioneAtto(arg0, arg1, arg2, arg3);
  }

  @Override
  public PostRichiestaEmissioneCertificatoSCResponseGenericResponse
      postRichiestaEmissioneCertificatoAtto(PostRichiestaEmissioneCertificatoAttoRequest arg0) {
    return instance.postRichiestaEmissioneCertificatoAtto(arg0);
  }
}
