package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class DichiarazioniISEE implements Serializable {

  private static final long serialVersionUID = -3959063258918678832L;

  private NucleoFamiliare NucleoFamiliare;

  public NucleoFamiliare getNucleoFamiliare() {
    return NucleoFamiliare;
  }

  public void setNucleoFamiliare(NucleoFamiliare nucleoFamiliare) {
    NucleoFamiliare = nucleoFamiliare;
  }

  @Override
  public String toString() {
    return "DichiarazioniISEE [NucleoFamiliare=" + NucleoFamiliare + "]";
  }
}
