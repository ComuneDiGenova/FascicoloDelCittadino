package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util;

public enum SiNoEnum {
  SI(true, "Sì"),
  NO(false, "No");

  private boolean id;

  private String descrizione;

  private SiNoEnum(boolean id, String descrizione) {
    this.id = id;
    this.descrizione = descrizione;
  }

  public boolean isId() {
    return id;
  }

  public void setId(boolean id) {
    this.id = id;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
}
