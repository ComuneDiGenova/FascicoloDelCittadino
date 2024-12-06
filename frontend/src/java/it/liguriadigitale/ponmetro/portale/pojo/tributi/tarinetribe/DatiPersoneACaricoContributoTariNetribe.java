package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.tarinetribe.model.Cittadinanza;
import java.io.Serializable;

public class DatiPersoneACaricoContributoTariNetribe implements Serializable {

  private static final long serialVersionUID = 7991983408887510634L;

  private String nome;

  private String cognome;

  private String codiceFiscale;

  private Cittadinanza cittadinanza;

  private String flagIsACarico;

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

  public Cittadinanza getCittadinanza() {
    return cittadinanza;
  }

  public void setCittadinanza(Cittadinanza cittadinanza) {
    this.cittadinanza = cittadinanza;
  }

  public String getFlagIsACarico() {
    return flagIsACarico;
  }

  public void setFlagIsACarico(String flagIsACarico) {
    this.flagIsACarico = flagIsACarico;
  }

  @Override
  public String toString() {
    return "DatiPersoneACaricoContributoTariNetribe [nome="
        + nome
        + ", cognome="
        + cognome
        + ", codiceFiscale="
        + codiceFiscale
        + ", cittadinanza="
        + cittadinanza
        + ", flagIsACarico="
        + flagIsACarico
        + "]";
  }
}
