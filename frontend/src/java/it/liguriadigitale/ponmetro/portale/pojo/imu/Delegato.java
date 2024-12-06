package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;
import java.time.LocalDate;

public class Delegato implements Serializable {
  private static final long serialVersionUID = 19876532131587963L;
  private String cognomeDelegato;
  private String nomeDelegato;
  private String codiceFiscaleDelegato;
  private LocalDate dataNascitaDelegato;
  private SessoEnum sessoDelegato;
  private String luogoNascitaDelegato;
  private String cittadinanzaDelegato;

  public String getCognomeDelegato() {
    return cognomeDelegato;
  }

  public void setCognomeDelegato(String cognome) {
    this.cognomeDelegato = cognome;
  }

  public String getNomeDelegato() {
    return nomeDelegato;
  }

  public void setNomeDelegato(String nome) {
    this.nomeDelegato = nome;
  }

  public String getCodiceFiscaleDelegato() {
    return codiceFiscaleDelegato;
  }

  public void setCodiceFiscaleDelegato(String codiceFiscale) {
    this.codiceFiscaleDelegato = codiceFiscale;
  }

  public LocalDate getDataNascitaDelegato() {
    return dataNascitaDelegato;
  }

  public void setDataNascitaDelegato(LocalDate dataNascita) {
    this.dataNascitaDelegato = dataNascita;
  }

  public SessoEnum getSessoDelegato() {
    return sessoDelegato;
  }

  public void setSessoDelegato(SessoEnum sesso) {
    this.sessoDelegato = sesso;
  }

  public String getLuogoNascitaDelegato() {
    return luogoNascitaDelegato;
  }

  public void setLuogoNascitaDelegato(String luogoNascita) {
    this.luogoNascitaDelegato = luogoNascita;
  }

  public String getCittadinanzaDelegato() {
    return cittadinanzaDelegato;
  }

  public void setCittadinanzaDelegato(String cittadinanza) {
    this.cittadinanzaDelegato = cittadinanza;
  }
}
