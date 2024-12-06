package it.liguriadigitale.ponmetro.portale.pojo.mipDebito;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class RataDebitoMipFascicolo implements Serializable {

  private static final long serialVersionUID = -6780777886274485638L;

  private String servizio;

  private String numeroDebito;

  private String numeroRata;

  private String iuv;

  private String codiceAvviso;

  private boolean esitoRata;

  private OffsetDateTime dataCreazioneRata;

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

  public String getNumeroRata() {
    return numeroRata;
  }

  public void setNumeroRata(String numeroRata) {
    this.numeroRata = numeroRata;
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

  public boolean isEsitoRata() {
    return esitoRata;
  }

  public void setEsitoRata(boolean esitoRata) {
    this.esitoRata = esitoRata;
  }

  public OffsetDateTime getDataCreazioneRata() {
    return dataCreazioneRata;
  }

  public void setDataCreazioneRata(OffsetDateTime dataCreazioneRata) {
    this.dataCreazioneRata = dataCreazioneRata;
  }

  @Override
  public String toString() {
    return "RataDebitoMipFascicolo [servizio="
        + servizio
        + ", numeroDebito="
        + numeroDebito
        + ", numeroRata="
        + numeroRata
        + ", iuv="
        + iuv
        + ", codiceAvviso="
        + codiceAvviso
        + ", esitoRata="
        + esitoRata
        + ", dataCreazioneRata="
        + dataCreazioneRata
        + "]";
  }
}
