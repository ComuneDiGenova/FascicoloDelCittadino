package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum TipoImmobileEnum {
  F("Unità immobiliare"),
  T("Particella terreni");

  private final String categoria;

  TipoImmobileEnum(final String text) {
    this.categoria = text;
  }

  public static TipoImmobileEnum getById(String id) {
    for (TipoImmobileEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
