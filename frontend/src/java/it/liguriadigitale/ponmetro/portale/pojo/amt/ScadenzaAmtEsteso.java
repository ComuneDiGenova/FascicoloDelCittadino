package it.liguriadigitale.ponmetro.portale.pojo.amt;

import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenza;

public class ScadenzaAmtEsteso extends Scadenza {

  private static final long serialVersionUID = -3525022828377968310L;

  private String nominativo;

  private String cf;

  public ScadenzaAmtEsteso() {
    super();
  }

  @Override
  public String toString() {
    return "ScadenzaAmtEsteso [nominativo=" + nominativo + ", cf=" + cf + "]";
  }

  public ScadenzaAmtEsteso(String nominativo, String cf) {
    super();
    this.nominativo = nominativo;
    this.cf = cf;
  }

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }
}
