package it.liguriadigitale.ponmetro.portale.pojo.mipDebito;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class TentativoDiPagamento implements Serializable {

  private static final long serialVersionUID = 2247549388206990968L;

  private String servizio;

  private String numeroDocumento;

  private String rata;

  private String iuv;

  private String codiceAvviso;

  private boolean esitoPagamento;

  private Double importoDaPagare;

  private Double importoPagato;

  private OffsetDateTime dataCreazione;

  private OffsetDateTime dataPagamento;

  private String attualizzato;

  private String nomeRicevuta;

  private byte[] pdfRicevuta;

  private Long anno;

  public String getServizio() {
    return servizio;
  }

  public void setServizio(String servizio) {
    this.servizio = servizio;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getRata() {
    return rata;
  }

  public void setRata(String rata) {
    this.rata = rata;
  }

  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public String getCodiceAvviso() {
    return codiceAvviso;
  }

  public void setCodiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
  }

  public boolean isEsitoPagamento() {
    return esitoPagamento;
  }

  public void setEsitoPagamento(boolean esitoPagamento) {
    this.esitoPagamento = esitoPagamento;
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

  public OffsetDateTime getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(OffsetDateTime dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public OffsetDateTime getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(OffsetDateTime dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public String getAttualizzato() {
    return attualizzato;
  }

  public void setAttualizzato(String attualizzato) {
    this.attualizzato = attualizzato;
  }

  public String getNomeRicevuta() {
    return nomeRicevuta;
  }

  public void setNomeRicevuta(String nomeRicevuta) {
    this.nomeRicevuta = nomeRicevuta;
  }

  public byte[] getPdfRicevuta() {
    return pdfRicevuta;
  }

  public void setPdfRicevuta(byte[] pdfRicevuta) {
    this.pdfRicevuta = pdfRicevuta;
  }

  public Long getAnno() {
    return anno;
  }

  public void setAnno(Long anno) {
    this.anno = anno;
  }

  @Override
  public String toString() {
    return "TentativoDiPagamento [servizio="
        + servizio
        + ", numeroDocumento="
        + numeroDocumento
        + ", rata="
        + rata
        + ", iuv="
        + iuv
        + ", codiceAvviso="
        + codiceAvviso
        + ", esitoPagamento="
        + esitoPagamento
        + ", importoDaPagare="
        + importoDaPagare
        + ", importoPagato="
        + importoPagato
        + ", dataCreazione="
        + dataCreazione
        + ", dataPagamento="
        + dataPagamento
        + ", attualizzato="
        + attualizzato
        + ", nomeRicevuta="
        + nomeRicevuta
        + ", pdfRicevuta="
        + Arrays.toString(pdfRicevuta)
        + ", anno="
        + anno
        + "]";
  }
}
