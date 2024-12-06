package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;
import java.time.LocalDate;

public class RimborsiImuLight implements Serializable {
  private static final long serialVersionUID = 132132156498765L;

  private String numeroDocumento;
  private int annoDocumento;
  private LocalDate dataPresentazione;
  private StatoRimborsoEnum statoPratica;
  private int uri;

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public int getAnnoDocumento() {
    return annoDocumento;
  }

  public void setAnnoDocumento(int annoDocumento) {
    this.annoDocumento = annoDocumento;
  }

  public LocalDate getDataPresentazione() {
    return dataPresentazione;
  }

  public void setDataPresentazione(LocalDate dataPresentazione) {
    this.dataPresentazione = dataPresentazione;
  }

  public StatoRimborsoEnum getStatoPratica() {
    return statoPratica;
  }

  public void setStatoPratica(StatoRimborsoEnum statoPratica) {
    this.statoPratica = statoPratica;
  }

  public int getUri() {
    return uri;
  }

  public void setUri(int uri) {
    this.uri = uri;
  }
}
