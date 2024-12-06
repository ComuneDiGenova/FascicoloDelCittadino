package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

import java.io.Serializable;

public class DatiRegistrazioneAllerteCortesia implements Serializable {

  private static final long serialVersionUID = -7648826449988616949L;

  private String cognome;

  private String nome;

  private String codiceFiscale;

  private String email;

  private String password;

  private String telefonoFisso;

  private String telefonoCellulare;

  private String indirizzo;

  private String comune;

  private String cap;

  private String provincia;

  private String nazione;

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTelefonoFisso() {
    return telefonoFisso;
  }

  public void setTelefonoFisso(String telefonoFisso) {
    this.telefonoFisso = telefonoFisso;
  }

  public String getTelefonoCellulare() {
    return telefonoCellulare;
  }

  public void setTelefonoCellulare(String telefonoCellulare) {
    this.telefonoCellulare = telefonoCellulare;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getNazione() {
    return nazione;
  }

  public void setNazione(String nazione) {
    this.nazione = nazione;
  }

  @Override
  public String toString() {
    return "DatiRegistrazioneAllerteCortesia [cognome="
        + cognome
        + ", nome="
        + nome
        + ", codiceFiscale="
        + codiceFiscale
        + ", email="
        + email
        + ", password="
        + password
        + ", telefonoFisso="
        + telefonoFisso
        + ", telefonoCellulare="
        + telefonoCellulare
        + ", indirizzo="
        + indirizzo
        + ", comune="
        + comune
        + ", cap="
        + cap
        + ", provincia="
        + provincia
        + ", nazione="
        + nazione
        + "]";
  }
}
