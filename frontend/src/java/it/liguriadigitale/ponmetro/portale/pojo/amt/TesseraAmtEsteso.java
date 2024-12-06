package it.liguriadigitale.ponmetro.portale.pojo.amt;

import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import java.time.LocalDate;

public class TesseraAmtEsteso extends Tessera {

  private static final long serialVersionUID = -8951339936761224026L;

  private String nominativo;

  private String nome;

  private String cognome;

  private String cf;

  private String messaggioNuovaTessera;

  private String urlNuovaTessera;

  private LocalDate dataNascita;

  public TesseraAmtEsteso() {
    super();
  }

  public TesseraAmtEsteso(String taxCode, String nominativo) {
    setCf(taxCode);
    setNominativo(nominativo);
  }

  public TesseraAmtEsteso(
      String taxCode, String nominativo, String messaggioNuovaTessera, String urlNuovaTessera) {
    setCf(taxCode);
    setNominativo(nominativo);
    setMessaggioNuovaTessera(messaggioNuovaTessera);
    setUrlNuovaTessera(urlNuovaTessera);
  }

  public TesseraAmtEsteso(
      String taxCode,
      String nominativo,
      String nome,
      String cognome,
      LocalDate dataNascita,
      String messaggioNuovaTessera,
      String urlNuovaTessera) {
    setCf(taxCode);
    setNominativo(nominativo);
    setNome(nome);
    setCognome(cognome);
    setDataNascita(dataNascita);
    setMessaggioNuovaTessera(messaggioNuovaTessera);
    setUrlNuovaTessera(urlNuovaTessera);
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

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getMessaggioNuovaTessera() {
    return messaggioNuovaTessera;
  }

  public void setMessaggioNuovaTessera(String messaggioNuovaTessera) {
    this.messaggioNuovaTessera = messaggioNuovaTessera;
  }

  public String getUrlNuovaTessera() {
    return urlNuovaTessera;
  }

  public void setUrlNuovaTessera(String urlNuovaTessera) {
    this.urlNuovaTessera = urlNuovaTessera;
  }

  @Override
  public String toString() {
    return "TesseraAmtEsteso [nominativo="
        + nominativo
        + ", cf="
        + cf
        + ", messaggioNuovaTessera="
        + messaggioNuovaTessera
        + ", urlNuovaTessera="
        + urlNuovaTessera
        + "]";
  }
}
