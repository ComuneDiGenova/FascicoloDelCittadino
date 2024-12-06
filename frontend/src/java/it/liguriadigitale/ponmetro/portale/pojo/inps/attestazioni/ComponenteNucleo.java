package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;

public class ComponenteNucleo implements Serializable {

  private static final long serialVersionUID = -5611990584829094489L;
  private String RapportoConDichiarante;
  private String Cognome;
  private String Nome;
  private String CodiceFiscale;

  public String getRapportoConDichiarante() {
    return RapportoConDichiarante;
  }

  public void setRapportoConDichiarante(String rapportoConDichiarante) {
    RapportoConDichiarante = rapportoConDichiarante;
  }

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

  public String getCodiceFiscale() {
    return CodiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    CodiceFiscale = codiceFiscale;
  }

  @Override
  public String toString() {
    return "ComponenteNucleo [RapportoConDichiarante="
        + RapportoConDichiarante
        + ", Cognome="
        + Cognome
        + ", Nome="
        + Nome
        + ", CodiceFiscale="
        + CodiceFiscale
        + "]";
  }
}
