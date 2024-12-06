package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.time.LocalDate;

public class Anagrafica implements Serializable {

  private static final long serialVersionUID = -772110301249045901L;

  private String Cognome;
  private String Nome;
  private LocalDate DataNascita;
  private String Cittadinanza;
  private String Sesso;
  private String ProvinciaNascita;
  private String CodiceComuneNascita;

  public String getCognome() {
    return Cognome;
  }

  public void setCognome(String cognome) {
    Cognome = cognome;
  }

  public String getNome() {
    return Nome;
  }

  public void setNome(String nome) {
    Nome = nome;
  }

  public LocalDate getDataNascita() {
    return DataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    DataNascita = dataNascita;
  }

  public String getCittadinanza() {
    return Cittadinanza;
  }

  public void setCittadinanza(String cittadinanza) {
    Cittadinanza = cittadinanza;
  }

  public String getSesso() {
    return Sesso;
  }

  public void setSesso(String sesso) {
    Sesso = sesso;
  }

  public String getProvinciaNascita() {
    return ProvinciaNascita;
  }

  public void setProvinciaNascita(String provinciaNascita) {
    ProvinciaNascita = provinciaNascita;
  }

  public String getCodiceComuneNascita() {
    return CodiceComuneNascita;
  }

  public void setCodiceComuneNascita(String codiceComuneNascita) {
    CodiceComuneNascita = codiceComuneNascita;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "Anagrafica [Cognome="
        + Cognome
        + ", Nome="
        + Nome
        + ", DataNascita="
        + DataNascita
        + ", Cittadinanza="
        + Cittadinanza
        + ", Sesso="
        + Sesso
        + ", ProvinciaNascita="
        + ProvinciaNascita
        + ", CodiceComuneNascita="
        + CodiceComuneNascita
        + "]";
  }
}
