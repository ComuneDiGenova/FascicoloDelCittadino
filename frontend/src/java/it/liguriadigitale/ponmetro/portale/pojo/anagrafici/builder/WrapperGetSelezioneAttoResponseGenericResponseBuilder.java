package it.liguriadigitale.ponmetro.portale.pojo.anagrafici.builder;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.WrapperGetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetSelezioneAttoResponseGenericResponse;

public class WrapperGetSelezioneAttoResponseGenericResponseBuilder {

  private GetSelezioneAttoResponseGenericResponse response;
  private boolean result;

  public static WrapperGetSelezioneAttoResponseGenericResponseBuilder getInstance() {
    return new WrapperGetSelezioneAttoResponseGenericResponseBuilder();
  }

  public WrapperGetSelezioneAttoResponseGenericResponseBuilder addResponse(
      GetSelezioneAttoResponseGenericResponse response) {
    this.response = response;
    result = checkCertificabilita(response);
    return this;
  }

  public WrapperGetSelezioneAttoResponseGenericResponseBuilder addResult(boolean result) {
    this.result = result;
    return this;
  }

  public WrapperGetSelezioneAttoResponseGenericResponse build() {

    WrapperGetSelezioneAttoResponseGenericResponse wrapper =
        new WrapperGetSelezioneAttoResponseGenericResponse();
    wrapper.setResponse(response);
    wrapper.setResult(result);
    return wrapper;
  }

  private boolean checkCertificabilita(GetSelezioneAttoResponseGenericResponse response) {
    if ("KO".equalsIgnoreCase(response.getStatus())) {
      return false;
    } else {
      return true;
    }
  }
}
