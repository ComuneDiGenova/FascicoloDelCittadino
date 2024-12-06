package it.liguriadigitale.ponmetro.portale.business.tributi.imu.ex;

public class IstanzaRimborsoResponse {

  // {"status":"NOT_FOUND","timestamp":"02-12-2022 10:32:52","message":"Il soggetto tutelato non
  // risulta presente in anagrafica "}

  private String status;
  private String timestamp;
  private String message;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
