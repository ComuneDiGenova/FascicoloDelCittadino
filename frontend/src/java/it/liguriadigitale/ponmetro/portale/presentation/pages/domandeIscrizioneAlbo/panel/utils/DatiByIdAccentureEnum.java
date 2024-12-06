package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.panel.utils;

public enum DatiByIdAccentureEnum {
  NOME(1, "NOME"),
  EMAIL(2, "EMAIL"),
  CELLULARE(3, "CELLULARE");

  private Integer id;

  private String descrizione;

  private DatiByIdAccentureEnum(Integer id, String descrizione) {
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
