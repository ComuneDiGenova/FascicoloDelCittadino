package it.liguriadigitale.ponmetro.portale.pojo.dietespeciali;

import java.io.Serializable;

public class RevocaDietaSpeciale implements Serializable {

  private static final long serialVersionUID = -8476247222147023396L;

  private String codiceFiscaleIscritto;

  private String identificativo;

  private String codiceFiscaleRichiedenteRevoca;

  private String nomeRichiedenteRevoca;

  private String cognomeRichiedenteRevoca;

  private String emailRichiedenteRevoca;

  public String getCodiceFiscaleIscritto() {
    return codiceFiscaleIscritto;
  }

  public void setCodiceFiscaleIscritto(String codiceFiscaleIscritto) {
    this.codiceFiscaleIscritto = codiceFiscaleIscritto;
  }

  public String getIdentificativo() {
    return identificativo;
  }

  public void setIdentificativo(String identificativo) {
    this.identificativo = identificativo;
  }

  public String getCodiceFiscaleRichiedenteRevoca() {
    return codiceFiscaleRichiedenteRevoca;
  }

  public void setCodiceFiscaleRichiedenteRevoca(String codiceFiscaleRichiedenteRevoca) {
    this.codiceFiscaleRichiedenteRevoca = codiceFiscaleRichiedenteRevoca;
  }

  public String getNomeRichiedenteRevoca() {
    return nomeRichiedenteRevoca;
  }

  public void setNomeRichiedenteRevoca(String nomeRichiedenteRevoca) {
    this.nomeRichiedenteRevoca = nomeRichiedenteRevoca;
  }

  public String getCognomeRichiedenteRevoca() {
    return cognomeRichiedenteRevoca;
  }

  public void setCognomeRichiedenteRevoca(String cognomeRichiedenteRevoca) {
    this.cognomeRichiedenteRevoca = cognomeRichiedenteRevoca;
  }

  public String getEmailRichiedenteRevoca() {
    return emailRichiedenteRevoca;
  }

  public void setEmailRichiedenteRevoca(String emailRichiedenteRevoca) {
    this.emailRichiedenteRevoca = emailRichiedenteRevoca;
  }

  @Override
  public String toString() {
    return "RevocaDietaSpeciale [codiceFiscaleIscritto="
        + codiceFiscaleIscritto
        + ", identificativo="
        + identificativo
        + ", codiceFiscaleRichiedenteRevoca="
        + codiceFiscaleRichiedenteRevoca
        + ", nomeRichiedenteRevoca="
        + nomeRichiedenteRevoca
        + ", cognomeRichiedenteRevoca="
        + cognomeRichiedenteRevoca
        + ", emailRichiedenteRevoca="
        + emailRichiedenteRevoca
        + "]";
  }
}
