package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

public enum EnumCortesia {
  ALLERTA_METEO(1),
  ALLERTA_SOSTA(2),
  ALLERTA_INTERRUZIONE_ACQUA(3),
  SOGGETTI_EVENTI(4),
  AMT(500),
  UNKNOWN(-1);

  private int id;

  private EnumCortesia(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static EnumCortesia getCortesiaDaId(int id) {

    for (EnumCortesia e : values()) {
      if (e.id == id) return e;
    }

    return UNKNOWN;
  }
}
