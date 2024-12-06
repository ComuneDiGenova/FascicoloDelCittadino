package it.liguriadigitale.ponmetro.portale.presentation.components.legenda;

import java.io.Serializable;

public class Legenda implements Serializable {

  private static final long serialVersionUID = 4047310845605901283L;

  private String testo;

  private String stile;

  public String getTesto() {
    return testo;
  }

  public void setTesto(String testo) {
    this.testo = testo;
  }

  public String getStile() {
    return stile;
  }

  public void setStile(String stile) {
    this.stile = stile;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Legenda [testo=");
    builder.append(testo);
    builder.append(", stile=");
    builder.append(stile);
    builder.append("]");
    return builder.toString();
  }
}
