package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

import java.io.Serializable;

public class DatiCompletiRegistrazioneUtenteAllerteCortesia implements Serializable {

  private static final long serialVersionUID = 7673747208694886035L;

  DatiRegistrazioneAllerteCortesia datiRegistrazioneAllerteCortesia;

  public DatiRegistrazioneAllerteCortesia getDatiRegistrazioneAllerteCortesia() {
    return datiRegistrazioneAllerteCortesia;
  }

  public void setDatiRegistrazioneAllerteCortesia(
      DatiRegistrazioneAllerteCortesia datiRegistrazioneAllerteCortesia) {
    this.datiRegistrazioneAllerteCortesia = datiRegistrazioneAllerteCortesia;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(
        "DatiCompletiRegistrazioneUtenteAllerteCortesia [datiRegistrazioneAllerteCortesia=");
    builder.append(datiRegistrazioneAllerteCortesia);
    builder.append("]");
    return builder.toString();
  }
}
