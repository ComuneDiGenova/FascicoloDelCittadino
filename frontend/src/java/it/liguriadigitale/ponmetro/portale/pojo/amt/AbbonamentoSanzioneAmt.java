package it.liguriadigitale.ponmetro.portale.pojo.amt;

public enum AbbonamentoSanzioneAmt {
  ABBONAMENTO(1, "abbonamento"),
  SANZIONE(2, "sanzione");

  private Integer id;

  private String descrizione;

  private AbbonamentoSanzioneAmt(Integer id, String descrizione) {
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
