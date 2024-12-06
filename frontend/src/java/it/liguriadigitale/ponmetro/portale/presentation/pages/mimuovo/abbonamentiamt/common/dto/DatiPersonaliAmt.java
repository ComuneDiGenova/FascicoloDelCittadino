package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class DatiPersonaliAmt implements Serializable {

  private static final long serialVersionUID = -2718192848216688246L;

  private String codiceFiscale;

  private String nome;

  private String cognome;

  private String nominativo;

  private LocalDate dataNascita;

  private String luogoNascita;

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

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getLuogoNascita() {
    return luogoNascita;
  }

  public void setLuogoNascita(String luogoNascita) {
    this.luogoNascita = luogoNascita;
  }

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  @Override
  public String toString() {
    return "DatiPersonaliAmt [codiceFiscale="
        + codiceFiscale
        + ", nominativo="
        + nominativo
        + ", dataNascita="
        + dataNascita
        + ", luogoNascita="
        + luogoNascita
        + "]";
  }
}
