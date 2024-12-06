package it.liguriadigitale.ponmetro.portale.pojo.mipDebito;

import java.io.Serializable;
import java.util.List;

public class DebitoMipFascicolo implements Serializable {

  private static final long serialVersionUID = -1691471749407285432L;

  private String debitore;

  private String servizio;

  private Long anno;

  private String numeroDebito;

  private String causale;

  private List<TentativoDiPagamento> tentativiDiPagamento;

  private Double importoTotaleDaPagare;

  private Double importoTotalePagato;

  private boolean statoDebito;

  private int index;

  private String iuv;

  public String getDebitore() {
    return debitore;
  }

  public void setDebitore(String debitore) {
    this.debitore = debitore;
  }

  public String getServizio() {
    return servizio;
  }

  public void setServizio(String servizio) {
    this.servizio = servizio;
  }

  public Long getAnno() {
    return anno;
  }

  public void setAnno(Long anno) {
    this.anno = anno;
  }

  public String getNumeroDebito() {
    return numeroDebito;
  }

  public void setNumeroDebito(String numeroDebito) {
    this.numeroDebito = numeroDebito;
  }

  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public List<TentativoDiPagamento> getTentativiDiPagamento() {
    return tentativiDiPagamento;
  }

  public void setTentativiDiPagamento(List<TentativoDiPagamento> tentativiDiPagamento) {
    this.tentativiDiPagamento = tentativiDiPagamento;
  }

  public Double getImportoTotaleDaPagare() {
    return importoTotaleDaPagare;
  }

  public void setImportoTotaleDaPagare(Double importoTotaleDaPagare) {
    this.importoTotaleDaPagare = importoTotaleDaPagare;
  }

  public Double getImportoTotalePagato() {
    return importoTotalePagato;
  }

  public void setImportoTotalePagato(Double importoTotalePagato) {
    this.importoTotalePagato = importoTotalePagato;
  }

  public boolean isStatoDebito() {
    return statoDebito;
  }

  public void setStatoDebito(boolean statoDebito) {
    this.statoDebito = statoDebito;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  @Override
  public String toString() {
    return "DebitoMipFascicolo [debitore="
        + debitore
        + ", servizio="
        + servizio
        + ", anno="
        + anno
        + ", numeroDebito="
        + numeroDebito
        + ", causale="
        + causale
        + ", tentativiDiPagamento="
        + tentativiDiPagamento
        + ", importoTotaleDaPagare="
        + importoTotaleDaPagare
        + ", importoTotalePagato="
        + importoTotalePagato
        + ", statoDebito="
        + statoDebito
        + ", index="
        + index
        + ", iuv="
        + iuv
        + "]";
  }
}
