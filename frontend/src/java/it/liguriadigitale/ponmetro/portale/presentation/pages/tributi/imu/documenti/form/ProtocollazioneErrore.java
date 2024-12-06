package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form;

import java.time.LocalDate;

public class ProtocollazioneErrore {

  private String status;
  private LocalDate data;
  private String message;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
