package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.util.List;

public class Veicoli implements Serializable {

  private static final long serialVersionUID = -5439035309924337783L;

  private List<Veicolo> Veicolo;

  public List<Veicolo> getVeicolo() {
    return Veicolo;
  }

  public void setVeicolo(List<Veicolo> veicolo) {
    Veicolo = veicolo;
  }

  @Override
  public String toString() {
    return "Veicoli [Veicolo=" + Veicolo + "]";
  }
}
