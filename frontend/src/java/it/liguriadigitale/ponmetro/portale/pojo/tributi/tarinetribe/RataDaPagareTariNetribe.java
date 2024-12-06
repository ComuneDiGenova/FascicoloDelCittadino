package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import java.io.Serializable;

public class RataDaPagareTariNetribe implements Serializable {

  private static final long serialVersionUID = -115584987627171251L;

  private String codiceFiscale;

  private String iuv;

  private Double importo;

  private String idServizio;

  private EsitoPagamento esitoPagamento;

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public EsitoPagamento getEsitoPagamento() {
    return esitoPagamento;
  }

  public void setEsitoPagamento(EsitoPagamento esitoPagamento) {
    this.esitoPagamento = esitoPagamento;
  }

  @Override
  public String toString() {
    return "RataDaPagareTariNetribe [codiceFiscale="
        + codiceFiscale
        + ", iuv="
        + iuv
        + ", importo="
        + importo
        + ", idServizio="
        + idServizio
        + ", esitoPagamento="
        + esitoPagamento
        + "]";
  }
}
