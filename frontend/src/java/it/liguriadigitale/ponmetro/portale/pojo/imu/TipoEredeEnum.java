package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum TipoEredeEnum {
  EREDE_UNICO("Erede Unico"),
  COEREDE("Coerede");

  private final String categoria;

  TipoEredeEnum(final String text) {
    this.categoria = text;
  }

  public static TipoEredeEnum getById(String id) {
    for (TipoEredeEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
