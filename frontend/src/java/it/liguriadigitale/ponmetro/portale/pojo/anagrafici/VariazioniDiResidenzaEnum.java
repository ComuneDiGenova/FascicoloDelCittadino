package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

public enum VariazioniDiResidenzaEnum {
  CAMBIO_INDIRIZZO(1, "Cambio indirizzo"),
  RICHIESTA_RESIDENZA(2, "Richiesta residenza");

  private Integer id;

  private String descrizione;

  private VariazioniDiResidenzaEnum(Integer id, String descrizione) {
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
