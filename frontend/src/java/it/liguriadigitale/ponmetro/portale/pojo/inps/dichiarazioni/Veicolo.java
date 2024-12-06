package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class Veicolo implements Serializable {

  private static final long serialVersionUID = -5439035309924337783L;

  private String TipoVeicolo;
  private String Targa;

  public String getTipoVeicolo() {
    return TipoVeicolo;
  }

  public void setTipoVeicolo(String tipoVeicolo) {
    TipoVeicolo = tipoVeicolo;
  }

  public String getTarga() {
    return Targa;
  }

  public void setTarga(String targa) {
    Targa = targa;
  }

  @Override
  public String toString() {
    return "Veicolo [TipoVeicolo=" + TipoVeicolo + ", Targa=" + Targa + "]";
  }
}
