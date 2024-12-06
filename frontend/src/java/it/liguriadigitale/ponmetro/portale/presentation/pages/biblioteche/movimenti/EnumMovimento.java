package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti;

public enum EnumMovimento {
  PRESTITI(1),
  PRENOTAZIONI(2),
  TUTTI(3);

  private int id;

  private EnumMovimento(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static EnumMovimento getMovimentoDaId(int id) {
    return EnumMovimento.values()[id - 1];
  }
}
