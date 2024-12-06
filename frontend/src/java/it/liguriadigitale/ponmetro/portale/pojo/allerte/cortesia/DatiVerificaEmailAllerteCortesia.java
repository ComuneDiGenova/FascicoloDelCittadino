package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

import java.io.Serializable;

public class DatiVerificaEmailAllerteCortesia implements Serializable {

  private static final long serialVersionUID = -1476917277377432139L;

  private String email;

  private String cellulare;

  private String reinvia;

  private String codiceEmail;

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

  public String getCodiceEmail() {
    return codiceEmail;
  }

  public void setCodiceEmail(String codiceEmail) {
    this.codiceEmail = codiceEmail;
  }

  @Override
  public String toString() {
    return "DatiVerificaEmailAllerteCortesia [email="
        + email
        + ", cellulare="
        + cellulare
        + ", reinvia="
        + reinvia
        + ", codiceEmail="
        + codiceEmail
        + "]";
  }
}
