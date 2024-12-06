package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

import java.io.Serializable;

public class DatiVerificaCellulareAllerteCortesia implements Serializable {

  private static final long serialVersionUID = 3939079610737539612L;

  private String email;

  private String cellulare;

  private String reinvia;

  private String codiceSms;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  public String getReinvia() {
    return reinvia;
  }

  public void setReinvia(String reinvia) {
    this.reinvia = reinvia;
  }

  public String getCodiceSms() {
    return codiceSms;
  }

  public void setCodiceSms(String codiceSms) {
    this.codiceSms = codiceSms;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatiVerificaCellulareAllerteCortesia [email=");
    builder.append(email);
    builder.append(", cellulare=");
    builder.append(cellulare);
    builder.append(", reinvia=");
    builder.append(reinvia);
    builder.append(", codiceSms=");
    builder.append(codiceSms);
    builder.append("]");
    return builder.toString();
  }
}
