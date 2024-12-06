package it.liguriadigitale.ponmetro.portale.pojo.mipDebito;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class DebitoMipFascicoloDatiGenerali implements Serializable {

  private static final long serialVersionUID = 8934423843544723496L;

  private String servizio;

  private String numeroDebito;

  private Long anno;

  private String debitore;

  private String causale;

  private boolean esitoDebito;

  private List<RataDebitoMipFascicolo> listaRateDebito;

  private List<Boolean> esitiRate;

  private OffsetDateTime dataCreazione;

  private Double importoDaPagare;

  private Double importoPagato;

  private String iuv;

  public String getServizio() {
    return servizio;
  }

  public void setServizio(String servizio) {
    this.servizio = servizio;
  }

  public String getNumeroDebito() {
    return numeroDebito;
  }

  public void setNumeroDebito(String numeroDebito) {
    this.numeroDebito = numeroDebito;
  }

  public Long getAnno() {
    return anno;
  }

  public void setAnno(Long anno) {
    this.anno = anno;
  }

  public String getDebitore() {
    return debitore;
  }

  public void setDebitore(String debitore) {
    this.debitore = debitore;
  }

  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public boolean isEsitoDebito() {
    return esitoDebito;
  }

  public void setEsitoDebito(boolean esitoDebito) {
    this.esitoDebito = esitoDebito;
  }

  public List<RataDebitoMipFascicolo> getListaRateDebito() {
    return listaRateDebito;
  }

  public void setListaRateDebito(List<RataDebitoMipFascicolo> listaRateDebito) {
    this.listaRateDebito = listaRateDebito;
  }

  public List<Boolean> getEsitiRate() {
    return esitiRate;
  }

  public void setEsitiRate(List<Boolean> esitiRate) {
    this.esitiRate = esitiRate;
  }

  public OffsetDateTime getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(OffsetDateTime dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public Double getImportoDaPagare() {
    return importoDaPagare;
  }

  public void setImportoDaPagare(Double importoDaPagare) {
    this.importoDaPagare = importoDaPagare;
  }

  public Double getImportoPagato() {
    return importoPagato;
  }

  public void setImportoPagato(Double importoPagato) {
    this.importoPagato = importoPagato;
  }

  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  @Override
  public String toString() {
    return "DebitoMipFascicoloDatiGenerali [servizio="
        + servizio
        + ", numeroDebito="
        + numeroDebito
        + ", anno="
        + anno
        + ", debitore="
        + debitore
        + ", causale="
        + causale
        + ", esitoDebito="
        + esitoDebito
        + ", listaRateDebito="
        + listaRateDebito
        + ", esitiRate="
        + esitiRate
        + ", dataCreazione="
        + dataCreazione
        + ", importoDaPagare="
        + importoDaPagare
        + ", importoPagato="
        + importoPagato
        + ", iuv="
        + iuv
        + "]";
  }
}
