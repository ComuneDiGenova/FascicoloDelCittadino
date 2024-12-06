package it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento;

public enum TipoUtenzaEnum {
  DIRETTA_RIPARTITA(1, "Diretta/Ripartita"),
  CENTRALIZZATA(2, "Centralizzata");

  private Integer id;

  private String descrizione;

  private TipoUtenzaEnum(Integer id, String descrizione) {
    this.setId(id);
    this.setDescrizione(descrizione);
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
