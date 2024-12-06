package it.liguriadigitale.ponmetro.portale.pojo.portale;

import java.io.Serializable;

public class PagamentoMensaSintetico implements Serializable {

  private static final long serialVersionUID = 7086980502746542362L;
  private String annoScolastico;
  private Long annoScolasticoPrimoNumero;
  private String codiceFiscaleImpegnato;
  private String situazionePagamento;
  private String metodoPagamento;

  public String getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  public Long getAnnoScolasticoPrimoNumero() {
    return annoScolasticoPrimoNumero;
  }

  public void setAnnoScolasticoPrimoNumero(Long annoScolasticoPrimoNumero) {
    this.annoScolasticoPrimoNumero = annoScolasticoPrimoNumero;
  }

  public String getCodiceFiscaleImpegnato() {
    return codiceFiscaleImpegnato;
  }

  public void setCodiceFiscaleImpegnato(String codiceFiscaleImpegnato) {
    this.codiceFiscaleImpegnato = codiceFiscaleImpegnato;
  }

  public String getSituazionePagamento() {
    return situazionePagamento;
  }

  public void setSituazionePagamento(String situazionePagamento) {
    this.situazionePagamento = situazionePagamento;
  }

  public String getMetodoPagamento() {
    return metodoPagamento;
  }

  public void setMetodoPagamento(String metodoPagamento) {
    this.metodoPagamento = metodoPagamento;
  }
}
