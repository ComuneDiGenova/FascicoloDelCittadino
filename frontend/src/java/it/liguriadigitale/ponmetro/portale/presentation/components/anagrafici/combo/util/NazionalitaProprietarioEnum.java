package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util;

public enum NazionalitaProprietarioEnum {
  ITALIANO("1", "ITALIANO"),
  STRANIERO("2", "STRANIERO");

  private String id;

  private String descrizione;

  private NazionalitaProprietarioEnum(String id, String descrizione) {
    this.id = id;
    this.descrizione = descrizione;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
}
