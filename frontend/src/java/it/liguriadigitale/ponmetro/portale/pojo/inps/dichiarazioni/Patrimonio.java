package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class Patrimonio implements Serializable {

  private static final long serialVersionUID = 9022471112935129786L;

  private String TipoPatrimonio;
  private String CodiceComuneStato;
  private Integer QuotaPosseduta;
  private Double ValoreImu;
  private Boolean FlagAbitazione;
  private BigDecimal MutuoResiduo;

  public String getTipoPatrimonio() {
    return TipoPatrimonio;
  }

  public void setTipoPatrimonio(String tipoPatrimonio) {
    TipoPatrimonio = tipoPatrimonio;
  }

  public String getCodiceComuneStato() {
    return CodiceComuneStato;
  }

  public void setCodiceComuneStato(String codiceComuneStato) {
    CodiceComuneStato = codiceComuneStato;
  }

  public Integer getQuotaPosseduta() {
    return QuotaPosseduta;
  }

  public void setQuotaPosseduta(Integer quotaPosseduta) {
    QuotaPosseduta = quotaPosseduta;
  }

  public Double getValoreImu() {
    return ValoreImu;
  }

  public void setValoreImu(Double valoreImu) {
    ValoreImu = valoreImu;
  }

  public Boolean getFlagAbitazione() {
    return FlagAbitazione;
  }

  public void setFlagAbitazione(Boolean flagAbitazione) {
    FlagAbitazione = flagAbitazione;
  }

  @Override
  public String toString() {
    return "Patrimonio [TipoPatrimonio="
        + TipoPatrimonio
        + ", CodiceComuneStato="
        + CodiceComuneStato
        + ", QuotaPosseduta="
        + QuotaPosseduta
        + ", ValoreImu="
        + ValoreImu
        + ", FlagAbitazione="
        + FlagAbitazione
        + "]";
  }

  public BigDecimal getMutuoResiduo() {
    return MutuoResiduo;
  }

  public void setMutuoResiduo(BigDecimal mutuoResiduo) {
    MutuoResiduo = mutuoResiduo;
  }
}
