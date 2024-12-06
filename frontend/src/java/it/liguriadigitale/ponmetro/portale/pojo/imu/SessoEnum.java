package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum SessoEnum {
  F("Femmina"),
  M("Maschio");

  private final String categoria;

  SessoEnum(final String text) {
    this.categoria = text;
  }

  public static SessoEnum getById(String id) {
    for (SessoEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
