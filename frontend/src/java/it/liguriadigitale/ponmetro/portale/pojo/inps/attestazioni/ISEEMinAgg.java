package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;

public class ISEEMinAgg implements Serializable {

  private static final long serialVersionUID = 9106349516524299438L;

  private Valori Valori;

  public Valori getValori() {
    return Valori;
  }

  public void setValori(Valori valori) {
    Valori = valori;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ISEEMinAgg [Valori=");
    builder.append(Valori);
    builder.append("]");
    return builder.toString();
  }
}
