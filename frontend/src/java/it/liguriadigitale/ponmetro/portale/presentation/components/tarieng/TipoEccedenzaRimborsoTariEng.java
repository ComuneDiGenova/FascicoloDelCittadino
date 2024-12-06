package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

public enum TipoEccedenzaRimborsoTariEng {
  TARI(1, "Eccedenza TARI"),
  TEFA(2, "Eccedenza TEFA"),
  TARIREALE(3, "Eccedenza da altri pagamenti");

  private Integer id;

  private String descrizione;

  private TipoEccedenzaRimborsoTariEng(Integer id, String descrizione) {
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
