package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class Valori implements Serializable {

  private static final long serialVersionUID = 4729132059794995949L;

  private BigDecimal ISEE;
  private BigDecimal ScalaEquivalenza;
  private BigDecimal ISE;
  private BigDecimal ISR;
  private BigDecimal ISP;

  public BigDecimal getISEE() {
    return ISEE;
  }

  public void setISEE(BigDecimal iSEE) {
    ISEE = iSEE;
  }

  public BigDecimal getScalaEquivalenza() {
    return ScalaEquivalenza;
  }

  public void setScalaEquivalenza(BigDecimal scalaEquivalenza) {
    ScalaEquivalenza = scalaEquivalenza;
  }

  public BigDecimal getISE() {
    return ISE;
  }

  public void setISE(BigDecimal iSE) {
    ISE = iSE;
  }

  public BigDecimal getISR() {
    return ISR;
  }

  public void setISR(BigDecimal iSR) {
    ISR = iSR;
  }

  public BigDecimal getISP() {
    return ISP;
  }

  public void setISP(BigDecimal iSP) {
    ISP = iSP;
  }

  @Override
  public String toString() {
    return "Valori [ISEE="
        + ISEE
        + ", ScalaEquivalenza="
        + ScalaEquivalenza
        + ", ISE="
        + ISE
        + ", ISR="
        + ISR
        + ", ISP="
        + ISP
        + "]";
  }
}
