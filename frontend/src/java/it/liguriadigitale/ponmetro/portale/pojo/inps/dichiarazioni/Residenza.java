package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class Residenza implements Serializable {

  private static final long serialVersionUID = -5697370091146256691L;

  private String Indirizzo;
  private String Civico;
  private String CodiceComune;
  private String Provincia;
  private String Cap;
  private String Telefono;

  public String getIndirizzo() {
    return Indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    Indirizzo = indirizzo;
  }

  public String getCivico() {
    return Civico;
  }

  public void setCivico(String civico) {
    Civico = civico;
  }

  public String getCodiceComune() {
    return CodiceComune;
  }

  public void setCodiceComune(String codiceComune) {
    CodiceComune = codiceComune;
  }

  public String getProvincia() {
    return Provincia;
  }

  public void setProvincia(String provincia) {
    Provincia = provincia;
  }

  public String getCap() {
    return Cap;
  }

  public void setCap(String cap) {
    Cap = cap;
  }

  public String getTelefono() {
    return Telefono;
  }

  public void setTelefono(String telefono) {
    Telefono = telefono;
  }

  @Override
  public String toString() {
    return "Residenza [Indirizzo="
        + Indirizzo
        + ", Civico="
        + Civico
        + ", CodiceComune="
        + CodiceComune
        + ", Provincia="
        + Provincia
        + ", Cap="
        + Cap
        + ", Telefono="
        + Telefono
        + "]";
  }
}
