package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util;

public enum ColoriEnum {
  NERO(1, "Nero"),
  ROSSO(2, "Rosso");

  private Integer id;

  private String descrizione;

  private ColoriEnum(Integer id, String descrizione) {
    this.id = id;
    this.descrizione = descrizione;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
}
