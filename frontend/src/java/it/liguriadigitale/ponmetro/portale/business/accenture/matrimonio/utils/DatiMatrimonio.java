package it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class DatiMatrimonio implements Serializable {

  private static final long serialVersionUID = 484228343728009956L;

  private String name;

  private String statoPraticaC;

  private String dichiarante;

  private String dichiaranteCel;

  private String coniuge;

  private String coniugeCel;

  private OffsetDateTime createdDate;

  private OffsetDateTime lastModifiedDate;

  private String comuneC;

  private String provinciaC;

  private OffsetDateTime dataAppuntamentoC;

  private LocalDate dataPresuntaC;

  private String numeroProtocolloC;

  private String ritoC;

  private String uRLPubblicoPdfC;

  private String nomeDichiarante;

  private String cognomeDichiarante;

  private String nominativoDichiarante;

  private String nomeConiuge;

  private String cognomeConiuge;

  private String nominativoConiuge;

  private String cellulareDichiarante;

  private String cellulareConiuge;

  private String emailDichiarante;

  private String emailConiuge;

  private String tipologiaRichiesta;

  private String DataSecondoAppuntamentoC;

  private String DataSecondoAppuntamentoFormulaC;

  private String DataSecondoAppuntamentoTextC;

  private String OraSecondoAppuntamentoFormulaC;

  private String OraSecondoAppuntamentoTextC;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatoPraticaC() {
    return statoPraticaC;
  }

  public void setStatoPraticaC(String statoPraticaC) {
    this.statoPraticaC = statoPraticaC;
  }

  public String getDichiarante() {
    return dichiarante;
  }

  public void setDichiarante(String dichiarante) {
    this.dichiarante = dichiarante;
  }

  public String getDichiaranteCel() {
    return dichiaranteCel;
  }

  public void setDichiaranteCel(String dichiaranteCel) {
    this.dichiaranteCel = dichiaranteCel;
  }

  public String getConiuge() {
    return coniuge;
  }

  public void setConiuge(String coniuge) {
    this.coniuge = coniuge;
  }

  public String getConiugeCel() {
    return coniugeCel;
  }

  public void setConiugeCel(String coniugeCel) {
    this.coniugeCel = coniugeCel;
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public OffsetDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getComuneC() {
    return comuneC;
  }

  public void setComuneC(String comuneC) {
    this.comuneC = comuneC;
  }

  public String getProvinciaC() {
    return provinciaC;
  }

  public void setProvinciaC(String provinciaC) {
    this.provinciaC = provinciaC;
  }

  public OffsetDateTime getDataAppuntamentoC() {
    return dataAppuntamentoC;
  }

  public void setDataAppuntamentoC(OffsetDateTime dataAppuntamentoC) {
    this.dataAppuntamentoC = dataAppuntamentoC;
  }

  public LocalDate getDataPresuntaC() {
    return dataPresuntaC;
  }

  public void setDataPresuntaC(LocalDate dataPresuntaC) {
    this.dataPresuntaC = dataPresuntaC;
  }

  public String getNumeroProtocolloC() {
    return numeroProtocolloC;
  }

  public void setNumeroProtocolloC(String numeroProtocolloC) {
    this.numeroProtocolloC = numeroProtocolloC;
  }

  public String getRitoC() {
    return ritoC;
  }

  public void setRitoC(String ritoC) {
    this.ritoC = ritoC;
  }

  public String getuRLPubblicoPdfC() {
    return uRLPubblicoPdfC;
  }

  public void setuRLPubblicoPdfC(String uRLPubblicoPdfC) {
    this.uRLPubblicoPdfC = uRLPubblicoPdfC;
  }

  public String getCellulareDichiarante() {
    return cellulareDichiarante;
  }

  public void setCellulareDichiarante(String cellulareDichiarante) {
    this.cellulareDichiarante = cellulareDichiarante;
  }

  public String getCellulareConiuge() {
    return cellulareConiuge;
  }

  public void setCellulareConiuge(String cellulareConiuge) {
    this.cellulareConiuge = cellulareConiuge;
  }

  public String getEmailDichiarante() {
    return emailDichiarante;
  }

  public void setEmailDichiarante(String emailDichiarante) {
    this.emailDichiarante = emailDichiarante;
  }

  public String getEmailConiuge() {
    return emailConiuge;
  }

  public void setEmailConiuge(String emailConiuge) {
    this.emailConiuge = emailConiuge;
  }

  public String getNomeDichiarante() {
    return nomeDichiarante;
  }

  public void setNomeDichiarante(String nomeDichiarante) {
    this.nomeDichiarante = nomeDichiarante;
  }

  public String getCognomeDichiarante() {
    return cognomeDichiarante;
  }

  public void setCognomeDichiarante(String cognomeDichiarante) {
    this.cognomeDichiarante = cognomeDichiarante;
  }

  public String getNomeConiuge() {
    return nomeConiuge;
  }

  public void setNomeConiuge(String nomeConiuge) {
    this.nomeConiuge = nomeConiuge;
  }

  public String getCognomeConiuge() {
    return cognomeConiuge;
  }

  public void setCognomeConiuge(String cognomeConiuge) {
    this.cognomeConiuge = cognomeConiuge;
  }

  public String getNominativoDichiarante() {
    return nominativoDichiarante;
  }

  public void setNominativoDichiarante(String nominativoDichiarante) {
    this.nominativoDichiarante = nominativoDichiarante;
  }

  public String getNominativoConiuge() {
    return nominativoConiuge;
  }

  public void setNominativoConiuge(String nominativoConiuge) {
    this.nominativoConiuge = nominativoConiuge;
  }

  public String getTipologiaRichiesta() {
    return tipologiaRichiesta;
  }

  public void setTipologiaRichiesta(String tipologiaRichiesta) {
    this.tipologiaRichiesta = tipologiaRichiesta;
  }

  public String getDataSecondoAppuntamentoC() {
    return DataSecondoAppuntamentoC;
  }

  public void setDataSecondoAppuntamentoC(String dataSecondoAppuntamentoC) {
    DataSecondoAppuntamentoC = dataSecondoAppuntamentoC;
  }

  public String getDataSecondoAppuntamentoFormulaC() {
    return DataSecondoAppuntamentoFormulaC;
  }

  public void setDataSecondoAppuntamentoFormulaC(String dataSecondoAppuntamentoFormulaC) {
    DataSecondoAppuntamentoFormulaC = dataSecondoAppuntamentoFormulaC;
  }

  public String getDataSecondoAppuntamentoTextC() {
    return DataSecondoAppuntamentoTextC;
  }

  public void setDataSecondoAppuntamentoTextC(String dataSecondoAppuntamentoTextC) {
    DataSecondoAppuntamentoTextC = dataSecondoAppuntamentoTextC;
  }

  public String getOraSecondoAppuntamentoFormulaC() {
    return OraSecondoAppuntamentoFormulaC;
  }

  public void setOraSecondoAppuntamentoFormulaC(String oraSecondoAppuntamentoFormulaC) {
    OraSecondoAppuntamentoFormulaC = oraSecondoAppuntamentoFormulaC;
  }

  public String getOraSecondoAppuntamentoTextC() {
    return OraSecondoAppuntamentoTextC;
  }

  public void setOraSecondoAppuntamentoTextC(String oraSecondoAppuntamentoTextC) {
    OraSecondoAppuntamentoTextC = oraSecondoAppuntamentoTextC;
  }

  @Override
  public String toString() {
    return "DatiMatrimonio [name="
        + name
        + ", statoPraticaC="
        + statoPraticaC
        + ", dichiarante="
        + dichiarante
        + ", dichiaranteCel="
        + dichiaranteCel
        + ", coniuge="
        + coniuge
        + ", coniugeCel="
        + coniugeCel
        + ", createdDate="
        + createdDate
        + ", lastModifiedDate="
        + lastModifiedDate
        + ", comuneC="
        + comuneC
        + ", provinciaC="
        + provinciaC
        + ", dataAppuntamentoC="
        + dataAppuntamentoC
        + ", dataPresuntaC="
        + dataPresuntaC
        + ", numeroProtocolloC="
        + numeroProtocolloC
        + ", ritoC="
        + ritoC
        + ", uRLPubblicoPdfC="
        + uRLPubblicoPdfC
        + ", nomeDichiarante="
        + nomeDichiarante
        + ", cognomeDichiarante="
        + cognomeDichiarante
        + ", nominativoDichiarante="
        + nominativoDichiarante
        + ", nomeConiuge="
        + nomeConiuge
        + ", cognomeConiuge="
        + cognomeConiuge
        + ", nominativoConiuge="
        + nominativoConiuge
        + ", cellulareDichiarante="
        + cellulareDichiarante
        + ", cellulareConiuge="
        + cellulareConiuge
        + ", emailDichiarante="
        + emailDichiarante
        + ", emailConiuge="
        + emailConiuge
        + ", tipologiaRichiesta="
        + tipologiaRichiesta
        + ", DataSecondoAppuntamentoC="
        + DataSecondoAppuntamentoC
        + ", DataSecondoAppuntamentoFormulaC="
        + DataSecondoAppuntamentoFormulaC
        + ", DataSecondoAppuntamentoTextC="
        + DataSecondoAppuntamentoTextC
        + ", OraSecondoAppuntamentoTextC="
        + OraSecondoAppuntamentoTextC
        + ", OraSecondoAppuntamentoFormulaC="
        + OraSecondoAppuntamentoFormulaC
        + "]";
  }
}
