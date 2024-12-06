package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.model.GetSelezioneAttoResponseGenericResponse;
import java.io.Serializable;

public class WrapperGetSelezioneAttoResponseGenericResponse implements Serializable {

  private static final long serialVersionUID = -5226835428473236896L;
  private GetSelezioneAttoResponseGenericResponse response;
  private boolean result;

  public GetSelezioneAttoResponseGenericResponse getResponse() {
    return response;
  }

  public void setResponse(GetSelezioneAttoResponseGenericResponse response) {
    this.response = response;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "WrapperGetSelezioneAttoResponseGenericResponse [response="
        + response
        + ", result="
        + result
        + "]";
  }
}
