package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;

public class CategoriaCatastale implements Serializable {

  private static final long serialVersionUID = -1987987546489898L;

  private String codice;

  private String descrizione;

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
}
