package it.liguriadigitale.ponmetro.portale.pojo.edilizia.abitabilita;

import java.io.Serializable;

public class Abitabilita implements Serializable {

  private static final long serialVersionUID = -8143233804547298470L;

  private String via;

  private Integer civicoDa;

  private Integer civicoA;

  private String letteraInterno;

  private String colore;

  private Integer numeroCartellinoDecreto;

  private Integer annoDecreto;

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public Integer getCivicoDa() {
    return civicoDa;
  }

  public void setCivicoDa(Integer civicoDa) {
    this.civicoDa = civicoDa;
  }

  public Integer getCivicoA() {
    return civicoA;
  }

  public void setCivicoA(Integer civicoA) {
    this.civicoA = civicoA;
  }

  public String getLetteraInterno() {
    return letteraInterno;
  }

  public void setLetteraInterno(String letteraInterno) {
    this.letteraInterno = letteraInterno;
  }

  public String getColore() {
    return colore;
  }

  public void setColore(String colore) {
    this.colore = colore;
  }

  public Integer getNumeroCartellinoDecreto() {
    return numeroCartellinoDecreto;
  }

  public void setNumeroCartellinoDecreto(Integer numeroCartellinoDecreto) {
    this.numeroCartellinoDecreto = numeroCartellinoDecreto;
  }

  public Integer getAnnoDecreto() {
    return annoDecreto;
  }

  public void setAnnoDecreto(Integer annoDecreto) {
    this.annoDecreto = annoDecreto;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Abitabilita [via=");
    builder.append(via);
    builder.append(", civicoDa=");
    builder.append(civicoDa);
    builder.append(", civicoA=");
    builder.append(civicoA);
    builder.append(", letteraInterno=");
    builder.append(letteraInterno);
    builder.append(", colore=");
    builder.append(colore);
    builder.append(", numeroCartellinoDecreto=");
    builder.append(numeroCartellinoDecreto);
    builder.append(", annoDecreto=");
    builder.append(annoDecreto);
    builder.append("]");
    return builder.toString();
  }
}
