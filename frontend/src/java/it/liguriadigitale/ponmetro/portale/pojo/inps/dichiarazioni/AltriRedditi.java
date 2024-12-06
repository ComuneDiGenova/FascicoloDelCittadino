package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class AltriRedditi implements Serializable {

  private static final long serialVersionUID = 5168921356554966611L;

  private Boolean FlagEsonerato;
  private Boolean FlagIntegrazione;
  private Boolean FlagRettifica;
  private BigDecimal RedditoIRPEF;
  private BigDecimal LavoroDipendente;
  private BigDecimal RedditiImpSost;

  public Boolean getFlagEsonerato() {
    return FlagEsonerato;
  }

  public void setFlagEsonerato(Boolean flagEsonerato) {
    FlagEsonerato = flagEsonerato;
  }

  public Boolean getFlagIntegrazione() {
    return FlagIntegrazione;
  }

  public void setFlagIntegrazione(Boolean flagIntegrazione) {
    FlagIntegrazione = flagIntegrazione;
  }

  public Boolean getFlagRettifica() {
    return FlagRettifica;
  }

  public void setFlagRettifica(Boolean flagRettifica) {
    FlagRettifica = flagRettifica;
  }

  public BigDecimal getRedditoIRPEF() {
    return RedditoIRPEF;
  }

  public void setRedditoIRPEF(BigDecimal redditoIRPEF) {
    RedditoIRPEF = redditoIRPEF;
  }

  public BigDecimal getLavoroDipendente() {
    return LavoroDipendente;
  }

  public void setLavoroDipendente(BigDecimal lavoroDipendente) {
    LavoroDipendente = lavoroDipendente;
  }

  public BigDecimal getRedditiImpSost() {
    return RedditiImpSost;
  }

  public void setRedditiImpSost(BigDecimal redditiImpSost) {
    RedditiImpSost = redditiImpSost;
  }

  @Override
  public String toString() {
    return "AltriRedditi [FlagEsonerato="
        + FlagEsonerato
        + ", FlagIntegrazione="
        + FlagIntegrazione
        + ", FlagRettifica="
        + FlagRettifica
        + ", RedditoIRPEF="
        + RedditoIRPEF
        + ", LavoroDipendente="
        + LavoroDipendente
        + ", RedditiImpSost="
        + RedditiImpSost
        + "]";
  }
}
