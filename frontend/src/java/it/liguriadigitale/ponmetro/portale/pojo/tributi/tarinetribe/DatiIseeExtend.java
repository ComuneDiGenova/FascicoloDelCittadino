package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIsee;

public class DatiIseeExtend extends DatiIsee {

  private static final long serialVersionUID = 4042705285693323011L;

  private String nominativo;

  private boolean iseePresentato;

  private Double importoDouble;

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public boolean isIseePresentato() {
    return iseePresentato;
  }

  public void setIseePresentato(boolean iseePresentato) {
    this.iseePresentato = iseePresentato;
  }

  public Double getImportoDouble() {
    return importoDouble;
  }

  public void setImportoDouble(Double importoDouble) {
    this.importoDouble = importoDouble;
  }

  @Override
  public String toString() {
    return "DatiIseeExtend [nominativo="
        + nominativo
        + ", iseePresentato="
        + iseePresentato
        + ", importoDouble="
        + importoDouble
        + "]";
  }
}
