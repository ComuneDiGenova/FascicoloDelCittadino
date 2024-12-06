package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsoResult;
import java.io.Serializable;
import java.util.List;

public class DatiRichiestaRimborsoTariNetribeResult implements Serializable {

  private boolean esitoOk;

  private List<TARIRimborsoResult> risultatiRimborsi;

  private String messaggioKO;

  public boolean isEsitoOk() {
    return esitoOk;
  }

  public void setEsitoOk(boolean esitoOk) {
    this.esitoOk = esitoOk;
  }

  public List<TARIRimborsoResult> getRisultatiRimborsi() {
    return risultatiRimborsi;
  }

  public void setRisultatiRimborsi(List<TARIRimborsoResult> risultatiRimborsi) {
    this.risultatiRimborsi = risultatiRimborsi;
  }

  public String getMessaggioKO() {
    return messaggioKO;
  }

  public void setMessaggioKO(String messaggioKO) {
    this.messaggioKO = messaggioKO;
  }

  @Override
  public String toString() {
    return "DatiRichiestaRimborsoTariNetribeResult [esitoOk="
        + esitoOk
        + ", risultatiRimborsi="
        + risultatiRimborsi
        + ", messaggioKO="
        + messaggioKO
        + "]";
  }
}
