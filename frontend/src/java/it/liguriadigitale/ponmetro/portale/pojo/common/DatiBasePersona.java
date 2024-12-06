package it.liguriadigitale.ponmetro.portale.pojo.common;

public class DatiBasePersona extends DatiBaseNascita {
  private String nome;
  private String cognome;
  private String codiceFiscale;

  public DatiBasePersona() {
    super();
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }
}
