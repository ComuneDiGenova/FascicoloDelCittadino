package it.liguriadigitale.ponmetro.portale.pojo.common;

public class DatiBaseLuogo {
  private String regione;
  private String provincia;
  private String comune;
  private String codiceComune;
  private String codiceAvviamentoPostale;

  public DatiBaseLuogo() {
    super();
  }

  public DatiBaseLuogo(
      String regione,
      String provincia,
      String comune,
      String codiceComune,
      String codiceAvviamentoPostale) {
    this();
    this.regione = regione;
    this.provincia = provincia;
    this.comune = comune;
    this.codiceComune = codiceComune;
    this.codiceAvviamentoPostale = codiceAvviamentoPostale;
  }

  public String getRegione() {
    return regione;
  }

  public void setRegione(String regione) {
    this.regione = regione;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getCodiceComune() {
    return codiceComune;
  }

  public void setCodiceComune(String codiceComune) {
    this.codiceComune = codiceComune;
  }

  public String getCodiceAvviamentoPostale() {
    return codiceAvviamentoPostale;
  }

  public void setCodiceAvviamentoPostale(String codiceAvviamentoPostale) {
    this.codiceAvviamentoPostale = codiceAvviamentoPostale;
  }

  @Override
  public String toString() {
    return "DatiBaseLuogo [regione="
        + regione
        + ", provincia="
        + provincia
        + ", comune="
        + comune
        + ", codiceComune="
        + codiceComune
        + ", codiceAvviamentoPostale="
        + codiceAvviamentoPostale
        + "]";
  }
}
