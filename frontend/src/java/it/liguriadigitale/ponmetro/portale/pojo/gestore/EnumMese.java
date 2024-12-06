package it.liguriadigitale.ponmetro.portale.pojo.gestore;

public enum EnumMese {
  GENNAIO(1),
  FEBBRAIO(2),
  MARZO(3),
  APRILE(4),
  MAGGIO(5),
  GIUGNO(6),
  LUGLIO(7),
  AGOSTO(8),
  SETTEMBRE(9),
  OTTOBRE(10),
  NOVEMBRE(11),
  DICEMBRE(12);

  private int id;

  private EnumMese(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static EnumMese getMeseDaId(int id) {
    return EnumMese.values()[id - 1];
  }
}
