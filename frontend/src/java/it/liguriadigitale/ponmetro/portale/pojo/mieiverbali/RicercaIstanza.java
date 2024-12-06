package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import java.io.Serializable;

public class RicercaIstanza implements Serializable {

  private static final long serialVersionUID = -3834245310514393509L;

  private String numeroProtocollo;

  private String numeroIstanza;

  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public String getNumeroIstanza() {
    return numeroIstanza;
  }

  public void setNumeroIstanza(String numeroIstanza) {
    this.numeroIstanza = numeroIstanza;
  }

  @Override
  public String toString() {
    return "RicercaIstanza [numeroProtocollo="
        + numeroProtocollo
        + ", numeroIstanza="
        + numeroIstanza
        + "]";
  }
}
