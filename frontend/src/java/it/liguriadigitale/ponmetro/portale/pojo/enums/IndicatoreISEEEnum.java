package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum IndicatoreISEEEnum {
  ORDINARIO(1, "Ordinario"),
  MINORENNE(2, "Minorenne"),
  UNIVERSITARIO(3, "Universitario"),
  SOCIOSANITARIO(4, "Sociosanitario"),
  DOTTORATO(5, "Dottorato"),
  RESIDENZIALE(6, "Residenziale");

  private Integer id;

  private String descrizione;

  private IndicatoreISEEEnum(Integer id, String descrizione) {
    this.setId(id);
    this.setDescrizione(descrizione);
  }

  public Integer getId() {
    return id;
  }

  private void setId(Integer id) {
    this.id = id;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
}
