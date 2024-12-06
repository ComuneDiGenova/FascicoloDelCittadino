package it.liguriadigitale.ponmetro.portale.pojo.amt;

import it.liguriadigitale.ponmetro.abbonamentiamt.model.Sanzione;

public class SanzioniAmtEsteso extends Sanzione {

  private static final long serialVersionUID = 3814868256376306795L;

  private String nominativo;

  private String cf;

  public SanzioniAmtEsteso() {
    super();
  }

  public SanzioniAmtEsteso(String taxCode, String nominativo) {
    setCf(taxCode);
    setNominativo(nominativo);
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

  @Override
  public String toString() {
    return "SanzioniAmtEsteso [nominativo=" + nominativo + ", cf=" + cf + "]";
  }
}
