package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

public enum TipiDocumentiDaCaricare {
  CI(1, "Carta d'identità"),
  PASSAPORTO(2, "Passaporto"),
  PATENTE(3, "Patente");

  private Integer id;

  private String descrizione;

  private TipiDocumentiDaCaricare(Integer id, String descrizione) {
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
