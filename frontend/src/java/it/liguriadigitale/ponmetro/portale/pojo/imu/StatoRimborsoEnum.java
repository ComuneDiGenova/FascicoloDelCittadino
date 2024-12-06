package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum StatoRimborsoEnum {
  C("In Compilazione"),
  P("Presentata"),
  S("Sospesa"),
  E("Evasa"),
  A("Annullata");

  private final String stato;

  StatoRimborsoEnum(final String text) {
    this.stato = text;
  }

  public static StatoRimborsoEnum getById(String id) {
    for (StatoRimborsoEnum e : values()) {
      if (e.stato.equalsIgnoreCase(id)) return e;
    }

    return null;
  }

  @Override
  public String toString() {
    return stato;
  }
}
