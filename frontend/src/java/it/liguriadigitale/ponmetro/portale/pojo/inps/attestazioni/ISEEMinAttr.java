package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;

public class ISEEMinAttr implements Serializable {

  private static final long serialVersionUID = 9010198759112074796L;

  private Valori Valori;

  public Valori getValori() {
    return Valori;
  }

  public void setValori(Valori valori) {
    Valori = valori;
  }

  @Override
  public String toString() {
    return "ISEEMinAttr [Valori=" + Valori + "]";
  }
}
